package com.example.groupproject.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.groupproject.MainActivity;
import com.example.groupproject.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText phone, username, password, cpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Registration");
        phone=findViewById(R.id.newPhone);
        username=findViewById(R.id.newUsername);
        password=findViewById(R.id.newPassword);
        cpassword=findViewById(R.id.newPasswordConfirm);
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pw=password.getText().toString();
                String ph=phone.getText().toString();
                String un=username.getText().toString();
                String cpw=cpassword.getText().toString();
                if(ph.isEmpty() || un.isEmpty() || pw.isEmpty() || cpw.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please input complete data", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pw.length()<6) {
                    Toast.makeText(getApplicationContext(), "Password should be at least 6 digits", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!pw.equals(cpw)) {
                    Toast.makeText(getApplicationContext(), "Passwords not matched", Toast.LENGTH_SHORT).show();
                    return;
                }
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, "https://i.cs.hku.hk/~khchan4/register.php",
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    String str1 = jsonObject.getString("status");
                                    if (str1.equals("0")) {
                                        Toast.makeText(getApplicationContext(), "Failed! User already exists", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Registration success", Toast.LENGTH_SHORT).show();
                                        finish();
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
                        params.put("username", un);
                        params.put("password", pw);
                        return params;
                    }
                };
                queue.add(postRequest);
            }
        });
    }
}