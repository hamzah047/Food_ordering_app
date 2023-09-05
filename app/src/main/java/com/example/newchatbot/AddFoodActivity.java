package com.example.newchatbot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.newchatbot.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import io.paperdb.Paper;

public class AddFoodActivity extends AppCompatActivity {

    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseStorage mStorage;
    ImageButton imageButton;
    EditText NameEditText,DesEditText,PriceEditText;
    Button AddData;

    private static final int Gallery_Code=1;
    Uri imageUrl=null;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);




        imageButton=findViewById(R.id.imageButton);
        NameEditText=findViewById(R.id.dishedittext);
        DesEditText=findViewById(R.id.descedittext);
        PriceEditText=findViewById(R.id.priceedittext);
        AddData=findViewById(R.id.adddata);

        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference().child("dishes");
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
                                    newPost.child("Price").setValue(priceStr);
                                    newPost.child("email").setValue(Paper.book().read("email"));
                                    newPost.child("image").setValue(task.getResult().toString());
                                    progressDialog.dismiss();

                                }
                            });
                        }
                    });
                }
            }
        });
    }
}