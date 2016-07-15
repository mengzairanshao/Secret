package hanzy.secret.Message;


import android.graphics.Bitmap;

/**
 * Created by h on 2016/7/6.
 */
public class ThreadsMessage {
    private Bitmap bitmap;
    private String tid=null;
    private String author=null;
    private String dblastpost=null;
    private String subject=null;
    private String views=null;
    private String replies=null;
    private String authorid=null;

    public ThreadsMessage(String author,String dblastpost,String subject,String views,String replies,String tid,String authorid,Bitmap bitmap){
        this.author=author;
        this.dblastpost=dblastpost;
        this.subject=subject;
        this.views=views;
        this.replies=replies;
        this.tid=tid;
        this.bitmap=bitmap;
        this.authorid=authorid;
    }

    public String getAuthor() {
        return author;
    }

    public String getDblastpost() {
        return dblastpost;
    }

    public String getReplies() {
        return replies;
    }

    public String getSubject() {
        return subject;
    }

    public String getViews() {
        return views;
    }

    public String getTid() {
        return tid;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getAuthorid() {
        return authorid;
    }
}
