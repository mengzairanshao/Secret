package hanzy.secret.aty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import hanzy.secret.Message.DetailMessage;
import hanzy.secret.R;
import hanzy.secret.net.GetDetail;

public class AtyDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_detail);

        new GetDetail(AtyDetail.this, new GetDetail.SuccessCallback() {
            @Override
            public void onSuccess(List<DetailMessage> detailMessages) {

            }
        }, new GetDetail.FailCallback() {
            @Override
            public void onFail() {

            }
        });
    }
}
