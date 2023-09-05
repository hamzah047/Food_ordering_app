package com.example.newchatbot.Driver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.newchatbot.LoginActivity;
import com.example.newchatbot.Prevalent.Prevalent;
import com.example.newchatbot.R;
import com.example.newchatbot.User.OrdersList;
import com.example.newchatbot.User.foodFragment;
import com.example.newchatbot.bankOrderList;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;

public class UserDashboard extends AppCompatActivity {
    private BottomNavigationView BNV;
    foodFragment MPF=new foodFragment();
    OrdersList OMF=new OrdersList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);


        BNV=findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.scrollView2,MPF).commit();

        BNV.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.person:
                    foodFragment MPF2=new foodFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.scrollView2,MPF2).commit();
                    return true;
                case R.id.home:
                    OrdersList OMF2=new OrdersList();
                    getSupportFragmentManager().beginTransaction().replace(R.id.scrollView2,OMF2).commit();
                    return true;
                case R.id.bank:
                    bankOrderList OMF3=new bankOrderList();
                    getSupportFragmentManager().beginTransaction().replace(R.id.scrollView2,OMF3).commit();
                    return true;
                case R.id.logout:
                    FirebaseAuth.getInstance().signOut();
                    Paper.book().delete(Prevalent.userPervalentEmail);
                    Paper.book().delete(Prevalent.userPervalentPassword);
                    Paper.book().delete(Prevalent.userPervalentType);

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    SharedPreferences settings = getSharedPreferences("session", Context.MODE_PRIVATE);
                    settings.edit().clear().commit();
                    return true;

            }
            return false;
        });

    }
}