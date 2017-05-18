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


    public static int WoodId    = 1;
    public static int CoalId    = 2;
    public static int IronId    = 3;
    public static int GoldId    = 4;
    public static int DiamondId = 5;
    public static int SoilId    = 6;
}
