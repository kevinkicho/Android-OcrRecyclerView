package com.example.kevin.myocrapp120517;

import android.content.Context;
import android.location.Geocoder;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by kevin on 12/3/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    List<ListItem> listItems;
    private Context context;

    public MyAdapter(List<ListItem> listItems, Context context){
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ListItem listItem = listItems.get(position);

        holder.textViewTitle.setText(listItem.getTitle());

        /*Picasso.with(context)
            .load(listItem.getImageUrl())
            .into(holder.imageView);*/


        holder.linearLayout.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View view){
               //Toast.makeText(context, "You clicked " + listItem.getDist(), Toast.LENGTH_LONG).show();

           }
        });

        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(context, "LongClick called on position " + position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewTitle;
        //public ImageView imageView;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            //imageView = (ImageView) itemView.findViewById(R.id.imageView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }
}
