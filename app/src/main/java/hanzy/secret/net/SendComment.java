package hanzy.secret.net;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import hanzy.secret.secret.Config;
import hanzy.secret.utils.logUtils;

/**
 * Created by h on 2016/7/23.
 *
 */
public class SendComment {

    private String TAG="SendComment";
    private String url;

    public SendComment(Context context, Object object, final SuccessCallback successCallback, FailCallback failCallback,String... kvs) {
        logUtils.log(context, TAG, "formhash=" + Config.getCachedDATA(context, Config.FORM_HASH));
        String params="";
        for (int i=0;i<kvs.length;i+=2){
            params=params+"&"+kvs[i]+"="+kvs[i+1];
        }
        url=Config.BASE_URL +"?version="+"4"+"&module="+"sendreply"+"&replysubmit="+"yes"+params;
        new NetConnection(context, url, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    if (jsonObject.getJSONObject("Message").getString("messageval").equals("post_reply_succeed")&&successCallback!=null){
                        successCallback.onSuccess(result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {

            }
        },"formhash",Config.getCachedDATA(context,Config.FORM_HASH),"message",object.toString());
    }

    public interface SuccessCallback {
        void onSuccess(String result);
    }

    public interface FailCallback {
        void onFail();
    }
}
