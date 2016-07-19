package hanzy.secret.Adapter;

import android.content.Context;
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
public class ThreadAdapter extends BaseAdapter{
    private Context context;
    private LayoutInflater inflater;
//    private ListView listView;
    private List<ThreadsMessage> threadsMessageList=new ArrayList<>();
    public String TAG="ThreadAdapter";
    public static int[] to={R.id.thread_author,R.id.thread_posttime,R.id.thread_title,R.id.thread_views,R.id.thread_replies,R.id.thread_user_img};
    public static String[] from={"author","dblastpost","subject","views","replies","thread_user_img"};

    public ThreadAdapter(Context context) {
//        this.listView=listView;
//        this.threadsMessageList=threadsMessageList;
        this.context = context;
        this.inflater= LayoutInflater.from(context);
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
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.aty_thread_list_cell,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        ThreadsMessage threadsMessage=getItem(position);
        viewHolder.author.setText(threadsMessage.getAuthor());
        viewHolder.dblastpost.setText(TimeUtils.times(Integer.parseInt(threadsMessage.getDblastpost())*1000L));
        viewHolder.replies.setText(threadsMessage.getReplies());
        viewHolder.subject1.setText(threadsMessage.getSubject());
        viewHolder.views.setText(threadsMessage.getViews());
        viewHolder.user_img.setImageBitmap(PicUtils.convertStringToIcon(threadsMessage.getBitmap()[0][2]));
        return convertView;
    }

    public Context getContext() {
        return context;
    }

    public void addAll(List<ThreadsMessage> threadsMessageList){
        this.threadsMessageList=threadsMessageList;
    }

    public void clear(){
        threadsMessageList.clear();
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






//    /**
//     * 用于更新我们想要更新的item
//     * @param itemIndex 想更新item的下标
//     * **/
//    private void updateView(int itemIndex)
//    {
////得到第1个可显示控件的位置,记住是第1个可显示控件噢。而不是第1个控件
//        int visiblePosition = listView.getFirstVisiblePosition();
////得到你需要更新item的View
//        View view = listView.getChildAt(itemIndex - visiblePosition);
//        ThreadsMessage threadsMessage=threadsMessageList.get(itemIndex);
//        final String[][] bitmap=threadsMessage.getBitmap();
//        if(bitmap!=null)
//        {
//            ViewHolder viewHolder=new ViewHolder(view);
//            viewHolder.author.setText(threadsMessage.getAuthor());
//            viewHolder.dblastpost.setText(TimeUtils.times(Integer.parseInt(threadsMessage.getDblastpost())*1000L));
//            viewHolder.replies.setText(threadsMessage.getReplies());
//            viewHolder.subject1.setText(threadsMessage.getSubject());
//            viewHolder.views.setText(threadsMessage.getViews());
//            viewHolder.user_img.setImageBitmap(PicUtils.convertStringToIcon(threadsMessage.getBitmap()[0][2]));
//        }
//    }




    //    public ThreadAdapter(List<ThreadsMessage> threadsMessageList,ListView listView,Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
//        super(context, data, resource, from, to);
//        this.listView=listView;
//        this.threadsMessageList=threadsMessageList;
//    }
//
//    public static List<Map<String, Object>> getData(List<ThreadsMessage> threadsMessages){
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        Map<String, Object> map = new HashMap<String, Object>();
//        for (int i=0;i<threadsMessages.size();i++){
//
//            map = new HashMap<String, Object>();
//            map.put("author", threadsMessages.get(i).getAuthor());
//            map.put("dblastpost", TimeUtils.times((long)Integer.parseInt(threadsMessages.get(i).getDblastpost())*1000L));
//            map.put("subject",  threadsMessages.get(i).getSubject());
//            map.put("views",  threadsMessages.get(i).getViews());
//            map.put("replies",  threadsMessages.get(i).getReplies());
//            map.put("thread_user_img", PicUtils.convertStringToIcon(threadsMessages.get(i).getBitmap()[0][2]));
//            list.add(map);
//        }
//        return list;
//    }
}
