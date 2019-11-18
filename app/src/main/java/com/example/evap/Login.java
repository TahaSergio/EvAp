package com.example.evap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText emailId, password;
    Button btnsignin;
    TextView tvsignup;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseAuth= FirebaseAuth.getInstance();
        emailId= findViewById(R.id.editText);
        password= findViewById(R.id.editText2);
        btnsignin= findViewById(R.id.button);
        tvsignup=findViewById(R.id.textView);
        mAuthStateListener= new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser= mFirebaseAuth.getCurrentUser();
                if( mFirebaseUser != null){
                    Toast.makeText(Login.this,"You are logged in", Toast.LENGTH_SHORT).show();
                    Intent i= new Intent(Login.this,Home.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(Login.this,"Please login", Toast.LENGTH_SHORT).show();
                }
            }
        };
        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= emailId.getText().toString();
                String pass= password.getText().toString();
                if(email.isEmpty()){
                    emailId.setError("Please enter your email Id");
                    emailId.requestFocus();
                }
                else if(pass.isEmpty()){
                    password.setError("Please enter your password");
                    password.requestFocus();
                }
                else if(email.isEmpty() && pass.isEmpty()){
                    Toast.makeText(Login.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                }
                else if(!(email.isEmpty() && pass.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task){
                            if(!task.isSuccessful()){
                                Toast.makeText(Login.this, "Login Error! Please try again. ", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Intent intToHome= new Intent(Login.this, Home.class);
                                startActivity(intToHome);
                            }

                        }
                    });
                }
                else {
                    Toast.makeText(Login.this, "Error occurred!. ", Toast.LENGTH_SHORT).show();
                }
            }


        });
        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intsignup=new Intent(Login.this, MainActivity.class);
                startActivity(intsignup);
            }


        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}

