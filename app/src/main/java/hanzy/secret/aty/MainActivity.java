package hanzy.secret.aty;

import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import hanzy.secret.Fragment.CatalogFragment;
import hanzy.secret.Fragment.HotThreadsFragment;
import hanzy.secret.Fragment.ProfileFragment;
import hanzy.secret.R;
import hanzy.secret.net.CookiesSet;
import hanzy.secret.secret.Config;
import hanzy.secret.utils.PicUtils;

public class MainActivity extends AppCompatActivity {

    private Boolean flag=true;
    private static String TAG="MainActivity";
    public static HotThreadsFragment hotThreadsFragment;
    public static CatalogFragment catalogFragment;
    public static ProfileFragment profileFragment;
    private TabLayout tabLayout;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CookiesSet.getCookieText(MainActivity.this);
        if (Config.getCachedDATA(MainActivity.this,Config.IS_LOGINED).equals("Logout_succeed")&&flag) {
            Toast.makeText(MainActivity.this, R.string.not_login_content_can_not_display,Toast.LENGTH_LONG).show();
            flag=false;
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
        if (Config.getCachedDATA(MainActivity.this,Config.IS_LOGINED).equals("Login_succeed")&&Config.getCachedDATA(MainActivity.this,Config.USER_HEADER_IMAGE)!=null){
                toolbar.setLogo(new BitmapDrawable(PicUtils.convertStringToIcon(Config.getCachedDATA(MainActivity.this,Config.USER_HEADER_IMAGE))));
            }else {
            toolbar.setLogo(R.drawable.user_img);
        }
            toolbar.setTitle(R.string.app_name);
        }
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        if (mViewPager != null) {
            mViewPager.setAdapter(mSectionsPagerAdapter);
        }


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        // tabLayout使用viewPager接收的tabSectionAdapter里设置的title
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(mViewPager);
        }

    }

    @Override
    protected void onResume() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        super.onResume();
        if (toolbar != null) {
            if (Config.getCachedDATA(MainActivity.this,Config.IS_LOGINED).equals("Login_succeed")&&Config.getCachedDATA(MainActivity.this,Config.USER_HEADER_IMAGE)!=null){
                toolbar.setLogo(new BitmapDrawable(PicUtils.convertStringToIcon(Config.getCachedDATA(MainActivity.this,Config.USER_HEADER_IMAGE))));
            }else {
                toolbar.setLogo(R.drawable.user_img);
            }
            toolbar.setTitle(R.string.app_name);
        }
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new HotThreadsFragment();
                case 1:
                    return new CatalogFragment();
                case 2:
                    return new ProfileFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "热门帖子";
                case 1:
                    return "论坛";
                case 2:
                    return "个人中心";
            }
            return null;
        }
    }
}
