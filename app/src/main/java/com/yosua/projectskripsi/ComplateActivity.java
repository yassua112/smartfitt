package com.yosua.projectskripsi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yosua.projectskripsi.Model.ComplateProfile;
import com.yosua.projectskripsi.Model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class ComplateActivity extends AppCompatActivity {


    private static final String TAG = "ComplateActivity";
    private PopupWindow popupWindow;
    private TextView C_Username, Vtinggi, Vberat,infoKalori,InfoTglLaghir,InfoGender,InfoTinggi,infoBeratBandan,InfoImt;
    private Spinner aktivitas;
    private RadioGroup Ggenre;
    private RadioButton Pria, wanita;
    double ValueAktivitas, Naktivitas;
    private int mYear, mDay, mMonth, umur, Usia;
    private String Keterangan, Status, JenisKelamin, gender;
    private TextView mDateDisplay;
    private Button mPickDate, mUpdate;
    static final int DATE_DIALOG_ID = 0;
    private int KkalSems;
    private double JumlahKalori;
    private int tinggi, berat, id;
    private LinearLayout provile, isiProvile;
    private double IMT;
    private double valnewIMT;
    private ImageView infoIMT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complate);

        final DecimalFormat Dkkal = new DecimalFormat("####,###");
        aktivitas = findViewById(R.id.aktivitas);
        //implementasi tanggal hari ini
        final Calendar c = Calendar.getInstance();
        c.add(YEAR, -17);
        popupWindow = new PopupWindow(this);
        mYear = c.get(YEAR);
        mMonth = c.get(MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mDateDisplay = (TextView) findViewById(R.id.showMyDate);
        mPickDate = (Button) findViewById(R.id.myDatePickerButton);
        mUpdate = (Button) findViewById(R.id.update);
        Vberat = (EditText) findViewById(R.id.Vberat);
        Vtinggi = (EditText) findViewById(R.id.Vtinggi);
        Ggenre = (RadioGroup) findViewById(R.id.gender);
        Pria = findViewById(R.id.radio_pria);
        wanita = findViewById(R.id.radio_wanita);
        provile = findViewById(R.id.ViewProfile);
        isiProvile = findViewById(R.id.IsiProfile);
        infoKalori = findViewById(R.id.inFOkalri);
        InfoTglLaghir = findViewById(R.id.INFOshowMyDate);
        InfoGender = findViewById(R.id.infoGender);
        InfoTinggi = findViewById(R.id.infoTinggi);
        infoBeratBandan = findViewById(R.id.infoBerat);
        InfoImt = findViewById(R.id.infoimt);
        infoIMT = findViewById(R.id.infoIMT);

        MainActivity mainActivity = new MainActivity();
        if (mainActivity.getInstance().dataLemparanKalori.equalsIgnoreCase("0")) {
            System.out.println("DATA KALORI KOSONG");
            isiProvile.setVisibility(View.VISIBLE);
            provile.setVisibility(View.GONE);
        } else {
            System.out.println("DATA KALORI ADA");
            provile.setVisibility(View.VISIBLE);
            isiProvile.setVisibility(View.GONE);
        }






        final ArrayAdapter<String> Myadapter = new ArrayAdapter<String>(ComplateActivity.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.activity));
        Myadapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        aktivitas.setAdapter(Myadapter);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            C_Username = findViewById(R.id.UserName);
            User user = SharedPrefManager.getInstance(this).getUser();
            C_Username.setText(user.getName());
            id = user.getId();

        } else {
            Intent intent = new Intent(ComplateActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        Ggenre.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = Ggenre.getCheckedRadioButtonId();
                if (id == R.id.radio_pria) {
                    JenisKelamin = Pria.getText().toString();

                } else if (id == R.id.radio_wanita) {
                    JenisKelamin = wanita.getText().toString();

                }

            }

        });

        aktivitas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int posision, long l) {


                if (aktivitas.getItemAtPosition(posision).equals("Kategori Aktivitas anda")) {
                    Toast.makeText(ComplateActivity.this, "Anda belum memilih kategori aktivitas anda", Toast.LENGTH_SHORT).show();

                } else {
                    mUpdate.setEnabled(true);
                    for (int i = 1; i < l; i++) {
                        ValueAktivitas = valAktivitas(i);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });


        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (JenisKelamin == null) {
                        Toast.makeText(ComplateActivity.this, "Jenis Kelamin Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                    }
                    berat = Integer.parseInt(Vberat.getText().toString());
                    tinggi = Integer.parseInt(Vtinggi.getText().toString());
                    StatusGizi();
                    HitungUmmur();
                    KkalFinal();
                    hitungKalori();
//                        Intent intent = new Intent(ComplateActivity.this,MainActivity.class);
//                        startActivity(intent);


                } catch (NumberFormatException e) {

                    Toast.makeText(ComplateActivity.this, "Tinggi , dan Berat badan Harus di isi", Toast.LENGTH_SHORT).show();
                }

            }
        });
        getDataUser();

    }

    private void updateDisplay() {

        this.mDateDisplay.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mMonth + 1).append("-")
                        .append(mDay).append("-")
                        .append(mYear).append(" "));

    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    mYear = year;
                    mMonth = month;
                    mDay = dayOfMonth;
                    //display the Current date
                    updateDisplay();

                }
            };

    @Override
    protected Dialog onCreateDialog(int id) {
        final Calendar c = Calendar.getInstance();
        c.add(YEAR, -17);
        switch (id) {
            case DATE_DIALOG_ID:
                DatePickerDialog dialog = new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);

                dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
                return dialog;
//                return new DatePickerDialog(this,
//                        mDateSetListener,
//                        mYear, mMonth, mDay);


        }

        return null;
    }

    private int HitungUmmur() {
        final Calendar c = Calendar.getInstance();
        int Tsekarang, Thunlahir;
        Tsekarang = c.get(YEAR);
        Thunlahir = mYear;
        return umur = Tsekarang - Thunlahir;
    }


    private double valAktivitas(int pos) {


        if (pos == 1) {

            ValueAktivitas = 1.2;
        } else if (pos == 2) {

            ValueAktivitas = 1.4;
        } else if (pos == 3) {

            ValueAktivitas = 1.5;
        } else if (pos == 4) {

            ValueAktivitas = 1.7;
        } else if (pos == 5) {

            ValueAktivitas = 2.1;
        }
        return ValueAktivitas;
    }

    private void StatusGizi() {
        double nTinggi;


        //Mengubah tinggi dari CM ke Meter kuadrat
        nTinggi = (tinggi * 0.01);
        nTinggi = Math.pow(nTinggi, 2);

        IMT = berat / nTinggi;

        //validasi perhitungan IMT
        if (IMT <= 17.0) {
            Status = "Kurus";
            Keterangan = "Kurang Berat Badan Berat";

        } else if (IMT >= 17.0 && IMT <= 18.5) {
            Status = "Kurus";
            Keterangan = "Kurang Berat Badan Ringan";
        } else if (IMT >= 18.5 && IMT <= 25.5) {
            Status = "Normal";
            Keterangan = "Normal";
        } else if (IMT >= 25.5 && IMT <= 27.0) {
            Status = "Gemuk";
            Keterangan = "Overweight";
        } else if (IMT >= 27.0) {
            Status = "Gemuk";
            Keterangan = "Obesitas";
        }

        return;
    }


    private int BMRP(String Jeniskelamin) {

        switch (Jeniskelamin) {
            case "Laki - Laki":
                if (berat <= 55 && umur >= 10 && umur <= 18) {
                    KkalSems = 1625;
                } else if (berat >= 54 && berat <= 60 && umur >= 10 && umur <= 18) {
                    KkalSems = 1713;
                } else if (berat >= 61 && berat <= 65 && umur >= 10 && umur <= 18) {
                    KkalSems = 1801;
                } else if (berat >= 66 && berat <= 70 && umur >= 10 && umur <= 18) {
                    KkalSems = 1889;
                } else if (berat >= 71 && berat <= 75 && umur >= 10 && umur <= 18) {
                    KkalSems = 1977;
                } else if (berat >= 76 && berat <= 80 && umur >= 10 && umur <= 18) {
                    KkalSems = 2242;
                } else if (berat >= 81 && berat <= 85 && umur >= 10 && umur <= 18) {
                    KkalSems = 2154;
                } else if (berat >= 86 && berat <= 90 && umur >= 10 && umur <= 18) {
                    KkalSems = 2242;
                } else if (berat > 90 && umur >= 10 && umur <= 18) {
                    KkalSems = 2242;
                } else if (berat <= 55 && umur >= 18 && umur <= 30) {
                    KkalSems = 1514;
                } else if (berat >= 54 && berat <= 60 && umur >= 18 && umur <= 30) {
                    KkalSems = 1589;
                } else if (berat >= 61 && berat <= 65 && umur >= 18 && umur <= 30) {
                    KkalSems = 1664;
                } else if (berat >= 66 && berat <= 70 && umur >= 18 && umur <= 30) {
                    KkalSems = 1739;
                } else if (berat >= 71 && berat <= 75 && umur >= 18 && umur <= 30) {
                    KkalSems = 1814;
                } else if (berat >= 76 && berat <= 80 && umur >= 18 && umur <= 30) {
                    KkalSems = 1889;
                } else if (berat >= 81 && berat <= 85 && umur >= 18 && umur <= 30) {
                    KkalSems = 1964;
                } else if (berat >= 86 && berat <= 90 && umur >= 18 && umur <= 30) {
                    KkalSems = 2039;
                } else if (berat > 90 && umur >= 18 && umur <= 30) {
                    KkalSems = 2039;
                } else if (berat <= 55 && umur >= 30 && umur <= 60) {
                    KkalSems = 1499;
                } else if (berat >= 54 && berat <= 60 && umur >= 30 && umur <= 60) {
                    KkalSems = 1556;
                } else if (berat >= 61 && berat <= 65 && umur >= 30 && umur <= 60) {
                    KkalSems = 1613;
                } else if (berat >= 66 && berat <= 70 && umur >= 30 && umur <= 60) {
                    KkalSems = 1670;
                } else if (berat >= 71 && berat <= 75 && umur >= 30 && umur <= 60) {
                    KkalSems = 1727;
                } else if (berat >= 76 && berat <= 80 && umur >= 30 && umur <= 60) {
                    KkalSems = 1785;
                } else if (berat >= 81 && berat <= 85 && umur >= 30 && umur <= 60) {
                    KkalSems = 1842;
                } else if (berat >= 86 && berat <= 90 && umur >= 30 && umur <= 60) {
                    KkalSems = 1899;
                } else if (berat >= 90 && umur >= 30 && umur <= 60) {
                    KkalSems = 1899;
                }

                break;

            case "Perempuan":
                if (berat <= 40 && umur >= 10 && umur <= 18) {
                    KkalSems = 1224;
                } else if (berat >= 41 && berat <= 45 && umur >= 10 && umur <= 18) {
                    KkalSems = 1291;
                } else if (berat >= 46 && berat <= 50 && umur >= 10 && umur <= 18) {
                    KkalSems = 1357;
                } else if (berat >= 51 && berat <= 55 && umur >= 10 && umur <= 18) {
                    KkalSems = 1424;
                } else if (berat >= 56 && berat <= 60 && umur >= 10 && umur <= 18) {
                    KkalSems = 1491;
                } else if (berat >= 61 && berat <= 65 && umur >= 10 && umur <= 18) {
                    KkalSems = 1557;
                } else if (berat >= 66 && berat <= 70 && umur >= 10 && umur <= 18) {
                    KkalSems = 1624;
                } else if (berat >= 71 && berat <= 75 && umur >= 10 && umur <= 18) {
                    KkalSems = 1691;
                } else if (berat >= 75 && umur >= 10 && umur <= 18) {
                    KkalSems = 1691;
                } else if (berat <= 40 && umur >= 18 && umur <= 30) {
                    KkalSems = 1075;
                } else if (berat >= 41 && berat <= 45 && umur >= 18 && umur <= 30) {
                    KkalSems = 1149;
                } else if (berat >= 46 && berat <= 50 && umur >= 18 && umur <= 30) {
                    KkalSems = 1223;
                } else if (berat >= 51 && berat <= 55 && umur >= 18 && umur <= 30) {
                    KkalSems = 1296;
                } else if (berat >= 56 && berat <= 60 && umur >= 18 && umur <= 30) {
                    KkalSems = 1370;
                } else if (berat >= 61 && berat <= 65 && umur >= 18 && umur <= 30) {
                    KkalSems = 1444;
                } else if (berat >= 66 && berat <= 70 && umur >= 18 && umur <= 30) {
                    KkalSems = 1516;
                } else if (berat >= 71 && berat <= 75 && umur >= 18 && umur <= 30) {
                    KkalSems = 1592;
                } else if (berat >= 75 && umur >= 18 && umur <= 30) {
                    KkalSems = 1592;
                } else if (berat <= 40 && umur >= 30 && umur <= 60) {
                    KkalSems = 1167;
                } else if (berat >= 41 && berat <= 45 && umur >= 30 && umur <= 60) {
                    KkalSems = 1207;
                } else if (berat >= 46 && berat <= 50 && umur >= 30 && umur <= 60) {
                    KkalSems = 1248;
                } else if (berat >= 51 && berat <= 55 && umur >= 30 && umur <= 60) {
                    KkalSems = 1288;
                } else if (berat >= 56 && berat <= 60 && umur >= 30 && umur <= 60) {
                    KkalSems = 1329;
                } else if (berat >= 61 && berat <= 65 && umur >= 30 && umur <= 60) {
                    KkalSems = 1369;
                } else if (berat >= 66 && berat <= 70 && umur >= 30 && umur <= 60) {
                    KkalSems = 1410;
                } else if (berat >= 71 && berat <= 75 && umur >= 30 && umur <= 60) {
                    KkalSems = 1450;
                } else if (berat >= 75 && umur >= 30 && umur <= 60) {
                    KkalSems = 1450;
                }
                break;
        }
        return KkalSems;
    }


    private double hitungKallsems() {
        BMRP(JenisKelamin);
        double SDA;

        int BMR = KkalSems;


        SDA = 0.1 * BMR;

        Naktivitas = ValueAktivitas * (BMR + SDA);

        return Naktivitas;

    }


    private double KkalFinal() {
        hitungKallsems();



        JumlahKalori = Naktivitas;

        return JumlahKalori;
    }

    private void getDataUser() {
        final String Id = String.valueOf(id);
        String URL = "http://sehattiaphari.com/smartfitAPI/test.php/?id=" +Id;
        System.out.println(URL);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {

                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                JSONObject infoUser = obj.getJSONObject("infoUser");
                                ComplateProfile complateProfile = new ComplateProfile();
                                complateProfile.setId(infoUser.getInt("id"));
                                complateProfile.setId_user(infoUser.getInt("id_user"));
                                complateProfile.setKalori(infoUser.getDouble("kalori"));
                                complateProfile.setKetGizi(infoUser.getString("KetGizi"));
                                complateProfile.setYears(infoUser.getString("Years"));
                                complateProfile.setVtinggi(infoUser.getInt("Vtinggi"));
                                complateProfile.setVberat(infoUser.getInt("Vberat"));
                                complateProfile.setJeniskelamin(infoUser.getString("JenisKelamin"));
                                complateProfile.setIMT(infoUser.getDouble("nilai_imt"));


                                String Kkal = complateProfile.getKetGizi();
                                final double kkal = complateProfile.getKalori();
                                int berat = complateProfile.getVberat();
                                int tinggi = complateProfile.getVtinggi();
                                String tanggal = complateProfile.getYears();
                                final double imt = complateProfile.getIMT();
                                String gender = complateProfile.getJeniskelamin();
                                final double ValImt = complateProfile.getIMT();

                                if(ValImt < 18.5){
                                      valnewIMT =  18.5 - ValImt ;
                                }else if (imt >22.5 ){
                                     valnewIMT = ValImt - 22.5;
                                }else{
                                    valnewIMT = ValImt;
                                }

                                infoIMT.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        AlertDialog.Builder b = new AlertDialog.Builder(ComplateActivity.this);


                                        b.setTitle("Info IMT Anda");
                                        if(ValImt < 18.5){
                                            b.setMessage("Anda Butuh Menambah IMT sebesar "+valnewIMT+" untuk Mencapai Normal");
                                        }else if (ValImt >22.5 ){
                                            b.setMessage("Anda Butuh Mengurangi IMT sebesar "+valnewIMT+" untuk Mencapai Normal");
                                        }else{
                                            b.setMessage("IMT anda Sudah Normal Sebesar "+ValImt);
                                        }
                                        b.show();

                                    }
                                });

                                InfoImt.setText(""+ValImt);
                                InfoGender.setText(gender);
                                infoBeratBandan.setText(berat+" Kg");
                                InfoTinggi.setText(tinggi+" Cm");
                                InfoTglLaghir.setText(tanggal);
                                infoKalori.setText(kkal+" cal");



                                Toast.makeText(getApplicationContext(), Kkal, Toast.LENGTH_SHORT).show();

                            } else {

                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        );


        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


    private void hitungKalori() {
        final String Jenis_kelamin = JenisKelamin;
        final String Berat = String.valueOf(berat);
        final String Tinggi = String.valueOf(tinggi);
        final String Ketgizi = Keterangan;
        final String kalori = String.valueOf(JumlahKalori);
        final String taggal = mYear + "-" + mMonth + "-" + mDay;
        final String Umur = taggal;
        final String Id = String.valueOf(id);
        final String valImt = String.valueOf(IMT);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_USER_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //converting response to Json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in respon
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("id_user", Id);
                param.put("tinggi", Tinggi);
                param.put("berat", Berat);
                param.put("umur", Umur);
                param.put("kalori", String.format("%.2f", kalori));
                param.put("jenis_kelamin", Jenis_kelamin);
                param.put("Ketgizi", Ketgizi);
                param.put("imt",valImt);

                return param;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }


}
