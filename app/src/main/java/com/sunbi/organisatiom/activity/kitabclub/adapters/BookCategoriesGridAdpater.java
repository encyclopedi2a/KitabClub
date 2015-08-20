package com.sunbi.organisatiom.activity.kitabclub.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.squareup.picasso.Picasso;
import com.sunbi.organisatiom.activity.kitabclub.BookSubCategory;
import com.sunbi.organisatiom.activity.kitabclub.R;
import com.sunbi.organisatiom.activity.kitabclub.models.GridRow;

import java.util.List;

/**
 * Created by gokarna on 7/18/15.
 */
public class BookCategoriesGridAdpater extends BaseAdapter implements RippleView.OnRippleCompleteListener {
    private Context context;
    private List<GridRow> list;
    private static String id = null;
    private String TAG;
    private RippleView rippleView;
    public BookCategoriesGridAdpater(Context c, List<GridRow> list) {
        context = c;
        this.list = list;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        GridRow gridRow = (GridRow) getItem(position);
        if (convertView == null) {
            grid = inflater.inflate(R.layout.book_categories_grid_item, null);
            final ImageView imageView = (ImageView) grid.findViewById(R.id.gridImage);
            Picasso.with(context)
                    .load("http://thesunbihosting.com/demo/book_store/uploads/book_cover/bxvOIKgfGaE7.jpg")
            .placeholder(R.drawable.imagebackground).into(imageView);
            TextView title = (TextView) grid.findViewById(R.id.title);
            title.setText(gridRow.getName());
            TextView bookNumber = (TextView) grid.findViewById(R.id.totalBooks);
            bookNumber.setText(gridRow.getTotal_books()+" books");
            //Assign the value to the field so that the id can be transferred to sub category class by iterating through hashmap
            rippleView = (RippleView) grid.findViewById(R.id.arrowRippleEffect);
            rippleView.setTag(gridRow.getId());
            rippleView.setOnRippleCompleteListener(this);
        } else {
            grid = convertView;
        }
        return grid;
    }

    @Override
    public void onComplete(RippleView rippleView) {
        Intent intent = new Intent(context, BookSubCategory.class);
        intent.putExtra("BookCategoryId",rippleView.getTag().toString());
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
