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
    public static final int WORLD_HEIGHT = SCREEN_HEIGHT * WORLD_HEIGHT_SCALE;
    public static final int WORLD_WIDTH = SCREEN_WIDTH * WORLD_WIDTH_SCALE;
    
    private static WorldModel world;
    private Entity[][] worldView;
    public Point SCREEN_START;
    
    private OccGrid<Integer> searchOverlay;
    private Point curMousePt;
    private static int magicCount;
    private static int magicLimit;
    public static void increaseMagicLimit(){
        if(magicLimit < 10){
            magicLimit += 1;
            System.out.println("magic limit increased");
        }
    }
    
    private long next_time;
    
    public static List<PImage> backgroundImgs;
    public static List<PImage> overlayImgs;
    public static List<PImage> obstacleImgs;
    public static List<PImage> veinImgs;
    public static List<PImage> magicVeinImgs;
    public static List<PImage> oreImgs;
    public static List<PImage> quakeImgs;
    public static List<PImage> blobImgs;
    public static List<PImage> magicBlobImgs;
    public static List<PImage> wyvernImgs;
    public static List<PImage> smithImgs;
    public static List<PImage> minerImgs;
    
    public void loadImages(){
        ImageLoad masterImageLoad = new ImageLoad();
        backgroundImgs = masterImageLoad.backgroundImgs;
        overlayImgs = masterImageLoad.overlayImgs;
        obstacleImgs = masterImageLoad.obstacleImgs;
        veinImgs = masterImageLoad.veinImgs;
        magicVeinImgs = masterImageLoad.magicVeinImgs;
        oreImgs = masterImageLoad.oreImgs;
        quakeImgs = masterImageLoad.quakeImgs;
        blobImgs = masterImageLoad.blobImgs;
        magicBlobImgs = masterImageLoad.magicBlobImgs;
        wyvernImgs = masterImageLoad.wyvernImgs;
        smithImgs = masterImageLoad.smithImgs;
        minerImgs = masterImageLoad.minerImgs;
    }

    private WorldModel setupWorld(){
        world = new WorldModel(WORLD_HEIGHT, WORLD_WIDTH);
        return world;
    }
    
    public static WorldModel getWorld(){return world;}

    public void setup(){
        setupWorld();
        loadImages();
        searchOverlay = new OccGrid<Integer>(WORLD_WIDTH, WORLD_HEIGHT, 0);
        curMousePt = new Point(0,0);
        magicCount = 0;
        magicLimit = 2;
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
        updateMouse();
        //update worldView and draw appropriate image
        for(int y = 0; y < SCREEN_HEIGHT; y++){
            for(int x = 0; x < SCREEN_WIDTH; x++){
                Point pt = new Point(x + SCREEN_START.getX(), y + SCREEN_START.getY());
                Background bg = world.getBackground(pt);
                if(searchOverlay.getCell(pt) == 3){
                    image(overlayImgs.get(2), x*TILE_WIDTH_PX, y*TILE_HEIGHT_PX);
                }
                else if(world.getTileOccupant(pt) != null){
                    Entity e = world.getTileOccupant(pt);
                    worldView[y][x] = e;
                    image(processAlpha(e, bg), x*TILE_WIDTH_PX, y*TILE_HEIGHT_PX);
                }
                else if(searchOverlay.getCell(pt) == 2){
                    image(overlayImgs.get(1), x*TILE_WIDTH_PX, y*TILE_HEIGHT_PX);
                }
                else if(searchOverlay.getCell(pt) == 1){
                    image(overlayImgs.get(0), x*TILE_WIDTH_PX, y*TILE_HEIGHT_PX);
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
    
    private void updateMouse(){
        searchOverlay = new OccGrid<Integer>(WORLD_WIDTH, WORLD_HEIGHT, 0);
        curMousePt = new Point((mouseX/TILE_WIDTH_PX) + SCREEN_START.getX(), (mouseY/TILE_HEIGHT_PX) + SCREEN_START.getY());
        Entity e = world.getTileOccupant(curMousePt);
        if(e instanceof Miner){
            Miner miner = (Miner) e;
            this.searchOverlay = miner.getOverlay();
        }else if(e instanceof OreBlob){
            OreBlob blob = (OreBlob) e;
            this.searchOverlay = blob.getOverlay();
        }else if(e instanceof Wyvern){
            Wyvern wyvern = (Wyvern) e;
            this.searchOverlay = wyvern.getOverlay();
        }
    }
    
    public void mouseClicked(){
        if(magicCount < magicLimit){
            boolean enoughSpace = true;
            for(int y = -2; y <= 2; y++){
                for(int x = -2; x <= 2; x++){
                    if(!world.openTile(new Point(curMousePt.getX() + x, curMousePt.getY() + y))){enoughSpace = false;}
                }
            }
            if(enoughSpace){
                magicCount += 1;
                world.addEntity(new MagicQuake(curMousePt, "vein"));
            }else if(world.openTile(curMousePt)){world.addEntity(new Quake("quake", ProcessWorld.quakeImgs, curMousePt, 100));}
        }
    }
    
    private PImage processAlpha(Entity entity, Background bgnd){
        PImage e = entity.getCurrentImage();
        EasyList<Integer> maskColors = new EasyList<Integer>(-197380, -655105);
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










