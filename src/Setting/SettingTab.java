package Setting;

import processing.core.PApplet;
import Game.*;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

/**
 * Created by Rober on 2017/6/6.
 */
public class SettingTab {
    PApplet par;
    PImage bg;
    Game game;

    int width = 180;
    int height = 250;
    Button btn[];
    String cont[] = {"Save", "Menu", "Exit"};

    SettingStatus status = SettingStatus.NORMAL;

    Saving save;

    public SettingTab(PApplet par, Game game){
        this.par  = par;
        this.game = game;

        save = new Saving(par, game);

        bg = par.loadImage("image/settingbg.png");
        btn = new Button[3];

        int stx = par.width/2 - width/2, sty = par.height/2 - height/2;
        int btnH = 40, gapH = 25;
        for(int i=0; i<3; i++){
            btn[i] = new Button(par, new PVector(stx+30,sty+i*btnH+gapH*(i+1)+10), 120, btnH, cont[i]);
        }
    }

    public void display(){
        par.imageMode(PConstants.CENTER);
        par.image(bg, par.width/2, par.height/2,  width ,height);
        par.imageMode(PConstants.CORNER);

        switch (status){
            case NORMAL:
                for(int i=0; i<3; i++){
                    btn[i].display();
                }
                break;

            case SAVING:
                save.display();
                break;
        }

    }

    public void mousePressed(){

        switch (status){
            case NORMAL:
                if(btn[0].isHover()){
                    save.loadKeep();
                    status = SettingStatus.SAVING;
                }
                else if(btn[1].isHover()){
                    game.goToMenu();
                }
                else if(btn[2].isHover()){
                    par.exit();
                }
                break;
            case SAVING:
                save.mousePressed();
                break;
        }
    }

    public void open(){
        for(int i=0; i<3; i++){
            btn[i].ratio = 1;
        }
        status = SettingStatus.NORMAL;
    }

    public void close(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    for(int i=0; i<3; i++){
                        btn[i].toDie();
                        Thread.sleep(100);
                    }
                    Thread.sleep(200);
                    game.goToGround(false);
                } catch (Exception e){}
            }
        });
        thread.start();
    }

    public void keyPressed(int keyCode){
        if(keyCode == 27){
            if(status==SettingStatus.NORMAL) close();
            else status = SettingStatus.NORMAL;
        }
    }
}
enum SettingStatus{
    NORMAL,
    SAVING
}
