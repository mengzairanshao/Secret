package hanzy.secret.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.nfc.Tag;
import android.util.Log;
import android.util.LongSparseArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hanzy.secret.Message.DetailMessage;
import hanzy.secret.Message.ThreadsMessage;
import hanzy.secret.secret.Config;
import hanzy.secret.utils.PicUtils;
import hanzy.secret.utils.TimeUtils;

/**
 * Created by h on 2016/7/7.
 */
public class GetDetail {

    public String TAG = "GetDetail";
    private int j;
    private List<DetailMessage> megs = new ArrayList<>();
    //private String[][] image;
    private Bitmap bitmap;
    private JSONObject jsonObject1 = new JSONObject();

    public GetDetail(final Context context, final SuccessCallback successCallback, final FailCallback failCallback, final String tid) {

        new NetConnection(context, Config.Base_URL, HttpMethod.GET, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(final String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (!jsonObject.getJSONObject("Variables").getString("auth").equals("null")) {
                        Log.e(TAG, "Get Json Data:" + jsonObject.toString());
                        JSONArray jsonArray = new JSONArray();
                        jsonArray = jsonObject.getJSONObject("Variables").getJSONArray("postlist");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject1 = jsonArray.getJSONObject(i);
                            if (jsonObject1.has("imagelist")) {
                                final String[][] bitmap = new String[jsonObject1.getJSONArray("imagelist").length()][3];
                                Log.e(TAG, "Length" + jsonObject1.getJSONArray("imagelist").length());
                                final HashMap<String, Object> data = setData(jsonObject1, "author", "dbdateline", "message", "tid", "pid");
                                for (j = 0; j < jsonObject1.getJSONArray("imagelist").length(); j++) {
                                    String url = Config.Net_URL +
                                            jsonObject1.getJSONObject("attachments").getJSONObject(jsonObject1.getJSONArray("imagelist").get(j).toString()).getString("url") +
                                            jsonObject1.getJSONObject("attachments").getJSONObject(jsonObject1.getJSONArray("imagelist").get(j).toString()).getString("attachment");
                                    bitmap[j][0] = jsonObject1.getJSONObject("attachments").getJSONObject(jsonObject1.getJSONArray("imagelist").get(j).toString()).getString("description");
                                    bitmap[j][1]=url;
                                    new GetPic(context, url, new GetPic.SuccessCallback() {
                                        @Override
                                        public void onSuccess(Object result) {
                                            bitmap[j-1][2]=((String[][]) result)[0][2];
                                            data.put("bitmap",bitmap);
                                            megs.add(new DetailMessage(data));
                                            if (successCallback != null)
                                                successCallback.onSuccess(megs);
                                        }
                                    }, new GetPic.FailCallback() {
                                        @Override
                                        public void onFail() {

                                        }
                                    });
                                }
                            } else {
                                HashMap<String, Object> data = setData(jsonObject1, "author", "dbdateline", "message", "tid", "pid");
                                megs.add(new DetailMessage(data));
                                if (successCallback != null)
                                    successCallback.onSuccess(megs);
                            }
                        }

                    } else {
                        Log.e(TAG, "Failed Get Json Data(auth==null)");
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
        }, "version", "4", "module", "viewthread", "tid", tid);

    }

    private HashMap<String, Object> setData(JSONObject jsonObject, String... strings) {
        final HashMap<String, Object> data = new HashMap<>();
        for (String string : strings) {
            try {
                data.put(string, jsonObject.getString(string));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public static interface SuccessCallback {
        void onSuccess(List<DetailMessage> detailMessages);
    }

    public static interface FailCallback {
        void onFail();
    }

}
