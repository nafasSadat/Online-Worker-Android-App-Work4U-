package com.example.Online_Worker_App;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RatingBar;
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
import com.google.android.material.imageview.ShapeableImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity {
    RatingBar ratingBar;
    float ratevalue;
    float finalrate,averagerate;
    public String rateurl="http://192.168.132.193:80/worker/Rating_worker.php";
    public String WorkerID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String phoneNum;
        String Wname,phto;



        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("W4U");
        toolbar.setSubtitle("");



        TextView name=findViewById(R.id.name);
        TextView lastname=findViewById(R.id.lastname);
        TextView email=findViewById(R.id.emailadd);
        TextView phone=findViewById(R.id.phonenumber);
        TextView age=findViewById(R.id.age);
        TextView skill=findViewById(R.id.skill);
        TextView exp=findViewById(R.id.exper);
        TextView salry=findViewById(R.id.salary);
        TextView address=findViewById(R.id.address);
        TextView gender=findViewById(R.id.gender);
        TextView rate=findViewById(R.id.rate);
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


        String text="Submit Your Feedback";
               TextView feedback=findViewById(R.id.feedbak);

        SpannableString ss=new SpannableString(text);
        ClickableSpan clickableSpan=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {


            }
        };
        ss.setSpan(clickableSpan,0,20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        feedback.setText(ss);

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitrate();
            }
        });


        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(DetailActivity.this);
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

    private void submitrate() {

        final Dialog dialog = new Dialog(DetailActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.submit_rate_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView countrate=dialog.findViewById(R.id.usersatify);
        TextView sumbit=dialog.findViewById(R.id.submitrate);
        TextView cancel=dialog.findViewById(R.id.canclrate);
        RatingBar ratingBar = dialog.findViewById(R.id.Rating_bar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratevalue=ratingBar.getRating();
                if(ratevalue<=1 && rating>0){
                    countrate.setText("Bad");
                    finalrate=ratevalue;
                    Log.d("rate",finalrate+"");

                }else if(ratevalue<=2 && ratevalue>1){
                    countrate.setText("Ok");
                    finalrate=ratevalue;
                    Log.d("rate",finalrate+"");
                }else if (ratevalue<=3 && ratevalue>2){
                    countrate.setText("Good");
                    finalrate=ratevalue;
                    Log.d("rate",finalrate+"");
                }else if(ratevalue<=4 && ratevalue>3){
                    countrate.setText("Very Good");
                    finalrate=ratevalue;
                    Log.d("rate",finalrate+"");

                }else if(ratevalue <=5 && ratevalue>4){
                    countrate.setText("Best");
                    finalrate=ratevalue;
                    Log.d("rate",finalrate+"");
                }


            }
        });
sumbit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        averagerate=(finalrate/5);
        Log.d("average",averagerate+"");
        countrate.setText(" ");
        ratingBar.setRating(0);
        SendRatetoDB();
        dialog.dismiss();

    }
});

cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        averagerate=0f;
        countrate.setText(" ");
        ratingBar.setRating(0);
         dialog.dismiss();
    }
});

        dialog.show();

    }




    private void SendRatetoDB() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Submitting...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
         progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, rateurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("connectformrateing", "Connected");
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Log.d("rate",jsonObject.getString("message"));
                    progressDialog.dismiss();

                    if(!jsonObject.getBoolean("error")){
                        Toast.makeText(getApplicationContext(),"Rate Successfully",Toast.LENGTH_LONG).show();

                        Log.d("updated", "Login Sucess");

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
                Toast.makeText(getApplicationContext(), "Not able to Submit", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("id",WorkerID);
                param.put("rate", String.valueOf(averagerate));
                Log.d("wokerid",WorkerID+averagerate+" ");
                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(getApplicationContext()).addToRequestQueue(request);
    }

}