package com.example.groupproject.ui.manage;

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



    /*
    ArrayList<Group> groups=new ArrayList<Group>();
    ArrayList<Task> tasks=new ArrayList<Task>();
    ArrayList<Member> members=new ArrayList<Member>();

    TextView group_name, progress;
    ProgressBar pb;
    Button edit,delete;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_manage, container, false);
        // change to fragment view
        return root;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){


        super.onViewCreated(view, savedInstanceState);

        // Testing, need change later
        String user="91234567";
        String group="1";

        group_name=(TextView)view.findViewById(R.id.textView3);
        progress=(TextView)view.findViewById(R.id.textView1);
        pb=(ProgressBar)view.findViewById(R.id.progressBar3);
        edit=(Button)view.findViewById(R.id.buttonedit);
        delete=(Button)view.findViewById(R.id.buttondelete);



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
                            arr=obj.getJSONArray("groupmates");
                            for(int i=0;i<arr.length();i++) {
                                JSONObject member=arr.getJSONObject(i);
                                members.add(
                                        new Member(
                                                obj.getString("group_id"),
                                                member.getString("user_id"),
                                                member.getString("username"),
                                                obj.getString("task_total"),
                                                member.getString("finished")
                                        )
                                );
                            }
                            RecyclerView recyclerView=view.findViewById(R.id.task_recycler_view);
                            LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            TaskAdapter adapter=new TaskAdapter(tasks, true);
                            recyclerView.setAdapter(adapter);
                            recyclerView=view.findViewById(R.id.member_recycler_view);
                            layoutManager=new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            MemberAdapter mAdapter=new MemberAdapter(members);
                            recyclerView.setAdapter(mAdapter);
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


        //Collect group of owner

        RequestQueue queue= Volley.newRequestQueue(getContext());
        StringRequest sr=new StringRequest(Request.Method.GET,"https://i.cs.hku.hk/~khchan4/groups.php?owner="+user,
                new Response.Listener<String>(){
                    public void onResponse(String response){
                        try{
                            JSONArray arr=new JSONArray(response);
                            for (int i=0;i<arr.length();i++){
                                JSONObject obj=arr.getJSONObject(i);
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

                        }
                        catch (Exception e){
                        }

                    }
                },
                new Response.ErrorListener(){
                    public void onErrorResponse(VolleyError error){
                    }
                });
        queue.add(sr);


        group_name.setText("COMP3330 Mid Term");
    }
    public void setProgress(String total, String finished) {
        int p=Integer.parseInt(finished)/Integer.parseInt(total)*100;
        pb.setProgress(p);
        progress.setText(finished+"/"+total);
    }
     */
}