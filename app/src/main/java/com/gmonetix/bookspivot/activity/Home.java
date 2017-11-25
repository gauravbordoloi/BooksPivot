package com.gmonetix.bookspivot.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.mertakdut.BookSection;
import com.github.mertakdut.Reader;
import com.github.mertakdut.exception.OutOfPagesException;
import com.github.mertakdut.exception.ReadingException;
import com.gmonetix.bookspivot.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubReader;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*PDFView pdfView = (PDFView) findViewById(R.id.home_pdfView);
        pdfView.fromAsset("sbi.pdf").load();*/

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_settings:
                //startActivity(new Intent(Home.this,SettingsActivity.class));
                /*try{
                    Reader reader = new Reader(); // Max string length for the current page.
                    reader.setIsIncludingTextContent(true); // Optional, to return the tags-excluded version.
                    reader.setFullContent("/sdcard/ebook.epub"); // Must call before readSection.

                    BookSection bookSection = reader.readSection(5);
                    String sectionContent = bookSection.getSectionContent(); // Returns content as html.
                    String sectionTextContent = bookSection.getSectionTextContent(); // Excludes html tags.
                    Log.e("TAG",sectionContent);
                    Log.e("TAG",sectionTextContent);
                    WebView webView = (WebView) findViewById(R.id.nav_website);
                    webView.loadData(sectionContent,"text/html","UTF-8");
                } catch (Exception e) {
                    Log.e("TAG","error occurred");
                }*/
                AssetManager assetManager = getAssets();
                try {
                    // find InputStream for book
                    InputStream epubInputStream = assetManager
                            .open("ebook.epub");

                    // Load Book from inputStream
                    Book book = (new EpubReader()).readEpub(epubInputStream);

                    // Log the book's authors
                    Log.e("epublib", "author(s): " + book.getMetadata().getAuthors());

                    // Log the book's title
                    Log.e("epublib", "title: " + book.getTitle());

                    // Log the book's coverimage property
                    Bitmap coverImage = BitmapFactory.decodeStream(book.getCoverImage()
                            .getInputStream());
                    Log.e("epublib", "Coverimage is " + coverImage.getWidth() + " by "
                            + coverImage.getHeight() + " pixels");

                    // Log the tale of contents
                    logTableOfContents(book.getTableOfContents().getTocReferences(), 0);
                } catch (IOException e) {
                    Log.e("epublib", e.getMessage());
                }
                break;
            case R.id.nav_upload:
                Intent intent = new Intent(Home.this,UploadActivity.class);
                startActivity(intent);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logTableOfContents(List<TOCReference> tocReferences, int depth) {
        if (tocReferences == null) {
            return;
        }
        for (TOCReference tocReference : tocReferences) {
            StringBuilder tocString = new StringBuilder();
            for (int i = 0; i < depth; i++) {
                tocString.append("\t");
            }
            tocString.append(tocReference.getTitle());
            Log.e("epublib", tocString.toString());

            logTableOfContents(tocReference.getChildren(), depth + 1);
        }
    }

    public File getFileFromAssets(String fileName) {

        File file = new File(getCacheDir() + "/" + fileName);

        if (!file.exists()) try {

            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(buffer);
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return file;
    }

}
