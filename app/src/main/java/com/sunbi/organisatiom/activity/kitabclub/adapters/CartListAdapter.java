package com.sunbi.organisatiom.activity.kitabclub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sunbi.organisatiom.activity.kitabclub.R;
import com.sunbi.organisatiom.activity.kitabclub.models.SQLiteData;
import com.sunbi.organisatiom.activity.kitabclub.sqlitedatabase.DatabaseOperation;

import java.util.List;

/**
 * Created by gokarna on 8/21/15.
 */
public class CartListAdapter extends BaseAdapter {
    private Context context;
    private List<SQLiteData> list;
    private ListView cartListView;
    private TextView total;
    public CartListAdapter(Context context,List<SQLiteData> list,ListView cartListView,TextView total) {
        this.context=context;
        this.list=list;
        this.cartListView=cartListView;
        this.total=total;
    }
    public CartListAdapter(Context context,TextView textView){

    }

    @Override
    public int getCount() {
        return list.size();
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
            grid = inflater.inflate(R.layout.custom_cart_viewer, null);
            SQLiteData data= list.get(position);
            final ViewHolder viewHolder=new ViewHolder();
            viewHolder.bookImage=(ImageView)grid.findViewById(R.id.bookimage);
            Picasso.with(context)
                    .load(data.getBookImage())
                    .into(viewHolder.bookImage);
            viewHolder.bookName=(TextView)grid.findViewById(R.id.bookname);
            viewHolder.bookName.setText(data.getBookName());
            viewHolder.counter=(TextView)grid.findViewById(R.id.counter);
            viewHolder.counter.setText(data.getBookQuantity() + " x $" + data.getBookPrice());
            viewHolder.subTotal=(TextView)grid.findViewById(R.id.total);
            viewHolder.subTotal.setText(String.valueOf("$"+Integer.parseInt(data.getBookQuantity())*Integer.parseInt(data.getBookPrice())));
            viewHolder.delete=(ImageView)grid.findViewById(R.id.delete);
            viewHolder.delete.setTag(data.getId());
            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SQLiteData data=new SQLiteData();
                    data.setId(Integer.parseInt(viewHolder.delete.getTag().toString()));
                    new DatabaseOperation(context).deleteData(data);
                    list=new DatabaseOperation(context).getAllData();
                    //setting cart list adapter after the order has been deleted
                    CartListAdapter cartListAdapter=new CartListAdapter(context,list,cartListView,total);
                    cartListView.setAdapter(cartListAdapter);
                    int totalSum=0;
                    for(int i=0; i<list.size();i++){
                        SQLiteData totalData=list.get(i);
                        totalSum=totalSum+(Integer.parseInt(totalData.getBookQuantity())*Integer.parseInt(totalData.getBookPrice()));
                    }
                    total.setText(("$"+totalSum));
                }
            });
        } else {
            grid = convertView;
        }
        return grid;
    }
    private class ViewHolder{
        ImageView bookImage,delete;
        TextView bookName,subTotal,counter;
    }
}
