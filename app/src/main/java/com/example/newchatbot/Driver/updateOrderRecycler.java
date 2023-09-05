package com.example.newchatbot.Driver;

import android.content.Context;
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

public class updateOrderRecycler extends RecyclerView.Adapter<updateOrderRecycler.MyViewHolder>{
    private WeakReference<Context> mContext;
    private List<Order> itemsList;
    Context context;
    String dataID;
    String dID;
    //    private ClickListener clickListener;
    public updateOrderRecycler(List<Order> mItemList, String ID, String driverId){
        this.itemsList=mItemList;
        dataID=ID;
        dID=driverId;
    }
    @NonNull
    @Override
    public updateOrderRecycler.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.update_order_item,parent,false);
        return new updateOrderRecycler.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull updateOrderRecycler.MyViewHolder holder, int position) {
        final Order item = itemsList.get(position);
        holder.Date.setText(item.getFoodName());
        holder.time.setText(String.valueOf(item.getDevliberyTime()));
        Picasso.get().load(item.getImage()).into(holder.Total);
        holder.charge.setText(item.getTotalPrice());
//        if(item.getStatus().equals("0")){
//            holder.Number.setText(String.valueOf("Pending"));
//            //holder.ButtonLayout.setVisibility(View.VISIBLE);
//        }
//        else{
//            holder.Number.setText(String.valueOf("Occupied"));
//        }
        holder.Number.setText(item.getAddress());
        holder.addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task<Void> reference = FirebaseDatabase.getInstance().getReference("order").child(item.getOrderID()).child("status").setValue("1");

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

        public TextView Number,time,Date,charge;
        public ImageView Total;
        public Button addData;
        public Button del,prev;
        private LinearLayout itemLayout,ButtonLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            charge=itemView.findViewById(R.id.tvpriceItem);
            Number = itemView.findViewById(R.id.tvNumber);
            time = itemView.findViewById(R.id.tvtime);
            Date=itemView.findViewById(R.id.tvDate);
            Total=itemView.findViewById(R.id.driverRecyclerImg);
            addData=itemView.findViewById(R.id.orderdeveliverbutton);
            itemLayout =  itemView.findViewById(R.id.itemLayout);
            ButtonLayout=itemView.findViewById(R.id.btnOrderLayout);

        }
    }
}

