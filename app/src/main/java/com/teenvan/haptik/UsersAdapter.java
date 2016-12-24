package com.teenvan.haptik;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import io.realm.Realm;

/**
 * Created by navneet on 08/12/16.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {

    private HashMap<String, User> mMessageList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, messg, favMessg;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.nameText);
            messg = (TextView)view.findViewById(R.id.bodyText);
            favMessg = (TextView)view.findViewById(R.id.favText);
        }
    }

    public UsersAdapter(HashMap<String, User> messages, Context context){
        this.mMessageList = messages;
        this.context = context;
    }

    @Override
    public UsersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UsersAdapter.MyViewHolder holder, int position) {

        ArrayList a = new ArrayList(mMessageList.values());
        User u = (User) a.get(position);
        holder.name.setText(u.getName());
        holder.messg.setText(u.getMsgCount()+"");
        holder.favMessg.setText(u.getFavmsgCount()+"");

        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }
}
