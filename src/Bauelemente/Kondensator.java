package Bauelemente;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Kondensator extends Bauelement {
    Image Kondensator2=new Image("file:Images/BauelementIcon/kondensatorN_S.png",50,50,false,false);
    Image Kondensator3=new Image("file:Images/BauelementIcon/kondensatorNO_SW.png",50,50,false,false);
    Image Kondensator0=new Image("file:Images/BauelementIcon/kondensatorO_W.png",50,50,false,false);
    Image Kondensator1=new Image("file:Images/BauelementIcon/kondensatorNW_SO.png",50,50,false,false);


    public Kondensator(double x, double y, double Orientation)
    {
        super(x,y,Orientation);
        //System.out.println("Class Kon: "+posX+","+posY+","+Orientation);
    }
    public String toxml(String xml){
        xml+=            "		<Kondensator>" + "Kondensator" + "</Kondensator>\n" //Kondensatorname in fett
                + "		<xkon>"+(int)posX+"</xkon>\n"
                + "		<ykon>"+(int)posY+"</ykon>\n"
                + "		<konor>"+(int)Orientation+"</konor>\n\n";
        return xml;
    }

    public void draw(GraphicsContext gc, double Orientation)
    {
        if(Orientation==0) {
            gc.drawImage(Kondensator0, posX - 25, posY - 25);
        }
        else if(Orientation==1) {
            gc.drawImage(Kondensator1, posX - 25, posY - 25);
        }
        else if(Orientation==2) {
            gc.drawImage(Kondensator2, posX - 25, posY - 25);
        }
        else if(Orientation==3) {
            gc.drawImage(Kondensator3, posX - 25, posY - 25);
        }
        else gc.drawImage(Kondensator0,posX-25,posY-25);
    }

}
