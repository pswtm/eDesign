package Bauelemente;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class Widerstand extends Bauelement {
    //Image Widerstand2=new Image("file:Images/BauelementIcon/widerstandN_S.png",50,50,false,false);
    //Image Widerstand3=new Image("file:Images/BauelementIcon/widerstandNO_SW.png",50,50,false,false);
    //Image Widerstand0=new Image("file:Images/BauelementIcon/widerstandO_W.png",50,50,false,false);
    //Image Widerstand1=new Image("file:Images/BauelementIcon/widerstandNW_SO.png",50,50,false,false);

    //Bilder von den Objekten beim drag and drop Schwarz und Transparent
    Image Widerstand00S=new Image("file:Images/Bauelementeschwarz/widerstand00S.png",50,50,false,false);
    Image Widerstand45S=new Image("file:Images/Bauelementeschwarz/widerstand45S.png",50,50,false,false);
    Image Widerstand90S=new Image("file:Images/Bauelementeschwarz/widerstand90S.png",50,50,false,false);
    Image Widerstand135S=new Image("file:Images/Bauelementeschwarz/widerstand135S.png",50,50,false,false);

    Image Widerstand00T=new Image("file:Images/Bauelementetransparent/widerstand00T.png",50,50,false,false);
    Image Widerstand45T=new Image("file:Images/Bauelementetransparent/widerstand45T.png",50,50,false,false);
    Image Widerstand90T=new Image("file:Images/Bauelementetransparent/widerstand90T.png",50,50,false,false);
    Image Widerstand135T=new Image("file:Images/Bauelementetransparent/widerstand135T.png",50,50,false,false);

    ImageView imageviewWiderstand1 = new ImageView();


    public Widerstand(double x, double y, double Orientation)
    {
        super(x,y,Orientation);
        imageviewWiderstand1.setImage(Widerstand00S);
        //Rechtsklick Drehung bzw ändern des Bildes
        imageviewWiderstand1.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                //Welches Bild ist aktuell? Wegen drehen des Bildes
                if (event.getButton() == MouseButton.SECONDARY) {
                    if(imageviewWiderstand1.getImage()==Widerstand00S)
                    {imageviewWiderstand1.setImage(Widerstand45S);}
                    else if(imageviewWiderstand1.getImage()==Widerstand45S)
                    {imageviewWiderstand1.setImage(Widerstand90S);}
                    else if(imageviewWiderstand1.getImage()==Widerstand90S)
                    {imageviewWiderstand1.setImage(Widerstand135S);}
                    else if(imageviewWiderstand1.getImage()==Widerstand135S)
                    {imageviewWiderstand1.setImage(Widerstand00S);}
                }}});
        //zeichnet während des drag das Transparente Bild
        imageviewWiderstand1.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {
                //Welches Bild ist aktuell? Wegen drehen des Bildes
                if(imageviewWiderstand1.getImage()==Widerstand00S)
                {imageviewWiderstand1.setImage(Widerstand00T);}
                else if(imageviewWiderstand1.getImage()==Widerstand45S)
                {imageviewWiderstand1.setImage(Widerstand45T);}
                else if(imageviewWiderstand1.getImage()==Widerstand90S)
                {imageviewWiderstand1.setImage(Widerstand90T);}
                else if(imageviewWiderstand1.getImage()==Widerstand135S)
                {imageviewWiderstand1.setImage(Widerstand135T);}

                imageviewWiderstand1.setX(event.getSceneX()-25);
                imageviewWiderstand1.setY(event.getSceneY()-25);
            }});
        //Ändert das Bild in das mit schwarzen Hintergrund beim Losllassen der Maustaste
        imageviewWiderstand1.setOnMouseReleased(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {
                //Welches Bild ist aktuell? Wegen drehen des Bildes
                if(imageviewWiderstand1.getImage()==Widerstand00T)
                {imageviewWiderstand1.setImage(Widerstand00S);}
                else if(imageviewWiderstand1.getImage()==Widerstand45T)
                {imageviewWiderstand1.setImage(Widerstand45S);}
                else if(imageviewWiderstand1.getImage()==Widerstand90T)
                {imageviewWiderstand1.setImage(Widerstand90S);}
                else if(imageviewWiderstand1.getImage()==Widerstand135T)
                {imageviewWiderstand1.setImage(Widerstand135S);}

                imageviewWiderstand1.setX(rundenBauteile(event.getSceneX())-25);
                imageviewWiderstand1.setY(rundenBauteile(event.getSceneY())-25);
            }});
    }
    /*
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
    */
    //Wird zum String xml hinzugefügt
    public String toxml(String xml){
        xml+=     "		<Widerstand>" + "Widerstand"  + "</Widerstand>\n"
                + "		<xwid>"+(int)posX+"</xwid>\n"
                + "		<ywid>"+(int)posY+"</ywid>\n"
                + "		<widor>"+(int)Orientation+"</widor>\n\n";
        return xml;
    }
    //Snap ans Raster der Bauteile
    public double rundenBauteile(double runden) {
        if (runden % 50 < 25) {
            return runden - (runden % 50);
        } else if (runden % 50 >= 25) {
            return runden + (50 - (runden % 50));
        } else return 0;
    }
    //Zeichnen Methode
    public void draw1( BorderPane borderPane)
    {
        imageviewWiderstand1.setX(posX-25);
        imageviewWiderstand1.setY(posY-25);
        borderPane.getChildren().add(imageviewWiderstand1);
    }
}
