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
import com.yosua.projectskripsi.Adapter.CustomList;
import com.yosua.projectskripsi.Model.HistoriMakanan;
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
public class HistoryMakanan extends Fragment {

   private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history_makanan, container, false);
        listView = view.findViewById(R.id.ListMakan);
        GetData();
        return view;
    }



 private void GetData() {
     User user = SharedPrefManager.getInstance(getActivity()).getUser();

     final int id_User = user.getId();
     final String Id = String.valueOf(id_User);
     String URL = "http://sehattiaphari.com/smartfitAPI/historyMknUser.php/?id="+ Id;

     StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
             new Response.Listener<String>() {
                 @Override
                 public void onResponse(String response) {
                     try {
                         JSONObject obj = new JSONObject(response);
                         JSONArray jsonArray = obj.getJSONArray("infoMkn");
                         final ArrayList<HistoriMakanan> classList = new ArrayList<>();
                         final String[] historiMkn = new String[jsonArray.length()];
                         final int[] kkal = new int[jsonArray.length()];
                         final String[] tanggal = new String[jsonArray.length()];
                         for (int i = 0 ; i < jsonArray.length();i++){
                             JSONObject object = jsonArray.getJSONObject(i);
                             HistoriMakanan historiMakanan = new HistoriMakanan();
                             historiMakanan.setMenupagi(object.getString("menuPa"));
                             historiMakanan.setMenuSel1(object.getString("menuSelp"));
                             historiMakanan.setMenuSiang(object.getString("menuSing"));
                             historiMakanan.setMenuSel2(object.getString("menuSelmal"));
                             historiMakanan.setMenuMalam(object.getString("menuMal"));
                             historiMakanan.setKkal(object.getInt("totKal"));
                             historiMakanan.setDate(object.getString("tanggal"));

                             String dataMakanan = "1. "+historiMakanan.getMenupagi()+"\n"+"2. "+historiMakanan.getMenuSel1()+"\n"+"3. "+historiMakanan.getMenuSiang()+"\n"+"4. "+historiMakanan.getMenuSel2()+"\n"+"5. " +historiMakanan.getMenuMalam();
                             int totalkal = historiMakanan.getKkal();
                             String tgl = historiMakanan.getDate();

                             classList.add(historiMakanan);
                             historiMkn[i] = dataMakanan;
                             kkal[i] = totalkal;
                             tanggal[i] = tgl;
                         }
                         CustomList customList =  new CustomList(getActivity(),historiMkn,kkal,tanggal);

                         listView.setAdapter(customList);



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
