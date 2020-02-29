package com.yosua.projectskripsi.Fragmen;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.yosua.projectskripsi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 */
public class notivMakanan extends Fragment {

    public static final String PREFS_NAME = "notivMakanan";

    private TextView txPagi,txSelp,txSiang,txSelSi,txMalm;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_notiv_makanan, container, false);

        txPagi = view.findViewById(R.id.txSWpagi);
        txSelp = view.findViewById(R.id.txSWSelsi);
        txSiang = view.findViewById(R.id.txSwSiang);
        txSelSi = view.findViewById(R.id.txSWSelso);
        txMalm = view.findViewById(R.id.txSwMalam);


        Switch SWp =  view.findViewById(R.id.SWpagi);
        Switch SWsp = view.findViewById(R.id.SWSelsi);
        Switch SWS = view.findViewById(R.id.SwSiang);
        Switch SWsl = view.findViewById(R.id.SWSelso);
        Switch SWMalm =  view.findViewById(R.id.SwMalam);


        final SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(PREFS_NAME,0);

        boolean silent = sharedPreferences.getBoolean("switchkey", true);
        boolean silent1 = sharedPreferences.getBoolean("switchkey1",true);
        boolean silent2 = sharedPreferences.getBoolean("switchkey2", true);
        boolean silent3 = sharedPreferences.getBoolean("switchkey3",true);
        boolean silent4 = sharedPreferences.getBoolean("switchkey4", true);

        final String OFF = sharedPreferences.getString("OFF1","OFF");
        final String ON = sharedPreferences.getString("ON1","ON");

        final String OFF1 = sharedPreferences.getString("OFF2","OFF");
        final String ON1 = sharedPreferences.getString("ON2","ON");

        final String OFF2 = sharedPreferences.getString("OFF3","OFF");
        final String ON2 = sharedPreferences.getString("ON3","ON");

        final String OFF3 = sharedPreferences.getString("OFF4","OFF");
        final String ON3 = sharedPreferences.getString("ON4","ON");

        final String OFF4 = sharedPreferences.getString("OFF5","OFF");
        final String ON4 = sharedPreferences.getString("ON5","ON");



        SWp.setChecked(silent);
        if (SWp.isChecked()){
            txPagi.setText(ON);
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
        }else{
            txPagi.setText(OFF);
            FirebaseMessaging.getInstance().unsubscribeFromTopic("makanpagi")
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

        SWp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    txPagi.setText(ON);
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


                }else{
                    txPagi.setText(OFF);
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("makanpagi")
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
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("OFF1","OFF");
                editor.putString("ON1","ON");
                editor.putBoolean("switchkey",isChecked);
                editor.apply();
            }



        });
//-------------------------------------------------------------------------------------------------------------
        SWsp.setChecked(silent1);
        if (SWsp.isChecked()){
            txSelp.setText(ON1);
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
        }else{
            txSelp.setText(OFF1);
            FirebaseMessaging.getInstance().unsubscribeFromTopic("makansnack1")
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

        SWsp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    txSelp.setText(ON1);
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


                }else{
                    txSelp.setText(OFF1);
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("makansnack1")
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
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("OFF2","OFF");
                editor.putString("ON2","ON");
                editor.putBoolean("switchkey1",isChecked);
                editor.apply();
            }



        });
//----------------------------------------------------------------------------------------------------------
        SWS.setChecked(silent2);
        if (SWS.isChecked()){
            txSiang.setText(ON2);
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
        }else{
            txSiang.setText(OFF2);
            FirebaseMessaging.getInstance().unsubscribeFromTopic("makansiang")
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

        SWS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    txSiang.setText(ON2);
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


                }else{
                    txSiang.setText(OFF2);
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("makansiang")
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
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("OFF3","OFF");
                editor.putString("ON3","ON");
                editor.putBoolean("switchkey2",isChecked);
                editor.apply();
            }



        });
//-------------------------------------------------------------------------------------------------------
        SWsl.setChecked(silent3);
        if (SWsl.isChecked()){
            txSelSi.setText(ON3);
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
        }else{
            txSelSi.setText(OFF3);
            FirebaseMessaging.getInstance().unsubscribeFromTopic("snacksore")
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

        SWsl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    txSelSi.setText(ON3);
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


                }else{
                    txSelSi.setText(OFF3);
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("snacksore")
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
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("OFF4","OFF");
                editor.putString("ON4","ON");
                editor.putBoolean("switchkey3",isChecked);
                editor.apply();
            }



        });
//--------------------------------------------------------------------------------------------------
        SWMalm.setChecked(silent3);
        if (SWMalm.isChecked()){
            txMalm.setText(ON4);
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
        }else{
            txMalm.setText(OFF4);
            FirebaseMessaging.getInstance().unsubscribeFromTopic("makanmalam")
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

        SWMalm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    txMalm.setText(ON4);
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


                }else{
                    txMalm.setText(OFF4);
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("makanmalam")
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
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("OFF5","OFF");
                editor.putString("ON5","ON");
                editor.putBoolean("switchkey4",isChecked);
                editor.apply();
            }



        });


        return view;
    }


}
