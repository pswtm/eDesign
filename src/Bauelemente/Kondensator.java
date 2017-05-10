package Bauelemente;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Kondensator extends Bauelement {

    public Kondensator(double x, double y, double Orientation)
    {
        super(x,y,Orientation);

        System.out.println("Class Kon: "+posX+","+posY+","+Orientation);
    }
    public String toxml(String xml){
        xml+=            "		<Kondensator>" + "Kondensator" + "</Kondensator>\n" //Kondensatorname in fett
                + "		<xkon>"+(int)posX+"</xkon>\n"
                + "		<ykon>"+(int)posY+"</ykon>\n"
                + "		<konor>"+(int)Orientation+"</konor>\n\n";
        return xml;
    }

    public void draw(GraphicsContext gc, Image image)
    {
        gc.drawImage(image,posX-25,posY-25);
    }

}
