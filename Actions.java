import java.util.List;
import java.util.LinkedList;

public class Actions{

    private static List<ScheduledAction> pendingAddition = new LinkedList();
    private static List<ScheduledAction> actions = new LinkedList();
    private static List<ScheduledAction> pendingRemoval = new LinkedList();
    public static boolean started;
    
    public static void doAll(){
        for(ScheduledAction action: pendingAddition){actions.add(action);}
        pendingAddition.clear();
        for(ScheduledAction action: actions){
            if(action.getTime() <= System.currentTimeMillis()){
                action.perform();
                pendingRemoval.add(action);
            }
        }
        for(ScheduledAction action: pendingRemoval){actions.remove(action);}
        pendingRemoval.clear();
    }
    
    public static void addAction(ScheduledAction action){pendingAddition.add(action);}
    
    public static void startActions(WorldModel world){
        if(!started){
            for(PositionedEntity e : world.getEntities()){
                if(e instanceof ActionedEntity){
                    if(e instanceof Vein){
                        Vein vein = (Vein) e;
                        vein.createNextAction(world);
                    }
                }
            }
            started = true;
        }
    }
}