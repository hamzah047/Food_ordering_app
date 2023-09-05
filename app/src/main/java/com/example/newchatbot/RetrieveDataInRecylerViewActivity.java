package com.example.newchatbot;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newchatbot.Prevalent.Prevalent;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class RetrieveDataInRecylerViewActivity extends AppCompatActivity {

    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseStorage mStorage;

    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    List<Model> studentMdlList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_data_in_recyler_view);

        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference().child("dishes");
        mStorage=FirebaseStorage.getInstance();
        recyclerView=findViewById(R.id.recyclerview_id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        studentMdlList =new ArrayList<Model>();
        customAdapter=new CustomAdapter(RetrieveDataInRecylerViewActivity.this, studentMdlList);
        recyclerView.setAdapter(customAdapter);


         mRef.addChildEventListener(new ChildEventListener() {
             @Override
             public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                 Model studentModel=snapshot.getValue(Model.class);
                 //Toast.makeText(RetrieveDataInRecylerViewActivity.this, Paper.book().read("email").toString(), Toast.LENGTH_SHORT).show();
                 //Log.d("emeil data",Paper.book().read("email").toString());
//                 if(studentModel.getEmail().equalsIgnoreCase(Paper.book().read("email"))){
//                     studentMdlList.add(studentModel);
//                     customAdapter.notifyDataSetChanged();
//                 }




             }

             @Override
             public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

             }

             @Override
             public void onChildRemoved(@NonNull DataSnapshot snapshot) {

             }

             @Override
             public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });
    }
}