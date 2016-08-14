package hanzy.secret.secret;

import android.app.Activity;
import android.content.Context;
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
    private String TAG = "StartingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Config.Init(StartingActivity.this);
        startActivity(new Intent(StartingActivity.this,MainActivity.class));
        finish();
    }
}
