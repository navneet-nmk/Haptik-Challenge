package com.teenvan.haptik;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.realm.Realm;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity implements Callback {

    private static final String TAG = MainActivity.class.getSimpleName() ;
    // Declaration of member variables
    String url = "http://haptik.mobi/android/test_data/";
    private Toolbar mToolbar;
    private TabLayout tabLayout;
    private ViewPager pager;
    private ArrayList<Message> mMessages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        pager = (ViewPager)findViewById(R.id.viewpager);
        pager.setAdapter(new MessageFragmentPagerAdapter(getSupportFragmentManager(), MainActivity.this));
        tabLayout.setupWithViewPager(pager);

        OkHttpClient client =  new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(this);


    }

    @Override
    public void onFailure(Call call, IOException e) {
        Log.d(TAG, e.getMessage());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        String responseData = response.body().string();
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString("Response", responseData);
        prefsEditor.apply();
        JSONObject json = null;
        mMessages = new ArrayList<>();
        try {
            json = new JSONObject(responseData);
            JSONArray messages = json.getJSONArray("messages");
            for(int i=0;i<messages.length();i++){
                JSONObject mssg = messages.getJSONObject(i);
                String body = mssg.getString("body");
                String username = mssg.getString("username");
                String name = mssg.getString("Name");
                String imageUrl = mssg.getString("image-url");
                String time = mssg.getString("message-time");

                Message message = new Message();
                message.setName(name);
                message.setBody(body);
                message.setImageUrl(imageUrl);
                message.setUsername(username);
                message.setMssgTime(time);

                realm.copyToRealmOrUpdate(message);

                mMessages.add(message);

            }

            realm.commitTransaction();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    GlobalBus.getBus().post(mMessages);
                }
            });



        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        GlobalBus.getBus().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        GlobalBus.getBus().unregister(this);
    }
}
