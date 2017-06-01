package Reminder;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.regex.PatternSyntaxException;

/**
 * Created by Rober on 2017/6/1.
 */
public class Reminder extends Exception{
    String why;
    PApplet par;
    PVector pos = null;

    int x, y;
    public Reminder(PApplet par, int x,int y, String s){
        why = s;
        this.par = par;
        this.x = x;
        this.y = y;
    }

    public Reminder(PApplet par, PVector pos, String s){
        this.par = par;
        this.pos = pos;
        this.why = s;
    }

    public String why(){
        return why;
    }

    public void display(){
        par.fill(255);
        par.textSize(20);
        if(pos!=null){
            par.text( why, pos.x, pos.y);
        }
        else
            par.text( why, x, y);
    }
}
