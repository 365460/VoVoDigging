package Setting;

/**
 * Created by Rober on 2017/5/5.
 */
public class Setting {
    public static int BlockNumWidth = 40*10;
    public static int BlockNumHeight= 41*10;
    public static int BlockSize = 60;

    public static int ScreenWidthNum = 14;
    public static int ScreenHeightNum = 10;

    public static int HeightSpaceNum = 4;
    public static int HeightMapNum = BlockNumHeight - HeightSpaceNum;
    public static int WidthMapNum = BlockNumWidth;
    public static int GameWidth = ScreenWidthNum*BlockSize;
    public static int GameHeight = ScreenHeightNum*BlockSize;

}
