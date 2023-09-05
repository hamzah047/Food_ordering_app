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
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.newchatbot.Driver.UserDashboard;
import com.example.newchatbot.Driver.updateOrderRecycler;
import com.example.newchatbot.model.Order;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class payment extends AppCompatActivity {
    EditText userName,BankName,IBAn,Amount;
    Button pay;
    String ORderID="";
    Order d=new Order();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment2);


        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this,R.style.SheetDialog);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_data);
        Button btn=bottomSheetDialog.findViewById(R.id.searchCancel);
        RatingBar rat=bottomSheetDialog.findViewById(R.id.ratingBar);

        pay=findViewById(R.id.btnpayPaymnet);
        userName=findViewById(R.id.etPaymentName);
        BankName=findViewById(R.id.etPaymentBankName);
        IBAn=findViewById(R.id.etPaymentIbanName);
        Amount=findViewById(R.id.etPaymentAmount);


       //


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("reached","reached here");
                Float ratingvalue=rat.getRating();
                Task<Void> reference1 = FirebaseDatabase.getInstance().getReference("order").child(ORderID).child("rating").setValue(String.valueOf(ratingvalue));
                bottomSheetDialog.dismiss();
                Intent i=new Intent(payment.this, UserDashboard.class);
                startActivity(i);
            }
        });

        Bundle extras = getIntent().getExtras();
        ORderID=extras.getString("OrderID");

       //
        //
        // Toast.makeText(payment.this, ORderID, Toast.LENGTH_SHORT).show();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("order");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                   if(datas.getKey().equals(ORderID)){
                       Log.d("dataherefound",datas.child("FoodName").getValue().toString());
                       d.setFoodName(datas.child("FoodName").getValue().toString());
                       d.setQuantity(datas.child("Quantity").getValue().toString());
                       d.setSize(datas.child("size").getValue().toString());
                       d.setUseremail(datas.child("Useremail").getValue().toString());
                       d.setResturantemail(datas.child("Resturantemail").getValue().toString());
                       d.setStatus(datas.child("status").getValue().toString());
                       d.setDevliberyTime(datas.child("delivery Time").getValue().toString());
                       d.setPrice(datas.child("price").getValue().toString());
                       d.setTotalPrice(datas.child("TotalPrice").getValue().toString());
                       d.setDeliveryCharges(datas.child("deliveryCharges").getValue().toString());
                       d.setOrderID(datas.getKey().toString());
                       d.setImage(datas.child("FoodImage").getValue().toString());
                       Amount.setHint("Amount Should be "+d.getTotalPrice());
                   }

                    //Log.d("datahere","here");
                    //Toast.makeText(checkDrivers.this, familyname, Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });






        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=userName.getText().toString();
                String bank=BankName.getText().toString();
                String IB=IBAn.getText().toString();
                String amon=Amount.getText().toString();
                if(name.isEmpty()){
                    userName.setError("User Name should'nt be Null");
                }
                else if(bank.isEmpty()){
                    BankName.setError("Bank Name should'nt be Null");
                }
                else if(IB.isEmpty()){
                    IBAn.setError("IBAN Number should'nt be Null");
                }
                else if(amon.isEmpty()){
                    Amount.setError("Amount should'nt be Null");
                }
                else{
                    if(amon.equals(d.getPrice())){
                        Task<Void> reference = FirebaseDatabase.getInstance().getReference("order").child(ORderID).child("status").setValue("1");

                        bottomSheetDialog.show();
                    }
                    else{
                        Amount.setError("Amount Should be Equal to "+d.getPrice());
                    }

                }
            }
        });

    }
}