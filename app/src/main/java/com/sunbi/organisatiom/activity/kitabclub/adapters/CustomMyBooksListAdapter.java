package com.sunbi.organisatiom.activity.kitabclub.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunbi.organisatiom.activity.kitabclub.R;

import java.util.List;

/**
 * Created by gokarna on 8/4/15.
 */
public class CustomMyBooksListAdapter extends BaseAdapter {
    private List<String> bookName;
    private List<String> bookPath;
    private Context context;

    public CustomMyBooksListAdapter(List<String> bookName,List<String> bookPath, Context context) {
        this.bookName = bookName;
        this.bookPath=bookPath;
        this.context = context;
    }

    @Override
    public int getCount() {
        return bookName.size();
    }

    @Override
    public Object getItem(int position) {
        return bookName.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context.getApplicationContext(),
                    R.layout.mybooks_list_item, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.bookImage.setImageResource(R.drawable.guy);
        holder.bookName.setText(bookName.get(position));
        holder.bookPath.setText(bookPath.get(position));
        holder.bookName.setBackgroundColor(Color.WHITE);
        holder.bookName.setBackgroundResource(R.drawable.selector_state);
        return convertView;
    }

    class ViewHolder {
        ImageView bookImage;
        TextView bookName;
        TextView bookPath;

        public ViewHolder(View view) {
            bookImage = (ImageView) view.findViewById(R.id.bookimage);
            bookName = (TextView) view.findViewById(R.id.bookname);
            bookPath=(TextView)view.findViewById(R.id.bookpath);
            view.setTag(this);
        }
    }
}
