package hanzy.secret.Fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import hanzy.secret.Adapter.CatalogAdapter;
import hanzy.secret.Message.CatalogMessage;
import hanzy.secret.R;
import hanzy.secret.aty.AtyForums;
import hanzy.secret.net.GetCatalog;
import hanzy.secret.net.NetConnection;

/**
 * Created by h on 2016/7/11.
 */
public class CatalogFragment extends Fragment{

    private String TAG="CatalogFragment";
    private List<CatalogMessage> catalogMessages=null;
    private ListView lv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.aty_catalog, container, false);
        lv= (ListView) view.findViewById(R.id.Catalog_list);
        new GetCatalog(getActivity(), new GetCatalog.SuccessCallback() {
            @Override
            public void onSuccess(List<CatalogMessage> catalogMessages) {
                if (NetConnection.isMobileNetworkAvailable(getActivity()).equals("mobile")){
                    Toast.makeText(getActivity(),R.string.MobileNetwork,Toast.LENGTH_LONG).show();
                }
                CatalogFragment.this.catalogMessages=catalogMessages;
                CatalogAdapter catalogAdapter=new CatalogAdapter(getActivity(),
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
                if (NetConnection.isMobileNetworkAvailable(getActivity()).equals("none")){
                    Toast.makeText(getActivity(),R.string.NetworkFailure,Toast.LENGTH_LONG).show();
                }else if (NetConnection.isMobileNetworkAvailable(getActivity()).equals("mobile")||NetConnection.isMobileNetworkAvailable(getActivity()).equals("wifi")){
                    Toast.makeText(getActivity(),R.string.LoadFailure,Toast.LENGTH_LONG).show();
                }

            }
        });
        lv.setOnItemClickListener(new OnItemClickListenerImp());
        return view;
    }
    public class OnItemClickListenerImp implements AdapterView.OnItemClickListener {

        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Intent intent=new Intent(getActivity(),AtyForums.class);
            Bundle bundle=new Bundle();
            bundle.putSerializable("Item",catalogMessages.get(position).getFormlistItem());
            intent.putExtras(bundle);
            intent.putExtra("values",(Serializable) catalogMessages.get(position).getForumlist());
            Log.e(TAG,"fid"+catalogMessages.get(position).getFid());
            intent.putExtra("fid",catalogMessages.get(position).getFid());
            startActivity(intent);
        }
    }
}
