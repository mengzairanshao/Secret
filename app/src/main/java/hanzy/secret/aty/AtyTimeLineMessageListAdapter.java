package hanzy.secret.aty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

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
        return null;
    }
}
