package com.example.booklistingapp;

// Book class
public class Book {
    private String mTitle;
    private String mSubtitle;
    private String mImageUrl;
    private String mWebReaderLink;
    private String mDesc;
    private String mTextInfo;

    public Book(String title, String subtitle, String imageUrl, String webReaderLink, String desc,String textInfo) {
        this.mTitle = title;
        this.mSubtitle = subtitle;
        this.mImageUrl = imageUrl;
        this.mWebReaderLink = webReaderLink;
        this.mDesc = desc;
        this.mTextInfo = textInfo;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getWebReaderLink() {
        return mWebReaderLink;
    }

    public String getDesc() {
        return mDesc;
    }
    public String getTextInfo() {
        return mTextInfo;
    }
}
