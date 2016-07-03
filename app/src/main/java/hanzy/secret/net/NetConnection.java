package hanzy.secret.net;

import android.content.Context;
import android.util.Log;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

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
                    Log.e(TAG,"POST成功"+responseBody);
                    if (successCallback!=null)successCallback.onSuccess(new String(responseBody));
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.e(TAG,"POST失败"+responseBody);
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
                    CookiesSet.getCookieText(context);
                    String json=new String(responseBody);
                    Log.e(TAG,"GET连接成功"+json);
                    if (successCallback!=null)successCallback.onSuccess(json);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    String json=new String(responseBody);
                    Log.e(TAG,"GET连接失败"+json);
                    if (failCallback!=null)failCallback.onFail();
                }
            });
        }

//        new AsyncTask<Void,Void,String>(){
//            @Override
//            protected String doInBackground(Void... params) {
//
//                StringBuffer paramStr=new StringBuffer();
//                for (int i=0;i<kvs.length;i+=2){
//                    paramStr.append(kvs[i]).append("=").append(kvs[i+1]).append("&");
//                }
//                try {
//                    URLConnection uc;
//
//                    switch (httpMethod){
//                        case POST:
//
//                            uc=new URL(url).openConnection();
//                            uc.setDoOutput(true);
//                            Log.e(TAG,uc.getOutputStream().toString());
//                            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(uc.getOutputStream(), Config.CHARSET));
//                            bufferedWriter.write(paramStr.toString());
//                            bufferedWriter.flush();
//
//                            break;
//                        default:
//                            uc=new URL(url+"?"+paramStr.toString()).openConnection();
//                            break;
//                    }
//
//                    System.out.println("Request url:"+uc.getURL());
//                    System.out.println("Request data:"+paramStr);
//                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(uc.getInputStream(),Config.CHARSET));
//                    Log.e(TAG,uc.getInputStream().toString());
//
//                    String line=null;
//                    StringBuffer result=new StringBuffer();
//                    while ((line=bufferedReader.readLine())!=null){
//                        result.append(line);
//                    }
//
//                    System.out.println("Result :"+result);
//                    return  result.toString();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                if (s!=null){
//                    if (successCallback!=null){
//                        successCallback.onSuccess(s);
//                    }
//                }else{
//                    if (failCallback!=null){
//                        failCallback.onFail();
//                    }
//                }
//                super.onPostExecute(s);
//
//            }
//        }.execute();
    }

        public static interface SuccessCallback {
            void onSuccess(String result);
        }

        public static interface FailCallback {
            void onFail();
        }

}

