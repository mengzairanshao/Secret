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
    public static int[] to={R.id.catalog_list};
    public static String[] from={"name"};
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
    public CatalogAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }

    public static List<Map<String, Object>> getData(List<CatalogMessage> catalogMessages){
        String key=null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i=0;i<catalogMessages.size();i++){
            map = new HashMap<String, Object>();
            map.put("name", catalogMessages.get(i).getName());
            list.add(map);
        }
        return list;
    }

    private  List<CatalogMessage> catalogMessages = new ArrayList<CatalogMessage>();
    public void set(List<CatalogMessage> catalogMessages){
        this.catalogMessages=catalogMessages;
        notifyDataSetChanged();
    }

    public List<CatalogMessage> getCatalogMessages() {
        return catalogMessages;
    }
}
