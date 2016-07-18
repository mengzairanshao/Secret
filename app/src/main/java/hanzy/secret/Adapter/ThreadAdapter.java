package hanzy.secret.Adapter;

import android.content.Context;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hanzy.secret.Message.ThreadsMessage;
import hanzy.secret.R;
import hanzy.secret.utils.PicUtils;
import hanzy.secret.utils.TimeUtils;


/**
 * Created by h on 2016/7/6.
 */
public class ThreadAdapter extends SimpleAdapter{
    public String TAG="ThreadAdapter";
    public static int[] to={R.id.thread_author,R.id.thread_posttime,R.id.thread_title,R.id.thread_views,R.id.thread_replies,R.id.hot_thread_user_img};
    public static String[] from={"author","dblastpost","subject","views","replies","hot_thread_user_img"};

    public ThreadAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }

    public static List<Map<String, Object>> getData(List<ThreadsMessage> threadsMessages){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i=0;i<threadsMessages.size();i++){

            map = new HashMap<String, Object>();
            map.put("author", threadsMessages.get(i).getAuthor());
            map.put("dblastpost", TimeUtils.times((long)Integer.parseInt(threadsMessages.get(i).getDblastpost())*1000L));
            map.put("subject",  threadsMessages.get(i).getSubject());
            map.put("views",  threadsMessages.get(i).getViews());
            map.put("replies",  threadsMessages.get(i).getReplies());
            map.put("hot_thread_user_img", PicUtils.convertStringToIcon(threadsMessages.get(i).getBitmap()[0][2]));
            list.add(map);
//            int[] locationList=new int[threadsMessages.size()];
//            if (i == 0) {
//                list.add(map);
//            } else {
//                int location = 0;
//                for (int k = 0; k < list.size(); k++) {
//                    if (Integer.parseInt(TimeUtils.getTimestamp(list.get(k).get("dblastpost").toString(), "MM.dd HH:mm"))
//                            >=Integer.parseInt(TimeUtils.getTimestamp(map.get("dblastpost").toString(), "MM.dd HH:mm"))) {
//                        location = k + 1;
//                    }
//                }
//                list.add(location,map);
//            }
        }
        return list;
    }
}
