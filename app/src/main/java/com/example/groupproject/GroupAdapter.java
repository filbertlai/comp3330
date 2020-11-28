package com.example.groupproject;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    ArrayList<Group> groups;
    boolean viewJoined;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View groupView;
        TextView name;
        TextView progress;
        TextView description;
        ImageButton join;

        public ViewHolder(View view) {
            super(view);
            groupView=view;
            name=groupView.findViewById(R.id.name);
            progress=view.findViewById(R.id.progress);
            description=view.findViewById(R.id.description);
            join=view.findViewById(R.id.join);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item, parent, false);
        final ViewHolder holder=new ViewHolder(view);
        CardView cardView=(CardView)view.findViewById(R.id.groupCardView);
        cardView.setRadius(30);
        cardView.setCardElevation(10);
        if(viewJoined) {
            holder.groupView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getAdapterPosition();
                    Group group = groups.get(position);
                    //Toast.makeText(view.getContext(), group.group_name, Toast.LENGTH_SHORT).show();
                    AppCompatActivity activity=(AppCompatActivity)view.getContext();
                    GroupFragment f=new GroupFragment();
                    f.setgpno(group.group_id);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, f).addToBackStack(null).commit();
                }
            });
        }
        else {
            holder.progress.setVisibility(View.GONE);
            holder.join.setVisibility(View.VISIBLE);
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
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Group group=groups.get(position);
        holder.name.setText(group.group_name);
        holder.description.setText(group.group_description);
        if(!viewJoined) {
            return;
        }
        holder.progress.setText(group.finished+"/"+group.total);
        if(group.finished.equals(group.total)) {
            holder.groupView.findViewById(R.id.groupHeader).setBackgroundColor(ContextCompat.getColor(holder.groupView.getContext(),R.color.finished));
        }
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public GroupAdapter(ArrayList<Group> groups, boolean viewJoined) {
        this.groups=groups;
        this.viewJoined=viewJoined;
    }

}