package Bauelemente;

import com.sun.javafx.geom.Rectangle;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;


public class Spannungsquelle extends Bauelement {

Image image;
    public Spannungsquelle(double x, double y, double Orientation)
    {
        super(x,y,Orientation);

        System.out.println("Class Spann: "+posX+","+posY+","+Orientation);

    }
    public void draw(GraphicsContext gc, Image image)
    {
        this.image=image;
        gc.drawImage(image,posX-25,posY-25);

    }
    public String toxml(String xml){
        xml+=     "		<Spannungsquelle>" + "Spannungsquelle" +  "</Spannungsquelle>\n"
                + "		<xspa>"+(int)posX+"</xspa>\n"
                + "		<yspa>"+(int)posY+"</yspa>\n"
                + "		<spaor>"+(int)Orientation+"</spaor>\n\n";
        return xml;
    }


}
