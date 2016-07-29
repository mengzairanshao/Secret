package hanzy.secret.net;
import android.content.Context;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import hanzy.secret.secret.Config;

/**
 * Created by h on 2016/6/29.
 */
public class Login {
    public String TAG="Login";
    public Login(final Context context, final String userName, String password, final SuccessCallback successCallback, final FailCallback failCallback){

        new NetConnection(context,Config.LOGIN_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {

            @Override
            public void onSuccess(String result) {
                try {
                    Log.e(TAG,"Result"+result);
                    JSONObject jsonObject=new JSONObject(result);
                        if (jsonObject.getJSONObject("Message").getString("messageval").equals("login_succeed")){
                            Log.e(TAG,"Login Success:"+userName);
                            Config.cacheDATA(context,"Login_succeed",Config.IS_LOGINED);
                            Config.cacheDATA(context,jsonObject.getJSONObject("Variables").getString("member_uid"),Config.AUTHOR_ID);
                            if (successCallback!=null){
                                successCallback.onSuccess(result);
                            }
                        }
                        else if(failCallback!=null){
                            Log.e(TAG,"Login Failed:"+userName);
                            failCallback.onFail();
                        }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                Config.cacheDATA(context,"Logout_succeed",Config.IS_LOGINED);
                Log.e(TAG,"Login Failed(onFail):"+userName);
                if (failCallback!=null) failCallback.onFail();
            }
        },Config.KEY_UERNAME,userName,Config.KEY_PASSWORD,password);

    }

    public static interface SuccessCallback{
        void onSuccess(String result);
    }

    public static interface FailCallback{
        void onFail();
    }
}
