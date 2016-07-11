package hanzy.secret.Adapter;

import android.content.Context;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hanzy.secret.Message.HotThreadMessage;
import hanzy.secret.R;

/**
 * Created by h on 2016/7/11.
 */
public class HotThreadAdapter extends SimpleAdapter{

    public String TAG="HotThreadAdapter";
    public static int[] to={R.id.hot_thread_author,R.id.hot_thread_subject,R.id.hot_thread_views,R.id.hot_thread_replies,R.id.hot_thread_dblastpost};
    public static String[] from={"author","subject","views","replies","dblastpost"};
    /**
     *
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     */
    public HotThreadAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }

    public static List<Map<String, Object>> getData(List<HotThreadMessage> hotThreadMessages){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i=0;i<hotThreadMessages.size();i++){
            map = new HashMap<String, Object>();
            map.put("author",hotThreadMessages.get(i).getAuthor());
            map.put("subject",hotThreadMessages.get(i).getSubject());
            map.put("views", hotThreadMessages.get(i).getViews());
            map.put("replies", hotThreadMessages.get(i).getReplies());
            map.put("dblastpost", hotThreadMessages.get(i).getDblastpost());
            list.add(map);
        }
        return list;
    }

    private  List<HotThreadMessage> hotThreadMessages = new ArrayList<HotThreadMessage>();
    public void set(List<HotThreadMessage> hotThreadMessages){
        this.hotThreadMessages=hotThreadMessages;
        notifyDataSetChanged();
    }

    public List<HotThreadMessage> getHotThreadMessages() {
        return hotThreadMessages;
    }
}
