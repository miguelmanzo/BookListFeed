package com.example.android.booklistfeed;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    /** Tag for log messages */
    private static final String LOG_TAG = BookLoader.class.getName();

    /** Query URL */
    private String mUrl;
    protected List<Book> mData;
    /**
     * Constructs a new {@link BookLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    /*@Override
    protected void onStartLoading() {
        forceLoad();
    }
*/
    /**
     * This is on a background thread.
     */
    @Override
    public List<Book> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<Book> books = QueryUtils.fetchBookData(mUrl);
        return books;
    }
    @Override
    protected void onStartLoading(){
        Log.d( LOG_TAG, "onStartLoading" );
        forceLoad();

    }

    @Override
    protected void onStopLoading(){
        Log.d( LOG_TAG, "onStopLoading" );
        cancelLoad();
    }

    @Override
    protected void onReset(){
        Log.d( LOG_TAG, "onReset" );
        super.onReset();
        onStopLoading();
        if(mData !=null){
            mData=null;
        }
    }

}

