package com.example.newchatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.newchatbot.Driver.RecyclerConfirmOrder;
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

public class ResturantOrderList extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;
    private DatabaseReference mDatabase;
    private ArrayList<Order> itemsList=new ArrayList<>();
    String emaildata="";
    String ID="";
    String driverID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant_order_list);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("order");

        ref.orderByChild("Resturantemail").equalTo(Paper.book().read("email")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    Log.d("datachecking","String.valueOf(itemsList.size())");
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
                    d.setImage(datas.child("FoodImage").getValue().toString());
                    d.setRating(datas.child("rating").getValue().toString());
                    d.setAddress(datas.child("address").getValue().toString());
                    ID=datas.getKey().toString();
                    itemsList.add(d);
                    //Log.d("datahere","here");
                    //Toast.makeText(checkDrivers.this, familyname, Toast.LENGTH_SHORT).show();
                }
                Log.d("datahere",ID);
                Log.d("datacheckingnoew",driverID);
                Log.d("datafound here",String.valueOf(itemsList.size()));
                RecyclerConfirmOrder recyclerviewItemAdapter = new RecyclerConfirmOrder(itemsList,ID,driverID,getApplicationContext());

                RecyclerView recyclerView=findViewById(R.id.recycleView);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
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