package com.example.newchatbot.Driver.fragments;

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

import com.example.newchatbot.Driver.RecyclerConfirmOrder;
import com.example.newchatbot.Driver.ReecycleOrderView;
import com.example.newchatbot.Driver.updateOrderRecycler;
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


public class UpdateOrders extends Fragment {

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
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("driver");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    Log.d("datafrom fiest",datas.child("Resturantemail").getValue().toString());
                    if(datas.child("driverEmail").getValue().equals(Paper.book().read("email"))){
                        emaildata=datas.child("Resturantemail").getValue().toString();
                        driverID=datas.getKey();
                        Log.d("dataprice",String.valueOf(emaildata));
                        Log.d("datachecking",emaildata);
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("order");

                        ref.orderByChild("Resturantemail").equalTo(emaildata).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot datas: dataSnapshot.getChildren()){
                                    Log.d("datachecking","String.valueOf(itemsList.size())");
                                    if(datas.child("driverID").getValue().toString().equals(driverID) && datas.child("status").getValue().toString().equals("0")){
                                        Order d=new Order();
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
                                        d.setAddress(datas.child("address").getValue().toString());
                                        d.setImage(datas.child("FoodImage").getValue().toString());
                                        ID=datas.getKey().toString();
                                        itemsList.add(d);
                                    }
                                    //Log.d("datahere","here");
                                    //Toast.makeText(checkDrivers.this, familyname, Toast.LENGTH_SHORT).show();
                                }
                                Log.d("datahere",ID);
                                Log.d("datacheckingnoew",driverID);
                                Log.d("datafound here",String.valueOf(itemsList.size()));
                                updateOrderRecycler recyclerviewItemAdapter = new updateOrderRecycler(itemsList,ID,driverID);

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

                }
//                Log.d("datachecking",String.valueOf(dataModalArrayList.size()));
//                Log.d("datafound here",String.valueOf(dataModalArrayList.size()));
//                UserFoodAdapter adapter = new UserFoodAdapter(CustomerDashboardActivity.this, dataModalArrayList);

                // after passing this array list
                // to our adapter class we are setting
                // our adapter to our list view.
                //     coursesGV.setAdapter(adapter);
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
        return inflater.inflate(R.layout.fragment_update_orders, container, false);
    }
}