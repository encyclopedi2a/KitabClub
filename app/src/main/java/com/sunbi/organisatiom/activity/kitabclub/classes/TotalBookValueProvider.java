package com.sunbi.organisatiom.activity.kitabclub.classes;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunbi.organisatiom.activity.kitabclub.R;
import com.sunbi.organisatiom.activity.kitabclub.interfaces.BooksPriceProvider;

/**
 * Created by gokarna on 8/20/15.
 */
public class TotalBookValueProvider implements View.OnClickListener {
    private Context context;
    private ImageView next, previous;
    private TextView subTotal, quantity;
    private Button add;
    private String price;
    private BooksPriceProvider booksPriceProvider;
    private AlertDialog alertDialog;

    public TotalBookValueProvider(Context context, String price, BooksPriceProvider booksPriceProvider) {
        this.context = context;
        this.price = price;
        this.booksPriceProvider = booksPriceProvider;
    }

    public void returnTotalNumberofBooks() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_dialog, null);
        next = (ImageView) view.findViewById(R.id.next);
        next.setOnClickListener(this);
        quantity = (TextView) view.findViewById(R.id.quantity);
        previous = (ImageView) view.findViewById(R.id.previous);
        previous.setOnClickListener(this);
        subTotal = (TextView) view.findViewById(R.id.subtotal);
        subTotal.setText("$" + price);
        add = (Button) view.findViewById(R.id.add);
        add.setOnClickListener(this);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.next:
                this.quantity.setText(String.valueOf(Integer.parseInt(this.quantity.getText().toString()) + 1));
                subTotal.setText("$" + String.valueOf(Integer.parseInt(this.quantity.getText().toString()) * Integer.parseInt(price)));
                break;
            case R.id.previous:
                if (Integer.parseInt(this.quantity.getText().toString()) > 0) {
                    this.quantity.setText(String.valueOf(Integer.parseInt(this.quantity.getText().toString()) - 1));
                    subTotal.setText("$" + String.valueOf(Integer.parseInt(this.quantity.getText().toString()) * Integer.parseInt(price)));
                }
                break;
            case R.id.add:
                booksPriceProvider.setTotalPrice(subTotal.getText().toString().substring(1), this.quantity.getText().toString());
                alertDialog.dismiss();
                break;
        }
    }
}
