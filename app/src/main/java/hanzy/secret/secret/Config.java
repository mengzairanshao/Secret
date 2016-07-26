package hanzy.secret.secret;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;

/**
 * Created by h on 2016/6/28.
 */
public class Config {

    public static final String NET_URL ="http://192.168.23.1/";
    public static final String BASE_URL = NET_URL +"api/mobile/index.php";
    public static final String PIC_URL= NET_URL +"uc_server/avatar.php";
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

    public static final String FORM_HASH = "";

    public static final int SPANNED_MESSAGE=2;
    public static final int USER_LOAD_IMAGE=1;

    public static final String BITMAP=null;
    public static final String AUTHORID=null;

    public static String getCachedDATA(Context context,String string){
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(string, null);
    }

    public static void cacheDATA(Context context,String data,String string){
        Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(string, data);
        e.commit();
    }
}
