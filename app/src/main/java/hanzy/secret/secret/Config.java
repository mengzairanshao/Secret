package hanzy.secret.secret;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import java.util.HashMap;

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
    public static final String KEY_VERSION ="version";
    public static final String KEY_TID="tid";
    public static final String KEY_PAGE="page";
    public static final String KEY_PAGE_SIZE="ppp";

    public static final String VALUE_MODULE_FORUMINDEX ="forumindex";
    public static final String VALUE_MOBILE_IS ="no";
    public static final String VALUE_VERSION_NUM = "4";
    public static final String VALUE_USER_HEADER_IMAGE_SIZE ="small";
    public static final String VALUE_PAGE_SIZE ="20";

    public static final int SPANNED_MESSAGE=2;
    public static final int USER_LOAD_IMAGE=1;

    public static final String USER_HEADER_IMAGE="USER_HEADER_IMAGE";
    public static final String FORM_HASH = "FORM_HASH";
    public static final String AUTHOR_ID ="AUTHOR_ID";
    public static final String IS_LOGIN ="IS_LOGIN";


    public static HashMap<String,String> hashMapAll =new HashMap<String,String>(){
        {
            put("GetDetail","1");
            put("GetCatalog","1");
            put("GetHotThread","1");
            put("GetPic","1");
            put("GetThread","1");
            put("GetCookies","1");
            put("SendComment","1");
            put("Login","1");
            put("Logout","1");
            put("NetConnection","1");

            put("DetailAdapter","1");
            put("CatalogAdapter","1");
            put("HotThreadAdapter","1");
            put("ThreadAdapter","1");

            put("ProfileFragment","1");
            put("HotThreadsFragment","1");
            put("CatalogFragment","1");
        }
    };

    public static HashMap<String,String> hashMapContext =new HashMap<String,String>(){
        {
            put("AtyDetail","1");
            put("MainActivity","1");
            put("AtyCatalog","1");
            put("AtyForums","1");
            put("AtyLogin","1");
            put("StartingActivity","1");
            put("AtyNoDisplay","1");
            put("AtyThreads","1");
        }
    };

    public static String getCachedDATA(Context context,String key){
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(key, null);
    }

    public static void cacheDATA(Context context,String data,String key){
        Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(key, data);
        e.commit();
    }

    public static void InitDATA(Context context){
        Config.cacheDATA(context,"Logout_succeed",Config.IS_LOGIN);
    }

    public static void Init(Context context){
        if (getCachedDATA(context,Config.IS_LOGIN)==null){
            InitDATA(context);
        }
        hashMapAll.putAll(hashMapContext);
    }
}
