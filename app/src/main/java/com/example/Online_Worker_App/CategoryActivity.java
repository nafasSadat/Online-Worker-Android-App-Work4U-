package com.example.Online_Worker_App;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryActivity extends AppCompatActivity {


    private RecyclerView cat_recyclerView;
    private RecyclerView.LayoutManager cmanager;
    private RecyclerView.Adapter cAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

     String category;

    CagetoryAdapter categoryadatper;
    List<CategoryData> categoryData;

    public CategoryActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        Toolbar toolbar = (Toolbar) findViewById(R.id.cat_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("W4U");
        toolbar.setSubtitle("");


        categoryData=new ArrayList<>();

        Intent intent = getIntent();
        category=intent.getStringExtra("Category");

        cat_recyclerView =findViewById(R.id.category_recyclerView);
        cmanager = new GridLayoutManager(this, 2);
        cat_recyclerView.setLayoutManager(cmanager);

        categoryData=new ArrayList<>();

        categoryadatper=new CagetoryAdapter(this,categoryData);
        cat_recyclerView.setAdapter(categoryadatper);

        swipeRefreshLayout=findViewById(R.id.catrefresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.teal_700));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cat_recyclerView.setAdapter(categoryadatper);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        getCategory(category);

    }

    private void getCategory(String category) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.show();

        String uRl = "http://192.168.132.193:80/worker/Category.php";

        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d("Catlogin", "Connected");
                if(!response.equals("error")) {
                    try {

                        JSONArray array = new JSONArray(response);

                        Log.d("Cat", array.getString(1));
                        for (int i = 0; i < array.length(); i++) {
                            Log.d("responess", i+" ");

                            JSONObject object = array.getJSONObject(i);

                            int id = object.getInt("id");
                            String name = object.getString("name").trim();
                            String lastname = object.getString("lastname").trim();
                            String email = object.getString("email").trim();
                            String mobile = object.getString("phone").trim();
                            String age = object.getString("age").trim();
                            String job = object.getString("job").trim();
                            String experience = object.getString("experience").trim();
                            String salary = object.getString("salary").trim();
                            String address = object.getString("address").trim();
                            String gender = object.getString("gender").trim();
                            String image = object.getString("photo").trim();
                            String rating=object.getString("rate");
                            String status=object.getString("wstate").trim();
                            String imgurl="http://192.168.165.193:80/worker/"+image;
                            Log.d("wstate", status);
                            Log.d("categoryimg2", imgurl);

                            String rate = String.valueOf(rating);
                            float newRate = Float.valueOf(rate);

                            CategoryData category_data = new CategoryData(id, name,lastname, email, mobile,age,job,experience,salary,address, gender, imgurl, newRate,status);
                            categoryData.add(category_data);

                            categoryadatper.notifyDataSetChanged();
                        }

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),response+""+e,Toast.LENGTH_LONG).show();
                        Log.d("ErrorH", response+""+e);
                    }

                    cAdapter = new CagetoryAdapter(CategoryActivity.this,categoryData);
                    cat_recyclerView.setAdapter(cAdapter);
                }else {
                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("job",category);
                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(request);

    }
}