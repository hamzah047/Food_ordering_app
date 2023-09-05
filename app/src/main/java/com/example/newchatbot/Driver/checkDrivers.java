package com.example.newchatbot.Driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.newchatbot.R;
import com.example.newchatbot.model.driver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import io.paperdb.Paper;

public class checkDrivers extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;
    private DatabaseReference mDatabase;
    private ArrayList<driver> itemsList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_drivers);

//        getSupportActionBar().setTitle("Drivers");
       // Toast.makeText(checkDrivers.this, Paper.book().read("ID"), Toast.LENGTH_SHORT).show();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("driver");

        reference.orderByChild("Resturantemail").equalTo(Paper.book().read("email")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    driver d=new driver();
                    d.setName(datas.child("Name").getValue().toString());
                    d.setDesc(datas.child("Desc").getValue().toString());
                    d.setImage(datas.child("image").getValue(String.class));
                    d.setStatus(datas.child("status").getValue(String.class));
                    String datasave=datas.child("image").getValue(String.class);
                    d.setPhone(datas.child("Phone").getValue().toString());
                    d.setResturantID(datas.child("ResturantID").getValue().toString());
                    d.setDriverID(datas.getKey());
                    itemsList.add(d);
                    //Log.d("datahere","here");
                    //Toast.makeText(checkDrivers.this, familyname, Toast.LENGTH_SHORT).show();
                }
                Log.d("datachecking",String.valueOf(itemsList.size()));
                Log.d("datafound here",String.valueOf(itemsList.size()));
                RecyclerViewDriver recyclerviewItemAdapter = new RecyclerViewDriver(itemsList);

                RecyclerView recyclerView=findViewById(R.id.recycleView);
                recyclerView.setHasFixedSize(true);
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