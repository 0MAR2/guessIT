package com.example.guessit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TwoPlayersMode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_players_mode);
        Button button=findViewById(R.id.home);
        Button submit=findViewById(R.id.submit);
        EditText palyer1=findViewById(R.id.p1name);
        EditText palyer2=findViewById(R.id.p2name);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p1name=palyer1.getText().toString();
                if (p1name.trim().isEmpty()) {
                    p1name="Player 1";
                }
                String p2name=palyer2.getText().toString();
                if (p2name.trim().isEmpty()) {
                    p2name="Player 2";
                }
                Intent intent = new Intent(TwoPlayersMode.this, TwoPlayersModePage2.class);
                intent.putExtra("p1name",p1name);
                intent.putExtra("p2name",p2name);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TwoPlayersMode.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}