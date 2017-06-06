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
    ReminderMode mode = ReminderMode.NORMAL;
    int delay = 0;

    int x, y;
    public Reminder(){

    }

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

    public Reminder(PApplet par, String s){
        why = s;
        this.par = par;
        this.mode = ReminderMode.BROADCAST;
    }

    public String why(){
        return why;
    }

    public int getDelay(){
        return delay;
    }

    public void setDelay(int v){
        delay = v;
    }

    public void display(){

        par.fill(255);
        par.textSize(20);
        switch (mode){
            case NORMAL:
                if(pos!=null){
                    par.text( why, pos.x, pos.y);
                }
                else
                    par.text( why, x, y);
                break;
            case BROADCAST:
                par.textAlign(par.CENTER);
                par.fill(255, 0, 0);
                par.text(why,par.width/2, 50);
                break;
        }
    }
}
enum ReminderMode{
       NORMAL,
    BROADCAST
}