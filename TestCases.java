import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.lang.Math;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import org.junit.Test;

public class TestCases
{
    private static final double DELTA = 0.00001;
    private Point position1 = new Point(0, 0);
    private Point position2 = new Point(1, 0);

    @Test
    public void TestPointAdjacent(){
        assertTrue(position1.adjacent(position2));
    }
    
    @Test
    public void TestPOintDistance(){
        assertEquals(position1.distanceSq(position2), 1, DELTA);
    }
    
    private Background bg = new Background("background");
    
    @Test
    public void TestBackgroundName(){
        assertTrue(bg.getName().equals("background"));
    }
    
    private Obstacle obs = new Obstacle("obs", position1);
    
    @Test
    public void TestObstaclePosition(){
        obs.setPosition(position2);
        assertTrue(obs.getPosition().getX() == 1 && obs.getPosition().getY() == 0);
    }
    
    @Test
    public void TestObstacleString(){
        assertTrue(obs.entityString().equals("obstacle obs 0 0"));
        //Interesting that changing the position above didn't affect this...
        //Due to UnitTest or this being a different mehtod?
    }

    private OccGrid mygrid = new OccGrid(2,2,null);
    
    @Test
    public void TestOccGrid(){
        mygrid.setCell(position1, obs);
        assertTrue(mygrid.getCell(position1) == obs);
        assertTrue(mygrid.getCell(position2) == null);
    }
    
    @Test
    public void TestOccGridReset(){
        mygrid.setCell(position1, obs);
        mygrid.setCell(position1, null);
        assertTrue(mygrid.getCell(position1) == null);
    }
    
    @Test
    public void TestTwoTuple(){
        TwoTuple<Point, Entity> myTwoTuple = new TwoTuple<>(obs.getPosition(), obs);
        assertTrue(myTwoTuple.getL() == position1 && myTwoTuple.getR() == obs);
    }
    
    WorldModel myWorld = new WorldModel(2,2, bg);
//    myWorld.addEntity(obs);
    
    @Test
    public void TestAddEntity(){
        assertTrue(myWorld.getTileOccupant(position1) == obs);
        assertTrue(myWorld.getEntities().contains(obs));
    }
    

    @Test
    public void TestWithinBounds(){
        assertTrue(myWorld.withinBounds(position1));
        assertFalse(myWorld.withinBounds(new Point(-1,-1)));
    }
    
    @Test
    public void TestIsOccupied(){
        assertTrue(myWorld.isOccupied(position1));
        assertFalse(myWorld.isOccupied(position2));
    }
/*
    @Test
    public void TestFindNearest(){
        assertTrue(myWorld.findNearest(position2, Obstacle.class) == obs);
    }
    
    @Test
    public void TestFindNearestNull(){
        assertTrue(myWorld.findNearest(position2, Vein.class) == null);
    }
*/
    @Test
    public void TestMoveEntity(){
        List<Point> tiles = myWorld.moveEntity(obs, position2);
        assertTrue(obs.getPosition() == position2);
        assertTrue(tiles.size() == 2 && tiles.get(0) == position1 && tiles.get(1) == position2);
    }

    @Test
    public void TestRemoveEntity(){
        myWorld.removeEntity(obs);
        assertTrue(myWorld.getEntities().size() == 0 && myWorld.getTileOccupant(obs.getPosition()) == null);
    }
    
    @Test
    public void TestRemoveEntityAt(){
        myWorld.removeEntityAt(obs.getPosition());
        assertTrue(myWorld.getEntities().size() == 0 && myWorld.getTileOccupant(obs.getPosition()) == null);
    }
    
    @Test
    public void TestGetBackground(){
        assertTrue(myWorld.getBackground(position1) == bg);
    }
    
    @Test
    public void TestSetBackground(){
        myWorld.setBackground(position1, null);
        assertTrue(myWorld.getBackground(position1) == null);
    }
    
    @Test
    public void TestGetTileOccupant(){
        assertTrue(myWorld.getTileOccupant(position1) == obs);
    }
}































