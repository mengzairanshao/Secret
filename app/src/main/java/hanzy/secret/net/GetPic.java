package hanzy.secret.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import hanzy.secret.secret.Config;
import hanzy.secret.utils.PicUtils;

/**
 * Created by h on 2016/7/11.
 */
public class GetPic {

    private Bitmap bitmap=null;
    private String TAG="GetPic";
    String[][] str=new String[1][3];
    public GetPic(Context context, final String uid, final String size, final SuccessCallback successCallback, FailCallback failCallback){
       new NetConnection(context, Config.PIC_URL, HttpMethod.GET, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                str[0][0]=uid;
                str[0][1]=Config.PIC_URL+"?uid="+uid+"&size="+size;
                str[0][2]=result;
                if (successCallback!=null)
                    successCallback.onSuccess(str);
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {

            }
        },"uid",uid,"size",size);
    }

    public GetPic(Context context, final String url, final SuccessCallback successCallback, FailCallback failCallback) {
        new NetConnection(context, url, HttpMethod.GET, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                str[0][0]="1";
                str[0][1]=url;
                str[0][2]=result;
                if (successCallback!=null){
                    successCallback.onSuccess(str);
                }
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {

            }
        });
    }

    public interface SuccessCallback {
        void onSuccess(Object result);
    }

    public interface FailCallback {
        void onFail();
    }
}
