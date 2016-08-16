package hanzy.secret.net;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import hanzy.secret.secret.Config;
import hanzy.secret.utils.logUtils;

/**
 * Created by h on 2016/6/30.
 *
 */
public class GetProfile {
    public String TAG="GetProfile";

    public GetProfile(final Context context) {
        new NetConnection(context, Config.BASE_URL, HttpMethod.GET, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                if (result!=null) {
                    try {
                        JSONObject obj = new JSONObject(result);
                        logUtils.log(context, TAG, "获取JSON数据:" + obj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                logUtils.log(context, TAG, "获取JSON数据失败");
            }
        },"version","4","module","profile");
    }

    public interface SuccessCallback {
        void onSuccess(String token);
    }

    public interface FailCallback {
        void onFail();
    }
}
