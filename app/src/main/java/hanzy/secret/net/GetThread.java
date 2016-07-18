package hanzy.secret.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hanzy.secret.Message.HotThreadMessage;
import hanzy.secret.Message.ThreadsMessage;
import hanzy.secret.secret.Config;
import hanzy.secret.utils.PicUtils;
import hanzy.secret.utils.TimeUtils;

/**
 * Created by h on 2016/6/30.
 */
public class GetThread {
    public String TAG = "GetThread";
    private Bitmap bitmap=null;
    private String url=null;
    private HashMap<String,String> hashMap=new HashMap<>();
    private List<ThreadsMessage> megs = new ArrayList<>();
    private List<ThreadsMessage> megs_another = new ArrayList<>();
    private Thread thread;
    public GetThread(final Context context, final SuccessCallback successCallback, final FailCallback failCallback, String fid, final Handler handler) {

        new NetConnection(context, Config.Base_URL, HttpMethod.GET, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (!jsonObject.getJSONObject("Variables").getString("auth").equals("null")) {
                        Log.e(TAG, "Get Json Data:" + jsonObject.toString());
                        JSONArray jsonArray = jsonObject.getJSONObject("Variables").getJSONArray("forum_threadlist");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1= jsonArray.getJSONObject(i);
                            final HashMap<String, Object> data=setData(jsonObject1,"author","dblastpost","subject","views","replies","tid","authorid");
                            final String[][] bitmap=new String[1][3];
                            bitmap[0][0]=jsonObject1.getString("authorid");
                            bitmap[0][1]=Config.PIC_URL+"?uid="+jsonObject1.getString("authorid")+"&size=small";
                            new GetPic(context, bitmap[0][1], new GetPic.SuccessCallback() {
                                @Override
                                public void onSuccess(String result) {
                                    bitmap[0][2]=result;
                                    data.put("bitmap", bitmap);
                                    if (megs.size()==0){
                                        megs.add(0,new ThreadsMessage(data));
                                    }else {
                                        int location=0;
                                        for (int j=0;j<megs.size();j++){
                                            if (Integer.parseInt(megs.get(j).getDblastpost())>=Integer.parseInt(data.get("dblastpost").toString())){
                                                location=j+1;
                                            }
                                        }
                                        megs.add(location,new ThreadsMessage(data));
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
        }, "version","4","module","forumdisplay","fid",fid,"page","1") ;



    }

    public static interface SuccessCallback {
        void onSuccess(List<ThreadsMessage> threadsMessages);
    }

    public static interface FailCallback {
        void onFail();
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
}
