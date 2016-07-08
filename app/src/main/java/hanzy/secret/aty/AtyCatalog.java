package hanzy.secret.aty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import hanzy.secret.Adapter.CatalogAdapter;
import hanzy.secret.Message.CatalogMessage;
import hanzy.secret.Message.ThreadsMessage;
import hanzy.secret.R;
import hanzy.secret.net.GetCatalog;
import hanzy.secret.net.GetThread;

/**
 * Created by h on 2016/6/28.
 */
public class AtyCatalog extends AppCompatActivity {

    private List<CatalogMessage> catalogMessages=null;
    private ListView lv=null;
    CatalogAdapter adapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_catalog);

        lv= (ListView) findViewById(R.id.Cataloglist);
        new GetCatalog(AtyCatalog.this, new GetCatalog.SuccessCallback() {
            @Override
            public void onSuccess(List<CatalogMessage> catalogMessages) {
                //AtyThreads.this.threadsMessages=threadsMessages;
                CatalogAdapter catalogAdapter=new CatalogAdapter(AtyCatalog.this,
                        CatalogAdapter.getData(catalogMessages),
                        R.layout.aty_catalog_list_cell,
                        CatalogAdapter.from,
                        CatalogAdapter.to);
                lv.setAdapter(catalogAdapter);
            }
        }, new GetCatalog.FailCallback() {
            @Override
            public void onFail() {

            }
        });
        lv.setOnItemClickListener(new OnItemClickListenerImp());
    }

    public class OnItemClickListenerImp implements AdapterView.OnItemClickListener {

        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Intent intent=new Intent(AtyCatalog.this,AtyDetail.class);
            intent.putExtra("fid",catalogMessages.get(position).getFid());
            startActivity(intent);
        }
    }
    }

