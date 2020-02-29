package com.yosua.projectskripsi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.alexfu.countdownview.CountDownView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yosua.projectskripsi.Model.Olahraga;
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

import jrizani.jrspinner.JRSpinner;

public class OlahrgaActivity extends AppCompatActivity {

    private static final String TAG = "OlahrgaActivity";
    Activity activity;
    private JRSpinner mJRSpinner;
    private CountDownView countDownView;
    private final int MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION = 123;

    private EditText WaktuOlng;
    private Button SVOlahraga,starActiv,PauseActiv;
    private LinearLayout activlay;
    private TextView kkalburn,onOFF,Info;
    private int id,menit;
    private String olaharaga,kkalolahraga,id_olahraga;
    float hasil = 0;

    Handler mhandler;
    private volatile boolean Stop = false;
    private Switch RecOnOFF;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olahrga);

        mJRSpinner = findViewById(R.id.Solahraga);
        WaktuOlng = findViewById(R.id.Time);
        SVOlahraga = findViewById(R.id.SaveOlngraga);
        activlay = findViewById(R.id.RecActivity);
        starActiv = findViewById(R.id.StartActiv);
        PauseActiv = findViewById(R.id.PauseActiv);
        kkalburn = findViewById(R.id.kkalBurn);
        countDownView = findViewById(R.id.CD);
        RecOnOFF = findViewById(R.id.switch1);
        onOFF = findViewById(R.id.OlhOnOFF);
        Info = findViewById(R.id.info);





        User user = SharedPrefManager.getInstance(this).getUser();
        id = user.getId();

        loaddataNotRec();
        RecOnOFF.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    Toast.makeText(getApplicationContext(),"ON",Toast.LENGTH_SHORT).show();
                    loaddata();
                    onOFF.setText("Reccomendation ON");
                }else if(!buttonView.isChecked()){
                    Toast.makeText(getApplicationContext(),"OFF",Toast.LENGTH_SHORT).show();
                    loaddataNotRec();
                    onOFF.setText("Reccomendation OFF");
                }
            }
        });


        WaktuOlng.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Info.setText("0");

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    String waktu2 = WaktuOlng.getText().toString();
                    int XXInto = Integer.parseInt(kkalolahraga)*Integer.parseInt(waktu2);
                    Info.setText(XXInto+" KKal terbakar/"+waktu2+" Menit");

                }catch (NumberFormatException e){
                    e.getMessage();
                }
            }
        });



        starActiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String waktu = WaktuOlng.getText().toString().intern();


                activlay.setVisibility(View.VISIBLE);

                PauseActiv.setEnabled(true);
                starActiv.setEnabled(false);


                StartCoundown(waktu);

            }
        });


        SVOlahraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanDataOlahraga();

            }
        });




    }

    private void loaddataNotRec() {
        final String Id = String.valueOf(id) ;
        String URL_OLAGRAGA = "http://sehattiaphari.com/smartfitAPI/api.php/?api=nonRec";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_OLAGRAGA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject obj = new JSONObject(response);
                            JSONArray dtamkn = obj.getJSONArray("infoUser");
                            final int m = dtamkn.length();
                            final ArrayList<Olahraga> classList = new ArrayList<>();
                            final String[] spinnerArray = new String[m];
                            final String[] olahragaKkal = new String[m];
                            final String[] OlahragaName = new String[m];
                            final String[] Id_olahraga = new String[m];



                            for(int i = 0;i < m ; i++ ){

                                JSONObject jsonObject =  dtamkn.getJSONObject(i);

                                String Olahraga = jsonObject.getString("nm_olahraga");
                                int kkal = jsonObject.getInt("kkalOlg");
                                int id_olahraga = jsonObject.getInt("id_olahraga");
                                Olahraga olahraga = new Olahraga(Olahraga,kkal,id_olahraga);



                                classList.add(olahraga);
                                String olahRaga = "Olahraga "+Olahraga+"("+kkal+")kkal/min" ;
                                spinnerArray[i] = Olahraga;
                                olahragaKkal[i] = Integer.toString(kkal);
                                OlahragaName[i] = olahRaga;
                                Id_olahraga[i]= Integer.toString(id_olahraga);

                            }

//
                            mJRSpinner.setItems(OlahragaName);
                            mJRSpinner.setTitle("Rekomendasi Olahraga");
                            mJRSpinner.setExpandTint(R.color.jrspinner_color_line);
                            mJRSpinner.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {

                                    if(position < 0 ){

                                        Toast.makeText(OlahrgaActivity.this ,"Anda belum memilih kategori aktivitas anda",Toast.LENGTH_SHORT).show();
                                    }else{
                                        olaharaga = spinnerArray[position];
                                        kkalolahraga = olahragaKkal[position];
                                        id_olahraga = Id_olahraga[position];

                                        Toast.makeText(OlahrgaActivity.this ,olaharaga + kkalolahraga,Toast.LENGTH_SHORT).show();
                                        activlay.setVisibility(View.VISIBLE);
                                    }
                                }
                            });

                        }catch (JSONException e){
                            Toast.makeText(OlahrgaActivity.this,""+e,Toast.LENGTH_SHORT).show();
                        }
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OlahrgaActivity.this ,error.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void loaddata(){

        final String Id = String.valueOf(id) ;
        String URL_OLAGRAGA = "http://sehattiaphari.com/smartfitAPI/api.php/?id="+Id+"&api=get";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_OLAGRAGA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject obj = new JSONObject(response);
                            JSONArray dtamkn = obj.getJSONArray("infoUser");
                            final int m = dtamkn.length();
                            final ArrayList<Olahraga> classList = new ArrayList<>();
                            final String[] spinnerArray = new String[m];
                            final String[] olahragaKkal = new String[m];
                            final String[] OlahragaName = new String[m];
                            final String[] Id_olahraga = new String[m];



                            for(int i = 0;i < m ; i++ ){

                                JSONObject jsonObject =  dtamkn.getJSONObject(i);

                                String Olahraga = jsonObject.getString("nm_olahraga");
                                int kkal = jsonObject.getInt("kkalOlg");

                                int id_olahraga = jsonObject.getInt("id_olahraga");
                                Olahraga olahraga = new Olahraga(Olahraga,kkal,id_olahraga);



                                classList.add(olahraga);
                                String olahRaga = "Olahraga "+Olahraga+"("+kkal+")kkal/min" ;
                                spinnerArray[i] = Olahraga;
                                olahragaKkal[i] = Integer.toString(kkal);
                                OlahragaName[i] = olahRaga;
                                Id_olahraga[i]= Integer.toString(id_olahraga);

                            }

                            mJRSpinner.setItems(OlahragaName);
                            mJRSpinner.setTitle("Rekomendasi Olahraga");
                            mJRSpinner.setExpandTint(R.color.jrspinner_color_line);

                            mJRSpinner.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {

                                    if(position < 0 ){

                                        Toast.makeText(OlahrgaActivity.this ,"Anda belum memilih kategori aktivitas anda",Toast.LENGTH_SHORT).show();
                                    }else{
                                        olaharaga = spinnerArray[position];
                                        kkalolahraga = olahragaKkal[position];
                                        id_olahraga = Id_olahraga[position];



                                        Toast.makeText(OlahrgaActivity.this ,olaharaga + kkalolahraga,Toast.LENGTH_SHORT).show();
                                        activlay.setVisibility(View.VISIBLE);
                                    }
                                }
                            });

                        }catch (JSONException e){
                           Toast.makeText(OlahrgaActivity.this,""+e,Toast.LENGTH_SHORT).show();
                        }
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OlahrgaActivity.this ,error.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }




    //
