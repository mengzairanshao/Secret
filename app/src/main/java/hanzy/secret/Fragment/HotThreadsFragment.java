package hanzy.secret.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import hanzy.secret.Adapter.HotThreadAdapter;
import hanzy.secret.Message.HotThreadMessage;
import hanzy.secret.R;
import hanzy.secret.aty.AtyDetail;
import hanzy.secret.net.GetHotThread;
import hanzy.secret.net.NetConnection;

/**
 * Created by h on 2016/7/11.
 */
public class HotThreadsFragment extends Fragment{
    private String TAG="CatalogFragment";
    private List<HotThreadMessage> hotThreadMessages=null;
    private ListView lv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.aty_hot_thread, container, false);
        lv= (ListView) view.findViewById(R.id.hot_thread_list);
        new GetHotThread(getActivity(), new GetHotThread.SuccessCallback() {
            @Override
            public void onSuccess(List<HotThreadMessage> hotThreadMessages) {
                if (NetConnection.isMobileNetworkAvailable(getActivity()).equals("mobile")){
                    Toast.makeText(getActivity(),R.string.MobileNetwork,Toast.LENGTH_LONG).show();
                }
                HotThreadsFragment.this.hotThreadMessages=hotThreadMessages;
                HotThreadAdapter hotThreadAdapter=new HotThreadAdapter(getActivity());
                hotThreadAdapter.addAll(hotThreadMessages);
                lv.setAdapter(hotThreadAdapter);
            }
        }, new GetHotThread.FailCallback() {
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
            Intent intent=new Intent(getActivity(),AtyDetail.class);
            intent.putExtra("tid",hotThreadMessages.get(position).getTid());
            intent.putExtra("subject",hotThreadMessages.get(position).getSubject());
            startActivity(intent);
        }
    }
}
