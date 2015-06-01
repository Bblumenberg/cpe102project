import java.util.Scanner;
import java.io.*;
import processing.core.*;
import java.util.List;
import java.util.ArrayList;

public class ImageLoad extends PApplet{

    private Scanner imagefile;
    
    public List<PImage> backgroundImgs;
    public List<PImage> overlayImgs;
    public List<PImage> obstacleImgs;
    public List<PImage> veinImgs;
    public List<PImage> magicVeinImgs;
    public List<PImage> oreImgs;
    public List<PImage> quakeImgs;
    public List<PImage> blobImgs;
    public List<PImage> magicBlobImgs;
    public List<PImage> wyvernImgs;
    public List<PImage> smithImgs;
    public List<PImage> minerImgs;
    
    public ImageLoad(){
        
        try{
            imagefile = new Scanner(new FileInputStream("imagelist"));
        }
        catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        
        backgroundImgs = new ArrayList<PImage>(7);
        overlayImgs = new ArrayList<PImage>(3);
        System.out.println(overlayImgs.size());
        obstacleImgs = new ArrayList<PImage>(0);
        veinImgs = new ArrayList<PImage>(0);
        magicVeinImgs = new ArrayList<PImage>(0);
        oreImgs = new ArrayList<PImage>(0);
        quakeImgs = new ArrayList<PImage>(0);
        blobImgs = new ArrayList<PImage>(0);
        magicBlobImgs = new ArrayList<PImage>(0);
        wyvernImgs = new ArrayList<PImage>(0);
        smithImgs = new ArrayList<PImage>(0);
        minerImgs = new ArrayList<PImage>(0);
        
        while(imagefile.hasNextLine()){
            String[] info = imagefile.nextLine().split("\\s");
            if(info.length >= 2){
                if(info[0].equals("grass")){
                    backgroundImgs.add(0, loadImage(info[1]));
                }
                else if(info[0].equals("rocks")){
                    backgroundImgs.add(1, loadImage(info[1]));
                }
                else if(info[0].equals("magicBackground")){
                    backgroundImgs.add(loadImage(info[1]));
                }
                else if(info[0].equals("obstacleBackground")){
                    backgroundImgs.add(loadImage(info[1]));
                }
                else if(info[0].equals("black")){
                    overlayImgs.add(0, loadImage(info[1]));
                }
                else if(info[0].equals("red")){
                    overlayImgs.add(1, loadImage(info[1]));
                }
                else if(info[0].equals("blue")){
                    overlayImgs.add(2, loadImage(info[1]));
                }
                else if(info[0].equals("obstacle")){
                    obstacleImgs.add(loadImage(info[1]));
                }
                else if(info[0].equals("vein")){
                    veinImgs.add(loadImage(info[1]));
                }
                else if(info[0].equals("magicVein")){
                    magicVeinImgs.add(loadImage(info[1]));
                }
                else if(info[0].equals("ore")){
                    oreImgs.add(loadImage(info[1]));
                }
                else if(info[0].equals("quake")){
                    quakeImgs.add(loadImage(info[1]));
                }
                else if(info[0].equals("blob")){
                    blobImgs.add(loadImage(info[1]));
                }
                else if(info[0].equals("magicBlob")){
                    magicBlobImgs.add(loadImage(info[1]));
                }
                else if(info[0].equals("wyvern")){
                    wyvernImgs.add(loadImage(info[1]));
                }
                else if(info[0].equals("blacksmith")){
                    smithImgs.add(loadImage(info[1]));
                }
                else if(info[0].equals("miner")){
                    minerImgs.add(loadImage(info[1]));
                }
            }
        }
    }
}