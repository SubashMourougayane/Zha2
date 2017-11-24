package com.example.hp.zha.RecyclerViews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hp.zha.Adapters.wallAdapter;
import com.example.hp.zha.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by HP on 11/15/2017.
 */

public class wallItemAdapter  extends RecyclerView.Adapter<wallItemAdapter.ViewHolder>{
    private int listItemLayout;
    private ArrayList<wallAdapter> itemList;
    static Context context;
    public wallItemAdapter(int layout,ArrayList<wallAdapter> list){
        this.listItemLayout=layout;
        this.itemList=list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
        final ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {    TextView pt=holder.t1;
         TextView dt=holder.t2;
         TextView tt=holder.t3;
         ImageView image=holder.imageView;
         pt.setText(itemList.get(position).getTitl());
         dt.setText(itemList.get(position).getDate());
         tt.setText(itemList.get(position).getTime());
        Glide.with(context).load(itemList.get(position).getUrl()).asBitmap().into(image);

    }

    @Override
    public int getItemCount()
    {
        return itemList==null?0:itemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {   public TextView t1,t2,t3;
        public ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            t1=(TextView)itemView.findViewById(R.id.postdes);
            t2=(TextView)itemView.findViewById(R.id.walldate);
            t3=(TextView)itemView.findViewById(R.id.walltime);
            imageView=(ImageView)itemView.findViewById(R.id.wallimage);
            context=itemView.getContext();
        }

        @Override
        public void onClick(View view) {

        }
    }

}
