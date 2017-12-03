package com.gmonetix.bookspivot.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.gmonetix.bookspivot.R;
import java.io.File;

public class UploadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
    }

    public void onBrowse(View view) {
        Intent chooseFile;
        Intent intent;
        chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
        chooseFile.setType("*/*");
        String[] mimetypes = {"application/pdf", "application/epub"};
        chooseFile.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
        intent = Intent.createChooser(chooseFile, "Choose a file");
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        if(requestCode == 1)
        {
            Uri uri = data.getData();
            Log.e("TAG",uri.toString());
            File myFile = new File(uri.toString());
            Log.e("TAG", String.valueOf(myFile.length()));

            Intent formIntent= new Intent(this,BookDetails.class);
            startActivity(formIntent);
        }
    }

}
