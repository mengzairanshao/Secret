package hanzy.secret.secret;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import hanzy.secret.R;
import hanzy.secret.aty.AtyLogin;
import hanzy.secret.aty.MainActivity;
import hanzy.secret.net.CookiesSet;
import hanzy.secret.net.HttpMethod;
import hanzy.secret.net.NetConnection;

public class StartingActivity extends Activity {
    private String TAG="StartingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String cookies = CookiesSet.getCookieText(StartingActivity.this);
        Log.e(TAG,"auth:"+cookies);
        if(!cookies.equals("")){
            new NetConnection(StartingActivity.this, Config.BASE_URL, HttpMethod.GET, new NetConnection.SuccessCallback() {
                @Override
                public void onSuccess(String result) {
                    try {
                        if (NetConnection.isMobileNetworkAvailable(StartingActivity.this).equals("mobile")){
                            Toast.makeText(StartingActivity.this, R.string.MobileNetwork,Toast.LENGTH_LONG).show();
                        }
                        JSONObject jsonObject=new JSONObject(result);
                        Log.e(TAG,"FormHash:"+jsonObject);
                        if (!jsonObject.getJSONObject("Variables").getString("auth").equals("null")){
                            Config.cacheDATA(StartingActivity.this,jsonObject.getJSONObject("Variables").getString("formhash"),Config.FORM_HASH);
                            startActivity(new Intent(StartingActivity.this,MainActivity.class));
                            finish();
                        }
                        else{
                            startActivity(new Intent(StartingActivity.this,AtyLogin.class));
                            finish();
                        }
                    } catch (JSONException e) {
                        startActivity(new Intent(StartingActivity.this,AtyLogin.class));
                        e.printStackTrace();
                    }

                }
            }, new NetConnection.FailCallback() {
                @Override
                public void onFail() {
                    if (NetConnection.isMobileNetworkAvailable(StartingActivity.this).equals("none")){
                        startActivity(new Intent(StartingActivity.this,MainActivity.class));
                        Toast.makeText(StartingActivity.this,R.string.NetworkFailure,Toast.LENGTH_LONG).show();
                        finish();
                    }else if (NetConnection.isMobileNetworkAvailable(StartingActivity.this).equals("mobile")||NetConnection.isMobileNetworkAvailable(StartingActivity.this).equals("wifi")){
                        startActivity(new Intent(StartingActivity.this,MainActivity.class));
                        Toast.makeText(StartingActivity.this,R.string.LoadFailure,Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            },"version","4","module","checkpost");
        }
        else{
            startActivity(new Intent(this,AtyLogin.class));
            finish();
        }
    }

}
