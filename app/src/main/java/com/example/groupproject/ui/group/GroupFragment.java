package com.example.groupproject.ui.group;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import com.example.groupproject.Member;
import com.example.groupproject.MemberAdapter;
import com.example.groupproject.R;
import com.example.groupproject.Task;
import com.example.groupproject.TaskAdapter;
import com.example.groupproject.editgp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GroupFragment extends Fragment {
    ArrayList<Task> tasks=new ArrayList<Task>();
    ArrayList<Member> members=new ArrayList<Member>();
    boolean isOwner;
    ProgressBar pb;
    TextView progress;
    Button edit, leave, delete;
    private String group="1";


    public void setgpno(String gpno){
        this.group=gpno;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_view, container, false);

        return root;
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pb=(ProgressBar)view.findViewById(R.id.progressBar);
        progress=(TextView)view.findViewById(R.id.userProgress);
        edit=(Button)view.findViewById(R.id.editGroup);
        leave=(Button)view.findViewById(R.id.leaveGroup);
        delete=(Button)view.findViewById(R.id.deleteGroup);

        String user="91234567";

        RequestQueue queue= Volley.newRequestQueue(getContext());
        StringRequest sr=new StringRequest(Request.Method.GET, "https://i.cs.hku.hk/~khchan4/group.php?user="+user+"&id="+group,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj=new JSONObject(response);
                            JSONArray arr=obj.getJSONArray("tasks");
                            isOwner=obj.getString("group_owner").equals(user);
                            setProgress(obj.getString("task_total"), obj.getString("finished"));
                            showOwnerFunc();
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
                            TaskAdapter adapter=new TaskAdapter(tasks, isOwner);
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

    }
    public void setProgress(String total, String finished) {
        int p=Integer.parseInt(finished)/Integer.parseInt(total)*100;
        pb.setProgress(p);
        progress.setText(finished+"/"+total);
    }
    public void showOwnerFunc() {
        if(isOwner) {
            leave.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
        }
    }
}
