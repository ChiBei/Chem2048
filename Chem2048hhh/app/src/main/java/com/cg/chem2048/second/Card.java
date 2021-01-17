package com.cg.chem2048.second;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringDef;
import androidx.core.content.ContextCompat;

import java.util.Objects;

public class Card extends FrameLayout {         //  card继承自framelayout，以下为设置card的各种代码
    public Card(@NonNull Context context) {
        super(context);

        LayoutParams lp=null;                   //???

//        background = new View(getContext());            //这三句在干嘛？？？
//        background.setBackgroundColor(0x33ffffff);
//        addView(background, lp);

        label=new TextView(getContext());
        label.setTextSize(25);
        label.setGravity(Gravity.CENTER);

        label.setBackgroundColor(0x50ffffff);
        lp=new LayoutParams(-1,-1);  //填充满整个父级
        lp.setMargins(20,20,0,0);
        addView(label,lp);

        setNum(0);
    }

    private int num=0;

    public int getNum(){

        return num;
    }

    String[]ChemName={
            "氢","氦","锂","铍","硼",
            "碳","氮","氧","氟","氖",
            "钠","镁","铝","硅","磷",
            "硫","氯","氩","钾","钙",
            "钪","钛","钒","铬","锰",
            "铁","钴","镍","铜","锌",
            "镓","锗","砷","硒","溴",
            "氪"};

    String[]ChemNameEng={
            "H","He","Li","Be","B",
            "C","N","O","F","Ne",
            "Na","Mg","Al","Si","P",
            "S","Cl","Ar","K","Ca",
            "Sc","Ti","V","Cr","Mn",
            "Fe","Co","Ni","Cu","Zn",
            "Ga","Ge","As","Se","Br",
            "Kr"};

    @SuppressLint("ResourceAsColor")
    public void setNum(int num) {
        this.num = num;

        if (num<=0){
            label.setText(" ");
        }else{
            label.setText(ChemNameEng[(int)(Math.log(num) /Math.log(2))-1]);     //和字符串相连，使int转换为字符串,接收参数需要字符串,如果无空格，则是给变量的地址赋值了。。但是好像不用加也没事？
            label.setTextColor(Color.WHITE);
        }

        switch (num) {
            case 0:
                label.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorof0));
                break;
            case 2:
                label.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorof2));
                break;
            case 4:
                label.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorof4));
                break;
            case 8:
                label.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorof8));
                break;
            case 16:
                label.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorof16));
                break;
            case 32:
                label.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorof32));
                break;
            case 64:
                label.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorof64));
                break;
            case 128:
                label.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorof128));
                break;
            case 256:
                label.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorof256));
                break;
            case 512:
                label.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorof512));
                break;
            case 1024:
                label.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorof1024));
                break;
            case 2048:
                label.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorof2048));
                break;
            case 4096:
                label.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorof4096));
                break;
            case 8192:
                label.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorof8192));
                break;
            case 16384:
                label.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorof16384));
                break;
            case 32768:
                label.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorof32768));
                break;
            case 65536:
                label.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorof65536));
                break;
            case 131072:
                label.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorof131072));
                break;
            default:
                label.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorof0));
                break;
        }
    }




    public boolean equals(Card o) {
        return getNum()==o.getNum();
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, label);
    }

    private TextView label;

    public TextView getLabel() {
        return label;
    }

    private View background;
}
