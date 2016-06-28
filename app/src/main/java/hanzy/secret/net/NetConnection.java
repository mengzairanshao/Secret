package hanzy.secret.net;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import hanzy.secret.secret.Config;

/**
 * Created by h on 2016/6/28.
 */
public class NetConnection {
    public NetConnection(final String url,final HttpMethod httpMethod,final SuccessCallback successCallback,final FailCallback failCallback,final String...kvs){


        new AsyncTask<Void,Void,String>(){
            @Override
            protected String doInBackground(Void... params) {

                StringBuffer paramStr=new StringBuffer();
                for (int i=0;i<kvs.length;i+=2){
                    paramStr.append(kvs[i]).append("=").append(kvs[i+1]).append("&");
                }
                try {
                    URLConnection uc;

                    switch (httpMethod){
                        case POST:
                            uc=new URL(url).openConnection();
                            uc.setDoOutput(true);
                            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(uc.getOutputStream(), Config.CHARSET));
                            bufferedWriter.write(paramStr.toString());
                            break;
                        default:
                            uc=new URL(url+"?"+paramStr.toString()).openConnection();
                            break;
                    }

                    System.out.println("Request url:"+uc.getURL());
                    System.out.println("Request data:"+paramStr);
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(uc.getInputStream(),Config.CHARSET));
                    String line=null;
                    StringBuffer result=new StringBuffer();
                    while ((line=bufferedReader.readLine())!=null){
                        result.append(line);
                    }

                    System.out.println("Result :"+result);
                    return  result.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                if (s!=null){
                    if (successCallback!=null){
                        successCallback.onSuccess(s);
                    }
                }else{
                    if (failCallback!=null){
                        failCallback.onFail();
                    }
                }
                super.onPostExecute(s);

            }
        };
    }

    public static interface SuccessCallback{
        void onSuccess(String result);
    }

    public static interface FailCallback{
        void onFail();
    }
}
