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
import com.sunbi.organisatiom.activity.kitabclub.BookDetail;
import com.sunbi.organisatiom.activity.kitabclub.R;
import com.sunbi.organisatiom.activity.kitabclub.models.GridRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gokarna on 7/18/15.
 */
public class BookListGridAdpater extends BaseAdapter {
    private Context context;
    private List<GridRow> list;

    public BookListGridAdpater(Context context, List<GridRow> list) {
        this.context = context;
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
        final GridRow gridRow = (GridRow) getItem(position);
        if (convertView == null) {
            grid = inflater.inflate(R.layout.book_list_grid_item, null);
            TextView textView = (TextView) grid.findViewById(R.id.gridText);
            textView.setText(gridRow.getName());
            ImageView imageView = (ImageView) grid.findViewById(R.id.gridImage);
            Picasso.with(context)
                    .load(gridRow.getImage_path())
                    .into(imageView);
            TextView priceText = (TextView) grid.findViewById(R.id.price);
            priceText.setText(gridRow.getPrice());
            final ArrayList<String> parameters = new ArrayList<>();
            parameters.add(gridRow.getId());
            parameters.add(gridRow.getName());
            parameters.add(gridRow.getImage_path());
            parameters.add(gridRow.getBookPath());
            parameters.add(gridRow.getAuthorName());
            parameters.add(gridRow.getPrice());
            parameters.add(gridRow.getDiscount());
            parameters.add(gridRow.getType());
            parameters.add(gridRow.getDescription());
            parameters.add(gridRow.getCategory_id());
            RippleView rippleView = (RippleView) grid.findViewById(R.id.arrowRippleEffect);
            rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                @Override
                public void onComplete(RippleView v) {

                    Intent intent = new Intent(context, BookDetail.class);
                    intent.putStringArrayListExtra("parameters", parameters);
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }

            });
        } else {
            grid = convertView;
        }
        return grid;
    }
}
