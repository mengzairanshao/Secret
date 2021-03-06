package hanzy.secret.net;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hanzy.secret.Message.CatalogMessage;
import hanzy.secret.secret.Config;
import hanzy.secret.utils.logUtils;

/**
 * Created by h on 2016/6/29.
 *
 */
public class GetCatalog {
    public String TAG = "GetCatalog";

    public GetCatalog(final Context context, final SuccessCallback successCallback, final FailCallback failCallback) {

        new NetConnection(context, Config.BASE_URL, HttpMethod.GET, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    logUtils.log(context, TAG, result);
                    JSONObject jsonObject = new JSONObject(result);
                    logUtils.log(context, TAG, "Get Json Data:" + jsonObject.toString());
                    JSONArray jsonArray = jsonObject.getJSONObject("Variables").getJSONArray("catlist");
                    List<CatalogMessage> megs = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        String[] formList = new String[jsonObject.getJSONArray("forums").length()];
                        for (int j = 0; j < jsonObject.getJSONArray("forums").length(); j++) {
                            formList[j] = jsonObject.getJSONArray("forums").getString(j);
                        }
                        HashMap<String, String> hashMap = new HashMap<>();
                        jsonObject = new JSONObject(result);
                        jsonArray = jsonObject.getJSONObject("Variables").getJSONArray("forumlist");
                        for (int j = 0; j < jsonArray.length(); j++) {
                            for (String aFormList : formList) {
                                if (jsonArray.getJSONObject(j).getString("fid").equals(aFormList)) {
                                    hashMap.put(jsonArray.getJSONObject(j).getString("fid"),
                                            jsonArray.getJSONObject(j).toString());
                                }
                            }
                        }
                        jsonObject = new JSONObject(result);
                        jsonArray = jsonObject.getJSONObject("Variables").getJSONArray("catlist");
                        jsonObject = jsonArray.getJSONObject(i);
                        megs.add(new CatalogMessage(jsonObject.getString("fid"), jsonObject.getString("name"), formList, hashMap));
                    }

                    if (successCallback != null) successCallback.onSuccess(megs);
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (failCallback != null) failCallback.onFail();
                }

            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                if (failCallback != null) failCallback.onFail();
            }
        }, Config.KEY_MOBILE, Config.VALUE_MOBILE_IS,
                Config.KEY_MODULE, Config.VALUE_MODULE_FORUMINDEX,
                Config.KEY_VERSION, Config.VALUE_VERSION_NUM);

    }

    public interface SuccessCallback {
        void onSuccess(List<CatalogMessage> catalogMessages);
    }

    public interface FailCallback {
        void onFail();
    }
}