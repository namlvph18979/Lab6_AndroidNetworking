package com.example.lab6_androidnetworking.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.lab6_androidnetworking.UI.Frag_create;
import com.example.lab6_androidnetworking.UI.Frag_login;

public class user_Adapter extends FragmentStatePagerAdapter {

    public user_Adapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new Frag_login();
            case 1 : return new Frag_create();
            default: return new Frag_login();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title ="";
        switch (position){
            case 0 : title ="Login";
                break;
            case 1 : title ="Create";
                break;
        }
        return title;
    }
}
