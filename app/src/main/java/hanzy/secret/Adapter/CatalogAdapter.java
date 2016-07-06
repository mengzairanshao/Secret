package hanzy.secret.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hanzy.secret.R;
import hanzy.secret.Message.CatalogMessage;

/**
 * Created by h on 2016/7/3.
 */
public class CatalogAdapter extends BaseAdapter{

    private Context context=null;
    public CatalogAdapter(Context context){
        this.context=context;
    }

    public Context getContext() {
        return context;
    }

    private List<CatalogMessage> data=new ArrayList<CatalogMessage>();

    public void addAll(List<CatalogMessage> data){
        this.data=data;
        notifyDataSetChanged();
    }

    public void clear(){
        data.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return data.size();
    }


    @Override
    public Object getItem(int position) {
        return data.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.aty_timeline_list_cell,null);
            convertView.setTag(new listCell((TextView) convertView.findViewById(R.id.tvCelllable)));
        }
        listCell lc= (listCell) convertView.getTag();
        CatalogMessage msg= (CatalogMessage) getItem(position);
        lc.getTvCellabel().setText(msg.getName());
        return convertView;
    }

    private  static class listCell{
        private TextView tvCellabel;

        public listCell(TextView tvCellabel){
            this.tvCellabel=tvCellabel;
        }
        public TextView getTvCellabel() {
            return tvCellabel;
        }
    }
}
