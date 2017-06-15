package Menu;

import Gui.*;
import Gui.Button;
import com.sun.deploy.net.proxy.pac.PACFunctions;
import processing.core.PApplet;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import Game.*;
import processing.core.PImage;
import processing.core.PVector;

/**
 * Created by Rober on 2017/6/6.
 */
public class Loader {
    PApplet par;
    Game game;
    Menu menu;
    Frame[] frame;
    Button btnback;
    boolean[] data;
    String[] title;

    public Loader(PApplet par, Game game, Menu menu){
        this.par = par;
        this.game = game;
        this.menu = menu;
        data = new boolean[3];
        title = new String[3];
        frame = new Frame[3];
        btnback = new Button(par, new PVector(600,500), 150,50, "Back");

//        loadKeep();
    }

    public void loadKeep(){
        for(int i=0; i<3; i++) data[i] = false;
        try{
            FileReader f = new FileReader("keep/keep");

            BufferedReader br = new BufferedReader(f);

            for(int i=0; i<3; i++){
                String exist = br.readLine();
                title[i] = br.readLine();
                if(exist.equals("1"))
                    data[i] = true;
                else title[i] = "No Data";
            }
        }
        catch(IOException e){

        }

        int st = 50;
        int imglen = 200;
        for(int i=0; i<3; i++){
            PImage img = null;
            if(data[i]) img = par.loadImage("keep/K"+(i+1)+"/img.png");
            frame[i] = new Frame(par,img, new PVector(st,150), title[i] , imglen);
            st = st+imglen+50;
        }
    }

    public void display(){

        for(int i=0; i<3; i++){
            frame[i].display();
        }
        btnback.display();
    }

    public void mousePressed(){
        for(int i=0; i<3; i++){
            if(data[i] && frame[i].isHover()){
                game.initOldGame("keep/K"+(i+1)+"/");
            }
        }

        if(btnback.isHover()){
            menu.toNormal();
        }
    }
}
