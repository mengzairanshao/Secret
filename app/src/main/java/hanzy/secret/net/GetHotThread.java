package hanzy.secret.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hanzy.secret.Message.HotThreadMessage;
import hanzy.secret.secret.Config;
import hanzy.secret.utils.logUtils;

/**
 * Created by h on 2016/7/9.
 *
 */
public class GetHotThread {
    private Handler handler;
    private Context context;
    private JSONObject jsonObject1;
    private String TAG = "GetHotThread";
    private Bitmap bitmap;
    private int length = 0;
    private int i = 0;
    private List<HotThreadMessage> megs = new ArrayList<>();

    public GetHotThread(final Context context, final SuccessCallback successCallback, final FailCallback failCallback, Handler handler) {
        this.context = context;
        this.handler = handler;
        new NetConnection(context, Config.BASE_URL, HttpMethod.GET, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    logUtils.log(context,TAG, "Get Json Data:" + result);
                    if (!jsonObject.getJSONObject("Variables").isNull("data")) {
                        JSONArray jsonArray = jsonObject.getJSONObject("Variables").getJSONArray("data");
                        length = jsonArray.length();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject1 = jsonArray.getJSONObject(i);
                            HashMap<String, Object> data = setData(jsonObject1, "author", "dbdateline", "dblastpost", "fid", "replies", "subject", "tid", "views");
                            String[][] bitmap = new String[1][3];
                            bitmap[0][0] = jsonObject1.getString("authorid");
                            bitmap[0][1] = Config.PIC_URL + "?uid=" + jsonObject1.getString("authorid") + "&size=" + Config.VALUE_USER_HEADER_IMAGE_SIZE;
                            data.put("bitmap", bitmap);
                            sortList(megs, data);
                            if (successCallback != null) {
                                if (megs.size() == length) {
                                    successCallback.onSuccess(megs);
                                    GetPic();
                                }
                            }
                        }
                    }else {
                        if (successCallback != null) {
                            successCallback.onSuccess(null);
                        }
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

    private void GetPic() {
        for (i = 0; i < megs.size(); i++) {
            new GetPic(context, megs.get(i).getBitmap()[0][1], new GetPic.SuccessCallback() {
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

    private HashMap<String, Object> setData(JSONObject jsonObject, String... strings) {
        HashMap<String, Object> data = new HashMap<>();
        if (jsonObject!=null) {
            for (String string : strings) {
                try {
                    data.put(string, jsonObject.getString(string));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else {
            for (String string : strings) {
                data.put(string, "");
            }
        }
        return data;
    }

    private void sortList(List<HotThreadMessage> megs, HashMap<String, Object> data) {
        if (megs.size() == 0) {
            megs.add(0, new HotThreadMessage(data));
        } else {
            int location = 0;
            for (int j = 0; j < megs.size(); j++) {
                if (Integer.parseInt(megs.get(j).getDblastpost()) >= Integer.parseInt(data.get("dblastpost").toString())) {
                    location = j + 1;
                }
            }
            megs.add(location, new HotThreadMessage(data));
        }
    }

    public interface SuccessCallback {
        void onSuccess(List<HotThreadMessage> hotThreadMessageList);
    }

    public interface FailCallback {
        void onFail();
    }
}
