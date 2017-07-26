package com.example.xella.booklistingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

    /**
     * Constructs a new {@link BookAdapter}.
     *
     * @param context of the app
     * @param books is the list of earthquakes, which is the data source of the adapter
     */
    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    /**
     * Returns a list item view that displays information about the book at the given position
     * in the list of books.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
        }

        // Find the book at the given position in the list of books
        Book currentBook = getItem(position);

        // Find the TextView with view ID title
        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        // Display the Title of the current book in that TextView
        titleView.setText(currentBook.getTitle());

        // Find the TextView with view ID author
        TextView authorView = (TextView) listItemView.findViewById(R.id.author);
        // Display the authore of the current book in that TextView
        authorView.setText(currentBook.getAuthor());

        // Find the TextView with view ID description
        TextView descriptionView = (TextView) listItemView.findViewById(R.id.description);
        // Display the description of the current book in that TextView
        descriptionView.setText(currentBook.getDescription());

        // Find the TextView with view ID publisher
        TextView publisherView = (TextView) listItemView.findViewById(R.id.publisher);
        // Display the publisher of the current book in that TextView
        publisherView.setText(currentBook.getPublisher());

        // Find the TextView with view ID publisher
        TextView publishedDateView = (TextView) listItemView.findViewById(R.id.published_date);
        // Display the publisher of the current book in that TextView
        publishedDateView.setText(currentBook.getPublishedDate());

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }
}
