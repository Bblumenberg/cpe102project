import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.lang.Math;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
}