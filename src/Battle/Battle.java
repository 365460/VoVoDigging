package Battle;

import Bag.BagMine;
import Reminder.Reminder;
import processing.core.PApplet;
import processing.core.PImage;


/**
 * Created by USER on 2017/5/18.
 */
public class Battle {
       private BattleState state;
       private BattleShop shop;
       private BattleFight fight;
       private PApplet parent;
       private Timer timer;
       private BagMine bag;
       static int totalDef;
       static int totalLuk;
       static int totalAtk;
       private int nightCnt;

       public Battle(PApplet parent, BagMine bag) {
              this.bag = bag;
              this.parent = parent;
              timer = new Timer(parent, BattleSetting.shopTime);
              setup();
       }

       public void reset() {
              nightCnt++;
              Battle.totalAtk = nightCnt*20;
              state = BattleState.SHOP;
              Battle.totalDef = 0;
              Battle.totalLuk = 0;
           parent.noStroke();
              timer = new Timer(parent, BattleSetting.shopTime);
              shop.updateShop();
              timer.start();
       }

       public void checkMouseClicked()throws Reminder {
              if (state == BattleState.SHOP) {
                     shop.buyItem();
              }
              else {
                     fight.checkMousePressed();
              }
       }

       public void setup(){
              shop = new BattleShop(parent, bag, 9, 840, 600);
              fight = new BattleFight(parent);
              DefensiveItem.mine = new PImage[6];
              for (int i = 0; i < 6; i++)  DefensiveItem.mine[i] = parent.loadImage("img/mine" + i + ".png");
              nightCnt = 0;
       }

       public int display() {
              if (state == BattleState.SHOP) {
                     shop.display();
                     timer.display(BattleSetting.backgroundWidth*2/3 + BattleSetting.leftSpace, BattleSetting.heightSpace, BattleSetting.backgroundWidth/3 - BattleSetting.leftSpace*2, BattleSetting.backgroundHeight/3 - BattleSetting.heightSpace*2);
                     if (timer.isTimeOver()) {
                            state = BattleState.FIGHT;
                            fight.reset();
                     }
              }
              else {
                     return fight.display();
              }
              return 0;
       }

       public int battleResult() {
              return fight.fightResult();
       }
    public void checkKeyPressed() {
        if(state == BattleState.SHOP) {
            if (parent.key == 'g') {
                System.out.println("key PREEEE");
                state = BattleState.FIGHT;
                fight.reset();
            }
        }
    }


}

enum BattleState{
       SHOP, FIGHT;
}
