package net.cosmiclion.opms.main.notices.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by longpham on 10/21/2016.
 */
public class NoticeDomain implements Parcelable {
    public String board_id;

    public String board_type;

    public String board_category;

    public String member_id;

    public String board_title;

    public String board_content;

    public String board_read_count;

    public String board_date;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.board_id);
        dest.writeString(this.board_type);
        dest.writeString(this.board_category);
        dest.writeString(this.member_id);
        dest.writeString(this.board_title);
        dest.writeString(this.board_content);
        dest.writeString(this.board_read_count);
        dest.writeString(this.board_date);
    }

    public NoticeDomain() {
    }

    protected NoticeDomain(Parcel in) {
        this.board_id = in.readString();
        this.board_type = in.readString();
        this.board_category = in.readString();
        this.member_id = in.readString();
        this.board_title = in.readString();
        this.board_content = in.readString();
        this.board_read_count = in.readString();
        this.board_date = in.readString();
    }

    public static final Parcelable.Creator<NoticeDomain> CREATOR = new Parcelable.Creator<NoticeDomain>() {
        @Override
        public NoticeDomain createFromParcel(Parcel source) {
            return new NoticeDomain(source);
        }

        @Override
        public NoticeDomain[] newArray(int size) {
            return new NoticeDomain[size];
        }
    };
}
