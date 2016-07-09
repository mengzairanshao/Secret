package hanzy.secret.aty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hanzy.secret.Adapter.CatalogAdapter;
import hanzy.secret.Message.CatalogMessage;
import hanzy.secret.R;

public class AtyForums extends AppCompatActivity {

    private ListView lv=null;
    private String[] values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_forums);

        lv= (ListView) findViewById(R.id.forums);
        Intent i=getIntent();
        Bundle bundle=i.getExtras();
        HashMap<String,String>hashMap= (HashMap<String, String>) bundle.getSerializable("Item");
        this.values=i.getStringArrayExtra("values");
        SimpleAdapter simpleAdapter=new SimpleAdapter(AtyForums.this,
                getData(hashMap),
                R.layout.aty_forums_list,
                new String[]{"text"},new int[]{R.id.forums_text});
        lv.setAdapter(simpleAdapter);
        i.getStringExtra("fid");
        lv.setOnItemClickListener(new OnItemClickListenerImp());
    }

    public List<Map<String, Object>> getData(HashMap<String,String> hashMap) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i=0;i<values.length;i++){
            map = new HashMap<String, Object>();
            map.put("text", hashMap.get(values[i]));
            list.add(map);
        }

        return list;
    }
    public class OnItemClickListenerImp implements AdapterView.OnItemClickListener {

        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Intent intent=new Intent(AtyForums.this,AtyThreads.class);
            intent.putExtra("fid",values[position]);
            startActivity(intent);
        }
    }
}

