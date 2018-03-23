package com.cabanettes.axel.nguyen.eric.depoker;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
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
    private int player1=-1;
    private int player2=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        btn.setOnClickListener(new RollBtnListener(die1,die2,die3,die4,die5));
    }

    public class RollBtnListener implements View.OnClickListener{
        private int[] dice;
        ToggleButton die1;
        ToggleButton die2;
        ToggleButton die3;
        ToggleButton die4;
        ToggleButton die5;
        TextView result;
        int turns;
        int maxturns=5;

        public RollBtnListener(ToggleButton die1,ToggleButton die2,ToggleButton die3,ToggleButton die4,ToggleButton die5){
            this.dice = new int[5];
            this.turns=0;
            this.die1=die1;
            this.die2=die2;
            this.die3=die3;
            this.die4=die4;
            this.die5=die5;
        }

        @Override
        public void onClick(View view) {
            result = (TextView) findViewById(R.id.result);
            for (int j=1;j<6;j++) {
                ToggleButton die = (ToggleButton) findViewById(getResources().getIdentifier("die"+j,"id",getPackageName()));
                if (die.isChecked() == false) {
                    Random r = new Random();
                    int v = r.nextInt(7 - 1) + 1;
                    this.dice[j-1] = v;
                }
            }

            this.die1.setText(""+dice[0]);
            this.die2.setText(""+dice[1]);
            this.die3.setText(""+dice[2]);
            this.die4.setText(""+dice[3]);
            this.die5.setText(""+dice[4]);
            /*
            * Bundle extras = getIntent().getExtras();
            * if(extras != null){
            *   String s = (String) extras.get("numberTurn");
            *   maxturns =
            * }*/
            this.turns++;
            if (turns==maxturns) {
                this.die1.setEnabled(false);
                this.die2.setEnabled(false);
                this.die3.setEnabled(false);
                this.die4.setEnabled(false);
                this.die5.setEnabled(false);
                this.die1.setChecked(false);
                this.die2.setChecked(false);
                this.die3.setChecked(false);
                this.die4.setChecked(false);
                this.die5.setChecked(false);
                Button btn = (Button) findViewById(R.id.roll);
                btn.setEnabled(false);
                handResult(result);
                if (player2==-1) {
                    //change to player 2
                } else {
                    //return winner/egality
                }
            } else {
                result.setText("Choose which dice to keep");//xml language
                this.die1.setEnabled(true);
                this.die2.setEnabled(true);
                this.die3.setEnabled(true);
                this.die4.setEnabled(true);
                this.die5.setEnabled(true);
            }
        }

        public void handResult(TextView result){
            List<Integer> sorteddice = new ArrayList<>();
            for (int i=0;i<5;i++){
                sorteddice.add(dice[i]);
            }
            Collections.sort(sorteddice);
            Hands hand = handtest(sorteddice);
            if (player1==-1){
                player1 = hand.ordinal();
            } else {
                player2=hand.ordinal();
            }
            switch (hand){
                case BUST:
                    result.setText("Bust");//xml language
                    break;
                case ONEPAIR:
                    result.setText("One Pair");//xml language
                    break;
                case TWOPAIR:
                    result.setText("Two Pair");//xml language
                    break;
                case THREEOFAKIND:
                    result.setText("Three of a kind");//xml language
                    break;
                case STRAIGHT:
                    result.setText("Straight");//xml language
                    break;
                case FULLHOUSE:
                    result.setText("Fullhouse");//xml language
                    break;
                case FOUROFAKIND:
                    result.setText("Four of a kind");//xml language
                    break;
                case FIVEOFAKIND:
                    result.setText("Five of a kind");//xml language
                    break;
            }



        }

        public boolean straight(List<Integer> a){
            for (int i=0;i<4;i++){
                if (a.get(i)+1!=a.get(i+1)){
                    return false;
                }
            }
            return true;
        }

        public Hands handtest(List<Integer> a){
            int[] counts = new int[6];
            for(int i=0;i<6;i++){
                counts[i]=0;
            }
            Hands result=null;
            for (int i = 0; i <5; i++) {
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
                        if (counts[j]==2) {
                            result = Hands.FULLHOUSE;
                            break;

                        }
                    }

                } else if (counts[i] == 2) {
                    result = Hands.ONEPAIR;
                    for (int j = 0; j < 6; j++) {
                        if (counts[j] == 2 && i!=j) {
                            result = Hands.TWOPAIR;
                            break;

                        }
                        else if (counts[j] == 3){
                            result = Hands.FULLHOUSE;
                            break;
                        }
                    }

                }
            }
            if (result==null){
                result = Hands.BUST;
            }
            if(straight(a)){
                result = Hands.STRAIGHT;
            }
            return result;
        }

    }
    public enum Hands{
        BUST,
        ONEPAIR,
        TWOPAIR,
        THREEOFAKIND,
        STRAIGHT,
        FULLHOUSE,
        FOUROFAKIND,
        FIVEOFAKIND
    }

}


