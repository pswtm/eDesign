package Bauelemente;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Kondensator extends Bauelement {

    public Kondensator(double x, double y, int Orientation)
    {
        super(x,y,Orientation);

        System.out.println("Class Kon: "+posX+","+posY+","+Orientation);
    }
    public void draw(GraphicsContext gc, Image image)
    {
        gc.drawImage(image,posX-25,posY-25);
    }

}
