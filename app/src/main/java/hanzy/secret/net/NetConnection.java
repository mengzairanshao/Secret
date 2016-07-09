package hanzy.secret.net;

import android.content.Context;
import android.util.Log;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by h on 2016/6/28.
 */
public class NetConnection {
    private final String TAG = "NetConnection";

    public NetConnection(final Context context, final String url, final HttpMethod httpMethod, final SuccessCallback successCallback, final FailCallback failCallback, final String... kvs) {

        if (httpMethod.equals(HttpMethod.POST)){
            final AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params=new RequestParams();
            //保存cookie，自动保存到了shareprefercece
            CookiesSet.Utils.clearCookies(context);
            CookiesSet.Utils.saveCookies(client,context);
            for (int i=0;i<kvs.length;i+=2){
                params.put(kvs[i],kvs[i+1]);
            }

            //Log.e(TAG,params.toString());
            client.post(url,params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String json=new String(responseBody);
                    Log.e(TAG,"POST成功"+json);
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

                    Log.e(TAG,"GET连接成功"+responseBody.length);
                    String json=new String(responseBody);
                    Log.e(TAG,"GET连接成功"+json);
                    if (successCallback!=null)successCallback.onSuccess(json);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.e(TAG,"GET连接失败");
                    if (failCallback!=null)failCallback.onFail();
                }
            });
        }
    }

        public static interface SuccessCallback {
            void onSuccess(String result);
        }

        public static interface FailCallback {
            void onFail();
        }

}

