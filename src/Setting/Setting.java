package Setting;

/**
 * Created by Rober on 2017/5/5.
 */
public class Setting {
    /*              map             */
    public static int BlockNumWidth = 40*5;
    public static int BlockNumHeight= 41*4;
    public static int BlockSize = 60;

    public static int ScreenWidthNum = 14;
    public static int ScreenHeightNum = 10;

    public static int HeightSpaceNum = 4;
    public static int HeightMapNum = BlockNumHeight - HeightSpaceNum;
    public static int WidthMapNum = BlockNumWidth;
    public static int GameWidth = ScreenWidthNum*BlockSize;
    public static int GameHeight = ScreenHeightNum*BlockSize;

    /*              block            */
    public static int TypeNum = 3;

    public static int SoilLevel = 1;
    public static int RockLevel = 2;
    public static int GoldLevel = 3;
    public static int WallLevel = 100;


    /*              Mine            */
    public static int MineNum = 6+1;
    public static String[] MineName = {"", "Soil", "Wood", "Coal", "Iron", "Gold", "Diamond"};
    public static int[] MineWeight =  { 0,      1,      1,      2,       2,    3,          4};
    final public static int SoilId    = 1;
    final public static int WoodId    = 2;
    final public static int CoalId    = 3;
    final public static int IronId    = 4;
    final public static int GoldId    = 5;
    final public static int DiamondId = 6;


    /*              Item            */
    public static int ItemNum =  4;
    public static String[] ItemName = { "Ladder",  "" };

}
