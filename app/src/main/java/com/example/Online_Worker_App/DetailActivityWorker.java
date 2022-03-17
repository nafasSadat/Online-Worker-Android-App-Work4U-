package com.example.Online_Worker_App;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.imageview.ShapeableImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivityWorker extends AppCompatActivity {


    public String WorkerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_worker);

        String phoneNum;
        String Wname,phto;



        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("W4U");
        toolbar.setSubtitle("");



        TextView name=findViewById(R.id.wname);
        TextView lastname=findViewById(R.id.wlastname);
        TextView email=findViewById(R.id.wemailadd);
        TextView phone=findViewById(R.id.wphonenumber);
        TextView age=findViewById(R.id.wage);
        TextView skill=findViewById(R.id.wskill);
        TextView exp=findViewById(R.id.wexper);
        TextView salry=findViewById(R.id.wsalary);
        TextView address=findViewById(R.id.waddress);
        TextView gender=findViewById(R.id.wgender);
        TextView rate=findViewById(R.id.wrate);
        ShapeableImageView shapeableImageView=findViewById(R.id.singleimg);


        phoneNum=getIntent().getStringExtra("phone");

        WorkerID=getIntent().getStringExtra("id");
        Log.d("wid",WorkerID+"");

        Wname=getIntent().getStringExtra("name");
        name.setText(Wname);
        lastname.setText(getIntent().getStringExtra("lastname"));
        email.setText(getIntent().getStringExtra("email"));
        phone.setText(getIntent().getStringExtra("phone"));
        age.setText(getIntent().getStringExtra("age"));
        skill.setText(getIntent().getStringExtra("skill"));
        exp.setText(getIntent().getStringExtra("exper"));
        salry.setText(getIntent().getStringExtra("salary"));
        address.setText(getIntent().getStringExtra("address"));
        gender.setText(getIntent().getStringExtra("gender"));
        rate.setText(getIntent().getStringExtra("rate"));
        phto=getIntent().getStringExtra("image");
        String WState =getIntent().getStringExtra("state");


        Glide.with(this)
                .load(phto)
                .apply(new RequestOptions().override(225, 225))
                .centerCrop()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(shapeableImageView);



        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(DetailActivityWorker.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.contactlayout);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                TextView buttoncall=dialog.findViewById(R.id.buttonforcall);

                TextView texname=dialog.findViewById(R.id.namecontace);
                texname.setText(Wname);
                TextView phoneno=dialog.findViewById(R.id.phonedialog);
                phoneno.setText(phoneNum);
                CircleImageView circleImageView=dialog.findViewById(R.id.imagedialog);

                Glide.with(dialog.getContext())
                        .load(phto)
                        .apply(new RequestOptions().override(225, 225))
                        .centerCrop()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(circleImageView);

                buttoncall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri;
                        uri = Uri.parse("tel:"+phoneNum);
                        Intent intent=new Intent(Intent.ACTION_DIAL,uri);
                        startActivity(intent);

                        dialog.dismiss();
                    }
                });

                TextView textmsg=dialog.findViewById(R.id.textmessage);
                textmsg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent=new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        String phone=phoneNum;
                        String shareSub="Your Subject here";
                        intent.putExtra(Intent.EXTRA_SUBJECT,phoneNum);
                        intent.putExtra(Intent.EXTRA_TEXT,phoneNum);
                        startActivity(Intent.createChooser(intent,phone.toString()));
                        dialog.dismiss();
                    }
                });


                dialog.show();

            }

        });
    }

    }
