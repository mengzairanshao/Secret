package hanzy.secret.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hanzy.secret.R;
import hanzy.secret.Message.CatalogMessage;
import hanzy.secret.utils.TimeUtils;

/**
 * Created by h on 2016/7/3.
 */
public class CatalogAdapter extends SimpleAdapter{

    public String TAG="CatalogAdapter";
    public static int[] to={R.id.thread_author,R.id.thread_posttime,R.id.thread_title,R.id.thread_views,R.id.thread_replies};
    public static String[] from={"author","dblastpost","subject","views","replies"};
    /**
     * Constructor
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
    public CatalogAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }

    public static List<Map<String, Object>> getData(List<CatalogMessage> catalogMessages){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i=0;i<catalogMessages.size();i++){

//            map = new HashMap<String, Object>();
//            map.put("author", catalogMessages.get(i).getAuthor());
//            map.put("dblastpost", TimeUtils.times((long)Integer.parseInt(catalogMessages.get(i).getDblastpost())*1000L));
//            map.put("subject",  catalogMessages.get(i).getSubject());
//            map.put("views",  catalogMessages.get(i).getViews());
//            map.put("replies",  catalogMessages.get(i).getReplies());
//            list.add(map);
        }
        return list;
    }
}
