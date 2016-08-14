package hanzy.secret.utils;

import android.content.Context;
import android.util.Log;

import hanzy.secret.secret.Config;

/**
 * Created by h on 2016/8/14.
 */
public class logUtils {

    public static void log(String TAG, String msg) {
        if (Config.hashMapAll.get(TAG).equals("1")) {
            Log.e(TAG, msg);
        }
    }

    public static void log(Context context, String TAG, String msg,String level) {
        String contextStr = context.toString();
        contextStr = contextStr.substring(contextStr.lastIndexOf(".") + 1, contextStr.indexOf("@"));
        if (Config.hashMapContext.containsKey(contextStr)
                && Config.hashMapAll.containsKey(TAG)
                && Config.hashMapAll.get(TAG).equals("1")
                && Config.hashMapContext.get(contextStr).equals("1")) {
            contextStr = contextStr + ": ";
            switch (level) {
                case "e":
                    Log.e(TAG, contextStr + msg);break;
                case "d":
                    Log.d(TAG, contextStr + msg);break;
            }
        }
    }

    public static void log(Context context, String TAG, String msg) {
        String contextStr = context.toString();
        contextStr = contextStr.substring(contextStr.lastIndexOf(".") + 1, contextStr.indexOf("@"));
        //Log.e(TAG,contextStr);
        if (Config.hashMapContext.containsKey(contextStr)
                && Config.hashMapAll.containsKey(TAG)
                && Config.hashMapAll.get(TAG).equals("1")
                && Config.hashMapContext.get(contextStr).equals("1")) {
            contextStr = contextStr + ": ";
            Log.e(TAG, contextStr + msg);
        }
    }
}
