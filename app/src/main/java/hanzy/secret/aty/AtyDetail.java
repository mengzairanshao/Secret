package hanzy.secret.aty;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import hanzy.secret.Adapter.DetailAdapter;
import hanzy.secret.Message.DetailMessage;
import hanzy.secret.R;
import hanzy.secret.net.GetDetail;
import hanzy.secret.net.NetConnection;
import hanzy.secret.net.SendComment;
import hanzy.secret.secret.Config;

public class AtyDetail extends AppCompatActivity {

    private String TAG="AtyDetail";
    private DetailAdapter detailAdapter;
    private Handler handler;
    private ListView listView;
    private List<DetailMessage> detailMessageList = null;
    private Button button;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_detail);
        listView = (ListView) findViewById(R.id.detailList);
        final Intent intent = getIntent();
        this.setTitle(intent.getStringExtra("subject"));
        setHandler();
        button= (Button) findViewById(R.id.detail_comment_button);
        editText= (EditText) findViewById(R.id.detail_comment_editText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText()!=null) {
                    if (editText.getText().length() <= 5) {
                        Toast.makeText(AtyDetail.this, R.string.content_cannot_be_less_than_six_words,Toast.LENGTH_LONG).show();
                    } else {
                        final Editable text=editText.getText();
                        new SendComment(AtyDetail.this, editText.getText(), new SendComment.SuccessCallback() {
                            @Override
                            public void onSuccess(String result) {
                                setReply(text,result);
                            }
                        }, new SendComment.FailCallback() {
                            @Override
                            public void onFail() {

                            }
                        }, "tid", intent.getStringExtra("tid"));
                    }
                }
            }
        });

        new GetDetail(AtyDetail.this, new GetDetail.SuccessCallback() {
            @Override
            public void onSuccess(List<DetailMessage> detailMessageList) {
                if (NetConnection.isMobileNetworkAvailable(AtyDetail.this).equals("mobile")) {
                    Toast.makeText(AtyDetail.this, R.string.MobileNetwork, Toast.LENGTH_LONG).show();
                }
                AtyDetail.this.detailMessageList = detailMessageList;
                detailAdapter = new DetailAdapter(AtyDetail.this);
                detailAdapter.addAll(detailMessageList);
                listView.setAdapter(detailAdapter);
            }
        }, new GetDetail.FailCallback() {
            @Override
            public void onFail() {
                if (NetConnection.isMobileNetworkAvailable(AtyDetail.this).equals("none")) {
                    Toast.makeText(AtyDetail.this, R.string.NetworkFailure, Toast.LENGTH_LONG).show();
                } else if (NetConnection.isMobileNetworkAvailable(AtyDetail.this).equals("mobile") || NetConnection.isMobileNetworkAvailable(AtyDetail.this).equals("wifi")) {
                    Toast.makeText(AtyDetail.this, R.string.LoadFailure, Toast.LENGTH_LONG).show();
                }

            }
        }, intent.getStringExtra("tid"), handler);
    }

    public void setHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                DetailAdapter.handlerSet(msg,detailMessageList,listView,handler,detailAdapter);
            }
        };
    }

    public void setReply(Editable text,String result){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("message",text);
        Log.e(TAG,"emoji"+text);
        editText.setText("");
        editText.clearFocus();
        Long l=System.currentTimeMillis();
        l=(l-l%1000)/1000;
        String timeStamp=Long.toString(l);
        Log.e(TAG,"TimeStamp"+timeStamp);
        hashMap.put("dbdateline",timeStamp);
        String[] str = {"member_username", "tid", "pid"};
        try {
            JSONObject jsonObject = new JSONObject(result);
            Log.e(TAG, "result" + jsonObject.toString());
            jsonObject = jsonObject.getJSONObject("Variables");
            for (int i = 0; i < str.length; i++) {
                if (str[i].equals("member_username")){
                    hashMap.put("author", jsonObject.getString(str[i]));
                }else {
                    hashMap.put(str[i], jsonObject.getString(str[i]));
                }
            }
            String[][] bitmap=new String[1][3];
            bitmap[0][0]=jsonObject.getString("member_uid");
            bitmap[0][1]= Html.fromHtml(jsonObject.getString("member_avatar")).toString();
            bitmap[0][2]= Config.getCachedDATA(AtyDetail.this,Config.USER_HEADER_IMAGE);
            hashMap.put("bitmap",bitmap);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        detailAdapter.addItem(hashMap);
    }
}
