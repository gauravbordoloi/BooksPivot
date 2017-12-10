package com.gmonetix.bookspivot.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gmonetix.bookspivot.R;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener{
     final String uploadID = UUID.randomUUID().toString();
    public static final String UPLOAD_URL = "http://xxyyzz.com/mmff/upload.php";//Upload server !!
    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    //Pdf request code
    private int PICK_PDF_REQUEST = 1;
    private Uri filePath;
    Button buttonChoose;
    Button buttonUpload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        //Initializing views

       buttonChoose=(Button)findViewById(R.id.buttonchoose);
       buttonUpload=(Button)findViewById(R.id.buttonupload);



        //Setting clicklistener
        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);

    }
    public void uploadMultipart() {


        //getting the actual path of the image
        String path = FilePath.getPath(this, filePath);
        new MultipartUploadRequest(this, uploadID, UPLOAD_URL)
                .addFileToUpload(path, "pdf") //Adding file
                .addParameter("name", name) //Adding text parameter to the request
                .setNotificationConfig(new UploadNotificationConfig())
                .setMaxRetries(2)
                .startUpload();

        //Uploading code
        if (path == null) {

            Toast.makeText(this, "Please move your .pdf file to internal storage and retry", Toast.LENGTH_LONG).show();
        } else {
            try {
                String uploadId = UUID.randomUUID().toString();

                //Starting the upload

            } catch (Exception exc) {
                Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void showFileChooser() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "choose a file"), PICK_PDF_REQUEST);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
        }
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if(requestCode == 1)
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }


    {
        Uri uri = data.getData();
        Log.e("TAG", uri.toString());
//            File myFile = new File(uri.toString());
//            Log.e("TAG", String.valueOf(myFile.length()));
        generateImageFromPdfUsingFD(uri, "bookfirstpage.png");
        Intent formIntent = new Intent(this, BookDetails.class);
        startActivity(formIntent);
    }
}}

    private void generateImageFromPdfUsingFD(Uri pdfUri, String name) {
        int pageNumber = 0;
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, pdfUri));
        PdfiumCore pdfiumCore = new PdfiumCore(this);
        try {
            ParcelFileDescriptor fd = getContentResolver().openFileDescriptor(pdfUri, "r");
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
                File file = new File(fullName);
//                File file = new File(Environment.getExternalStorageDirectory() + "/" + "KhoborKagoj", name);
                out = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 15, out); // bmp is your Bitmap instance
            } catch (Exception e) {
                Log.e("TAG catch1", "" + e.getMessage());
            } finally {
                try {
                    if (out != null)
                        out.close();
                } catch (Exception e) {
//todo with exception
                    Log.e("TAG Catch2", "" + e.getMessage());
                }
            }
            pdfiumCore.closeDocument(pdfDocument); // important!
        } catch (Exception e) {
//todo with exception
            Log.e("TAG catch 3", "" + e.getMessage());
        }
    }


    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
            showFileChooser();
        }
        if (v == buttonUpload) {
            uploadMultipart();
        }
    }
}