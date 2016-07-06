package hanzy.secret.aty;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import java.util.List;

import hanzy.secret.Adapter.CatalogAdapter;
import hanzy.secret.R;
import hanzy.secret.net.GetCatalog;
import hanzy.secret.net.GetThread;
import hanzy.secret.Message.CatalogMessage;

/**
 * Created by h on 2016/6/28.
 */
public class AtyCatalog extends ListActivity {

    CatalogAdapter adapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_timeline);

        adapter=new CatalogAdapter(AtyCatalog.this);
        setListAdapter(adapter);
        final ProgressDialog progressDialog=ProgressDialog.show(AtyCatalog.this,"oha","waiting");
        new GetThread(AtyCatalog.this, new GetThread.SuccessCallback() {
            @Override
            public void onSuccess(String timeline) {

            }
        }, new GetThread.FailCallback() {
            @Override
            public void onFail() {

            }
        });
        new GetCatalog(AtyCatalog.this, new GetCatalog.SuccessCallback() {
            @Override
            public void onSuccess(List<CatalogMessage> timeline) {
                progressDialog.dismiss();
                adapter.addAll(timeline);
            }
        }, new GetCatalog.FailCallback() {
            @Override
            public void onFail() {
                progressDialog.dismiss();
            }
        });
    }

}
