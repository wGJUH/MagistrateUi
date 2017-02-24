package com.example.wgjuh.magistrateprojectui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wgjuh.magistrateprojectui.R;
import com.example.wgjuh.magistrateprojectui.SingleUser;

import java.util.ArrayList;

/**
 * Created by wGJUH on 24.02.2017.
 */

public class ClassmatesAdapter extends RecyclerView.Adapter<ClassmatesAdapter.ViewHolder> {

    ArrayList<SingleUser> users;

    public ClassmatesAdapter(ArrayList<SingleUser> users){
        this.users = users;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user_list_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.groupName.setText(users.get(position).getGroupName());
        holder.userName.setText(users.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView groupName;
        public ViewHolder(View itemView) {
            super(itemView);
            userName = (TextView)itemView.findViewById(R.id.single_user_name);
            groupName = (TextView)itemView.findViewById(R.id.single_group_name);
        }
    }
}
