package Setting;

import Game.*;
import processing.core.PApplet;
import processing.core.PVector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Rober on 2017/6/7.
 */
public class Saving {
    PApplet par;
    Game game;

    Button[] btn;

    boolean[] hasdata;
    String[]  cont;

    int width = 180;
    int height = 250;

    public Saving(PApplet par, Game game){
        this.par  = par;
        this.game = game;

        hasdata = new boolean[3];
        cont    = new String[3];
        btn     = new Button[3];

        loadKeep();
    }

    public void loadKeep(){

        try{

            FileReader f = new FileReader("keep/keep");
            BufferedReader br = new BufferedReader(f);

            for(int i=0; i<3; i++){
                String s1 = br.readLine();
                cont[i] = br.readLine();

                if(s1.equals("1")){
                    hasdata[i] = true;
                }
                else{
                    hasdata[i] = false;
                    cont[i] = "+";
                }
            }
        }catch(IOException e){}

        int stx = par.width/2 - width/2, sty = par.height/2 - height/2;
        int btnH = 40, gapH = 25;
        for(int i=0; i<3; i++){
            btn[i] = new Button(par, new PVector(stx+30,sty+i*btnH+gapH*(i+1)+10), 120, btnH, cont[i]);
        }
    }

    public void display(){
        for(int i=0; i<3; i++){
            btn[i].setFont(10);
            btn[i].display();
        }
    }

    public void mousePressed(){
        for(int i=0; i<3; i++){
            if(btn[i].isHover()){
                game.saveGame(i+1);
                loadKeep();
                break;
            }
        }
    }
}
