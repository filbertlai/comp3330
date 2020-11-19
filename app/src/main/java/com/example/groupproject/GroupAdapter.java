package com.example.groupproject;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    ArrayList<Group> groups;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View groupView;
        TextView name;
        TextView progress;
        TextView description;

        public ViewHolder(View view) {
            super(view);
            groupView=view;
            name=groupView.findViewById(R.id.name);
            progress=view.findViewById(R.id.progress);
            description=view.findViewById(R.id.description);
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
        holder.groupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();
                Group group=groups.get(position);
                Toast.makeText(view.getContext(), group.group_name, Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Group group=groups.get(position);
        holder.name.setText(group.group_name);
        holder.progress.setText(group.finished+"/"+group.total);
        holder.description.setText(group.group_description);
        if(group.finished.equals(group.total)) {
            holder.groupView.findViewById(R.id.groupHeader).setBackgroundColor(Color.parseColor("#a3e9a4"));
        }
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public GroupAdapter(ArrayList<Group> groups) {
        this.groups=groups;
    }

}