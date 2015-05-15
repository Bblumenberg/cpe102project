import java.lang.Math;

public class RandomGen{
    public static int gen(int lower, int upper){
        double range = upper - lower;
        double mult = Math.random();
        return (int) lower + Math.round(Math.round(Math.rint(range * mult)));
    }
}