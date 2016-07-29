package hanzy.secret.aty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hanzy.secret.R;
import hanzy.secret.net.CookiesSet;
import hanzy.secret.net.Login;
import hanzy.secret.secret.Config;

/**
 * Created by h on 2016/6/28.
 */
public class AtyLogin extends AppCompatActivity {

    private static String TAG = "AtyLogin";
    private EditText editText_uerName, editText_password;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_login);
        editText_password = (EditText) findViewById(R.id.etCode);
        editText_uerName = (EditText) findViewById(R.id.user_name);
        button= (Button) findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editText_uerName.getText()) || TextUtils.isEmpty(editText_password.getText())) {
                    Toast.makeText(AtyLogin.this, R.string.username_or_password_can_not_be_empty, Toast.LENGTH_LONG).show();
                    return;
                }
                new Login(AtyLogin.this, editText_uerName.getText().toString(), editText_password.getText().toString(), new Login.SuccessCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(AtyLogin.this, R.string.succeed_login, Toast.LENGTH_LONG).show();
                        CookiesSet.getCookieText(AtyLogin.this);
                        startActivity(new Intent(AtyLogin.this,AtyNoDisplay.class));
                        finish();
                    }
                }, new Login.FailCallback() {
                    @Override
                    public void onFail() {
                        Toast.makeText(AtyLogin.this, R.string.failed_login, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
