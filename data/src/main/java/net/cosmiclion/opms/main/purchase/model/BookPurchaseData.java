package net.cosmiclion.opms.main.purchase.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by longpham on 12/10/2016.
 */

public class BookPurchaseData {

    @SerializedName("product_id")
    public String product_id;

    @SerializedName("product_title")
    public String product_title;

    @SerializedName("product_author")
    public String product_author;

    @SerializedName("product_translator")
    public String product_translator;

    @SerializedName("cover_image1")
    public String cover_image1;

    @SerializedName("cover_image2")
    public String cover_image2;

    @SerializedName("cover_image3")
    public String cover_image3;

    @SerializedName("cover_image4")
    public String cover_image4;

    @SerializedName("is_order")
    public String is_order;

    @SerializedName("is_rental")
    public String is_rental;

    @SerializedName("pivot")
    public Pivot pivot;

    public class Pivot{
        @SerializedName("order_id")
        public String order_id;

        @SerializedName("product_id")
        public String product_id;
    }
}
