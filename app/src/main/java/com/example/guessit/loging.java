package com.example.guessit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class loging extends AppCompatActivity {
    EditText mail,password;
    Button login;
    FirebaseAuth mAuth;
    TextView register;

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
        setContentView(R.layout.activity_loging);
        mail=findViewById(R.id.mail);
        password=findViewById(R.id.loginpass);
        login=findViewById(R.id.loginbtn);
        mAuth= FirebaseAuth.getInstance();
        register=findViewById(R.id.newhere);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),register.class);
                startActivity(intent);
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=String.valueOf(mail.getText());
                String pass=String.valueOf(password.getText());

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(loging.this, "enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    Toast.makeText(loging.this, "enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(loging.this, "Login successful.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    Toast.makeText(loging.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}