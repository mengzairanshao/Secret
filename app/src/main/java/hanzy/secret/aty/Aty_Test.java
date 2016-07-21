package hanzy.secret.aty;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;

import hanzy.secret.Fragment.ForumsFragment;
import hanzy.secret.Fragment.dummy.DummyContent;
import hanzy.secret.R;
import hanzy.secret.net.GetPic;
import hanzy.secret.net.GetTest;
import hanzy.secret.utils.PicUtils;

public class Aty_Test extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty__test);
        final ImageView imageView= (ImageView) findViewById(R.id.img);
        new GetPic(Aty_Test.this, "http://192.168.23.1/data/attachment/forum/201607/07/004316rrxril619r6ir9br.jpg", new GetPic.SuccessCallback() {
            @Override
            public void onSuccess(Object result) {
                imageView.setImageBitmap(PicUtils.convertStringToIcon(((String[][]) result)[0][1]));
            }
        }, new GetPic.FailCallback() {
            @Override
            public void onFail() {

            }
        });
    }
}
