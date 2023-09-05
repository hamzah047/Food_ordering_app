package com.example.newchatbot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;


import com.example.newchatbot.Driver.DriverAdmin;
import com.example.newchatbot.Driver.UserDashboard;
import com.example.newchatbot.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import io.paperdb.Paper;

public class SplashScreenActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    //Intent intent=new Intent(SplashScreenActivity.this, LoginActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Paper.init(this);
        mAuth=FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();

        //CheckRemeberMe();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CheckRemeberMe();
            }
        },3000);
    }

    private void CheckRemeberMe() {
        String email=Paper.book().read(Prevalent.userPervalentEmail);
        String pass=Paper.book().read(Prevalent.userPervalentPassword);
        SharedPreferences sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);

        String type= sharedpreferences.getString("type","");
        //Toast.makeText(this, type+" asd", Toast.LENGTH_SHORT).show();
        if(type.equals("Customer")){
            startActivity(new Intent(SplashScreenActivity.this, UserDashboard.class));

        }
        else if(type.equals("driver")){
            startActivity(new Intent(SplashScreenActivity.this, DriverAdmin.class));
        }
        else if(type.equals("Restaurant")){
            startActivity(new Intent(SplashScreenActivity.this, DashboardActivity.class));
        }
        else{
            startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
        }
    }
}