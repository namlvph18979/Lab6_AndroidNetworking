package com.example.lab6_androidnetworking.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab6_androidnetworking.MainActivity;
import com.example.lab6_androidnetworking.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Acti_hello extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acti_hello);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2626);
                    NextLogin();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
    public  void NextLogin(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user==null){
            startActivity(new Intent(Acti_hello.this, Acti_opition.class));
            finish();
        }else{
            startActivity(new Intent(Acti_hello.this, MainActivity.class));
            finish();
        }
    }
}