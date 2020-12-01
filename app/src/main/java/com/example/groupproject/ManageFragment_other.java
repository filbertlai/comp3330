package com.example.groupproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.groupproject.Member;
import com.example.groupproject.R;
import com.example.groupproject.Task;
import com.example.groupproject.TaskAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ManageFragment_other extends Fragment {
    ArrayList<Task> tasks=new ArrayList<Task>();
    ProgressBar pb;
    TextView progress, member_name;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_view_other, container, false);

        return root;
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pb=(ProgressBar)view.findViewById(R.id.progressBar);
        progress=(TextView)view.findViewById(R.id.userProgress);
        member_name=(TextView)view.findViewById(R.id.name);
        member_name.setText("Progress of Peter");
        progress.setText("/");


        String user="91230000"; // Peter
        String group="1";

        RequestQueue queue= Volley.newRequestQueue(getContext());
        StringRequest sr=new StringRequest(Request.Method.GET, "https://i.cs.hku.hk/~khchan4/group.php?user="+user+"&id="+group,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj=new JSONObject(response);
                            JSONArray arr=obj.getJSONArray("tasks");

                            setProgress(obj.getString("task_total"), obj.getString("finished"));
                            for(int i=0;i<arr.length();i++) {
                                JSONObject task=arr.getJSONObject(i);
                                tasks.add(
                                        new Task(
                                                obj.getString("user_id"),
                                                task.getString("task_id"),
                                                task.getString("task_name"),
                                                task.getString("task_no"),
                                                task.getString("status")
                                        )
                                );
                            }
                            RecyclerView recyclerView=view.findViewById(R.id.task_recycler_view);
                            LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            TaskAdapter adapter=new TaskAdapter(tasks, false);
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
    public void setProgress(String total, String finished) {
        int p=Integer.parseInt(finished)/Integer.parseInt(total)*100;
        pb.setProgress(p);
        progress.setText(finished+"/"+total);
    }

}