package hanzy.secret.Message;


/**
 * Created by h on 2016/7/6.
 */
public class ThreadsMessage {
    private String author=null;
    private String dblastpost=null;
    private String subject=null;
    private String views=null;
    private String replies=null;

    public ThreadsMessage(String author,String dblastpost,String subject,String views,String replies){
        this.author=author;
        this.dblastpost=dblastpost;
        this.subject=subject;
        this.views=views;
        this.replies=replies;
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

}
