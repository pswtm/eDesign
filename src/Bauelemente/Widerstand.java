package Bauelemente;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class Widerstand extends Bauelement {
    Image Widerstand2=new Image("file:Images/BauelementIcon/widerstandN_S.png",50,50,false,false);
    Image Widerstand3=new Image("file:Images/BauelementIcon/widerstandNO_SW.png",50,50,false,false);
    Image Widerstand0=new Image("file:Images/BauelementIcon/widerstandO_W.png",50,50,false,false);
    Image Widerstand1=new Image("file:Images/BauelementIcon/widerstandNW_SO.png",50,50,false,false);
    ImageView imageviewWiderstand1 = new ImageView();


    public Widerstand(double x, double y, double Orientation)
    {
        super(x,y,Orientation);
        //System.out.println("Class Wid: "+posX+","+posY+","+Orientation);
        imageviewWiderstand1.setImage(Widerstand0);
        imageviewWiderstand1.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                System.out.println("click");
                if (event.getButton() == MouseButton.SECONDARY) {
                    if(imageviewWiderstand1.getImage()==Widerstand0){
                        // System.out.println("Rechtsklick Maus image2");
                        imageviewWiderstand1.setImage(Widerstand1);
                    }
                    else if(imageviewWiderstand1.getImage()==Widerstand1)
                    {
                        imageviewWiderstand1.setImage(Widerstand2);
                    }
                    else if(imageviewWiderstand1.getImage()==Widerstand2)
                    {
                        imageviewWiderstand1.setImage(Widerstand3);
                    }
                    else if(imageviewWiderstand1.getImage()==Widerstand3)
                    {
                        imageviewWiderstand1.setImage(Widerstand0);
                    }
                }}});
        imageviewWiderstand1.setOnMouseReleased(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {
                imageviewWiderstand1.setX(rundenBauteile(event.getSceneX())-25);
                imageviewWiderstand1.setY(rundenBauteile(event.getSceneY())-25);
            }});

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
    public double rundenBauteile(double runden) {
        if (runden % 50 < 25) {
            return runden - (runden % 50);
        } else if (runden % 50 >= 25) {
            return runden + (50 - (runden % 50));
        } else return 0;
    }
    public void draw1( BorderPane borderPane)
    {
        imageviewWiderstand1.setX(posX-25);
        imageviewWiderstand1.setY(posY-25);
        borderPane.getChildren().add(imageviewWiderstand1);
    }
}
