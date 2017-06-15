package Setting;

import processing.core.PApplet;

/**
 * Created by Rober on 2017/5/5.
 */
public class Setting {
    /*              map             */
    public static int BlockNumWidth = 50; // 200
    public static int BlockNumHeight= 50+4; // 160
    public static int BlockSize = 60;

    public static int ScreenWidthNum = 14;
//    public static int ScreenWidthNum = 7;
    public static int ScreenHeightNum = 10;

    public static int HeightSpaceNum = 4;
    public static int HeightMapNum   = BlockNumHeight - HeightSpaceNum;
    public static int WidthMapNum    = BlockNumWidth;
    public static int GameWidth      = ScreenWidthNum*BlockSize;
    public static int GameHeight     = ScreenHeightNum*BlockSize;
    public static int MapWidth       = BlockNumWidth*BlockSize;
    public static int MapHeight      = BlockNumHeight*BlockSize;

    public static int PosShop = 150;
    public static int PosUpgrade = 450;
    public static int PosGate = 650;
    public static int buildWidthNum = 2;

    /*              block            */
    public static int TypeNum = 3;

    public static int SoilLevel    = 1;
    public static int WoodLevel    = 1;
    public static int CoalLevel    = 3;
    public static int RockLevel    = 2;
    public static int GoldLevel    = 3;
    public static int DiamondLevel = 5;
    public static int WallLevel    = 100;

    /*              Mine            */
    public static int MineNum = 7;
    public static String[] MineName = {"", "Soil", "Wood", "Coal", "Iron", "Gold", "DO", "Wall"};
    public static int[] MineWeight =  { 0,      1,      1,      2,       2,    3,     4,   1000};
    final public static int SoilId    = 1;
    final public static int WoodId    = 2;
    final public static int CoalId    = 3;
    final public static int IronId    = 4;
    final public static int GoldId    = 5;
    final public static int DiamondId = 6;
    final public static int WallId    = 7;

                /* Mine-img */
        final public static String SolidIcon  = "image/soilIcon.png";
        final public static String WoodIcon   = "image/woodIcon.png";
        final public static String CoalIcon   = "image/coalIcon.png";
        final public static String IronIcon   = "image/iron.png";
        final public static String GoldIcon   = "image/gold.png";
        final public static String DiamonIcon = "image/dim.png";
        final public static String[] MIneImage = {"", SolidIcon, WoodIcon, CoalIcon, IronIcon, GoldIcon, DiamonIcon};

    /*              Item            */
    public static int ItemNum =  5;
    public static String[] ItemName = {"", "hand", "IronTool", "GoldTool", "DimTool", "Ladder" };
    public static int[] ItemUsageCount = { 0,   0,         30,         20,       30,       20 };
    public static int[] ItemLevel =      { 0,   2,          4,          5,        5,  0 };

   final public static int Nothing     = 0;
   final public static int ToHandId    = 1;
   final public static int ToIronId    = 2;
   final public static int ToGoldId    = 3;
   final public static int ToDiamondId = 4;
   final public static int ToLadderId  = 5;

            /* item-img */
        final public static String  ToHandIcon    = "image/handTool.png";
        final public static String  ToIronIcon    = "image/ironTool.png";
        final public static String  ToGoldIcon    = "image/goldTool.png";
        final public static String  ToDiamondIcon = "image/dimTool.png";
        final public static String  ToLadder      = "image/ladder.png";
        final public static String[] ToolImage = {"", ToHandIcon, ToIronIcon, ToGoldIcon, ToDiamondIcon, ToLadder};

    /*    Reminder  */
    public static String ReNoLadder = "you have not enough ladder.";

    /*  animation*/
    public static int DiggingTime = 700;
    public static float MovingTime  = 0.2f;
    public static float MoveV = 20;

    public static int getMoveV(PApplet par) {
        return 30;
    }


}
