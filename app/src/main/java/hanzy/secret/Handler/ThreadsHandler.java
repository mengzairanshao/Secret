package hanzy.secret.Handler;

import android.graphics.Bitmap;
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
    public static void updateView(int itemIndex)
    {
        View view;
        if (itemIndex>=listView.getFirstVisiblePosition()&&itemIndex<(listView.getChildCount()+listView.getFirstVisiblePosition())) {
            ThreadsMessage threadsMessage = threadsMessageList.get(itemIndex);
            final String bitmap = threadsMessage.getBitmap()[0][2];
            if (bitmap != null) {
                view = listView.getChildAt(itemIndex);
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
