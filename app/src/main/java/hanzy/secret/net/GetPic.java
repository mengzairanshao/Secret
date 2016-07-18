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
    public GetPic(Context context, String uid, String size, final SuccessCallback successCallback, FailCallback failCallback){
       new NetConnection(context, Config.PIC_URL, HttpMethod.GET, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                if (successCallback!=null)
                    successCallback.onSuccess(result);
                bitmap=PicUtils.convertStringToIcon(result);
                if (GetPic.this.bitmap==null){
                    Log.e(TAG,"Bitmap==null");
                }else {
                    Log.e(TAG,"Bitmap!=null");
                }
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {

            }
        },"uid",uid,"size",size);
    }

    public GetPic(Context context,String url, final SuccessCallback successCallback, FailCallback failCallback) {
        new NetConnection(context, url, HttpMethod.GET, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                if (successCallback!=null){
                    successCallback.onSuccess(result);
                }
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {

            }
        });
    }


    public static interface SuccessCallback {
        void onSuccess(String result);
    }

    public static interface FailCallback {
        void onFail();
    }
}
