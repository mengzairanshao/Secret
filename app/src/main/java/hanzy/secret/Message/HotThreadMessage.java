package hanzy.secret.Message;

import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;

/**
 * Created by h on 2016/7/9.
 */
public class HotThreadMessage {

    private String TAG="HotThreadMessage";
    private Bitmap image;
    private String tid;
    private String fid;
    private String author;
    private String subject;
    private String views;
    private String replies;
    private String dbdateline;
    private String dblastpost;


    public HotThreadMessage(String author, String dbdataline, String dblastpost, String fid, String replies, String subject, String tid, String views) {
        this.author = author;
        this.dbdateline = dbdataline;
        this.dblastpost = dblastpost;
        this.fid = fid;
        this.replies = replies;
        this.subject = subject;
        this.tid = tid;
        this.views = views;
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

    public Bitmap getImage() {
        if (image==null)
            Log.e(TAG,"image==null");
        else
            Log.e(TAG,"image!=null"+image+"tid=="+tid);
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
