package com.example.newchatbot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.newchatbot.food.RecyclerViewFood;
import com.example.newchatbot.model.food;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class EditDishes extends AppCompatActivity {
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseStorage mStorage;
    ImageButton imageButton;
    EditText NameEditText,DesEditText,PriceEditText;
    Button AddData;

    private static final int Gallery_Code=1;
    Uri imageUrl=null;
    food d=new food();
    ProgressDialog progressDialog;
String ORderID="";
    Task<Uri> downloadUrl;
    String getImageURl="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dishes);


        imageButton=findViewById(R.id.imageButton);
        NameEditText=findViewById(R.id.dishedittext);
        DesEditText=findViewById(R.id.descedittext);
        PriceEditText=findViewById(R.id.priceedittext);
        AddData=findViewById(R.id.adddata);

        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference().child("dishes");
        mStorage=FirebaseStorage.getInstance();


        Bundle extras = getIntent().getExtras();
        ORderID=extras.getString("DishesID");

        //Toast.makeText(payment.this, ORderID, Toast.LENGTH_SHORT).show();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("dishes");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    if(datas.getKey().equals(ORderID)){
                        d.setDesc(datas.child("Desc").getValue().toString());
                        DesEditText.setError(d.getDesc());
                        d.setName(datas.child("Name").getValue().toString());
                        NameEditText.setText(d.getName());
                        d.setPrice(datas.child("Price").getValue().toString());
                        PriceEditText.setText(d.getPrice());
                        d.setEmail(datas.child("email").getValue().toString());
                        d.setImage(datas.child("image").getValue(String.class));
                        getImageURl=d.getImage();
                        Picasso.get().load(d.getImage()).into(imageButton);
                        d.setFoodID(datas.getKey());

                    }

                    //itemsList.add(d);
                    //Log.d("datahere","here");
                    //Toast.makeText(checkDrivers.this, familyname, Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        AddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameStr=NameEditText.getText().toString().trim();
                String descStr=DesEditText.getText().toString().trim();
                String priceStr=PriceEditText.getText().toString().trim();

                if(!(nameStr.isEmpty() && !descStr.isEmpty() && !priceStr.isEmpty() && imageUrl!=null)){
                    progressDialog.setTitle("Updating");
                    progressDialog.show();
                    EditFooddat();
                }
            }
        });


        progressDialog=new ProgressDialog(this);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,Gallery_Code);
                ConvertImage();
            }
        });

    }
    private void ConvertImage(){

        StorageReference filepath=mStorage.getReference().child("imagePost").child(imageUrl.getLastPathSegment());
        filepath.putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                downloadUrl=taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        String t=task.getResult().toString();
                        getImageURl=t;
                    }
                });
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==Gallery_Code &&resultCode==RESULT_OK){
            imageUrl=data.getData();
            imageButton.setImageURI(imageUrl);
        }


    }
    private void  EditFooddat(){
        Task<Void> reference = FirebaseDatabase.getInstance().getReference("dishes").child(ORderID).child("Price").setValue(PriceEditText.getText().toString());
        Task<Void> reference1 = FirebaseDatabase.getInstance().getReference("dishes").child(ORderID).child("Name").setValue(NameEditText.getText().toString());
        Task<Void> reference2 = FirebaseDatabase.getInstance().getReference("dishes").child(ORderID).child("Desc").setValue(DesEditText.getText().toString());
        Task<Void> reference3 = FirebaseDatabase.getInstance().getReference("dishes").child(ORderID).child("image").setValue(getImageURl);
        progressDialog.hide();
        Intent i=new Intent(EditDishes.this,DashboardActivity.class);
        startActivity(i);
    }
}