package hanzy.secret.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import hanzy.secret.R;
import hanzy.secret.aty.AtyThreads;

/**
 * Created by h on 2016/7/11.
 */
public class ForumFragment extends Fragment{
    private String TAG="ForumFragment";
    private ListView lv;
    private String[] values;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.aty_forums, container, false);
        lv= (ListView) view.findViewById(R.id.forums);
        Bundle bundle=getFragmentManager().findFragmentByTag("CatalogFragment").getArguments();
        HashMap<String,String> hashMap= (HashMap<String, String>) bundle.getSerializable("Item");
        this.values=bundle.getStringArray("values");
        Log.e(TAG,"values"+this.values);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getActivity(),
                getData(hashMap),
                R.layout.aty_forums_list_cell,
                new String[]{"text"},new int[]{R.id.forums_list});
        lv.setAdapter(simpleAdapter);
        bundle.getString("fid");
        lv.setOnItemClickListener(new OnItemClickListenerImp());
        return view;
    }

    public List<Map<String, Object>> getData(HashMap<String,String> hashMap) {
        JSONObject jsonObject;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i=0;i<values.length;i++){
            try {
                jsonObject=new JSONObject(hashMap.get(values[i]));
                map = new HashMap<String, Object>();
                map.put("text", jsonObject.getString("name"));
                list.add(map);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return list;
    }

    public class OnItemClickListenerImp implements AdapterView.OnItemClickListener {

        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Intent intent=new Intent(getActivity(),AtyThreads.class);
            intent.putExtra("fid",values[position]);
            startActivity(intent);
        }
    }
}
