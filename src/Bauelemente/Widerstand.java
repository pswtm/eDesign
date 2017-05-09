package Bauelemente;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Widerstand extends Bauelement {

    public Widerstand(double x, double y, int Orientation)
    {
        super(x,y,Orientation);
        System.out.println("Class Wid: "+posX+","+posY+","+Orientation);
    }
    public void draw(GraphicsContext gc, Image image)
    {
        gc.drawImage(image,posX-25,posY-25);
    }

}
