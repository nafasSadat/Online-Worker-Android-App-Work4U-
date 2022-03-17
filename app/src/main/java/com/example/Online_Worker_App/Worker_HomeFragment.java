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

public class Worker_HomeFragment extends Fragment {

    private RecyclerView wrecyclerView;
    private RecyclerView.LayoutManager wmanager;
    private RecyclerView.Adapter wmAdapter;
    SwipeRefreshLayout wswipeRefreshLayout;

    RecyclerAdapterWorker wrecyclerAdapter;
    List<UserData> Woker_Data;
    UserData workerData;


    public Worker_HomeFragment() {
        // Required empty public constructor
    }


    public static Worker_HomeFragment newInstance(String param1, String param2) {
        Worker_HomeFragment fragment = new Worker_HomeFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_worker__home, container, false);

        wrecyclerView = view.findViewById(R.id.worker_recyclerView);
        wmanager = new GridLayoutManager(getContext(), 2);
        wrecyclerView.setLayoutManager(wmanager);

        Woker_Data=new ArrayList<>();
        wrecyclerAdapter=new RecyclerAdapterWorker(getContext(),Woker_Data);
        wrecyclerView.setAdapter(wrecyclerAdapter);

        wswipeRefreshLayout=view.findViewById(R.id.refresh);
        wswipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.teal_700));
        wswipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                wrecyclerView.setAdapter(wrecyclerAdapter);
                wswipeRefreshLayout.setRefreshing(false);
            }
        });

        getAllWorkerData();

        return view;

    }

    private void getAllWorkerData() {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Reading your details");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        //progressDialog.show();

        String uRl = "http://192.168.132.193:80/worker/getWorkers.php";

        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  progressDialog.dismiss();
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

                            String imgurl="http://192.168.132.193:80/worker/"+image;

                            Log.d("im2", imgurl);

                             String rate = String.valueOf(rating);
                            float newRate = Float.valueOf(rate);
                            Log.d("newRate", Workersatte+"");


                            UserData user_data = new UserData(id, name,lastname, email, mobile,age,job,experience,salary,address, gender, imgurl, newRate,Workersatte);
                            Woker_Data.add(user_data);

                            wrecyclerAdapter.notifyDataSetChanged();
                        }

                    } catch (Exception e) {
                        Toast.makeText(getContext(),response+""+e,Toast.LENGTH_LONG).show();
                        Log.d("ErrorH", response+""+e);
                    }

                    wmAdapter = new RecyclerAdapterWorker(getContext(), Woker_Data);
                    wrecyclerView.setAdapter(wmAdapter);
                }else {
                    Toast.makeText(getContext(),response,Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(getContext(), "Error Connection ",Toast.LENGTH_LONG).show();

            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(getContext()).addToRequestQueue(request);

    }
}