package hanzy.secret.aty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import hanzy.secret.Adapter.ThreadAdapter;
import hanzy.secret.Message.ThreadsMessage;
import hanzy.secret.R;
import hanzy.secret.net.GetThread;
import hanzy.secret.net.NetConnection;

/**
 * Created by h on 2016/7/6.
 */
public class AtyThreads extends AppCompatActivity {

    private ListView lv;
    private List<ThreadsMessage> threadsMessages=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_thread);
        lv= (ListView) findViewById(R.id.threadlist);
        Intent i=getIntent();
        new GetThread(AtyThreads.this, new GetThread.SuccessCallback() {
            @Override
            public void onSuccess(List<ThreadsMessage> threadsMessages) {
                AtyThreads.this.threadsMessages=threadsMessages;
                ThreadAdapter threadAdapter=new ThreadAdapter(AtyThreads.this,
                        ThreadAdapter.getData(threadsMessages),
                        R.layout.aty_thread_list_cell,
                        ThreadAdapter.from,
                        ThreadAdapter.to);
                lv.setAdapter(threadAdapter);
            }
        }, new GetThread.FailCallback() {
            @Override
            public void onFail() {
                if (NetConnection.isMobileNetworkAvailable(AtyThreads.this).equals("none")){
                    Toast.makeText(AtyThreads.this,R.string.NetworkFailure,Toast.LENGTH_LONG).show();
                }
                if (NetConnection.isMobileNetworkAvailable(AtyThreads.this).equals("mobile")){
                    Toast.makeText(AtyThreads.this,R.string.MobileNetwork,Toast.LENGTH_LONG).show();
                }
            }
        },i.getStringExtra("fid"));
        lv.setOnItemClickListener(new OnItemClickListenerImp());
    }

    public class OnItemClickListenerImp implements AdapterView.OnItemClickListener {

        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Intent intent=new Intent(AtyThreads.this,AtyDetail.class);
            intent.putExtra("tid",threadsMessages.get(position).getTid());
            intent.putExtra("subject",threadsMessages.get(position).getSubject());
            startActivity(intent);
        }
    }
}
