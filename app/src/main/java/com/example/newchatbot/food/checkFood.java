package com.example.newchatbot.food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.newchatbot.Driver.RecyclerViewDriver;
import com.example.newchatbot.Driver.checkDrivers;
import com.example.newchatbot.R;
import com.example.newchatbot.model.driver;
import com.example.newchatbot.model.food;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import io.paperdb.Paper;

public class checkFood extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;
    private DatabaseReference mDatabase;
    private ArrayList<food> itemsList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_food);

        //getSupportActionBar().setTitle("Dishes");
       // Toast.makeText(checkFood.this, Paper.book().read("ID"), Toast.LENGTH_SHORT).show();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("dishes");

        reference.orderByChild("email").equalTo(Paper.book().read("email")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    food d=new food();
                    d.setDesc(datas.child("Desc").getValue().toString());
                    d.setName(datas.child("Name").getValue().toString());
                    d.setPrice(datas.child("Price").getValue().toString());
                    d.setEmail(datas.child("email").getValue().toString());
                    d.setImage(datas.child("image").getValue(String.class));
                    d.setFoodID(datas.getKey());
                    itemsList.add(d);
                    //Log.d("datahere","here");
                    //Toast.makeText(checkDrivers.this, familyname, Toast.LENGTH_SHORT).show();
                }
                Log.d("datachecking",String.valueOf(itemsList.size()));
                Log.d("datafound here",String.valueOf(itemsList.size()));
                RecyclerViewFood recyclerviewItemAdapter = new RecyclerViewFood(itemsList,getApplicationContext());

                RecyclerView recyclerView=findViewById(R.id.recycleView2);
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