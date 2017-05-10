package Bauelemente;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Widerstand extends Bauelement {

    public Widerstand(double x, double y, double Orientation)
    {
        super(x,y,Orientation);
        System.out.println("Class Wid: "+posX+","+posY+","+Orientation);
    }
    public void draw(GraphicsContext gc, Image image)
    {
        gc.drawImage(image,posX-25,posY-25);
    }
    public String toxml(String xml){
        xml+=     "		<Widerstand>" + "Widerstand"  + "</Widerstand>\n"
                + "		<xwid>"+(int)posX+"</xwid>\n"
                + "		<ywid>"+(int)posY+"</ywid>\n"
                + "		<widor>"+(int)Orientation+"</widor>\n\n";
        return xml;
    }
}
