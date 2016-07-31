package hanzy.secret.net;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import hanzy.secret.secret.Config;

/**
 * Created by h on 2016/7/27.
 */
public class Logout {

    private String TAG ="Logout";
    public Logout(final Context context, final SuccessCallback successCallback, FailCallback failCallback) {
        new NetConnection(context, Config.BASE_URL, HttpMethod.GET, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    Log.e(TAG,""+jsonObject.toString());
                    if (jsonObject.getJSONObject("Message").getString("messageval").equals("logout_succeed")){
                        CookiesSet.Utils.clearCookies(context);
                        Config.cacheDATA(context,"Logout_succeed",Config.IS_LOGIN);
                        if (successCallback!=null){
                            successCallback.onSuccess(result);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {

            }
        },Config.KEY_MODULE,"login",Config.KEY_ACTION,"logout",Config.KEY_VERSION,"4");
    }

    public static interface SuccessCallback {
        void onSuccess(String result);
    }

    public static interface FailCallback {
        void onFail();
    }
}
