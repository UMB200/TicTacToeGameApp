package com.example.umix.ticktackgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    //0: yellow 1: red 2: empty

    int whoPlays = 0;
    int[] cellStatus = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    String msg;

    Button restartBtn;
    TextView winText;

    //winning combinations
    int[][] effectiveCombinations = {
            {0, 1,2},
            {0,3,6},
            {0,4,8},
            {1,4,7},
            {2,5,8},
            {3,4,5},
            {6,4,2},
            {6,7,8},
     };
    //boolean to dteremine if game is over
    boolean gameOver = false;

    //pupulates grid with the circle clicked in relevant square
    public void drop(View view){
        //find out which one was clicked
        ImageView counter = (ImageView) view;

        //retrieve tag
        int tagStatus = Integer.parseInt(counter.getTag().toString());

        //check if cell is taken then we proceed, if not can't move
        if(cellStatus[tagStatus] == 2 && !gameOver) {
            //set retrieved tag to status
            cellStatus[tagStatus] = whoPlays;
            //move out of the screen
            counter.setTranslationY(-1500);

            if (whoPlays == 0) {
                //set image from Drawable folder
                counter.setImageResource(R.drawable.yellow);
                whoPlays = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                whoPlays = 0;
            }
            //move in to clicked image
            counter.animate().translationYBy(1500).rotationX(1800).setDuration(300);
            whoWon();
        }
        else  {
            Toast.makeText(this, "please chose another spot", Toast.LENGTH_SHORT).show();
        }
    }

    //determine who won
    public void whoWon() {
        for(int[]effectiveCombinations : effectiveCombinations ){
            if(cellStatus[effectiveCombinations[0]] == cellStatus[effectiveCombinations [1]] &&
                    cellStatus[effectiveCombinations[1]] == cellStatus[effectiveCombinations [2]] &&
                    cellStatus[effectiveCombinations[0]] !=2){
                String msg;
                if(whoPlays == 1){
                    msg = "Yellow";
                }
                else {
                    msg = "Red";
                }

                //Toast.makeText(this, msg + " won!", Toast.LENGTH_LONG).show();
                restartBtn = (Button)findViewById(R.id.restartBtn);
                winText = (TextView)findViewById(R.id.winText);
                winText.setText(msg + " won!");
                restartBtn.setVisibility(View.VISIBLE);
                winText.setVisibility(View.VISIBLE);
                gameOver = false;
            }

        }

    }
    //starts game over and removes all elements from the screen
    public  void restart(View view){
        restartBtn = (Button)findViewById(R.id.restartBtn);
        winText = (TextView)findViewById(R.id.winText);
        //set both elements invisible
        restartBtn.setVisibility(View.INVISIBLE);
        winText.setVisibility(View.INVISIBLE);

        android.support.v7.widget.GridLayout gridLayout = (android.support.v7.widget.GridLayout) findViewById(R.id.gridLayout);

        //loop throigh all elements in the gri
        for(int i = 0; i< gridLayout.getChildCount(); i++){
            //get all elements
            ImageView circles = (ImageView)gridLayout.getChildAt(i);
            //remove all elements
            circles.setImageDrawable(null);
        }

        //set all elements to null
        for(int i = 0; i < cellStatus.length; i++){
            cellStatus[i] = 2;
        }
        whoPlays = 0;
        gameOver = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
