package hanzy.secret.secret;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import hanzy.secret.R;
import hanzy.secret.aty.AtyCatalog;
import hanzy.secret.aty.AtyLogin;
import hanzy.secret.net.CookiesSet;
import hanzy.secret.net.HttpMethod;
import hanzy.secret.net.NetConnection;

public class MainActivity extends AppCompatActivity {
    private String TAG="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String cookies = CookiesSet.getCookieText(MainActivity.this);
        Log.e(TAG,"auth:"+cookies);
        if(!cookies.equals("")){
            new NetConnection(MainActivity.this, Config.Base_URL, HttpMethod.GET, new NetConnection.SuccessCallback() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject jsonObject=new JSONObject(result);
                        Log.e(TAG,"Net:");
                        if (!jsonObject.getJSONObject("Variables").getString("auth").equals("null")){
                            startActivity(new Intent(MainActivity.this,AtyCatalog.class));
                            finish();
                        }
                        else{
                            startActivity(new Intent(MainActivity.this,AtyLogin.class));
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new NetConnection.FailCallback() {
                @Override
                public void onFail() {
                    if (NetConnection.isMobileNetworkAvailable(MainActivity.this).equals("none")){
                        Toast.makeText(MainActivity.this,R.string.NetworkFailure,Toast.LENGTH_LONG).show();
                    }
                    if (NetConnection.isMobileNetworkAvailable(MainActivity.this).equals("mobile")){
                        Toast.makeText(MainActivity.this, R.string.MobileNetwork,Toast.LENGTH_LONG).show();
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
