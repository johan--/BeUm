/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.cosmiclion.opms.login;

import android.support.annotation.NonNull;

import net.cosmiclion.opms.login.model.LoginRequest;
import net.cosmiclion.opms.login.model.ResponseData;
import net.cosmiclion.opms.login.model.UserInfoData;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation to load tasks from the data sources into a cache.
 * <p/>
 * For simplicity, this implements a dumb synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source only if the local database doesn't
 * exist or is empty.
 */
public class TasksRepository implements TasksDataSource {

    private static TasksRepository INSTANCE = null;

    private final TasksDataSource mRemoteDataSource;

    private final TasksDataSource mLocalDataSource;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    boolean mCacheIsDirty = false;

    // Prevent direct instantiation.
    private TasksRepository(@NonNull TasksDataSource tasksRemoteDataSource,
                            @NonNull TasksDataSource tasksLocalDataSource) {
        mRemoteDataSource = checkNotNull(tasksRemoteDataSource);
        mLocalDataSource = checkNotNull(tasksLocalDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param tasksRemoteDataSource the backend data source
     * @param tasksLocalDataSource  the device storage data source
     * @return the {@link TasksRepository} instance
     */
    public static TasksRepository getInstance(TasksDataSource tasksRemoteDataSource,
                                              TasksDataSource tasksLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new TasksRepository(tasksRemoteDataSource, tasksLocalDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(TasksDataSource, TasksDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getLoginResponse(@NonNull LoginRequest loginRequest, @NonNull final LoadLoginCallback callback) {
        checkNotNull(callback);

        if (!mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            mRemoteDataSource.getLoginResponse(loginRequest, new LoadLoginCallback() {
                @Override
                public void onLoginLoaded(ResponseData response) {
//                    Debug.i("TasksRepository", "===getLoginResponse===" +
//                            ((MobileToken)response.getResponse()).getMobileToken());
                    callback.onLoginLoaded(response);
                }

                @Override
                public void onDataNotAvailable(String errorMessage) {
                    callback.onDataNotAvailable(errorMessage);
                }
            });
        } else {
            // Query the local storage if available. If not, query the network.
//            mTasksLocalDataSource.getTasks(new LoadTasksCallback() {
//                @Override
//                public void onTasksLoaded(List<Task> tasks) {
//                    refreshCache(tasks);
//                    callback.onTasksLoaded(new ArrayList<>(mCachedTasks.values()));
//                }
//
//                @Override
//                public void onDataNotAvailable() {
//                    getTasksFromRemoteDataSource(callback);
//                }
//            });
        }
    }

    @Override
    public void getUserInfoResponse(@NonNull String token, @NonNull final LoadUserInfoCallback callback) {
        if (!mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            mRemoteDataSource.getUserInfoResponse(token, new LoadUserInfoCallback() {
                @Override
                public void onUserInfoLoaded(ResponseData response) {
//                    saveUserInfo(new Gson().fromJson((String)response.getResponse(),UserInfoData.class));
                    callback.onUserInfoLoaded(response);
                }

                @Override
                public void onDataNotAvailable(String errorMessage) {
                    callback.onDataNotAvailable(errorMessage);
                }
            });
        } else {
//            mTasksLocalDataSource.getTasks(new LoadTasksCallback() {
//                @Override
//                public void onTasksLoaded(List<Task> tasks) {
//                    refreshCache(tasks);
//                    callback.onTasksLoaded(new ArrayList<>(mCachedTasks.values()));
//                }
//
//                @Override
//                public void onDataNotAvailable() {
//                    getTasksFromRemoteDataSource(callback);
//                }
//            });
        }
    }

    @Override
    public void saveUserInfo(@NonNull UserInfoData userInfoData) {
        mLocalDataSource.saveUserInfo(userInfoData);
    }


    @Override
    public void getBaseImageUrlResponse(@NonNull final LoadBaseImageUrlCallback callback) {
        checkNotNull(callback);

        mRemoteDataSource.getBaseImageUrlResponse(new LoadBaseImageUrlCallback() {

            @Override
            public void onBaseImageUrlLoaded(ResponseData response) {
                callback.onBaseImageUrlLoaded(response);
            }

            @Override
            public void onDataNotAvailable(String errorMessage) {
                callback.onDataNotAvailable(errorMessage);
            }
        });

    }
}
