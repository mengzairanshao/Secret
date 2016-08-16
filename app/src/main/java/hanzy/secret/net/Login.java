package hanzy.secret.net;
import android.content.Context;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import hanzy.secret.secret.Config;
import hanzy.secret.utils.logUtils;

/**
 * Created by h on 2016/6/29.
 *
 */
public class Login {
    public String TAG="Login";
    public Login(final Context context, final String userName, String password, final SuccessCallback successCallback, final FailCallback failCallback){

        new NetConnection(context,Config.LOGIN_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    logUtils.log(context, TAG, "Result" + jsonObject.toString());
                    if (jsonObject.getJSONObject("Message").getString("messageval").equals("login_succeed")){
                        logUtils.log(context, TAG, "Login Success:" + userName);
                            Config.cacheDATA(context,"Login_succeed",Config.IS_LOGIN);
                            Config.cacheDATA(context,jsonObject.getJSONObject("Variables").getString("member_uid"),Config.AUTHOR_ID);
                            if (successCallback!=null){
                                successCallback.onSuccess(result);
                            }
                        }
                        else if(failCallback!=null){
                        logUtils.log(context, TAG, "Login Failed:" + userName);
                            failCallback.onFail();
                        }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                Config.cacheDATA(context,"Logout_succeed",Config.IS_LOGIN);
                logUtils.log(context, TAG, "Login Failed(onFail):" + userName);
                if (failCallback!=null) failCallback.onFail();
            }
        },Config.KEY_UERNAME,userName,Config.KEY_PASSWORD,password);

    }

    public interface SuccessCallback {
        void onSuccess(String result);
    }

    public interface FailCallback {
        void onFail();
    }
}
