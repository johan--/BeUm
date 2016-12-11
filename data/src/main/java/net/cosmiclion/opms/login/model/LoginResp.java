package net.cosmiclion.opms.login.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import net.cosmiclion.opms.network.retrofit2.CustomizedTypeAdapterFactory;

/**
 * Created by longpham on 12/9/2016.
 */

public class LoginResp extends CustomizedTypeAdapterFactory<Retrofit2Response> {

    public LoginResp() {
        super(Retrofit2Response.class);
    }

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

    @Override
    protected void beforeWrite(Retrofit2Response source, JsonElement toSerialize) {
        JsonObject custom = toSerialize.getAsJsonObject().get("custom").getAsJsonObject();
        custom.add("size", new JsonPrimitive(custom.entrySet().size()));
    }

    @Override
    protected void afterRead(JsonElement deserialized) {
        JsonObject custom = deserialized.getAsJsonObject().get("custom").getAsJsonObject();
        custom.remove("size");
    }
}
