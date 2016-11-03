package com.geekgamerz.clashroyalestart;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import static java.lang.Math.pow;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private Button myButton;
    private int progressStatus = 0;
    //Define the buttons and text

    private Button increase;
    private Button decrease;
    private TextView numVal;
    //progress bar view
    private TextView textView;
    private Handler handler = new Handler();
    private EditText available_cards;
    private EditText current_level;
    private EditText arena_edittext;
    //use "a" array
    private TextView Total_cards_required;

    //use "cr"array

    private TextView Gold;

    //cards required
    private int[] a = {0, 0, 2, 4, 10, 20, 50, 100, 200, 400, 800, 1000, 2000, 5000};
    //gold required
    private int[] cost = {0, 0, 5, 20, 50, 150, 400, 1000, 2000, 4000, 8000, 20000, 50000, 100000};
    private TextView errorinput;

    ////considered common card for testing
    private int card_type = 1;

    //default number

    private int n = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        numVal = (TextView) findViewById(R.id.number);
        decrease = (Button) findViewById(R.id.buttonDecrease);
        increase = (Button) findViewById(R.id.buttonIncrease);

        //old code

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.progresstv);
        myButton = (Button) findViewById(R.id.pbbutton);
        current_level = (EditText) findViewById(R.id.editText2);
        available_cards = (EditText) findViewById(R.id.editText1);
        arena_edittext = (EditText) findViewById(R.id.editText3);
        Total_cards_required = (TextView) findViewById(R.id.textView5);
        Gold = (TextView) findViewById(R.id.textView6);
        errorinput = (TextView) findViewById(R.id.textView7);


        //////////////////BUTTON IS HERE////////////////////

        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                errorinput.setText("");
                testinputs();
            }
        });

        //Set the textView to the initial value

        numVal.setText(Integer.toString(n));

        //Button to increase the  number

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (n < 13) {

                    //Nothing will happen when the number reaches 13
                    n = n + 1;
                    numVal.setText(Integer.toString(n));
                }
            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (n > 1) {
                    n = n - 1;
                    numVal.setText(Integer.toString(n));
                }
            }
        });
    }


    void testinputs() {
        int sucess = 1;
        int current = Integer.parseInt(current_level.getText().toString());
        int available = Integer.parseInt(available_cards.getText().toString());
        int arena = Integer.parseInt(numVal.getText().toString());
        if (available < 0) {
            errorinput.setText("invalid number of availabe cards");
            sucess = 0;
        } else {

            ////this is not working!!!!!!!!
            if (current < 1 || current >= 14) {
                errorinput.setText("enter a valid level");
                sucess = 0;
            } else {
                if (arena < 1 || arena > 9) {
                    errorinput.setText("invalid arena");
                    sucess = 0;
                }
            }
        }
        if (sucess == 1) progressfunction();  ///if all i/p correct
    }


    void progressfunction() {

        int current = Integer.parseInt(current_level.getText().toString());
        final int available = Integer.parseInt(available_cards.getText().toString());
        int arena = Integer.parseInt(numVal.getText().toString());
        int gold_required, arena_score = 1;
        double days_req;

        //for amount of cards per requests

        switch (arena) {
            case 1:
            case 2:
            case 3:
                arena_score = 1;
                break;
            case 4:
            case 5:
            case 6:
                arena_score = 2;
                break;
            case 7:
            case 8:
                arena_score = 3;
                break;
            case 9:
                arena_score = 4;
                break;
        }

        final int card_required = a[current];
        if (card_type == 1) {
            gold_required = cost[current];
        } else {
            gold_required = cost[current + 2];
        }

        //main formula

        days_req = (int) (card_required - available) / (pow(10, card_type) * 3 * arena_score);
        Gold.setText("" + gold_required);
        Total_cards_required.setText("" + card_required);
        errorinput.setText("Number of days required : " + days_req);

        ///////////progress bar script here
        progressStatus = 0;
        final double progress_result = (available * 100) / card_required;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressStatus < progress_result) {
                    progressStatus += 1;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            textView.setText("Progress: " + available + "/" + card_required);
                        }
                    });
                    try {
                        Thread.sleep(100);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }
            //progress bar stuff ends here
        }).start();
    }


}


