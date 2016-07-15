package hanzy.secret.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import hanzy.secret.Message.ThreadsMessage;
import hanzy.secret.secret.Config;
import hanzy.secret.utils.PicUtils;

/**
 * Created by h on 2016/6/30.
 */
public class GetThread {
    public String TAG = "GetThread";
    private Bitmap bitmap=null;
    public GetThread(final Context context, final SuccessCallback successCallback, final FailCallback failCallback,String fid) {

        new NetConnection(context, Config.Base_URL, HttpMethod.GET, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (!jsonObject.getJSONObject("Variables").getString("auth").equals("null")) {
                        Log.e(TAG, "Get Json Data:" + jsonObject.toString());
                        JSONArray jsonArray;
                        jsonArray = jsonObject.getJSONObject("Variables").getJSONArray("forum_threadlist");
                        JSONObject jsonObject1;
                        List<ThreadsMessage> megs = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject1 = jsonArray.getJSONObject(i);

                            GetPic getPic=new GetPic(context, jsonObject1.getString("authorid"), "small", new GetPic.SuccessCallback() {
                                @Override
                                public void onSuccess(String result) {
                                    bitmap= PicUtils.convertStringToIcon(result);
                                }
                            }, new GetPic.FailCallback() {
                                @Override
                                public void onFail() {

                                }
                            });
                            Log.e(TAG,"ThreadMessage"+jsonObject1.getString("author")+jsonObject1.getString("dblastpost")+jsonObject1.getString("subject")+jsonObject1.getString("views")+jsonObject1.getString("replies"));
                            megs.add(new ThreadsMessage(jsonObject1.getString("author"),
                                    jsonObject1.getString("dblastpost"),
                                    jsonObject1.getString("subject"),
                                    jsonObject1.getString("views"),
                                    jsonObject1.getString("replies"),
                                    jsonObject1.getString("tid"),
                                    jsonObject1.getString("authorid"),
                                    bitmap));
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
        }, "version","4","module","forumdisplay","fid",fid,"page","1");

    }

    public static interface SuccessCallback {
        void onSuccess(List<ThreadsMessage> threadsMessages);
    }

    public static interface FailCallback {
        void onFail();
    }
}
