package com.yosua.projectskripsi;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.yosua.projectskripsi.Model.ComplateProfile;
import com.yosua.projectskripsi.Model.DailyStep;
import com.yosua.projectskripsi.Model.User;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.result.DailyTotalResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class MainActivity extends AppCompatActivity {
    Handler handler = new Handler();


    private TextView pname, jmlKal, TxStatus, Logout, History, Step, jarakV, kalori, jadwal;
    private CardView CVprofile, CVmakanan, CVolahraga;
    private int id_User;

    private GoogleApiClient mClient = null;
    private OnDataPointListener mListener;
    TextView yoursStep, RealName;
    private long startTime, endTime;
    private SweetAlertDialog sDialog;
    public static String Kkal;
    public static String dataLemparanKalori;
    public static  double kkal;

    private static MainActivity instance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        jmlKal = findViewById(R.id.kkal);
        TxStatus = findViewById(R.id.TxStatus);
        Logout = findViewById(R.id.LogoutBtn);
        History = findViewById(R.id.history);
        jadwal = findViewById(R.id.idJadwal);



        pname = findViewById(R.id.pname);

        DailyStep dailyStep = new DailyStep();
        long step = dailyStep.getTotal();


        if (SharedPrefManager.getInstance(this).isLoggedIn()) {

            User user = SharedPrefManager.getInstance(this).getUser();


            id_User = user.getId();
            pname.setText(user.getName());
            CVprofile = findViewById(R.id.Profile);
            CVmakanan = findViewById(R.id.makanan);
            CVolahraga = findViewById(R.id.olahraga);

            CVprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(MainActivity.this, ComplateActivity.class);
                    startActivity(intent); new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Apa Anda Akan Keluar ?")
                            .setConfirmText("Ya")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.setTitleText("Berhasil")
                                            .setConfirmText("OK")
                                            .setCancelClickListener(null)
                                            .setConfirmClickListener(null)
                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                                    SharedPrefManager.getInstance(getApplicationContext()).logout();
                                    mClient.maybeSignOut();
                                    finish();
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


            CVolahraga.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(MainActivity.this, OlahrgaActivity.class));
                }
            });
            getDataUser();

        }

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HistoryActivity.class));


            }
        });

        jadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel("MyNotification", "MyNotification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        connectFitness();


    }

    @Override
    protected void onResume() {
        super.onResume();
        connectFitness();
    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    public static MainActivity getInstance() {
        return instance;
    }


    private void getDataUser() {
        System.out.println("DISINI 1");

        final String Id = String.valueOf(id_User);
        String URL = "http://sehattiaphari.com/smartfitAPI/test.php/?id=" + Id;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            if (!obj.getBoolean("error")) {

                                JSONObject infoUser = obj.getJSONObject("infoUser");

                                if (infoUser.isNull("id") == true) {
                                    System.out.println("DATA KOSONG");
                                    dataLemparanKalori = jmlKal.getText().toString();
                                } else {
                                    System.out.println("DATA ADA");
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                    ComplateProfile complateProfile = new ComplateProfile();
                                    complateProfile.setId(infoUser.getInt("id"));
                                    complateProfile.setId_user(infoUser.getInt("id_user"));
                                    complateProfile.setKalori(infoUser.getDouble("kalori"));
                                    complateProfile.setKetGizi(infoUser.getString("KetGizi"));
                                    Kkal = complateProfile.getKetGizi();
                                    System.out.println("JUMLAH KALORI : " + Kkal);
                                    kkal = complateProfile.getKalori();
                                    jmlKal.setText(String.valueOf(kkal) + "Kkal");
                                    dataLemparanKalori = jmlKal.getText().toString();
                                    System.out.println("HAHAHAHA" + dataLemparanKalori);
                                    TxStatus.setText(Kkal);
                                    if (!jmlKal.equals(null)) {
                                        CVmakanan.setVisibility(View.VISIBLE);
                                        CVolahraga.setVisibility(View.VISIBLE);
                                    }

                                    CVmakanan.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(MainActivity.this, MakanActivity.class);
                                            intent.putExtra("kalori", String.valueOf(kkal));
                                            startActivity(intent);
                                        }
                                    });
                                }


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

    private void connectFitness() {
        if (mClient == null) {
            mClient = new GoogleApiClient.Builder(this)
                    .addApi(Fitness.SENSORS_API)
                    .addApi(Fitness.RECORDING_API)
                    .addApi(Fitness.HISTORY_API)
                    .addScope(new Scope(Scopes.FITNESS_LOCATION_READ)) // GET STEP VALUES
                    .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ))
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                                                @Override
                                                public void onConnected(Bundle bundle) {

                                                    // Now you can make calls to the Fitness APIs.
                                                    new VerifyDataTask().execute();

                                                }

                                                @Override
                                                public void onConnectionSuspended(int i) {
                                                    // If your connection to the sensor gets lost at some point,
                                                    // you'll be able to determine the reason and react to it here.
                                                    if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST) {

                                                    } else if (i
                                                            == GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {

                                                    }
                                                }
                                            }
                    )
                    .enableAutoManage(this, 0, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult result) {

                        }
                    })
                    .build();
        }

    }

    private class VerifyDataTask extends AsyncTask<String[], Long, String[]> {

        @Override
        protected void onPreExecute() {
            jarakV = findViewById(R.id.textView5jarak);
            kalori = findViewById(R.id.textView4Kalori);
            Step = findViewById(R.id.textView3Step);
        }

        protected String[] doInBackground(String[]... params) {
            long total = 0;
            float kkalPend = 0.0f;
            float jarak = 0;
            String[] arrayTotal = new String[4];


            PendingResult<DailyTotalResult> result = Fitness.HistoryApi.readDailyTotal(mClient, DataType.TYPE_STEP_COUNT_DELTA);
            PendingResult<DailyTotalResult> result2 = Fitness.HistoryApi.readDailyTotal(mClient, DataType.TYPE_CALORIES_EXPENDED);
            PendingResult<DailyTotalResult> result3 = Fitness.HistoryApi.readDailyTotal(mClient, DataType.TYPE_DISTANCE_DELTA);

            DailyTotalResult totalResult = result.await(10, TimeUnit.SECONDS);
            DailyTotalResult totalResult2 = result2.await(10, TimeUnit.SECONDS);
            DailyTotalResult totalResult3 = result3.await(10, TimeUnit.SECONDS);

            if (totalResult.getStatus().isSuccess() || totalResult2.getStatus().isSuccess()) {
                DataSet totalSet = totalResult.getTotal();
                DataSet totalset2 = totalResult2.getTotal();
                DataSet totalset3 = totalResult3.getTotal();
                total = totalSet.isEmpty()
                        ? 0
                        : totalSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();
                kkalPend = totalset2.isEmpty()
                        ? 0
                        : totalset2.getDataPoints().get(0).getValue(Field.FIELD_CALORIES).asFloat();
                jarak = totalset3.isEmpty()
                        ? 0
                        : totalset3.getDataPoints().get(0).getValue(Field.FIELD_DISTANCE).asFloat();
            } else {
                Toast.makeText(getApplicationContext(), "We cant connect to Google FIT, Please Download It first", Toast.LENGTH_SHORT).show();
            }


            DailyStep dailyStep = new DailyStep();
            dailyStep.setJarak(jarak);
            dailyStep.setKalstep(kkalPend);
            dailyStep.setTotal(total);

            arrayTotal[1] = String.valueOf(total);
            arrayTotal[2] = String.valueOf(String.format("%.2f", kkalPend));
            arrayTotal[3] = String.valueOf(String.format("%.2f", jarak / 1000));


            return arrayTotal;

        }


        @Override
        protected void onProgressUpdate(Long... values) {
            super.onProgressUpdate(values);


        }

        @Override
        protected void onPostExecute(String[] aVoid) {
            super.onPostExecute(aVoid);


            Step.setText(aVoid[1] + "\nLangkah");
            jarakV.setText(aVoid[3] + "\nKm");
            kalori.setText(aVoid[2] + "\nCal");

        }
    }


    private Runnable timetask = new Runnable() {
        @Override
        public void run() {
            new VerifyDataTask().execute();
            handler.postDelayed(timetask, 5000);
        }
    };


    private void allNotification() {
        FirebaseMessaging.getInstance().subscribeToTopic("makanpagi")
                .addOnCompleteListener(new OnCompleteListener<Void>() {


                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Succesfull";
                        if (!task.isSuccessful()) {
                            msg = "Faild";
                        }


                    }
                });

        FirebaseMessaging.getInstance().subscribeToTopic("makansnack1")
                .addOnCompleteListener(new OnCompleteListener<Void>() {


                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Succesfull";
                        if (!task.isSuccessful()) {
                            msg = "Faild";
                        }


                    }
                });

        FirebaseMessaging.getInstance().subscribeToTopic("makansiang")
                .addOnCompleteListener(new OnCompleteListener<Void>() {


                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Succesfull";
                        if (!task.isSuccessful()) {
                            msg = "Faild";
                        }


                    }
                });
        FirebaseMessaging.getInstance().subscribeToTopic("snacksore")
                .addOnCompleteListener(new OnCompleteListener<Void>() {


                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Succesfull";
                        if (!task.isSuccessful()) {
                            msg = "Faild";
                        }


                    }
                });


        FirebaseMessaging.getInstance().subscribeToTopic("makanmalam")
                .addOnCompleteListener(new OnCompleteListener<Void>() {


                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Succesfull";
                        if (!task.isSuccessful()) {
                            msg = "Faild";
                        }


                    }
                });

        FirebaseMessaging.getInstance().subscribeToTopic("makanmalam")
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Succesfull";
                        if (!task.isSuccessful()) {
                            msg = "Faild";
                        }


                    }
                });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}