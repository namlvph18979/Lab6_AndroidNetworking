package com.example.lab6_androidnetworking.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.lab6_androidnetworking.DAO.User_DAO;
import com.example.lab6_androidnetworking.R;
import com.google.android.material.textfield.TextInputLayout;


public class Frag_login extends Fragment {

    TextInputLayout email,passWord;
    CheckBox checkBox;
    TextView login;
    SharedPreferences sharedPreferences;
    int check =0;
    User_DAO dao;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Frag_login() {
        // Required empty public constructor
    }


    public static Frag_login newInstance(String param1, String param2) {
        Frag_login fragment = new Frag_login();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_frag_login, container, false);
        call(view);
        dao = new User_DAO();
        sharedPreferences = getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
        email.getEditText().setText(sharedPreferences.getString("userName",""));
        passWord.getEditText().setText(sharedPreferences.getString("passWord",""));
        checkBox.setChecked(sharedPreferences.getBoolean("rememberMe",false));
        String linkAPI = "https://26tugd-8080.csb.app/users";
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _userName = email.getEditText().getText().toString().trim();
                String _passWord = passWord.getEditText().getText().toString().trim();
                if (_userName.isEmpty()){
                    email.setError("Mời bạn nhận UserName");
                    email.setErrorEnabled(true);
                }else{
                    email.setErrorEnabled(false);
                    check ++;
                }
                if (_passWord.isEmpty()){
                    passWord.setError("Mời bạn nhận PassWord");
                    passWord.setErrorEnabled(true);
                }else{
                    passWord.setErrorEnabled(false);
                    check ++;
                }
                if (check==2){
                    remember(_userName,_passWord,checkBox.isChecked());
                    dao.loginUser(_userName,_passWord,view.getContext());
                    check=0;
                }else {
                    check =0;
                }

            }
        });
        return view;
    }
    public void call(View view){
        email = view.findViewById(R.id.Frag_login_edt_userName);
        passWord = view.findViewById(R.id.Frag_login_edt_passWord);
        checkBox = view.findViewById(R.id.Frag_login_checkBox);
        login = view.findViewById(R.id.Frag_login_tv_login);
    }
    public void remember(String user,String pass,boolean check){
        sharedPreferences = getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!check){
            editor.clear();
        }else{
            editor.putString("userName",user);
            editor.putString("passWord",pass);
            editor.putBoolean("rememberMe",check);
        }
        editor.commit();
    }
}