package hanzy.secret.net;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import hanzy.secret.secret.Config;
import hanzy.secret.utils.TimeUtils;

/**
 * Created by h on 2016/7/8.
 */
public class GetTest {

    public String TAG="GetTest";
    public GetTest(Context context){
        new NetConnection(context, Config.Base_URL, HttpMethod.GET, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                if (result!=null) {
                    try {
                        JSONObject obj = new JSONObject(result);
                        Log.e(TAG,"josn长度"+obj.getJSONObject("Variables").getJSONArray("data").length());
                        Log.e(TAG,"获取JSON数据:"+obj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                Log.e(TAG,"获取JSON数据失败");
            }
        },"version","4","module","hotthread");
    }
    public static interface SuccessCallback{
        void onSuccess(String token);
    }

    public static interface FailCallback{
        void onFail();
    }
}
