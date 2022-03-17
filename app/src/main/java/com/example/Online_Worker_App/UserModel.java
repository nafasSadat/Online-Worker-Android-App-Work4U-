package com.example.Online_Worker_App;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.RequestQueue;

import java.util.HashMap;

public class UserModel {

    private static UserModel mInstance;
    SharedPreferences sharedPreferences;
    private RequestQueue mRequestQueue;
    public static final String KEY_UERNAME="name";
    public static final String KEY_EMAIL="email";
    public static final String KEY_USERID="id";
    private static final String SHARED_PREF_NAME="mysharedpref12";
    public static final String KEY_ACCOUNT="account";
    public static  String KEY_IMAGE="photo";

    private Context mCtx;

    public UserModel(Context mCtx) {
        this.mCtx = mCtx;

    }

    public static synchronized UserModel getmInstance(Context context){

        if (mInstance == null){
            mInstance = new UserModel(context);
        }
        return mInstance;
    }

public boolean userLogin(String id,String name,String email,String Account,String photo){
SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
SharedPreferences.Editor editor=sharedPreferences.edit();
    editor.putString(KEY_USERID, id);
editor.putString(KEY_UERNAME,name);
editor.putString(KEY_EMAIL,email);
editor.putString(KEY_ACCOUNT,Account);
editor.putString(KEY_IMAGE,photo);
editor.apply();
return true;

}

    public boolean setImage(String photo){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        KEY_IMAGE=null;
        editor.putString(KEY_IMAGE,photo);
        editor.apply();
        return true;

    }



public boolean isLoggedIn(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_ACCOUNT,null)!=null){
            return true;
        }
        return false;
}



    public boolean isLogout(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;

    }

    public String get_Name(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_UERNAME,null);

    }
    public String getEmail(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL,null);

    }

    public int get_Id(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return Integer.parseInt(sharedPreferences.getString(KEY_USERID,null));

    }

public HashMap<String,String> getUserDetail() {
    HashMap<String, String> user = new HashMap<>();
    SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
    user.put(KEY_USERID,sharedPreferences.getString(KEY_USERID,null));
    user.put(KEY_UERNAME,sharedPreferences.getString(KEY_UERNAME,null));
    user.put(KEY_EMAIL,sharedPreferences.getString(KEY_USERID,null));
    user.put(KEY_ACCOUNT,sharedPreferences.getString(KEY_ACCOUNT,null));
    Log.d("FromUsersession",KEY_USERID);
    return user;
}


public String getImage(){
    SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
    return sharedPreferences.getString(KEY_IMAGE,null);
}

}






