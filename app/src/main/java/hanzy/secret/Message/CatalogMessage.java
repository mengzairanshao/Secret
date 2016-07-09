package hanzy.secret.Message;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;

/**
 * Created by h on 2016/7/3.
 */
public class CatalogMessage {

    private String fid=null;
    private String name=null;
    private String forumlist[]=null;
    private HashMap<String,String> formlistItem=null;

    public CatalogMessage(String fid, String name, String[] forumlist,HashMap<String,String> formlistItem){
        this.name=name;
        this.forumlist=forumlist;
        this.fid=fid;
        this.formlistItem=formlistItem;
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

    public HashMap<String, String> getFormlistItem() {
        return formlistItem;
    }
}
