package com.example.newchatbot.Chat;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newchatbot.CustomerDashboardActivity;
import com.example.newchatbot.Driver.UserDashboard;
import com.example.newchatbot.Prevalent.Prevalent;
import com.example.newchatbot.R;
import com.example.newchatbot.model.ChatMessage;
import com.example.newchatbot.model.food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;

public class chatActivity extends AppCompatActivity {
    ActionBarDrawerToggle actionBarDrawerToggle;
    TextView txtFullName;
    private ListView listView;
    FirebaseDatabase mDatabase;
    ProgressDialog progressDialog;
    DatabaseReference mRef;
    private ImageButton buttonSend;
    private EditText etMessage;
    private ChatMessageAdapter adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference chatref;
    ArrayList<String> Questions=new ArrayList<String>();
    int userreponce=0;
    String FoodID="";
    food foodItem=new food();
    ArrayList<String> userREponce=new ArrayList<String>();
int setprice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        Bundle extras = getIntent().getExtras();
        FoodID= extras.getString("FoodID");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("dishes");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    if(datas.getKey().equals(FoodID)){

                        foodItem.setDesc(datas.child("Desc").getValue().toString());
                        foodItem.setName(datas.child("Name").getValue().toString());
                        foodItem.setPrice(datas.child("Price").getValue().toString());
                        foodItem.setEmail(datas.child("email").getValue().toString());
                        foodItem.setImage(datas.child("image").getValue(String.class));
                        setprice=Integer.parseInt(foodItem.getPrice());
                        Log.d("datahere",datas.getKey());
                        Log.d("dataprice",String.valueOf(setprice));
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

        progressDialog=new ProgressDialog(this);
        Questions.add("How are you?");
        Questions.add("What size you want to order \nfor(pizza :s, m,l, xl)\nfor(burger :s, m, l)\nfor(karahi:half, full, 1kg, 2kg...)");
        Questions.add("What quantity do you want to order");
        Questions.add("what thing do you want to add or neglect.");
        Questions.add("Are you sure to order these (Yes/No)");
        Questions.add("Your Ordered is placed thanks");
        Questions.add("You have not placed any Order thanks");
        Questions.add("How do you want to Pay (Cash on delivery/Bank)");
        Questions.add("Give us your address");


        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference().child("order");

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                sendBotMessage();
            }
        }, 1);
     //put here time 1000 milliseconds=1 second
