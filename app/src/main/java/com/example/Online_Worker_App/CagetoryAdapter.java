package com.example.Online_Worker_App;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class CagetoryAdapter extends RecyclerView.Adapter<CagetoryAdapter.MyCategoryHolder>{

    private Context mContext;
    private List<CategoryData> catdata ;
    RequestOptions requestOptions;


    public CagetoryAdapter (Context context, List<CategoryData> categoryData){
        this.mContext = context;
        this.catdata = categoryData;
        requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

    }



    @Override
    public CagetoryAdapter.MyCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.worker_litst_item,parent,false);

        return new CagetoryAdapter.MyCategoryHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyCategoryHolder holder, int position) {

        CategoryData catinfo = catdata.get(position);
        Log.d("position",position+"");

        holder.wPhone.setText("Phone: "+catinfo.getMobile());
        holder.wName.setText(catinfo.getName());
        holder.job.setText(catinfo.getJob());
        holder.showrate2.setText(String.valueOf(catinfo.getRating()));
        holder.status.setText(catinfo.getStatus());


        //Glide.with(mContext).load(userInfo.getImage()).into(holder.mImageView);
        Log.d("Adapter",catdata.get(position).getImage());

        // Glide.with(mContext).load(products.get(position).getImage()).apply(requestOptions).into(holder.mImageView);
        Glide.with(mContext)
                .load(catdata.get(position).getImage())
                .apply(new RequestOptions().override(225, 225))
                .centerCrop()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.wImgeView);


        holder.wContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,DetailActivity.class);

                intent.putExtra("id",catinfo.getId());
                intent.putExtra("name",catinfo.getName());
                intent.putExtra("lastname",catinfo.getLastname());
                intent.putExtra("email",catinfo.getEmail());
                intent.putExtra("phone",catinfo.getMobile());
                intent.putExtra("age",catinfo.getAge());
                intent.putExtra("skill",catinfo.getJob());
                intent.putExtra("exper",catinfo.getExperience());
                intent.putExtra("salary",catinfo.getSalary());
                intent.putExtra("address",catinfo.getAddress());
                intent.putExtra("gender",catinfo.getGender());
                intent.putExtra("image",catinfo.getImage());
                intent.putExtra("rate",String.valueOf(catinfo.getRating()));

                mContext.startActivity(intent);

            }
        });

    }



    @Override
    public int getItemCount() {
        return catdata.size();
    }




    public class MyCategoryHolder extends RecyclerView.ViewHolder {

        private TextView wName, wPhone,job,showrate2,status;
        private ImageView wImgeView;
        private RatingBar wRate;
        private LinearLayout wContainer;


        public MyCategoryHolder (View view){
            super(view);

            wName = view.findViewById(R.id.workerName);
            wImgeView = view.findViewById(R.id.worker_image);
            showrate2 = view.findViewById(R.id.showrate2);
            job = view.findViewById(R.id.Worker_Skill);
            status=view.findViewById(R.id.satus2);
            wPhone=view.findViewById(R.id.Worker_phone);

            wContainer = view.findViewById(R.id.worker_container);
        }
    }
}
