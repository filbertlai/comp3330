package com.example.groupproject.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.groupproject.R;
import com.example.groupproject.ui.create.CreateFragment;
import com.example.groupproject.ui.home.HomeFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class editgp extends Fragment {
    private String group="1";

    public void setgpno(String gpno){
        this.group=gpno;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_editgp, container, false);
        return root;
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText gn = (EditText) view.findViewById(R.id.groupName);
        EditText gd = (EditText) view.findViewById(R.id.groupDescription);
        Button add = (Button) view.findViewById(R.id.createGroup);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String user = "91234567";
        add.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String new_gn = gn.getText().toString();
                String new_gd = gd.getText().toString();
                if(new_gd.length()<1 && new_gn.length()<1){
                    AlertDialog.Builder errormsg = new AlertDialog.Builder(getContext());
                    errormsg.setTitle("Error");
                    TextView errortv = new TextView(getActivity());
                    errortv.setText("Error. Please input name and Description!");
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
                    errormsg.show();
                }
                else if(new_gd.length()<1){
                    AlertDialog.Builder errormsg = new AlertDialog.Builder(getContext());
                    errormsg.setTitle("Error");
                    TextView errortv = new TextView(getActivity());
                    errortv.setText("Error. Please input description!");
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
                    errormsg.show();
                }
                else if(new_gn.length()<1){
                    AlertDialog.Builder errormsg = new AlertDialog.Builder(getContext());
                    errormsg.setTitle("Error");
                    TextView errortv = new TextView(getActivity());
                    errortv.setText("Error. Please input name!");
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
                    errormsg.show();
                }
                else{
                    StringRequest postRequest = new StringRequest(Request.Method.POST, "https://i.cs.hku.hk/~khchan4/group.php",
                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String str1 = jsonObject.getString("status");
                                        if (str1.equals("0")) {
                                            AlertDialog.Builder errormsg = new AlertDialog.Builder(getContext());
                                            errormsg.setTitle("Error");
                                            TextView errortv = new TextView(getActivity());
                                            errortv.setText("Error. Please Create Again!");
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
                                            errormsg.show();
                                        } else {
                                            AlertDialog.Builder errormsg = new AlertDialog.Builder(getContext());
                                            errormsg.setTitle("Success");
                                            TextView errortv = new TextView(getActivity());
                                            errortv.setText("Edited a Group!");
                                            errortv.setTextColor(Color.BLACK);
                                            errortv.setGravity(Gravity.CENTER);
                                            errortv.setTextSize(15);
                                            errortv.setPadding(5, 5, 5, 5);
                                            errormsg.setView(errortv);
                                            errormsg.setNegativeButton("OK", new DialogInterface.OnClickListener(){
                                                @Override
                                                public void onClick(DialogInterface d, int arg1) {
                                                    d.dismiss();
                                                    HomeFragment fragment2 = new HomeFragment();
                                                    FragmentManager fragmentManager = getFragmentManager();
                                                    FragmentTransaction fragmentTransaction =
                                                            fragmentManager.beginTransaction();
                                                    fragmentTransaction.replace(R.id.editgp, fragment2);
                                                    fragmentTransaction.addToBackStack(null);
                                                    fragmentTransaction.commit();
                                                };
                                            });
                                            errormsg.show();
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

                                    NetworkResponse networkResponse = error.networkResponse;
                                    if (networkResponse != null && networkResponse.data != null) {
                                        try {
                                            String responseBody = new String(error.networkResponse.data, "utf-8");
                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        }

                                    }

//
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("id", group);
                            params.put("name", new_gn);
                            params.put("description", new_gd);
                            return params;
                        }
                    };
                    queue.add(postRequest);
                }
            }
        });
    }
}
