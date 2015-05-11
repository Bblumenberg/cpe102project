import java.util.List;
import java.util.ArrayList;
import processing.core.*;
import java.util.scanner;

public class ProcessWorld extends PApplet{

    private static final int AMINATION_TIME = 100;

    private static final int WORLD_WIDTH_SCALE = 2;
    private static final int WORLD_HEIGHT_SCALE = 2;
    
    private static final int SCREEN_WIDTH = 640;
    private static final int SCREEN_HEIGHT = 480;
    private static final int TILE_WIDTH = 32;
    private static final int TILE_HEIGHT = 32;
    
    private Entity[][] worldView;

    public void setup(){
        Background defaultBackground = new Background("defaultBackground");
        WorldModel world = new WorldModel((SCREEN_HEIGHT/TILE_HEIGHT)*WORLD_HEIGHT_SCALE, (SCREEN_WIDTH/TILE_WIDTH)*WORLD_WIDTH_SCALE, defaultBackground);
        size(SCREEN_WIDTH, SCREEN_HEIGHT);
        background(color(255,255,255));
        worldView = new Entity[SCREEN_HEIGHT/TILE_HEIGHT][SCREEN_WIDTH/TILE_WIDTH]
        for(int y = 0; y < (SCREEN_HEIGHT/TILE_HEIGHT)*WORLD_HEIGHT_SCALE; y++){
            for(int x = 0; x < (SCREEN_WIDTH/TILE_WIDTH)*WORLD_WIDTH_SCALE; x++){
                worldView[y][x]=worldModel.get
                
            }
        }
    }
}