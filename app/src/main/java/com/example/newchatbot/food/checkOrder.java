package com.example.newchatbot.food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newchatbot.Chat.chatActivity;
import com.example.newchatbot.Driver.ReecycleOrderView;
import com.example.newchatbot.R;
import com.example.newchatbot.foodDetails;
import com.example.newchatbot.model.Order;
import com.example.newchatbot.model.food;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.paperdb.Paper;

public class checkOrder extends AppCompatActivity {
    String FoodID="";
    TextView food_name, food_description, hay, hay1, sizes;
    EditText etServing;
    ImageView food_image;
    AppCompatButton btnAddOrder, btnRemoveOrder;
    AppCompatSpinner sizeSpinner, orderSpinner;

    String foodId = "";
    String selectSize = "";
    String selectOrderType = "";

    //ArrayList<FoodOrder> items = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference foodRef, cartRef, orders, sizeRef;
    food currentFood;
    String ORderID;
    Order d=new Order();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_order);


        Bundle extras = getIntent().getExtras();
        ORderID=extras.getString("IDdata");


        food_name = findViewById(R.id.food_name);
        hay = findViewById(R.id.hay);
        hay1 = findViewById(R.id.hay1);
//        sizeSpinner = findViewById(R.id.size_spin);
//        orderSpinner = findViewById(R.id.order_spin);
        food_image = findViewById(R.id.image_food);
        food_description = findViewById(R.id.food_description);
        btnAddOrder = findViewById(R.id.btnAddOrder);
        btnAddOrder.setVisibility(View.GONE);
        //btnRemoveOrder = findViewById(R.id.btnRemoveOrder);
        sizes = findViewById(R.id.size1);

//        btnAddOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(foodDetails.this, chatActivity.class);
//                i.putExtra("FoodID",FoodID);
//                startActivity(i);
//            }
//        });
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("order");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    Log.d("datachecking","String.valueOf(itemsList.size())");
                    if(datas.getKey().equals(ORderID)){

                        d.setFoodName(datas.child("FoodName").getValue().toString());
                        food_name.setText(d.getFoodName());
                        d.setQuantity(datas.child("Quantity").getValue().toString());
                        sizes.setText("Quantity :"+d.getQuantity());
                        d.setSize(datas.child("size").getValue().toString());
                        d.setExtra(datas.child("extra").getValue().toString());
                        food_description.setText("Extra :"+d.getExtra());
                        d.setUseremail(datas.child("Useremail").getValue().toString());
                        d.setResturantemail(datas.child("Resturantemail").getValue().toString());
                        d.setStatus(datas.child("status").getValue().toString());
                        d.setDevliberyTime(datas.child("delivery Time").getValue().toString());
                        d.setPrice(datas.child("price").getValue().toString());
                        d.setTotalPrice(datas.child("TotalPrice").getValue().toString());
                        d.setDeliveryCharges(datas.child("deliveryCharges").getValue().toString());
                        d.setOrderID(datas.getKey().toString());
                        d.setImage(datas.child("FoodImage").getValue().toString());
                        d.setAddress(datas.child("address").getValue().toString());
                        hay.setText("Address :"+d.getAddress());
                        Picasso.get().load(d.getImage()).into(food_image);
                    }
                    //Log.d("datahere","here");
                    //Toast.makeText(checkDrivers.this, familyname, Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }
}