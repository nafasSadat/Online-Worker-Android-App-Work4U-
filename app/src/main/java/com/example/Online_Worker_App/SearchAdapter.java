package com.example.Online_Worker_App;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MySearchViewHolder>  {
    private Context mContext;
    private List<UserSearchData> searchData ;

    RequestOptions requestOptions;
    public SearchAdapter (Context context,List<UserSearchData> usersearchs){
        this.mContext = context;
        this.searchData = usersearchs;
        requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

    }
    @NonNull
    @Override
    public SearchAdapter.MySearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.worker_litst_item_search,parent,false);
        return new SearchAdapter.MySearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MySearchViewHolder holder, int position) {

        UserSearchData searinfo = searchData.get(position);
        Log.d("position",position+"");

        holder.sname.setText(searinfo.getName());
        holder.sjob.setText(searinfo.getJob());
        holder.saddress.setText(String.valueOf(searinfo.getAddress()));
        Log.d("Adapter",searinfo.getAddress());

        holder.sContainer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,DetailActivity.class);

                intent.putExtra("id",String.valueOf(searinfo.getId()));
                intent.putExtra("name",searinfo.getName());
                intent.putExtra("lastname",searinfo.getLastname());
                intent.putExtra("email",searinfo.getEmail());
                intent.putExtra("phone",searinfo.getMobile());
                intent.putExtra("age",searinfo.getAge());
                intent.putExtra("skill",searinfo.getJob());
                intent.putExtra("exper",searinfo.getExperience());
                intent.putExtra("salary",searinfo.getSalary());
                intent.putExtra("address",searinfo.getAddress());
                intent.putExtra("gender",searinfo.getGender());
                intent.putExtra("image",searinfo.getImage());
                intent.putExtra("rate",String.valueOf(searinfo.getRating()));
                intent.putExtra("state",searinfo.getWorkerState());

                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchData.size();
    }

    public void setfilter(List<UserSearchData>actorsList) {
        searchData = new ArrayList<>();
        searchData.addAll(actorsList);
        notifyDataSetChanged();

    }

    public class MySearchViewHolder extends RecyclerView.ViewHolder {

        private TextView sname, sjob,saddress;


        private LinearLayout sContainer1;


        public MySearchViewHolder (View view){
            super(view);

            sname = view.findViewById(R.id.worker_Name);
            sjob = view.findViewById(R.id.Worker_Skill_search);
            saddress = view.findViewById(R.id.Worker_add);
            sContainer1 = view.findViewById(R.id.wsearch_container);

        }
    }

}
