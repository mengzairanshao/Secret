package hanzy.secret.Message;

import android.content.Context;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by h on 2016/7/7.
 */
public class DetailMessage{

    private String[][] bitmap=null;
    private String pid=null;
    private String tid=null;
    private String author=null;
    private String dbdateline=null;
    private String subject=null;
    private String message=null;

    public DetailMessage(HashMap<String,Object> data){
        this.author=data.get("author").toString();
        this.dbdateline=data.get("dbdateline").toString();
        this.message=data.get("message").toString();
        this.tid=data.get("tid").toString();
        this.pid=data.get("pid").toString();
        if (data.containsKey("bitmap")) this.bitmap=(String[][]) data.get("bitmap");
    }


    public String getAuthor() {
        return author;
    }

    public String getDateline() {
        return dbdateline;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public String getTid() {
        return tid;
    }

    public String getPid() {
        return pid;
    }

    public String[][] getBitmap() {
        return bitmap;
    }

    public void setBitmap(int position ,String bitmap) {
        this.bitmap[position][2] = bitmap;
    }
}
