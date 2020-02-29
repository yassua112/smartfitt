package com.yosua.projectskripsi;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yosua.projectskripsi.Model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {

    EditText EditTxusername, EditTxemail, EditTxpassword, c_password;
    ProgressBar loading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loading = findViewById(R.id.loading);
        //Implement direcly  activity if user already logged in

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            Toast.makeText(RegisterActivity.this,"You already LoggedIn",Toast.LENGTH_SHORT).show();
            return;
        }

        EditTxusername = findViewById(R.id.EdituTxsername);
        EditTxemail = findViewById(R.id.EditTxemail);
        EditTxpassword = findViewById(R.id.EditTxpassword);
        c_password = findViewById(R.id.c_password);

        findViewById(R.id.regbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //action when user pressed on button register
                //this is the action
                loading.setVisibility(View.VISIBLE);
                registerUser();
            }
        });


    }

    private void registerUser() {
        final String username = EditTxusername.getText().toString().trim();
        final String email = EditTxemail.getText().toString().trim();
        final String password = EditTxpassword.getText().toString().trim();
        final String C_password = c_password.getText().toString().trim();


        //registration validation
        if (TextUtils.isEmpty(username)){
            EditTxusername.setError("Please Enter your Username");
            EditTxusername.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(email)){
            EditTxemail.setError("Please Enter Your Email");
            EditTxemail.requestFocus();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            EditTxemail.setError("Enter a valid email");
            EditTxemail.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(password)){
            EditTxpassword.setError("Enter a password");
            EditTxpassword.requestFocus();
            return;
        }
        if (!password.equals(C_password)){
            EditTxpassword.setError("Password is not match");
            EditTxpassword.requestFocus();
            return;
        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.setVisibility(View.GONE);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("user");

                                //creating a new user object
                                User user = new User(
                                        userJson.getInt("id"),
                                        userJson.getString("username"),
                                        userJson.getString("email")
                                );

                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                //starting the profile activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
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
                        Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    public void BackLogin(View view){
        finish();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }
}
