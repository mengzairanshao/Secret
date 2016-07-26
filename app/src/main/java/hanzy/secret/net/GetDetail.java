package hanzy.secret.net;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceScreen;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hanzy.secret.Message.DetailMessage;
import hanzy.secret.R;
import hanzy.secret.secret.Config;

/**
 * Created by h on 2016/7/7.
 */
public class GetDetail {
    public String TAG = "GetDetail";
    private Handler handler;
    private Context context;
    private String[][] bitmap;
    private List<DetailMessage> megs = new ArrayList<>();
    private JSONObject jsonObject1 = new JSONObject();
    private int length = 0;

    public GetDetail(final Context context, final SuccessCallback successCallback, final FailCallback failCallback, final String tid, Handler handler) {

        this.context = context;
        this.handler = handler;
        new NetConnection(context, Config.BASE_URL, HttpMethod.GET, new NetConnection.SuccessCallback() {
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
                            HashMap<String, Object> data = new HashMap<>();
                            setData(data, jsonObject1, "author", "dbdateline", "message", "tid", "pid");
                            bitmap = new String[1][3];
                            bitmap[0][0] = jsonObject1.getString("authorid");
                            bitmap[0][1] = Config.PIC_URL + "?uid=" + jsonObject1.getString("authorid") + "&size=small";
                            data.put("bitmap", bitmap);
                            sortList(megs, data);
                            if (successCallback != null) {
                                successCallback.onSuccess(megs);
                            }
                            if (megs.size() == length) {
                                GetPic();
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
                        message.what = Config.USER_LOAD_IMAGE;
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

    private void setData(final HashMap<String, Object> data, final JSONObject jsonObject, String... strings) {
        for (final String string : strings) {
            try {
                if (string.equals("message")) {
                    String str = jsonObject.getString(string);
                    if (jsonObject.has("imagelist")) {
                        for (int j = 0; j < jsonObject1.getJSONArray("imagelist").length(); j++) {
                            str += "<img src=\""
                                    + Config.NET_URL//<p style="text-align: center;">
                                    + jsonObject1.getJSONObject("attachments").getJSONObject(jsonObject1.getJSONArray("imagelist").get(j).toString()).getString("url")
                                    + jsonObject1.getJSONObject("attachments").getJSONObject(jsonObject1.getJSONArray("imagelist").get(j).toString()).getString("attachment")
                                    + "\"/><p><strong>"
                                    + jsonObject1.getJSONObject("attachments").getJSONObject(jsonObject1.getJSONArray("imagelist").get(j).toString()).getString("description")
                                    + "</strong></p>";
                        }
                    }
                    Spanned spanned = Html.fromHtml(jsonObject.getString(string));
                    data.put(string, spanned);
                    final String finalStr = str;
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            /**
                             * 要实现图片的显示需要使用Html.fromHtml的一个重构方法：public static Spanned
                             * fromHtml (String source, Html.ImageGetterimageGetter,
                             * Html.TagHandler
                             * tagHandler)其中Html.ImageGetter是一个接口，我们要实现此接口，在它的getDrawable
                             * (String source)方法中返回图片的Drawable对象才可以。
                             */
                            Html.ImageGetter imageGetter = new Html.ImageGetter() {
                                @Override
                                public Drawable getDrawable(String source) {
                                    source = Html.fromHtml(source).toString();
                                    URL url;
                                    Drawable drawable = null;
                                    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                                    int width, height;
                                    try {
                                        url = new URL(source);
                                        drawable = Drawable.createFromStream(
                                                url.openStream(), null);
                                        width = drawable.getIntrinsicWidth() * 2;
                                        height = drawable.getIntrinsicHeight() * 2;
                                        if (drawable.getIntrinsicWidth() * 2 > (wm.getDefaultDisplay().getWidth() * 0.85)) {
                                            width = (int) (wm.getDefaultDisplay().getWidth() * 0.85);
                                            height = height * (int) (wm.getDefaultDisplay().getWidth() * 0.85) / (2 * drawable.getIntrinsicWidth());
                                        }
                                        drawable.setBounds(10, 10,
                                                width + 10,
                                                height + 10);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    return drawable;
                                }
                            };
                            Spanned spanned = Html.fromHtml(finalStr, imageGetter, null);
                            data.put(string, spanned);
                            Message message = handler.obtainMessage();
                            message.what = Config.SPANNED_MESSAGE;
                            message.obj = data;
                            handler.sendMessage(message);
                        }
                    });
                    t.start();
                } else {
                    data.put(string, jsonObject.getString(string));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
