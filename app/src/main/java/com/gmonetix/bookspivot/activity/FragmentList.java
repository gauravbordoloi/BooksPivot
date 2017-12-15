package com.gmonetix.bookspivot.activity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmonetix.bookspivot.R;

/**
 * Created by this-is-nsh on 15/12/17.
 */

public class FragmentList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] name;
    private final String[] upload;
    private final String[] download;
    private final Integer[] imageId;

    public FragmentList(Activity context,
                        String[] name, Integer[] imageId, String[]  upload, String[]  download ) {
        super(context, R.layout.list_single, name );
        this.context = context;
        this.name = name;
        this.upload= upload;
        this.download= download;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_fragment, null, true);
        TextView title = (TextView) rowView.findViewById(R.id.book_title);
        TextView upload_b = (TextView) rowView.findViewById(R.id.book_upload);
        TextView download_b = (TextView) rowView.findViewById(R.id.book_downloads);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.book1);
        title.setText(name[position]);
        upload_b.setText(upload[position]);
        download_b.setText(download[position]);
        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}