package hanzy.secret.Message;


import android.graphics.Bitmap;

import java.util.HashMap;

/**
 * Created by h on 2016/7/6.
 */
public class ThreadsMessage {
    private String[][] bitmap;
    private String tid=null;
    private String author=null;
    private String dblastpost=null;
    private String subject=null;
    private String views=null;
    private String replies=null;
    private String authorid=null;

    public ThreadsMessage(HashMap<String,Object> hashMap){
        this.author=hashMap.get("author").toString();
        this.dblastpost=hashMap.get("dblastpost").toString();
        this.subject=hashMap.get("subject").toString();
        this.views=hashMap.get("views").toString();
        this.replies=hashMap.get("replies").toString();
        this.tid=hashMap.get("tid").toString();
        this.bitmap=(String[][]) hashMap.get("bitmap");
        this.authorid=hashMap.get("authorid").toString();
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

    public String[][] getBitmap() {
        return bitmap;
    }

    public String getAuthorid() {
        return authorid;
    }

    public void setBitmap(int position,String s){
        bitmap[position][2]=s;
    }
}
