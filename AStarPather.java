import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.lang.Math;

public class AStarPather{

    class Node{
        public Node previous;
        public Point pt;
        public int g;
        public int h;
        public Node(Node previous, Point pt, int g, int h){
            this.previous = previous;
            this.pt = pt;
            this.g = g;
            this.h = h;
        }
    }
    
    class NodeList extends ArrayList<Node>{
        private List<Point> pointList;
        public NodeList(int length){
            super(length);
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
        public boolean containsNode(Node node){
            return pointList.contains(node.pt);
        }
        public boolean containsPoint(Point point){
            return pointList.contains(point);
        }
    }
    
    
    private int count;
    private Node goal;
    private Node start;
    private Point targetPt;
    private NodeList openSet;
    private NodeList closedSet;
    private Comparator<Node> comp;
    
    public AStarPather(Point startPt, Point targetPt){
        count = 0;
        this.targetPt = targetPt;
        goal = null;
        start = new Node(null, startPt, 0, startPt.manhatten(targetPt));
        openSet = new NodeList(0);
        closedSet = new NodeList(0);
        openSet.addNode(start);
        comp = (Node one, Node two) -> {return (one.g + one.h) - (two.g + two.h);};
    }
    
    public Point search(WorldModel world, OccGrid<Integer> grid, Miner m){
        while(openSet.size() > 0 && count < 1200){
            grid.setCell(targetPt, 3);
            count += 1;
            Collections.sort(openSet, comp);
            Node current = (Node) openSet.get(0);
            openSet.removeNode(current);
            //did we find the target?
            if(current.pt.adjacent(targetPt)){goal = current; break;}
            //we didn't find it
            else{
                closedSet.addNode(current);
                grid.setCell(current.pt, 1);
                //for every point next to the current one:
                for(Point neighborPt : current.pt.openAdjacencyList(world)){
                    Node neighbor = new Node(current, neighborPt, current.g + 1, neighborPt.manhatten(targetPt));
                    //have we searched it yet?
                    if(closedSet.containsPoint(neighbor.pt)){continue;}
                    else{
                        if(!openSet.containsPoint(neighbor.pt)){
                            //not in open set
                            openSet.addNode(neighbor);
                        }
                        else{
                            //it's already in the open set
                            //find the old node so we can check the f-value
                            for(Node node : (List<Node>) openSet){
                                if(node.pt.equals(neighbor.pt)){
                                    //these nodes are in the same place; have the same h-value
                                    if(neighbor.g < node.g){
                                        //neighbor has a quicker route than the original
                                        openSet.removeNode(node);
                                        openSet.addNode(neighbor);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        //once found
        if(goal == null){return start.pt;}
        while(goal.previous.previous != null){
            grid.setCell(goal.pt, 2);
            goal = goal.previous;
        }
        grid.setCell(targetPt, 3);
        return goal.pt;
    }
}