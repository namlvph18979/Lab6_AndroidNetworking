package com.example.lab6_androidnetworking.DAO;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.lab6_androidnetworking.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class User_DAO {

    FirebaseUser _UserAuth = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public void createUser(String email, String passwd, String fullname, Context context){
        mAuth.createUserWithEmailAndPassword(email,passwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser Fuser = mAuth.getCurrentUser();
                            Fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(fullname)
                                            .build();
                                    Fuser.updateProfile(profileUpdates)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(context, "Vui lòng xác nhận Email.",Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                            });

                        } else {
                            Toast.makeText(context, "Tạo tài khoản thất bại.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void loginUser(String email,String passwd,Context context){
        mAuth.signInWithEmailAndPassword(email,passwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            boolean emailVerified = firebaseUser.isEmailVerified();
                            Log.e("TAG", "email Verified: "+emailVerified );
                            if (emailVerified != true) {
                                Toast.makeText(context, "Bạn cần xác nhận email trước khi đăng nhập", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, MainActivity.class);
                                context.startActivity(intent);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Tài Khoản hoặc Mật Khẩu Không Đúng", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void checkUser(TextView username,TextView email){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        username.setText(user.getDisplayName());
        email.setText(user.getEmail());
    }

}
