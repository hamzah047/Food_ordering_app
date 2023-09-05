package com.example.newchatbot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    Context context;
    List<Model> studentModelList;

    public CustomAdapter(Context context, List<Model> modelList) {
        this.context = context;
        this.studentModelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.design_row,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Model studentModel= studentModelList.get(position);
        holder.tvName.setText(studentModel.getName());
        holder.tvDesc.setText(studentModel.getDesc());
        holder.tvPrice.setText(studentModel.getPrice());

        String imageUri=null;
        imageUri=studentModel.getImage();
        Picasso.get().load(imageUri).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return studentModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvName,tvDesc,tvPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image_recyclerview);
            tvName=itemView.findViewById(R.id.name_recyclerview);
            tvDesc=itemView.findViewById(R.id.desc_recyclerview);
            tvPrice=itemView.findViewById(R.id.price_recyclerview);
        }
    }
}
