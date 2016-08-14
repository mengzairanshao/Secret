package hanzy.secret.aty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hanzy.secret.Message.CatalogMessage;
import hanzy.secret.R;

public class AtyForums extends AppCompatActivity {

    HashMap<String,String>hashMap;
    private ListView lv;
    private String[] values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_forums);
        lv= (ListView) findViewById(R.id.forums);
        Intent i=getIntent();
        Bundle bundle=i.getExtras();
        hashMap= (HashMap<String, String>) bundle.getSerializable("Item");
        values=i.getStringArrayExtra("values");
        setTitle(i.getStringExtra("name"));
        SimpleAdapter simpleAdapter=new SimpleAdapter(AtyForums.this,
                getData(hashMap),
                R.layout.aty_forums_list_cell,
                new String[]{"text","topic","news_num"},new int[]{R.id.forums_list,R.id.topic,R.id.news_num});
        lv.setAdapter(simpleAdapter);
        lv.setOnItemClickListener(new OnItemClickListenerImp());
    }

    public List<Map<String, Object>> getData(HashMap<String,String> hashMap) {
        JSONObject jsonObject;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        for (String value : values) {
            try {
                jsonObject = new JSONObject(hashMap.get(value));
                map = new HashMap<>();
                map.put("text", jsonObject.getString("name"));
                map.put("topic", jsonObject.getString("threads"));
                map.put("news_num", jsonObject.getString("todayposts"));
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
            Intent intent=new Intent(AtyForums.this,AtyThreads.class);
            try {
                JSONObject jsonObject=new JSONObject(hashMap.get(values[position]));
                intent.putExtra("name",jsonObject.getString("name"));
                intent.putExtra("fid",values[position]);
                startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
