package hanzy.secret.net;
import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.util.TextUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by h on 2016/6/29.
 */
public class CookiesSet {
    private static String TAG="GetCookies";

    public static String getCookieText(Context context) {
        PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
        List<Cookie> cookies = myCookieStore.getCookies();
        String Value="";
        Log.d(TAG, "cookies.size() = " + cookies.size());
        Utils.setCookies(cookies);
        for (Cookie cookie : cookies) {
            if(cookie.getName().contains("auth")){ Value=cookie.getValue();
            Log.e(TAG,"Value"+Value);}
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < cookies.size(); i++) {
            Cookie cookie = cookies.get(i);
            String cookieName = cookie.getName();
            String cookieValue = cookie.getValue();
            if (!TextUtils.isEmpty(cookieName)
                    && !TextUtils.isEmpty(cookieValue)) {
                stringBuffer.append(cookieName + "=");
                stringBuffer.append(cookieValue + ";");
            }
        }

        Log.e("cookie", stringBuffer.toString());
        return Value;
    }

    public static class Utils {
        private  static List<Cookie> cookies;
        public static List<Cookie> getCookies() {
            return cookies != null ? cookies : new ArrayList<Cookie>();
        }
        public  static void setCookies(List<Cookie> cookies) {
            Utils.cookies = cookies;
        }

        public static void clearCookies(Context context){
            PersistentCookieStore cookieStore = new PersistentCookieStore(context);
            cookieStore.clear();
        }

        public static void saveCookies(AsyncHttpClient client, Context context) {
            PersistentCookieStore cookieStore = new PersistentCookieStore(context);
            client.setCookieStore(cookieStore);
        }

    }
}
