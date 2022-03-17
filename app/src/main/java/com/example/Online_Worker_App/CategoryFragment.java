package com.example.Online_Worker_App;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class CategoryFragment extends Fragment {

    CardView electrical,plubming,painting,carpentry,powerman,landscaping;

    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
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
        View view= inflater.inflate(R.layout.fragment_category, container, false);
        electrical=view.findViewById(R.id.cardViewelectrical);
        plubming=view.findViewById(R.id.cardviewplumbing);
        painting=view.findViewById(R.id.cardviewpainting);
        carpentry=view.findViewById(R.id.cardviewcarpentry);
        powerman=view.findViewById(R.id.cardviewpowerman);
        landscaping=view.findViewById(R.id.cardviewland);

        electrical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),CategoryActivity.class);
                intent.putExtra("Category","Electrical");
                startActivity(intent);
            }
        });
        plubming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),CategoryActivity.class);
                intent.putExtra("Category","Plumbing");
                startActivity(intent);
            }
        });
        painting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),CategoryActivity.class);
                intent.putExtra("Category","Painter");
                startActivity(intent);
            }
        });
        carpentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(),CategoryActivity.class);
                intent.putExtra("Category","Carpentry");
                startActivity(intent);
            }
        });
        powerman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),CategoryActivity.class);
                intent.putExtra("Category","Man Power");
                startActivity(intent);
            }
        });
        landscaping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),CategoryActivity.class);
                intent.putExtra("Category","Land Scaping");
                startActivity(intent);
            }
        });

        return view;
    }
}