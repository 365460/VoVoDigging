package Battle;

import Reminder.*;
import Bag.BagMine;
import Window.MessageBox;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by USER on 2017/5/18.
 */
public class BattleShop {
    private PApplet parent;
    private int shopListSum;
    private int itemListSum;
    private int shelfWidth;
    private int shelfHeight;
    private int bgWidth;
    private int bgHeight;

    private ArrayList<DefensiveItem> defItemList;
    private ArrayList<Shelf> shopList;
    private PImage background;
    private boolean[] visit;
    private BagMine bag;
    private ShopState shopState;
    private MessageBox checkBox;
    private Shelf targetShelf;

    private void generateList(int n){
        int []req = new int[6];
        for (int i = 0; i < n; i++) {
            int luckyNum = (int)(Math.random()*11);
            int defNum = (int)(Math.random()*30);
            for(int j = 0; j < 6; j++) req[j] = (int)(Math.random()*3);
            DefensiveItem tmp = new DefensiveItem(parent, luckyNum, defNum, i, req, BattleSetting.itemName[i]);
            defItemList.add(tmp);
        }
    }

    public BattleShop(PApplet parent, BagMine bag, int maxItem, int bgWidth, int bgHeight){
        this.bag = bag;
        this.parent = parent;
        itemListSum = maxItem;
        shopListSum = 6;
        shopState = ShopState.SELECT;
        background = parent.loadImage("img/background.jpg");
        visit = new boolean[maxItem];
        defItemList = new ArrayList<DefensiveItem>();
        shopList = new ArrayList<Shelf>();
        generateList(itemListSum);
        this.bgHeight = bgHeight;
        this.bgWidth = bgWidth;
        shelfHeight = (bgHeight - 70) / (shopListSum);
        shelfWidth = bgWidth * 2 / 3 - 10;
        checkBox = new MessageBox(parent, (BattleSetting.backgroundWidth-BattleSetting.windowWidth)/2, (BattleSetting.backgroundHeight-BattleSetting.windowHeight)/2, BattleSetting.windowWidth, BattleSetting.windowHeight, "CHECK");
        updateShop();
    }

    public void updateShop(){
        shopList.clear();
        Arrays.fill(visit, false);
        Shelf tmpShelf;
        for (int i = 0; i < shopListSum; ) {
            int itemNum = (int)(Math.random() * itemListSum);
            if (visit[itemNum] == false) {
                tmpShelf = new Shelf(parent, defItemList.get(itemNum), shelfWidth, shelfHeight, BattleSetting.leftSpace, BattleSetting.leftSpace + i * (shelfHeight + 10));
                shopList.add(tmpShelf);
                visit[itemNum] = true;
                i++;
            }
        }
        System.out.println(shopList.size());
    }

    private void showTotalValue(float x, float y, float boxW, float boxH) {
        parent.fill(244, 66, 75, 200);
        parent.rect(x, y, boxW, boxH);
        float textSz = (boxH - BattleSetting.heightSpace*4)/3;
        parent.textAlign(parent.BOTTOM, parent.LEFT);
        parent.textSize(textSz);
        parent.fill(0, 0, 0);
        parent.text("LUK: "+ Battle.totalLuk, x, y + textSz + BattleSetting.heightSpace);
        parent.text("DEF: "+ Battle.totalDef, x, y + textSz*2 + BattleSetting.heightSpace*2);
        parent.text("ENEMY ATK: "+ Battle.totalAtk, x, y + textSz*3 + BattleSetting.heightSpace*3);
    }

    private void showBag(float x, float y, float bagW, float bagH) {
            float x_interval = 5;
            float y_interval = 5;
            float gridW = (bagW - x_interval*3)/2;
            float gridH = (bagH - y_interval*4)/3;
            parent.fill(255,255,255);
            parent.rect(x, y, bagW, bagH);
            parent.textAlign(parent.BOTTOM, parent.LEFT);
            parent.textSize(gridH/4);

            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 2; c++) {
                    float gridX = x + gridW*c + x_interval*(c+1);
                    float gridY = y + gridH*r + y_interval*(r+1);
                    parent.fill(206, 150, 101);
                    parent.rect(gridX, gridY, gridW, gridH);
                    parent.image(DefensiveItem.mine[r*2 + c], gridX + x_interval, gridY + y_interval, gridW - x_interval*2, gridH - y_interval*2);
                    parent.fill(0, 0, 0);
                    parent.text(bag.getNum(r*2+c), gridX, gridY + gridH - 2);
                }
            }
    }

    public void buyItem() throws Reminder{
         if(shopState == ShopState.SELECT){
             for(Shelf shelf : shopList)
             {
                 if (shelf.checkMouseOnShelf()) {
                     targetShelf = shelf;
                     shopState = ShopState.CHECK;
                     break;
                 }
             }
         }
         else{
             int isBuy = checkBox.checkMousePressed();
             if (isBuy >= 1)//no = 1, yes = 2, else = 0
             {
                 shopState = ShopState.SELECT;
                 if(isBuy == 2) {
                     if (targetShelf.isValidBuy(bag.getMine())) {
                         Battle.totalDef += targetShelf.getItem().getDefValue();
                         Battle.totalLuk += targetShelf.getItem().getLuckyValue();
                         bag.takeMine(targetShelf.getItem().getRequire());
                     } else {
                         parent.textAlign(parent.CENTER, parent.CENTER);
                         parent.textSize(BattleSetting.backgroundWidth/2);
                         throw new Reminder(parent,"You can't buy it");
                     }
                 }
             }
         }
    }

    public void display(){
        parent.image(background, 0, 0, bgWidth, bgHeight);

        for(int i = 0; i < shopList.size(); i++){
            shopList.get(i).display();
        }

        showBag(  BattleSetting.backgroundWidth*2/3 + BattleSetting.leftSpace, BattleSetting.backgroundHeight*5/9 + BattleSetting.heightSpace, BattleSetting.backgroundWidth/3 - BattleSetting.leftSpace*2, BattleSetting.backgroundHeight*4/9 - BattleSetting.heightSpace*2);
        showTotalValue(BattleSetting.backgroundWidth*2/3 + BattleSetting.leftSpace,   BattleSetting.backgroundHeight/3 + BattleSetting.heightSpace, BattleSetting.backgroundWidth/3 - BattleSetting.leftSpace*2, BattleSetting.backgroundHeight*2/9 - BattleSetting.heightSpace*2);

        if(shopState == ShopState.SELECT){
            for(Shelf tmp : shopList){
                if(tmp.checkMouseOnShelf()) tmp.showValueWindos();
            }
        }
        else{
            checkBox.display("Buy " + targetShelf.getItem().getName() + "?");
        }
    }
}

enum ShopState{
     SELECT, CHECK;
}
