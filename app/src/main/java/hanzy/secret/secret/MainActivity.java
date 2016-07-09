package hanzy.secret.secret;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import hanzy.secret.aty.AtyCatalog;
import hanzy.secret.aty.AtyDetail;
import hanzy.secret.aty.AtyLogin;
import hanzy.secret.aty.Aty_Test;
import hanzy.secret.net.CookiesSet;

public class MainActivity extends AppCompatActivity {
    private String TAG="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String cookies = CookiesSet.getCookieText(MainActivity.this);
        Log.e(TAG,"auth:"+cookies);
        if(!cookies.equals("")){
            startActivity(new Intent(this,Aty_Test.class));
        }
        else{
            startActivity(new Intent(this,AtyLogin.class));
        }
        finish();
    }
}
