package com.mg.kode.kodebrowser.data.model;

import android.os.Parcel;
import android.os.Parcelable;


public class ArticleCategory implements Parcelable {

    private String _id;
    private String slug;
    private String title;

    public ArticleCategory(String _id, String slug, String title) {
        this._id = _id;
        this.slug = slug;
        this.title = title;
    }

    public String get_id() {
        return _id;
    }

    public String getSlug() {
        return slug;
    }

    public String getTitle() {
        return title;
    }

    protected ArticleCategory(Parcel in) {
        _id = in.readString();
        slug = in.readString();
        title = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(slug);
        dest.writeString(title);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ArticleCategory> CREATOR = new Parcelable.Creator<ArticleCategory>() {
        @Override
        public ArticleCategory createFromParcel(Parcel in) {
            return new ArticleCategory(in);
        }

        @Override
        public ArticleCategory[] newArray(int size) {
            return new ArticleCategory[size];
        }

    };
}