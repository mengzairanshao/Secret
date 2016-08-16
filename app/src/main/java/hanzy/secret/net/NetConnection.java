package hanzy.secret.net;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import hanzy.secret.aty.AtyLogin;
import hanzy.secret.utils.PicUtils;
import hanzy.secret.utils.logUtils;


/**
 * Created by h on 2016/6/28.
 *
 */
public class NetConnection {
    private static Bitmap bitmap = null;
    private static ConnectivityManager connectivityManager;
    private final String TAG = "NetConnection";

    public NetConnection(final Context context, final String url, final HttpMethod httpMethod, final SuccessCallback successCallback, final FailCallback failCallback, final String... kvs) {

        if (httpMethod.equals(HttpMethod.POST)) {
            AsyncHttpClient client;
            RequestParams params = new RequestParams();
            //保存cookie，自动保存到了sharePreference
            Activity activity = (Activity) context;
            if (activity instanceof AtyLogin) {
                client = new AsyncHttpClient();
                CookiesSet.Utils.clearCookies(context);
                CookiesSet.Utils.saveCookies(client, context);
            } else {
                FinalAsyncHttpClient finalAsyncHttpClient = new FinalAsyncHttpClient();
                client = finalAsyncHttpClient.getAsyncHttpClient();
            }
            for (int i = 0; i < kvs.length; i += 2) {
                params.put(kvs[i], kvs[i + 1]);
            }
            client.post(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String json = new String(responseBody);
                    logUtils.log(context, TAG, "POST成功");
                    if (successCallback != null) successCallback.onSuccess(json);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    logUtils.log(context, TAG, "POST失败");
                    if (failCallback != null) failCallback.onFail();
                }
            });
        }
        if (httpMethod.equals(HttpMethod.GET)) {
            FinalAsyncHttpClient finalAsyncHttpClient = new FinalAsyncHttpClient();
            AsyncHttpClient client = finalAsyncHttpClient.getAsyncHttpClient();
            RequestParams params = new RequestParams();
            for (int i = 0; i < kvs.length; i += 2) {
                params.put(kvs[i], kvs[i + 1]);
            }

            client.get(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (statusCode == 200) {
                        String json = new String(responseBody);
                        Boolean hasPic = false;
                        for (Header header : headers) {
                            if (header.getName().equals("Content-Type") && header.getValue().contains("image")) {
                                hasPic = true;
                                bitmap = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);
                                json = PicUtils.convertIconToString(bitmap);
                                logUtils.log(context, TAG, "GET连接成功(获取图片)");
                            }
                        }
                        if (!hasPic) {
                            logUtils.log(context, TAG, "GET连接成功");
                        }
                        if (successCallback != null) successCallback.onSuccess(json);
                    } else {
                        logUtils.log(context, TAG, "statusCode==" + statusCode);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    logUtils.log(context, TAG, "statusCode==" + statusCode + "  GET连接失败");
                    if (failCallback != null) failCallback.onFail();
                }
            });
        }
    }

    @NonNull
    public static String isMobileNetworkAvailable(Context context) {
        if (null == connectivityManager) {
            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo.isConnected()) {
            return "wifi";
        } else if (mobileInfo.isConnected()) {
            return "mobile";
        } else {
            return "none";
        }
    }

    public interface SuccessCallback {
        void onSuccess(String result);
    }

    public interface FailCallback {
        void onFail();
    }

}
