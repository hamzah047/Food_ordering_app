package com.example.newchatbot;

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

import com.example.newchatbot.Driver.ReecycleOrderView;
import com.example.newchatbot.model.Order;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;

public class RecylerbankOrderList extends RecyclerView.Adapter<RecylerbankOrderList.MyViewHolder>{
    private WeakReference<Context> mContext;
    private List<Order> itemsList;
    Context context;
    String dataID;
    String dID;
    //    private ClickListener clickListener;
    public RecylerbankOrderList(List<Order> mItemList, String ID, String driverId,Context con){
        this.itemsList=mItemList;
        dataID=ID;
        dID=driverId;
        context=con;
    }
    @NonNull
    @Override
    public RecylerbankOrderList.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_bank_list,parent,false);
        return new RecylerbankOrderList.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecylerbankOrderList.MyViewHolder holder, int position) {
        final Order item = itemsList.get(position);
        holder.Date.setText(item.getFoodName());
        holder.time.setText(String.valueOf(item.getTotalPrice()));
        Picasso.get().load(item.getImage()).into(holder.Total);
        if(item.getStatus().equals("0")){
            holder.Number.setText(String.valueOf("Pending"));
        }
        else{
            holder.Number.setText(String.valueOf("Occupied"));
        }

        holder.addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            Intent i=new Intent(context,payment.class);
            Log.d("datafoudn",item.getOrderID());
            i.putExtra("OrderID", item.getOrderID());
            context.startActivity(i);

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
        public Button addData;
        private LinearLayout itemLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            Number = itemView.findViewById(R.id.tvNumber);
            time = itemView.findViewById(R.id.tvtime);
            Date=itemView.findViewById(R.id.tvDate);
            Total=itemView.findViewById(R.id.driverRecyclerImg);
            addData=itemView.findViewById(R.id.orderdeveliverbutton);
            itemLayout =  itemView.findViewById(R.id.itemLayout);
        }
    }
}
