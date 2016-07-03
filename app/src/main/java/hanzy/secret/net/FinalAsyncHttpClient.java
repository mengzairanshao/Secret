package hanzy.secret.net;

import com.loopj.android.http.AsyncHttpClient;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;

/**
 * Created by h on 2016/6/30.
 */
public class FinalAsyncHttpClient {

    AsyncHttpClient client;

    public FinalAsyncHttpClient() {
        client = new AsyncHttpClient();
        client.setConnectTimeout(5);//5s超时
        if (CookiesSet.Utils.getCookies() != null) {//每次请求都要带上cookie
            BasicCookieStore bcs = new BasicCookieStore();
            bcs.addCookies(CookiesSet.Utils.getCookies().toArray(new Cookie[CookiesSet.Utils.getCookies().size()]));
            client.setCookieStore(bcs);
        }
    }

    public AsyncHttpClient getAsyncHttpClient() {
        return this.client;
    }

}
