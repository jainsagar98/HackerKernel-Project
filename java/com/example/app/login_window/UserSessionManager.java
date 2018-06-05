package com.example.app.login_window;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Windows on 04-06-2018.
 */

public class UserSessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;


    public UserSessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences("My Pref", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createUserLoginSession(String response){
        editor.putBoolean("IsUserLoggedIn", true);
        editor.putString("myResponse3", response);
        editor.commit();
    }

    public boolean checkLogin(){
        if(!this.isUserLoggedIn()){
            Intent i = new Intent(_context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);

            return true;
        }
        return false;
    }


    public String getUserDetails(){

        String user = pref.getString("myResponse3", null);
        return user;
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, MainActivity.class);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }
    public boolean isUserLoggedIn(){

        return pref.getBoolean("IsUserLoggedIn", true);
    }
}
