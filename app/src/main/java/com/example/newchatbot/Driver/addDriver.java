package com.example.newchatbot.Driver;

import static android.util.Patterns.EMAIL_ADDRESS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.newchatbot.LoginActivity;
import com.example.newchatbot.Prevalent.Prevalent;
import com.example.newchatbot.R;
import com.example.newchatbot.RegisterActivity;
import com.example.newchatbot.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class addDriver extends AppCompatActivity {
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseStorage mStorage;
    FirebaseFirestore db;
    CircleImageView imageButton;
    EditText NameEditText,DesEditText,PriceEditText,DriverUSer,DriverPass;
    Button AddData;

    private static final int Gallery_Code=1;
    Uri imageUrl=null;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_driver);

        db = FirebaseFirestore.getInstance();
        DriverPass=findViewById(R.id.etDriverPassword);
        DriverUSer=findViewById(R.id.etDriverUserName);
        imageButton=findViewById(R.id.imageButtonnew);
        NameEditText=findViewById(R.id.dishedittext);
        DesEditText=findViewById(R.id.descedittext);
        PriceEditText=findViewById(R.id.priceedittext);
        AddData=findViewById(R.id.adddata);

        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference().child("driver");
        mStorage=FirebaseStorage.getInstance();

        progressDialog=new ProgressDialog(this);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,Gallery_Code);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==Gallery_Code &&resultCode==RESULT_OK){
            imageUrl=data.getData();
            imageButton.setImageURI(imageUrl);
        }

        AddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameStr=NameEditText.getText().toString().trim();
                String descStr=DesEditText.getText().toString().trim();
                String priceStr=PriceEditText.getText().toString().trim();

                if(!(nameStr.isEmpty() && !descStr.isEmpty() && !priceStr.isEmpty() && imageUrl!=null)){
                    progressDialog.setTitle("Uploading");
                    progressDialog.show();

                    StorageReference filepath=mStorage.getReference().child("imagePost").child(imageUrl.getLastPathSegment());
                    filepath.putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> downloadUrl=taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    String t=task.getResult().toString();
                                    DatabaseReference newPost=mRef.push();
                                    newPost.child("Name").setValue(nameStr);
                                    newPost.child("Desc").setValue(descStr);
                                    newPost.child("Phone").setValue(priceStr);
                                    newPost.child("driverEmail").setValue(DriverUSer.getText().toString().trim());
                                    newPost.child("Resturantemail").setValue(Paper.book().read("email"));
                                    newPost.child("status").setValue("0");
                                    Log.d("realte",newPost.getKey());
                                    newPost.child("ResturantID").setValue(newPost.getKey());

                                    newPost.child("image").setValue(task.getResult().toString());
                                    registerUser();
                                    progressDialog.dismiss();


                                }
                            });
                        }
                    });
                }
            }
        });
    }
    private void registerUser() {
        String email=DriverUSer.getText().toString().trim();
        String pass=DriverPass.getText().toString().trim();

        if(email.isEmpty()){
            DriverUSer.setError("Email is Required!");
            DriverUSer.requestFocus();
            return;
        }
        if(pass.isEmpty()){
            DriverPass.setError("Email is Required!");
            DriverPass.requestFocus();
            return;
        }
        if(!EMAIL_ADDRESS.matcher(email).matches()){
            DriverUSer.setError("Enter Valid Email!");
            DriverUSer.requestFocus();
            return;
        }
        if(pass.isEmpty()){
            DriverPass.setError("Enter Password!");
            DriverPass.requestFocus();
            return;
        }
        if(pass.length()<6){
            DriverPass.setError("Password Length Must Be 6!");
            DriverPass.requestFocus();
            return;
        }

else{
            progressDialog.setTitle("Registering User Please Wait!");
            progressDialog.show();



                String id = UUID.randomUUID().toString().trim();
                Map<String,Object> doc=new HashMap<>();
                doc.put("Name",NameEditText.getText().toString());
                doc.put("Email",email);
                doc.put("Password",pass);
                doc.put("User Type","driver");
                doc.put("Status","Approved");
                doc.put("ID",id);
                db.collection("Restaurant").document(id).set(doc)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                //Toast.makeText(  addDriver.this,"Restaurant Added Successfully",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                            //    Toast.makeText(  RegisterActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
            }


    }
}