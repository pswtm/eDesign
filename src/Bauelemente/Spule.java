package Bauelemente;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class Spule extends Bauelement {

    public Spule(double x, double y, double Orientation)
    {
        super(x,y,Orientation);

        System.out.println("Class Spule: "+posX+","+posY+","+Orientation);
    }
    public void draw(GraphicsContext gc, Image image)
    {
        gc.drawImage(image,posX-25,posY-25);
    }
    public String toxml(String xml){
        xml+=     "		<Spule>" + "Spule" + "</Spule>\n"
                + "		<xspu>"+(int)posX+"</xspu>\n"
                + "		<yspu>"+(int)posY+"</yspu>\n"
                + "		<spuor>"+(int)Orientation+"</spuor>\n\n";
        return xml;
    }
}
