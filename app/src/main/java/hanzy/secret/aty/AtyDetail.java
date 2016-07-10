package hanzy.secret.aty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import hanzy.secret.Adapter.DetailAdapter;
import hanzy.secret.Message.DetailMessage;
import hanzy.secret.R;
import hanzy.secret.net.GetDetail;
import hanzy.secret.net.NetConnection;

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
                if (NetConnection.isMobileNetworkAvailable(AtyDetail.this).equals("mobile")){
                    Toast.makeText(AtyDetail.this,R.string.MobileNetwork,Toast.LENGTH_LONG).show();
                }
                DetailAdapter detailAdapter=new DetailAdapter(AtyDetail.this,
                        DetailAdapter.getData(detailMessages),
                        R.layout.aty_detail_list_cell,
                        DetailAdapter.from,
                        DetailAdapter.to);
                lv.setAdapter(detailAdapter);
            }
        }, new GetDetail.FailCallback() {
            @Override
            public void onFail() {
                if (NetConnection.isMobileNetworkAvailable(AtyDetail.this).equals("none")){
                    Toast.makeText(AtyDetail.this,R.string.NetworkFailure,Toast.LENGTH_LONG).show();
                }else if (NetConnection.isMobileNetworkAvailable(AtyDetail.this).equals("mobile")||NetConnection.isMobileNetworkAvailable(AtyDetail.this).equals("wifi")){
                    Toast.makeText(AtyDetail.this,R.string.LoadFailure,Toast.LENGTH_LONG).show();
                }

            }
        },intent.getStringExtra("tid"));
    }
}
