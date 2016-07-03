package hanzy.secret.aty;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hanzy.secret.R;
import hanzy.secret.net.Message;

/**
 * Created by h on 2016/7/3.
 */
public class AtyTimeLineMessageListAdapter extends BaseAdapter{

    private Context context=null;
    public AtyTimeLineMessageListAdapter(Context context){
        this.context=context;
    }

    public Context getContext() {
        return context;
    }

    private List<Message> data=new ArrayList<Message>();

    public void addAll(List<Message> data){
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
        Message msg= (Message) getItem(position);
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
