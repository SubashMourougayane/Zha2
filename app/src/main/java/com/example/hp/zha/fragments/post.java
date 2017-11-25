package com.example.hp.zha.fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hp.zha.Adapters.postAdap;
import com.example.hp.zha.Adapters.postAdapter;
import com.example.hp.zha.R;
import com.example.hp.zha.RecyclerViews.postItemAdapter;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by HP on 11/16/2017.
 */

public class post extends Fragment
{
    ImageView ig1,ig2;
    RecyclerView recyclerView;
    ArrayList<postAdapter> pa=new ArrayList<>();
    public post()
    {

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.post,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_post);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        ig1=(ImageView)view.findViewById(R.id.imageButton);
        ig2=(ImageView)view.findViewById(R.id.imageView2);
        Firebase.setAndroidContext(view.getContext());

//        pa.add(new postAdapter());
//        pa.add(new postAdapter());
//        postItemAdapter ptD=new postItemAdapter(R.layout.post_card,pa);
//        recyclerView.setAdapter(ptD);

        ig1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getActivity().finish();
            }
        });
        ig2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getFragmentManager().beginTransaction().add(R.id.frame_container,new create_post()).commit();
            }
        });
        return view;
    }

}
