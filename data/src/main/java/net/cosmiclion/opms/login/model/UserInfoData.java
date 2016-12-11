package net.cosmiclion.opms.login.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by longpham on 12/10/2016.
 */

public class UserInfoData {
    @SerializedName("member_id")
    public String member_id;

    @SerializedName("member_account")
    public String member_account;

    @SerializedName("member_truename")
    public String member_truename;

    @SerializedName("member_sex")
    public String member_sex;

    @SerializedName("member_birthday")
    public String member_birthday;

    @SerializedName("member_mobilephone")
    public String member_mobilephone;

    @SerializedName("allow_email_inform")
    public String allow_email_inform;

    @SerializedName("allow_sms_inform")
    public String allow_sms_inform;

    @SerializedName("allow_app_inform")
    public String allow_app_inform;

    @SerializedName("member_addtime")
    public String member_addtime;

    @SerializedName("member_login_time")
    public String member_login_time;

    @SerializedName("member_old_login_time")
    public String member_old_login_time;

    @SerializedName("adult_check")
    public String adult_check;

    @SerializedName("member_login_num")
    public String member_login_num;

    @SerializedName("member_login_ip")
    public String member_login_ip;

    @SerializedName("member_old_login_ip")
    public String member_old_login_ip;

    @SerializedName("member_state")
    public String member_state;

    @SerializedName("member_mobile_token")
    public String member_mobile_token;

    @SerializedName("facebook_sync")
    public String facebook_sync;
}
