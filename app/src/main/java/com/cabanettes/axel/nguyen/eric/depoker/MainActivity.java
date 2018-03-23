package com.cabanettes.axel.nguyen.eric.depoker;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.cabanettes.axel.nguyen.eric.depoker.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int player1 = -1;
    private int player2 = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean("dark_theme", false);

        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialisation des buttons et dés
        Button btn = (Button) findViewById(R.id.roll);
        ToggleButton die1 = (ToggleButton) findViewById(R.id.die1);
        die1.setEnabled(false);
        ToggleButton die2 = (ToggleButton) findViewById(R.id.die2);
        die2.setEnabled(false);
        ToggleButton die3 = (ToggleButton) findViewById(R.id.die3);
        die3.setEnabled(false);
        ToggleButton die4 = (ToggleButton) findViewById(R.id.die4);
        die4.setEnabled(false);
        ToggleButton die5 = (ToggleButton) findViewById(R.id.die5);
        die5.setEnabled(false);
        int turnNumber = preferences.getInt("turnNumber",3);
        btn.setOnClickListener(new RollBtnListener(turnNumber, die1, die2, die3, die4, die5));
    }

    public class RollBtnListener implements View.OnClickListener {
        private int[] dice;
        ToggleButton die1;
        ToggleButton die2;
        ToggleButton die3;
        ToggleButton die4;
        ToggleButton die5;
        TextView result;
        int turns;
        int maxturns;



        public RollBtnListener(int turnNumber, ToggleButton die1, ToggleButton die2, ToggleButton die3, ToggleButton die4, ToggleButton die5) {
            this.dice = new int[5];
            this.turns = 0;
            this.die1 = die1;
            this.die2 = die2;
            this.die3 = die3;
            this.die4 = die4;
            this.die5 = die5;
            maxturns = turnNumber;
        }

        @Override
        public void onClick(View view) {
            //Gestion du son
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.sound);
            mp.start();

            //Gestion du jeu
            result = (TextView) findViewById(R.id.result);
            for (int j = 1; j < 6; j++) {
                ToggleButton die = (ToggleButton) findViewById(getResources().getIdentifier("die" + j, "id", getPackageName()));
                if (die.isChecked() == false) {
                    Random r = new Random();
                    int v = r.nextInt(7 - 1) + 1;
                    this.dice[j - 1] = v;
                }
            }

            this.die1.setText("" + dice[0]);
            this.die2.setText("" + dice[1]);
            this.die3.setText("" + dice[2]);
            this.die4.setText("" + dice[3]);
            this.die5.setText("" + dice[4]);
            changeImage(die1);
            changeImage(die2);
            changeImage(die3);
            changeImage(die4);
            changeImage(die5);
            /*
            * Bundle extras = getIntent().getExtras();
            * if(extras != null){
            *   String s = (String) extras.get("numberTurn");
            *   maxturns =
            * }*/
            this.turns++;
            this.die1.setChecked(false);
            this.die2.setChecked(false);
            this.die3.setChecked(false);
            this.die4.setChecked(false);
            this.die5.setChecked(false);
            if (turns == maxturns) {
                this.die1.setEnabled(false);
                this.die2.setEnabled(false);
                this.die3.setEnabled(false);
                this.die4.setEnabled(false);
                this.die5.setEnabled(false);
                Button btn = (Button) findViewById(R.id.roll);
                btn.setEnabled(false);
                handResult(result);
                if (player2 == -1) {
                    //change to player 2
                } else {
                    //return winner/egality
                }
            } else {
                result.setText(getResources().getString(R.string.chooseDice));
                this.die1.setEnabled(true);
                this.die2.setEnabled(true);
                this.die3.setEnabled(true);
                this.die4.setEnabled(true);
                this.die5.setEnabled(true);
            }
        }

        public void handResult(TextView result) {
            List<Integer> sorteddice = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                sorteddice.add(dice[i]);
            }
            Collections.sort(sorteddice);
            Hands hand = handtest(sorteddice);
            if (player1 == -1) {
                player1 = hand.ordinal();
            } else {
                player2 = hand.ordinal();
            }
            switch (hand) {
                case BUST:
                    result.setText(getResources().getString(R.string.bust));
                    break;
                case ONEPAIR:
                    result.setText(getResources().getString(R.string.onePair));
                    break;
                case TWOPAIR:
                    result.setText(getResources().getString(R.string.twoPair));
                    break;
                case THREEOFAKIND:
                    result.setText(getResources().getString(R.string.threeKind));
                    break;
                case STRAIGHT:
                    result.setText(getResources().getString(R.string.straight));
                    break;
                case FULLHOUSE:
                    result.setText(getResources().getString(R.string.fullHouse));
                    break;
                case FOUROFAKIND:
                    result.setText(getResources().getString(R.string.fourKind));
                    break;
                case FIVEOFAKIND:
                    result.setText(getResources().getString(R.string.fiveKind));
                    break;
            }


        }

        public boolean straight(List<Integer> a) {
            for (int i = 0; i < 4; i++) {
                if (a.get(i) + 1 != a.get(i + 1)) {
                    return false;
                }
            }
            return true;
        }

        public Hands handtest(List<Integer> a) {
            int[] counts = new int[6];
            for (int i = 0; i < 6; i++) {
                counts[i] = 0;
            }
            Hands result = null;
            for (int i = 0; i < 5; i++) {
                if (dice[i] == 1) {
                    counts[0]++;
                } else if (dice[i] == 2) {
                    counts[1]++;
                } else if (dice[i] == 3) {
                    counts[2]++;
                } else if (dice[i] == 4) {
                    counts[3]++;
                } else if (dice[i] == 5) {
                    counts[4]++;
                } else if (dice[i] == 6) {
                    counts[5]++;
                }
            }
            for (int i = 0; i < 6; i++) {

                if (counts[i] == 5) {
                    result = Hands.FIVEOFAKIND;
                    break;

                } else if (counts[i] == 4) {
                    result = Hands.FOUROFAKIND;
                    break;

                } else if (counts[i] == 3) {
                    result = Hands.THREEOFAKIND;
                    for (int j = 0; j < 6; j++) {
                        if (counts[j] == 2) {
                            result = Hands.FULLHOUSE;
                            break;

                        }
                    }

                } else if (counts[i] == 2) {
                    result = Hands.ONEPAIR;
                    for (int j = 0; j < 6; j++) {
                        if (counts[j] == 2 && i != j) {
                            result = Hands.TWOPAIR;
                            break;

                        } else if (counts[j] == 3) {
                            result = Hands.FULLHOUSE;
                            break;
                        }
                    }

                }
            }
            if (result == null) {
                result = Hands.BUST;
            }
            if (straight(a)) {
                result = Hands.STRAIGHT;
            }
            return result;
        }

    }

    public enum Hands {
        BUST,
        ONEPAIR,
        TWOPAIR,
        THREEOFAKIND,
        STRAIGHT,
        FULLHOUSE,
        FOUROFAKIND,
        FIVEOFAKIND
    }

    private void changeImage(ToggleButton button){
        button.setHeight(button.getMeasuredWidth());
        ImageSpan imageSpan = imageSpan = new ImageSpan(this, android.R.drawable.ic_menu_info_details);;
        Drawable diePic = getResources().getDrawable(R.drawable.dice1);;
        switch (button.getText().toString()){
            case "1":
                diePic = getResources().getDrawable(R.drawable.dice1);
                break;
            case "2":
                diePic = getResources().getDrawable(R.drawable.dice2);
                break;
            case "3":
                diePic = getResources().getDrawable(R.drawable.dice3);
                break;
            case "4":
                diePic = getResources().getDrawable(R.drawable.dice4);
                break;
            case "5":
                diePic = getResources().getDrawable(R.drawable.dice5);
                break;
            case "6":
                diePic = getResources().getDrawable(R.drawable.dice6);
                break;
        }
        diePic.setBounds(0, 0, 64, 64);
        imageSpan = new ImageSpan(diePic);
        SpannableString content = new SpannableString("X");
        content.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        button.setText(content);
        button.setTextOn(content);
        button.setTextOff(content);
    }
}


