package com.sunbi.organisatiom.activity.kitabclub.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunbi.organisatiom.activity.kitabclub.R;

import java.util.List;

/**
 * Created by gokarna on 8/21/15.
 */
public class PushNotificationListAdapter extends BaseAdapter {
    private Context context;
    private List<String> content;
    private List<String> title;
    private List<String> date;
    public PushNotificationListAdapter(Context context,List<String> content,List<String> title,List<String> date) {
        this.context=context;
        this.content=content;
        this.title=title;
        this.date=date;
    }
    @Override
    public int getCount() {
        return content.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            grid = inflater.inflate(R.layout.push_notification_custom_list, null);
            final ViewHolder viewHolder=new ViewHolder();
            viewHolder.remove=(ImageView)grid.findViewById(R.id.remove);
            viewHolder.date=(TextView)grid.findViewById(R.id.date);
            viewHolder.date.setText(date.get(position));
            Log.d("Date",date.get(position));
            viewHolder.title=(TextView)grid.findViewById(R.id.title);
            viewHolder.title.setText(title.get(position));
            viewHolder.description=(TextView)grid.findViewById(R.id.content);
            viewHolder.description.setText(content.get(position));
        } else {
            grid = convertView;
        }
        return grid;
    }
    private class ViewHolder{
        ImageView remove;
        TextView date,title,description;
    }
}
