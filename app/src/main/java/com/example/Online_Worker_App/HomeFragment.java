package com.example.Online_Worker_App;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter mAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    RecyclerAdapter recyclerAdapter;

    List<UserData> WorkersData;
    public HomeFragment() {

    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.products_recyclerView);
        manager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(manager);

        WorkersData=new ArrayList<>();

        recyclerAdapter=new RecyclerAdapter(getContext(),WorkersData);
        recyclerView.setAdapter(recyclerAdapter);

        swipeRefreshLayout=view.findViewById(R.id.refresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.teal_700));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.setAdapter(recyclerAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        getAllData();

        return view;
    }

    private void getAllData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.show();

        String uRl = "http://172.16.204.105:80/worker/getWorkers.php";

        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d("HomeLogin", "Connected");
                if(!response.equals("error")) {
                    try {
                        JSONArray array = new JSONArray(response);
                        Log.d("Home", array.getString(1));

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
                            String Workersatte=object.getString("wstate").trim();

                            String imgurl="http://172.16.204.105:80/worker/"+image;
                            Log.d("rate", rating);
                            Log.d("im2", imgurl);

                            String rate = String.valueOf(rating);
                            float newRate = Float.valueOf(rate);
                            Log.d("UnewRate", Workersatte+"");
                            UserData user_data = new UserData(id, name,lastname, email, mobile,age,job,experience,salary,address, gender, imgurl, newRate,Workersatte);
                            WorkersData.add(user_data);

                            recyclerAdapter.notifyDataSetChanged();
                        }

                    } catch (Exception e) {
                        Toast.makeText(getContext(),response+""+e,Toast.LENGTH_LONG).show();
                        Log.d("ErrorH", response+""+e);
                    }

                    mAdapter = new RecyclerAdapter(getContext(),WorkersData);
                    recyclerView.setAdapter(mAdapter);
                }else {
                    Toast.makeText(getContext(),response,Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(getContext()).addToRequestQueue(request);
    }
}