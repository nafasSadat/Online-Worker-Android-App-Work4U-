package com.example.Online_Worker_App;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class WorkerProfileFragment extends Fragment {

    WorkerSession workerSession;
    EditText weditname,weditlastname,weditemail,weditphone,weditage,weditExp,weditSalary,weditAddress,weditpassword;

    String getWID="ID";
    RadioButton editradio;
    TextView wEdit,wSaveEdit,wEditphoto,wUploadtoDB;
    CardView wcardViewedit,wcardViewsave;
    CircleImageView worker_profile_image,circleImageView;
    String photo;
    Bitmap bitmap;
    String wecodedimage;
    String imgurls="http://192.168.165.193:80/worker/";



    public WorkerProfileFragment() {
        // Required empty public constructor
    }

    public static WorkerProfileFragment newInstance(String param1, String param2) {
        WorkerProfileFragment fragment = new WorkerProfileFragment();
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
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_worker_profile, container, false);


        weditname=view.findViewById(R.id.editnamew);
        weditlastname=view.findViewById(R.id.editlname);
        weditemail=view.findViewById(R.id.editemailw);
        weditphone=view.findViewById(R.id.edit_phonew);
        weditage=view.findViewById(R.id.edit_agew);
        weditExp=view.findViewById(R.id.edit_expw);
        weditSalary=view.findViewById(R.id.edit_salaryw);
        weditAddress=view.findViewById(R.id.edit_addressw);
        weditpassword=view.findViewById(R.id.edit_passw);
        wEdit=view.findViewById(R.id.updatew);
        wSaveEdit=view.findViewById(R.id.savew);
        wSaveEdit.setVisibility(TextView.INVISIBLE);

        worker_profile_image=view.findViewById(R.id.workrimgp);

        wcardViewedit=view.findViewById(R.id.cardView15);
        wcardViewsave=view.findViewById(R.id.cardView14);
        wcardViewsave.setVisibility(CardView.INVISIBLE);
        circleImageView=view.findViewById(R.id.workrimgp);

        workerSession=new WorkerSession(getContext());

        HashMap<String,String> user=workerSession.getWorkerDetail();
        getWID=user.get(workerSession.KER_WORKERID);

        wEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weditname.setFocusableInTouchMode(true);
                weditemail.setFocusableInTouchMode(true);
                weditage.setFocusableInTouchMode(true);
                weditAddress.setFocusableInTouchMode(true);

                InputMethodManager imm= (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(weditname,InputMethodManager.SHOW_IMPLICIT);
                wEdit.setVisibility(TextView.INVISIBLE);
                wcardViewsave.setVisibility(CardView.VISIBLE);
                wcardViewedit.setVisibility(CardView.INVISIBLE);
                wSaveEdit.setVisibility(TextView.VISIBLE);
            }
        });


        wSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("clicked","Save click");
                SaveWorkerDetails();

                wEdit.setVisibility(TextView.VISIBLE);
                wcardViewedit.setVisibility(CardView.VISIBLE);
                wSaveEdit.setVisibility(TextView.INVISIBLE);
                wcardViewsave.setVisibility(CardView.INVISIBLE);
                weditname.setFocusableInTouchMode(false);
                weditemail.setFocusableInTouchMode(false);
                weditphone.setFocusableInTouchMode(false);
                weditpassword.setFocusableInTouchMode(false);

            }
        });


        wEditphoto= view.findViewById(R.id.editphtoworker);
        wEditphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"I clicked",Toast.LENGTH_LONG).show();
                ChooseFile();
            }
        });

        wUploadtoDB=view.findViewById(R.id.uploadtodbworker);
        wUploadtoDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UploadWorkerPicture(getWID,wecodedimage);
            }
        });



        return view;
    }

    private void UploadWorkerPicture(String getWID, String wecodedimage) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading your Image");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        String uRlP = "http://192.168.165.193:80/worker/Worker_ImgUpload.php";

        StringRequest request = new StringRequest(Request.Method.POST, uRlP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Login", "Connected");
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    progressDialog.dismiss();
                    if(!jsonObject.getBoolean("error")){
                        String getp=jsonObject.getString("account");
                        Log.d("Omag", getp);
                        workerSession.setworkerImage(getp);

                        Toast.makeText(getContext(),"Upload Successfully",Toast.LENGTH_LONG).show();

                        Log.d("UploadImg", "Sucess");
                    }else{
                        Toast.makeText(getContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(getContext(),"Try Again!",Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("id",getWID);
                param.put("photo",wecodedimage);
                return param;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(getContext()).addToRequestQueue(request);
    }

    private void ChooseFile() {

        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select picture"),1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==RESULT_OK && data!=null){
            Uri filePath =data.getData();
            try {
                InputStream inputStream=getContext().getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                worker_profile_image.setImageBitmap(bitmap);
                imageStore(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    private void imageStore(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imagebytes=byteArrayOutputStream.toByteArray();
        wecodedimage= android.util.Base64.encodeToString(imagebytes, Base64.DEFAULT);

    }



    private void SaveWorkerDetails() {
        String updatewname= weditname.getText().toString().trim();
        String updatewlastname= weditlastname.getText().toString().trim();
        String updatewemail= weditemail.getText().toString().trim();
        String updatewphone= weditphone.getText().toString().trim();
        String updatewage= weditage.getText().toString().trim();
        String updatewexp= weditExp.getText().toString().trim();
        String updatewsalary= weditSalary.getText().toString().trim();
        String updatewadd = weditAddress.getText().toString().trim();
        String updatewpassword=weditpassword.getText().toString().trim();
        //String udatephoto=profile_image.getImageSrc();
        String wid=getWID;
        String account="Worker";
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Updating... ");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();

        String newRl = "http://192.168.165.193:80/worker/Edit_Worker_Details.php";

        StringRequest request = new StringRequest(Request.Method.POST, newRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("profile", "Connected");
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Log.d("Fromfile",jsonObject.getString("message"));

                    if(!jsonObject.getBoolean("error")){
                        progressDialog.dismiss();
                        // String imgurl="http://192.168.113.193:80/worker/"+image;
                        WorkerSession.getwInstance(getContext()).WorkerLogin(wid,updatewname,updatewemail,account,wecodedimage);
                        Toast.makeText(getContext(),"Update Successfully",Toast.LENGTH_LONG).show();

                        Log.d("updated", "Login Sucess");

                    }else {
                        Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("id",wid);
                param.put("name",updatewname);
                param.put("lastname",updatewlastname);
                param.put("email",updatewemail);
                param.put("mobile",updatewphone);
                param.put("age",updatewage);
                param.put("exper",updatewexp);
                param.put("salary",updatewsalary);
                param.put("add",updatewadd);
                param.put("psw",updatewpassword);
                // param.put("state",state);
                Log.d("Hash",wid+updatewname+" "+updatewlastname);
                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(getContext()).addToRequestQueue(request);

    }

    @Override
    public void onResume() {
        super.onResume();
        getWorkerDetail();
    }

    private void getWorkerDetail() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Reading your details");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
       // progressDialog.show();
        String uRl = "http://192.168.165.193:80/worker/ReadWorker_detail.php";

        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  progressDialog.dismiss();
                Log.d("worpr", "Connected");
                try {
                    JSONObject jsonObject=new JSONObject(response);

                    if(!jsonObject.getBoolean("error")){

                        String WName=jsonObject.getString("name").trim();
                        String WLName=jsonObject.getString("lastname").trim();
                        String WEmail=jsonObject.getString("email").trim();
                        String WPhone=jsonObject.getString("phone").trim();
                        String WAge=jsonObject.getString("age").trim();
                       // String WJob=jsonObject.getString("job").trim();
                        String WExp=jsonObject.getString("experience").trim();
                        String WSalary=jsonObject.getString("salary").trim();
                        String WAddress=jsonObject.getString("address").trim();
                        String wPassword=jsonObject.getString("password").trim();
                        String photo=jsonObject.getString("photo");

                        Log.d("namewp",photo);
                        String imgurl="http://192.168.165.193:80/worker/"+photo;

                        weditname.setText(WName);
                        weditlastname.setText(WLName);
                        weditemail.setText(WEmail);
                        weditphone.setText(WPhone);
                        weditage.setText(WAge);

                        weditExp.setText(WExp);
                        weditSalary.setText(WSalary);
                        weditAddress.setText(WAddress);
                        weditpassword.setText(wPassword);

                        Glide.with(getContext())
                                .load(imgurl)
                                .apply(new RequestOptions().override(225, 225))
                                .centerCrop()
                                .fitCenter()
                                .placeholder(R.drawable.prif2)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(circleImageView);


                        Log.d("Userid", jsonObject.getString("id"));
                        Log.d("email", jsonObject.getString("address"));
                        Log.d("Login", "Login Sucess");
                    }else{
                        Toast.makeText(getContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("id",getWID);
                Log.d("wor",getWID);
                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(getContext()).addToRequestQueue(request);

    }
}