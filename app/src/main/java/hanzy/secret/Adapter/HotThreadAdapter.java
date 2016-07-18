package hanzy.secret.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
        ViewHolder viewHolder=null;
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
        viewHolder.user_img.setImageBitmap(PicUtils.convertStringToIcon(hotThreadMessage.getBitmap()));
        return convertView;
    }

    public Context getContext() {
        return context;
    }

    public void addAll(List<HotThreadMessage> hotThreadMessages){
        this.hotThreadMessages.addAll(hotThreadMessages);
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
//    /**
//     *
//     *
//     * @param context  The context where the View associated with this SimpleAdapter is running
//     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
//     *                 Maps contain the data for each row, and should include all the entries specified in
//     *                 "from"
//     * @param resource Resource identifier of a view layout that defines the views for this list
//     *                 item. The layout file should include at least those named views defined in "to"
//     * @param from     A list of column names that will be added to the Map associated with each
//     *                 item.
//     * @param to       The views that should display column in the "from" parameter. These should all be
//     *                 TextViews. The first N views in this list are given the values of the first N columns
//     */
//    public HotThreadAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
//        super(context, data, resource, from, to);
//    }
//
//
//    public static List<Map<String, Object>> GetData(){
//        return list;
//    }
//
//    public static List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//    public void setData(){
//        Map<String, Object> map = new HashMap<String, Object>();
//        for (int i=0;i<hotThreadMessages.size();i++){
//            map = new HashMap<String, Object>();
//            map.put("author",hotThreadMessages.get(i).getAuthor());
//            map.put("subject",hotThreadMessages.get(i).getSubject());
//            map.put("views", hotThreadMessages.get(i).getViews());
//            map.put("replies", hotThreadMessages.get(i).getReplies());
//            map.put("dblastpost", TimeUtils.times(Integer.parseInt(hotThreadMessages.get(i).getDblastpost())*1000L));
//            map.put("hot_thread_user_img",hotThreadMessages.get(i).getImage());
//            list.add(map);
//            notifyDataSetChanged();
//        }
//        //return list;
//    }
//
//    private  List<HotThreadMessage> hotThreadMessages = new ArrayList<HotThreadMessage>();
//    public int set(List<HotThreadMessage> hotThreadMessages){
//        this.hotThreadMessages=hotThreadMessages;
//        setData();
//        return 1;
//    }
//
//    public List<HotThreadMessage> getHotThreadMessages() {
//        return hotThreadMessages;
//    }


}
