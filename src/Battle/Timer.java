package Battle;


import processing.core.PApplet;

/**
 * Created by USER on 2017/6/6.
 */
public class Timer extends Thread{
       PApplet parent;
       int remainTime;
       public Timer(PApplet parent, int time) {
              this.parent = parent;
              this.remainTime = time;
       }

       @Override
       public void run() {
              while (remainTime > 0) {
                     try {
                            remainTime--;
                            Thread.sleep(1000);
                     } catch (Exception e) {
                            System.out.println("Timer GG"+e);
                     }
              }
       }

       public void display (float x, float y, float boxW, float boxH) {
              parent.fill(0, 0, 0, 134);
              parent.rect(x, y, boxW, boxH);
              float textSz = boxW/2 - BattleSetting.leftSpace*6;
              parent.textSize(textSz);
              parent.textAlign(parent.CENTER, parent.CENTER);
              parent.fill(255, 255, 255);
              parent.text("00", x + boxW/4, y + boxH/2);
              parent.text(":", x + boxW/2, y + boxH/2 - 2);
              String str = String.valueOf(remainTime);
              if(remainTime < 10) str = "0" + str;
              parent.text(str, x + boxW*3/4, y + boxH/2);
       }

       public boolean isTimeOver() {
              return remainTime == 0;
       }

       public int getRemainTime(){
              return remainTime;
       }
}
