package Setting;

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
    String cont;
    boolean hoving;
    public int dx = 0, dir = 1;
    float ratio = 1;

    int font = 20;

    public Button(){

    }

    public Button(PApplet par,PVector pos,int w,int h, String s){
        this.par    = par;
        this.pos    = pos;
        this.width  = w;
        this.height = h;
        this.cont   = s;
    }

    public void setFont(int v){
        font = v;
    }
    public void display(){
        float w = width*ratio;
        float h = height*ratio;

        par.textAlign(PConstants.CENTER);
        par.textSize(font*ratio);

        par.fill(0);
        par.text(cont, pos.x+width/2, pos.y+height/2+5);

        par.noFill();
        par.strokeWeight(2);
        if(isHover())
            par.stroke(255,0,0);
        else
            par.stroke(183,111,111);

        par.rectMode(PConstants.CENTER);
        par.rect(pos.x+width/2, pos.y+height/2, w, h, 30);
        par.rectMode(PConstants.CORNER);

    }

    public void toDie(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                float s = 0.1f;
                while(ratio-s>=0){
                    ratio -= s;
                    try{
                       Thread.sleep(50);
                    }catch (Exception e){}
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
}
