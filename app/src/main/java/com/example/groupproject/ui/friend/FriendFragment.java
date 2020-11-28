package com.example.groupproject.ui.friend;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.groupproject.Friend;
import com.example.groupproject.Group;
import com.example.groupproject.GroupAdapter;
import com.example.groupproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FriendFragment extends Fragment {
    ArrayList<Friend> friends = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_friend, container, false);
        return root;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String user="91234567";


        RequestQueue queue= Volley.newRequestQueue(getContext());
        StringRequest sr=new StringRequest(Request.Method.GET, "https://i.cs.hku.hk/~khchan4/friends.php?user="+user,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray arr = new JSONArray(response);
                            for(int i=0;i<arr.length();i++) {
                                JSONObject obj = arr.getJSONObject(i);
                                friends.add(
                                        new Friend(
                                                obj.getString("username"),
                                                obj.getString("phone")
                                        )
                                );
                            }
                            TableLayout tf = (TableLayout) view.findViewById(R.id.tableFriends);
                            for (int i=0; i<friends.size(); i++) {
                                TableRow tr= new TableRow(getActivity());
                                TextView tv1 = new TextView(getActivity());
                                TextView tv2 = new TextView(getActivity());
                                Button btn = new Button(getActivity());
                                tv1.setText(friends.get(i).fd_name);
                                tv1.setTextColor(Color.BLACK);
                                tv1.setGravity(Gravity.CENTER);
                                tv1.setTextSize(15);
                                tv1.setPadding(5, 5, 5, 5);
                                tv2.setText(friends.get(i).phone_no);
                                tv2.setTextColor(Color.BLACK);
                                tv2.setGravity(Gravity.CENTER);
                                tv2.setTextSize(15);
                                tv2.setPadding(5, 5, 5, 5);
                                btn.setText("remove");
                                int finalI = i;
                                btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view)
                                    {
                                        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://i.cs.hku.hk/~khchan4/friend.php",
                                                new Response.Listener<String>()
                                                {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        // response
                                                        Log.d("Response", response);
                                                        tf.removeView(tr);
                                                    }
                                                },
                                                new Response.ErrorListener()
                                                {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        // error
                                                        Log.d("Error.Response", response);
                                                    }
                                                }
                                        ) {
                                            @Override
                                            protected Map<String, String> getParams()
                                            {
                                                Map<String, String> params = new HashMap<String, String>();
                                                params.put("user", user);
                                                params.put("remove", friends.get(finalI).phone_no);
                                                return params;
                                            }
                                        };
                                        queue.add(postRequest);
                                    }
                                });
                                tr.addView(tv1);
                                tr.addView(tv2);
                                tr.addView(btn);
                                tf.addView(tr);
                            }
                        }
                        catch(Exception e) {
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        queue.add(sr);

        Button addfd = (Button) view.findViewById(R.id.add_friend);
        addfd.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Add Friend");

                TextView prompt = new TextView(getActivity());
                prompt.setText("Phone Number:");
                prompt.setTextColor(Color.BLACK);
                prompt.setGravity(Gravity.CENTER);
                prompt.setTextSize(15);
                prompt.setPadding(5, 5, 5, 5);
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                LinearLayout layout = new LinearLayout(getContext());
                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setLayoutParams(parms);

                layout.setGravity(Gravity.CLIP_VERTICAL);
                layout.setPadding(2, 2, 2, 2);

                LinearLayout.LayoutParams tv1Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                tv1Params.bottomMargin = 5;
                layout.addView(prompt ,tv1Params);
                layout.addView(input, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                builder.setView(layout);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://i.cs.hku.hk/~khchan4/friend.php",
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
                                                FriendFragment fragmentManager = new FriendFragment();
                                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                                fragmentTransaction.replace(R.id.friendfragment, fragmentManager);
                                                fragmentTransaction.addToBackStack(null);
                                                fragmentTransaction.commit();
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
                                String new_fd = input.getText().toString();
                                params.put("user", user);
                                params.put("add", new_fd);
                                return params;
                            }
                        };
                        queue.add(postRequest);
                    }
                });
                builder.show();
            }
        });
    }
}


