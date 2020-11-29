package com.example.groupproject.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.groupproject.LoginInfo;
import com.example.groupproject.MainActivity;
import com.example.groupproject.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText phone, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        getSupportActionBar().setTitle("Welcome!");
        Button login_btn = (Button) findViewById(R.id.login_button);
        phone=(EditText)findViewById(R.id.Name);
        password=(EditText)findViewById(R.id.Pw);
        login_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ph = phone.getText().toString();
                String pw = password.getText().toString();
                if (pw.isEmpty() || ph.isEmpty()) {
                    /*AlertDialog.Builder errormsg = new AlertDialog.Builder(getApplicationContext());
                    errormsg.setTitle("Error");
                    TextView errortv = new TextView(getApplicationContext());
                    errortv.setText("Please input all information!");
                    errortv.setTextColor(Color.BLACK);
                    errortv.setGravity(Gravity.CENTER);
                    errortv.setTextSize(15);
                    errortv.setPadding(5, 5, 5, 5);
                    errormsg.setView(errortv);
                    errormsg.setNegativeButton("OK", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface d, int arg1) {
                            d.dismiss();
                        };
                    });
                    errormsg.show();*/
                    Toast.makeText(getApplicationContext(), "Please input all information!", Toast.LENGTH_SHORT).show();
                } else {
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest postRequest = new StringRequest(Request.Method.POST, "https://i.cs.hku.hk/~khchan4/login.php",
                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response) {
                                    JSONObject jsonObject = null;
                                    try {
                                        jsonObject = new JSONObject(response);
                                        String str1 = jsonObject.getString("status");
                                        if (str1.equals("0")) {
                                            /*AlertDialog.Builder errormsg = new AlertDialog.Builder(getApplicationContext());
                                            errormsg.setTitle("Error");
                                            TextView errortv = new TextView(getApplicationContext());
                                            errortv.setText("Login failed! Wrong Login Credentials");
                                            errortv.setTextColor(Color.BLACK);
                                            errortv.setGravity(Gravity.CENTER);
                                            errortv.setTextSize(15);
                                            errortv.setPadding(5, 5, 5, 5);
                                            errormsg.setView(errortv);
                                            errormsg.setNegativeButton("OK", new DialogInterface.OnClickListener(){
                                                @Override
                                                public void onClick(DialogInterface d, int arg1) {
                                                    d.dismiss();
                                                };
                                            });
                                            errormsg.show();*/
                                            Toast.makeText(getApplicationContext(), "Login failed! Wrong Login Credentials", Toast.LENGTH_SHORT).show();
                                        } else {
                                            /*LoginInfo loginInfo = new LoginInfo();
                                            loginInfo.setUser(ph);*/
                                            SharedPreferences pref = getSharedPreferences("progressSharer", MODE_PRIVATE);
                                            pref.edit()
                                                    .putString("user_id", ph)
                                                    .commit();
                                            phone.setText("");
                                            password.setText("");
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("phone", ph);
                            params.put("password", pw);
                            return params;
                        }
                    };
                    queue.add(postRequest);
                }
            }
        });
        findViewById(R.id.newUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}