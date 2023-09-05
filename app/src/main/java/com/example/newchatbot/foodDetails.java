package com.example.newchatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newchatbot.Chat.chatActivity;
import com.example.newchatbot.model.food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class foodDetails extends AppCompatActivity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        Bundle extras = getIntent().getExtras();
        FoodID= extras.getString("dataID");

        food_name = findViewById(R.id.food_name);
        hay = findViewById(R.id.hay);
        hay1 = findViewById(R.id.hay1);
//        sizeSpinner = findViewById(R.id.size_spin);
//        orderSpinner = findViewById(R.id.order_spin);
        food_image = findViewById(R.id.image_food);
        food_description = findViewById(R.id.food_description);
        btnAddOrder = findViewById(R.id.btnAddOrder);
        //btnRemoveOrder = findViewById(R.id.btnRemoveOrder);
        sizes = findViewById(R.id.size1);

        btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(foodDetails.this, chatActivity.class);
                i.putExtra("FoodID",FoodID);
                startActivity(i);
            }
        });
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("dishes");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    if(datas.getKey().equals(FoodID)){
                        food d=new food();
                        d.setDesc(datas.child("Desc").getValue().toString());
                        d.setName(datas.child("Name").getValue().toString());
                        d.setPrice(datas.child("Price").getValue().toString());
                        d.setEmail(datas.child("email").getValue().toString());
                        d.setImage(datas.child("image").getValue(String.class));
                        Log.d("datahere",datas.getKey());
                        Picasso.get().load(d.getImage()).into(food_image);
                        food_name.setText(d.getName());
                        food_description.setText(d.getDesc());
                        sizes.setText("One Size :"+d.getPrice());
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
}