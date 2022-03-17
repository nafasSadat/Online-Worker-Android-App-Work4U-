package com.example.Online_Worker_App;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.RequestQueue;

import java.util.HashMap;

public class WorkerSession {

    public static final String KER_WORKERID ="id";
    private static WorkerSession mInstance;
    private RequestQueue mRequestQueue;

    public static final String SHARED_PREF_NAME_Worker="workersharedpref";
    public static final String KEY_ACCOUNTW="worker_account";
    public static final String KEY_WOKERNAME="name";
    public static final String KEY_WORKEREMAIL="email";
    public static final String KEY_WORKERPHONE="phone";
    public static final String KEY_WORKERIMG="image";

    private static final String KEY_WORKERAGE="age";
    private static final String KEY_WORKERKILL="skill";
    private static final String KEY_WORKEREXPER="exp";
    private static final String KEY_WORKERSALART="salary";
    private static final String KEY_WORKERADDRESS="address";
    private static final String KEY_WORKERGENDER="gender";
    private static final String KEY_WORKERPASSWORD="password";


    private Context mCtx;

    public WorkerSession(Context mCtx) {
        this.mCtx = mCtx;

    }

    public static synchronized WorkerSession getwInstance(Context context){

        if (mInstance == null){
            mInstance = new WorkerSession(context);
        }
        return mInstance;
    }



    public boolean WorkerLogin(String id,String name,String email,String Accountw,String image){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME_Worker,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(KEY_ACCOUNTW,Accountw);
        editor.putString(KEY_WOKERNAME,name);
        editor.putString(KEY_WORKEREMAIL,email);
        editor.putString(KEY_WORKERIMG,image);
        editor.putString(KER_WORKERID,id);
        editor.apply();
        return true;
    }


    public boolean setworkerImage(String photo){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME_Worker,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(KEY_WORKERIMG,photo);
        editor.apply();
        return true;

    }


    public boolean isWLoggedIn(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME_Worker,Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_ACCOUNTW,null)!=null){
            return true;
        }
        return false;
    }

    public boolean isLogout(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME_Worker,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;

    }

    public String get_Name(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME_Worker,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_WOKERNAME,null);

    }
    public String getEmail(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME_Worker,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_WORKEREMAIL,null);

    }


    public String get_WId(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME_Worker,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KER_WORKERID,null);

    }

    public String get_Wphone(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME_Worker,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_WORKERPHONE,null);

    }

    public String getWImage(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME_Worker,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_WORKERIMG,null);
    }

    public HashMap<String,String> getWorkerDetail() {
        HashMap<String, String> user = new HashMap<>();
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME_Worker,Context.MODE_PRIVATE);
        user.put(KER_WORKERID,sharedPreferences.getString(KER_WORKERID,null));
        user.put(KEY_WOKERNAME,sharedPreferences.getString(KEY_WOKERNAME,null));
        user.put(KEY_WORKEREMAIL,sharedPreferences.getString(KEY_WORKEREMAIL,null));
        user.put(KEY_ACCOUNTW,sharedPreferences.getString(KEY_ACCOUNTW,null));
        Log.d("FromUsersession",KER_WORKERID);
        return user;
    }
}
