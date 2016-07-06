package hanzy.secret.aty;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.List;

import hanzy.secret.Adapter.ThreadAdapter;
import hanzy.secret.Message.ThreadsMessage;
import hanzy.secret.R;
import hanzy.secret.net.GetThread;

/**
 * Created by h on 2016/7/6.
 */
public class AtyThreads extends Activity{

    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thread);
        lv= (ListView) findViewById(R.id.threadList);
        new GetThread(AtyThreads.this, new GetThread.SuccessCallback() {
            @Override
            public void onSuccess(List<ThreadsMessage> threadsMessages) {
                SimpleAdapter adapter=new SimpleAdapter(AtyThreads.this,
                        new ThreadAdapter().getData(threadsMessages),
                        R.layout.thread_list_cell,
                        new String[]{"author","dblastpost","subject","views","replies"},
                        new int[]{R.id.author,R.id.posttime,R.id.threadtitle,R.id.views,R.id.replies});
                lv.setAdapter(adapter);
            }
        }, new GetThread.FailCallback() {
            @Override
            public void onFail() {

            }
        });
    }
}
