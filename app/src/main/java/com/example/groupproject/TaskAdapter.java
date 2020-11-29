package com.example.groupproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.groupproject.ui.edit.EditTaskFragment;
import com.example.groupproject.ui.friend.FriendFragment;
import com.example.groupproject.ui.group.GroupFragment;
import com.example.groupproject.ui.home.HomeFragment;
import com.example.groupproject.ui.join.JoinFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    ArrayList<Task> tasks;
    public boolean isOwner, viewFriend=false;
    public TaskStatusCallback callback;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View taskView;
        CheckBox task;
        View taskContainer;
        ImageButton edit;

        public ViewHolder(View view) {
            super(view);
            taskView=view;
            task=taskView.findViewById(R.id.task);
            taskContainer=taskView.findViewById(R.id.taskContainer);
            edit=taskView.findViewById(R.id.edit);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        final ViewHolder holder=new ViewHolder(view);
        CardView cardView=(CardView)view.findViewById(R.id.taskCardView);
        cardView.setRadius(30);
        cardView.setCardElevation(10);
        /*if(layoutID==R.layout.group_item) {
            holder.groupView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getAdapterPosition();
                    Group group = groups.get(position);
                    //Toast.makeText(view.getContext(), group.group_name, Toast.LENGTH_SHORT).show();
                    AppCompatActivity activity=(AppCompatActivity)view.getContext();
                    Fragment f=new GroupFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, f).addToBackStack(null).commit();
                }
            });
        }
        else {
            holder.groupView.findViewById(R.id.join).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getAdapterPosition();
                    Group group = groups.get(position);
                    //Toast.makeText(view.getContext(), "Join", Toast.LENGTH_SHORT).show();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment f= new HomeFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, f).addToBackStack(null).commit();
                }
            });
        }*/
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task=tasks.get(position);
        holder.task.setText(task.task_name);
        if(isOwner) {
            holder.edit.setVisibility(View.VISIBLE);
        }
        if(task.status.equals("1")) {
            holder.taskContainer.setBackgroundColor(ContextCompat.getColor(holder.taskView.getContext(),R.color.finished));
            holder.task.setBackgroundColor(ContextCompat.getColor(holder.taskView.getContext(),R.color.finished));
            holder.task.setChecked(true);
        }
        if(viewFriend) {
            holder.task.setClickable(false);
            return;
        }
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task current=tasks.get(holder.getAdapterPosition());
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment f= new EditTaskFragment();
                Bundle data = new Bundle();
                data.putString( "task_id" , current.task_id);
                data.putString("task_name", current.task_name);
                data.putString("task_no", current.task_no);
                f.setArguments(data);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, f).addToBackStack(null).commit();
            }
        });
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                Task current=tasks.get(holder.getAdapterPosition());
                if(isChecked) {
                    view.setBackgroundColor(ContextCompat.getColor(holder.taskView.getContext(),R.color.finished));
                    holder.taskContainer.setBackgroundColor(ContextCompat.getColor(holder.taskView.getContext(),R.color.finished));
                    current.status="1";
                    changeTaskStatus(current.user_id, current.task_id, current.status);
                }
                else {
                    view.setBackgroundColor(ContextCompat.getColor(holder.taskView.getContext(),R.color.unfinished));
                    holder.taskContainer.setBackgroundColor(ContextCompat.getColor(holder.taskView.getContext(),R.color.unfinished));
                    current.status="0";
                    changeTaskStatus(current.user_id, current.task_id, current.status);
                }
                callback.taskStatusChanged(isChecked);
            }
            public void changeTaskStatus(String user, String task, String status) {
                RequestQueue queue= Volley.newRequestQueue(holder.task.getContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, "https://i.cs.hku.hk/~khchan4/task.php",
                       new Response.Listener<String>()
                       {
                           @Override
                           public void onResponse(String response) {

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
                       params.put("user", user);
                       params.put("id", task);
                       params.put("status", status);
                       return params;
                   }
               };
               queue.add(postRequest);
           }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public TaskAdapter(ArrayList<Task> tasks, boolean isOwner) {
        this.tasks=tasks;
        this.isOwner=isOwner;
    }

}