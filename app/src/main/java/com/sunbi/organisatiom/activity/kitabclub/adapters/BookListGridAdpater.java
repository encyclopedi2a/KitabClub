package com.sunbi.organisatiom.activity.kitabclub.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunbi.organisatiom.activity.kitabclub.BookCart;
import com.sunbi.organisatiom.activity.kitabclub.R;

/**
 * Created by gokarna on 7/18/15.
 */
public class BookListGridAdpater extends BaseAdapter {
    private Context mContext;
    private final String[] web;
    private final int[] Imageid;

    public BookListGridAdpater(Context c, String[] web, int[] Imageid) {
        mContext = c;
        this.Imageid = Imageid;
        this.web = web;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return web.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.book_list_grid_item, null);
            TextView textView = (TextView) grid.findViewById(R.id.gridText);
            ImageView imageView = (ImageView) grid.findViewById(R.id.gridImage);
            textView.setText(web[position]);
            imageView.setImageResource(Imageid[position]);
            ImageView cartImage=(ImageView)grid.findViewById(R.id.cart);
            cartImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, BookCart.class);
                    mContext.startActivity(intent);
                }
            });
        } else {
            grid = (View) convertView;
        }
        return grid;
    }
}
