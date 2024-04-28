package com.example.guessit;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class TwoPlayersModePage2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_players_mode_page2);
        TextView test=findViewById(R.id.welcoming);
        String name = getIntent().getStringExtra("p1name");
        test.setText("Hello "+name+"!");
        TextView test2=findViewById(R.id.description);
        String name2 = getIntent().getStringExtra("p2name");
        test2.setText("Write here the word you want "+name2+" to guess!");
        EditText wordinput=findViewById(R.id.wordinput);
        Button submit=findViewById(R.id.submit);
        Button button=findViewById(R.id.home);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word=wordinput.getText().toString();
                word=word.toLowerCase();
                String pattern = "^[a-z]+$";
                if (word.length()==5){
                    if (word.matches(pattern)) {
                        Intent intent=new Intent(TwoPlayersModePage2.this,TwoPlayersModePage3.class);
                        intent.putExtra("word",word);
                        intent.putExtra("p1name",name);
                        intent.putExtra("p2name",name2);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(TwoPlayersModePage2.this, "The word can't contains special characters, spaces, or numbers.", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(TwoPlayersModePage2.this, "Please enter a 5 letters word", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TwoPlayersModePage2.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}