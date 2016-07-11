package hanzy.secret.aty;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import hanzy.secret.Fragment.ForumsFragment;
import hanzy.secret.Fragment.dummy.DummyContent;
import hanzy.secret.R;
import hanzy.secret.net.GetTest;

public class Aty_Test extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_forums);

        new GetTest(this);
    }

}
