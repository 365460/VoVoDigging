package Battle;

import Window.MessageBox;
import gifAnimation.*;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Created by USER on 2017/6/4.
 */
public class BattleFight {
       private PApplet parent;
       private PImage bg;
       private static PImage loseImg;
       private static PImage winImg;
       private static Gif checkImg;
       Gif waitImg;
       private MessageBox message;
       private int atkCnt;
       private int defCnt;
       private FightState state;
       public Timer timer;

       int finish = 0;


       public BattleFight(PApplet parent) {
              this.parent = parent;
              this.bg = parent.loadImage("img/fight.jpg");
              BattleFight.loseImg = parent.loadImage("img/gamelose.jpg");
              BattleFight.winImg  = parent.loadImage("img/gamewin.jpg");
              BattleFight.checkImg = new Gif(parent, "img/checking.gif");
              waitImg  = new Gif(parent, "img/a.gif");
              message = new MessageBox(parent, (BattleSetting.backgroundWidth-BattleSetting.windowWidth)/2, (BattleSetting.backgroundHeight-BattleSetting.windowHeight)/2, BattleSetting.windowWidth, BattleSetting.windowHeight, "Result");
       }

       public void reset() {
              atkCnt = 0;
              defCnt = 0;
              finish = 0;
              state = FightState.WAIT;
              timer = new Timer(parent, BattleSetting.fightTime);
              timer.start();
              waitImg.play();
              calResult();
       }

       public void calResult() {
              for (int i = 0; i < Battle.totalAtk; i++) {
                     int tmp = (int)(Math.random()*101);
                     if(tmp < 60) atkCnt++;
              }

              for (int i = 0; i < Battle.totalDef; i++) {
                     int tmp = (int)(Math.random()*101);
                     if(tmp < Battle.totalLuk) defCnt++;
              }
       }

       public int fightResult() {
              return defCnt - atkCnt;
       }

       public void checkMousePressed(){
              int check = message.checkMousePressed();
              if (state == FightState.CHECK && (check == 1 || check == 2)) {
                     finish = 1;
                     checkImg.pause();
//                     if (atkCnt > defCnt) state = FightState.LOSE;
//                     else state = FightState.WIN;
//                     timer = new Timer(parent, 5);
//                     timer.start();
              }
       }

       public int display(){
              if (state == FightState.WAIT) {
                     parent.image(waitImg, 0, 0, BattleSetting.backgroundWidth, BattleSetting.backgroundHeight);
                     if (timer.isTimeOver()) {
                            state = FightState.CHECK;
                            waitImg.pause();
                            checkImg.play();
                     }
              } else if (state == FightState.CHECK) {
                     parent.image(checkImg, 0, 0, BattleSetting.backgroundWidth, BattleSetting.backgroundHeight);
                     if(defCnt > atkCnt)
                            message.display("You are safe this round.");
                     else
                            message.display("you got "+(atkCnt-defCnt)+" damage");
              } else if (state == FightState.WIN) {
                     if (timer.getRemainTime() == 0) return 1;
              } else {
                     parent.image(loseImg, 0, 0, BattleSetting.backgroundWidth, BattleSetting.backgroundHeight);
                     if (timer.getRemainTime() == 0) return 1;
              }
              return finish;
       }

}

enum FightState{
       WAIT, CHECK, LOSE, WIN;
}