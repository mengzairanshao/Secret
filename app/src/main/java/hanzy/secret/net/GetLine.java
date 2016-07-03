package hanzy.secret.net;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import hanzy.secret.secret.Config;


/**
 * Created by h on 2016/6/29.
 */
public class GetLine {
    public String TAG="GetLine";
    public GetLine(final Context context,final SuccessCallback successCallback, final FailCallback failCallback){

        new NetConnection(context, Config.Base_URL, HttpMethod.GET, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    if (!jsonObject.getJSONObject("Variables").getString("auth").equals("null"))
                    {
                        Log.e(TAG,"获取json数据:"+result);
                        JSONArray jsonArray=jsonObject.getJSONObject("Variables").getJSONArray("catlist");
                        JSONObject jsonObject1;
                        List<Message> megs=new ArrayList<>();
                        for (int i=0;i<jsonArray.length();i++){
                            jsonObject1=jsonArray.getJSONObject(i);
                            String[] formList=new String[jsonObject1.getJSONArray("forums").length()];
                            for (int j=0;j<jsonObject1.getJSONArray("forums").length();j++){
                                formList[j]=jsonObject1.getJSONArray("forums").getString(j);
                            }
                            megs.add(new Message(jsonObject1.getString("fid"),jsonObject1.getString("name"),formList));
                        }

                        if (successCallback!=null)successCallback.onSuccess(megs);
                    }else {
                        Log.e(TAG,"获取json数据失败");
                        if (failCallback!=null)failCallback.onFail();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (failCallback!=null)failCallback.onFail();
                }

            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                Log.e(TAG,"获取json数据失败(onFail)");
                if (failCallback!=null)failCallback.onFail();
            }
        },Config.MOBILE,Config.MOBILE_IS,Config.VERSION,Config.VERSION_NUM,
          Config.MODULE,Config.MODULE_FORUMINDEX);

    }

    public static interface SuccessCallback{
        void onSuccess(List<Message> timeline);
    }

    public static interface FailCallback{
        void onFail();
    }
}