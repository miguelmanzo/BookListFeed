package com.example.android.booklistfeed;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.Loader;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {


    private static final String LOG_TAG = BookActivity.class.getName();

    private SearchView mSearchViewField;

    ListView bookListView;

    private static String DEFAULT_URL = "https://www.googleapis.com/books/v1/volumes?q=harry+potter&orderBy=relevance&maxResults=10&langRestrict=en";

    private View loadingIndicator;

    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int BOOK_LOADER_ID = 1;

    /** Adapter for the list of books */
    public BookAdapter mAdapter;

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    /**
     * URL for books data from the Google Books API
     */
    private String mUrlRequestGoogleBooks = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);


        mSearchViewField = (SearchView) findViewById(R.id.search_view);

        mSearchViewField.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                    updateQueryUrl(query.toString());
                    restartLoader();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                return false;
            }
        });


        // Find a reference to the {@link ListView} in the layout
        bookListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes an empty list of books as input
        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setAdapter(mAdapter);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

       /* bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Book bookInstance = mAdapter.getItem(position);

                Uri bookUri = Uri.parse(bookInstance.getmPreview());

                Intent i = new Intent(Intent.ACTION_VIEW, bookUri);

                startActivity(i);

            }
        });
*/
    }

    /**
     * Check if query contains spaces if YES replace these with PLUS sign
     *
     * @param searchValue - user data from SearchView
     * @return improved String URL for making HTTP request
     */
    private String updateQueryUrl(String searchValue) {

        if (searchValue.contains(" ")) {
            searchValue = searchValue.replace(" ", "+");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("https://www.googleapis.com/books/v1/volumes?q=").append(searchValue).append("&filter=paid-ebooks&maxResults=20&langRestrict=en");
        mUrlRequestGoogleBooks = sb.toString();
        return mUrlRequestGoogleBooks;
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        if(
                mSearchViewField.getQuery().toString().isEmpty()){mUrlRequestGoogleBooks = DEFAULT_URL;
        }
        else {
            updateQueryUrl(mSearchViewField.getQuery().toString());
        }
            return new BookLoader(this, mUrlRequestGoogleBooks);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        // Hide loading indicator because the data has been loaded
        loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No earthquakes found."
        mEmptyStateTextView.setText(R.string.no_earthquakes);

        // Clear the adapter of previous earthquake data
        mAdapter.clear();
        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            //mAdapter.addAll(earthquakes);
            updateUi(books);
        }
    }
    private void updateUi(List<Book> books) {
        //ArrayList<Book> earthquakes = QueryUtils.extractEarthquakes();
        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of books
        BookAdapter adapter = new BookAdapter(this, books);
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setAdapter(adapter);
    }



    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();

    }

    public void restartLoader() {
        mEmptyStateTextView.setVisibility(View.GONE);
        loadingIndicator.setVisibility(View.VISIBLE);
        getLoaderManager().restartLoader(BOOK_LOADER_ID, null, BookActivity.this);
    }


}
