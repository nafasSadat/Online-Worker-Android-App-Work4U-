package com.example.Online_Worker_App;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

public class ActivityRegister extends AppCompatActivity {
    EditText userName,emailAddress,password,mobile;
    RadioGroup radioGroup;
    Button register;
    TextView gotologin,gotoworkerpage;
    String uRl = "http://172.16.204.105:80/worker/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = findViewById(R.id.username);
        emailAddress = findViewById(R.id.email);
        password = findViewById(R.id.password);
        mobile = findViewById(R.id.mobile);
        radioGroup = findViewById(R.id.radioButton);


        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String user_name = userName.getText().toString();
                final String email = emailAddress.getText().toString();
                final String txt_password = password.getText().toString();
                final String txt_mobile = mobile.getText().toString();
                int checkedId = radioGroup.getCheckedRadioButtonId();
                RadioButton selected_gender = radioGroup.findViewById(checkedId);

                if (selected_gender == null){
                    Toast.makeText(ActivityRegister.this, "Select gender please", Toast.LENGTH_SHORT).show();
                }
                else {
                    final String gender = selected_gender.getText().toString();
                    if(TextUtils.isEmpty(user_name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(txt_password) ||
                            TextUtils.isEmpty(txt_mobile)){
                        Toast.makeText(ActivityRegister.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    }

                    else{
                        register(user_name,email,txt_password,txt_mobile,gender);
                    }
                }

                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        gotologin=findViewById(R.id.gotologinpage);
        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }



    private void register(final String username, final String email, final String password, final String mobile, final String gender){
        final ProgressDialog progressDialog = new ProgressDialog(ActivityRegister.this);
        progressDialog.setTitle("Registering your account");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("You are registered successfully")){
                    Toast.makeText(ActivityRegister.this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ActivityRegister.this,MainActivity.class));
                    progressDialog.dismiss();
                    finish();
                }else {
                    Toast.makeText(ActivityRegister.this, response, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ActivityRegister.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("username",username);
                param.put("email",email);
                param.put("psw",password);
                param.put("mobile",mobile);
                param.put("gender",gender);
                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(ActivityRegister.this).addToRequestQueue(request);

    }
}