package com.gmonetix.bookspivot.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.gmonetix.bookspivot.R;


public class BookDetails extends AppCompatActivity {

    private EditText mAuthorname,mBookname,mVersionnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        mAuthorname=(EditText)findViewById(R.id.input_author);
        mBookname=(EditText)findViewById(R.id.input_bookName);
        mVersionnumber=(EditText)findViewById(R.id.input_version);
    }
}
