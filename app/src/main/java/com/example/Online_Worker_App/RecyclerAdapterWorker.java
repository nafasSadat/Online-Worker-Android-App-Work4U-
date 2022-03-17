package com.example.Online_Worker_App;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class RecyclerAdapterWorker extends RecyclerView.Adapter<RecyclerAdapterWorker.MyViewHolder> {

    private Context mContext;
    private List<UserData> usersdata ;
    RequestOptions requestOptions;

    public RecyclerAdapterWorker(Context context, List<UserData> products){
        this.mContext = context;
        this.usersdata = products;
        requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

    }

    @NonNull
    @Override
    public RecyclerAdapterWorker.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.category_litst_item,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterWorker.MyViewHolder holder, int position) {

         UserData userInfo = usersdata.get(position);
        Log.d("position",position+"");

        holder.wPhone1.setText("Phone: "+userInfo.getMobile());
        holder.Name1.setText(userInfo.getName());
        holder.rateing.setText(String.valueOf(userInfo.getRating()));
        holder.job1.setText(userInfo.getJob());
        holder.state.setText(userInfo.getWorkerState());


        //Glide.with(mContext).load(userInfo.getImage()).into(holder.mImageView);
        Log.d("Adapter",userInfo.getImage());

       // Glide.with(mContext).load(products.get(position).getImage()).apply(requestOptions).into(holder.mImageView);
        Glide.with(mContext)
                .load(usersdata.get(position).getImage())
                .apply(new RequestOptions().override(225, 225))
                .centerCrop()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.wImgeVie1);


        holder.wContainer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, DetailActivityWorker.class);

                intent.putExtra("id",String.valueOf(userInfo.getId()));
                intent.putExtra("name",userInfo.getName());
                intent.putExtra("lastname",userInfo.getLastname());
                intent.putExtra("email",userInfo.getEmail());
                intent.putExtra("phone",userInfo.getMobile());
                intent.putExtra("age",userInfo.getAge());
                intent.putExtra("skill",userInfo.getJob());
                intent.putExtra("exper",userInfo.getExperience());
                intent.putExtra("salary",userInfo.getSalary());
                intent.putExtra("address",userInfo.getAddress());
                intent.putExtra("gender",userInfo.getGender());
                intent.putExtra("image",userInfo.getImage());
                intent.putExtra("rate",String.valueOf(userInfo.getRating()));
                intent.putExtra("state",userInfo.getWorkerState());

                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return usersdata.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView Name1, wPhone1,job1,rateing,state;
        private ImageView wImgeVie1;

        private LinearLayout wContainer1;


        public MyViewHolder (View view){
            super(view);

            Name1 = view.findViewById(R.id.workerName1);
            wImgeVie1 = view.findViewById(R.id.worker_image1);
            job1 = view.findViewById(R.id.Worker_Skill1);
            wPhone1=view.findViewById(R.id.Worker_phone1);
            state=view.findViewById(R.id.userstate);
            rateing=view.findViewById(R.id.showrate);
            wContainer1 = view.findViewById(R.id.category_container);
        }
    }
}
