package com.example.newchatbot;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newchatbot.Driver.ReecycleOrderView;
import com.example.newchatbot.User.RecycleOrderList;
import com.example.newchatbot.model.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import io.paperdb.Paper;


public class bankOrderList extends Fragment {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;
    private DatabaseReference mDatabase;
    private ArrayList<Order> itemsList=new ArrayList<>();
    String emaildata="";
    String ID="";
    String driverID="";
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("order");

        ref.orderByChild("Useremail").equalTo(Paper.book().read("email")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    String rat=datas.child("payment").getValue().toString().toLowerCase();
                    Log.d("dataname",datas.child("FoodName").getValue().toString());
                    String bank="bank";
                    Boolean res=rat.contains(bank.toLowerCase());
                    String rat2=datas.child("status").getValue().toString();
                    if(rat2.equals("0")){
                        if(res){
                            Log.d("datachecking","String.valueOf(itemsList.size())");
                            Order d=new Order();
                            //Toast.makeText(DriverAdmin.this, "here", Toast.LENGTH_SHORT).show();
                            d.setFoodName(datas.child("FoodName").getValue().toString());
                            d.setQuantity(datas.child("Quantity").getValue().toString());
                            d.setSize(datas.child("size").getValue().toString());
                            d.setUseremail(datas.child("Useremail").getValue().toString());
                            d.setResturantemail(datas.child("Resturantemail").getValue().toString());
                            d.setStatus(datas.child("status").getValue().toString());
                            d.setDevliberyTime(datas.child("delivery Time").getValue().toString());
                            d.setPrice(datas.child("price").getValue().toString());
                            d.setTotalPrice(datas.child("TotalPrice").getValue().toString());
                            d.setDeliveryCharges(datas.child("deliveryCharges").getValue().toString());
                            d.setOrderID(datas.getKey().toString());
                            ID=datas.getKey().toString();
                            d.setImage(datas.child("FoodImage").getValue().toString());
                            itemsList.add(d);
                        }
                    }


                    //Log.d("datahere","here");
                    //Toast.makeText(checkDrivers.this, familyname, Toast.LENGTH_SHORT).show();
                }
                Log.d("datahere",ID);
                Log.d("datacheckingnoew",driverID);
                Log.d("datafound here",String.valueOf(itemsList.size()));
                RecylerbankOrderList recyclerviewItemAdapter = new RecylerbankOrderList(itemsList,ID,driverID,getContext());

                RecyclerView recyclerView=getView().findViewById(R.id.recycleView);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(recyclerviewItemAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bank_order_list, container, false);
    }
}