public static abstract class SaveLoad{

    private static final int BGND_KEY = 'background';
    private static final int BGND_NUM_PROPERTIES = 4;
    private static final int BGND_NAME = 1;
    private static final int BGND_COL = 2;
    private static final int BGND_ROW = 3;
    
    private static final int MINER_KEY = 'miner';
    private static final int MINER_NUM_PROPERTIES = 7;
    private static final int MINER_NAME = 1;
    private static final int MINER_LIMIT = 4;
    private static final int MINER_COL = 2;
    private static final int MINER_ROW = 3;
    private static final int MINER_RATE = 5;
    private static final int MINER_ANIMATION_RATE = 6;
    
    private static final int OBSTACLE_KEY = 'obstacle';
    private static final int OBSTACLE_NUM_PROPERTIES = 4;
    private static final int OBSTACLE_NAME = 1;
    private static final int OBSTACLE_COL = 2;
    private static final int OBSTACLE_ROW = 3;
    
    private static final int ORE_KEY = 'ore';
    private static final int ORE_NUM_PROPERTIES = 5;
    private static final int ORE_NAME = 1;
    private static final int ORE_COL = 2;
    private static final int ORE_ROW = 3;
    private static final int ORE_RATE = 4;
    
    private static final int SMITH_KEY = 'blacksmith';
    private static final int SMITH_NUM_PROPERTIES = 7;
    private static final int SMITH_NAME = 1;
    private static final int SMITH_COL = 2;
    private static final int SMITH_ROW = 3;
    private static final int SMITH_LIMIT = 4;
    private static final int SMITH_RATE = 5;
    private static final int SMITH_REACH = 6;
    
    private static final int VEIN_KEY = 'vein';
    private static final int VEIN_NUM_PROPERTIES = 6;
    private static final int VEIN_NAME = 1;
    private static final int VEIN_RATE = 4;
    private static final int VEIN_COL = 2;
    private static final int VEIN_ROW = 3;
    private static final int VEIN_REACH = 5;

    private Scanner save = new Scanner("gaia.sav");
    
    public static void load(WorldModel world){
        while(save.hasNext()){
            
        }
        save.close()
    }
}