package Window;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * Created by USER on 2017/6/8.
 */
public class MessageBox {
       PApplet parent;
       Button yesButton;
       Button noButton;
       private PVector pos;
       private float width;
       private float height;
       private String title;

       public MessageBox(PApplet parent, float x, float y, float wid, float height, String title) {
            this.parent = parent;
            pos = new PVector(x, y);
            this.title = title;
            this.width = wid;
            this.height = height;

            float x_interval = wid / 20;
            float y_interval = height / 20;
            float buttonWid = (width - x_interval*3) / 2;
            float buttonHei = (height*2/5 - y_interval);
            yesButton = new Button(parent, pos.x + x_interval, pos.y + height*3/5, buttonWid, buttonHei, "YES");
            noButton  = new Button(parent, pos.x + x_interval*2 + buttonWid, pos.y + height*3/5, buttonWid, buttonHei, "NO");
       }

       public void display(String message) {
            parent.fill(0, 0, 0);
            parent.rect(pos.x, pos.y, width, height, height/10);
            float y_interval = height / 20;
            //show title
            parent.textAlign(parent.CENTER, parent.BOTTOM);
            parent.textSize(height/5 - y_interval);
            parent.fill(255, 255, 255);
            parent.text(title, pos.x + width/2, pos.y + height/5 - y_interval);
            //show message
            parent.textSize(height*1/5 - y_interval);
            parent.text(message, pos.x + width/2, pos.y + height*3/5 - y_interval);
            // show button
            yesButton.display();
            noButton.display();
       }

       public int checkMousePressed() {
              if (yesButton.checkMouseAt()) return 2;
              else if(noButton.checkMouseAt()) return 1;
              else return 0;
       }

}
