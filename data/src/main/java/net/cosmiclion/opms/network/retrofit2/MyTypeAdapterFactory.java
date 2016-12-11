//package net.cosmiclion.opms.network.retrofit2;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.TypeAdapter;
//import com.google.gson.TypeAdapterFactory;
//import com.google.gson.reflect.TypeToken;
//import com.google.gson.stream.JsonReader;
//import com.google.gson.stream.JsonWriter;
//
//import net.cosmiclion.opms.login.model.MobileToken;
//import net.cosmiclion.opms.login.model.ResultResponse;
//import net.cosmiclion.opms.login.model.UserInfoData;
//import net.cosmiclion.opms.login.model.UserInfoResponse;
//
//import java.io.IOException;
//
///**
// * Created by longpham on 12/10/2016.
// */
//
//public class MyTypeAdapterFactory implements TypeAdapterFactory {
//
//    @Override
//    @SuppressWarnings("unchecked")
//    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
//        if (!ResultResponse.class.isAssignableFrom(type.getRawType())) {
//            return null;
//        }
//
//        TypeAdapter<JsonElement> jsonElementAdapter = gson.getAdapter(JsonElement.class);
//        TypeAdapter<MobileToken> mobileTokenTypeAdapter =
//                gson.getDelegateAdapter(this, TypeToken.get(MobileToken.class));
//        TypeAdapter<UserInfoData> userInfoTokenTypeAdapter =
//                gson.getDelegateAdapter(this, TypeToken.get(UserInfoData.class));
//
//        return (TypeAdapter<T>) new NewsFeedItemTypeAdapter(
//                jsonElementAdapter, mobileTokenTypeAdapter, userInfoTokenTypeAdapter).nullSafe();
//    }
//
//    private static class NewsFeedItemTypeAdapter extends TypeAdapter<ResultResponse> {
//
//        private final TypeAdapter<JsonElement> jsonElementAdapter;
//        private final TypeAdapter<MobileToken> mMobileTokenAdapter;
//        private final TypeAdapter<UserInfoData> mUserInfoAdapter;
//
//        NewsFeedItemTypeAdapter(TypeAdapter<JsonElement> jsonElementAdapter,
//                                TypeAdapter<MobileToken> mobileTokenAdapter,
//                                TypeAdapter<UserInfoData> userInfoAdapter) {
//            this.jsonElementAdapter = jsonElementAdapter;
//            this.mMobileTokenAdapter = mobileTokenAdapter;
//            this.mUserInfoAdapter = userInfoAdapter;
//        }
//
//        @Override
//        public void write(JsonWriter out, ResultResponse value) throws IOException {
//            if (value.getClass().isAssignableFrom(MobileToken.class)) {
//                mMobileTokenAdapter.write(out, (MobileToken) value);
//
//            } else if (value.getClass().isAssignableFrom(UserInfoData.class)) {
//                mUserInfoAdapter.write(out, (UserInfoData) value);
//
//            }
//
//        }
//
//        @Override
//        public ResultResponse read(JsonReader in) throws IOException {
//            JsonObject objectJson = jsonElementAdapter.read(in).getAsJsonObject();
//
//            if (objectJson.has("Title")) {
//                return mMobileTokenAdapter.fromJsonTree(objectJson);
//
//            } else if (objectJson.has("CampaignName")) {
//                return mUserInfoAdapter.fromJsonTree(objectJson);
//            }
//
//            return null;
//        }
//    }
//}