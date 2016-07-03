package hanzy.secret.aty;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import hanzy.secret.R;
import hanzy.secret.net.GetLine;
import hanzy.secret.net.GetProfile;
import hanzy.secret.net.Message;

/**
 * Created by h on 2016/6/28.
 */
public class AtyTimeLine extends ListActivity {

    AtyTimeLineMessageListAdapter adapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_timeline);

        adapter=new AtyTimeLineMessageListAdapter(AtyTimeLine.this);
        setListAdapter(adapter);
        final ProgressDialog progressDialog=ProgressDialog.show(AtyTimeLine.this,"Wait","A");
        new GetLine(AtyTimeLine.this, new GetLine.SuccessCallback() {
            @Override
            public void onSuccess(List<Message> timeline) {
                progressDialog.dismiss();
                adapter.addAll(timeline);
            }
        }, new GetLine.FailCallback() {
            @Override
            public void onFail() {
                progressDialog.dismiss();
            }
        });
    }

}
