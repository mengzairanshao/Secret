package hanzy.secret.secret;

import android.content.Context;
import android.content.SharedPreferences.Editor;

/**
 * Created by h on 2016/6/28.
 */
public class Config {

    public static final String KEY_TOKEN="token";
    public static final String APP_ID="hanzy.secret";
    public static final String CHARSET ="utf-8" ;

    public static String getCachedToken(Context context){
        return context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).getString(KEY_TOKEN,null);

    }
    public static void cachedToken(Context context,String token){
        Editor editor=context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).edit();
        editor.putString(KEY_TOKEN,token);
        editor.commit();
    }
}
