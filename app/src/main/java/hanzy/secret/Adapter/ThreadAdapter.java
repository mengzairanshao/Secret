package hanzy.secret.Adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hanzy.secret.Message.ThreadsMessage;
import hanzy.secret.R;
import hanzy.secret.utils.TimeUtils;

/**
 * Created by h on 2016/7/6.
 */
public class ThreadAdapter {
    public List<Map<String, Object>> getData(List<ThreadsMessage> threadsMessages){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i=0;i<threadsMessages.size();i++){

            map = new HashMap<String, Object>();
            map.put("author", threadsMessages.get(i).getAuthor());
            map.put("dblastpost", TimeUtils.times(Integer.getInteger(threadsMessages.get(i).getDblastpost())));
            map.put("subject",  threadsMessages.get(i).getSubject());
            map.put("views",  threadsMessages.get(i).getViews());
            map.put("replies",  threadsMessages.get(i).getReplies());
            list.add(map);
        }
        return list;
    }
}
