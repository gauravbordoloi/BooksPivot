package com.gmonetix.bookspivot.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.gmonetix.bookspivot.R;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;

import java.io.File;
import java.io.FileOutputStream;

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
//            File myFile = new File(uri.toString());
//            Log.e("TAG", String.valueOf(myFile.length()));
            generateImageFromPdfUsingFD(uri,"bookfirstpage.png");
            Intent formIntent= new Intent(this,BookDetails.class);
            startActivity(formIntent);
        }
    }

    private void generateImageFromPdfUsingFD(Uri pdfUri, String name) {
        int pageNumber = 0;
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, pdfUri));
        PdfiumCore pdfiumCore = new PdfiumCore(this);
        try {
            ParcelFileDescriptor fd = getContentResolver().openFileDescriptor(pdfUri,"r");
            PdfDocument pdfDocument = pdfiumCore.newDocument(fd);
            pdfiumCore.openPage(pdfDocument, pageNumber);
            int width = pdfiumCore.getPageWidthPoint(pdfDocument, pageNumber);
            int height = pdfiumCore.getPageHeightPoint(pdfDocument, pageNumber);
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            pdfiumCore.renderPageBitmap(pdfDocument, bmp, pageNumber, 0, 0, width, height);
            FileOutputStream out = null;
            try {

                String path = Environment.getExternalStorageDirectory() + "/" + "KhoborKagoj/";
// Create the parent path
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                String fullName = path + name;
                File file = new File (fullName);
//                File file = new File(Environment.getExternalStorageDirectory() + "/" + "KhoborKagoj", name);
                out = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 15, out); // bmp is your Bitmap instance
            } catch (Exception e) {
                Log.e("TAG catch1","" + e.getMessage());
            } finally {
                try {
                    if (out != null)
                        out.close();
                } catch (Exception e) {
//todo with exception
                    Log.e("TAG Catch2","" + e.getMessage());
                }
            }
            pdfiumCore.closeDocument(pdfDocument); // important!
        } catch(Exception e) {
//todo with exception
            Log.e("TAG catch 3","" + e.getMessage());
        }
    }

}
