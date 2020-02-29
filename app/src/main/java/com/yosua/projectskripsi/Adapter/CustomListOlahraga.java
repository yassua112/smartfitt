package com.yosua.projectskripsi.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.yosua.projectskripsi.R;

public class CustomListOlahraga extends ArrayAdapter<String> {


    private final Activity context;
    private final String[] nm_olahrga;
    private final String[] tgL;
    private final int[] waktu;
    private final float[]kkaltot;
    public CustomListOlahraga(Activity context,
                      String[] nm_olahrga, String[] tgL, int[] waktu, float[] kkaltot) {
        super(context, R.layout.listview_hismkn, nm_olahrga);
        this.context = context;
        this.nm_olahrga = nm_olahrga;
        this.tgL = tgL;
        this.waktu = waktu;
        this.kkaltot = kkaltot;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.listview_hisolah, null, true);
        TextView nm_olas = (TextView) rowView.findViewById(R.id.ShowOlh);
        TextView Waktu = (TextView) rowView.findViewById(R.id.Waktus);
        TextView tgl = (TextView)rowView.findViewById(R.id.Tanggals);
        TextView klas = (TextView)rowView.findViewById(R.id.Kals);


        nm_olas.setText(nm_olahrga[position]);
        Waktu.setText(waktu[position]+" Menit");
        tgl.setText(tgL[position]);
        klas.setText(kkaltot[position]+" Kalori");

        return rowView;
    }
}
