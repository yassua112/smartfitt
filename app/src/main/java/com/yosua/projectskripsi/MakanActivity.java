package com.yosua.projectskripsi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yosua.projectskripsi.Fragmen.HistoryMakanan;
import com.yosua.projectskripsi.Model.RekMakanan;
import com.yosua.projectskripsi.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import jrizani.jrspinner.JRSpinner;

public class MakanActivity extends AppCompatActivity {

    int id ;
    private Switch ActiveRec;
    private Double Pokokpagi,LaukPagi,PaukPgi,buahPagi,Selang1,Selang2,PokokSiang,LaukSiang,
                PaukSiang,buahSiang,Selang3,Selang4,PokokMalam,LaukMalam,PaukMalam,buahmalam;
    private JRSpinner spinerPokokpagi,spinerLaukpagi,spinerPaukpagi,spinerBuahPagi
            ,spinerPokoksiang,spinerLauksiang,spinerPauksiang,spinerBuahsiang,spinerPokokmalam,spinerLaukmalam,spinerPaukmalam,spinerBuahmalam
            ,spinerMenuSel1,spinerMenuSel2,spinerMenuSel3,spinerMenuSel4;
    private String kalori,menuMakanPgi,MenuMakanSelang1,menuMakanSiang,menuMakanSelang2,menuMakanMalam;
    private String MknPokokpagi,MknLaukPagi,MknPaukPgi,MknbuahPagi,MknSelang1,MknSelang2,MknPokokSiang,MknLaukSiang,
                   MknPaukSiang,MknbuahSiang,MknSelang3,MknSelang4,MknPokokMalam,MknLaukMalam,MknPaukMalam,Mknbuahmalam;
    private int kkMknPokokpagi,kkMknLaukPagi,kkMknPaukPgi,kkMknbuahPagi,kkMknSelang1,kkMknSelang2,kkMknPokokSiang,kkMknLaukSiang,kkMknPaukSiang,
                kkMknbuahSiang,kkMknSelang3,kkMknSelang4,kkMknPokokMalam,kkMknLaukMalam,kkMknPaukMalam,kkMknbuahmalam;
    private TextView reccom;
    private Button saveMkn;







    //TODo:complate to get data from database
    //TODO:Complate to save data to database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makan);
        User user = SharedPrefManager.getInstance(this).getUser();
        id = user.getId();
        spinerPokokpagi = findViewById(R.id.PokokPagi);
        spinerLaukpagi = findViewById(R.id.LaukPagi);
        spinerPaukpagi = findViewById(R.id.PaukPagi);
        spinerBuahPagi = findViewById(R.id.BuahPagi);
        spinerBuahsiang = findViewById(R.id.BuahSiang);
        spinerLauksiang = findViewById(R.id.LaukSiang) ;
        spinerPauksiang = findViewById(R.id.PaukSiang) ;
        spinerPokoksiang = findViewById(R.id.PokokSiang);
        spinerBuahmalam = findViewById(R.id.BuahMalam);
        spinerLaukmalam = findViewById(R.id.LaukMalam);
        spinerPaukmalam = findViewById(R.id.PaukMalam) ;
        spinerPokokmalam = findViewById(R.id.PokokMalam);
        spinerMenuSel1 = findViewById(R.id.PMenuSelang1);
        spinerMenuSel2 = findViewById(R.id.PMenuSelang2);
        spinerMenuSel3 = findViewById(R.id.PMenuSelang3);
        spinerMenuSel4 = findViewById(R.id.PMenuSelang4);
        saveMkn = findViewById(R.id.SaveMkn);
        reccom = findViewById(R.id.RECCOM);

        Intent i = getIntent() ;
        kalori = i.getStringExtra("kalori");
        HitungKebHari();
        getMakanPagi();
        getMakanMalam();
        getMakanSiang();
        getSelangpagi();
        getSelangSore();


        saveMkn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int totalKal = kkMknPokokpagi+kkMknLaukPagi+kkMknPaukPgi+kkMknbuahPagi+kkMknSelang1+kkMknSelang2+kkMknPokokSiang+kkMknLaukSiang+kkMknPaukSiang+
                        kkMknbuahSiang+kkMknSelang3+kkMknSelang4+kkMknPokokMalam+kkMknLaukMalam+kkMknPaukMalam+kkMknbuahmalam;

