package com.sunbi.organisatiom.activity.kitabclub;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.Resources;
import nl.siegmann.epublib.domain.Spine;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubReader;
import nl.siegmann.epublib.service.MediatypeService;

public class TableContent extends ListActivity {
    private List<RowData> contentDetails;
    private Book book;
    private String bookPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        bookPath = getIntent().getStringExtra("bookPath");
        contentDetails = new ArrayList<>();

        try {
            File file = new File(bookPath);
            InputStream epubInputStream = new FileInputStream(file);
            book = (new EpubReader()).readEpub(epubInputStream);
            downloadResource(file, bookPath);
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

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            RowData rowData = getItem(position);
            if (null == convertView) {
                LayoutInflater inflater=(LayoutInflater)TableContent.this.getSystemService(TableContent.this.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list, null);
            }
            TextView title=(TextView)convertView.findViewById(R.id.title);
            title.setText(rowData.getTitle());
            title.setGravity(Gravity.CENTER_VERTICAL);
            switch (position) {
                case 0:
                    title.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                    title.setPadding(10, 30, 0, 30);
                    title.setTextSize(20);
                    title.setTypeface(null, Typeface.BOLD);
                    title.setGravity(Gravity.CENTER);
                    break;
                default:
                    title.setTextColor(Color.parseColor("#b3000000"));
                    title.setPadding(10, 30, 0, 30);

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
            String pish = "<html><head><style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/Times_New_Romance.ttf\")}body {font-family: MyFont;font-size: medium;text-align: justify;}</style></head><body>";
            String pas = "</body></html>";
            String myHtmlString = pish + new String(rowData.getResource().getData()) + pas;
            intent.putExtra("display", myHtmlString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        startActivity(intent);
    }

    private void downloadResource(File file, String path) {
        try {
            InputStream epubis = new FileInputStream(file);
            book = (new EpubReader()).readEpub(epubis);
            Resources rst = book.getResources();
            Collection<Resource> clrst = rst.getAll();
            Iterator<Resource> itr = clrst.iterator();

            while (itr.hasNext()) {
                Resource rs = itr.next();

                if ((rs.getMediaType() == MediatypeService.JPG)
                        || (rs.getMediaType() == MediatypeService.PNG)
                        || (rs.getMediaType() == MediatypeService.GIF)) {
                    File oppath1 = new File(path, "Images/"
                            + rs.getHref().replace("Images/", ""));

                    oppath1.getParentFile().mkdirs();
                    oppath1.createNewFile();

                    FileOutputStream fos1 = new FileOutputStream(oppath1);
                    fos1.write(rs.getData());
                    fos1.close();

                } else if (rs.getMediaType() == MediatypeService.CSS) {
                    File oppath = new File(path, "Styles/"
                            + rs.getHref().replace("Styles/", ""));

                    oppath.getParentFile().mkdirs();
                    oppath.createNewFile();

                    FileOutputStream fos = new FileOutputStream(oppath);
                    fos.write(rs.getData());
                    fos.close();

                }

            }


        } catch (Exception e) {
            Log.v("error", e.getMessage());
        }
    }
}
