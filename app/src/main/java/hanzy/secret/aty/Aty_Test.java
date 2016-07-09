package hanzy.secret.aty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import hanzy.secret.R;
import hanzy.secret.net.GetTest;

public class Aty_Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty__test);

        new GetTest(Aty_Test.this);
    }
}
