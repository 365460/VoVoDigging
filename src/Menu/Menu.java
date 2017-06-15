package Menu;

import Gui.Button;
import processing.core.*;
import Game.*;

import java.text.DateFormat;
import java.util.Date;

import static java.lang.Float.max;
import static java.lang.Float.min;


/**
 * Created by Rober on 2017/6/6.
 */

public class Menu {
    PApplet par;
    PImage bg;
    Button[] btn;
    Game game;
    Loader load;
    int button_width = 400;
    int button_height = 50;
    float base = 200;

    float  tlen;

    float yy = base -60;
    float force = -15;
    float up = force;

    String[] fontList = PFont.list();

    MenuStatus status = MenuStatus.Normal;
    String[] message = {"Start New Game", "Load Game", "Exit"};

    public Menu(PApplet par, Game game){

        this.par = par;
        this.game = game;
        load = new Loader(par, game, this);
        tlen =  par.textWidth("VoVoDigging");
        bg   = par.loadImage("image/menuBg.jpg");

        btn = new Button[3];
        for(int i=0; i<3; i++){
            btn[i] = new Button(par, new PVector(par.width/2-button_width/2, base + i*par.height/5),
                button_width, button_height, message[i]);
        }
        btn[2].setHoverText("QQ");
//        par.printArray(fontList);
    }

    public void display(){

        par.image(bg, 0, 0, par.width, par.height);
//        par.background(150);
        switch (status){
            case Normal:

                par.fill(108, 114, 124,110);
                par.noStroke();
                par.rectMode(PConstants.CORNER);
                par.rect(170, base-150,490,500);
                par.stroke(255);

                par.fill(255);
                par.textSize(50);
                par.textAlign(PConstants.CENTER, PConstants.BOTTOM);

                par.text("VoVoDiggin", par.width/2, base-60);
                par.text("VoVoDiggin", par.width/2+4, base-60);

                if(force!=0) yy += up;
                up += 3.;
                if(yy>=base-60 && force<0){
                    force *= 0.7;
                    up = force;
                }

                if(force==0) yy = base-60;
                par.text("g",par.width/2+150+8, yy);
                par.text("g",par.width/2+150+4+8, yy);
//                par.line(par.width/2-tlen, base, par.width/2+tlen/2, base);


                for(Button b:btn){
                    b.display();
                }
                break;
            case loadData:;
                load.display();
        }
    }


    public void toNormal(){
        status = MenuStatus.Normal;
        force = -15;
        up = force;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    for(int i=2; i>=0; i--){
                        btn[i].moveTo(par.width/2-button_width/2);
                        Thread.sleep(200);
                    }
                    Thread.sleep(300);
                }catch(Exception e){

                }
            }
        });
        thread.start();
    }

    public void toLoader(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    for(int i=0; i<3; i++){
                        btn[i].moveTo(-button_width);
                        Thread.sleep(200);
                    }
                    Thread.sleep(300);

                }catch(Exception e){

                }
                status = MenuStatus.loadData;
            }
        });
        thread.start();
    }

    public void mousePressed(){
        switch(status){
            case Normal:
                if(btn[0].isHover()){
                    game.initNewGame();
                }
                if(btn[1].isHover()){
                    load.loadKeep();
                    toLoader();
//                    status = MenuStatus.loadData;
                }
                if(btn[2].isHover()){
                    par.exit();
                }
                break;

            case loadData:
                load.mousePressed();
                break;
        }
    }

}
enum MenuStatus{
    Normal,
    loadData
}
