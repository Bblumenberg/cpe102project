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
        assertEquals(position1.distance_sq(position2), 1, DELTA);
    }
    
    private Background bg = new Background("background");
    
    @Test
    public void TestBackgroundName(){
        assertTrue(bg.get_name().equals("background"));
    }
    
    private Obstacle obs = new Obstacle("obs", position1);
    
    @Test
    public void TestObstaclePosition(){
        obs.set_position(position2);
        assertTrue(obs.get_position().get_x() == 1.0 && obs.get_position().get_y() == 0.0);
    }
    
    @Test
    public void TestObstacleString(){
        assertTrue(obs.entity_string().equals("obstacle obs 0.0 0.0"));
        //Interesting that changing the position above didn't affect this...
        //Due to UnitTest or this being a different mehtod?
    }
}