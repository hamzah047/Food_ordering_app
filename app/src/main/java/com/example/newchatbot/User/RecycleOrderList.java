package com.example.newchatbot.User;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newchatbot.Driver.ReecycleOrderView;
import com.example.newchatbot.R;
import com.example.newchatbot.model.Order;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RecycleOrderList extends RecyclerView.Adapter<RecycleOrderList.MyViewHolder>{
    private WeakReference<Context> mContext;
    private List<Order> itemsList;
    Context context;
    String dataID;
    String dID;
    //    private ClickListener clickListener;
    public RecycleOrderList(List<Order> mItemList, String ID, String driverId,Context con){
        this.itemsList=mItemList;
        dataID=ID;
        dID=driverId;
        context=con;
    }
    @NonNull
    @Override
    public RecycleOrderList.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_order,parent,false);
        return new RecycleOrderList.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecycleOrderList.MyViewHolder holder, int position) {
        final Order item = itemsList.get(position);
        holder.Date.setText(item.getFoodName());
        holder.time.setText(String.valueOf(item.getDevliberyTime()));
        holder.chrges.setText(item.getTotalPrice());
        FirebaseDatabase.getInstance().getReference()
                .child("driver").child(item.getRiderId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot appleSnapshot: snapshot.getChildren()) {
                            holder.rider.setText("Driver "+snapshot.child("Name").getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
       // holder.rider.setText(item.getRiderId());
        Picasso.get().load(item.getImage()).into(holder.Total);

        if(item.getStatus().equals("0")){
            holder.cancel.setVisibility(View.VISIBLE);
            holder.Number.setText(String.valueOf("Pending"));
        }
        else{
            holder.addData.setVisibility(View.VISIBLE);
            holder.Number.setText(String.valueOf("Delivered"));
        }

        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference()
                        .child("order").child(item.getOrderID()).removeValue();

                itemsList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,itemsList.size());
            }
        });
        holder.addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Task<Void> reference = FirebaseDatabase.getInstance().getReference("order").child(dataID).child("driverID").setValue(dID);
                Task<Void> reference1 = FirebaseDatabase.getInstance().getReference("order").child(dataID).child("status").setValue("1");
                Log.d("datacheck1",dataID);
                Log.d("datacheck1",dID);

                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context,R.style.SheetDialog);
                bottomSheetDialog.setContentView(R.layout.bottom_sheet_data);
                Button btn=bottomSheetDialog.findViewById(R.id.searchCancel);
                RatingBar rat=bottomSheetDialog.findViewById(R.id.ratingBar);
                rat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Log.d("datarached","sdf");
                    }
                });
               //


                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("reached","reached here");
                        Float ratingvalue=rat.getRating();
                        Task<Void> reference1 = FirebaseDatabase.getInstance().getReference("order").child(dataID).child("rating").setValue(String.valueOf(ratingvalue));
                        itemsList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,itemsList.size());
                        bottomSheetDialog.dismiss();
                    }
                });



                bottomSheetDialog.show();
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
    class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView Number,time,Date,chrges,rider;
        public ImageView Total;
        public Button addData,cancel;

        private LinearLayout itemLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            rider=itemView.findViewById(R.id.tvRiderInfo);
            chrges=itemView.findViewById(R.id.tvchrges);
            Number = itemView.findViewById(R.id.tvNumber);
            time = itemView.findViewById(R.id.tvtime);
            Date=itemView.findViewById(R.id.tvDate);
            cancel=itemView.findViewById(R.id.ordercancelbutton);
            Total=itemView.findViewById(R.id.driverRecyclerImg);
            addData=itemView.findViewById(R.id.orderdeveliverbutton);
            itemLayout =  itemView.findViewById(R.id.itemLayout);
        }
    }
}

