package hanzy.secret.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;
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
    private Handler handler;
    private Context context;
    private String[][] bitmap;
    private String url;
    private int j;
    private List<DetailMessage> megs = new ArrayList<>();
    private JSONObject jsonObject1 = new JSONObject();
    private int length = 0;

    public GetDetail(final Context context, final SuccessCallback successCallback, final FailCallback failCallback, final String tid, Handler handler) {

        this.context = context;
        this.handler = handler;
        new NetConnection(context, Config.Base_URL, HttpMethod.GET, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(final String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (!jsonObject.getJSONObject("Variables").getString("auth").equals("null")) {
                        Log.e(TAG, "Get Json Data:" + jsonObject.toString());
                        JSONArray jsonArray = jsonObject.getJSONObject("Variables").getJSONArray("postlist");
                        length = jsonArray.length();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject1 = jsonArray.getJSONObject(i);
                            HashMap<String, Object> data = setData(jsonObject1, "author", "dbdateline", "message", "tid", "pid");
                            if (jsonObject1.has("imagelist")) {
                                bitmap = new String[jsonObject1.getJSONArray("imagelist").length() + 1][3];
                                bitmap[0][0] = jsonObject1.getString("authorid");
                                bitmap[0][1] = Config.PIC_URL + "?uid=" + jsonObject1.getString("authorid") + "&size=small";
                                for (j = 0; j < jsonObject1.getJSONArray("imagelist").length(); j++) {
                                    url = Config.Net_URL
                                            + jsonObject1.getJSONObject("attachments").getJSONObject(jsonObject1.getJSONArray("imagelist").get(j).toString()).getString("url")
                                            + jsonObject1.getJSONObject("attachments").getJSONObject(jsonObject1.getJSONArray("imagelist").get(j).toString()).getString("attachment");
                                    bitmap[j + 1][0] = jsonObject1.getJSONObject("attachments").getJSONObject(jsonObject1.getJSONArray("imagelist").get(j).toString()).getString("description");
                                    bitmap[j + 1][1] = url;
                                }

                            } else {
                                bitmap = new String[1][3];
                                bitmap[0][0] = jsonObject1.getString("authorid");
                                bitmap[0][1] = Config.PIC_URL + "?uid=" + jsonObject1.getString("authorid") + "&size=small";
                            }
                            data.put("bitmap", bitmap);
                            sortList(megs, data);
                            if (successCallback != null) {
                                successCallback.onSuccess(megs);
                                if (megs.size() == length) {
                                    GetPic();
                                }
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

    private void GetPic() {
        for (int i = 0; i < megs.size(); i++) {
            for (int j = 0; j < megs.get(i).getBitmap().length; j++) {
                new GetPic(context, megs.get(i).getBitmap()[j][1], new GetPic.SuccessCallback() {
                    @Override
                    public void onSuccess(Object result) {
                        Message message = handler.obtainMessage();
                        message.what = 1;
                        message.obj = result;
                        handler.sendMessage(message);
                    }
                }, new GetPic.FailCallback() {
                    @Override
                    public void onFail() {

                    }
                });
            }
        }
    }

    private HashMap<String, Object> setData(JSONObject jsonObject, String... strings) {
        final HashMap<String, Object> data = new HashMap<>();
        for (String string : strings) {
            try {
                data.put(string, jsonObject.getString(string).replace("<br />","\r"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    private void sortList(List<DetailMessage> megs, HashMap<String, Object> data) {
        if (megs.size() == 0) {
            megs.add(0, new DetailMessage(data));
        } else {
            int location = 0;
            for (int j = 0; j < megs.size(); j++) {
                if (Integer.parseInt(megs.get(j).getDateline()) <= Integer.parseInt(data.get("dbdateline").toString())) {
                    location = j + 1;
                }
            }
            megs.add(location, new DetailMessage(data));
        }
    }

    public static interface SuccessCallback {
        void onSuccess(List<DetailMessage> detailMessageList);
    }

    public static interface FailCallback {
        void onFail();
    }

}
