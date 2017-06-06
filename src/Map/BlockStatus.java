package Map;

/**
 * Created by Rober on 2017/6/5.
 */
public enum BlockStatus{
        NORMAL(1),
        LADDER(2),
        DIGGING(3),
        FIN(4),
        EMPTY(5);

        private final int value;

        private BlockStatus(int value){
            this.value = value;
        }

        public int getValue(){
            return value;
        }

        static BlockStatus toStatus(int v){
            switch (v){
                case 1:
                    return NORMAL;
                case 2:
                    return LADDER;
                case 4:
                    return FIN;
                case 5:
                    return EMPTY;
                default:
                    return EMPTY;
            }
        }
};
