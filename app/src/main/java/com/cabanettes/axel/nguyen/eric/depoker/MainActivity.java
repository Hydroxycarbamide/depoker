package com.cabanettes.axel.nguyen.eric.depoker;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int player1 = -1;
    private int player2 = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Initialisation du Dark theme
        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean("dark_theme", false);
        if (useDarkTheme) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String namePlayer1 = Accueil.db.getJoueur(1).getName();
        TextView result = (TextView) findViewById(R.id.result);
        result.setText(namePlayer1 + " " + getResources().getString(R.string.turn));

        //Initialisation des buttons et dés
        Button btn = (Button) findViewById(R.id.roll);
        for (int j = 1; j <= 5; j++) {
            ToggleButton die = (ToggleButton) findViewById(getResources().getIdentifier("die" + j, "id", getPackageName()));
            die.setEnabled(false);
        }
        int turnNumber = preferences.getInt("turnNumber", 3);
        btn.setOnClickListener(new RollBtnListener(turnNumber));
    }

    //Ecouteur pour le bouton Roll the dice
    public class RollBtnListener implements View.OnClickListener {
        private int[] dice;
        TextView result = (TextView) findViewById(R.id.result);
        ColorStateList oldColors;
        int turns;
        int maxturns;


        public RollBtnListener(int turnNumber) {
            this.dice = new int[5];
            this.turns = 0;
            maxturns = turnNumber;
            oldColors = result.getTextColors();
            result.setTextColor(Color.parseColor("#03A9F4"));
        }

        @Override
        public void onClick(View view) {
            result.setTextColor(oldColors);
            String namePlayer2 = Accueil.db.getJoueur(2).getName();
            Button btn = (Button) findViewById(R.id.roll);
            if ((String) btn.getText() == getResources().getString(R.string.next)) {
                result.setTextColor(Color.parseColor("#03A9F4"));
                this.result.setText(namePlayer2 + " " + getResources().getString(R.string.turn));
                btn.setText(R.string.roll);
            } else if (btn.getText() == getResources().getString(R.string.end)) {
                Intent intent = new Intent();
                if (player1 > player2) {
                    intent.putExtra("winner", 1);
                } else if (player1 < player2) {
                    intent.putExtra("winner", 2);
                } else intent.putExtra("winner", 0);
                intent.putExtra("valuePlayer1",player1);
                intent.putExtra("valuePlayer2",player2);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                //Gestion du son
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.sound);
                mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    public void onPrepared(MediaPlayer player) {
                        player.start();
                    }
                });

                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                mp.start();

                //Gestion du jeu
                this.turns++;
                for (int j = 1; j <= 5; j++) {
                    ToggleButton die = (ToggleButton) findViewById(getResources().getIdentifier("die" + j, "id", getPackageName()));
                    if (die.isChecked() == false) {
                        Random r = new Random();
                        int v = r.nextInt(7 - 1) + 1;
                        this.dice[j - 1] = v;
                    }
                    Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
                    die.startAnimation(shake);
                    die.setText("" + dice[j-1]);
                    changeImage(die);
                    die.setChecked(false);
                }

                if (turns == maxturns) {
                    for (int j = 1; j <= 5; j++) {
                        ToggleButton die = (ToggleButton) findViewById(getResources().getIdentifier("die" + j, "id", getPackageName()));
                        die.setEnabled(false);
                    }

                    //btn.setEnabled(false);
                    handResult(result);
                    if (player2 == -1) {
                        //change to player 2
                        this.turns = 0;
                        btn.setText(R.string.next);
                    } else {
                        btn.setText(R.string.end);
                    }
                } else {
                    result.setText(getResources().getString(R.string.chooseDice));
                    for (int j = 1; j <= 5; j++) {
                        ToggleButton die = (ToggleButton) findViewById(getResources().getIdentifier("die" + j, "id", getPackageName()));
                        die.setEnabled(true);
                    }
                }
            }
        }


        //Gestion de du resultat
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
            result.setTextColor(getResources().getColor(R.color.colorAccent));
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

        //Vérification d'une suite de 5 dés
        public boolean straight(List<Integer> a) {
            for (int i = 0; i < 4; i++) {
                if (a.get(i) + 1 != a.get(i + 1)) {
                    return false;
                }
            }
            return true;
        }

        //Gestion de la détection du résultat de la main
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


    //Fonction permettant de changer l'image des dés
    private void changeImage(ToggleButton button) {
        button.setHeight(button.getMeasuredWidth());
        Drawable diePic;
        switch (button.getText().toString()) {
            case "1":
                diePic = ContextCompat.getDrawable(this,R.drawable.dice1);
                break;
            case "2":
                diePic = ContextCompat.getDrawable(this,R.drawable.dice2);
                break;
            case "3":
                diePic = ContextCompat.getDrawable(this,R.drawable.dice3);
                break;
            case "4":
                diePic = ContextCompat.getDrawable(this,R.drawable.dice4);
                break;
            case "5":
                diePic = ContextCompat.getDrawable(this,R.drawable.dice5);
                break;
            case "6":
                diePic = ContextCompat.getDrawable(this,R.drawable.dice6);
                break;
            default:
                diePic = ContextCompat.getDrawable(this,android.R.drawable.ic_menu_info_details);
        }
        button.setAllCaps(false);
        diePic.setBounds(0, 0, button.getMeasuredWidth() - 70, button.getMeasuredWidth() - 64);
        ImageSpan imageSpan = new ImageSpan(diePic);
        SpannableString content = new SpannableString(button.getText().toString());
        content.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        button.setText(content);
        button.setTextOn(content);
        button.setTextOff(content);
    }

    //Gestion du blocage de la rotation d'écran
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                lockScreenRotation(Configuration.ORIENTATION_PORTRAIT);
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                lockScreenRotation(Configuration.ORIENTATION_LANDSCAPE);
                break;
            default:
                break;
        }
    }
    private void lockScreenRotation(int orientation) {
        switch (orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            default:
                this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                break;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            confirmDialog();
        }
        return false;
    }

    public void confirmDialog(){
        Log.d("Dialog", "Starting...");
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle(getString(R.string.sureLeaving));
        builder1.setMessage(getString(R.string.loseProgress));
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                getResources().getString(R.string.Yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

        builder1.setNegativeButton(
                getResources().getString(R.string.No),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }



}
