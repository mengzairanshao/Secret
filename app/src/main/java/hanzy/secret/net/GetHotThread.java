package hanzy.secret.net;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hanzy.secret.Message.CatalogMessage;
import hanzy.secret.Message.HotThreadMessage;
import hanzy.secret.secret.Config;

/**
 * Created by h on 2016/7/9.
 */
public class GetHotThread {

    public String TAG = "GetHotThread";

    public GetHotThread(final Context context, final SuccessCallback successCallback, final FailCallback failCallback) {

        new NetConnection(context, Config.Base_URL, HttpMethod.GET, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (!jsonObject.getJSONObject("Variables").getString("auth").equals("null")) {

                        Log.e(TAG, "Get Json Data:" + result);
                        JSONArray jsonArray = jsonObject.getJSONObject("Variables").getJSONArray("data");
                        List<HotThreadMessage> megs = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            megs.add(new HotThreadMessage(jsonObject.getString("author"),
                                    jsonObject.getString("dbdateline"),
                                    jsonObject.getString("dblastpost"),
                                    jsonObject.getString("fid"),
                                    jsonObject.getString("replies"),
                                    jsonObject.getString("subject"),
                                    jsonObject.getString("tid"),
                                    jsonObject.getString("views")));
                        }

                        if (successCallback != null) successCallback.onSuccess(megs);
                    } else {
                        Log.e(TAG,"Failed Get Json Data(auth==null)");
                        if (failCallback != null) failCallback.onFail();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (failCallback != null) failCallback.onFail();
                }

            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                if (failCallback != null) failCallback.onFail();
            }
        }, "version","4","module","hotthread");

    }

    public static interface SuccessCallback {
        void onSuccess(List<HotThreadMessage> hotThreadMessages);
    }

    public static interface FailCallback {
        void onFail();
    }
}
