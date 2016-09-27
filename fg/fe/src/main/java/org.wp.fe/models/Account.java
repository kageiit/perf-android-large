package org.wp.fe.models;

import com.android.volley.VolleyError;
import com.wordpress.rest.RestRequest;
import de.greenrobot.event.EventBus;
import org.json.JSONObject;
import org.wp.fe.datasets.AccountTable;
import org.wp.fe.datasets.ReaderUserTable;
import org.wp.fe.ui.prefs.PrefsEvents;
import org.wp.fe.util.AppLog;
import org.wp.fe.util.AppLog.T;

import java.util.Map;

/**
 * Class for managing logged in user informations.
 */
public class Account extends AccountModel {
    public void fetchAccountDetails() {
        if (!hasAccessToken()) {
            AppLog.e(T.API, "User is not logged in with WordPress.com, ignoring the fetch account details request");
            return;
        }
        com.wordpress.rest.RestRequest.Listener listener = new RestRequest.Listener() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (jsonObject != null) {
                    updateFromRestResponse(jsonObject);
                    save();

                    ReaderUserTable.addOrUpdateUser(ReaderUser.fromJson(jsonObject));
                }
            }
        };

        RestRequest.ErrorListener errorListener = new RestRequest.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                AppLog.e(T.API, volleyError);
            }
        };

//        WordPress.getRestClientUtilsV1_1().get("me", listener, errorListener);
    }

    public void fetchAccountSettings() {
        if (!hasAccessToken()) {
            AppLog.e(T.API, "User is not logged in with WordPress.com, ignoring the fetch account settings request");
            return;
        }
        com.wordpress.rest.RestRequest.Listener listener = new RestRequest.Listener() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (jsonObject != null) {
                    updateAccountSettingsFromRestResponse(jsonObject);
                    save();
                    EventBus.getDefault().post(new PrefsEvents.AccountSettingsFetchSuccess());
                }
            }
        };

        RestRequest.ErrorListener errorListener = new RestRequest.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                AppLog.e(T.API, volleyError);
                EventBus.getDefault().post(new PrefsEvents.AccountSettingsFetchError(volleyError));
            }
        };

//        WordPress.getRestClientUtilsV1_1().get("me/settings", listener, errorListener);
    }

    public void postAccountSettings(Map<String, String> params) {
        if (!hasAccessToken()) {
            AppLog.e(T.API, "User is not logged in with WordPress.com, ignoring the post account settings request");
            return;
        }
        com.wordpress.rest.RestRequest.Listener listener = new RestRequest.Listener() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (jsonObject != null) {
                    updateAccountSettingsFromRestResponse(jsonObject);
                    save();
                    EventBus.getDefault().post(new PrefsEvents.AccountSettingsPostSuccess());
                }
            }
        };

        RestRequest.ErrorListener errorListener = new RestRequest.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                AppLog.e(T.API, volleyError);
                EventBus.getDefault().post(new PrefsEvents.AccountSettingsPostError(volleyError));
            }
        };

//        WordPress.getRestClientUtilsV1_1().post("me/settings", params, null, listener, errorListener);
    }

    public void signout() {
        init();
        save();
    }

    public void save() {
        AccountTable.save(this);
    }
}
