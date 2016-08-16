package hanzy.secret.aty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import hanzy.secret.Adapter.CatalogAdapter;
import hanzy.secret.Message.CatalogMessage;
import hanzy.secret.R;
import hanzy.secret.net.GetCatalog;
import hanzy.secret.net.NetConnection;
import hanzy.secret.utils.logUtils;

/**
 * Created by h on 2016/6/28.
 *
 */
public class AtyCatalog extends AppCompatActivity {

    private String TAG = "AtyCatalog";
    private List<CatalogMessage> catalogMessages = null;
    private ListView lv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_catalog);
        lv = (ListView) findViewById(R.id.Catalog_list);
        new GetCatalog(AtyCatalog.this, new GetCatalog.SuccessCallback() {
            @Override
            public void onSuccess(List<CatalogMessage> catalogMessages) {
                if (NetConnection.isMobileNetworkAvailable(AtyCatalog.this).equals("mobile")) {
                    Toast.makeText(AtyCatalog.this, R.string.MobileNetwork, Toast.LENGTH_LONG).show();
                }
                AtyCatalog.this.catalogMessages = catalogMessages;
                CatalogAdapter catalogAdapter = new CatalogAdapter(AtyCatalog.this,
                        CatalogAdapter.getData(catalogMessages),
                        R.layout.aty_catalog_list_cell,
                        CatalogAdapter.from,
                        CatalogAdapter.to);
                catalogAdapter.set(catalogMessages);
                lv.setAdapter(catalogAdapter);
            }
        }, new GetCatalog.FailCallback() {
            @Override
            public void onFail() {
                if (NetConnection.isMobileNetworkAvailable(AtyCatalog.this).equals("none")) {
                    Toast.makeText(AtyCatalog.this, R.string.NetworkFailure, Toast.LENGTH_LONG).show();
                } else if (NetConnection.isMobileNetworkAvailable(AtyCatalog.this).equals("mobile") || NetConnection.isMobileNetworkAvailable(AtyCatalog.this).equals("wifi")) {
                    Toast.makeText(AtyCatalog.this, R.string.LoadFailure, Toast.LENGTH_LONG).show();
                }

            }
        });
        lv.setOnItemClickListener(new OnItemClickListenerImp());
    }

    public class OnItemClickListenerImp implements AdapterView.OnItemClickListener {

        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Intent intent = new Intent(AtyCatalog.this, AtyForums.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Item", catalogMessages.get(position).getFormlistItem());
            intent.putExtras(bundle);
            intent.putExtra("values", (Serializable) catalogMessages.get(position).getForumlist());
            logUtils.log(AtyCatalog.this, TAG, "fid" + catalogMessages.get(position).getFid());
            intent.putExtra("fid", catalogMessages.get(position).getFid());
            startActivity(intent);
        }
    }
}

