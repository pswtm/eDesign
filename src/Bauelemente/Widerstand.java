package Bauelemente;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Widerstand extends Bauelement {
    Image Widerstand2=new Image("file:Images/BauelementIcon/widerstandN_S.png",50,50,false,false);
    Image Widerstand3=new Image("file:Images/BauelementIcon/widerstandNO_SW.png",50,50,false,false);
    Image Widerstand0=new Image("file:Images/BauelementIcon/widerstandO_W.png",50,50,false,false);
    Image Widerstand1=new Image("file:Images/BauelementIcon/widerstandNW_SO.png",50,50,false,false);


    public Widerstand(double x, double y, double Orientation)
    {
        super(x,y,Orientation);
        //System.out.println("Class Wid: "+posX+","+posY+","+Orientation);
    }
    public void draw(GraphicsContext gc, double Orientation)
    {
        if(Orientation==0) {
            gc.drawImage(Widerstand0, posX - 25, posY - 25);
        }
        else if(Orientation==1) {
            gc.drawImage(Widerstand1, posX - 25, posY - 25);
        }
        else if(Orientation==2) {
            gc.drawImage(Widerstand2, posX - 25, posY - 25);
        }
        else if(Orientation==3) {
            gc.drawImage(Widerstand3, posX - 25, posY - 25);
        }
        else gc.drawImage(Widerstand0,posX-25,posY-25);
    }
    public String toxml(String xml){
        xml+=     "		<Widerstand>" + "Widerstand"  + "</Widerstand>\n"
                + "		<xwid>"+(int)posX+"</xwid>\n"
                + "		<ywid>"+(int)posY+"</ywid>\n"
                + "		<widor>"+(int)Orientation+"</widor>\n\n";
        return xml;
    }
}
