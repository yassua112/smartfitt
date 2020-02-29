package com.yosua.projectskripsi.Fragmen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yosua.projectskripsi.Adapter.CustomListOlahraga;
import com.yosua.projectskripsi.Model.HistoriOlahraga;
import com.yosua.projectskripsi.Model.User;
import com.yosua.projectskripsi.R;
import com.yosua.projectskripsi.SharedPrefManager;
import com.yosua.projectskripsi.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * create an instance of this fragment.
 */
public class HistoryOlahraga extends Fragment {

    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_olahraga, container, false);
        listView = view.findViewById(R.id.ListVewOlahraga);
        GetData();
        return view;
    }



    private void GetData() {
        User user = SharedPrefManager.getInstance(getActivity()).getUser();

        final int id_User = user.getId();
        final String Id = String.valueOf(id_User);
        String URL = "http://sehattiaphari.com/smartfitAPI/historyOlhUser.php/?id="+ Id;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("infoOlRg");
                            final ArrayList<HistoriOlahraga> classList = new ArrayList<>();

                            final int[] waktu = new int[jsonArray.length()];
                            final String[] tanggal = new String[jsonArray.length()];
                            final String[] nm_olahraga = new String[jsonArray.length()];
                            final float[]kalTot = new float[jsonArray.length()];

                            for (int i = 0 ; i < jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                   HistoriOlahraga historiOlahraga = new HistoriOlahraga();
                                   historiOlahraga.setNm_olahraga(object.getString("nm_olahraga"));
                                   historiOlahraga.setWaktu(object.getInt("waktu"));
                                   historiOlahraga.setDate(object.getString("tanggal"));
                                   historiOlahraga.setTotalKal(Float.valueOf(object.getString("kalori")));

                                String nam_olahraga = historiOlahraga.getNm_olahraga();
                                float kalorit = historiOlahraga.getTotalKal();
                                int wakLam = historiOlahraga.getWaktu();
                                String Tanggal = historiOlahraga.getDate();


                                classList.add(historiOlahraga);


                                nm_olahraga[i] = nam_olahraga;
                                tanggal[i] = Tanggal;
                                waktu[i] = wakLam;
                                kalTot[i] = kalorit;


                            }
                            CustomListOlahraga customListOlahraga = new CustomListOlahraga(getActivity(),nm_olahraga,tanggal,waktu,kalTot);
                            listView.setAdapter(customListOlahraga);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        );
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }




}
