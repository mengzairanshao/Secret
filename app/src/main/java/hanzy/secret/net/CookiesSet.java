package hanzy.secret.net;
import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import org.apache.http.cookie.Cookie;
import java.util.ArrayList;
import java.util.List;

import hanzy.secret.utils.logUtils;

/**
 * Created by h on 2016/6/29.
 *
 */
public class CookiesSet {
    private static String TAG="GetCookies";
    public static String getCookieText(Context context) {
        PersistentCookieStore cookieStore=new PersistentCookieStore(context);
        List<Cookie> cookies = cookieStore.getCookies();
        logUtils.log(context,TAG, "cookies.size() = " + cookies.size());
        Utils.setCookies(cookies);
//        for (Cookie cookie : cookies) {
//            if(cookie.getName().contains("auth")) Value=cookie.getValue();
//        }
//        StringBuffer stringBuffer = new StringBuffer();
//        for (int i = 0; i < cookies.size(); i++) {
//            Cookie cookie = cookies.get(i);
//            String cookieName = cookie.getName();
//            String cookieValue = cookie.getValue();
//            if (!TextUtils.isEmpty(cookieName)
//                    && !TextUtils.isEmpty(cookieValue)) {
//                stringBuffer.append(cookieName + "=");
//                stringBuffer.append(cookieValue + ";");
//            }
//        }

//        logUtils.log(context,TAG, stringBuffer.toString());
        return "1";
    }

    public static class Utils {
        //cookies are only a copy of the cookies that get from Net.
        //the cookies can make it easy to us to check it
        private  static List<Cookie> cookies;
        //Get Current Cookies
        public static List<Cookie> getCookies() {
            return cookies != null ? cookies : new ArrayList<Cookie>();
        }
        //Copy the cookies get from the Net
        public  static void setCookies(List<Cookie> cookies) {
            Utils.cookies = cookies;
        }

        //clear the cookies that are on the local disk
        public static void clearCookies(Context context){
            PersistentCookieStore cookieStore=new PersistentCookieStore(context);
            cookieStore.clear();
        }

        //store the cookies on the local disk
        public static void saveCookies(AsyncHttpClient client, Context context) {
            PersistentCookieStore cookieStore=new PersistentCookieStore(context);
            client.setCookieStore(cookieStore);
        }

    }
}
