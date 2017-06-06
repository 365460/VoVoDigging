package Menu;

import Gui.Button;
import processing.core.*;
import Game.*;

import java.text.DateFormat;
import java.util.Date;


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
    String[] fontList = PFont.list();

    MenuStatus status = MenuStatus.Normal;
    String[] message = {"Start New Game", "Load Game", "Exit"};

    public Menu(PApplet par, Game game){

        this.par = par;
        this.game = game;
        load = new Loader(par, game, this);
        tlen =  par.textWidth("VoVoDigging");
        bg  = par.loadImage("image/menuBg.jpg");

        btn = new Button[3];
        for(int i=0; i<3; i++){
            btn[i] = new Button(par, new PVector(par.width/2-button_width/2, base + i*par.height/5),
                button_width, button_height, message[i]);
        }
//        par.printArray(fontList);
    }

    public void display(){

        par.image(bg, 0, 0, par.width, par.height);
        switch (status){
            case Normal:

                par.stroke(255);
                par.fill(255);
                par.textSize(50);
                par.textAlign(PConstants.CENTER, PConstants.BOTTOM);

                par.text("VoVoDigging", par.width/2, base-60);
//                par.line(par.width/2-tlen, base, par.width/2+tlen/2, base);

                for(int i=0; i<3; i++){
                    btn[i].display();
                }
                break;
            case loadData:;
                load.display();
        }
    }

    public void toNormal(){
        status = MenuStatus.Normal;
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
