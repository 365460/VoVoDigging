package Gui;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import static java.lang.Math.abs;

/**
 * Created by Rober on 2017/6/6.
 */
public class Button {
    PApplet par;

    PVector pos;
    int width, height;
    String cont, hoveText;
    boolean hoving;
    public int dx = 0, dir = 1;

    boolean QQ = false;

    public Button(){

    }

    public Button(PApplet par,PVector pos,int w,int h, String s){
        this.par    = par;
        this.pos    = pos;
        this.width  = w;
        this.height = h;
        this.cont   = s;
        this.hoveText = s;
    }

    public void display(){
        par.textAlign(PConstants.CENTER);
        par.textSize(30);

        par.noFill();
        if(isHover()) par.stroke(255, 0, 0);
        else par.stroke(255);
        par.rect(pos.x, pos.y, width, height);

        if(isHover()){
            par.fill(255,0,0);
            par.text(hoveText, pos.x+width/2 + dx, pos.y+height/2+8);
            if(abs(dx)>=20){
                dir = dir==1? -1:1;
            }
            dx += dir*2;
        }
        else{
            hoving = false;
            dx = 0;
            dir = 1;
            par.fill(255);
            par.text(cont, pos.x+width/2, pos.y+height/2+8);
        }
    }

    public void moveTo(float x){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                float s = 30;
                if(pos.x>x){
                    while(pos.x > x){
                        pos.x -= s;
                        try{
                            Thread.sleep(5);
                        }
                        catch(Exception e){}
                    }
                    pos.x = x;
                }
                else{
                    while(pos.x <= x){
                        pos.x += s;
                        try{
                            Thread.sleep(10);
                        }
                        catch(Exception e){}
                    }
                    pos.x = x;

                }

            }
        });
        thread.start();
    }

    public boolean isHover(){
        float x = par.mouseX, y = par.mouseY;
        if( pos.x>x || x>pos.x+width) return false;
        if( pos.y>y || y>pos.y+height) return false;
        return true;
    }

    public void setHoverText(String s){
        hoveText = s;
    }
}
