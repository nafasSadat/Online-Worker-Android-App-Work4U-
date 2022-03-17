package com.example.Online_Worker_App;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkerRegister extends AppCompatActivity {

    EditText workername,workerlastname,workeremailAddress,workermobile,workerage,wexper,wsalary,waddress,wpassword;
    RadioGroup wradioGroup;
    Button wregister;
    TextView wgotologin,wgotoworkerpage;
    Spinner spinner;
    String uRl = "http://192.168.165.193:80/worker/worker_register.php";
    String spinner_job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_register);


        workername = findViewById(R.id.wusername);
        workerlastname = findViewById(R.id.wlastname);
        workeremailAddress = findViewById(R.id.wemail);
        workermobile = findViewById(R.id.wmobile);
        workerage = findViewById(R.id.wage);
        spinner = findViewById(R.id.spinnerid);
        wexper = findViewById(R.id.wexperince);
        wsalary = findViewById(R.id.wsalary);
        waddress = findViewById(R.id.waddress);
        wpassword = findViewById(R.id.wpassword);
        wradioGroup = findViewById(R.id.wradioButton);


        List<String> list=new ArrayList<String>();

        ArrayAdapter<CharSequence> arrayAdapter= ArrayAdapter.createFromResource(this,R.array.works, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_job=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        wregister = findViewById(R.id.wregister);

        wregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String wuser_name = workername.getText().toString();
                final String wlastname = workerlastname.getText().toString();
                final String wemail = workeremailAddress.getText().toString();
                final String wmobile = workermobile.getText().toString();
                final String w_age = workerage.getText().toString();
                final String w_exper = wexper.getText().toString();
                final String w_salary = wsalary.getText().toString();
                final String w_password = wpassword.getText().toString();
                final String w_address = waddress.getText().toString();

                int checkedId = wradioGroup.getCheckedRadioButtonId();
                RadioButton selected_wgender = wradioGroup.findViewById(checkedId);
                if (selected_wgender == null) {
                    Toast.makeText(WorkerRegister.this, "Select gender please", Toast.LENGTH_SHORT).show();
                } else {
                    final String wgender = selected_wgender.getText().toString();
                    if (TextUtils.isEmpty(wuser_name) || TextUtils.isEmpty(wlastname) || TextUtils.isEmpty(wemail) ||
                            TextUtils.isEmpty(wmobile) || TextUtils.isEmpty(w_age) || TextUtils.isEmpty(spinner_job) || TextUtils.isEmpty(w_exper)
                            || TextUtils.isEmpty(w_salary) || TextUtils.isEmpty(w_password) || TextUtils.isEmpty(w_address)) {
                        Toast.makeText(WorkerRegister.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    } else {
                        wokerregister(wuser_name, wlastname,wemail, wmobile, w_age, spinner_job,w_exper,w_salary,w_address,w_password,wgender);
                    }
                }
            }
        });



    }

    private void wokerregister(final String WName,final String WLName, final String WEmail,final String Wmobile,final String Wage,final String WJob,
                               final String WExper,final String WSalary,final String WAdd,final String WPassword,final String Wgender){


        final ProgressDialog progressDialog = new ProgressDialog(WorkerRegister.this);
        progressDialog.setTitle("Registering your account");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();


        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("You are registered successfully")){
                    Toast.makeText(WorkerRegister.this, response, Toast.LENGTH_SHORT).show();

                       Intent intent = new Intent(WorkerRegister.this,MainActivity.class);
                       intent.putExtra("Parent","WorkerRegister");
                       startActivity(intent);

                    progressDialog.dismiss();
                    finish();
                }else {
                    Toast.makeText(WorkerRegister.this, response, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WorkerRegister.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("name",WName);
                param.put("lastname",WLName);
                param.put("email",WEmail);
                param.put("phone",Wmobile);
                param.put("age",Wage);
                param.put("job",WJob);
                param.put("experience",WExper);
                param.put("salary",WSalary);
                param.put("address",WAdd);
                param.put("gender",Wgender);
                param.put("psw",WPassword);
                Log.d("pasword",WPassword);
                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(WorkerRegister.this).addToRequestQueue(request);

    }
}