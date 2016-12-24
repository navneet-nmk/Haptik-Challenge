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
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;


public class MessagesFragment extends Fragment {
    // Declaration of member variables
    private RecyclerView mMessagesList;
    private ArrayList<Message> mMessages;
    String url = "http://haptik.mobi/android/test_data/";
    RealmResults<Message> results;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_messages, container, false);


        GlobalBus.getBus().register(this);

        Realm.init(getActivity());

        mMessagesList = (RecyclerView)root.findViewById(R.id.messagesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mMessagesList.setLayoutManager(mLayoutManager);
        mMessagesList.setItemAnimator(new DefaultItemAnimator());

        // Get the messages from database
        Realm realm = Realm.getDefaultInstance();
         results = realm.where(Message.class)
                .findAll();

        MessagesAdapter adapter = new MessagesAdapter(new ArrayList<>(results), getActivity());
        mMessagesList.setAdapter(adapter);


        return root;
    }


    @Subscribe
    public void getMessages(ArrayList<Message> mMessages){
        if(results == null) {
            MessagesAdapter adapter = new MessagesAdapter(mMessages, getActivity());
            mMessagesList.setAdapter(adapter);
        }
    }

}
