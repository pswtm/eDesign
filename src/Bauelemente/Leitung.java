package Bauelemente;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Created by fabiankuschke on 09.05.17.
 */
public class Leitung extends Bauelement{
    int xend;
    int yend;
    Line line =new Line();


    public Leitung(int xstart, int ystart, int Orientation, int xend, int yend)
    {
        super(xstart,ystart,Orientation);
        this.xend=xend;
        this.yend=yend;
        System.out.println("Class Leitung: "+xstart+","+ystart+","+xend+","+yend);
        line.setStartX(xstart);
        line.setStartY(ystart);
        line.setEndX(xend);
        line.setEndY(yend);
        line.setStroke(Color.WHITE);
    }
    public int getxstart() {return posX;}
    public int getystart() {return posY;}
    public int getxend()   {return xend;}
    public int getyend()   {return yend;}
    public Line getline()  {return line;}
}
