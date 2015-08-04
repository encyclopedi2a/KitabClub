package com.sunbi.organisatiom.activity.kitabclub.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunbi.organisatiom.activity.kitabclub.R;
import com.sunbi.organisatiom.activity.kitabclub.models.ListRow;

import java.util.List;

/**
 * Created by gokarna on 8/4/15.
 */
public class CustomMyBooksListAdapter extends BaseAdapter {
    private List<ListRow> mAppList;
    private Context context;

    public CustomMyBooksListAdapter(List<ListRow> mAppList, Context context) {
        this.mAppList = mAppList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mAppList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAppList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context.getApplicationContext(),
                    R.layout.item_list_app, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        ListRow listRow = (ListRow) getItem(position);
       // Picasso.with(context)
         //       .load(listRow.getImagePath())
           //     .placeholder(R.drawable.imagebackground).fit().centerCrop().into(holder.iv_icon);
        holder.iv_icon.setImageResource(R.drawable.guy);
        holder.tv_name.setText(listRow.getBookName());
        holder.tv_name.setBackgroundColor(Color.WHITE);
        holder.tv_name.setBackgroundResource(R.drawable.selector_state);
        return convertView;
    }

    class ViewHolder {
        ImageView iv_icon;
        TextView tv_name;

        public ViewHolder(View view) {
            iv_icon = (ImageView) view.findViewById(R.id.bookimage);
            tv_name = (TextView) view.findViewById(R.id.bookname);
            view.setTag(this);
        }
    }
}
