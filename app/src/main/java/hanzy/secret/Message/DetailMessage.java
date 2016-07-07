package hanzy.secret.Message;

import android.content.Context;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by h on 2016/7/7.
 */
public class DetailMessage{

    private String pid=null;
    private String tid=null;
    private String author=null;
    private String dbdateline=null;
    private String subject=null;
    private String message=null;

    public DetailMessage(String author,String dbdateline,String message,String tid,String pid){
        this.author=author;
        this.dbdateline=dbdateline;
        //this.subject=subject;
        this.message=message;
        this.tid=tid;
        this.pid=pid;
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
}
