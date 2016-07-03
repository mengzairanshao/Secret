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

        new NetConnection(context,Config.Login_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {

            @Override
            public void onSuccess(String result) {
                try {
                    Log.e(TAG,"Result"+result);
                    JSONObject jsonObject=new JSONObject(result);

                    Log.e(TAG,"返回值"+ result);
                    if (result!=null) {
                        if (result.contains("login_succeed")){
                            Log.e(TAG,"Login Success:"+userName);
                            //Log.e(TAG,"Key:"+Config.getCachedToken(context));
                            if (successCallback!=null)successCallback.onSuccess(Config.SUCCEED_LOGIN);
                        }
                        else if(failCallback!=null){
                            Log.e(TAG,"Login Failed:"+userName);
                            failCallback.onFail();
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                Log.e(TAG,"Login Failed(onFail):"+userName);
                if (failCallback!=null) failCallback.onFail();
            }
        },Config.UERNAME,userName,Config.PASSWORD,password);

    }

    public static interface SuccessCallback{
        void onSuccess(String token);
    }

    public static interface FailCallback{
        void onFail();
    }
}
