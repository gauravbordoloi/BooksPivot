package com.gmonetix.bookspivot.activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.SupportActivity;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gmonetix.bookspivot.R;


public class firstfragment extends Fragment {
    // Store instance variables
    private String title;
    private int page;

    ListView list;
    String[] book_name = {
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

    String[] book_upload = {
            "Uploaded on Aug 26, 2017",
            "Uploaded on Aug 26, 2017",
            "Uploaded on Aug 26, 2017",
            "Uploaded on Aug 26, 2017",
            "Uploaded on Aug 26, 2017",
            "Uploaded on Aug 26, 2017",
            "Uploaded on Aug 26, 2017"
    } ;

    String[] book_download = {
            "123 Downloads",
            "123 Downloads",
            "123 Downloads",
            "123 Downloads",
            "123 Downloads",
            "123 Downloads",
            "123 Downloads"
    } ;

    // newInstance constructor for creating fragment with arguments
    public static firstfragment newInstance(int page, String title) {
        firstfragment fragmentFirst = new firstfragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }


    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_firstfragment, container, false);

        FragmentList adapter = new
                FragmentList(getActivity(), book_name, imageId , book_upload, book_download);
        list=(ListView) view.findViewById(R.id.list1);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getActivity(), "You Clicked at " +book_name[+ position], Toast.LENGTH_SHORT).show();

            }
        });





        return view;



    }


}
