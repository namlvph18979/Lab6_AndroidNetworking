package com.example.lab6_androidnetworking.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.lab6_androidnetworking.Adapter.user_Adapter;
import com.example.lab6_androidnetworking.R;
import com.google.android.material.tabs.TabLayout;

public class Acti_opition extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acti_opition);
        tabLayout = findViewById(R.id.Acti_opition_TabLayout);
        viewPager = findViewById(R.id.Acti_opition_ViewPager);
        user_Adapter adapter = new user_Adapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}