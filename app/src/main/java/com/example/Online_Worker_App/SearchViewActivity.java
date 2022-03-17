package com.example.Online_Worker_App;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import android.app.SearchManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchViewActivity extends AppCompatActivity {


    private List<UserSearchData> actorsLists;
    private SearchAdapter searchAdapter;
    private RecyclerView recyclerView;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);


        Toolbar toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("W4U");
        toolbar.setSubtitle("");
        recyclerView=findViewById(R.id.recyclers_erach);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getData();

        actorsLists=new ArrayList<>();

        searchAdapter=new SearchAdapter(this,actorsLists);
        recyclerView.setAdapter(searchAdapter);

    }

    private void getData(){
        final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
//        progressDialog.show();
        String uRl = "http://192.168.132.193:80/worker/getWorkers.php";

        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d("SearchConn", "Connected");
                if(!response.equals("error")) {
                    try {

                        JSONArray array = new JSONArray(response);

                        Log.d("Search", array.getString(1));
                        for (int i = 0; i < array.length(); i++) {
                            Log.d("serachresp", i+" ");

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
                            Log.d("searchname", name);
                            Log.d("im2", imgurl);

                            String rate = String.valueOf(rating);
                            float newRate = Float.valueOf(rate);
                            Log.d("UnewRate", Workersatte+"");

                            UserSearchData user_data1 = new UserSearchData(id, name,lastname, email, mobile,age,job,experience,salary,address, gender, imgurl, newRate,Workersatte);
                            actorsLists.add(user_data1);
                            searchAdapter.notifyDataSetChanged();
                        }

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),response+""+e,Toast.LENGTH_LONG).show();
                        Log.d("ErrorH", response+""+e);
                    }

                    searchAdapter = new SearchAdapter(SearchViewActivity.this,actorsLists);
                    recyclerView.setAdapter(searchAdapter);
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
        });

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(request);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        final MenuItem menuItem=menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        //SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

       // searchView= (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (searchView.isIconified()) {
                } else {
                    searchView.setIconified(true);
                }
                menuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                final List<UserSearchData>filtermodList=filter(actorsLists,newText);
                searchAdapter.setfilter(filtermodList);
                return true;
            }
        });

        return true;
    }

    private List<UserSearchData>filter(List<UserSearchData>hi,String query){
        query=query.toLowerCase();
        final List<UserSearchData> filtermodList=new ArrayList<>();
        for (UserSearchData modal:hi){
            final String text=modal.getName().toLowerCase();
            if(text.startsWith(query)){
                filtermodList.add(modal);
            }
        }
        return filtermodList;
    }
}