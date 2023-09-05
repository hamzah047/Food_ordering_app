package com.example.newchatbot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.util.Patterns.EMAIL_ADDRESS;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView logintext;
    private FirebaseAuth mAuth;
    private EditText fullname,email,password,cpassword;
    private Button register;
    private ProgressDialog progressDialog;
    private CheckBox checkBox;
    private String userType,userStatus;
    boolean checked=false;
    TextView radioGroup;

    FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        logintext=(TextView)findViewById(R.id.login_text_donor_register);
        logintext.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        fullname=(EditText) findViewById(R.id.fullname_donor_register);
        email=(EditText) findViewById(R.id.email_donor_register);
        password=(EditText) findViewById(R.id.password_donor_register);
        cpassword=(EditText) findViewById(R.id.confirm_password_donor_register);
        checkBox=(CheckBox)findViewById(R.id.checkbox_donor_register);

        register=(Button) findViewById(R.id.sign_up_donor_register);
        register.setOnClickListener(this);

        progressDialog=new ProgressDialog(this);

        radioGroup=findViewById(R.id.rg);

        db = FirebaseFirestore.getInstance();

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
         checked= ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.restaurants:
                if (checked)
                    userType="Restaurant";
                    userStatus="Pending";
                    break;
            case R.id.customers:
                if (checked)
                    userType="Customer";
                    userStatus="Pending";
                    break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_text_donor_register:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.sign_up_donor_register:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String name=fullname.getText().toString().trim();
        String useremail=email.getText().toString().trim();
        String userpassword=password.getText().toString().trim();
        String userconfirmpassword=cpassword.getText().toString().trim();

        String stat=userType;
        String pen=userStatus;

        if(name.isEmpty()){
            fullname.setError("Full Name is Required!");
            fullname.requestFocus();
            return;
        }
        if(useremail.isEmpty()){
            email.setError("Email is Required!");
            email.requestFocus();
            return;
        }
        if(!EMAIL_ADDRESS.matcher(useremail).matches()){
            email.setError("Enter Valid Email!");
            email.requestFocus();
            return;
        }
        if(userpassword.isEmpty()){
            password.setError("Enter Password!");
            password.requestFocus();
            return;
        }
        if(userpassword.length()<6){
            password.setError("Password Length Must Be 6!");
            password.requestFocus();
            return;
        }
        if(userconfirmpassword.isEmpty()){
            cpassword.setError("Enter Password Again!");
            cpassword.requestFocus();
            return;
        }else if(!userpassword.matches(userconfirmpassword)){
            cpassword.setError("Password Does Not Match!");
            cpassword.requestFocus();
            return;
        }
        if(checked==false){
            radioGroup.setError("Select User User Type!");
            radioGroup.requestFocus();
            return;
        }
        if(!checkBox.isChecked()){
            checkBox.setError("Click Here If You Agree!");
            checkBox.requestFocus();
            return;
        }
        progressDialog.setTitle("Registering User Please Wait!");
        progressDialog.show();

        if(userType.equalsIgnoreCase("Customer")){
            mAuth.createUserWithEmailAndPassword(useremail,userpassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                user u=new user(name,useremail,userpassword,userType,userStatus);

                                FirebaseDatabase.getInstance().getReference("user")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(RegisterActivity.this,"User Register Successfully!",Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                            finish();
                                            progressDialog.dismiss();
                                        }else{
                                            Toast.makeText(RegisterActivity.this,"Failed To Register!",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }else{
                                Toast.makeText(RegisterActivity.this,"Failed To Register!",Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
        }else if(userType.equalsIgnoreCase("Restaurant")){


            String id = UUID.randomUUID().toString().trim();
            Map<String,Object> doc=new HashMap<>();
            doc.put("Name",name);
            doc.put("Email",useremail);
            doc.put("Password",userpassword);
            doc.put("User Type",stat);
            doc.put("Status",pen);
            doc.put("ID",id);
            db.collection("Restaurant").document(id).set(doc)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            Toast.makeText(  RegisterActivity.this,"Restaurant Added Successfully",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(  RegisterActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }

    }


}