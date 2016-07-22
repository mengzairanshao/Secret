package hanzy.secret.Adapter;

import android.content.Context;
import android.os.Handler;
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
import hanzy.secret.utils.PicUtils;
import hanzy.secret.utils.TimeUtils;

/**
 * Created by h on 2016/7/7.
 */
public class DetailAdapter extends BaseAdapter {
    private static Handler handler;
    private static List<DetailMessage> detailMessageList_copy;
    private static ListView listView;
    private String TAG = "DetailAdapter";
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
    public static void updateView(Context context, int itemIndex) {
        View view;
        if (itemIndex >= listView.getFirstVisiblePosition() && itemIndex < (listView.getChildCount() + listView.getFirstVisiblePosition())) {
            DetailMessage detailMessage = detailMessageList_copy.get(itemIndex);
            final String bitmap = detailMessage.getBitmap()[0][2];
            if (bitmap != null) {
                view = listView.getChildAt(itemIndex);
                ViewHolder viewHolder = new ViewHolder(view);
                viewHolder.author.setText(detailMessage.getAuthor());
                viewHolder.posttime.setText(TimeUtils.times(Integer.parseInt(detailMessage.getDateline()) * 1000L));
                viewHolder.message.setText(detailMessage.getMessage());
                viewHolder.user_img.setImageBitmap(PicUtils.convertStringToIcon(detailMessage.getBitmap()[0][2]));

                for (int i = 1; i < detailMessage.getBitmap().length; i++) {
                    if ((detailMessage.getBitmap()[i][2] != null)) {
                        for (int j = 0; j < viewHolder.linearLayout.getChildCount(); j++) {
                            if (viewHolder.linearLayout.getChildAt(j).getTag().equals(detailMessage.getBitmap()[i][1])) {
                                ((ImageView) viewHolder.linearLayout.getChildAt(j)).setImageBitmap(PicUtils.convertStringToIcon(detailMessage.getBitmap()[i][2]));
                            }
                        }
                    }
                }
            }
        }
    }

    public Context getContext() {
        return context;
    }

    public void addAll(List<DetailMessage> detailMessageList) {
        this.detailMessageList = detailMessageList;
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
        viewHolder.message.setText(detailMessage.getMessage());
        viewHolder.user_img.setImageBitmap(PicUtils.convertStringToIcon(detailMessage.getBitmap()[0][2]));
        setImageView(detailMessage, viewHolder);
        return convertView;
    }

    public void setImageView(DetailMessage detailMessage, ViewHolder viewHolder) {
        for (int i = 1; i < detailMessage.getBitmap().length; i++) {
            if (viewHolder.linearLayout.getChildCount() != 0) {
                for (int j = 0; j < viewHolder.linearLayout.getChildCount(); j++) {
                    aBoolean = aBoolean || viewHolder.linearLayout.getChildAt(j).getTag().equals(detailMessage.getBitmap()[i][1]);
                }
                if (!aBoolean) {
                    imageView = new ImageView(context);
                    imageView.setTag(detailMessage.getBitmap()[i][1]);
                    imageView.setImageBitmap(PicUtils.convertStringToIcon(detailMessage.getBitmap()[i][2]));
                    imageView.setPadding(5,0,5,0);
                    viewHolder.linearLayout.addView(imageView);
                }
            } else {
                imageView = new ImageView(context);
                imageView.setTag(detailMessage.getBitmap()[i][1]);
                imageView.setImageBitmap(PicUtils.convertStringToIcon(detailMessage.getBitmap()[i][2]));
                imageView.setPadding(5,0,5,0);
                viewHolder.linearLayout.addView(imageView);
            }
        }
    }

    public static class ViewHolder {
        public TextView author;
        public TextView message;
        public TextView posttime;
        public ImageView user_img;
        public LinearLayout linearLayout;

        public ViewHolder(View view) {
            this.author = (TextView) view.findViewById(R.id.detail_author);
            this.posttime = (TextView) view.findViewById(R.id.detail_posttime);
            this.message = (TextView) view.findViewById(R.id.detail_message);
            this.user_img = (ImageView) view.findViewById(R.id.detail_user_img);
            this.linearLayout = (LinearLayout) view.findViewById(R.id.detail_image);
        }
    }
}
