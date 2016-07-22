package hanzy.secret.aty;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.List;

import hanzy.secret.Adapter.DetailAdapter;
import hanzy.secret.Message.DetailMessage;
import hanzy.secret.R;
import hanzy.secret.net.GetDetail;
import hanzy.secret.net.NetConnection;

public class AtyDetail extends AppCompatActivity {

    private DetailAdapter detailAdapter;
    private Handler handler;
    private ListView listView;
    private List<DetailMessage> detailMessageList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_detail);
        listView = (ListView) findViewById(R.id.detailList);
        Intent intent = getIntent();
        this.setTitle(intent.getStringExtra("subject"));
        setHandler();
        new GetDetail(AtyDetail.this, new GetDetail.SuccessCallback() {
            @Override
            public void onSuccess(List<DetailMessage> detailMessageList) {
                if (NetConnection.isMobileNetworkAvailable(AtyDetail.this).equals("mobile")) {
                    Toast.makeText(AtyDetail.this, R.string.MobileNetwork, Toast.LENGTH_LONG).show();
                }
                AtyDetail.this.detailMessageList = detailMessageList;
                detailAdapter = new DetailAdapter(AtyDetail.this);
                detailAdapter.addAll(detailMessageList);
                listView.setAdapter(detailAdapter);
            }
        }, new GetDetail.FailCallback() {
            @Override
            public void onFail() {
                if (NetConnection.isMobileNetworkAvailable(AtyDetail.this).equals("none")) {
                    Toast.makeText(AtyDetail.this, R.string.NetworkFailure, Toast.LENGTH_LONG).show();
                } else if (NetConnection.isMobileNetworkAvailable(AtyDetail.this).equals("mobile") || NetConnection.isMobileNetworkAvailable(AtyDetail.this).equals("wifi")) {
                    Toast.makeText(AtyDetail.this, R.string.LoadFailure, Toast.LENGTH_LONG).show();
                }

            }
        }, intent.getStringExtra("tid"), handler);
    }

    public void setHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    DetailAdapter.set(handler, detailMessageList, listView);
                    String[][] bitmap = (String[][]) msg.obj;
                    DetailMessage detailMessage;
                    for (int i = 0; i < detailMessageList.size(); i++) {
                        detailMessage = detailMessageList.get(i);
                        for (int k = 0; k < bitmap.length; k++) {
                            for (int j = 0; j < detailMessage.getBitmap().length; j++) {
                                if (detailMessage.getBitmap()[j][1].equals(bitmap[k][1])) {
                                    detailMessageList.get(i).setBitmap(j, bitmap[k][2]);
                                    DetailAdapter.updateView(AtyDetail.this,i);
                                }
                            }
                        }
                    }
                }
            }
        };
    }
}
