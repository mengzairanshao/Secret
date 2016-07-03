package hanzy.secret.secret;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import hanzy.secret.R;
import hanzy.secret.aty.AtyLogin;
import hanzy.secret.aty.AtyTimeLine;
import hanzy.secret.net.CookiesSet;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String cookies = CookiesSet.getCookieText(MainActivity.this);
        if(cookies!=null)
        startActivity(new Intent(this,AtyTimeLine.class));
        else
            startActivity(new Intent(this,AtyLogin.class));
        finish();
    }
}
