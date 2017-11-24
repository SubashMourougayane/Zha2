package com.example.hp.zha.RecyclerViews;

import android.content.Context;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hp.zha.Adapters.photoAdapter;
import com.example.hp.zha.R;

import java.util.ArrayList;

/**
 * Created by HP on 11/16/2017.
 */

public class photoItemAdapter  extends RecyclerView.Adapter<photoItemAdapter.ViewHolder>
{
    private int listItemLayout;
    private ArrayList<photoAdapter> list1;
    static Context context1;
    public photoItemAdapter(int layout,ArrayList<photoAdapter> list)
    {
        this.list1=list;
        this.listItemLayout=layout;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
        final ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {  TextView t1=holder.tv;
       ImageView t2=holder.imageView;
       t1.setText(list1.get(position).getTitle());
        Glide.with(context1).load(list1.get(position).getSurl()).asBitmap().into(t2);

    }

    @Override
    public int getItemCount() {
        return list1==null?0:list1.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {   public TextView tv;
        public ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            tv=(TextView)itemView.findViewById(R.id.textView);
            imageView=(ImageView)itemView.findViewById(R.id.imageView);
            context1=itemView.getContext();
        }

        @Override
        public void onClick(View view) {

        }
    }
}
