package net.cosmiclion.opms.login.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by longpham on 9/16/2016.
 */
//public class LoginResponse extends Retrofit2Response {
//
//    public LoginResponse() {
//        super();
//    }
//
//    public LoginResponse(String response) {
//        super(response);
//    }
//
//    public static class LoginDeserializer implements JsonDeserializer<LoginResponse> {
//        private static final String TAG = "LoginDeserializer";
//
//        @Override
//        public LoginResponse deserialize(JsonElement json, Type type, JsonDeserializationContext jdc) {
//            try {
//                Debug.i(TAG, json.toString());
//                String jsonRoot = json.getAsJsonObject().toString();
//                JSONObject jsonRespData = new JSONObject(jsonRoot);
//                LoginResponse loginResponse = new LoginResponse();
//                boolean success = jsonRespData.getBoolean("success");
//                loginResponse.setSuccess(success);
//                String jsonResponse = jsonRespData.getString("response");
//
//                if (success) {
//                    MobileToken mobileToken = (MobileToken) new Gson().fromJson(jsonResponse, MobileToken.class);
//                    String token = mobileToken.getMobileToken() + ":";
//                    token = Base64.encodeToString(token.getBytes("UTF-8"), Base64.NO_WRAP);
//                    mobileToken.setMobileToken(token);
//                    loginResponse.setResponse((MobileToken) mobileToken);
//                    return loginResponse;
//                } else {
//                    return new LoginResponse(jsonResponse);
//                }
//            } catch (JSONException | UnsupportedEncodingException e) {
//                throw new JsonParseException(e);
//            }
//        }
//    }
//
//    public class MobileToken {
//        @SerializedName("member_mobile_token")
//        private String member_mobile_token;
//
//        public String getMobileToken() {
//            return member_mobile_token;
//        }
//
//        public void setMobileToken(String member_mobile_token) {
//            this.member_mobile_token = member_mobile_token;
//        }
//    }
//}


public class LoginResponse {

    @SerializedName("response")
    private Object response;

    @SerializedName("success")
    private boolean success;

    public Object getResponse() {
        return response;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public LoginResponse() {
    }

    public class MobileToken {
        @SerializedName("member_mobile_token")
        private String member_mobile_token;

        public String getMobileToken() {
            return member_mobile_token;
        }

        public void setMobileToken(String member_mobile_token) {
            this.member_mobile_token = member_mobile_token;
        }
    }


}