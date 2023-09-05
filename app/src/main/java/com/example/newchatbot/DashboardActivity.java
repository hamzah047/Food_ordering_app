package com.example.newchatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.newchatbot.Driver.addDriver;
import com.example.newchatbot.Driver.checkDrivers;
import com.example.newchatbot.Prevalent.Prevalent;
import com.example.newchatbot.food.checkFood;
import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;

public class DashboardActivity extends AppCompatActivity {

    Button button;
    private FirebaseAuth FirebseAuth;
    private CardView cardViewAddDetails, cardViewDietPlan,addDriver,showDrivers,Orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard2);




        showDrivers=findViewById(R.id.CVCheckDrivers);

        FirebseAuth=FirebaseAuth.getInstance();
        addDriver=findViewById(R.id.cardviewAddDetailsDriver);
        button=findViewById(R.id.logoutbtn);
        //Toast.makeText(DashboardActivity.this, Paper.book().read("ID"), Toast.LENGTH_SHORT).show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });

        cardViewAddDetails=findViewById(R.id.cardviewAddDetails);
        cardViewAddDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddFoodActivity.class));
            }
        });

        addDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), addDriver.class));

            }
        });
        cardViewDietPlan=findViewById(R.id.cardviewdietplan);
        cardViewDietPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), checkFood.class));
            }
        });

        showDrivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), checkDrivers.class));
            }
        });

        Orders=findViewById(R.id.CVCheckOrder);
        Orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ResturantOrderList.class));
            }
        });
    }
}