package com.example.guessit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class SinglePlayerMode extends AppCompatActivity {
    int rowcount=0;
    int nbtriesleft=5;
    TextView test;
    DatabaseReference reference;
    FirebaseDatabase db;
    String word;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_players_mode_page3);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        test=findViewById(R.id.welcoming);
        test.setText("Hello "+username+"!");
        TextView triesleft=findViewById(R.id.tries);
        Button button=findViewById(R.id.home);
        Button submit=findViewById(R.id.submit);
        EditText Try=findViewById(R.id.input);
        Random random = new Random();
        int randomNumber = random.nextInt(663) + 1;
        String id = String.valueOf(randomNumber);
        readData(id, new DataCallback() {
            @Override
            public void onDataLoaded(String a) {
                word=a.toLowerCase();
            }
        });

        triesleft.setText("You have "+nbtriesleft+" tries left!");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SinglePlayerMode.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wordtry=Try.getText().toString();
                wordtry=wordtry.toLowerCase();
                String pattern = "^[a-z]+$";
                if (rowcount<6) {
                    int colcount = 0;
                    if (wordtry.length() == 5) {
                        if (wordtry.matches(pattern)) {
                            rowcount = rowcount + 1;
                            nbtriesleft=nbtriesleft-1;
                            triesleft.setText("You have "+nbtriesleft+" tries left!");
                            String test = word;
                            do{
                                colcount++;
                                if ((test.charAt(colcount - 1)) == (wordtry.charAt(colcount - 1))) {
                                    String caseID = "case" + String.valueOf(rowcount) + String.valueOf(colcount);
                                    int resourceId = getResources().getIdentifier(caseID, "id", getPackageName());
                                    TextView caseToChange = findViewById(resourceId);
                                    char a = wordtry.charAt(colcount - 1);
                                    caseToChange.setText(String.valueOf(a));
                                    caseToChange.setTextColor(Color.WHITE);
                                    caseToChange.setBackground(getResources().getDrawable(R.drawable.greencase));
                                    char chararray[] = test.toCharArray();
                                    chararray[colcount - 1] = '#';
                                    String nstr = new String(chararray);
                                    test = nstr;
                                }

                            }while (colcount < 5);
                            colcount = 0;
                            do {
                                colcount++;
                                String caseID = "case" + String.valueOf(rowcount) + String.valueOf(colcount);
                                int resourceId = getResources().getIdentifier(caseID, "id", getPackageName());
                                TextView caseToChange = findViewById(resourceId);
                                if (caseToChange.getText().length()!=1){
                                    if (test.indexOf(wordtry.charAt(colcount - 1)) != -1) {
                                        char a = wordtry.charAt(colcount - 1);
                                        caseToChange.setText(String.valueOf(a));
                                        caseToChange.setTextColor(Color.WHITE);
                                        caseToChange.setBackground(getResources().getDrawable(R.drawable.yellowcase));
                                        char chararray[] = test.toCharArray();
                                        chararray[test.indexOf(wordtry.charAt(colcount - 1))] = '#';
                                        String nstr = new String(chararray);
                                        test = nstr;
                                    } else {
                                        char a = wordtry.charAt(colcount - 1);
                                        caseToChange.setText(String.valueOf(a));
                                        caseToChange.setTextColor(Color.WHITE);
                                        caseToChange.setBackground(getResources().getDrawable(R.drawable.red));
                                    }
                                }

                                Try.setText("");
                                Try.requestFocus();

                            } while (colcount < 5);}
                        else{
                            Toast.makeText(SinglePlayerMode.this, "The word can't contains special characters, spaces, or numbers.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SinglePlayerMode.this, "Only 5 letters words are accepted", Toast.LENGTH_SHORT).show();
                    }
                    if(word.equals(wordtry)){
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View popupView = inflater.inflate(R.layout.winnerpopup, null);
                        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        popupWindow.setFocusable(true);
                        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                        TextView congrats=popupView.findViewById(R.id.winnercongrats);
                        TextView theword=popupView.findViewById(R.id.theword);
                        Button playagian=popupView.findViewById(R.id.playagainb);
                        Button seetries=popupView.findViewById(R.id.dismissb);
                        Button exit=popupView.findViewById(R.id.homeb);
                        Try.setEnabled(false);
                        submit.setEnabled(false);
                        congrats.setText(username+"!");
                        theword.setText("You've guessed the word:"+word);
                        exit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(SinglePlayerMode.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                        playagian.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(SinglePlayerMode.this, SinglePlayerMode.class);
                                startActivity(intent);
                            }
                        });
                        seetries.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                            }
                        });
                    }else if (rowcount==5){
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View popupView = inflater.inflate(R.layout.loserpopup, null);
                        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        popupWindow.setFocusable(true);
                        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                        TextView congrats=popupView.findViewById(R.id.winnercongrats);
                        TextView theword=popupView.findViewById(R.id.theword);
                        Button playagian=popupView.findViewById(R.id.playagainb);
                        Button seetries=popupView.findViewById(R.id.dismissb);
                        Button exit=popupView.findViewById(R.id.homeb);
                        Try.setEnabled(false);
                        submit.setEnabled(false);
                        congrats.setText(username+"!");
                        theword.setText("the word was: "+word);
                        exit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(SinglePlayerMode.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        playagian.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(SinglePlayerMode.this, TwoPlayersModePage2.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        seetries.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                            }
                        });
                    }
                }

            }
        });
    }
    public interface DataCallback {
        void onDataLoaded(String word);
    }
    public void readData(String id, DataCallback callback) {
        reference = FirebaseDatabase.getInstance().getReference("Words");
        reference.child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    String word = String.valueOf(dataSnapshot.getValue());
                    callback.onDataLoaded(word);
                } else {
                    callback.onDataLoaded(null);
                }
            }
        });
    }


}

