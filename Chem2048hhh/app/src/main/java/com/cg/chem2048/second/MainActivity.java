package com.cg.chem2048.second;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    public MainActivity(){
        mainActivity=this;
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        present_score_num=(TextView) findViewById(R.id.present_score_num);          //强制类型转换
        highest_score_num=(TextView) findViewById(R.id.highest_score_num);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {                                   //重写不小心退出游戏的方法
        new AlertDialog.Builder(this)
                .setTitle("游戏进度会丢失")
                .setMessage("确认退出？")
                .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                    }
                    })
                .setNegativeButton("留下", null)
                .show();
    }



    public void clearScore(){
        score=0;
        showScore();
    }

    public void showScore(){                //分数展示
        present_score_num.setText(score+" ");
    }

    public void addScore(int s){            //分数累计
        score+=s;
        showScore();

        int maxScore = Math.max(score, getBestScore());
        saveBestScore(maxScore);
        showBestScore(maxScore);

    }

    public void saveBestScore(int s) {
        SharedPreferences.Editor e = getPreferences(MODE_PRIVATE).edit();
        e.putInt(SP_KEY_BEST_SCORE, s);
        e.commit();
    }

    public int getBestScore(){
        return getPreferences(MODE_PRIVATE).getInt(SP_KEY_BEST_SCORE, 0);
    }

    public void showBestScore(int s){
        highest_score_num.setText(s+"");
    }






    private int score=0;                    //初始分数设为0
    private TextView present_score_num;
    private TextView highest_score_num;

    private static MainActivity mainActivity=null;

    public static MainActivity getMainActivity() {          //???
        return mainActivity;
    }

    public static final String SP_KEY_BEST_SCORE = "bestScore";

}