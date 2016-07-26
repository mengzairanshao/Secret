package hanzy.secret.Adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hanzy.secret.Message.DetailMessage;
import hanzy.secret.Message.ThreadsMessage;
import hanzy.secret.R;
import hanzy.secret.secret.Config;
import hanzy.secret.utils.PicUtils;
import hanzy.secret.utils.TimeUtils;

/**
 * Created by h on 2016/7/7.
 */
public class DetailAdapter extends BaseAdapter {
    private static Handler handler;
    private static List<DetailMessage> detailMessageList_copy;
    private static ListView listView;
    private static String TAG = "DetailAdapter";
    private Context context;
    private LayoutInflater inflater;
    private List<DetailMessage> detailMessageList = new ArrayList<>();
    private ImageView imageView;
    private Boolean aBoolean = false;


    public DetailAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * @param handler
     * @param detailMessageList
     * @param listView          修改静态变量
     */
    public static void set(Handler handler, List<DetailMessage> detailMessageList, ListView listView) {
        DetailAdapter.handler = handler;
        DetailAdapter.detailMessageList_copy = detailMessageList;
        DetailAdapter.listView = listView;
    }

    /**
     * @param itemIndex itemIndex是想要修改的ListView的编号
     */
    public static void updateView(int itemIndex) {
        View view;
        if (itemIndex >= listView.getFirstVisiblePosition() && itemIndex < (listView.getChildCount() + listView.getFirstVisiblePosition())) {
            DetailMessage detailMessage = detailMessageList_copy.get(itemIndex);
            view = listView.getChildAt(itemIndex);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.message.setText((Spanned) detailMessage.getMessage());
            viewHolder.user_img.setImageBitmap(PicUtils.convertStringToIcon(detailMessage.getBitmap()[0][2]));
        }
    }

    public static void handlerSet(Message msg, List<DetailMessage> detailMessageList, ListView listView, Handler handler, DetailAdapter detailAdapter) {
        if (msg.what == Config.USER_LOAD_IMAGE) {
            DetailAdapter.set(handler, detailMessageList, listView);
            String[][] bitmap = (String[][]) msg.obj;
            DetailMessage detailMessage;
            for (int i = 0; i < detailMessageList.size(); i++) {
                detailMessage = detailMessageList.get(i);
                for (int k = 0; k < bitmap.length; k++) {
                    for (int j = 0; j < detailMessage.getBitmap().length; j++) {
                        if (detailMessage.getBitmap()[j][1].equals(bitmap[k][1])) {
                            detailMessageList.get(i).setBitmap(j, bitmap[k][2]);
                            updateView(i);
                        }
                    }
                }
            }
        }
        if (msg.what == Config.SPANNED_MESSAGE) {
            DetailAdapter.set(handler, detailMessageList, listView);
            HashMap<String, Object> hashMap = (HashMap<String, Object>) msg.obj;
            DetailMessage detailMessage;
            for (int i = 0; i < detailMessageList.size(); i++) {
                detailMessage = detailMessageList.get(i);
                if (detailMessage.getPid() == hashMap.get("pid")) {
                    detailMessageList.get(i).setMessage(hashMap.get("message"));
                    updateView(i);
                }
            }
        }

    }

    public Context getContext() {
        return context;
    }

    public void addAll(List<DetailMessage> detailMessageList) {
        this.detailMessageList = detailMessageList;
//        notifyDataSetChanged();
    }

    public void addItem(HashMap<String,Object> hashMap){
        detailMessageList.add(new DetailMessage(hashMap));
        notifyDataSetChanged();
    }

    public void clear() {
        detailMessageList.clear();
    }

    @Override
    public int getCount() {
        return detailMessageList.size();
    }

    @Override
    public DetailMessage getItem(int position) {
        return detailMessageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.aty_detail_list_cell, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        DetailMessage detailMessage = getItem(position);
        viewHolder.author.setText(detailMessage.getAuthor());
        viewHolder.posttime.setText(TimeUtils.times(Integer.parseInt(detailMessage.getDateline()) * 1000L));
        if (detailMessage.getMessage() != null){
            viewHolder.message.setText((Spanned) detailMessage.getMessage());
            //viewHolder.message.setMovementMethod(LinkMovementMethod.getInstance());
        }
        viewHolder.user_img.setImageBitmap(PicUtils.convertStringToIcon(detailMessage.getBitmap()[0][2]));
        return convertView;
    }

    public static class ViewHolder {
        public TextView author;
        public TextView message;
        public TextView posttime;
        public ImageView user_img;

        public ViewHolder(View view) {
                this.author = (TextView) view.findViewById(R.id.detail_author);
                this.posttime = (TextView) view.findViewById(R.id.detail_posttime);
                this.message = (TextView) view.findViewById(R.id.detail_message);
                this.user_img = (ImageView) view.findViewById(R.id.detail_user_img);
        }
    }
}
