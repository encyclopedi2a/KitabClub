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

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gokarna on 7/18/15.
 */
public class BookDrawerViewGridAdpater extends BaseAdapter {
    private Context context;
    private List<String> imagePath;
    private List<String> bookPath;

    public BookDrawerViewGridAdpater(Context context, List<String> imagePath, List<String> bookPath) {
        this.context = context;
        //this is done to remove the duplicate element from arraylist if present
        Set<String> set = new LinkedHashSet<>();
        set.addAll(imagePath);
        imagePath.clear();
        imagePath.addAll(set);
        this.imagePath = imagePath;
        for (int i = 0; i < 100; i++) {
            imagePath.add("");
        }
        this.bookPath = bookPath;
        for (int i = 0; i < 100; i++) {
            bookPath.add("");
        }
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
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.book_drawer_view_grid_item, null);
        }
        final ImageView imageView = (ImageView) convertView.findViewById(R.id.gridImage);
        String imagepath = imagePath.get(position);
        Bitmap myBitmap = BitmapFactory.decodeFile(imagepath);
        imageView.setImageBitmap(myBitmap);
        RelativeLayout linearLayout = (RelativeLayout) convertView.findViewById(R.id.linearlayout);
        linearLayout.setTag(imagepath);
        if (linearLayout.getTag().equals("")) {
            linearLayout.setOnClickListener(null);
            imageView.setImageResource(0);
        } else {
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imageView.getDrawable() != null) {
                        Intent intent = new Intent(context, TableContent.class);
                        intent.putExtra("bookPath", bookPath.get(position).toString());
                        context.startActivity(intent);
                    } else {

                    }
                }
            });
        }
        return convertView;
    }
}