                new SweetAlertDialog(MakanActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Total Kalori Makanan Anda "+totalKal)
                        .setContentText("Ingin Menyimpan Menu ? ")
                        .setConfirmText("Ya")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.setTitleText("Berhasil")
                                        .setConfirmText("OK")
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                SimpanDataMakanan();

                            }

                        })
                        .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();


            }
        });



    }

    private void HitungKebHari(){
        Double Kkal = Double.valueOf(kalori);
        Double Menupagi,SlngPgi,Menusiang,selingSore,menumalam;

                Menupagi = Kkal * 0.25;
                SlngPgi = Kkal * 0.1;
                Menusiang = Kkal * 0.3;
                selingSore = Kkal * 0.1;
                menumalam = Kkal * 0.25;


            Pokokpagi = Menupagi*0.3;
            LaukPagi= Menupagi*0.2;
            PaukPgi = Menupagi*0.3;
            buahPagi = Menupagi*0.2;

            Selang1= SlngPgi*0.5;
            Selang2= SlngPgi*0.5;

            PokokSiang= Menusiang*0.3;
            LaukSiang= Menusiang*0.2;
            PaukSiang= Menusiang*0.3;
            buahSiang = Menusiang*0.2;

            Selang3= selingSore*0.5;
            Selang4= selingSore*0.5;

            PokokMalam= menumalam*0.3;
            LaukMalam= menumalam*0.2;
            PaukMalam= menumalam*0.3;
            buahmalam = menumalam*0.2;



    }

    public void getMakanPagi(){

        final int mknpokok = 1;
        final int mknlauk =  3;
        final int mknpaik =  6;
        final int mknbuah = 5;
        String URL = "http://sehattiaphari.com/smartfitAPI/rekMakanan.php?mknpokok="+mknpokok+"&mknlauk="+mknlauk+"&mknpauk="+mknpaik+"&mknbuah="+mknbuah+"&api=mknpagi"+"&Pokokpagi="+Pokokpagi+"&LaukPagi="+LaukPagi+"&PaukPgi="+PaukPgi+"&buahPagi="+buahPagi;

            StringRequest stringRequest =  new StringRequest(Request.Method.GET, URL,
                    new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray Arraymkn = obj.getJSONArray("infoMkn");
                    JSONArray Arraymkn2 = obj.getJSONArray("infoMkn1");
                    JSONArray Arraymkn3 = obj.getJSONArray("infoMkn2");
                    JSONArray Arraymkn4 = obj.getJSONArray("infoMkn3");

                    final int n = Arraymkn2.length();
                    final int m = Arraymkn.length();
                    final int o = Arraymkn3.length();
                    final int p = Arraymkn4.length();

                    final ArrayList<RekMakanan> classList = new ArrayList<>();

                    final String[] MknPokok = new String[m];
                    final String[] MknLauk = new String[n];
                    final String[] MknPauk = new String[o];
                    final String[] MknBuah = new String[p];

                    final int[] kkMknPokok = new int[m];
                    final int[] kkMknLauk = new int[n];
                    final int[] kkMknPauk = new int[o];
                    final int[] kkMknBuah = new int[p];

                    //perulangan untuk makanan pokok
                    for (int i = 0 ; i < m ; i++){
                        JSONObject nmMakananPokok = Arraymkn.getJSONObject(i);

                        String nmMakanpokok = nmMakananPokok.getString("nmpokok");
                        int kklnmMakanpokok = nmMakananPokok.getInt("kal_nmpokok");

                        RekMakanan rekMakanan = new RekMakanan();
                        classList.add(rekMakanan);
                        MknPokok[i] = nmMakanpokok;
                        kkMknPokok[i] = kklnmMakanpokok;

                    }

                    //perulangan untk makanan Lauk
                    for(int i = 0 ; i < Arraymkn2.length(); i ++){
                        JSONObject nmMakananLauk = Arraymkn2.getJSONObject(i);
                        String nmMakananlauk = nmMakananLauk.getString("nmlauk");
                        int kklnmMakananlauk = nmMakananLauk.getInt("kal_nmlauk");
                        MknLauk[i] = nmMakananlauk;
                        kkMknLauk[i] = kklnmMakananlauk;
                    }

                    //perulangan untuk makanan pauk
                    for(int i = 0 ; i <Arraymkn4.length() ; i ++){
                        JSONObject nmMakananBuah = Arraymkn4.getJSONObject(i);

                        String nmMakananbuah = nmMakananBuah.getString("nmbuah");
                        int kknmMakananbuah = nmMakananBuah.getInt("kal_nmbuah");
                        RekMakanan rekMakanan =  new RekMakanan();

                        classList.add(rekMakanan);
                        MknBuah[i] = nmMakananbuah ;
                        kkMknBuah[i] = kknmMakananbuah;

                    }




                    //perulangan untuk untuk buah

                    for(int i = 0 ; i <Arraymkn3.length() ; i ++){
                        JSONObject nmMakananPauk = Arraymkn3.getJSONObject(i);

                        String nmMakananpauk = nmMakananPauk.getString("nmpauk");
                        int kknmMakananpauk = nmMakananPauk.getInt("kal_nmpauk");
                        RekMakanan rekMakanan =  new RekMakanan();
                        classList.add(rekMakanan);
                        MknPauk[i] = nmMakananpauk ;
                        kkMknPauk[i]= kknmMakananpauk;

                    }

                    spinerPokokpagi.setItems(MknPokok);

                    spinerPokokpagi.setTitle("Makanan Non Rekomendasi");
                    spinerPokokpagi.setExpandTint(R.color.jrspinner_color_line);
                        spinerPokokpagi.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {

                                if(position < 0 ){

                                    Toast.makeText(MakanActivity.this ,"Anda belum memilih kategori aktivitas anda",Toast.LENGTH_SHORT).show();
                                }else{
                                    MknPokokpagi = MknPokok[position];
                                    kkMknPokokpagi = kkMknPokok[position];

                                    Toast.makeText(MakanActivity.this ,MknPokokpagi ,Toast.LENGTH_SHORT).show();
                                }

                            }
                        });


                    spinerLaukpagi.setItems(MknLauk);
                    spinerLaukpagi.setTitle("Makanan Non Rekomendasi");
                    spinerLaukpagi.setExpandTint(R.color.jrspinner_color_line);

                    spinerLaukpagi.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {

                            if(position < 0 ){

                                Toast.makeText(MakanActivity.this ,"Anda belum memilih kategori aktivitas anda",Toast.LENGTH_SHORT).show();
                            }else{
                                MknLaukPagi = MknLauk[position];
                                kkMknLaukPagi = kkMknLauk[position];

                                Toast.makeText(MakanActivity.this , MknLaukPagi,Toast.LENGTH_SHORT).show();
                            }

                        }
                    });



                    spinerPaukpagi.setItems(MknPauk);
                    spinerPaukpagi.setTitle("Makanan Non Rekomendasi");
                    spinerPaukpagi.setExpandTint(R.color.jrspinner_color_line);

                    spinerPaukpagi.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {

                            if(position < 0 ){

                                Toast.makeText(MakanActivity.this ,"Anda belum memilih kategori aktivitas anda",Toast.LENGTH_SHORT).show();
                            }else{
                                MknPaukPgi = MknPauk[position];
                                kkMknPaukPgi=kkMknPauk[position];

                                Toast.makeText(MakanActivity.this ,MknPaukPgi ,Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                    spinerBuahPagi.setItems(MknBuah);
                    spinerBuahPagi.setTitle("Makanan Non Rekomendasi");
                    spinerBuahPagi.setExpandTint(R.color.jrspinner_color_line);
                    spinerBuahPagi.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {

                            if(position < 0 ){

                                Toast.makeText(MakanActivity.this ,"Anda belum memilih kategori aktivitas anda",Toast.LENGTH_SHORT).show();
                            }else{
                                MknbuahPagi = MknBuah[position];
                                kkMknbuahPagi =kkMknBuah[position];

                                Toast.makeText(MakanActivity.this ,MknbuahPagi ,Toast.LENGTH_SHORT).show();
                            }

                        }
                    });



                }catch (JSONException e){
                    e.printStackTrace();
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        );

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    public void getMakanSiang(){

        final int mknpokok = 1;
        final int mknlauk =  3;
        final int mknpaik =  6;
        final int mknbuah = 5;
        String URL = "http://sehattiaphari.com/smartfitAPI/rekMakanan.php?mknpokok="+mknpokok+"&mknlauk="+mknlauk+"&mknpauk="+mknpaik+"&mknbuah="+mknbuah+"&api=mknsiang"+"&Pokokpagi="+PokokSiang+"&LaukPagi="+LaukSiang+"&PaukPgi="+PaukSiang+"&buahPagi="+buahSiang;

        StringRequest stringRequest =  new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray Arraymkn = obj.getJSONArray("infoMkn");
                            JSONArray Arraymkn2 = obj.getJSONArray("infoMkn1");
                            JSONArray Arraymkn3 = obj.getJSONArray("infoMkn2");
                            JSONArray Arraymkn4 = obj.getJSONArray("infoMkn3");

                            final int n = Arraymkn2.length();
                            final int m = Arraymkn.length();
                            final int o = Arraymkn3.length();
                            final int p = Arraymkn4.length();

                            final ArrayList<RekMakanan> classList = new ArrayList<>();

                            final String[] MknPokok = new String[m];
                            final String[] MknLauk = new String[n];
                            final String[] MknPauk = new String[o];
                            final String[] MknBuah = new String[p];

                            final int[] kkMknPokok = new int[m];
                            final int[] kkMknLauk = new int[n];
                            final int[] kkMknPauk = new int[o];
                            final int[] kkMknBuah = new int[p];

                            //perulangan untuk makanan pokok
                            for (int i = 0 ; i < m ; i++){
                                JSONObject nmMakananPokok = Arraymkn.getJSONObject(i);

                                String nmMakanpokok = nmMakananPokok.getString("nmpokok");
                                int kklnmMakanpokok = nmMakananPokok.getInt("kal_nmpokok");

                                RekMakanan rekMakanan = new RekMakanan();
                                classList.add(rekMakanan);
                                MknPokok[i] = nmMakanpokok;
                                kkMknPokok[i] = kklnmMakanpokok;

                            }

                            //perulangan untk makanan Lauk
                            for(int i = 0 ; i < Arraymkn2.length(); i ++){
                                JSONObject nmMakananLauk = Arraymkn2.getJSONObject(i);
                                String nmMakananlauk = nmMakananLauk.getString("nmlauk");
                                int kklnmMakananlauk = nmMakananLauk.getInt("kal_nmlauk");
                                MknLauk[i] = nmMakananlauk;
                                kkMknLauk[i] = kklnmMakananlauk;
                            }

                            //perulangan untuk makanan pauk
                            for(int i = 0 ; i <Arraymkn4.length() ; i ++){
                                JSONObject nmMakananBuah = Arraymkn4.getJSONObject(i);

                                String nmMakananbuah = nmMakananBuah.getString("nmbuah");
                                int kknmMakananbuah = nmMakananBuah.getInt("kal_nmbuah");
                                RekMakanan rekMakanan =  new RekMakanan();

                                classList.add(rekMakanan);
                                MknBuah[i] = nmMakananbuah ;
                                kkMknBuah[i] = kknmMakananbuah;

                            }




                            //perulangan untuk untuk buah

                            for(int i = 0 ; i <Arraymkn3.length() ; i ++){
                                JSONObject nmMakananPauk = Arraymkn3.getJSONObject(i);

                                String nmMakananpauk = nmMakananPauk.getString("nmpauk");
                                int kknmMakananpauk = nmMakananPauk.getInt("kal_nmpauk");
                                RekMakanan rekMakanan =  new RekMakanan();
                                classList.add(rekMakanan);
                                MknPauk[i] = nmMakananpauk ;
                                kkMknPauk[i]= kknmMakananpauk;

                            }

                            spinerPokoksiang.setItems(MknPokok);
                            spinerPokoksiang.setTitle("Makanan Non Rekomendasi");
                            spinerPokoksiang.setExpandTint(R.color.jrspinner_color_line);
                            spinerPokoksiang.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {

                                    if(position < 0 ){

                                        Toast.makeText(MakanActivity.this ,"Anda belum memilih kategori aktivitas anda",Toast.LENGTH_SHORT).show();
                                    }else{
                                        MknPokokSiang = MknPokok[position];
                                        kkMknPokokSiang = kkMknPokok[position];
                                        Toast.makeText(MakanActivity.this ,MknPokokSiang ,Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                            spinerLauksiang.setItems(MknLauk);
                            spinerLauksiang.setTitle("Makanan Non Rekomendasi");
                            spinerLauksiang.setExpandTint(R.color.jrspinner_color_line);
                            spinerLauksiang.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {

                                    if(position < 0 ){

                                        Toast.makeText(MakanActivity.this ,"Anda belum memilih kategori aktivitas anda",Toast.LENGTH_SHORT).show();
                                    }else{
                                        MknLaukSiang = MknLauk[position];
                                        kkMknLaukSiang = kkMknLauk[position];

                                        Toast.makeText(MakanActivity.this ,MknLaukSiang ,Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                            spinerPauksiang.setItems(MknPauk);
                            spinerPauksiang.setTitle("Makanan Non Rekomendasi");
                            spinerPauksiang.setExpandTint(R.color.jrspinner_color_line);
                            spinerPauksiang.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {

                                    if(position < 0 ){

                                        Toast.makeText(MakanActivity.this ,"Anda belum memilih kategori aktivitas anda",Toast.LENGTH_SHORT).show();
                                    }else{
                                        MknPaukSiang = MknPauk[position];
                                        kkMknPaukSiang = kkMknPauk[position];

                                        Toast.makeText(MakanActivity.this ,MknPaukSiang ,Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                            spinerBuahsiang.setItems(MknBuah);
                            spinerBuahsiang.setTitle("Makanan Non Rekomendasi");
                            spinerBuahsiang.setExpandTint(R.color.jrspinner_color_line);
                            spinerBuahsiang.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {

                                    if(position < 0 ){

                                        Toast.makeText(MakanActivity.this ,"Anda belum memilih kategori aktivitas anda",Toast.LENGTH_SHORT).show();
                                    }else{
                                        MknbuahSiang = MknBuah[position];
                                        kkMknbuahSiang = kkMknBuah[position];


                                        Toast.makeText(MakanActivity.this ,MknbuahSiang ,Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });



                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        );

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void getMakanMalam(){

        final int mknpokok = 1;
        final int mknlauk =  3;
        final int mknpaik =  6;
        final int mknbuah = 5;
        String URL = "http://sehattiaphari.com/smartfitAPI/rekMakanan.php?mknpokok="+mknpokok+"&mknlauk="+mknlauk+"&mknpauk="+mknpaik+"&mknbuah="+mknbuah+"&api=mknmalam"+"&Pokokpagi="+PokokMalam+"&LaukPagi="+LaukMalam+"&PaukPgi="+PaukMalam+"&buahPagi="+buahmalam;

        StringRequest stringRequest =  new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray Arraymkn = obj.getJSONArray("infoMkn");
                            JSONArray Arraymkn2 = obj.getJSONArray("infoMkn1");
                            JSONArray Arraymkn3 = obj.getJSONArray("infoMkn2");
                            JSONArray Arraymkn4 = obj.getJSONArray("infoMkn3");

                            final int n = Arraymkn2.length();
                            final int m = Arraymkn.length();
                            final int o = Arraymkn3.length();
                            final int p = Arraymkn4.length();

                            final ArrayList<RekMakanan> classList = new ArrayList<>();

                            final String[] MknPokok = new String[m];
                            final String[] MknLauk = new String[n];
                            final String[] MknPauk = new String[o];
                            final String[] MknBuah = new String[p];

                            final int[] kkMknPokok = new int[m];
                            final int[] kkMknLauk = new int[n];
                            final int[] kkMknPauk = new int[o];
                            final int[] kkMknBuah = new int[p];

                            //perulangan untuk makanan pokok
                            for (int i = 0 ; i < m ; i++){
                                JSONObject nmMakananPokok = Arraymkn.getJSONObject(i);

                                String nmMakanpokok = nmMakananPokok.getString("nmpokok");
                                int kklnmMakanpokok = nmMakananPokok.getInt("kal_nmpokok");

                                RekMakanan rekMakanan = new RekMakanan();
                                classList.add(rekMakanan);
                                MknPokok[i] = nmMakanpokok;
                                kkMknPokok[i] = kklnmMakanpokok;

                            }

                            //perulangan untk makanan Lauk
                            for(int i = 0 ; i < Arraymkn2.length(); i ++){
                                JSONObject nmMakananLauk = Arraymkn2.getJSONObject(i);
                                String nmMakananlauk = nmMakananLauk.getString("nmlauk");
                                int kklnmMakananlauk = nmMakananLauk.getInt("kal_nmlauk");
                                MknLauk[i] = nmMakananlauk;
                                kkMknLauk[i] = kklnmMakananlauk;
                            }

                            //perulangan untuk makanan pauk
                            for(int i = 0 ; i <Arraymkn4.length() ; i ++){
                                JSONObject nmMakananBuah = Arraymkn4.getJSONObject(i);

                                String nmMakananbuah = nmMakananBuah.getString("nmbuah");
                                int kknmMakananbuah = nmMakananBuah.getInt("kal_nmbuah");
                                RekMakanan rekMakanan =  new RekMakanan();

                                classList.add(rekMakanan);
                                MknBuah[i] = nmMakananbuah ;
                                kkMknBuah[i] = kknmMakananbuah;

                            }




                            //perulangan untuk untuk buah

                            for(int i = 0 ; i <Arraymkn3.length() ; i ++){
                                JSONObject nmMakananPauk = Arraymkn3.getJSONObject(i);

                                String nmMakananpauk = nmMakananPauk.getString("nmpauk");
                                int kknmMakananpauk = nmMakananPauk.getInt("kal_nmpauk");
                                RekMakanan rekMakanan =  new RekMakanan();
                                classList.add(rekMakanan);
                                MknPauk[i] = nmMakananpauk ;
                                kkMknPauk[i]= kknmMakananpauk;

                            }

                            spinerPokokmalam.setItems(MknPokok);
                            spinerPokokmalam.setTitle("Makanan Non Rekomendasi");
                            spinerPokokmalam.setExpandTint(R.color.jrspinner_color_line);
                            spinerPokokmalam.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {

                                    if(position < 0 ){

                                        Toast.makeText(MakanActivity.this ,"Anda belum memilih kategori aktivitas anda",Toast.LENGTH_SHORT).show();
                                    }else{
                                        MknPokokMalam = MknPokok[position];
                                        kkMknPokokMalam = kkMknPokok[position];

                                        Toast.makeText(MakanActivity.this ,MknPokokMalam ,Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                            spinerLaukmalam.setItems(MknLauk);
                            spinerLaukmalam.setTitle("Makanan Non Rekomendasi");
                            spinerLaukmalam.setExpandTint(R.color.jrspinner_color_line);
                            spinerLaukmalam.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {

                                    if(position < 0 ){

                                        Toast.makeText(MakanActivity.this ,"Anda belum memilih kategori aktivitas anda",Toast.LENGTH_SHORT).show();
                                    }else{
                                        MknLaukMalam = MknLauk[position];
                                        kkMknLaukMalam = kkMknLauk[position];

                                        Toast.makeText(MakanActivity.this ,MknLaukMalam ,Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                            spinerPaukmalam.setItems(MknPauk);
                            spinerPaukmalam.setTitle("Makanan Non Rekomendasi");
                            spinerPaukmalam.setExpandTint(R.color.jrspinner_color_line);
                            spinerPaukmalam.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {

                                    if(position < 0 ){

                                        Toast.makeText(MakanActivity.this ,"Anda belum memilih kategori aktivitas anda",Toast.LENGTH_SHORT).show();
                                    }else{
                                        MknPaukMalam = MknPauk[position];
                                        kkMknPaukMalam = kkMknPauk[position];


                                        Toast.makeText(MakanActivity.this ,MknPaukMalam ,Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                            spinerBuahmalam.setItems(MknBuah);
                            spinerBuahmalam.setTitle("Makanan Non Rekomendasi");
                            spinerBuahmalam.setExpandTint(R.color.jrspinner_color_line);
                            spinerBuahmalam.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {

                                    if(position < 0 ){

                                        Toast.makeText(MakanActivity.this ,"Anda belum memilih kategori aktivitas anda",Toast.LENGTH_SHORT).show();
                                    }else{
                                       Mknbuahmalam = MknBuah[position];
                                       kkMknbuahmalam = kkMknBuah[position];


                                        Toast.makeText(MakanActivity.this ,Mknbuahmalam ,Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });



                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        );

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void getSelangpagi(){
        final int menuBuah = 5;
        final int menuSusu = 8;
        final String URL = "http://sehattiaphari.com/smartfitAPI/rekMakanan.php?mknpokok="+menuBuah+"&mknlauk="+menuSusu+"&api=selangpagi"+"&Pokokpagi="+Selang1+"&LaukPagi="+Selang2;

        StringRequest stringRequest =  new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArrayBH = obj.getJSONArray("infobuah");
                            JSONArray JsonArraySS = obj.getJSONArray("infosusu");

                            final int a = jsonArrayBH.length();
                            final int b = JsonArraySS.length();
                            final ArrayList<RekMakanan> classList = new ArrayList<>();
                            final String[] Databuah = new String[a];
                            final String[] DataSusu = new String[b];
                            final int[] kkDatabuah = new int[a];
                            final int[] kkDataSusu = new int[b];

                            for (int i = 0 ; i < a ; i ++){
                                JSONObject JBUAH = jsonArrayBH.getJSONObject(i);

                                String BUAH = JBUAH.getString("nmbuah");
                                int kkBUAH = JBUAH.getInt("kkbuah");

                                RekMakanan rekMakanan = new RekMakanan();
                                rekMakanan.setBuah(BUAH);
                                classList.add(rekMakanan);
                                Databuah[i] = BUAH;
                                kkDatabuah[i] = kkBUAH;

                            }

                            for (int i = 0 ; i < b ; i ++){
                                JSONObject JSUSU = JsonArraySS.getJSONObject(i);

                                String SUSU = JSUSU.getString("nmsusu");
                                int kkSUSU = JSUSU.getInt("kksusu");

                                RekMakanan rekMakanan = new RekMakanan();
                                rekMakanan.setBuah(SUSU);
                                classList.add(rekMakanan);

                                kkDataSusu[i] = kkSUSU;
                                DataSusu[i] = SUSU;
                            }



                        spinerMenuSel1.setItems(Databuah);
                        spinerMenuSel1.setTitle("Non Rekomendasi Menu 1");
                        spinerMenuSel1.setExpandTint(R.color.jrspinner_color_line);
                        spinerMenuSel1.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                MknSelang1 = Databuah[position];
                                kkMknSelang1 = kkDatabuah[position];

                                Toast.makeText(getApplicationContext(),""+MknSelang1,Toast.LENGTH_SHORT).show();
                            }
                        });

                        spinerMenuSel2.setItems(DataSusu);
                        spinerMenuSel2.setTitle("Non Rekomendasi Menu 2");
                        spinerMenuSel2.setExpandTint(R.color.jrspinner_color_line);
                        spinerMenuSel2.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                MknSelang2 = DataSusu[position];
                                kkMknSelang2 = kkDataSusu[position];

                                Toast.makeText(getApplicationContext(),""+MknSelang2,Toast.LENGTH_SHORT).show();
                            }
                        });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void getSelangSore(){
        final int menuBuah = 5;
        final int menuSusu = 8;
        final String URL = "http://sehattiaphari.com/smartfitAPI/rekMakanan.php?mknpokok="+menuBuah+"&mknlauk="+menuSusu+"&api=selangsore"+"&Pokokpagi="+Selang3+"&LaukPagi="+Selang4;

        StringRequest stringRequest =  new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArrayBH = obj.getJSONArray("infobuah");
                            JSONArray JsonArraySS = obj.getJSONArray("infosusu");

                            final int a = jsonArrayBH.length();
                            final int b = JsonArraySS.length();
                            final ArrayList<RekMakanan> classList = new ArrayList<>();
                            final String[] Databuah = new String[a];
                            final String[] DataSusu = new String[b];
                            final int[] kkDatabuah = new int[a];
                            final int[] kkDataSusu = new int[b];

                            for (int i = 0 ; i < a ; i ++){
                                JSONObject JBUAH = jsonArrayBH.getJSONObject(i);

                                String BUAH = JBUAH.getString("nmbuah");
                                int kkBUAH = JBUAH.getInt("kkbuah");

                                RekMakanan rekMakanan = new RekMakanan();
                                rekMakanan.setBuah(BUAH);
                                classList.add(rekMakanan);
                                Databuah[i] = BUAH;
                                kkDatabuah[i] = kkBUAH;

                            }

                            for (int i = 0 ; i < b ; i ++){
                                JSONObject JSUSU = JsonArraySS.getJSONObject(i);

                                String SUSU = JSUSU.getString("nmsusu");
                                int kkSUSU = JSUSU.getInt("kksusu");

                                RekMakanan rekMakanan = new RekMakanan();
                                rekMakanan.setBuah(SUSU);
                                classList.add(rekMakanan);

                                kkDataSusu[i] = kkSUSU;
                                DataSusu[i] = SUSU;
                            }


                            spinerMenuSel3.setItems(Databuah);
                            spinerMenuSel3.setTitle("Non Rekomendasi Menu 1");
                            spinerMenuSel3.setExpandTint(R.color.jrspinner_color_line);

                            spinerMenuSel3.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    MknSelang3 = Databuah[position];
                                    kkMknSelang3 = kkDatabuah[position];


                                    Toast.makeText(getApplicationContext(),""+MknSelang3,Toast.LENGTH_SHORT).show();
                                }
                            });

                            spinerMenuSel4.setItems(DataSusu);
                            spinerMenuSel4.setTitle("Non Rekomendasi Menu 1");
                            spinerMenuSel4.setExpandTint(R.color.jrspinner_color_line);
                            spinerMenuSel4.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    MknSelang4 = DataSusu[position];
                                    kkMknSelang4 = kkDataSusu[position];

                                    Toast.makeText(getApplicationContext(),""+MknSelang4,Toast.LENGTH_SHORT).show();

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void SimpanDataMakanan(){
        final int ID = id ;
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final String NowD = format.format(date);
        menuMakanPgi = MknPokokpagi+","+ MknLaukPagi+","+ MknPaukPgi+","+ MknbuahPagi;
        menuMakanSiang = MknPokokSiang+","+ MknLaukSiang+","+ MknPaukSiang+","+ MknbuahSiang;
        menuMakanMalam = MknPokokMalam+","+ MknLaukMalam+","+ MknPaukMalam+","+ Mknbuahmalam;
        MenuMakanSelang1 = MknSelang1+","+ MknSelang2;
        menuMakanSelang2 = MknSelang3+","+ MknSelang4;
        final int totalkalori = kkMknPokokpagi+kkMknLaukPagi+kkMknPaukPgi+kkMknbuahPagi+kkMknSelang1+kkMknSelang2+kkMknPokokSiang+kkMknLaukSiang+kkMknPaukSiang+
                kkMknbuahSiang+kkMknSelang3+kkMknSelang4+kkMknPokokMalam+kkMknLaukMalam+kkMknPaukMalam+kkMknbuahmalam;
        final String URL = "http://sehattiaphari.com/smartfitAPI/rekMakanan.php?api=SaveFood";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //converting response to Json object
                    JSONObject obj = new JSONObject(response);

                    //if no error in respon
                    if(!obj.getBoolean("error")){
                        Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_SHORT).show();


                    }else{
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @Override
            protected Map<String,String>getParams()throws AuthFailureError{
                Map<String,String> param = new HashMap<>();
                param.put("id_user",String.valueOf(ID));
                param.put("MenuMakanPagi",menuMakanPgi);
                param.put("MenuMakanSelang1",MenuMakanSelang1);
                param.put("MenuMakanSiang",menuMakanSiang);
                param.put("MenuMakanSelang2",menuMakanSelang2);
                param.put("MenuMakanMakanMalam",menuMakanMalam);
                param.put("kaloriTol",Integer.toString(totalkalori));
                param.put("w_date",NowD);
                return param;
            }
        };
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
            startActivity(new Intent(getApplicationContext(),MainActivity.class));


    }


    @Override
    protected void onResume() {
        super.onResume();
    }


}
