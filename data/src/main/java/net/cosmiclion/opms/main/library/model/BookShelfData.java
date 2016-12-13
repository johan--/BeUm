package net.cosmiclion.opms.main.library.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by longpham on 10/26/2016.
 */
public class BookShelfData {
    @SerializedName("bs_id")
    public String bs_id;

    @SerializedName("bs_book_id")
    public String bs_book_id;

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

    @SerializedName("filename")
    public String filename;

    @SerializedName("filetype")
    public String filetype;

}
