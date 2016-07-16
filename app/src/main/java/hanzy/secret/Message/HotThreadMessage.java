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
    private Bitmap image;
    private String tid;
    private String fid;
    private String author;
    private String subject;
    private String views;
    private String replies;
    private String dbdateline;
    private String dblastpost;


    public HotThreadMessage(HashMap<String,String> hashMap,Bitmap bitmap) {
        this.author = hashMap.get("author");
        this.dbdateline = hashMap.get("dbdateline");
        this.dblastpost = hashMap.get("dblastpost");
        this.fid = hashMap.get("fid");
        this.replies = hashMap.get("replies");
        this.subject = hashMap.get("subject");
        this.tid = hashMap.get("tid");
        this.views = hashMap.get("views");
        this.image=bitmap;
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
