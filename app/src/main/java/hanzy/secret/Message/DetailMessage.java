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
    private String fid=null;
    private String tid=null;
    private String author=null;
    private String dblastpost=null;
    private String subject=null;
    private String message=null;

    public DetailMessage(String author,String dblastpost,String subject,String message,String tid,String fid,String pid){
        this.author=author;
        this.dblastpost=dblastpost;
        this.subject=subject;
        this.message=message;
        this.tid=tid;
        this.fid=fid;
        this.pid=pid;
    }

    public String getAuthor() {
        return author;
    }

    public String getDblastpost() {
        return dblastpost;
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

    public String getFid() {
        return fid;
    }

    public String getPid() {
        return pid;
    }
}
