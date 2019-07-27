package com.example.android.booklistfeed;

import android.graphics.Bitmap;

/**
 * An {@link Book} object contains information related to a single book.
 */

public class Book {

    /*Title of the book*/
    private String mTitle;

    /*Author of the book*/
    private String mAuthor;

    /*Cover of the book*/
    private Bitmap mCover;

    /*Preview URL of the book*/
    private String mPreview;

    public Book(String title) {
        mTitle = title;
    }

    public Book(String title, Bitmap cover) {
        mTitle = title;
        mCover = cover;

    }
    public Book(String title, String author, Bitmap cover, String preview) {
        mTitle = title;
        mAuthor = author;
        mCover = cover;
        mPreview = preview;

    }

    public String getmTitle() {
        return mTitle;
    }


    public String getmAuthor() {
        return mAuthor;
    }


    public Bitmap getmCover() {
        return mCover;
    }


    public String getmPreview() {
        mPreview = "https://play.google.com/store/books/details?id=ilc0DwAAQBAJ";
        return mPreview;
    }
}
