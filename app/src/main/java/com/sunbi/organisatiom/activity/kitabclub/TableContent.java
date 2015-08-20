package com.sunbi.organisatiom.activity.kitabclub;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.Spine;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubReader;

public class TableContent extends ListActivity {
    private LayoutInflater inflater;
    private List<RowData> contentDetails;
    private Book book;
    private String bookPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        bookPath=getIntent().getStringExtra("bookPath");
        inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        contentDetails = new ArrayList<>();

        try {
            File file=new File(bookPath);
            InputStream epubInputStream =new FileInputStream(file);
            book = (new EpubReader()).readEpub(epubInputStream);
            book.setSpine(new Spine(book.getTableOfContents()));
            logContentsTable(book.getTableOfContents().getTocReferences(), 0);
        } catch (IOException e) {
            Log.e("epublib", e.getMessage());
        }

        CustomAdapter adapter = new CustomAdapter(this, R.layout.list,
                R.id.title, contentDetails);
        setListAdapter(adapter);
        getListView().setTextFilterEnabled(true);
    }

    private class CustomAdapter extends ArrayAdapter<RowData> {

        public CustomAdapter(Context context, int resource,
                             int textViewResourceId, List<RowData> objects) {
            super(context, resource, textViewResourceId, objects);
        }

        private class ViewHolder {
            private View row;
            private TextView titleHolder = null;

            public ViewHolder(View row) {
                super();
                this.row = row;
            }

            public TextView getTitle() {
                if (null == titleHolder)
                    titleHolder = (TextView) row.findViewById(R.id.title);
                return titleHolder;
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            TextView title = null;
            RowData rowData = getItem(position);
            if (null == convertView) {
                convertView = inflater.inflate(R.layout.list, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            holder = (ViewHolder) convertView.getTag();
            title = holder.getTitle();
            title.setText(rowData.getTitle());
            title.setBackgroundResource(R.drawable.partition);
            title.setGravity(Gravity.CENTER_VERTICAL);
            switch (position) {
                case 0:
                    title.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                    title.setPadding(0,30,0,30);
                    title.setTextSize(20);
                    title.setTypeface(null, Typeface.BOLD);
                    title.setGravity(Gravity.CENTER);
                    break;
            }
            return convertView;
        }

    }


    private void logContentsTable(List<TOCReference> tocReferences, int depth) {
        if (tocReferences == null) {
            return;
        }
        for (TOCReference tocReference : tocReferences) {
            StringBuilder tocString = new StringBuilder();
            for (int i = 0; i < depth; i++) {
                tocString.append("\t");
            }
            tocString.append(tocReference.getTitle());
            RowData row = new RowData();
            row.setTitle(tocString.toString());
            row.setResource(tocReference.getResource());
            contentDetails.add(row);
            logContentsTable(tocReference.getChildren(), depth + 1);
        }
    }

    private class RowData {
        private String title;
        private Resource resource;

        public RowData() {
            super();
        }

        public String getTitle() {
            return title;
        }

        public Resource getResource() {
            return resource;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setResource(Resource resource) {
            this.resource = resource;
        }

    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        RowData rowData = contentDetails.get(position);
        Intent intent = new Intent(TableContent.this, BookReader.class);
        try {
            String pish = "<html><head><style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/font/Times_New_Romance.ttf\")}body {font-family: MyFont;font-size: medium;text-align: justify;}</style></head><body>";
            String pas = "</body></html>";
            String myHtmlString = pish + new String(rowData.getResource().getData()) + pas;
            intent.putExtra("display", myHtmlString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        startActivity(intent);
    }
}
