public class ScheduledAction{

    private long time;
    private Action action;
    private ActionedEntity parent;
    private WorldModel world;
    public ScheduledAction(ActionedEntity parent, WorldModel world, int waitTime, Action action){
        this.time = System.currentTimeMillis() + waitTime;
        this.action = action;
        this.parent = parent;
        this.world = world;
    }
    
    public long getTime(){return time;}
    
    public void perform(){action.method(parent, world);}
}