package com.mg.kode.kodebrowser.data.model;


public class NewsArticle {
	
    private String _id;
    private String title;
    private String source;
    private String thumnailLink;
    private String date;
    private ArticleCategory articleCategory;

    public NewsArticle(String _id, String title, String source, String thumnailLink, String date, ArticleCategory articleCategory) {
        this._id = _id;
        this.title = title;
        this.source = source;
        this.thumnailLink = thumnailLink;
        this.date = date;
        this.articleCategory = articleCategory;
    }

    public String get_id() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public String getSource() {
        return source;
    }

    public String getThumnailLink() {
        return thumnailLink;
    }

    public String getDate() {
        return date;
    }
}
