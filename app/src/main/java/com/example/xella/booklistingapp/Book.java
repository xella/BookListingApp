package com.example.xella.booklistingapp;

/**
 * Created by maria on 26/07/17.
 */

public class Book {

    private String mTitle;

    private String mAuthor;

    private String mDescription;

    private String mPublisher;

    private String mPublishedDate;

    public Book(String title, String author, String description, String publisher, String publishedDate) {
        mTitle = title;
        mAuthor = author;
        mDescription = description;
        mPublisher = publisher;
        mPublishedDate = publishedDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getPublisher() {
        return mPublisher;
    }

    public String getPublishedDate() {
        return mPublishedDate;
    }
}
