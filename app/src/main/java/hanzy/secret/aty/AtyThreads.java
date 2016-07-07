package hanzy.secret.aty;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

import hanzy.secret.Adapter.ThreadAdapter;
import hanzy.secret.Message.ThreadsMessage;
import hanzy.secret.R;
import hanzy.secret.net.GetThread;

/**
 * Created by h on 2016/7/6.
 */
public class AtyThreads extends Activity {

    private ListView lv;
    private List<ThreadsMessage> threadsMessages=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thread);
        lv= (ListView) findViewById(R.id.list);
        new GetThread(AtyThreads.this, new GetThread.SuccessCallback() {
            @Override
            public void onSuccess(List<ThreadsMessage> threadsMessages) {
                AtyThreads.this.threadsMessages=threadsMessages;
                ThreadAdapter threadAdapter=new ThreadAdapter(AtyThreads.this,
                        ThreadAdapter.getData(threadsMessages),
                        R.layout.thread_list_cell,
                        ThreadAdapter.from,
                        ThreadAdapter.to);
                lv.setAdapter(threadAdapter);
            }
        }, new GetThread.FailCallback() {
            @Override
            public void onFail() {

            }
        });
        lv.setOnItemClickListener(new OnItemClickListenerImp());
    }

    public class OnItemClickListenerImp implements AdapterView.OnItemClickListener {

        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Intent intent=new Intent(AtyThreads.this,AtyDetail.class);
            intent.putExtra("tid",threadsMessages.get(position).getTid());
            startActivity(intent);
        }
    }
}
