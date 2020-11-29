package com.example.groupproject.ui.manage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.groupproject.Group;
import com.example.groupproject.GroupAdapter;
import com.example.groupproject.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ManageFragment extends Fragment {
    ArrayList<Group> groups=new ArrayList<Group>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String user="91234567";

        RequestQueue queue= Volley.newRequestQueue(getContext());
        StringRequest sr=new StringRequest(Request.Method.GET, "https://i.cs.hku.hk/~khchan4/groups.php?user="+user,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray arr = new JSONArray(response);
                            for(int i=0;i<arr.length();i++) {
                                JSONObject obj=arr.getJSONObject(i);
                                if(!user.equals(obj.getString("group_owner"))) {
                                    continue;
                                }
                                groups.add(
                                        new Group(
                                                obj.getString("user_id"),
                                                obj.getString("group_id"),
                                                obj.getString("group_name"),
                                                obj.getString("group_description"),
                                                obj.getString("group_owner"),
                                                obj.getString("finished"),
                                                obj.getString("total")
                                        )
                                );
                            }
                            RecyclerView recyclerView=view.findViewById(R.id.recycler_view);
                            LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            GroupAdapter adapter=new GroupAdapter(groups, true);
                            recyclerView.setAdapter(adapter);
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
    }
}

/*public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }
}*/
