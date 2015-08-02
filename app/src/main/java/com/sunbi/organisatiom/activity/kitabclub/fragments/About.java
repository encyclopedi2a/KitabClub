package com.sunbi.organisatiom.activity.kitabclub.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunbi.organisatiom.activity.kitabclub.R;

public class About extends Fragment {
    private TextView textView;
    private View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_about, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textView=(TextView)view.findViewById(R.id.description);
        textView.setText(Html.fromHtml(descriptionContent()));
    }
    private String descriptionContent(){
        String description="<b>KitabClub</b><br><br>In the business for over many years , we at Kitabclub specialize in attaining exclusive watches at incredible prices. We are also members of the internatonal " +
                "books Association which provides us access to the most luxurious and exclusive books nt he world.";
        return description;
    }
}
