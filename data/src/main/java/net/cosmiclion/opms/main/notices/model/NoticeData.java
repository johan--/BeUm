package net.cosmiclion.opms.main.notices.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by longpham on 12/10/2016.
 */

public class NoticeData {

    @SerializedName("board_id")
    public String board_id;

    @SerializedName("board_type")
    public String board_type;

    @SerializedName("board_category")
    public String board_category;

    @SerializedName("member_id")
    public String member_id;

    @SerializedName("board_title")
    public String board_title;

    @SerializedName("board_content")
    public String board_content;

    @SerializedName("board_read_count")
    public String board_read_count;

    @SerializedName("board_date")
    public String board_date;

}
