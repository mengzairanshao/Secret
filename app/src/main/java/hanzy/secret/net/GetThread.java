package hanzy.secret.net;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import hanzy.secret.Adapter.ThreadAdapter;
import hanzy.secret.Message.CatalogMessage;
import hanzy.secret.Message.ThreadsMessage;
import hanzy.secret.secret.Config;

/**
 * Created by h on 2016/6/30.
 */
public class GetThread {
    public String TAG = "GetThread";

    public GetThread(final Context context, final SuccessCallback successCallback, final FailCallback failCallback) {

        new NetConnection(context, Config.Base_URL, HttpMethod.GET, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (!jsonObject.getJSONObject("Variables").getString("auth").equals("null")) {
                        Log.e(TAG, "Get Json Data:" + jsonObject.toString());
                        JSONArray jsonArray = new JSONArray();
                        jsonArray = jsonObject.getJSONObject("Variables").getJSONArray("forum_threadlist");
                        JSONObject jsonObject1 = new JSONObject();
                        List<ThreadsMessage> megs = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject1 = jsonArray.getJSONObject(i);
                            Log.e(TAG,"ThreadMessage"+jsonObject1.getString("author")+jsonObject1.getString("dblastpost")+jsonObject1.getString("subject")+jsonObject1.getString("views")+jsonObject1.getString("replies"));
                            megs.add(new ThreadsMessage(jsonObject1.getString("author"),jsonObject1.getString("dblastpost"),jsonObject1.getString("subject"),jsonObject1.getString("views"),jsonObject1.getString("replies"),jsonObject1.getString("tid")));
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
        }, "version","4","module","forumdisplay","fid","2","page","1");

    }

    public static interface SuccessCallback {
        void onSuccess(List<ThreadsMessage> threadsMessages);
    }

    public static interface FailCallback {
        void onFail();
    }
}
