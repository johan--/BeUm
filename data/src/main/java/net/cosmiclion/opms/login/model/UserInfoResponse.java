package net.cosmiclion.opms.login.model;

/**
 * Created by longpham on 12/9/2016.
 */

public class UserInfoResponse extends Retrofit2Response {
//    public UserInfoResponse() {
//        super();
//    }
//
//    public UserInfoResponse(String response) {
//        super(response);
//    }
//
//    public static class UserInfoDeserializer implements JsonDeserializer<UserInfoResponse> {
//        private static final String TAG = "UserInfoDeserializer";
//
//        @Override
//        public UserInfoResponse deserialize(JsonElement json, Type type, JsonDeserializationContext jdc) {
//            try {
//                Debug.i(TAG, json.toString());
//                String jsonRoot = json.getAsJsonObject().toString();
//                JSONObject jsonRespData = new JSONObject(jsonRoot);
//                UserInfoResponse userInfo = new UserInfoResponse();
//                boolean success = jsonRespData.getBoolean("success");
//                userInfo.setSuccess(success);
//                String jsonResponse = jsonRespData.getString("response");
//                Debug.i(TAG, jsonResponse);
//                if (success) {
//                    Info info = (Info) new Gson().fromJson(jsonResponse, UserInfoResponse.Info.class);
//                    Debug.i(TAG, info.toString());
//                    userInfo.setResponse((UserInfoResponse.Info) info);
//                    return userInfo;
//                } else {
//                    return new UserInfoResponse(jsonResponse);
//                }
//            } catch (JSONException e) {
//                throw new JsonParseException(e);
//            }
//        }
//    }
//
//    public class Info {
//        @SerializedName("member_id")
//        public String member_id;
//
//        @SerializedName("member_account")
//        public String member_account;
//
//        @SerializedName("member_truename")
//        public String member_truename;
//
//        @SerializedName("member_sex")
//        public String member_sex;
//
//        @SerializedName("member_birthday")
//        public String member_birthday;
//
//        @SerializedName("member_mobilephone")
//        public String member_mobilephone;
//
//        @SerializedName("allow_email_inform")
//        public String allow_email_inform;
//
//        @SerializedName("allow_sms_inform")
//        public String allow_sms_inform;
//
//        @SerializedName("allow_app_inform")
//        public String allow_app_inform;
//
//        @SerializedName("member_addtime")
//        public String member_addtime;
//
//        @SerializedName("member_login_time")
//        public String member_login_time;
//
//        @SerializedName("member_old_login_time")
//        public String member_old_login_time;
//
//        @SerializedName("adult_check")
//        public String adult_check;
//
//        @SerializedName("member_login_num")
//        public String member_login_num;
//
//        @SerializedName("member_login_ip")
//        public String member_login_ip;
//
//        @SerializedName("member_old_login_ip")
//        public String member_old_login_ip;
//
//        @SerializedName("member_state")
//        public String member_state;
//
//        @SerializedName("member_mobile_token")
//        public String member_mobile_token;
//
//
//    }
}
