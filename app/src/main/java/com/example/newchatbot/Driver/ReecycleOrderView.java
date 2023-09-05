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
import com.example.newchatbot.model.Order;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;

public class ReecycleOrderView extends RecyclerView.Adapter<ReecycleOrderView.MyViewHolder>{
    private WeakReference<Context> mContext;
    private List<Order> itemsList;
    Context context;
    String dataID;
    String dID;
    //    private ClickListener clickListener;
    public ReecycleOrderView(List<Order> mItemList, String ID, String driverId){
        this.itemsList=mItemList;
        dataID=ID;
        dID=driverId;
    }
    @NonNull
    @Override
    public ReecycleOrderView.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_orders,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ReecycleOrderView.MyViewHolder holder, int position) {
        final Order item = itemsList.get(position);
        holder.Date.setText(item.getFoodName());
        holder.time.setText(String.valueOf(item.getDevliberyTime()));
        holder.chrger.setText(item.getTotalPrice());
        Picasso.get().load(item.getImage()).into(holder.Total);
//        if(item.getStatus().equals("0")){
//            holder.Number.setText(String.valueOf("Pending"));
//        }
//        else{
//            holder.Number.setText(String.valueOf("Occupied"));
//        }
        holder.Number.setText(item.getAddress());
        holder.addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task<Void> reference = FirebaseDatabase.getInstance().getReference("order").child(item.getOrderID()).child("driverID").setValue(dID);
                Log.d("datacheck1",dataID);
                Log.d("datacheck1",dID);
                itemsList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,itemsList.size());
//                Intent intent = new Intent(context, DriverAdmin.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView Number,time,Date,chrger;
        public ImageView Total;
        public Button addData;
        private LinearLayout itemLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            chrger=itemView.findViewById(R.id.tvpriceItem);
            Number = itemView.findViewById(R.id.tvNumber);
            time = itemView.findViewById(R.id.tvtime);
            Date=itemView.findViewById(R.id.tvDate);
            Total=itemView.findViewById(R.id.driverRecyclerImg);
            addData=itemView.findViewById(R.id.orderdeveliverbutton);
            itemLayout =  itemView.findViewById(R.id.itemLayout);
        }
    }
}
