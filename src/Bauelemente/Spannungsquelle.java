package Bauelemente;

import com.sun.javafx.geom.Rectangle;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;


public class Spannungsquelle extends Bauelement {

    Image Spannungsquelle2=new Image("file:Images/BauelementIcon/spannungsquelleN_S.png",50,50,false,false);
    Image Spannungsquelle3=new Image("file:Images/BauelementIcon/spannungsquelleNO_SW.png",50,50,false,false);
    Image Spannungsquelle0=new Image("file:Images/BauelementIcon/spannungsquelleO_W.png",50,50,false,false);
    Image Spannungsquelle1=new Image("file:Images/BauelementIcon/spannungsquelleNW_SO.png",50,50,false,false);

    public Spannungsquelle(double x, double y, double Orientation)
    {
        super(x,y,Orientation);
        //System.out.println("Class Spann: "+posX+","+posY+","+Orientation);

    }
    public void draw(GraphicsContext gc, double Orientation)
    {
        if(Orientation==0) {
            gc.drawImage(Spannungsquelle0, posX - 25, posY - 25);
        }
        else if(Orientation==1) {
            gc.drawImage(Spannungsquelle1, posX - 25, posY - 25);
        }
        else if(Orientation==2) {
            gc.drawImage(Spannungsquelle2, posX - 25, posY - 25);
        }
        else if(Orientation==3) {
            gc.drawImage(Spannungsquelle3, posX - 25, posY - 25);
        }
        else gc.drawImage(Spannungsquelle0,posX-25,posY-25);

    }
    public String toxml(String xml){
        xml+=     "		<Spannungsquelle>" + "Spannungsquelle" +  "</Spannungsquelle>\n"
                + "		    <xspa>"+(int)posX+"</xspa>\n"
                + "		    <yspa>"+(int)posY+"</yspa>\n"
                + "		    <spaor>"+(int)Orientation+"</spaor>\n\n";
        return xml;
    }


}
