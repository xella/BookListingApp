package com.example.xella.booklistingapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Google API URL
    private static final String BOOKS_URL = "https://www.googleapis.com/books/v1/volumes?maxResults=10&q=";

    private static final String LOG_TAG = MainActivity.class.getName();

    /** Adapter for the list of books */
    private BookAdapter bookAdapter;

    // TextView visible when there is a problem with the internet connection and the list is empty
    private TextView emptyStateTextView;

    // Progress bar visible when the internet connection is delayed or slow
    private View loadingIndicator;

    private EditText editTextView;
    private Button searchButton;
    private ListView booksListView;

    private static final String LIST_STATE = "list";
    private Parcelable mListState = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(LOG_TAG,"onCreate() called..");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initiatilzing layout elements EditText, Button and ListView, ProgressBar and TextView for empty state of the list
        editTextView = (EditText) findViewById(R.id.search_field);
        searchButton = (Button) findViewById(R.id.search_btn);
        booksListView = (ListView) findViewById(R.id.list_view);
        loadingIndicator = findViewById(R.id.loading_indicator);
        emptyStateTextView = (TextView) findViewById(R.id.empty_view);
        Log.v(LOG_TAG, "Layout elements are initialized");

        booksListView.setEmptyView(emptyStateTextView);
        emptyStateTextView.setText(R.string.no_data_yet);

        final ArrayList<Book> booksResult = new ArrayList<>();

        // Set up the adapter for the ListView
        bookAdapter = new BookAdapter(MainActivity.this, booksResult);
        booksListView.setAdapter(bookAdapter);

        // Set the OnClickListener for the seatch button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emptyStateTextView.setVisibility(View.GONE);
                Log.v(LOG_TAG, "Search button onClickListener called");
                String urlEnding = editTextView.getText().toString();
                if (urlEnding.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Search field is empty!", Toast.LENGTH_LONG).show();
                    return;
                }
                String searchUrl = BOOKS_URL + urlEnding;
                BookAsyncTask task = new BookAsyncTask();
                task.execute(searchUrl);
                loadingIndicator.setVisibility(View.VISIBLE);
                Log.v(LOG_TAG,"Looking for url: " + searchUrl);
            }
        });
    }

    private void updateUI(List<Book> booksData) {
        if (booksData != null && !booksData.isEmpty()) {
            loadingIndicator.setVisibility(View.GONE);
            //BookAdapter bookAdapter = (BookAdapter) booksListView.getAdapter();
            bookAdapter.clear();
            bookAdapter.addAll(booksData);
            booksListView.setVisibility(View.VISIBLE);
        } else {
            booksListView.setVisibility(View.GONE);
        }
    }

    // Write list state to bundle
    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        mListState = booksListView.onSaveInstanceState();
        state.putParcelable(LIST_STATE, mListState);
    }

    // Restore list state from bundle
    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        mListState = state.getParcelable(LIST_STATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // ONLY call this part once the data items have been loaded back into the adapter
        // for example, inside a success callback from the network
        if (mListState != null) {
            booksListView.onRestoreInstanceState(mListState);
            mListState = null;
        }
    }

    private class BookAsyncTask extends AsyncTask<String, Void, List<Book>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            booksListView.setVisibility(View.GONE);
            emptyStateTextView.setVisibility(View.GONE);
            loadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Book> doInBackground(String... searchUrl) {
            if (isConnected()) {
                Log.v(LOG_TAG, "There is an internet connection");
                if (searchUrl[0].equals(BOOKS_URL)) {
                    return null;
                }
                return QueryUtils.fetchBookData(searchUrl[0]);
            }
            Log.v(LOG_TAG, "No internet connection");
            return null;
        }

        @Override
        protected void onPostExecute(List<Book> booksData) {
            // Clear the adapter of previous earthquake data
            bookAdapter.clear();
            loadingIndicator.setVisibility(View.GONE);
            if (isConnected()) {
                // If there is a valid list of {@link Book}s, then add then to the adapter's
                // data set. This will trigger the ListView to update.
                if (booksData != null && !booksData.isEmpty()) {
                    updateUI(booksData);
                } else {
                    emptyStateTextView.setText(R.string.no_books);
                }
            } else {
                emptyStateTextView.setText(R.string.no_internet_connection);
            }
        }
    }

    public boolean isConnected() {
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
