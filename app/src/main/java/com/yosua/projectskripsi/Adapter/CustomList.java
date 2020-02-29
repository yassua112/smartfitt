package com.yosua.projectskripsi.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.yosua.projectskripsi.R;

public class CustomList extends ArrayAdapter<String> {


    private final Activity context;
    private final String[] menu;
    private final int[] kkaltot;
    private final String[] tgL;
    public CustomList(Activity context,
                      String[] Menu, int[] Kkaltot, String[] tgl) {
        super(context, R.layout.listview_hismkn, Menu);
        this.context = context;
        this.menu = Menu;
        this.kkaltot = Kkaltot;
        this.tgL = tgl;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.listview_hismkn, null, true);
        TextView MenuMkn = (TextView) rowView.findViewById(R.id.ShowMenu);
        TextView kkal = (TextView) rowView.findViewById(R.id.jmlkan);
        TextView tgl = (TextView)rowView.findViewById(R.id.Tanggal);

        MenuMkn.setText(menu[position]);
        kkal.setText(String.valueOf(kkaltot[position]));
        tgl.setText(tgL[position]);


        return rowView;
    }
}
