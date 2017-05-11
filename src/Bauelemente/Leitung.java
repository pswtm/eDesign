package Bauelemente;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Leitung extends Bauelement{
    double xend;
    double yend;
    Line line =new Line();


    public Leitung(double xstart, double ystart, double Orientation, double xend, double yend)
    {
        super(xstart,ystart,Orientation);
        this.xend=xend;
        this.yend=yend;
        //System.out.println("Class Leitung: "+xstart+","+ystart+","+xend+","+yend);
        line.setStartX(xstart);
        line.setStartY(ystart);
        line.setEndX(xend);
        line.setEndY(yend);
        line.setStroke(Color.WHITE);
    }
    //Wird zum String xml hinzugefügt
    public String toxml(String xml){
        xml+=     "		<Leitung>" + "Leitung" + "</Leitung>\n"
                + "		<xles>"+(int)posX+"</xles>\n"
                + "		<yles>"+(int)posY+"</yles>\n"
                + "		<xlee>"+(int)xend+"</xlee>\n"
                + "		<ylee>"+(int)yend+"</ylee>\n\n";
        return xml;
    }
    public void draw1(BorderPane borderPane)
    {
//Gibts hier nicht wird in Editor gezeichnet
    }
    public double getxstart() {return posX;}
    public double getystart() {return posY;}
    public double getxend()   {return xend;}
    public double getyend()   {return yend;}
    public Line getline()  {return line;}

}
