package com.example.groupproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupproject.ui.manage.ManageFragment_other;

import java.util.ArrayList;

public class MemberAdapter2 extends RecyclerView.Adapter<MemberAdapter2.ViewHolder> {
    ArrayList<Member> members;
    ArrayList<Task> tasks=new ArrayList<Task>();


    static class ViewHolder extends RecyclerView.ViewHolder {
        View memberView;
        TextView name;
        TextView progress;

        public ViewHolder(View view) {
            super(view);
            memberView=view;
            name=memberView.findViewById(R.id.memberName);
            progress=view.findViewById(R.id.memberProgress);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.member_item, parent, false);
        final ViewHolder holder=new ViewHolder(view);
        CardView cardView=(CardView)view.findViewById(R.id.memberCardView);
        cardView.setRadius(30);
        cardView.setCardElevation(10);
        holder.memberView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int position = holder.getAdapterPosition();
                Member member=members.get(position);
                Toast.makeText(view.getContext(), "Progress of "+member.username, Toast.LENGTH_SHORT).show();


                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment f= new ManageFragment_other();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, f).addToBackStack(null).commit();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Member member=members.get(position);
        holder.name.setText(member.username);
        if(member.total.equals("0")) {
            holder.progress.setText("0.0%");
            return;
        }
        float p=Float.parseFloat(member.finished)/Float.parseFloat(member.total)*100;
        holder.progress.setText(String.format("%.1f", p)+"%");
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public MemberAdapter2(ArrayList<Member> members) {
        this.members=members;
    }

}