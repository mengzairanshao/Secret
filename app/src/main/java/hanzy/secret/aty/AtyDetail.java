package hanzy.secret.aty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import hanzy.secret.Adapter.DetailAdapter;
import hanzy.secret.Message.DetailMessage;
import hanzy.secret.R;
import hanzy.secret.net.GetDetail;

public class AtyDetail extends AppCompatActivity {

    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_detail);
        lv= (ListView) findViewById(R.id.detailList);

        Intent intent=getIntent();
        this.setTitle(intent.getStringExtra("subject"));
        new GetDetail(AtyDetail.this, new GetDetail.SuccessCallback() {
            @Override
            public void onSuccess(List<DetailMessage> detailMessages) {
                DetailAdapter detailAdapter=new DetailAdapter(AtyDetail.this,
                        DetailAdapter.getData(detailMessages),
                        R.layout.aty_detail_list,
                        DetailAdapter.from,
                        DetailAdapter.to);
                lv.setAdapter(detailAdapter);
            }
        }, new GetDetail.FailCallback() {
            @Override
            public void onFail() {

            }
        },intent.getStringExtra("tid"));
    }
}
