package com.gmonetix.bookspivot.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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


        // Get ListView object from xml
        ListView listView = (ListView) findViewById(R.id.list);

        // Defined Array values to show in ListView
        String[] values = new String[] { "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"
        };

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);



    }

}
