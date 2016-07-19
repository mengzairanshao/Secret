package hanzy.secret.aty;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import hanzy.secret.Adapter.ThreadAdapter;
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

        handler=new Handler(){

            @Override
            public void handleMessage(Message msg) {
                    if(msg.what==1)
                    {
                        int[] url=(int[]) msg.obj;
                        for(int i=0;i<threadsMessageList.size();i++)
                        {
                            ThreadsMessage threadsMessage=threadsMessageList.get(url[i]);
                                threadsMessageList.set(url[i], threadsMessage);
                                updateView(url[i]);
                                break;
                        }
                    }
            }
        };


        new GetThread(AtyThreads.this, new GetThread.SuccessCallback() {
            @Override
            public void onSuccess(List<ThreadsMessage> threadsMessageList) {
                if (NetConnection.isMobileNetworkAvailable(AtyThreads.this).equals("mobile")){
                    Toast.makeText(AtyThreads.this,R.string.MobileNetwork,Toast.LENGTH_LONG).show();
                }
                AtyThreads.this.threadsMessageList=threadsMessageList;
                ThreadAdapter threadAdapter=new ThreadAdapter(AtyThreads.this);
                threadAdapter.addAll(threadsMessageList);
                listView.setAdapter(threadAdapter);
            }
        }, new GetThread.FailCallback() {
            @Override
            public void onFail() {

            }
        },i.getStringExtra("fid"),handler);
//        new GetThread(AtyThreads.this, new GetThread.SuccessCallback() {
//            @Override
//            public void onSuccess(List<ThreadsMessage> threadsMessageList) {
//                if (NetConnection.isMobileNetworkAvailable(AtyThreads.this).equals("mobile")){
//                    Toast.makeText(AtyThreads.this,R.string.MobileNetwork,Toast.LENGTH_LONG).show();
//                }
//                AtyThreads.this.threadsMessageList=threadsMessageList;
//                ThreadAdapter threadAdapter=new ThreadAdapter(threadsMessageList,lv,AtyThreads.this,
//                        ThreadAdapter.getData(threadsMessageList),
//                        R.layout.aty_thread_list_cell,
//                        ThreadAdapter.from,
//                        ThreadAdapter.to);
//                threadAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
//                    @Override
//                    public boolean setViewValue(View view, Object data, String textRepresentation) {
//                        if ((view instanceof ImageView) && (data instanceof Bitmap)){
//                            ImageView iv = (ImageView) view;
//                            iv.setImageBitmap((Bitmap) data);
//                            return true;
//                        }
//                        return false;
//                    }
//                });
//                threadAdapter.notifyDataSetChanged();
//                listView.setAdapter(threadAdapter);
//            }
//        }, new GetThread.FailCallback() {
//            @Override
//            public void onFail() {
//                if (NetConnection.isMobileNetworkAvailable(AtyThreads.this).equals("none")){
//                    Toast.makeText(AtyThreads.this,R.string.NetworkFailure,Toast.LENGTH_LONG).show();
//                }else if (NetConnection.isMobileNetworkAvailable(AtyThreads.this).equals("mobile")||NetConnection.isMobileNetworkAvailable(AtyThreads.this).equals("wifi")){
//                    Toast.makeText(AtyThreads.this,R.string.LoadFailure,Toast.LENGTH_LONG).show();
//                }
//
//            }
//        },i.getStringExtra("fid"),handler);

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


    /**
     * 用于更新我们想要更新的item
     * @param itemIndex 想更新item的下标
     * **/
    private void updateView(int itemIndex)
    {
//得到第1个可显示控件的位置,记住是第1个可显示控件噢。而不是第1个控件
        int visiblePosition = listView.getFirstVisiblePosition();
//得到你需要更新item的View
        int count=listView.getChildCount();
        int count1=listView.getCount();
//        View view = listView.getChildAt(0);
        View view = listView.getChildAt(itemIndex - visiblePosition-1);
        ThreadsMessage threadsMessage=threadsMessageList.get(itemIndex);
        final String bitmap=threadsMessage.getBitmap()[0][2];
        if(bitmap!=null)
        {
            ViewHolder viewHolder=new ViewHolder(view);
            viewHolder.author.setText(threadsMessage.getAuthor());
            viewHolder.dblastpost.setText(TimeUtils.times(Integer.parseInt(threadsMessage.getDblastpost())*1000L));
            viewHolder.replies.setText(threadsMessage.getReplies());
            viewHolder.subject1.setText(threadsMessage.getSubject());
            viewHolder.views.setText(threadsMessage.getViews());
            viewHolder.user_img.setImageBitmap(PicUtils.convertStringToIcon(threadsMessage.getBitmap()[0][2]));
        }
    }

    static class ViewHolder {

        public TextView author;
        public TextView subject1;
        public TextView views;
        public TextView replies;
        public TextView dblastpost;
        public ImageView user_img;

        public ViewHolder(View view) {
            this.author = (TextView) view.findViewById(R.id.thread_author);
            this.dblastpost = (TextView) view.findViewById(R.id.thread_posttime);
            this.replies = (TextView) view.findViewById(R.id.thread_replies);
            this.subject1 = (TextView) view.findViewById(R.id.thread_title);
            this.user_img = (ImageView) view.findViewById(R.id.thread_user_img);
            this.views = (TextView) view.findViewById(R.id.thread_views);
        }
    }
}
