package hanzy.secret.aty;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import hanzy.secret.R;
import hanzy.secret.net.GetPic;
import hanzy.secret.net.HttpMethod;
import hanzy.secret.net.NetConnection;
import hanzy.secret.secret.Config;
import hanzy.secret.utils.PicUtils;

public class Aty_Test extends Activity {

    private String TAG="Aty_Test";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty__test);
        final ImageView imageView= (ImageView) findViewById(R.id.img);
        new NetConnection(Aty_Test.this, Config.BASE_URL, HttpMethod.GET, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG,""+result);
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {

            }
        },"version","4","module","profile");
    }
}
