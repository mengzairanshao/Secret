package hanzy.secret.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import hanzy.secret.Adapter.HotThreadAdapter;
import hanzy.secret.Message.HotThreadMessage;
import hanzy.secret.R;
import hanzy.secret.aty.AtyDetail;
import hanzy.secret.net.GetHotThread;
import hanzy.secret.net.NetConnection;

/**
 * Created by h on 2016/7/11.
 *
 */
public class HotThreadsFragment extends Fragment {

    private Handler handler;
    private View rootView;
    private String TAG = "HotThreadsFragment";
    private List<HotThreadMessage> hotThreadMessageList = null;
    private PullToRefreshListView listView;
    private ListView listViewAct;
    private HotThreadAdapter hotThreadAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.aty_hot_thread, container, false);
        listView = (PullToRefreshListView) view.findViewById(R.id.hot_thread_list);
        listViewAct = listView.getRefreshableView();
        hotThreadAdapter = new HotThreadAdapter(getActivity());
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                new GetHotThread(getActivity(), new GetHotThread.SuccessCallback() {
                    @Override
                    public void onSuccess(List<HotThreadMessage> hotThreadMessageList) {
                        HotThreadsFragment.this.hotThreadMessageList = hotThreadMessageList;
                        hotThreadAdapter.addAll(HotThreadsFragment.this.hotThreadMessageList);
                        listView.onRefreshComplete();
                    }
                }, new GetHotThread.FailCallback() {
                    @Override
                    public void onFail() {

                    }
                }, handler);
            }
        });
        if (rootView == null) {
            rootView = view;
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
            return rootView;
        }
        setHandler();
        new GetHotThread(getActivity(), new GetHotThread.SuccessCallback() {
            @Override
            public void onSuccess(List<HotThreadMessage> hotThreadMessageList) {
                if (hotThreadMessageList != null) {
                    if (NetConnection.isMobileNetworkAvailable(getActivity()).equals("mobile")) {
                        Toast.makeText(getActivity(), R.string.MobileNetwork, Toast.LENGTH_LONG).show();
                    }
                    HotThreadsFragment.this.hotThreadMessageList = hotThreadMessageList;
                    HotThreadAdapter hotThreadAdapter = new HotThreadAdapter(getActivity());
                    hotThreadAdapter.addAll(hotThreadMessageList);
                    HotThreadsFragment.this.hotThreadAdapter = hotThreadAdapter;
                    listViewAct.setAdapter(hotThreadAdapter);
                } else {
                    Toast.makeText(getActivity(), R.string.no_more, Toast.LENGTH_LONG).show();
                }
            }
        }, new GetHotThread.FailCallback() {
            @Override
            public void onFail() {
                if (NetConnection.isMobileNetworkAvailable(getActivity()).equals("none")) {
                    Toast.makeText(getActivity(), R.string.NetworkFailure, Toast.LENGTH_LONG).show();
                } else if (NetConnection.isMobileNetworkAvailable(getActivity()).equals("mobile") || NetConnection.isMobileNetworkAvailable(getActivity()).equals("wifi")) {
                    Toast.makeText(getActivity(), R.string.LoadFailure, Toast.LENGTH_LONG).show();
                }

            }
        }, handler);
        listViewAct.setOnItemClickListener(new OnItemClickListenerImp());
        return view;
    }

    public void setHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    String[][] bitmap = (String[][]) msg.obj;
                    for (String[] aBitmap : bitmap) {
                        for (int i = 0; i < hotThreadMessageList.size(); i++) {
                            HotThreadMessage threadsMessage = hotThreadMessageList.get(i);
                            if (threadsMessage.getBitmap()[0][1].equals(aBitmap[1])) {
                                hotThreadMessageList.get(i).setBitmap(0, bitmap[0][2]);
                                hotThreadAdapter.addAll(hotThreadMessageList);
                            }
                        }
                    }
                }
            }
        };
    }

    public class OnItemClickListenerImp implements AdapterView.OnItemClickListener {

        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Intent intent = new Intent(getActivity(), AtyDetail.class);
            intent.putExtra("tid", hotThreadMessageList.get(position - 1).getTid());
            intent.putExtra("subject", hotThreadMessageList.get(position - 1).getSubject());
            startActivity(intent);
        }
    }
}
