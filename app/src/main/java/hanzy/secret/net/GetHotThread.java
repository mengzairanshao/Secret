package hanzy.secret.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hanzy.secret.Message.HotThreadMessage;
import hanzy.secret.secret.Config;

/**
 * Created by h on 2016/7/9.
 */
public class GetHotThread {

    private JSONObject jsonObject1;
    private String TAG = "GetHotThread";
    private Bitmap bitmap;
    private List<HotThreadMessage> megs = new ArrayList<>();

    public GetHotThread(final Context context, final SuccessCallback successCallback, final FailCallback failCallback) {
        new NetConnection(context, Config.Base_URL, HttpMethod.GET, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (!jsonObject.getJSONObject("Variables").getString("auth").equals("null")) {
                        Log.e(TAG, "Get Json Data:" + result);
                        JSONArray jsonArray = jsonObject.getJSONObject("Variables").getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject1 = jsonArray.getJSONObject(i);
                            final HashMap<String, String> data = setData(jsonObject1, "author", "dbdateline", "dblastpost", "fid", "replies", "subject", "tid", "views");
                            new GetPic(context, jsonObject1.getString("authorid"), "small", new GetPic.SuccessCallback() {
                                @Override
                                public void onSuccess(String result) {
                                    data.put("bitmap", result);
                                    if (megs.size()==0){
                                        megs.add(0,new HotThreadMessage(data));
                                    }else {
                                        int location=0;
                                        for (int j=0;j<megs.size();j++){
                                            if (Integer.parseInt(megs.get(j).getDblastpost())>=Integer.parseInt(data.get("dblastpost"))){
                                                location=j+1;
                                            }
                                        }
                                        megs.add(location,new HotThreadMessage(data));
                                    }
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
        }, "version", "4", "module", "hotthread");
    }

    private HashMap<String, String> setData(JSONObject jsonObject, String... strings) {
        final HashMap<String, String> data = new HashMap<>();
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
        void onSuccess(List<HotThreadMessage> hotThreadMessages);
    }

    public static interface FailCallback {
        void onFail();
    }
}
