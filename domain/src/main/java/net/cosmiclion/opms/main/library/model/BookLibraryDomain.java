package net.cosmiclion.opms.main.library.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by longpham on 9/16/2016.
 */
public class BookLibraryDomain implements Parcelable {
    public String bs_id;

    public String member_id;

    public String bs_name;

    public String bs_order;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bs_id);
        dest.writeString(this.member_id);
        dest.writeString(this.bs_name);
        dest.writeString(this.bs_order);
    }

    public BookLibraryDomain() {
    }

    protected BookLibraryDomain(Parcel in) {
        this.bs_id = in.readString();
        this.member_id = in.readString();
        this.bs_name = in.readString();
        this.bs_order = in.readString();
    }

    public static final Parcelable.Creator<BookLibraryDomain> CREATOR = new Parcelable.Creator<BookLibraryDomain>() {
        @Override
        public BookLibraryDomain createFromParcel(Parcel source) {
            return new BookLibraryDomain(source);
        }

        @Override
        public BookLibraryDomain[] newArray(int size) {
            return new BookLibraryDomain[size];
        }
    };
}
