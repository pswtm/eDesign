package Bauelemente;

import javafx.geometry.Orientation;

/**
 * Created by Merve on 24.04.2017.
 */
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
    int snap1X=0,snap1Y=0;
    int snap2X=0,snap2Y=0;

    protected int posX = 0, posY = 0, Orientation=0;
    //Icon

<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> origin/master
    public Bauelement(int posX, int posY, int Orientation) {
        this.posX = posX;
        this.posY = posY;
        this.Orientation = Orientation;
    }
<<<<<<< HEAD
=======
=======
public Bauelement(int posX, int posY, int Orientation) {
    this.posX = posX;
    this.posY = posY;
    this.Orientation = Orientation;
}
>>>>>>> origin/master
>>>>>>> origin/master
}
