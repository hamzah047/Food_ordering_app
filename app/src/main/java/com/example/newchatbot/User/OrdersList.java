package com.example.newchatbot.User;

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
import android.widget.Toast;

import com.example.newchatbot.Driver.DriverAdmin;
import com.example.newchatbot.Driver.ReecycleOrderView;
import com.example.newchatbot.R;
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


public class OrdersList extends Fragment {
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
                    String rat=datas.child("rating").getValue().toString();
                    String rat1=datas.child("payment").getValue().toString().toLowerCase();
                    String bank="bank";
                    Boolean res=rat1.contains(bank.toLowerCase());
                    Log.d("dataname",res.toString());
                    if(res==false){
                        Log.d("dataname","res.toString()");
                        if(rat.equals("0")){
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
                            d.setImage(datas.child("FoodImage").getValue().toString());
                            d.setRating(datas.child("rating").getValue().toString());
                            d.setAddress(datas.child("address").getValue().toString());
                            d.setRiderId(datas.child("driverID").getValue().toString());
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
                RecycleOrderList recyclerviewItemAdapter = new RecycleOrderList(itemsList,ID,driverID,getContext());

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



        // Toast.makeText(DriverAdmin.this, Paper.book().read("ID"), Toast.LENGTH_SHORT).show();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_orders_list, container, false);
    }
}