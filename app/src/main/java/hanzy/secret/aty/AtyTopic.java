package hanzy.secret.aty;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import hanzy.secret.R;
import hanzy.secret.net.GetTopic;

public class AtyTopic extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_topic);
        new GetTopic(AtyTopic.this);
    }
}                                                                                                                                                                                                                                                                                                                                                                                                                                                   