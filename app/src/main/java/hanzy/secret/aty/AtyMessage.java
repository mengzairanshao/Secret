package hanzy.secret.aty;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;

import hanzy.secret.R;

/**
 * Created by h on 2016/6/28.
 */
public class AtyMessage extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_message);
    }
}
