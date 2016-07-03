package hanzy.secret.net;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by h on 2016/7/3.
 */
public class Message {

    private String fid=null;
    private String name=null;
    private String forumlist[]=null;

    public Message(String fid,String catlist,String[] forumlist){
        this.name=catlist;
        this.forumlist=forumlist;
    }

    public String getFid() {
        return fid;
    }

    public String getName() {
        return name;
    }

    public String[] getForumlist() {
        return forumlist;
    }

}
