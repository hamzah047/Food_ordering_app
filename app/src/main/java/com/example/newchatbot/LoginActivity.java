package com.example.newchatbot;;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
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

import static android.util.Patterns.EMAIL_ADDRESS;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView registertext,forgotpassword,contactadmin;
    private Button login;
    private EditText email,password;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private CheckBox checkRemeberMe;


    private String userType,userStatus;
    boolean checked=false;
    TextView radioGroup;
    FirebaseFirestore db;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registertext=(TextView)findViewById(R.id.register_text_donor_login);
        registertext.setOnClickListener(this);

        login=(Button)findViewById(R.id.sign_in_donor_login);
        login.setOnClickListener(this);

        email=(EditText) findViewById(R.id.email_donor_login);
        password=(EditText) findViewById(R.id.password_donor_login);

        forgotpassword=(TextView)findViewById(R.id.forgot_password_donor_login);
        forgotpassword.setOnClickListener(this);

        mAuth=FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(this);

        radioGroup=findViewById(R.id.rg);

        db= FirebaseFirestore.getInstance();

        checkRemeberMe=(CheckBox)findViewById(R.id.checkbox_remeber_me);
        Paper.init(this);

        builder=new AlertDialog.Builder(this);

        contactadmin=findViewById(R.id.contat_admin);
        contactadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder.setTitle("Contact Admin").setMessage("Contact On: UzairKhan@gmail.com")
                        .setCancelable(true)
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();
            }
        });
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
            case R.id.driver:
                if (checked)
                    userType="driver";
                userStatus="Pending";
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_text_donor_login:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
                break;
            case R.id.sign_in_donor_login:
                userlogin();
                break;
            case R.id.forgot_password_donor_login:
               // startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
                break;

        }
    }

    private void userlogin() {
        String emailstr=email.getText().toString().trim();
        String passstr=password.getText().toString().trim();

        if(emailstr.isEmpty()){
            email.setError("Email is Required!");
            email.requestFocus();
            return;
        }
        if(!EMAIL_ADDRESS.matcher(emailstr).matches()){
            email.setError("Enter Valid Email!");
            email.requestFocus();
            return;
        }
        if(passstr.isEmpty()){
            password.setError("Enter Password!");
            password.requestFocus();
            return;
        }
        if(passstr.length()<6){
            password.setError("Password Length Must Be 6!");
            password.requestFocus();
            return;
        }
        if(checked==false){
            radioGroup.setError("Select User User Type!");
            radioGroup.requestFocus();
            return;
        }
        if(checkRemeberMe.isChecked()){
            Paper.book().write(Prevalent.userPervalentEmail,emailstr);
            Paper.book().write(Prevalent.userPervalentPassword,passstr);
            Paper.book().write(Prevalent.userPervalentType,userType);

        }
        progressDialog.setTitle("Checking Credentials Please Wait!");
        progressDialog.show();

        if(userType.equalsIgnoreCase("Customer")){
            mAuth.signInWithEmailAndPassword(emailstr,passstr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser User= FirebaseAuth.getInstance().getCurrentUser();
                        if(User.isEmailVerified()) {
                            startActivity(new Intent(LoginActivity.this, UserDashboard.class));
                            Paper.book().write("email",emailstr);
                            Paper.book().write("type","Customer");
                            SharedPreferences sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();

                            // below two lines will put values for
                            editor.putString("type","Customer");
                            editor.apply();
                            finish();
                            progressDialog.dismiss();
                        }else{
                            User.sendEmailVerification();
                            //Toast.makeText(LoginActivity.this,"Please Verify Your Email",Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }else{
                        //Toast.makeText(LoginActivity.this,"Check Your Credentials",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            });
        }
        if(userType.equalsIgnoreCase("driver")){
            db.collection("Restaurant").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot doc:task.getResult()){
                                if(doc.getString("Email").equalsIgnoreCase(emailstr) &&
                                        doc.getString("Password").equalsIgnoreCase(passstr) &&
                                        doc.getString("Status").equalsIgnoreCase("Approved")){
                                    progressDialog.dismiss();
                                    SharedPreferences sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString("type","driver");
                                    Paper.book().write("email",emailstr);
                                    Paper.book().write("type","driver");
                                    editor.apply();
                                    //Paper.book().write("userEmail",doc.getString("Resturantemail"));
                                    Paper.book().write("ID",doc.getString("ID"));
                                    startActivity(new Intent(LoginActivity.this, DriverAdmin.class));
                                    finish();
                                }else if (doc.getString("Email").equalsIgnoreCase(emailstr) &&
                                        doc.getString("Password").equalsIgnoreCase(passstr) &&
                                        doc.getString("Status").equalsIgnoreCase("Pending")){
                                    progressDialog.dismiss();
                                    Paper.book().destroy();
                                    //Toast.makeText(LoginActivity.this,"Please Wait Admin Didn't Approved Your Account!",Toast.LENGTH_SHORT).show();
                                }else{
                                    progressDialog.dismiss();
                                    //Toast.makeText(LoginActivity.this,"User Is Not Registered!",Toast.LENGTH_SHORT).show();

                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            //Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else if(userType.equalsIgnoreCase("Restaurant")){
            db.collection("Restaurant").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot doc:task.getResult()){
                                if(doc.getString("Email").equalsIgnoreCase(emailstr) &&
                                        doc.getString("Password").equalsIgnoreCase(passstr) &&
                                        doc.getString("Status").equalsIgnoreCase("Approved")){
                                    progressDialog.dismiss();
                                    //
                                    SharedPreferences sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedpreferences.edit();

                                    // below two lines will put values for
                                    editor.putString("type","Restaurant");
                                    editor.apply();
                                    //password = sharedpreferences.getString("PASSWORD_KEY", null);

                                    Paper.book().write("email",emailstr);
                                    Paper.book().write("type","driver");
                                    //Paper.book().write("ID",doc.getString("Restaurant"));
                                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                                    finish();
                                }else if (doc.getString("Email").equalsIgnoreCase(emailstr) &&
                                        doc.getString("Password").equalsIgnoreCase(passstr) &&
                                        doc.getString("Status").equalsIgnoreCase("Pending")){
                                    progressDialog.dismiss();
                                    Paper.book().destroy();
                                    //Toast.makeText(LoginActivity.this,"Please Wait Admin Didn't Approved Your Account!",Toast.LENGTH_SHORT).show();
                                }else{
                                    progressDialog.dismiss();
                                    //Toast.makeText(LoginActivity.this,"User Is Not Registered!",Toast.LENGTH_SHORT).show();

                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}