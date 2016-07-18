package hanzy.secret.Adapter;

import android.content.Context;
import android.util.Log;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hanzy.secret.Message.DetailMessage;
import hanzy.secret.Message.ThreadsMessage;
import hanzy.secret.R;
import hanzy.secret.utils.PicUtils;
import hanzy.secret.utils.TimeUtils;

/**
 * Created by h on 2016/7/7.
 */
public class DetailAdapter extends SimpleAdapter {
    public static int[] to = {R.id.detail_author, R.id.detail_posttime, R.id.detail_message, R.id.detail_image};
    public static String[] from = {"author", "dbdateline", "message", "image"};
    private String TAG = "DetailAdapter";

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
    public DetailAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }

    public static List<Map<String, Object>> getData(List<DetailMessage> detailMessages) {
        String TAG = "DetailAdapter";
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();

        for (int i = 0; i < detailMessages.size(); i++) {
            map = new HashMap<String, Object>();
            map.put("author", detailMessages.get(i).getAuthor());
            map.put("dbdateline", TimeUtils.times((long) Integer.parseInt(detailMessages.get(i).getDateline()) * 1000L));
            // map.put("subject",  detailMessages.get(i).getSubject());
            map.put("message", detailMessages.get(i).getMessage());
            if (detailMessages.get(i).getBitmap() != null) {
                for (int j = 0; j < detailMessages.get(i).getBitmap().length; j++) {
                    map.put("image", PicUtils.convertStringToIcon(detailMessages.get(i).getBitmap()[j][2]));
                }
            }
            if (i == 0) {
                list.add(map);
            } else {
                int location = 0;
                for (int k = 0; k < list.size(); k++) {
                    if (Integer.parseInt(TimeUtils.getTimestamp(map.get("dbdateline").toString(), "MM.dd HH:mm"))
                            >= Integer.parseInt(TimeUtils.getTimestamp(list.get(k).get("dbdateline").toString(), "MM.dd HH:mm"))) {
                        location = k + 1;
                    }
                }
                list.add(location, map);
            }
        }
        return list;
    }
}
