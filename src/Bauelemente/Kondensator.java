package Bauelemente;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class Kondensator extends Bauelement {
    Image Kondensator2=new Image("file:Images/BauelementIcon/kondensatorN_S.png",50,50,false,false);
    Image Kondensator3=new Image("file:Images/BauelementIcon/kondensatorNO_SW.png",50,50,false,false);
    Image Kondensator0=new Image("file:Images/BauelementIcon/kondensatorO_W.png",50,50,false,false);
    Image Kondensator1=new Image("file:Images/BauelementIcon/kondensatorNW_SO.png",50,50,false,false);
    ImageView imageviewKondensator1 = new ImageView();


    public Kondensator(double x, double y, double Orientation)
    {
        super(x,y,Orientation);
        //System.out.println("Class Kon: "+posX+","+posY+","+Orientation);
        imageviewKondensator1.setImage(Kondensator0);
        imageviewKondensator1.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                System.out.println("click");
                if (event.getButton() == MouseButton.SECONDARY) {
                    if(imageviewKondensator1.getImage()==Kondensator0){
                        // System.out.println("Rechtsklick Maus image2");
                        imageviewKondensator1.setImage(Kondensator1);
                    }
                    else if(imageviewKondensator1.getImage()==Kondensator1)
                    {
                        imageviewKondensator1.setImage(Kondensator2);
                    }
                    else if(imageviewKondensator1.getImage()==Kondensator2)
                    {
                        imageviewKondensator1.setImage(Kondensator3);
                    }
                    else if(imageviewKondensator1.getImage()==Kondensator3)
                    {
                        imageviewKondensator1.setImage(Kondensator0);
                    }
                }}});
        imageviewKondensator1.setOnMouseReleased(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {
                imageviewKondensator1.setX(rundenBauteile(event.getSceneX())-25);
                imageviewKondensator1.setY(rundenBauteile(event.getSceneY())-25);
            }});
        imageviewKondensator1.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {
                imageviewKondensator1.setX(event.getSceneX()-25);
                imageviewKondensator1.setY(event.getSceneY()-25);
            }});

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
    public double rundenBauteile(double runden) {
        if (runden % 50 < 25) {
            return runden - (runden % 50);
        } else if (runden % 50 >= 25) {
            return runden + (50 - (runden % 50));
        } else return 0;
    }
    public void draw1(BorderPane borderPane)
    {
        imageviewKondensator1.setX(posX-25);
        imageviewKondensator1.setY(posY-25);
        borderPane.getChildren().add(imageviewKondensator1);
    }

}
