package com.example.newchatbot.Driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.newchatbot.Driver.fragments.UpdateOrders;
import com.example.newchatbot.Driver.fragments.listOfOrders;
import com.example.newchatbot.LoginActivity;
import com.example.newchatbot.Prevalent.Prevalent;
import com.example.newchatbot.R;
import com.example.newchatbot.User.OrdersList;
import com.example.newchatbot.User.foodFragment;
import com.example.newchatbot.model.Order;
import com.example.newchatbot.model.driver;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import io.paperdb.Paper;

public class DriverAdmin extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;
    private DatabaseReference mDatabase;
    private ArrayList<Order> itemsList=new ArrayList<>();
    String emaildata="";
    String ID="";
    String driverID="";
    private BottomNavigationView BNV;
    listOfOrders MPF=new listOfOrders();
    UpdateOrders OMF=new UpdateOrders();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_admin);

        BNV=findViewById(R.id.bottomNavigationViewnew);
        getSupportFragmentManager().beginTransaction().replace(R.id.scrollView3,MPF).commit();

        BNV.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.person:
                    listOfOrders MPF2=new listOfOrders();

                    getSupportFragmentManager().beginTransaction().replace(R.id.scrollView3,MPF2).commit();
                    return true;
                case R.id.home:
                    UpdateOrders OMF2=new UpdateOrders();
                    getSupportFragmentManager().beginTransaction().replace(R.id.scrollView3,OMF2).commit();
                    return true;
                case R.id.logout:
                    FirebaseAuth.getInstance().signOut();
                    Paper.book().delete(Prevalent.userPervalentEmail);
                    Paper.book().delete(Prevalent.userPervalentPassword);
                    Paper.book().delete(Prevalent.userPervalentType);
                    SharedPreferences settings = getSharedPreferences("session", Context.MODE_PRIVATE);
                    settings.edit().clear().commit();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return true;
            }
            return false;
        });


    }
}