import java.util.List;
import java.util.ArrayList;

public class DFSPather{
    
    private NodeList searched;
    private Node start;
    private Point target;
    private WorldModel world;
    private Node result;
    private int count;
    
    public DFSPather(Point startPt, Point target, WorldModel world){
        searched = new NodeList();
        this.start = new Node(startPt, null);
        this.target = target;
        this.world = world;
        this.result = start;
        this.count = 0;
    }
    
    private class Node{
        public Point pt;
        public Node previous;
        public Node(Point pt, Node previous){
            this.pt = pt;
            this.previous = previous;
        }
    }
    
    private class NodeList extends ArrayList<Node>{
        private List<Point> pointList;
        public NodeList(){
            super(0);
            pointList = new ArrayList<Point>(0);
        }
        public void addNode(Node node){
            add(node);
            pointList.add(node.pt);
        }
        public void removeNode(Node node){
            remove(node);
            pointList.remove(node.pt);
        }
        public boolean containsPoint(Node node){
            return pointList.contains(node.pt);
        }
    }

    
    public void search(Node current, WorldModel world){
        if(count < 1000){
            count += 1;
            searched.addNode(current);
            
            if(current.pt.adjacent(target)){result = current;}
            
            Node next = new Node(new Point(current.pt.getX() + 1, current.pt.getY()), current);
            if(world.openTile(next.pt) && !searched.containsPoint(next)){
                search(next, world);}
            
            next = new Node(new Point(current.pt.getX(), current.pt.getY() + 1), current);
            if(world.openTile(next.pt) && !searched.containsPoint(next)){
                search(next, world);}
            
            next = new Node(new Point(current.pt.getX() - 1, current.pt.getY()), current);
            if(world.openTile(next.pt) && !searched.containsPoint(next)){
                search(next, world);}
            
            next = new Node(new Point(current.pt.getX(), current.pt.getY() - 1), current);
            if(world.openTile(next.pt) && !searched.containsPoint(next)){
                search(next, world);}
        }
    }
    
    public Point startSearch(){
        search(start, world);
        if(result.previous != null){
            while(result.previous.previous != null){
                result = result.previous;
            }
        }
        return result.pt;
    }
}