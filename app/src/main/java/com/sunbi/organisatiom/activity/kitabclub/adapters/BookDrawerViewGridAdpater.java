package com.sunbi.organisatiom.activity.kitabclub.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sunbi.organisatiom.activity.kitabclub.R;
import com.sunbi.organisatiom.activity.kitabclub.TableContent;

import java.util.List;

/**
 * Created by gokarna on 7/18/15.
 */
public class BookDrawerViewGridAdpater extends BaseAdapter {
    private Context context;
    private List<String> imagePath;
    private List<String> bookPath;
    public BookDrawerViewGridAdpater(Context context, List<String> imagePath,List<String> bookPath) {
        this.context =context;
        this.imagePath=imagePath;
        this.bookPath=bookPath;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return imagePath.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return imagePath.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            grid = inflater.inflate(R.layout.book_drawer_view_grid_item, null);
            final ImageView imageView = (ImageView) grid.findViewById(R.id.gridImage);
            String imagepath = imagePath.get(position);
            Bitmap myBitmap = BitmapFactory.decodeFile(imagepath);
            imageView.setImageBitmap(myBitmap);
            RelativeLayout linearLayout=(RelativeLayout)grid.findViewById(R.id.linearlayout);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TableContent.class);
                    intent.putExtra("bookPath",bookPath.get(position).toString());
                    context.startActivity(intent);
                }
            });
        } else {
            grid = convertView;
        }
        return grid;
    }
}
