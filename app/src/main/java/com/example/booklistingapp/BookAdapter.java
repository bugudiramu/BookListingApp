package com.example.booklistingapp;

// Array Adapter

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class BookAdapter extends ArrayAdapter<Book> {
    BookAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {


        Book book = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
        }

//        TextView title = (TextView) view.findViewById(R.id.book_title);
//        TextView subtitle = (TextView) view.findViewById(R.id.book_subtitle);
        ImageView image = view.findViewById(R.id.book_image);
//        TextView imageUrl = view.findViewById(R.id.book_image);

        assert book != null;
//        title.setText(book.getTitle());
//        subtitle.setText(book.getSubtitle());
//        imageUrl.setText(book.getImageUrl());
        // Loading images using picasso
//        String imageUri = book.getImageUrl();
//        Log.v("ImageUri", imageUri);
//        Picasso.with(getContext()).load(imageUri).into(image);
        Picasso.with(getContext()).load(book.getImageUrl()).
                error(R.drawable.ic_launcher_background).
                noFade().
                into((ImageView) image);
        return view;

    }
}