package Window;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * Created by USER on 2017/6/8.
 */
public class Button {
       PApplet parent;
       PVector pos;
       private float width;
       private float height;
       private String message;

       Button(PApplet parent, float x, float y, float wid, float hei, String mes){
           this.parent = parent;
           pos = new PVector(x, y);
           width = wid;
           height = hei;
           message = mes;
       }

       public void display(){
           float x_interval = width / 20;
           float y_interval = height / 20;
           parent.fill(255, 255, 255);
           parent.rect(pos.x, pos.y, width, height);

           parent.fill(0, 0, 0);
           parent.textAlign(parent.CENTER, parent.CENTER);
           parent.textSize(height*2/5 - y_interval);
           parent.text(message, pos.x + width/2, pos.y + height/2);
       }

       public boolean checkMouseAt(){
           if(parent.mouseX < pos.x || parent.mouseX > pos.x + width) return false;
           else if(parent.mouseY < pos.y || parent.mouseY > pos.y + height) return false;
           else return true;
       }

}
