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
        //GetPic getPic=new GetPic(this,"5","small");
        //ImageView imageView= (ImageView) findViewById(R.id.img);
        //imageView.setImageBitmap(getPic.getBitmap());
    }
}
