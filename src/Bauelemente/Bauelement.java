package Bauelemente;


import javafx.geometry.Orientation;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Bauelement {

    //Position des Bauelements auf dem Raster

    //Ausrichtung des Bauelements (Himmelsrichtung)
    final int ausrN = 0;
    final int ausrNO = 1;
    final int ausrO = 2;
    final int ausrSO = 3;
    final int ausrS = 4;
    final int ausrSW = 5;
    final int ausrW = 6;
    final int ausrNW = 7;

    //SnapPoint-Positionen
    double snap1X=0,snap1Y=0;
    double snap2X=0,snap2Y=0;

    protected double posX = 0, posY = 0, Orientation=0;

    //Icon
    public Bauelement(double posX, double posY, double Orientation) {
        this.posX = posX;
        this.posY = posY;
        this.Orientation = Orientation;
    }

    public abstract void draw(GraphicsContext gc, double Orientation);

    public abstract String toxml(String xml);

    public void deleteimage(GraphicsContext gc)
    {
        gc.clearRect(posX-25,posY-25,50,50);
    }


}
