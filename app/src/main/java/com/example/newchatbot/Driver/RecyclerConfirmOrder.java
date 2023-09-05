package com.example.newchatbot.Driver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newchatbot.R;
import com.example.newchatbot.food.checkOrder;
import com.example.newchatbot.model.Order;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;

public class RecyclerConfirmOrder extends RecyclerView.Adapter<RecyclerConfirmOrder.MyViewHolder>{
    private WeakReference<Context> mContext;
    private List<Order> itemsList;
    Context context;
    String dataID;
    String dID;
    //    private ClickListener clickListener;
    public RecyclerConfirmOrder(List<Order> mItemList, String ID, String driverId,Context con){
        this.itemsList=mItemList;
        dataID=ID;
        dID=driverId;
        context=con;
    }
    @NonNull
    @Override
    public RecyclerConfirmOrder.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.confirm_order_items,parent,false);
        return new RecyclerConfirmOrder.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerConfirmOrder.MyViewHolder holder, int position) {
        final Order item = itemsList.get(position);
        holder.Date.setText(item.getFoodName());
        holder.time.setText(String.valueOf(item.getTotalPrice()));
        Picasso.get().load(item.getImage()).into(holder.Total);
        if(item.getStatus().equals("0")){
            holder.Number.setText(String.valueOf("Pending"));
            holder.ButtonLayout.setVisibility(View.VISIBLE);
        }
        else{

            holder.Number.setText(String.valueOf("Rating "+item.getRating()));
        }
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference()
                        .child("order").child(item.getOrderID()).removeValue();

                itemsList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,itemsList.size());


            }
        });
        holder.prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, checkOrder.class);
                i.putExtra("IDdata",item.getOrderID());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
//        holder.addData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Task<Void> reference = FirebaseDatabase.getInstance().getReference("order").child(item.getOrderID()).child("status").setValue("1");
//                Log.d("datacheck1",dataID);
//                Log.d("datacheck1",dID);
//                itemsList.remove(position);
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position,itemsList.size());
////                Intent intent = new Intent(context, DriverAdmin.class);
////                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                context.startActivity(intent);
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView Number,time,Date;
        public ImageView Total;
        public Button addData;
        public Button del,prev;
        private LinearLayout itemLayout,ButtonLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            Number = itemView.findViewById(R.id.tvNumber);
            time = itemView.findViewById(R.id.tvtime);
            Date=itemView.findViewById(R.id.tvDate);
            Total=itemView.findViewById(R.id.driverRecyclerImg);
            itemLayout =  itemView.findViewById(R.id.itemLayout);
            del=itemView.findViewById(R.id.foodDelDelete);
            prev=itemView.findViewById(R.id.foodDelprev);
            ButtonLayout=itemView.findViewById(R.id.btnOrderLayout);

        }
    }
}
