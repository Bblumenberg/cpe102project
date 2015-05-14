import java.util.Scanner;
import java.io.*;

public abstract class SaveLoad{

    private static final String BGND_KEY = "background";
    private static final int BGND_NUM_PROPERTIES = 4;
    private static final int BGND_NAME = 1;
    private static final int BGND_COL = 2;
    private static final int BGND_ROW = 3;
    
    private static final String MINER_KEY = "miner";
    private static final int MINER_NUM_PROPERTIES = 7;
    private static final int MINER_NAME = 1;
    private static final int MINER_LIMIT = 4;
    private static final int MINER_COL = 2;
    private static final int MINER_ROW = 3;
    private static final int MINER_RATE = 5;
    private static final int MINER_ANIMATION_RATE = 6;
    
    private static final String OBSTACLE_KEY = "obstacle";
    private static final int OBSTACLE_NUM_PROPERTIES = 4;
    private static final int OBSTACLE_NAME = 1;
    private static final int OBSTACLE_COL = 2;
    private static final int OBSTACLE_ROW = 3;
    
    private static final String ORE_KEY = "ore";
    private static final int ORE_NUM_PROPERTIES = 5;
    private static final int ORE_NAME = 1;
    private static final int ORE_COL = 2;
    private static final int ORE_ROW = 3;
    private static final int ORE_RATE = 4;
    
    private static final String SMITH_KEY = "blacksmith";
    private static final int SMITH_NUM_PROPERTIES = 7;
    private static final int SMITH_NAME = 1;
    private static final int SMITH_COL = 2;
    private static final int SMITH_ROW = 3;
    private static final int SMITH_LIMIT = 4;
    private static final int SMITH_RATE = 5;
    private static final int SMITH_REACH = 6;
    
    private static final String VEIN_KEY = "vein";
    private static final int VEIN_NUM_PROPERTIES = 6;
    private static final int VEIN_NAME = 1;
    private static final int VEIN_RATE = 4;
    private static final int VEIN_COL = 2;
    private static final int VEIN_ROW = 3;
    private static final int VEIN_REACH = 5;
    
    private static Scanner save;
        
    public static void load(WorldModel world){

        try{
            save = new Scanner(new FileInputStream("gaia.sav"));
        }
        catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        
        while(save.hasNextLine()){
            String[] info = save.nextLine().split("\\s");
            if(info[0].equals(BGND_KEY)){
                if(info[1].equals("grass")){
                    Background bg = new Background(info[BGND_NAME], ProcessWorld.backgroundImgs);
                    world.setBackground(new Point(Integer.valueOf(info[BGND_COL]), Integer.valueOf(info[BGND_ROW])), bg);
                }
                else{
                    Background bg = new Background(info[BGND_NAME], ProcessWorld.backgroundImgs);
                    bg.currentImg = 1;
                    world.setBackground(new Point(Integer.valueOf(info[BGND_COL]), Integer.valueOf(info[BGND_ROW])), bg);
                }
            }
            
            else{addEntity(world, info);}
        }
        save.close();
    }
    
    private static void addEntity(WorldModel world, String[] info){
        PositionedEntity newEntity = createFromInfo(info);
        if(newEntity != null){world.addEntity(newEntity);}
    }
    
    private static PositionedEntity createFromInfo(String[] info){
        String type = info[0];
        if(type.equals(MINER_KEY)){return createMiner(info);}
        else if(type.equals(VEIN_KEY)){return createVein(info);}
        else if(type.equals(ORE_KEY)){return createOre(info);}
        else if(type.equals(SMITH_KEY)){return createSmith(info);}
        else if(type.equals(OBSTACLE_KEY)){return createObstacle(info);}
        else{return null;}
    }
    
    private static MinerNotFull createMiner(String[] info){
        MinerNotFull miner = new MinerNotFull(info[MINER_NAME], ProcessWorld.minerImgs, Integer.valueOf(info[MINER_LIMIT]), new Point(Integer.valueOf(info[MINER_COL]), Integer.valueOf(info[MINER_ROW])), Integer.valueOf(info[MINER_RATE]), Integer.valueOf(info[MINER_ANIMATION_RATE]));
        return miner;
    }
    
    private static Vein createVein(String[] info){
        Vein vein = new Vein(info[VEIN_NAME], ProcessWorld.veinImgs, Integer.valueOf(info[VEIN_RATE]), new Point(Integer.valueOf(info[VEIN_COL]), Integer.valueOf(info[VEIN_ROW])), Integer.valueOf(info[VEIN_REACH]));
        return vein;
    }
    
    private static Ore createOre(String[] info){
        Ore ore = new Ore(info[ORE_NAME], ProcessWorld.oreImgs, new Point(Integer.valueOf(info[ORE_COL]), Integer.valueOf(info[ORE_ROW])), Integer.valueOf(info[ORE_RATE]));
        return ore;
    }
    
    private static Blacksmith createSmith(String[] info){
        Blacksmith smith = new Blacksmith(info[SMITH_NAME], ProcessWorld.smithImgs, new Point(Integer.valueOf(info[SMITH_COL]), Integer.valueOf(info[SMITH_ROW])), Integer.valueOf(info[SMITH_LIMIT]), Integer.valueOf(info[SMITH_RATE]), Integer.valueOf(info[SMITH_REACH]));
        return smith;
    }
    
    private static Obstacle createObstacle(String[] info){
        Obstacle obstacle = new Obstacle(info[OBSTACLE_NAME], ProcessWorld.obstacleImgs, new Point(Integer.valueOf(info[OBSTACLE_COL]), Integer.valueOf(info[OBSTACLE_ROW])));
        return obstacle;
    }
}






















