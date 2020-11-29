package com.example.groupproject;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupproject.ui.group.GroupFragment;
import com.example.groupproject.ui.home.HomeFragment;
import com.example.groupproject.ui.join.JoinFragment;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    ArrayList<Task> tasks;
    boolean isOwner;

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