import java.util.List;
import java.util.ArrayList;
import processing.core.*;

public class ProcessWorld extends PApplet{

    private static final int ANIMATION_TIME = 100;

    private static final int WORLD_WIDTH_SCALE = 2;
    private static final int WORLD_HEIGHT_SCALE = 2;
    
    private static final int SCREEN_WIDTH_PX = 640;
    private static final int SCREEN_HEIGHT_PX = 480;
    private static final int TILE_WIDTH_PX = 32;
    private static final int TILE_HEIGHT_PX = 32;
    private static final int SCREEN_HEIGHT = SCREEN_HEIGHT_PX/TILE_HEIGHT_PX;
    private static final int SCREEN_WIDTH = SCREEN_WIDTH_PX/TILE_WIDTH_PX;
    private static final int WORLD_HEIGHT = SCREEN_HEIGHT * WORLD_HEIGHT_SCALE;
    private static final int WORLD_WIDTH = SCREEN_WIDTH * WORLD_WIDTH_SCALE;
    
    private static WorldModel world;
    private Entity[][] worldView;
    public Point SCREEN_START;
    
    private long next_time;
    
    public static List<PImage> backgroundImgs;
    public static List<PImage> obstacleImgs;
    public static List<PImage> veinImgs;
    public static List<PImage> oreImgs;
    public static List<PImage> quakeImgs;
    public static List<PImage> blobImgs;
    public static List<PImage> smithImgs;
    public static List<PImage> minerImgs;
    
    public void loadImages(){
        ImageLoad masterImageLoad = new ImageLoad();
        backgroundImgs = masterImageLoad.backgroundImgs;
        obstacleImgs = masterImageLoad.obstacleImgs;
        veinImgs = masterImageLoad.veinImgs;
        oreImgs = masterImageLoad.oreImgs;
        quakeImgs = masterImageLoad.quakeImgs;
        blobImgs = masterImageLoad.blobImgs;
        smithImgs = masterImageLoad.smithImgs;
        minerImgs = masterImageLoad.minerImgs;
        
        /*backgroundImgs.add(loadImage("images/grass.bmp"));
        backgroundImgs.add(loadImage("images/rock.bmp"));
        
        obstacleImgs.add(loadImage("images/obstacle.bmp"));
        
        veinImgs.add(loadImage("images/vein.bmp"));
        
        oreImgs.add(loadImage("images/ore.bmp"));
        
        for(Integer i = 1; i <= 6; i++){
            quakeImgs.add(loadImage("images/quake" + i.toString() + ".bmp"));
        }
        
        for(Integer i = 1; i <= 12; i++){
            blobImgs.add(loadImage("images/blob" + i.toString() + ".bmp"));
        }
        
        smithImgs.add(loadImage("images/blacksmith.bmp"));
        
        for(Integer i = 1; i <= 5; i++){
            minerImgs.add(loadImage("images/miner" + i.toString() + ".bmp"));
        }*/
    }

    private WorldModel setupWorld(){
        world = new WorldModel(WORLD_HEIGHT, WORLD_WIDTH);
        return world;
    }
    
    public static WorldModel getWorld(){return world;}

    public void setup(){
        setupWorld();
        loadImages();
        size(SCREEN_WIDTH_PX, SCREEN_HEIGHT_PX);
        background(color(255,255,255));
        SaveLoad.load(world);
        worldView = new Entity[SCREEN_HEIGHT][SCREEN_WIDTH];
        SCREEN_START = new Point(0,0);
        for(int y = 0; y < (SCREEN_HEIGHT); y++){
            for(int x = 0; x < (SCREEN_WIDTH); x++){
                Point pt = new Point(x, WORLD_HEIGHT - y);
                worldView[y][x]=world.getBackground(pt);
                if(world.getTileOccupant(pt) != null){
                    worldView[y][x] = world.getTileOccupant(pt);
                }
                
            }
        }
        next_time = System.currentTimeMillis() + ANIMATION_TIME;
    }
    
    public void draw(){
        background(color(255,255,255));
        //update worldView and draw appropriate image
        for(int y = 0; y < SCREEN_HEIGHT; y++){
            for(int x = 0; x < SCREEN_WIDTH; x++){
                Point pt = new Point(x + SCREEN_START.getX(), y + SCREEN_START.getY());
                Background bg = world.getBackground(pt);
                if(world.getTileOccupant(pt) != null){
                    Entity e = world.getTileOccupant(pt);
                    worldView[y][x] = e;
                    image(processAlpha(e, bg), x*TILE_WIDTH_PX, y*TILE_HEIGHT_PX);
                }
                else{
                    worldView[y][x] = bg;
                    image(bg.getCurrentImage(), x*TILE_WIDTH_PX, y*TILE_HEIGHT_PX);
                }
            }
        }
        //other stuff
        Animations.AnimateAll(world);
        Actions.doAll();
        Actions.startActions(world);
    }
    
    public void keyPressed(){
        switch(key){
                case 'w':
                    if(SCREEN_START.getY() > 0){
                        SCREEN_START = new Point(SCREEN_START.getX(), SCREEN_START.getY() - 1);
                    }
                    break;
                case 'a':
                    if(SCREEN_START.getX() > 0){
                        SCREEN_START = new Point(SCREEN_START.getX() - 1, SCREEN_START.getY());
                    }
                    break;
                case 's':
                    if(SCREEN_START.getY() < WORLD_HEIGHT - SCREEN_HEIGHT){
                        SCREEN_START = new Point(SCREEN_START.getX(), SCREEN_START.getY() + 1);
                    }
                    break;
                case 'd':
                    if(SCREEN_START.getX() < WORLD_WIDTH - SCREEN_WIDTH){
                        SCREEN_START = new Point(SCREEN_START.getX() + 1, SCREEN_START.getY());
                    }
                    break;
        }
    }
    
    private PImage processAlpha(Entity entity, Background bgnd){
        PImage e = entity.getCurrentImage();
        EasyList<Integer> maskColors = new EasyList<Integer>(-197380);
        e.format = RGB;
        e.loadPixels();
        PImage bg = bgnd.getCurrentImage();
        bg.format = RGB;
        bg.loadPixels();
        PImage img = createImage(32,32, RGB);
        img.loadPixels();
        for(int i = 0; i < img.pixels.length; i++){
            if (maskColors.contains(e.pixels[i])){img.pixels[i] = bg.pixels[i];}
            else{img.pixels[i] = e.pixels[i];}
        }
        img.updatePixels();
        return img;
    }
    
    public static void main(String[] args)
    {
        PApplet.main("ProcessWorld");
    }
}










