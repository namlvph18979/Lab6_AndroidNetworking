package com.example.lab6_androidnetworking.DAO;

import android.util.Log;

import java.util.regex.Pattern;

public class Validate {
    public boolean checkEmail(String email){
        String EMAIL_PATTERN = "^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$";
        boolean type = Pattern.matches(EMAIL_PATTERN,email);
        if (!type){
            return false;
        }
        return true;
    }
    public void checkInput(String input){
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9\\s]+$");
        String abc ="lamnh";
        String xyz =" lam nh";
        String xx ="09lamnh";
        Log.e("TAG", "checkInput: "+pattern.matcher(abc).find() );
        Log.e("TAG", "checkInput: "+pattern.matcher(xyz).find() );
        Log.e("TAG", "checkInput: "+pattern.matcher(xx).find() );
//        if (pattern.matcher(input).find()){
//            return true;
//        }
//        return false;
    }

    public boolean checkInputName(String inputName){
        Pattern pattern = Pattern.compile("^[a-z_A-Z\\s]+$");

        if (pattern.matcher(inputName).find()){
            return true;
        }
        return false;
    }

}
