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
public class notifOlahraga extends Fragment {

    TextView onOlahPagi,onOlahSore;
    Switch sw2;

    public static final String PREFS_NAME = "notifOlahraga";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notif_olahraga, container, false);
        final SharedPreferences settings = this.getActivity().getSharedPreferences(PREFS_NAME,0);

        boolean silent = settings.getBoolean("switchkey", true);
        boolean silenr1 = settings.getBoolean("switchkey1",true);


        final String OFF = settings.getString("On/Off","OFF");
        final String ON = settings.getString("Off/ON","ON");

        final String OFF1 =  settings.getString("on/Off1","OFF");
        final String ON1 = settings.getString("Off/ON1","ON");

        Switch sw2 = view.findViewById(R.id.SWOlSelsi);
        Switch sw1 = view.findViewById(R.id.SWOlpagi);

        onOlahPagi = view.findViewById(R.id.olhOraoff);
        onOlahSore = view.findViewById(R.id.onOffOlhmlm);


        sw1.setChecked(silent);
        if(sw1.isChecked()){
            onOlahPagi.setText(ON);
            FirebaseMessaging.getInstance().subscribeToTopic("olahragapagi")
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
        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    onOlahPagi.setText(ON);
                            FirebaseMessaging.getInstance().subscribeToTopic("olahragapagi")
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
                    onOlahPagi.setText(OFF);

                    FirebaseMessaging.getInstance().unsubscribeFromTopic("olahragapagi")
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

                SharedPreferences.Editor editor = settings.edit();
                editor.putString("On/Off","OFF");
                editor.putString("Off/ON","ON");
                editor.putBoolean("switchkey", isChecked);
                editor.apply();
            }
        });





        sw2.setChecked(silenr1);
        if(sw2.isChecked()){
            onOlahSore.setText(ON1);
            FirebaseMessaging.getInstance().subscribeToTopic("olahragasore")
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
            onOlahSore.setText(OFF1);

            FirebaseMessaging.getInstance().unsubscribeFromTopic("olahragasore")
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
        sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    onOlahSore.setText(ON1);
                    FirebaseMessaging.getInstance().subscribeToTopic("olahragasore")
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
                    onOlahSore.setText(OFF1);

                    FirebaseMessaging.getInstance().unsubscribeFromTopic("olahragasore")
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

                SharedPreferences.Editor editor = settings.edit();
                editor.putString("On/Off1","OFF");
                editor.putString("Off/ON1","ON");
                editor.putBoolean("switchkey1", isChecked);
                editor.apply();
            }
        });





        return view;
    }

}
