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

import hanzy.secret.Message.HotThreadMessage;
import hanzy.secret.R;
import hanzy.secret.utils.PicUtils;
import hanzy.secret.utils.TimeUtils;

/**
 * Created by h on 2016/7/11.
 */
public class HotThreadAdapter extends BaseAdapter{

    private static List<HotThreadMessage> hotThreadMessageList_copy=new ArrayList<>();
    private static Handler handler;
    private static ListView listView;
    private LayoutInflater inflater;
    private Context context=null;
    private List<HotThreadMessage> hotThreadMessages=new ArrayList<>();
    public String TAG="HotThreadAdapter";
    public static int[] to={R.id.hot_thread_author,R.id.hot_thread_subject,R.id.hot_thread_views,R.id.hot_thread_replies,R.id.hot_thread_dblastpost,R.id.hot_thread_user_img};
    public static String[] from={"author","subject","views","replies","dblastpost","hot_thread_user_img"};

    public HotThreadAdapter(Context context) {
        this.context = context;
        this.inflater=LayoutInflater.from(context);
    }

//    /**
//     * @param handler
//     * @param threadsMessageList
//     * @param listView
//     * 修改静态变量
//     */
//    public static void set(Handler handler, List<HotThreadMessage> threadsMessageList, ListView listView) {
//        HotThreadAdapter.handler = handler;
//        HotThreadAdapter.hotThreadMessageList_copy = threadsMessageList;
//        HotThreadAdapter.listView = listView;
//    }

//    public static void updateView(int itemIndex) {
//        View view;
//        if (itemIndex >= listView.getFirstVisiblePosition() && itemIndex < (listView.getChildCount() + listView.getFirstVisiblePosition())) {
//            HotThreadMessage hotThreadMessage = hotThreadMessageList_copy.get(itemIndex);
//            final String bitmap = hotThreadMessage.getBitmap()[0][2];
//            if (bitmap != null) {
//                view = listView.getChildAt(itemIndex);
//                ViewHolder viewHolder = new ViewHolder(view);
//                viewHolder.author.setText(hotThreadMessage.getAuthor());
//                viewHolder.dblastpost.setText(TimeUtils.times(Integer.parseInt(hotThreadMessage.getDblastpost()) * 1000L));
//                //viewHolder.dbdateline.setText(TimeUtils.times(Integer.parseInt(hotThreadMessage.getDbdateline()) * 1000L));
//                viewHolder.replies.setText(hotThreadMessage.getReplies());
//                viewHolder.subject1.setText(hotThreadMessage.getSubject());
//                viewHolder.views.setText(hotThreadMessage.getViews());
//                viewHolder.user_img.setImageBitmap(PicUtils.createCircleImage(hotThreadMessage.getBitmap()[0][2]));
//            }
//        }
//    }

    @Override
    public int getCount() {
        return hotThreadMessages.size();
    }

    @Override
    public HotThreadMessage getItem(int position) {
        return hotThreadMessages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.aty_hot_thread_list_cell,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        HotThreadMessage hotThreadMessage=getItem(position);
        viewHolder.author.setText(hotThreadMessage.getAuthor());
        viewHolder.dblastpost.setText(TimeUtils.times(Integer.parseInt(hotThreadMessage.getDblastpost())*1000L));
        viewHolder.replies.setText(hotThreadMessage.getReplies());
        viewHolder.subject1.setText(hotThreadMessage.getSubject());
        viewHolder.views.setText(hotThreadMessage.getViews());
        viewHolder.user_img.setImageBitmap(PicUtils.createCircleImage(hotThreadMessage.getBitmap()[0][2]));
        return convertView;
    }

    public Context getContext() {
        return context;
    }

    public void addAll(List<HotThreadMessage> hotThreadMessages){
        this.hotThreadMessages=hotThreadMessages;
        notifyDataSetChanged();
    }

    public void clear(){
        hotThreadMessages.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder {

        public TextView author;
        public TextView subject1;
        public TextView views;
        public TextView replies;
        public TextView dblastpost;
        public ImageView user_img;

        public ViewHolder(View view) {
            this.author = (TextView) view.findViewById(R.id.hot_thread_author);
            this.dblastpost = (TextView) view.findViewById(R.id.hot_thread_dblastpost);
            this.replies = (TextView) view.findViewById(R.id.hot_thread_replies);
            this.subject1 = (TextView) view.findViewById(R.id.hot_thread_subject);
            this.user_img = (ImageView) view.findViewById(R.id.hot_thread_user_img);
            this.views = (TextView) view.findViewById(R.id.hot_thread_views);
        }
    }

}
