package hanzy.secret.aty;

import android.app.Activity;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import hanzy.secret.net.GetPic;
import hanzy.secret.net.HttpMethod;
import hanzy.secret.net.NetConnection;
import hanzy.secret.secret.Config;

/**
 * Created by h on 2016/7/28.
 */
public class AtyNoDisplay extends Activity{

    private String TAG="AtyNoDisplay";
    private int num=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new NetConnection(AtyNoDisplay.this, Config.BASE_URL, HttpMethod.GET, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject1 = new JSONObject(result);
                    Config.cacheDATA(AtyNoDisplay.this, jsonObject1.getJSONObject("Variables").getString("formhash"), Config.FORM_HASH);
                    if (--num==0){
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {

            }
        }, Config.KEY_VERSION, "4", Config.KEY_MODULE, "checkpost");

        new GetPic(AtyNoDisplay.this, Config.getCachedDATA(AtyNoDisplay.this,Config.AUTHOR_ID), "middle", new GetPic.SuccessCallback() {
            @Override
            public void onSuccess(Object result) {
                Config.cacheDATA(AtyNoDisplay.this,((String[][]) result)[0][2],Config.USER_HEADER_IMAGE);
                if (--num==0){
                    finish();
                }
            }
        }, new GetPic.FailCallback() {
            @Override
            public void onFail() {

            }
        });
    }
}
