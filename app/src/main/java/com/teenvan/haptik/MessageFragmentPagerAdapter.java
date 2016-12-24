package com.teenvan.haptik;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by navneet on 08/12/16.
 */

public class MessageFragmentPagerAdapter extends FragmentPagerAdapter{

    // Declaration of member variables
     final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "All Messages", "Contact Map" };
    private Context context;


    public MessageFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
               MessagesFragment cFragment = new MessagesFragment();
                return cFragment;
            case 1:
                UsersFragment fragment = new UsersFragment();
                return fragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
