package com.example.Online_Worker_App;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class WorkerViewActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ViewPager woker_viewPager;
    private DrawerLayout worker_drawer;
    public String title;

    WorkerSession workerSession;

    CircleImageView profile;
    TextView Uname,Uemail;
    String WN;
    String WE;
    String WPImage;
    RequestOptions reqOptions;
    String WorkerAvailibality;
    String Worker_ID;
    String updateurl = "http://192.168.132.193:80/worker/Update_State.php";

    private TabLayout worker_tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_view);

        workerSession=new WorkerSession(this);

        WN=workerSession.get_Name();
        WE=workerSession.getEmail();
        WPImage=workerSession.getWImage();
        Worker_ID=workerSession.get_WId();
        Log.d("widupd",Worker_ID);





        workerSession=new WorkerSession(this);

        woker_viewPager = (ViewPager)findViewById(R.id.viewpager_worker);

        worker_tabLayout = (TabLayout) findViewById(R.id.worker_tab_layout);

        worker_tabLayout.addTab(worker_tabLayout.newTab().setText("Home").setIcon(R.drawable.ic_baseline_home_24));
        worker_tabLayout.addTab(worker_tabLayout.newTab().setText("Category").setIcon(R.drawable.ic_baseline_category_24));
        worker_tabLayout.addTab(worker_tabLayout.newTab().setText("Profile").setIcon(R.drawable.ic_baseline_account_circle_24));

        worker_tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        Toolbar toolbar = (Toolbar) findViewById(R.id.worker_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("W4U");
        toolbar.setSubtitle("");


        DrawerLayout worker_drawer = (DrawerLayout) findViewById(R.id.Worker_drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, worker_drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        worker_drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_worker);
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.workernameheader);
        nav_user.setText(WN);
        TextView nav_email = (TextView)hView.findViewById(R.id.worker_email_header);
        nav_email.setText(WE);
        CircleImageView nav_image=hView.findViewById(R.id.nav_profile_image_worker);



        Glide.with(getApplicationContext())
                .load(WPImage)
                .apply(new RequestOptions().override(225, 225))
                .centerCrop()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(nav_image);

        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

        WViewPagerAdaper pagerAdapter = new WViewPagerAdaper(getSupportFragmentManager());
        woker_viewPager.setAdapter(pagerAdapter);
        woker_viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(worker_tabLayout));

        worker_tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                woker_viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        if (savedInstanceState == null) {
            navigationView.getMenu().performIdentifierAction(R.id.worker_homefragment,0);
        }



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.Worker_drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @SuppressLint("ShowToast")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

       // FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = null;
        FragmentManager fm  = getSupportFragmentManager();
        switch(item.getItemId()) {
            case R.id.worker_homefragment:
                title="Sample App";
                woker_viewPager.setCurrentItem(0);
                break;

            case R.id.worker_profilefragment:
                 woker_viewPager.setCurrentItem(2);
                break;

            case R.id.nav_category:
                woker_viewPager.setCurrentItem(1);
                break;

            case R.id.nav_update_state:
                ShowDialog();

                break;

            case R.id.nav_searchw:
                Intent intent=new Intent(this,SearchViewActivity.class);
                Toast.makeText(getApplicationContext(),"Toast",Toast.LENGTH_LONG).show();
                startActivity(intent);

                break;

            case R.id.nav_logout:
                logoutfunc();
                break;
            default:
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.Worker_drawerLayout);
        item.setChecked(true);
        getSupportActionBar().setTitle(title);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }

    private void ShowDialog() {
        final Dialog dialog = new Dialog(WorkerViewActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.state_change_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));



        TextView sumbitbrn=dialog.findViewById(R.id.avsumbit);
        TextView cancelbtn=dialog.findViewById(R.id.avcancel);

        RadioGroup radioGroupavi=dialog.findViewById(R.id.availRadiobrn);

        sumbitbrn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedId = radioGroupavi.getCheckedRadioButtonId();
                RadioButton selected_gender = radioGroupavi.findViewById(checkedId);
                if (selected_gender == null){
                    Toast.makeText(WorkerViewActivity.this, "Select Availability please", Toast.LENGTH_SHORT).show();
                }
                else {
                    WorkerAvailibality=selected_gender.getText().toString();
                    UpdateState(WorkerAvailibality);
                    dialog.dismiss();

                }
            }
        });

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void UpdateState(String Status) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Updating...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, updateurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("connectformrateing", "Connected");
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Log.d("rate",jsonObject.getString("message"));
                    progressDialog.dismiss();

                    if(!jsonObject.getBoolean("error")){
                        Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_LONG).show();

                    }else {
                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("id",Worker_ID);
                param.put("status",Status);
                Log.d("sendtodb",Worker_ID+Status+" ");
                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void logoutfunc() {
        workerSession.isLogout();
        Intent intent=new Intent(WorkerViewActivity.this,MainActivity.class);
        finish();
        startActivity(intent);
    }
}