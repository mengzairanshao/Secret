package hanzy.secret.Message;

import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by h on 2016/7/9.
 */
public class HotThreadMessage {

    private String TAG="HotThreadMessage";
    private String[][] bitmap;
    private String tid;
    private String fid;
    private String author;
    private String subject;
    private String views;
    private String replies;
    private String dbdateline;
    private String dblastpost;


    public HotThreadMessage(HashMap<String,Object> hashMap) {
        this.author = hashMap.get("author").toString();
        this.dbdateline = hashMap.get("dbdateline").toString();
        this.dblastpost = hashMap.get("dblastpost").toString();
        this.fid = hashMap.get("fid").toString();
        this.replies = hashMap.get("replies").toString();
        this.subject = hashMap.get("subject").toString();
        this.tid = hashMap.get("tid").toString();
        this.views = hashMap.get("views").toString();
        this.bitmap=(String[][]) hashMap.get("bitmap");
    }
    public String getAuthor() {
        return author;
    }

    public String getDbdateline() {
        return dbdateline;
    }

    public String getDblastpost() {
        return dblastpost;
    }

    public String getFid() {
        return fid;
    }

    public String getReplies() {
        return replies;
    }

    public String getSubject() {
        return subject;
    }

    public String getTid() {
        return tid;
    }

    public String getViews() {
        return views;
    }

    public String[][] getBitmap() {
        return bitmap;
    }

    public void setBitmap(int position, String string) {
        this.bitmap[position][2] = string;
    }
}
