package net.cosmiclion.opms.main.library.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by longpham on 9/16/2016.
 */
public class BookShelfDomain implements Parcelable {
    public String bs_id;

    public String bs_book_id;

    public String product_id;

    public String product_title;

    public String product_author;

    public String product_translator;

    public String cover_image1;

    public String cover_image2;

    public String cover_image3;

    public String cover_image4;

    public String filename;

    public String filetype;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bs_id);
        dest.writeString(this.bs_book_id);
        dest.writeString(this.product_id);
        dest.writeString(this.product_title);
        dest.writeString(this.product_author);
        dest.writeString(this.product_translator);
        dest.writeString(this.cover_image1);
        dest.writeString(this.cover_image2);
        dest.writeString(this.cover_image3);
        dest.writeString(this.cover_image4);
        dest.writeString(this.filename);
        dest.writeString(this.filetype);
    }

    public BookShelfDomain() {
    }

    protected BookShelfDomain(Parcel in) {
        this.bs_id = in.readString();
        this.bs_book_id = in.readString();
        this.product_id = in.readString();
        this.product_title = in.readString();
        this.product_author = in.readString();
        this.product_translator = in.readString();
        this.cover_image1 = in.readString();
        this.cover_image2 = in.readString();
        this.cover_image3 = in.readString();
        this.cover_image4 = in.readString();
        this.filename = in.readString();
        this.filetype = in.readString();
    }

    public static final Parcelable.Creator<BookShelfDomain> CREATOR = new Parcelable.Creator<BookShelfDomain>() {
        @Override
        public BookShelfDomain createFromParcel(Parcel source) {
            return new BookShelfDomain(source);
        }

        @Override
        public BookShelfDomain[] newArray(int size) {
            return new BookShelfDomain[size];
        }
    };
}
