package com.gmonetix.bookspivot.model;

/**
 * Created by gaura on 9/4/2017.
 */

public class Book {

    private String mAuthorName;
    private String mBook;
    private String mVersion;

    private String url;

    public void setmAuthorName(String author){
        this.mAuthorName=author;

    }
    public void setmBook(String bookname){
        this.mBook=bookname;
    }
    public void setmVersion(String version){
        this.mVersion=version;
    }

    public void setUrl(String url){
        this.url=url;
    }
    public String getUrl(){
        return this.url;
    }
    public String getmAuthorName() {
        return mAuthorName;
    }

    public String getmBook() {
        return mBook;
    }

    public String getmVersion() {
        return mVersion;
    }
}
