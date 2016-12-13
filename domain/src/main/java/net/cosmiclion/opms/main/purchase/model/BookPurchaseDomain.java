package net.cosmiclion.opms.main.purchase.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import net.cosmiclion.opms.utils.Debug;

import java.io.File;

import static net.cosmiclion.opms.utils.Constants.APP_PATH;

/**
 * Created by longpham on 10/21/2016.
 */
public class BookPurchaseDomain implements Parcelable {
    public String product_id;

    public String product_title;

    public String product_author;

    public String product_translator;

    public String cover_image1;

    public String cover_image2;

    public String cover_image3;

    public String cover_image4;

    public String is_order;

    public String is_rental;

    public Pivot pivot;

    public void setPivot(String order_id, String product_id) {
        this.pivot = new Pivot(order_id, product_id);
    }

    // add values
    public String filename;
    public int filetype;
    public String company_name;
    public int company_id;
    public String purchase_time;
    public String base_cover_image;

    /** add check file exists */
    public boolean isFileExists(Context ctx) {
        File file = new File(APP_PATH, filename);
        Debug.i("BookDomain", "Epub_file_path_download=" + APP_PATH);
        return file.exists();
    }

    public static class Pivot implements Parcelable {

        public Pivot(String order_id, String product_id) {
            this.order_id = order_id;
            this.product_id = product_id;
        }

        public String order_id;

        public String product_id;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.order_id);
            dest.writeString(this.product_id);
        }

        protected Pivot(Parcel in) {
            this.order_id = in.readString();
            this.product_id = in.readString();
        }

        public static final Creator<Pivot> CREATOR = new Creator<Pivot>() {
            @Override
            public Pivot createFromParcel(Parcel source) {
                return new Pivot(source);
            }

            @Override
            public Pivot[] newArray(int size) {
                return new Pivot[size];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.product_id);
        dest.writeString(this.product_title);
        dest.writeString(this.product_author);
        dest.writeString(this.product_translator);
        dest.writeString(this.cover_image1);
        dest.writeString(this.cover_image2);
        dest.writeString(this.cover_image3);
        dest.writeString(this.cover_image4);
        dest.writeString(this.is_order);
        dest.writeString(this.is_rental);
        dest.writeParcelable(this.pivot, flags);
        dest.writeString(this.filename);
        dest.writeInt(this.filetype);
        dest.writeString(this.company_name);
        dest.writeInt(this.company_id);
        dest.writeString(this.purchase_time);
    }

    public BookPurchaseDomain() {
    }

    protected BookPurchaseDomain(Parcel in) {
        this.product_id = in.readString();
        this.product_title = in.readString();
        this.product_author = in.readString();
        this.product_translator = in.readString();
        this.cover_image1 = in.readString();
        this.cover_image2 = in.readString();
        this.cover_image3 = in.readString();
        this.cover_image4 = in.readString();
        this.is_order = in.readString();
        this.is_rental = in.readString();
        this.pivot = in.readParcelable(Pivot.class.getClassLoader());
        this.filename = in.readString();
        this.filetype = in.readInt();
        this.company_name = in.readString();
        this.company_id = in.readInt();
        this.purchase_time = in.readString();
    }

    public static final Parcelable.Creator<BookPurchaseDomain> CREATOR = new Parcelable.Creator<BookPurchaseDomain>() {
        @Override
        public BookPurchaseDomain createFromParcel(Parcel source) {
            return new BookPurchaseDomain(source);
        }

        @Override
        public BookPurchaseDomain[] newArray(int size) {
            return new BookPurchaseDomain[size];
        }
    };
}
