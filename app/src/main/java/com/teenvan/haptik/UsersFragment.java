package com.teenvan.haptik;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;


public class UsersFragment extends Fragment {
    // Declaration of member variables
    private RecyclerView mMessagesList;
    private  RealmResults<Message> results;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_users, container, false);

        mMessagesList = (RecyclerView)root.findViewById(R.id.usersList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mMessagesList.setLayoutManager(mLayoutManager);
        mMessagesList.setItemAnimator(new DefaultItemAnimator());

        GlobalBus.getBus().register(this);


        Realm.init(getActivity());
        Realm realm = Realm.getDefaultInstance();

         results = realm.where(Message.class)
                .findAll();



        HashMap<String, User> map = changeToHashMap(new ArrayList<Message>(results));
        UsersAdapter adapter = new UsersAdapter(map, getActivity());
        mMessagesList.setAdapter(adapter);


        results.addChangeListener(new RealmChangeListener<RealmResults<Message>>() {
            @Override
            public void onChange(RealmResults<Message> element) {
                HashMap<String, User> map = changeToHashMap(new ArrayList<Message>(results));
                UsersAdapter adapter = new UsersAdapter(map, getActivity());
                mMessagesList.setAdapter(adapter);
            }
        });

        return root;
    }

    public HashMap<String, User> changeToHashMap(ArrayList<Message> results){
        HashMap<String, User> map = new HashMap<>();

        for(int i=0;i<results.size();i++){
            Message m = results.get(i);
            User u = map.get(m.getUsername());
            if(u!=null){
                u.setMsgCount(u.getMsgCount()+1);
                if(m.isFavorited()){
                    u.setFavmsgCount(u.getFavmsgCount()+1);
                }
            }else if(u==null){
                u = new User();
                u.setName(m.getName());
                u.setMsgCount(0);
                u.setFavmsgCount(0);
            }
            map.put(m.getUsername(), u);
        }
        return map;
    }

    @Subscribe
    public void getMessages(ArrayList<Message> mMessages){
        if(results == null) {
            HashMap<String, User> map = changeToHashMap(mMessages);
            UsersAdapter adapter = new UsersAdapter(map, getActivity());
            mMessagesList.setAdapter(adapter);
        }
    }




}
