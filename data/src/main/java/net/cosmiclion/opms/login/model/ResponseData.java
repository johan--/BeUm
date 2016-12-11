package net.cosmiclion.opms.login.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import net.cosmiclion.opms.utils.Debug;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by longpham on 12/9/2016.
 */

public class ResponseData {
    @SerializedName("response")
    private Object response;

    @SerializedName("success")
    private boolean success;

    public ResponseData() {
    }

    public ResponseData(String response) {
        this.response = response;
    }

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

    public static class Deserializer implements JsonDeserializer<ResponseData> {
        private static final String TAG = "ResponseData-Deserializer";

        @Override
        public ResponseData deserialize(JsonElement json, Type type, JsonDeserializationContext jdc) {
            try {
                Debug.i(TAG, json.toString());
                String jsonRoot = json.getAsJsonObject().toString();
                JSONObject jsonRespData = new JSONObject(jsonRoot);
                ResponseData loginResponse = new ResponseData();
                boolean success = jsonRespData.getBoolean("success");
                loginResponse.setSuccess(success);
                String jsonResponse = jsonRespData.getString("response");
                loginResponse.setResponse((String) jsonResponse);
                return loginResponse;
            } catch (JSONException e) {
                throw new JsonParseException(e);
            }
        }
    }
}
