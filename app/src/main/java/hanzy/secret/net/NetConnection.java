package hanzy.secret.net;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.Log;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import org.json.JSONObject;

import hanzy.secret.aty.AtyLogin;
import hanzy.secret.utils.PicUtils;


/**
 * Created by h on 2016/6/28.
 */
public class NetConnection {
    private final String TAG = "NetConnection";
    private static Bitmap bitmap=null;
    public NetConnection(final Context context, final String url, final HttpMethod httpMethod, final SuccessCallback successCallback, final FailCallback failCallback, final String... kvs) {

        if (httpMethod.equals(HttpMethod.POST)){
            AsyncHttpClient client;
            RequestParams params=new RequestParams();
            //保存cookie，自动保存到了shareprefercece
            Activity activity=(Activity)context;
            if (activity instanceof AtyLogin){
                client = new AsyncHttpClient();
                CookiesSet.Utils.clearCookies(context);
                CookiesSet.Utils.saveCookies(client,context);
            }else {
                FinalAsyncHttpClient finalAsyncHttpClient=new FinalAsyncHttpClient();
                client=finalAsyncHttpClient.getAsyncHttpClient();
            }
            for (int i=0;i<kvs.length;i+=2){
                params.put(kvs[i],kvs[i+1]);
            }
            client.post(url,params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.e(TAG,"POST成功");
                    String json=new String(responseBody);
                    if (successCallback!=null)successCallback.onSuccess(json);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.e(TAG,"POST失败");
                    if (failCallback!=null)failCallback.onFail();
                }
            });
        }
        if(httpMethod.equals(HttpMethod.GET)) {
            FinalAsyncHttpClient finalAsyncHttpClient=new FinalAsyncHttpClient();
            AsyncHttpClient client=finalAsyncHttpClient.getAsyncHttpClient();
            RequestParams params=new RequestParams();
            for (int i=0;i<kvs.length;i+=2){
                    params.put(kvs[i],kvs[i+1]);
                }

            client.get(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (statusCode==200){
                        String json=new String(responseBody);
                        for(int i=0;i<headers.length;i++) {
                            if (headers[i].getName().equals("Content-Type")&&headers[i].getValue().contains("image")) {
                                bitmap=BitmapFactory.decodeByteArray(responseBody,0,responseBody.length);
                                json= PicUtils.convertIconToString(bitmap);
                            }
                        }
                        Log.e(TAG,"GET连接成功");
                        if (successCallback!=null)successCallback.onSuccess(json);
                    }else {
                        Log.e(TAG,"statusCode=="+statusCode);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.e(TAG,"statusCode=="+statusCode);
                    Log.e(TAG,"GET连接失败");
                    if (failCallback!=null)failCallback.onFail();
                }
            });
        }
    }

        public interface SuccessCallback {
            void onSuccess(String result);
        }

        public interface FailCallback {
            void onFail();
        }

    private static ConnectivityManager connectivityManager;

    @NonNull
    public static String isMobileNetworkAvailable(Context context){
        if (null==connectivityManager){
            connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        NetworkInfo wifiInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo.isConnected()){
            return "wifi";
        }else if (mobileInfo.isConnected()){
            return "mobile";
        }else{
            return "none";
        }
    }

}
