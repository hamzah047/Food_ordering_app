package com.example.newchatbot.food;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newchatbot.Driver.RecyclerViewDriver;
import com.example.newchatbot.EditDishes;
import com.example.newchatbot.R;
import com.example.newchatbot.model.driver;
import com.example.newchatbot.model.food;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;

public class RecyclerViewFood extends RecyclerView.Adapter<RecyclerViewFood.MyViewHolder>{
    private WeakReference<Context> mContext;
    private List<food> itemsList;
    Context context;
    //    private ClickListener clickListener;
    public RecyclerViewFood(List<food> mItemList,Context con){
        this.itemsList=mItemList;
        context=con;
    }

    @NonNull
    @Override
    public RecyclerViewFood.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_row,parent,false);
        return new RecyclerViewFood.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewFood.MyViewHolder holder, int position) {
        final food item = itemsList.get(position);
        holder.Date.setText(item.getName());
        holder.time.setText(String.valueOf(item.getDesc()));
        holder.Number.setText(item.getPrice());


        Uri myUri = Uri.parse(item.getImage());
        Bitmap myBitmap = BitmapFactory.decodeFile(myUri.getPath());
        Picasso.get().load(item.getImage()).into(holder.Total);
        holder.Total.setImageBitmap(myBitmap);
        // holder.Total.setBackgroundResource(myUri);
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               FirebaseDatabase.getInstance().getReference()
                        .child("dishes").child(item.getFoodID()).removeValue();

                itemsList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,itemsList.size());


            }
        });
        holder.prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, EditDishes.class);
                i.putExtra("DishesID",item.getFoodID());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Toast.makeText(v.getContext(),"String.valueOf(item.getUId())",Toast.LENGTH_SHORT).show();
                    //SharedPreferences sha = (SharedPreferences) mContext.get().getSharedPreferences("Invoice", Context.MODE_PRIVATE);
                    //Toast.makeText(v.getContext(),String.valueOf(item.getUId()),Toast.LENGTH_SHORT).show();
                    //SharedPreferences.Editor editor = sha.edit();
                    //editor.putString("Key", String.valueOf(item.getUId()));
                    //editor.apply();

//                    Intent i = new Intent(v.getContext(), Showinvoice.class);
//                    i.putExtra("Store","Print");
//                    i.putExtra("Filename",String.valueOf(item.getUId()));
//                    v.getContext().startActivity(i);
                }catch (Exception e){
                    Toast.makeText(v.getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
   public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView Number,time,Date;
        public ImageView Total;
        private LinearLayout itemLayout;
        public Button del,prev;

        public MyViewHolder(View itemView) {
            super(itemView);
            Number = itemView.findViewById(R.id.tvNumber);
            time = itemView.findViewById(R.id.tvtime);
            Date=itemView.findViewById(R.id.tvDate);
            Total=itemView.findViewById(R.id.driverRecyclerImg);
            del=itemView.findViewById(R.id.foodDelDelete);
            prev=itemView.findViewById(R.id.foodDelprev);
            itemLayout =  itemView.findViewById(R.id.itemLayout);
        }
    }
}
