package com.example.guessit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;
    Dbhelper localDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        localDB=new Dbhelper(this);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String usernam = sharedPreferences.getString("name", "");
        Boolean checkinsertdata=localDB.insertuserdata(usernam,0);
        if (user==null){
            Intent intent= new Intent(getApplicationContext(),loging.class);
            startActivity(intent);
            finish();
        }

        Button button=findViewById(R.id.b2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,TwoPlayersMode.class);
                startActivity(intent);
                finish();
            }
        });
        Button button1=findViewById(R.id.b1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SinglePlayerMode.class);
                startActivity(intent);
                finish();
            }
        });
        Button button2=findViewById(R.id.logout);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(getApplicationContext(),loging.class);
                startActivity(intent);
                finish();
            }
        });
    }
}