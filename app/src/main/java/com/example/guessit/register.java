package com.example.guessit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {
    EditText mail,password;
    Button register;
    FirebaseAuth mAuth;
    TextView login;
    FirebaseDatabase db;
    DatabaseReference reference;
    EditText username;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent= new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mail=findViewById(R.id.regmail);
        username=findViewById(R.id.username);
        password=findViewById(R.id.regpass);
        register=findViewById(R.id.regbtn);
        mAuth=FirebaseAuth.getInstance();
        login=findViewById(R.id.haveacc);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),loging.class);
                startActivity(intent);
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=String.valueOf(mail.getText());
                String pass=String.valueOf(password.getText());
                String usernam=String.valueOf(username.getText());

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(register.this, "enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    Toast.makeText(register.this, "enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!email.isEmpty() && !pass.isEmpty() && !usernam.isEmpty()){
                    users user =new users(usernam,email,pass,0);
                    db=FirebaseDatabase.getInstance();
                    reference=db.getReference("Users");
                    reference.child(usernam).setValue(user);
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", usernam);
                    editor.apply();
                }
                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(register.this, "Account created.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent= new Intent(getApplicationContext(),loging.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}