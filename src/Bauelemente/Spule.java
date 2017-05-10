package Bauelemente;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;


public class Spule extends Bauelement {
    Image Spule2=new Image("file:Images/BauelementIcon/spuleN_S.png",50,50,false,false);
    Image Spule3=new Image("file:Images/BauelementIcon/spuleNO_SW.png",50,50,false,false);
    Image Spule0=new Image("file:Images/BauelementIcon/spuleO_W.png",50,50,false,false);
    Image Spule1=new Image("file:Images/BauelementIcon/spuleNW_SO.png",50,50,false,false);
    ImageView imageviewSpule1 = new ImageView();


    public Spule(double x, double y, double Orientation)
    {
        super(x,y,Orientation);
        //System.out.println("Class Spule: "+posX+","+posY+","+Orientation);
        imageviewSpule1.setImage(Spule0);
        imageviewSpule1.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                System.out.println("click");
                if (event.getButton() == MouseButton.SECONDARY) {
                    if(imageviewSpule1.getImage()==Spule0){
                        // System.out.println("Rechtsklick Maus image2");
                        imageviewSpule1.setImage(Spule1);
                    }
                    else if(imageviewSpule1.getImage()==Spule1)
                    {
                        imageviewSpule1.setImage(Spule2);
                    }
                    else if(imageviewSpule1.getImage()==Spule2)
                    {
                        imageviewSpule1.setImage(Spule3);
                    }
                    else if(imageviewSpule1.getImage()==Spule3)
                    {
                        imageviewSpule1.setImage(Spule0);
                    }
                }}});
        imageviewSpule1.setOnMouseReleased(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {
                imageviewSpule1.setX(rundenBauteile(event.getSceneX())-25);
                imageviewSpule1.setY(rundenBauteile(event.getSceneY())-25);
            }});

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
    public double rundenBauteile(double runden) {
        if (runden % 50 < 25) {
            return runden - (runden % 50);
        } else if (runden % 50 >= 25) {
            return runden + (50 - (runden % 50));
        } else return 0;
    }
    public void draw1(BorderPane borderPane)
    {
        imageviewSpule1.setX(posX-25);
        imageviewSpule1.setY(posY-25);
        borderPane.getChildren().add(imageviewSpule1);
    }
}
