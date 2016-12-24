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

import java.util.List;

import io.realm.Realm;

/**
 * Created by navneet on 08/12/16.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {

    private List<Message> mMessageList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, body;
        public ImageView mUserImage;
        public CheckBox mFavCheckBox;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.nameText);
            mUserImage = (ImageView)view.findViewById(R.id.userImage);
            body = (TextView)view.findViewById(R.id.bodyText);
            mFavCheckBox = (CheckBox)view.findViewById(R.id.favoriteBox);
        }
    }

    public MessagesAdapter(List<Message> messages, Context context){
        this.mMessageList = messages;
        this.context = context;
    }

    @Override
    public MessagesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessagesAdapter.MyViewHolder holder, int position) {
        Realm.init(context);
        final Realm realm = Realm.getDefaultInstance();
            final Message message =  mMessageList.get(position);
            holder.name.setText(message.getName());
            holder.body.setText(message.getBody());
        if(!message.getImageUrl().isEmpty()) {
            Picasso.with(context).load(message.getImageUrl()).into(holder.mUserImage);
        }

        holder.mFavCheckBox.setChecked(message.isFavorited());

        holder.mFavCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, final boolean b) {

                   realm.executeTransaction(new Realm.Transaction() {
                       @Override
                       public void execute(Realm realm) {
                            Message messg = realm.where(Message.class)
                                    .beginGroup()
                                    .equalTo("name",
                                    message.getName())
                                    .equalTo("body", message.getBody())
                                    .endGroup()
                                    .findFirst();
                           messg.setFavorited(b);
                       }
                   });

            }
        });

        notifyDataSetChanged();


    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }
}
