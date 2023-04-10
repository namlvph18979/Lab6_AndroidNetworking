package com.example.lab6_androidnetworking.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.lab6_androidnetworking.DAO.User_DAO;
import com.example.lab6_androidnetworking.DAO.Validate;
import com.example.lab6_androidnetworking.R;
import com.google.android.material.textfield.TextInputLayout;

public class Frag_create extends Fragment {

    TextInputLayout fullName,email,pass,pass2;
    TextView create;
    CheckBox checkBox;
    int check =0;
    User_DAO dao;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Frag_create() {
        // Required empty public constructor
    }


    public static Frag_create newInstance(String param1, String param2) {
        Frag_create fragment = new Frag_create();
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
        View view =inflater.inflate(R.layout.fragment_frag_create, container, false);
        call(view);
        Validate validate = new Validate();
        dao = new User_DAO();
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _fullName = fullName.getEditText().getText().toString().trim();
                String _pass = pass.getEditText().getText().toString().trim();
                String _pass2 = pass2.getEditText().getText().toString().trim();
                String _email = email.getEditText().getText().toString().trim();

                if (_fullName.isEmpty()){
                    fullName.setError("Mời bạn nhập họ và tên");
                    fullName.setErrorEnabled(true);
                } else if (!validate.checkInputName(_fullName)) {
                    fullName.setError("Có ký tự đặc biệt");
                }else{
                    fullName.setErrorEnabled(false);
                    check++;
                }

                if (_email.isEmpty()){
                    email.setError("Mời bạn nhập email");
                    email.setErrorEnabled(true);
                }else if (!validate.checkEmail(_email)){
                    email.setError("Sai định dạng email");
                }else{
                    email.setErrorEnabled(false);
                    check++;
                }

                if (_pass.isEmpty()){
                    pass.setError("Mời bạn nhập mật khẩu");
                    pass.setErrorEnabled(true);
                }else{
                    pass.setErrorEnabled(false);
                    check++;
                }

                if (_pass2.isEmpty()){
                    pass2.setError("Mời bạn nhập mật khẩu");
                    pass2.setErrorEnabled(true);
                }else if(!_pass.equals(_pass2)){
                    pass2.setError("Mật khẩu không khớp");
                    pass2.setErrorEnabled(true);
                }else{
                    pass2.setErrorEnabled(false);
                    check++;
                }
                if (!checkBox.isChecked()){
                    Toast.makeText(view.getContext(), "Chưa chấp nhận điều khoản", Toast.LENGTH_SHORT).show();
                }else{
                    check++;
                }


                if (check==5){
                    dao.createUser(_email,_pass,_fullName,view.getContext());
                    check=0;
                }else{
                    check=0;
                }
            }
        });
        return view;
    }
    private void call(View view){
        fullName = view.findViewById(R.id.Frag_create_edt_fullName);
        pass = view.findViewById(R.id.Frag_create_edt_pass);
        pass2 = view.findViewById(R.id.Frag_createedt_pass2);
        email = view.findViewById(R.id.Frag_create_edt_email);
        checkBox= view.findViewById(R.id.Frag_create_checkBox);
        create = view.findViewById(R.id.Frag_create_tv_create);
    }
}