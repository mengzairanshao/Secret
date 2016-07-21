package hanzy.secret.Handler;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import hanzy.secret.Message.ThreadsMessage;
import hanzy.secret.R;
import hanzy.secret.utils.PicUtils;
import hanzy.secret.utils.TimeUtils;

/**
 * Created by h on 2016/7/20.
 */
public class ThreadsHandler {
    private static Handler handler;
    private static List<ThreadsMessage> threadsMessageList;
    private static ListView listView;

    public static void set(Handler handler,List<ThreadsMessage> threadsMessageList,ListView listView) {
        ThreadsHandler.handler=handler;
        ThreadsHandler.threadsMessageList=threadsMessageList;
        ThreadsHandler.listView=listView;
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
            this.dblastpost = (TextView) view.findViewById(R.id.thread_dblastpost);
            this.replies = (TextView) view.findViewById(R.id.thread_replies);
            this.subject1 = (TextView) view.findViewById(R.id.thread_subject);
            this.user_img = (ImageView) view.findViewById(R.id.thread_user_img);
            this.views = (TextView) view.findViewById(R.id.thread_views);
        }
    }

//    public static void getHandler() {
//        handler=new Handler(){
//
//            @Override
//            public void handleMessage(Message msg) {
//                if(msg.what==1)
//                {
//                    String[][] bitmap=(String[][]) msg.obj;
//                    for (int j=0;j<bitmap.length;j++){
//                        for(int i=0;i<threadsMessageList.size();i++)
//                        {
//                            ThreadsMessage threadsMessage=threadsMessageList.get(i);
//                            if (threadsMessage.getBitmap()[0][1].equals(bitmap[j][1])){
//                                threadsMessageList.get(i).setBitmap(0,bitmap[0][2]);
//                                updateView(i);
//                            }
//
//                        }
//                    }
//
//                }
//
//            }
//        };
//    }

    /**
     * 用于更新我们想要更新的item
     * @param itemIndex 想更新item的下标
     * **/
    public static void updateView(int itemIndex)
    {
        View view;
//得到第1个可显示控件的位置,记住是第1个可显示控件噢。而不是第1个控件
        if (itemIndex>=listView.getFirstVisiblePosition()&&itemIndex<(listView.getChildCount()+listView.getFirstVisiblePosition())) {
            view = listView.getChildAt(itemIndex);
            ThreadsMessage threadsMessage = threadsMessageList.get(itemIndex);
            final String bitmap = threadsMessage.getBitmap()[0][2];
            if (bitmap != null) {
                ViewHolder viewHolder = new ViewHolder(view);
                viewHolder.author.setText(threadsMessage.getAuthor());
                viewHolder.dblastpost.setText(TimeUtils.times(Integer.parseInt(threadsMessage.getDblastpost()) * 1000L));
                viewHolder.replies.setText(threadsMessage.getReplies());
                viewHolder.subject1.setText(threadsMessage.getSubject());
                viewHolder.views.setText(threadsMessage.getViews());
                viewHolder.user_img.setImageBitmap(PicUtils.convertStringToIcon(threadsMessage.getBitmap()[0][2]));
            }
        }
    }
}
