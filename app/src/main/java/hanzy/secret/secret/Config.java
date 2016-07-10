package hanzy.secret.secret;

import android.content.Context;
import android.content.SharedPreferences.Editor;

/**
 * Created by h on 2016/6/28.
 */
public class Config {

    public static final String Base_URL="http://192.168.23.1/api/mobile/index.php";
    public static final String Login_URL=Base_URL+"?version=4&module=login&action=login&loginsubmit=yes&infloat=yes&lssubmit=yes";
    public static final String UERNAME="username";
    public static final String PASSWORD="password";
    public static final String ACTION="action";
    public static final String APP_ID="hanzy.secret";
    public static final String CHARSET ="utf-8" ;
    public static final String SUCCEED_LOGIN="succeed login";
    public static final String FAILED_LOGIN="failed login";
    public static final String MOBILE="mobile";
    public static final String MOBILE_IS="no";
    public static final String MODULE="module";
    public static final String MODULE_FORUMINDEX="forumindex";
    public static final String VERSION="version";
    public static final String VERSION_NUM = "4";

}
