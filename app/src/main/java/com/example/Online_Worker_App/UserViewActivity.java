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

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserViewActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ViewPager viewPager;
    private DrawerLayout drawer;
    public String title;

    UserModel userModel;

    CircleImageView profile;
    TextView Uname,Uemail;
    String N;
    String E;
    String PImage;


    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);

        userModel=new UserModel(this);



         N=userModel.get_Name();
         E=userModel.getEmail();
        PImage=userModel.getImage();



        viewPager = (ViewPager)findViewById(R.id.viewpager);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("Home").setIcon(R.drawable.ic_baseline_home_24));
        tabLayout.addTab(tabLayout.newTab().setText("Category").setIcon(R.drawable.ic_baseline_category_24));
        tabLayout.addTab(tabLayout.newTab().setText("Profile").setIcon(R.drawable.ic_baseline_account_circle_24));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("W4U");
        toolbar.setSubtitle("");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.usernameheader);
        nav_user.setText(N);
        TextView nav_email = (TextView)hView.findViewById(R.id.useremailheader);
        nav_email.setText(E);
        CircleImageView nav_image=hView.findViewById(R.id.nav_profile_image);

        Glide.with(getApplicationContext())
                .load(PImage)
                .apply(new RequestOptions().override(225, 225))
                .centerCrop()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(nav_image);




       // Glide.with(getApplicationContext()).load(PImage).into(nav_image);


        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

        //set viewpager adapter
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        //change Tab selection when swipe ViewPager
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //change ViewPager page when tab selected
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        if (savedInstanceState == null) {
            navigationView.getMenu().performIdentifierAction(R.id.nav_camera,0);
        }


    }//End of Oncreate

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_minu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.itemlogout:
                userModel.isLogout();
                Intent intent=new Intent(this,MainActivity.class);
                finish();
                startActivity(intent);
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        FragmentManager fm  = getSupportFragmentManager();
        Fragment fragment=null;
        switch(item.getItemId()) {
            case R.id.nav_camera:
                title="Sample App";
                viewPager.setCurrentItem(0);

                break;

            case R.id.nav_gallery:
                //viewPager.setCurrentItem(1);
                viewPager.setCurrentItem(1);
                break;

            case R.id.nav_slideshow:
                viewPager.setCurrentItem(2);

                break;

            case R.id.nav_search:
                Intent intent=new Intent(this,SearchViewActivity.class);
                //Toast.makeText(getApplicationContext(),"Toast",Toast.LENGTH_LONG).show();
                startActivity(intent);
                break;
            default:
                break;


        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        item.setChecked(true);
        getSupportActionBar().setTitle(title);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;




    }


    }



