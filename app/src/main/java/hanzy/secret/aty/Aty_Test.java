package hanzy.secret.aty;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import hanzy.secret.Fragment.ForumsFragment;
import hanzy.secret.Fragment.dummy.DummyContent;
import hanzy.secret.R;

public class Aty_Test extends FragmentActivity implements ForumsFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_forums);

        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ForumsFragment fragment = new ForumsFragment();
        fragmentTransaction.add(R.id.linear, fragment,"one");
        fragmentTransaction.commit();
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