//        Handler(Looper.getMainLooper()).postDelayed({
//
//                mSomeFUnction()
//        }, mDelay)
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            public void run() {
//
//            }
//        }, 1000);   //5 seconds

        buttonSend = (ImageButton) findViewById(R.id.btnSend);
        etMessage = (EditText) findViewById(R.id.et_message);
        adapter = new ChatMessageAdapter(this, new ArrayList<ChatMessage>());
        listView = (ListView) findViewById(R.id.message_view);

        listView.setAdapter(adapter);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg = etMessage.getText().toString();
                String delFood = "no";
                String foodName = "no";
                String price = "no";
                String quantity = "no";
                String orderType = "no";
                String size = "no";
                String serving = "no";
                if (msg.isEmpty()) {
                    etMessage.setError("Please type a text message");
                    etMessage.requestFocus();
                    return;
                } else {
                    if(userreponce==6){

                        if(!msg.toLowerCase().equals("attock".toLowerCase())){
                            //Toast.makeText(chatActivity.this,"data here",Toast.LENGTH_LONG).show();
                            ChatMessage chatMessage = new ChatMessage(true,msg);
                            adapter.add(chatMessage);
                            etMessage.setText("");
                            ChatMessage chatMessage2 = new ChatMessage( false,"Only Attock is allowed");
                            //chatref.child(Common.currentUser.getPhone()).push().setValue("bot: "+message);
                            adapter.add(chatMessage2);
                        }
                        else if(msg.toLowerCase().equals("attock".toLowerCase())){
                            //Toast.makeText(chatActivity.this,"data here2",Toast.LENGTH_LONG).show();
                            ChatMessage chatMessage = new ChatMessage(true,msg);
                            adapter.add(chatMessage);
                            etMessage.setText("");
                            listView.setSelection(adapter.getCount() - 1);

                            userreponce=userreponce+1;
                            userREponce.add(msg);
                            final Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //Do something after 100ms
                                    sendBotMessage();
                                }
                            }, 1000);

                        }
                    }
                    else{
                        ChatMessage chatMessage = new ChatMessage(true,msg);
                        adapter.add(chatMessage);
                        etMessage.setText("");
                        listView.setSelection(adapter.getCount() - 1);

                        userreponce=userreponce+1;
                        userREponce.add(msg);
                        final Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //Do something after 100ms
                                sendBotMessage();
                            }
                        }, 1000);


                    }

                    //callApi(msg, foodName, price, quantity, orderType, size, delFood, serving);

                }
            }
        });


    }
    private void sendBotMessage(){
        Log.d("datacame","here");
        if(userreponce==0){
            //Toast.makeText(chatActivity.this, "data1", Toast.LENGTH_SHORT).show();
            ChatMessage chatMessage = new ChatMessage( false,Questions.get(0));
            //chatref.child(Common.currentUser.getPhone()).push().setValue("bot: "+message);
            adapter.add(chatMessage);
        }
        else if(userreponce==1){
           // Toast.makeText(chatActivity.this, "data2", Toast.LENGTH_SHORT).show();
            ChatMessage chatMessage = new ChatMessage( false,Questions.get(1));
            //chatref.child(Common.currentUser.getPhone()).push().setValue("bot: "+message);
            adapter.add(chatMessage);
        }
        else if(userreponce==2){
            // Toast.makeText(chatActivity.this, "data2", Toast.LENGTH_SHORT).show();
            ChatMessage chatMessage = new ChatMessage( false,Questions.get(2));
            //chatref.child(Common.currentUser.getPhone()).push().setValue("bot: "+message);
            adapter.add(chatMessage);
        }
        else if(userreponce==3){
            // Toast.makeText(chatActivity.this, "data2", Toast.LENGTH_SHORT).show();
            ChatMessage chatMessage = new ChatMessage( false,Questions.get(3));
            //chatref.child(Common.currentUser.getPhone()).push().setValue("bot: "+message);
            adapter.add(chatMessage);
        }

        else if(userreponce==4){
            // Toast.makeText(chatActivity.this, "data2", Toast.LENGTH_SHORT).show();
            ChatMessage chatMessage = new ChatMessage( false,Questions.get(4));
            //chatref.child(Common.currentUser.getPhone()).push().setValue("bot: "+message);
            adapter.add(chatMessage);
        }
        else if(userreponce==5){
            // Toast.makeText(chatActivity.this, "data2", Toast.LENGTH_SHORT).show();
            if(userREponce.get(4).equals("no")||userREponce.get(4).equals("No")||userREponce.get(4).equals("NO")){
                ChatMessage chatMessage = new ChatMessage( false,Questions.get(6));
                //chatref.child(Common.currentUser.getPhone()).push().setValue("bot: "+message);
                progressDialog.setTitle("Order fail");
                progressDialog.show();
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        Intent i=new Intent(chatActivity.this, UserDashboard.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
                }, 1000);
            }
            else{

                ChatMessage chatMessage = new ChatMessage( false,Questions.get(7));
                //chatref.child(Common.currentUser.getPhone()).push().setValue("bot: "+message);
                adapter.add(chatMessage);

            }


        }
        else if(userreponce==6){
            ChatMessage chatMessage2 = new ChatMessage( false,Questions.get(8));
            //chatref.child(Common.currentUser.getPhone()).push().setValue("bot: "+message);
            adapter.add(chatMessage2);

        }
        else if(userreponce==7){
            ChatMessage chatMessage2 = new ChatMessage( false,Questions.get(5));
            //chatref.child(Common.currentUser.getPhone()).push().setValue("bot: "+message);
            adapter.add(chatMessage2);
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    orderdFoord();
                    //Do something after 100ms
                    Intent i=new Intent(chatActivity.this, UserDashboard.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            }, 1000);
        }


    }

    private void orderdFoord(){
        Log.d("dataprice",String.valueOf(setprice));
        int totalPrice=(setprice*Integer.parseInt(userREponce.get(2)))+100;
        DatabaseReference newPost=mRef.push();
        newPost.child("FoodName").setValue(foodItem.getName());
        newPost.child("FoodImage").setValue(foodItem.getImage());
        newPost.child("Quantity").setValue(userREponce.get(2));
        newPost.child("size").setValue(userREponce.get(1));
        newPost.child("Useremail").setValue(Paper.book().read("email"));
        newPost.child("Resturantemail").setValue(foodItem.getEmail());
        newPost.child("status").setValue("0");
        newPost.child("driverID").setValue("0");
        newPost.child("delivery Time").setValue("0");
        newPost.child("price").setValue(foodItem.getPrice());
        newPost.child("TotalPrice").setValue(String.valueOf(totalPrice));
        newPost.child("deliveryCharges").setValue("100");
        newPost.child("rating").setValue("0");
        newPost.child("payment").setValue(userREponce.get(5));
        newPost.child("extra").setValue(userREponce.get(3));
        newPost.child("address").setValue(userREponce.get(6));
        progressDialog.dismiss();
    };
}