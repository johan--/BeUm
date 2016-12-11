package net.cosmiclion.opms.main.notices.source.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import net.cosmiclion.opms.login.model.ResponseData;
import net.cosmiclion.opms.main.notices.service.NoticesService;
import net.cosmiclion.opms.main.notices.source.NoticesDataSource;
import net.cosmiclion.opms.network.retrofit2.ApiClient;
import net.cosmiclion.opms.utils.Debug;

import retrofit2.Call;
import retrofit2.Callback;

import static com.google.common.base.Preconditions.checkNotNull;

public class NoticesRemoteDataSource implements NoticesDataSource {
    private String TAG = getClass().getSimpleName();
    private static NoticesRemoteDataSource INSTANCE = null;
    private Context mContext;

    public static NoticesRemoteDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new NoticesRemoteDataSource(context);
        }
        return INSTANCE;
    }

    private NoticesRemoteDataSource(@NonNull Context context) {
        this.mContext = checkNotNull(context);
    }

    @Override
    public void getNoticesResponse(@NonNull final LoadNoticesCallback callback) {
        Debug.i(TAG, "===getNoticesResponse===");

        NoticesService service = ApiClient.getClient(mContext).create(NoticesService.class);

        final Call<ResponseData> call = service.getNotices();

        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call,
                                   retrofit2.Response<ResponseData> response) {
                callback.onNoticesLoaded(response.body());
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Debug.i(TAG, "Something went wrong: " + t.getMessage());
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }
}
