package hanzy.secret.aty;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.List;
import java.util.SortedSet;

import hanzy.secret.Adapter.ThreadAdapter;
import hanzy.secret.Message.ThreadsMessage;
import hanzy.secret.R;
import hanzy.secret.net.GetThread;
import hanzy.secret.net.NetConnection;
import hanzy.secret.utils.TimeUtils;

/**
 * Created by h on 2016/7/6.
 */
public class AtyThreads extends AppCompatActivity {

    private String TAG="AtyThreads";
    private ListView lv;
    private List<ThreadsMessage> threadsMessages=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_thread);
        lv= (ListView) findViewById(R.id.threadlist);
        Intent i=getIntent();
        setTitle(i.getStringExtra("name"));
        new GetThread(AtyThreads.this, new GetThread.SuccessCallback() {
            @Override
            public void onSuccess(List<ThreadsMessage> threadsMessages) {
                if (NetConnection.isMobileNetworkAvailable(AtyThreads.this).equals("mobile")){
                    Toast.makeText(AtyThreads.this,R.string.MobileNetwork,Toast.LENGTH_LONG).show();
                }
                AtyThreads.this.threadsMessages=threadsMessages;
                ThreadAdapter threadAdapter=new ThreadAdapter(AtyThreads.this,
                        ThreadAdapter.getData(threadsMessages),
                        R.layout.aty_thread_list_cell,
                        ThreadAdapter.from,
                        ThreadAdapter.to);
                threadAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                    @Override
                    public boolean setViewValue(View view, Object data, String textRepresentation) {
                        if ((view instanceof ImageView) && (data instanceof Bitmap)){
                            ImageView iv = (ImageView) view;
                            iv.setImageBitmap((Bitmap) data);
                            return true;
                        }
                        return false;
                    }
                });
                threadAdapter.notifyDataSetChanged();
                lv.setAdapter(threadAdapter);
            }
        }, new GetThread.FailCallback() {
            @Override
            public void onFail() {
                if (NetConnection.isMobileNetworkAvailable(AtyThreads.this).equals("none")){
                    Toast.makeText(AtyThreads.this,R.string.NetworkFailure,Toast.LENGTH_LONG).show();
                }else if (NetConnection.isMobileNetworkAvailable(AtyThreads.this).equals("mobile")||NetConnection.isMobileNetworkAvailable(AtyThreads.this).equals("wifi")){
                    Toast.makeText(AtyThreads.this,R.string.LoadFailure,Toast.LENGTH_LONG).show();
                }

            }
        },i.getStringExtra("fid"));
        lv.setOnItemClickListener(new OnItemClickListenerImp());
    }

    public class OnItemClickListenerImp implements AdapterView.OnItemClickListener {

        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Intent intent=new Intent(AtyThreads.this,AtyDetail.class);
            Log.e(TAG,"position="+position+"   tid="+threadsMessages.get(position).getTid());
            Log.e(TAG,"position="+position+"   subject="+threadsMessages.get(position).getSubject());
            intent.putExtra("tid",threadsMessages.get(position).getTid());
            intent.putExtra("subject",threadsMessages.get(position).getSubject());
            startActivity(intent);
        }
    }
}