//    public void HitungOlahraga(){
//        final String namaOlahraga =  olaharaga;
//        final String kalolahraga =  kkalolahraga;
//
//
//    }
//

    public void StartCoundown(String Waktu){


        try{
            menit = Integer.valueOf(Waktu);
            int kkal = Integer.valueOf(kkalolahraga);
            int sec = 1*60;
            int millsec = menit*60*1000;
            float kkaldet = (float)kkal/(float)sec;



            countDownView.setStartDuration(millsec);
            countDownView.start();

            CountCallgotBurn countCallgotBurn1 = new CountCallgotBurn(menit,kkaldet);
            countCallgotBurn1.start();
            Stop = false;




            PauseActiv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    PauseActiv.setEnabled(false);
                    countDownView.stop();
                    PauseActiv.setVisibility(View.GONE);
                    starActiv.setVisibility(View.GONE);
                    SVOlahraga.setVisibility(View.VISIBLE);
                    Stop = true;

                }
            });

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_SHORT).show();
            starActiv.setEnabled(true);
        }
    }
    private class CountCallgotBurn extends Thread {
        int sec;
        float kkaldet;
        float temp = 0;

        CountCallgotBurn(int minutes, float kkaldet) {

            this.kkaldet = kkaldet;
            this.sec = minutes * 60;
        }

        @Override
        public void run() {
            for (int i = 0; i <= sec; i++) {
                if(Stop)
                    return;
                    temp = temp + kkaldet;
                    hasil = temp;
                    try {
                        kkalburn.setText("" + String.format("%.2f", hasil) + "Calori");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }

        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    //TODO:complate this methode to save the activity
    public void simpanDataOlahraga(){
      Toast.makeText(getApplicationContext(),"TEST",Toast.LENGTH_SHORT).show();
        final String URL = "http://sehattiaphari.com/smartfitAPI/api.php/?api=set";
        final String ID = String.valueOf(id);
        final String ID_olahraga = String.valueOf(id_olahraga);
        final String Hasil = String.valueOf(hasil);
        final String waktu = String.valueOf(menit);
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final String NowD = format.format(date);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Ada Kesalahan Saat Menyimpan Data",Toast.LENGTH_SHORT).show();
            }
    }){

            @Override
            protected Map<String, String>getParams()throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("id_user",(ID));
                param.put("id_olahraga",ID_olahraga);
                param.put("date",NowD);
                param.put("waktu",waktu);
                param.put("kalori",Hasil);
                return param;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));


    }



}
