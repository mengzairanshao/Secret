package hanzy.secret.secret;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import hanzy.secret.R;
import hanzy.secret.aty.AtyLogin;
import hanzy.secret.aty.AtyTimeLine;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String token =Config.getCachedToken(this);
        startActivity(new Intent(this,AtyTimeLine.class));
//        if(token!=null){
//            Intent i=new Intent(this, AtyTimeLine.class);
//            i.putExtra(Config.KEY_TOKEN,token);
//            startActivity(i);
//        }else {
//            startActivity(new Intent(this, AtyLogin.class));
//        }

        finish();
    }
}
