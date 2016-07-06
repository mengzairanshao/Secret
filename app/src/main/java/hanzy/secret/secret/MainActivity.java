package hanzy.secret.secret;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import hanzy.secret.aty.AtyLogin;
import hanzy.secret.aty.AtyCatalog;
import hanzy.secret.net.CookiesSet;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String cookies = CookiesSet.getCookieText(MainActivity.this);
        if(!cookies.equals("")){
            startActivity(new Intent(this,AtyCatalog.class));
        }
        else{
            startActivity(new Intent(this,AtyLogin.class));
        }
        finish();
    }
}
