package hanzy.secret.secret;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * Created by h on 2016/6/28.
 */
public class Config {

    public static final String SITE_URL ="http://192.168.23.1/";
    public static final String BASE_URL = SITE_URL +"api/mobile/index.php";
    public static final String PIC_URL= SITE_URL +"uc_server/avatar.php";
    public static final String LOGIN_URL = BASE_URL +"?version=4&module=login&action=login&loginsubmit=yes&infloat=yes&lssubmit=yes";
    public static final String CHARSET ="utf-8" ;
    public static final String APP_ID="hanzy.secret";

    public static final String KEY_UERNAME ="username";
    public static final String KEY_PASSWORD ="password";
    public static final String KEY_ACTION ="action";
    public static final String KEY_MOBILE ="mobile";
    public static final String KEY_MODULE ="module";
    public static final String KEY_MODULE_FORUMINDEX ="forumindex";
    public static final String KEY_VERSION ="version";

    public static final String VALUE_MOBILE_IS ="no";
    public static final String VALUE_VERSION_NUM = "4";

    public static final int SPANNED_MESSAGE=2;
    public static final int USER_LOAD_IMAGE=1;

    public static final String USER_HEADER_IMAGE="USER_HEADER_IMAGE";
    public static final String FORM_HASH = "FORM_HASH";
    public static final String AUTHOR_ID ="AUTHOR_ID";
    public static final String IS_LOGINED="IS_LOGINED";

    public static String NUM="0";

    public static String getCachedDATA(Context context,String key){
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(key, null);
    }

    public static void cacheDATA(Context context,String data,String key){
        Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(key, data);
        e.commit();
    }
}
