package com.example.newchatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;


import com.example.newchatbot.Prevalent.Prevalent;
import com.example.newchatbot.food.RecyclerViewFood;
import com.example.newchatbot.food.checkFood;
import com.example.newchatbot.model.food;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.index.qual.LengthOf;

import java.util.ArrayList;

import io.paperdb.Paper;

public class CustomerDashboardActivity extends AppCompatActivity {
    Button button;
    GridView coursesGV;
    ArrayList<food> dataModalArrayList=new ArrayList<>();
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);
        coursesGV = findViewById(R.id.idGVCourses);


//        button=findViewById(R.id.logoutbtn);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                Paper.book().delete(Prevalent.userPervalentEmail);
//                Paper.book().delete(Prevalent.userPervalentPassword);
//                Paper.book().delete(Prevalent.userPervalentType);
//                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            }
//        });


//        getSupportActionBar().setTitle("Dishes");
        //Toast.makeText(CustomerDashboardActivity.this, Paper.book().read("ID"), Toast.LENGTH_SHORT).show();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("dishes");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    food d=new food();
                    d.setDesc(datas.child("Desc").getValue().toString());
                    d.setName(datas.child("Name").getValue().toString());
                    d.setPrice(datas.child("Price").getValue().toString());
                    d.setEmail(datas.child("email").getValue().toString());
                    d.setImage(datas.child("image").getValue(String.class));
                    Log.d("datahere",datas.getKey());
                    d.setFoodID(datas.getKey());
                    dataModalArrayList.add(d);
                    //Log.d("datahere","here");
                    //Toast.makeText(checkDrivers.this, familyname, Toast.LENGTH_SHORT).show();
                }
                Log.d("datachecking",String.valueOf(dataModalArrayList.size()));
                Log.d("datafound here",String.valueOf(dataModalArrayList.size()));
                UserFoodAdapter adapter = new UserFoodAdapter(CustomerDashboardActivity.this, dataModalArrayList);

                // after passing this array list
                // to our adapter class we are setting
                // our adapter to our list view.
                coursesGV.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }
}