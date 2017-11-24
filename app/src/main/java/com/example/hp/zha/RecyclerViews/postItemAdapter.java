package com.example.hp.zha.RecyclerViews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hp.zha.Adapters.photoAdapter;
import com.example.hp.zha.Adapters.postAdapter;
import com.example.hp.zha.R;

import java.util.ArrayList;

/**
 * Created by HP on 11/17/2017.
 */

public class postItemAdapter extends RecyclerView.Adapter<postItemAdapter.ViewHolder> {
    private int listItemLayout1;
    private ArrayList<postAdapter> list1;

    static Context context1;
    public postItemAdapter(int layout,ArrayList<postAdapter> list)
    {
        listItemLayout1=layout;
        list1=list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout1, parent, false);
        final ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        TextView item=holder.t1;
        TextView item2=holder.t2;
        TextView item3=holder.t3;
        ImageView item4=holder.imageView;
        System.out.println("bowbow"+list1.get(position).getTitl()+"  "+ list1.get(position).getDate());
       item.setText(list1.get(position).getTitl());
       item2.setText(list1.get(position).getDate());
       item3.setText(list1.get(position).getTime());
        Glide.with(context1).load(list1.get(position).getUrl()).asBitmap().into(item4);
    }

    @Override
    public int getItemCount()
    {
        return list1==null?0:list1.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener

    {
        public TextView t1,t2,t3;
        public ImageView imageView;
        public ViewHolder(View itemView)
        {
            super(itemView);
            t1=(TextView)itemView.findViewById(R.id.textView12);
            t2=(TextView)itemView.findViewById(R.id.date);
            t3=(TextView)itemView.findViewById(R.id.time);
            imageView=(ImageView)itemView.findViewById(R.id.imageView4);
            context1=itemView.getContext();

        }

        @Override
        public void onClick(View view) {

        }
    }
}
