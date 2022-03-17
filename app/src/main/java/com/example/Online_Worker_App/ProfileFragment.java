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

public class ProfileFragment extends Fragment {

UserModel usersession;
EditText editname,editemail,editphone,editpassword;

      String getID="ID";
      TextView Edit,SaveEdit,Editphoto,UploadtoDB;
      CardView cardViewedit,cardViewsave;
      CircleImageView profile_image;
      String photo;
      Bitmap bitmap;
      String ecodedimage;
      String URL="http://192.168.132.193:80/worker/";
      Context mContext;



    public ProfileFragment() {
        // Required empty public constructor
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
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        editname=view.findViewById(R.id.editname);
        editemail=view.findViewById(R.id.editemail);
        editphone=view.findViewById(R.id.editmobile);
        editpassword=view.findViewById(R.id.editpassword);
        Edit=view.findViewById(R.id.update);
        SaveEdit=view.findViewById(R.id.save);
        SaveEdit.setVisibility(TextView.INVISIBLE);

        profile_image=view.findViewById(R.id.imageofpp);

        cardViewedit=view.findViewById(R.id.cardView10);
        cardViewsave=view.findViewById(R.id.cardView11);
        cardViewsave.setVisibility(CardView.INVISIBLE);

        usersession=new UserModel(getContext());

        HashMap<String,String> user=usersession.getUserDetail();
        getID=user.get(usersession.KEY_USERID);


        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editname.setFocusableInTouchMode(true);
                editemail.setFocusableInTouchMode(true);
                editphone.setFocusableInTouchMode(true);
                editpassword.setFocusableInTouchMode(true);

                InputMethodManager imm= (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editname,InputMethodManager.SHOW_IMPLICIT);
                Edit.setVisibility(TextView.INVISIBLE);
                cardViewsave.setVisibility(CardView.VISIBLE);
                cardViewedit.setVisibility(CardView.INVISIBLE);
                SaveEdit.setVisibility(TextView.VISIBLE);
            }
        });

        SaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("clicked","Save click");
                SaveDetails();

                Edit.setVisibility(TextView.VISIBLE);
                cardViewedit.setVisibility(CardView.VISIBLE);
               SaveEdit.setVisibility(TextView.INVISIBLE);
               cardViewsave.setVisibility(CardView.INVISIBLE);
                editname.setFocusableInTouchMode(false);
                editemail.setFocusableInTouchMode(false);
                editphone.setFocusableInTouchMode(false);
                editpassword.setFocusableInTouchMode(false);

            }
        });

        Editphoto= view.findViewById(R.id.editphto);
        Editphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"I clicked",Toast.LENGTH_LONG).show();
                ChooseFile();
            }
        });

        UploadtoDB=view.findViewById(R.id.uploadtodb);
        UploadtoDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UploadPicture(getID,ecodedimage);
            }
        });

        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

       if(requestCode==1 && resultCode==RESULT_OK && data!=null){
           Uri filePath =data.getData();
           try {
               InputStream inputStream=getContext().getContentResolver().openInputStream(filePath);
               bitmap =BitmapFactory.decodeStream(inputStream);
               profile_image.setImageBitmap(bitmap);

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
        ecodedimage= android.util.Base64.encodeToString(imagebytes, Base64.DEFAULT);

    }

    private void SaveDetails(){

        String updateuname=editname.getText().toString().trim();
        String updateUemail=editemail.getText().toString().trim();
        String updateUphone=editphone.getText().toString().trim();
        String updateUpassword=editpassword.getText().toString().trim();
        //String udatephoto=profile_image.getImageSrc();
        String uuid=getID;
        String account="User";

        Log.d("uudid",uuid);


        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Reading your details");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
       // progressDialog.show();

        String newRl = URL+"Edit_User_Detail.php";

        StringRequest request = new StringRequest(Request.Method.POST, newRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("profile", "Connected");
                try {
                        JSONObject jsonObject=new JSONObject(response);
                       Log.d("Fromfile",jsonObject.getString("message"));

                            if(!jsonObject.getBoolean("error")){
                                UserModel.getmInstance(getContext()).userLogin(uuid,updateuname,updateUemail,account,ecodedimage);
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
                param.put("id",uuid);
                param.put("name",updateuname);
                param.put("email",updateUemail);
                param.put("mobile",updateUphone);
                param.put("psw",updateUpassword);
               // param.put("state",state);
                Log.d("Hash",uuid+updateuname+" "+updateUphone);
                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(getContext()).addToRequestQueue(request);

    }// End of Save Function

    private void getUserDetail(){

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Reading your details");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        //progressDialog.show();

        String uRl = URL+"ReadUser_detail.php";

        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  progressDialog.dismiss();
                Log.d("Login", "Connected");
                try {
                    JSONObject jsonObject=new JSONObject(response);

                        if(!jsonObject.getBoolean("error")){

                            String Name=jsonObject.getString("name").trim();
                            String Email=jsonObject.getString("email").trim();
                            String Phone=jsonObject.getString("mobile").trim();
                            String Password=jsonObject.getString("password").trim();
                            String photo=jsonObject.getString("photo");

                            String PImagurl=URL+photo;


                            editname.setText(Name);
                            editemail.setText(Email);
                            editphone.setText(Phone);
                            editpassword.setText(Password);
                            profile_image.setImageBitmap(bitmap);

                            Glide.with(getContext()).load(photo).into(profile_image);


                            Glide.with(getContext())
                                    .load(PImagurl)
                                    .apply(new RequestOptions().override(225, 225))
                                    .centerCrop()
                                    .fitCenter()
                                    .placeholder(R.drawable.prif2)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(profile_image);


                            Log.d("Userid", jsonObject.getString("id"));
                            Log.d("photo", jsonObject.getString("photo"));
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
                param.put("id",getID);
                return param;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(getContext()).addToRequestQueue(request);

    }
    @Override
    public void onResume() {
        super.onResume();
        getUserDetail();


    }

       private void ChooseFile(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select picture"),1);

    }

   private void UploadPicture(final String id, final String photo){
       final ProgressDialog progressDialog = new ProgressDialog(getContext());
       progressDialog.setTitle("Uploading your Image");
       progressDialog.setCancelable(false);
       progressDialog.setCanceledOnTouchOutside(false);
       progressDialog.setIndeterminate(false);
       progressDialog.show();
       String uRlP = URL+"Userupload.php";
       StringRequest request = new StringRequest(Request.Method.POST, uRlP, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               Log.d("Login", "Connected");
               try {
                   JSONObject jsonObject=new JSONObject(response);
                   progressDialog.dismiss();
                       if(!jsonObject.getBoolean("error")){
                           String getp=jsonObject.getString("photo");
                           Log.d("Omag", getp);
                           usersession.setImage(getp);
                           Toast.makeText(getContext(),"Upload Successfully",Toast.LENGTH_LONG).show();
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
               param.put("id",id);
               param.put("photo",ecodedimage);
                return param;
           }
       };

       request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
       MySingleton.getmInstance(getContext()).addToRequestQueue(request);
   }
}