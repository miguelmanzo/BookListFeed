package com.example.android.booklistfeed;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
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

        // Find the earthquake at the given position in the list of earthquakes
        final Book currentBook = getItem(position);


        // Find the TextView with view ID title
        TextView primaryLocationView = (TextView) listItemView.findViewById(R.id.title);
        // Display the location of the current earthquake in that TextView
        primaryLocationView.setText(currentBook.getmTitle());

       // Find the TextView with view ID author
        TextView authorView = (TextView) listItemView.findViewById(R.id.author);
        // Display the location of the current author in that TextView
        authorView.setText(currentBook.getmAuthor());


        ImageView img = listItemView.findViewById(R.id.book_cover);

        img.setImageBitmap(currentBook.getmCover());


        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri bookUri = Uri.parse(currentBook.getmPreview());

                Intent i = new Intent(Intent.ACTION_VIEW, bookUri);

                v.getContext().startActivity(i);}});


        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }

}

