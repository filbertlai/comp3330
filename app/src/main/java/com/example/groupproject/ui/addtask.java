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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.groupproject.Group;
import com.example.groupproject.GroupAdapter;
import com.example.groupproject.R;
import com.example.groupproject.ui.group.GroupFragment;
import com.example.groupproject.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class addtask extends Fragment {
    String group_id = "1";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_addtask, container, false);

        return root;
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String user = "91234567";
        String task_id = "1";
        EditText tn = (EditText) view.findViewById(R.id.Name);
//        EditText to = (EditText) view.findViewById(R.id.order);
        Button add = (Button) view.findViewById(R.id.buttonadd);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        add.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String new_tn = tn.getText().toString();
//                String new_to = to.getText().toString();
//                if(new_tn.length()<1 && new_to.length()<1){
//                    AlertDialog.Builder errormsg = new AlertDialog.Builder(getContext());
//                    errormsg.setTitle("Error");
//                    TextView errortv = new TextView(getActivity());
//                    errortv.setText("Error. Please input Name and Order!");
//                    errortv.setTextColor(Color.BLACK);
//                    errortv.setGravity(Gravity.CENTER);
//                    errortv.setTextSize(15);
//                    errortv.setPadding(5, 5, 5, 5);
//                    errormsg.setView(errortv);
//                    errormsg.setNegativeButton("OK", new DialogInterface.OnClickListener(){
//                        @Override
//                        public void onClick(DialogInterface d, int arg1) {
//                            d.dismiss();
//                        };
//                    });
//                    errormsg.show();
//                }
                if(new_tn.length()<1){
                    AlertDialog.Builder errormsg = new AlertDialog.Builder(getContext());
                    errormsg.setTitle("Error");
                    TextView errortv = new TextView(getActivity());
                    errortv.setText("Error. Please input Name!");
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
//                else if(new_to.length()<1){
//                    AlertDialog.Builder errormsg = new AlertDialog.Builder(getContext());
//                    errormsg.setTitle("Error");
//                    TextView errortv = new TextView(getActivity());
//                    errortv.setText("Error. Please input Order!");
//                    errortv.setTextColor(Color.BLACK);
//                    errortv.setGravity(Gravity.CENTER);
//                    errortv.setTextSize(15);
//                    errortv.setPadding(5, 5, 5, 5);
//                    errormsg.setView(errortv);
//                    errormsg.setNegativeButton("OK", new DialogInterface.OnClickListener(){
//                        @Override
//                        public void onClick(DialogInterface d, int arg1) {
//                            d.dismiss();
//                        };
//                    });
//                    errormsg.show();
//                }
                else{
                    StringRequest postRequest = new StringRequest(Request.Method.POST, "https://i.cs.hku.hk/~khchan4/task.php",
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
                                            errortv.setText("Error. Please Add Again!");
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
                                            errortv.setText("Add a task!");
                                            errortv.setTextColor(Color.BLACK);
                                            errortv.setGravity(Gravity.CENTER);
                                            errortv.setTextSize(15);
                                            errortv.setPadding(5, 5, 5, 5);
                                            errormsg.setView(errortv);
                                            errormsg.setNegativeButton("OK", new DialogInterface.OnClickListener(){
                                                @Override
                                                public void onClick(DialogInterface d, int arg1) {
                                                    d.dismiss();
                                                    GroupFragment fragment2 = new GroupFragment();
                                                    fragment2.setgpno(group_id);
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
                            params.put("group", group_id);
                            params.put("name", new_tn);
                            return params;
                        }
                    };
                    queue.add(postRequest);
                }

            }
        });

    }
}