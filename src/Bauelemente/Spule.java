package Bauelemente;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class Spule extends Bauelement {
    Image Spule2=new Image("file:Images/BauelementIcon/spuleN_S.png",50,50,false,false);
    Image Spule3=new Image("file:Images/BauelementIcon/spuleNO_SW.png",50,50,false,false);
    Image Spule0=new Image("file:Images/BauelementIcon/spuleO_W.png",50,50,false,false);
    Image Spule1=new Image("file:Images/BauelementIcon/spuleNW_SO.png",50,50,false,false);


    public Spule(double x, double y, double Orientation)
    {
        super(x,y,Orientation);

        //System.out.println("Class Spule: "+posX+","+posY+","+Orientation);
    }
    public void draw(GraphicsContext gc, double Orientation)
    {
        if(Orientation==0) {
            gc.drawImage(Spule0, posX - 25, posY - 25);
        }
        else if(Orientation==1) {
            gc.drawImage(Spule1, posX - 25, posY - 25);
        }
        else if(Orientation==2) {
            gc.drawImage(Spule2, posX - 25, posY - 25);
        }
        else if(Orientation==3) {
            gc.drawImage(Spule3, posX - 25, posY - 25);
        }
        else gc.drawImage(Spule0,posX-25,posY-25);
    }
    public String toxml(String xml){
        xml+=     "		<Spule>" + "Spule" + "</Spule>\n"
                + "		<xspu>"+(int)posX+"</xspu>\n"
                + "		<yspu>"+(int)posY+"</yspu>\n"
                + "		<spuor>"+(int)Orientation+"</spuor>\n\n";
        return xml;
    }
}
