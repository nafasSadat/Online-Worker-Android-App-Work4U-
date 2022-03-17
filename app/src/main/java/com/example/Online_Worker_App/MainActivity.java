package com.example.Online_Worker_App;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText email,password;
    Button login;
    TextView gotoregister;
    CheckBox checkedStatus;
    SharedPreferences sharedPreferences;
    String profileimage;

    TextView gotoworkers;

    String uRl = "http://172.16.204.105:80/worker/";
    private Context mCtx;
    private List<UserData> userData;

    RadioGroup radioGroup2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        checkedStatus = findViewById(R.id.checkbox);
        radioGroup2 = findViewById(R.id.workeroruser);





        userData = new ArrayList<>();


        if(WorkerSession.getwInstance(this).isWLoggedIn()){
                finish();
                startActivity(new Intent(this,WorkerViewActivity.class));
            }
        if(UserModel.getmInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,UserViewActivity.class));
        }


        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        String loginStatus = sharedPreferences.getString(getResources().getString(R.string.prefStatus),"");
        if (loginStatus.equals("loggedin")){
            startActivity(new Intent(MainActivity.this,UserViewActivity.class));
            finish();
        }


        TextView textView=findViewById(R.id.gotoregister);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), ActivityRegister.class);
                startActivity(intent);
                finish();
            }
        });


        gotoworkers=findViewById(R.id.gotoworker);
        gotoworkers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),WorkerRegister.class);
                startActivity(intent);
                finish();
            }
        });



        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String tex_email = email.getText().toString();
                String tex_password = password.getText().toString();
                int checkedId = radioGroup2.getCheckedRadioButtonId();
                RadioButton selected_state = radioGroup2.findViewById(checkedId);
                if (selected_state == null){
                    Toast.makeText(MainActivity.this, "Select gender please", Toast.LENGTH_SHORT).show();
                }else {
                    final String state = selected_state.getText().toString();
                    if (TextUtils.isEmpty(tex_email) || TextUtils.isEmpty(tex_password)){
                    Toast.makeText(MainActivity.this, "All Fields Required", Toast.LENGTH_SHORT).show();
                    }
                else{

                    login(tex_email,tex_password,state);
                    }
                }
            }
        });

    }


    private void login(final String email, final String password,final String state){
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
       // String uRl = "http://192.168.165.193:80/worker/Loginn.php";
        String uRL=uRl+"Loginn.php";

        StringRequest request = new StringRequest(Request.Method.POST, uRL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Login", "Connected");
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString("account").equals("User")){

                        if(!jsonObject.getBoolean("error")){

                            String id=jsonObject.getString("id");
                            String u_name=jsonObject.getString("name");
                            String u_email=jsonObject.getString("email");
                            String u_account=jsonObject.getString("account");
                            String u_img=jsonObject.getString("photo").trim();
                            String imgurl=uRl+u_img;
                            UserModel.getmInstance(getApplicationContext()).userLogin(id, u_name, u_email, u_account, imgurl);

                            Log.d("Userimg", jsonObject.getString("photo"));
                            Log.d("Userimg2", imgurl);
                            Toast.makeText(getApplicationContext(),"Login Successfully",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(MainActivity.this,UserViewActivity.class);
                            startActivity(intent);
                            finish();
                            Log.d("Login", "Login Sucess");
                        }else{
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();

                        }

                    //Login us Worker
                    }else if(jsonObject.getString("account").equals("Worker")){

                        Log.d("account", jsonObject.getString("account"));
                        Log.d("name", jsonObject.getString("name"));
                        Log.d("response",response);
                        if(!jsonObject.getBoolean("error")){

                            String id=jsonObject.getString("id");
                            String u_name=jsonObject.getString("name");
                            String u_email=jsonObject.getString("email");
                            String u_account=jsonObject.getString("account");
                            String u_img=jsonObject.getString("photo").trim();

                            String imgurl2=uRl+u_img;


                           WorkerSession.getwInstance(getApplicationContext())
                                   .WorkerLogin(id,u_name,u_email,u_account,imgurl2);

                            Log.d("nameshow", jsonObject.getString("photo"));
                            Log.d("nameshow", imgurl2);
                            Toast.makeText(getApplicationContext(),"Login Successfully",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(MainActivity.this,WorkerViewActivity.class);
                            startActivity(intent);
                            finish();
                            Log.d("Login", "Login Sucess");
                        }else{
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();

                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                //param.put("name",name)
                param.put("email",email);
                param.put("psw",password);
                param.put("state",state);
                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(MainActivity.this).addToRequestQueue(request);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}