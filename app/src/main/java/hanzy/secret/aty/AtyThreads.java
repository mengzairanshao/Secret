package hanzy.secret.aty;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import hanzy.secret.Adapter.ThreadAdapter;
import hanzy.secret.Handler.ThreadsHandler;
import hanzy.secret.Message.ThreadsMessage;
import hanzy.secret.R;
import hanzy.secret.net.GetThread;
import hanzy.secret.net.NetConnection;
import hanzy.secret.utils.PicUtils;
import hanzy.secret.utils.TimeUtils;

/**
 * Created by h on 2016/7/6.
 */
public class AtyThreads extends AppCompatActivity {

    private ThreadAdapter threadAdapter;
    private Handler handler;
    private String TAG="AtyThreads";
    private ListView listView;
    private List<ThreadsMessage> threadsMessageList=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_thread);
        listView= (ListView) findViewById(R.id.threadlist);
        Intent i=getIntent();
        setTitle(i.getStringExtra("name"));
        setHandler();
        new GetThread(AtyThreads.this, new GetThread.SuccessCallback() {
            @Override
            public void onSuccess(List<ThreadsMessage> threadsMessageList) {
                if (NetConnection.isMobileNetworkAvailable(AtyThreads.this).equals("mobile")){
                    Toast.makeText(AtyThreads.this,R.string.MobileNetwork,Toast.LENGTH_LONG).show();
                }
                AtyThreads.this.threadsMessageList=threadsMessageList;
                threadAdapter=new ThreadAdapter(AtyThreads.this);
                threadAdapter.addAll(threadsMessageList);
                threadAdapter.notifyDataSetChanged();
                listView.setAdapter(threadAdapter);
            }
        }, new GetThread.FailCallback() {
            @Override
            public void onFail() {

            }
        },i.getStringExtra("fid"),handler);

        listView.setOnItemClickListener(new OnItemClickListenerImp());

    }
    public class OnItemClickListenerImp implements AdapterView.OnItemClickListener {

        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Intent intent=new Intent(AtyThreads.this,AtyDetail.class);
            Log.e(TAG,"position="+position+"   tid="+threadsMessageList.get(position).getTid());
            Log.e(TAG,"position="+position+"   subject="+threadsMessageList.get(position).getSubject());
            intent.putExtra("tid",threadsMessageList.get(position).getTid());
            intent.putExtra("subject",threadsMessageList.get(position).getSubject());
            startActivity(intent);
        }
    }

    public void setHandler(){
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==1)
                {
                    ThreadAdapter.set(handler,threadsMessageList,listView);
                    String[][] bitmap=(String[][]) msg.obj;
                    for (String[] aBitmap : bitmap) {
                        for (int i = 0; i < threadsMessageList.size(); i++) {
                            ThreadsMessage threadsMessage = threadsMessageList.get(i);
                            if (threadsMessage.getBitmap()[0][1].equals(aBitmap[1])) {
                                threadsMessageList.get(i).setBitmap(0, bitmap[0][2]);
                                ThreadAdapter.updateView(i);
                            }
                        }
                    }
                }
            }
        };
    }
}
