package com.gmonetix.bookspivot.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.gmonetix.bookspivot.R;

public class DownloadsActivity extends AppCompatActivity {

    ListView list;
    String[] book = {
            "Book 1",
            "Book 2",
            "Book 3",
            "Book 4",
            "Book 5",
            "Book 6",
            "Book 7"
    } ;
    Integer[] imageId = {
            R.drawable.bglogin2,
            R.drawable.bglogin2,
            R.drawable.bglogin2,
            R.drawable.bglogin2,
            R.drawable.bglogin2,
            R.drawable.bglogin2,
            R.drawable.bglogin2

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloads);

        CustomList adapter = new
                CustomList(DownloadsActivity.this, book, imageId);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(DownloadsActivity.this, "You Clicked at " +book[+ position], Toast.LENGTH_SHORT).show();

            }
        });

    }

}




