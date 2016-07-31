package hanzy.secret.Adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hanzy.secret.Message.ThreadsMessage;
import hanzy.secret.R;
import hanzy.secret.utils.PicUtils;
import hanzy.secret.utils.TimeUtils;


/**
 * Created by h on 2016/7/6.
 */
public class ThreadAdapter extends BaseAdapter {
    private static Handler handler;
    private static List<ThreadsMessage> threadsMessageList_copy;
    private static ListView listView;
    public String TAG = "ThreadAdapter";
    private Context context;
    private LayoutInflater inflater;
    private List<ThreadsMessage> threadsMessageList = new ArrayList<>();

    public ThreadAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * @param handler
     * @param threadsMessageList
     * @param listView
     * 修改静态变量
     */
    public static void set(Handler handler, List<ThreadsMessage> threadsMessageList, ListView listView) {
        ThreadAdapter.handler = handler;
        ThreadAdapter.threadsMessageList_copy = threadsMessageList;
        ThreadAdapter.listView = listView;
    }

    /**
     * @param itemIndex
     * itemIndex是想要修改的ListView的编号
     */
    public static void updateView(int itemIndex) {
        View view;
        if (itemIndex >= listView.getFirstVisiblePosition() && itemIndex < (listView.getChildCount() + listView.getFirstVisiblePosition())) {
            ThreadsMessage threadsMessage = threadsMessageList_copy.get(itemIndex);
            final String bitmap = threadsMessage.getBitmap()[0][2];
            if (bitmap != null) {
                view = listView.getChildAt(itemIndex);
                ViewHolder viewHolder = new ViewHolder(view);
                viewHolder.author.setText(threadsMessage.getAuthor());
                viewHolder.dblastpost.setText(TimeUtils.times(Integer.parseInt(threadsMessage.getDblastpost()) * 1000L));
                viewHolder.dbdateline.setText(TimeUtils.times(Integer.parseInt(threadsMessage.getDbdateline()) * 1000L));
                viewHolder.replies.setText(threadsMessage.getReplies());
                viewHolder.subject1.setText(threadsMessage.getSubject());
                viewHolder.views.setText(threadsMessage.getViews());
                viewHolder.user_img.setImageBitmap(PicUtils.createCircleImage(threadsMessage.getBitmap()[0][2]));
            }
        }
    }

    public Context getContext() {
        return context;
    }

    public void addAll(List<ThreadsMessage> threadsMessageList) {
        this.threadsMessageList = threadsMessageList;
    }

    public void clear() {
        threadsMessageList.clear();
    }

    @Override
    public int getCount() {
        return threadsMessageList.size();
    }

    @Override
    public ThreadsMessage getItem(int position) {
        return threadsMessageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.aty_thread_list_cell, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ThreadsMessage threadsMessage = getItem(position);
        viewHolder.author.setText(threadsMessage.getAuthor());
        viewHolder.dblastpost.setText(TimeUtils.times(Integer.parseInt(threadsMessage.getDblastpost()) * 1000L));
        viewHolder.dbdateline.setText(TimeUtils.times(Integer.parseInt(threadsMessage.getDbdateline()) * 1000L));
        viewHolder.replies.setText(threadsMessage.getReplies());
        viewHolder.subject1.setText(threadsMessage.getSubject());
        viewHolder.views.setText(threadsMessage.getViews());
        viewHolder.user_img.setImageBitmap(PicUtils.createCircleImage(threadsMessage.getBitmap()[0][2]));
        return convertView;
    }

    public static class ViewHolder {
        public TextView author;
        public TextView subject1;
        public TextView views;
        public TextView replies;
        public TextView dblastpost;
        public TextView dbdateline;
        public ImageView user_img;

        public ViewHolder(View view) {
            this.author = (TextView) view.findViewById(R.id.thread_author);
            this.dblastpost = (TextView) view.findViewById(R.id.thread_dblastpost);
            this.dbdateline = (TextView) view.findViewById(R.id.thread_dbdateline);
            this.replies = (TextView) view.findViewById(R.id.thread_replies);
            this.subject1 = (TextView) view.findViewById(R.id.thread_subject);
            this.user_img = (ImageView) view.findViewById(R.id.thread_user_img);
            this.views = (TextView) view.findViewById(R.id.thread_views);
        }
    }
}
