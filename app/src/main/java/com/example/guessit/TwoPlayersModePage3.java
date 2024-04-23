package com.example.guessit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.content.Context;
import android.view.ViewGroup;
import android.view.Gravity;
import android.graphics.drawable.ColorDrawable;

public class TwoPlayersModePage3 extends AppCompatActivity {
    int rowcount=0;
    int nbtriesleft=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_players_mode_page3);
        TextView test=findViewById(R.id.welcoming);
        String name2 = getIntent().getStringExtra("p2name");
        String name = getIntent().getStringExtra("p1name");
        String word = getIntent().getStringExtra("word");
        test.setText("Hello "+name2+"!");
        TextView triesleft=findViewById(R.id.tries);
        Button button=findViewById(R.id.home);
        Button submit=findViewById(R.id.submit);
        EditText Try=findViewById(R.id.input);
        triesleft.setText("You have "+nbtriesleft+" tries left!");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TwoPlayersModePage3.this, MainActivity.class);
                startActivity(intent);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wordtry=Try.getText().toString();
                wordtry=wordtry.toLowerCase();
                if (rowcount<6) {
                    int colcount = 0;
                    if (wordtry.length() == 5) {
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

                        } while (colcount < 5);
                    } else {
                        Toast.makeText(TwoPlayersModePage3.this, "Only 5 letters words are accepted", Toast.LENGTH_SHORT).show();
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
                        congrats.setText("Congratulation "+name2+"!");
                        theword.setText("You've guessed "+name+"'s word:"+word);
                        exit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(TwoPlayersModePage3.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                        playagian.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(TwoPlayersModePage3.this, TwoPlayersModePage2.class);
                                intent.putExtra("p1name",name);
                                intent.putExtra("p2name",name2);
                                startActivity(intent);
                            }
                        });
                        seetries.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                            }
                        });
                    }
                }else {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.winnerpopup, null);
                    PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                }

            }
        });
    }
}