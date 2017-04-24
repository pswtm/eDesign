package Bauelemente;

/**
 * Created by Merve on 24.04.2017.
 */
public interface Bauelement {


    //Position des Bauelements auf dem Raster
    int posX = 0, posY = 0;

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

    //Icon





}
