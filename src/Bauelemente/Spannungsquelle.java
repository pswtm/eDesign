package Bauelemente;

import com.sun.javafx.geom.Rectangle;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import java.awt.event.MouseAdapter;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;


public class Spannungsquelle extends Bauelement {

    Image Spannungsquelle2=new Image("file:Images/BauelementIcon/spannungsquelleN_S.png",50,50,false,false);
    Image Spannungsquelle3=new Image("file:Images/BauelementIcon/spannungsquelleNO_SW.png",50,50,false,false);
    Image Spannungsquelle0=new Image("file:Images/BauelementIcon/spannungsquelleO_W.png",50,50,false,false);
    Image Spannungsquelle1=new Image("file:Images/BauelementIcon/spannungsquelleNW_SO.png",50,50,false,false);
    ImageView imageviewSpannungsquelle1 = new ImageView();

    public Spannungsquelle(double x, double y, double Orientation)
    {
        super(x,y,Orientation);
        //System.out.println("Class Spann: "+posX+","+posY+","+Orientation);
        imageviewSpannungsquelle1.setImage(Spannungsquelle0);
        imageviewSpannungsquelle1.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                System.out.println("click");
                if (event.getButton() == MouseButton.SECONDARY) {
                    if(imageviewSpannungsquelle1.getImage()==Spannungsquelle0){
                        // System.out.println("Rechtsklick Maus image2");
                        imageviewSpannungsquelle1.setImage(Spannungsquelle1);
                    }
                    else if(imageviewSpannungsquelle1.getImage()==Spannungsquelle1)
                    {
                        imageviewSpannungsquelle1.setImage(Spannungsquelle2);
                    }
                    else if(imageviewSpannungsquelle1.getImage()==Spannungsquelle2)
                    {
                        imageviewSpannungsquelle1.setImage(Spannungsquelle3);
                    }
                    else if(imageviewSpannungsquelle1.getImage()==Spannungsquelle3)
                    {
                        imageviewSpannungsquelle1.setImage(Spannungsquelle0);
                    }
            }}});
        imageviewSpannungsquelle1.setOnMouseReleased(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {
                imageviewSpannungsquelle1.setX(rundenBauteile(event.getSceneX()-25));
                imageviewSpannungsquelle1.setY(rundenBauteile(event.getSceneY()-25));
            }});

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
    public void draw1(GraphicsContext gc, double Orientation, BorderPane borderPane)
    {
        if(Orientation==0) {

            imageviewSpannungsquelle1.setX(posX-25);
            imageviewSpannungsquelle1.setY(posY-25);
            borderPane.getChildren().add(imageviewSpannungsquelle1);
            //gc.drawImage(Spannungsquelle0, posX - 25, posY - 25);
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
    public double rundenBauteile(double runden) {
        if (runden % 50 < 25) {
            return runden - (runden % 50);
        } else if (runden % 50 >= 25) {
            return runden + (50 - (runden % 50));
        } else return 0;
    }

}
