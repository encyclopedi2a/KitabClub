package com.sunbi.organisatiom.activity.kitabclub.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.andexert.library.RippleView;
import com.sunbi.organisatiom.activity.kitabclub.BookList;
import com.sunbi.organisatiom.activity.kitabclub.R;

/**
 * Created by gokarna on 7/18/15.
 */
public class BookCategoriesGridAdpater extends BaseAdapter implements RippleView.OnRippleCompleteListener {
    private Context mContext;
    private final int[] Imageid;

    public BookCategoriesGridAdpater(Context c, int[] Imageid) {
        mContext = c;
        this.Imageid = Imageid;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return Imageid.length;
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
            grid = inflater.inflate(R.layout.book_categories_grid_item, null);
            ImageView imageView = (ImageView) grid.findViewById(R.id.gridImage);
            RippleView rippleView=(RippleView)grid.findViewById(R.id.arrowRippleEffect);
            rippleView.setOnRippleCompleteListener(this);
            imageView.setBackgroundResource(Imageid[position]);
        } else {
            grid = (View) convertView;
        }
        return grid;
    }

    @Override
    public void onComplete(RippleView rippleView) {
        Intent intent=new Intent(mContext, BookList.class);
        mContext.startActivity(intent);
    }
}
