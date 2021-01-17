package com.cg.chem2048.second;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

public class GameView extends GridLayout {

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    public GameView(Context context) {
        super(context);
        initGameView();
    }

    private void initGameView(){

        setOnTouchListener(new View.OnTouchListener() {

            private float startX,startY,offsetX,offsetY; //touch滑动的起点坐标,touch偏移的距离长度

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){                     //侦听两个动作
                    case MotionEvent.ACTION_DOWN:               //手指按下
                        startX=event.getX();
                        startY=event.getY();
                        break;

                    case MotionEvent.ACTION_UP:                 //手指抬起，两个动作结合就是一次划动
                        offsetX=event.getX()-startX;
                        offsetY=event.getY()-startY;

                        if(Math.abs(offsetX)>Math.abs(offsetY)){            //x方向位移绝对值比y大：手指是水平移动
                            if (offsetX<-10){                          //水平至少-5个像素->向左
                                swipeLeft();
                            } else if (offsetX>10){
                                swipeRight();
                            }
                        }
                        else{                                               //x方向位移绝对值比y小：手指是竖直移动
                            if (offsetY<-10){
                                swipeUp();
                            } else if (offsetY>10){
                                swipeDown();
                            }
                        }
                        break;
                }
                return true; //手指触发有action down move up三个动作，
                            //如果onTouch返回false，则会让程序以为action_down还没有触发成功，
                            //则程序会默认不去侦听剩下两个动作（因为move很消耗资源），则侦听不了up了
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int cardWidth=(Math.min(w,h)-20)/5;  //手机屏幕的width和height二者之间取最小的

        addCards(cardWidth,cardWidth);    //正方形卡片

        startGame();

    }

    private void addCards(int cardWidth,int cardHeight){


        Card c;

        for (int y=0;y<5;y++){              //六列
            for (int x=0;x<5;x++){                 //六行
                c=new Card(getContext());
                c.setNum(0);                    //初始化为0，则一开始每个卡片都显示是空的，参照card.java里面定义的
                addView(c,cardWidth,cardHeight);

                cardsMap[x][y]=c;    //把前面设置的c添加到二维数组里面，留着后面用

            }
        }
    }

    private  void startGame() {                 //游戏正式开始
        MainActivity.getMainActivity().clearScore();
        for (int y=0;y<5;y++){
            for (int x=0;x<5;x++) {
                cardsMap[x][y].setNum(0);       //先设置为0

            }
        }
        addRandomNum();                         //再产生两个随机数
        addRandomNum();

    }




    private void addRandomNum(){
        emptyPoint.clear();         //清空

        for (int y=0;y<5;y++){                                  //遍历所有元素
            for (int x=0;x<5;x++){
                if(cardsMap[x][y].getNum()<=0){                 //如果此处为空的话，则给他添加一个数字
                    emptyPoint.add(new Point(x,y));
                }
            }
        }

        Point p =emptyPoint.remove((int)(Math.random()*emptyPoint.size()));
        //随机移除一个点，random生成0-1的随机数
        cardsMap[p.x][p.y].setNum(Math.random()>0.1?2:4);
        //这个random即为随机产生的一个数，如果>0.1，则设置为2，否则为4，则2出现的概率为0.1

    }


    private  void swipeLeft() {

        boolean merge=false;      //是否有合并，就添加新的数字

        for (int y=0;y<5;y++){                                  //对于一行中的左右滑动，即从y=0开始遍历
            for (int x=0;x<5;x++){                              //从左上角开始遍历

                for(int x1=x+1;x1<5;x1++){                       //从当前位置向右遍历,从第x1列（第2列）开始遍历

                    if(cardsMap[x1][y].getNum()>0){             //如果第x1列的数大于0，即非空->即第一次遍历出数字来了
                        if(cardsMap[x][y].getNum()<=0) {
                            //那么要根据一开始遍历所有元素的时候的情况来解决
                            //①：如果一开始的数是空的，那么要把遍历出来的x1的值给到一开始的这个最左边的空格子，
                            // 而且遍历出来的这个数要置为0（相当于交换数字）



                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);

                            x--;

                            merge=true;                   //有合并

                        }else if (cardsMap[x][y].equals(cardsMap[x1][y])){
                            //②：如果查到一个格子，里面x1 y的值等于这次遍历的原始值
                            // （找出的x1 y是大于0的，也就是说一开始的数也是非空，也就是说最左边的格子非空）
                            // 那么可以合并为两倍，遍历出来的数字同样要置为0
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);

                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            //相加成功（*2成功），则把加出来的数加到分数里面，此时x y已经翻倍了

                            merge=true;


                        }
                        break;
                    }
                }
            }
        }
        if(merge){
            addRandomNum();
            checkComplete();            //检查是否游戏结束

        }


    }




    private  void swipeRight() {
        boolean merge=false;

        for (int y=0;y<5;y++){
            for (int x=4;x>=0;x--){                              //从右向左遍历,从4开始

                for(int x1=x-1;x1>=0;x1--){                       //逐项减遍历

                    if(cardsMap[x1][y].getNum()>0){
                        if(cardsMap[x][y].getNum()<=0) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x++;                                    //要改
                            merge=true;


                        }else if (cardsMap[x1][y].equals(cardsMap[x][y])){
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);

                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());

                            merge=true;


                        }
                        break;

                    }
                }
            }
        }
        if(merge){
            addRandomNum();
            checkComplete();

        }

    }



    private  void swipeUp() {
        boolean merge=false;

        for (int x=0;x<5;x++){
            for (int y=0;y<5;y++){                              //xy反过来遍历

                for(int y1=y+1;y1<5;y1++){                       //逐项减遍历

                    if(cardsMap[x][y1].getNum()>0){
                        if(cardsMap[x][y].getNum()<=0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y--;                                    //要改
                            merge=true;

                        }else if (cardsMap[x][y1].equals(cardsMap[x][y])){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum()*2);
                            cardsMap[x][y1].setNum(0);

                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge=true;

                        }
                        break;

                    }
                }
            }
        }
        if(merge){
            addRandomNum();
            checkComplete();

        }
    }

    private  void swipeDown() {
        boolean merge=false;

        for (int x=0;x<5;x++){
            for (int y=4;y>=0;y--){                              //xy反过来遍历

                for(int y1=y-1;y1>=0;y1--){                       //逐项减遍历

                    if(cardsMap[x][y1].getNum()>0){
                        if(cardsMap[x][y].getNum()<=0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y++;                                    //要改
                            merge=true;

                        }else if (cardsMap[x][y1].equals(cardsMap[x][y])){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum()*2);
                            cardsMap[x][y1].setNum(0);

                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge=true;

                        }
                        break;
                    }
                }
            }
        }

        if(merge){
            addRandomNum();
            checkComplete();
        }

    }

    private void checkComplete(){
        boolean complete=true;
        ALL:                            //标签，用于break跳出所有循环
        for (int y=0;y<5;y++){
            for (int x=0;x<5;x++){
               if (cardsMap[x][y].getNum()==0||                     //二维数组值为0，说明是空格，游戏没有结束
                       //如果对于遍历的这一个格子来说，它的四个方向上有和他相同的格子，则游戏没有结束
                       (x>0&&cardsMap[x][y].equals(cardsMap[x-1][y]))||     //x>0的格子，才判断左边有没有相同的
                       (x<4&&cardsMap[x][y].equals(cardsMap[x+1][y]))||     //x<4的格子，才判断右边有没有相同的
                       (y>0&&cardsMap[x][y].equals(cardsMap[x][y-1]))||     //y>0的格子，才判断上边有没有相同的
                       (y<4&&cardsMap[x][y].equals(cardsMap[x][y+1]))       //y<4的格子，才判断下边有没有相同的
               ){
                   complete=false;          //没有结束
                   break ALL;
                }
            }
        }
        if (complete){
            new AlertDialog.Builder(getContext())
                    .setTitle("hello")
                    .setMessage("end")
                    .setPositiveButton("begin again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startGame();
                        }
                    }).show();
        }
    }


    private Card[][] cardsMap=new Card[5][5];
    private List<Point> emptyPoint=new ArrayList<Point>();



}
