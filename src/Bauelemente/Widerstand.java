package Bauelemente;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class Widerstand extends Bauelement {

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

    public Widerstand(int ID,double x, double y, int Orientation1)
    {
        super(ID,x,y,Orientation1);
        imageviewWiderstand1.setImage(Widerstand00S);
        //Rechtsklick Drehung bzw ändern des Bildes
        imageviewWiderstand1.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                //Welches Bild ist aktuell? Wegen drehen des Bildes
                if (event.getButton() == MouseButton.SECONDARY) {
                    if(imageviewWiderstand1.getImage()==Widerstand00S)
                    {imageviewWiderstand1.setImage(Widerstand45S);Orientation=1;}
                    else if(imageviewWiderstand1.getImage()==Widerstand45S)
                    {imageviewWiderstand1.setImage(Widerstand90S);Orientation=2;}
                    else if(imageviewWiderstand1.getImage()==Widerstand90S)
                    {imageviewWiderstand1.setImage(Widerstand135S);Orientation=3;}
                    else if(imageviewWiderstand1.getImage()==Widerstand135S)
                    {imageviewWiderstand1.setImage(Widerstand00S);Orientation=0;}
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

                //Mülleimer Funktion löscht alle Händler und das Bild Klasse bleibt allerdings erhalten
                if(event.getSceneX()<=125&&event.getSceneY()>=450&&event.getSceneY()<=500) {
                    imageviewWiderstand1.setImage(null);
                    imageviewWiderstand1.removeEventHandler(MouseEvent.ANY, this);
                }
            }});
    }

    //Wird zum String xml hinzugefügt
    public String toxml(String xml){
        xml+=     "		<Widerstand>" + "Widerstand"  + "</Widerstand>\n"
                + "         <ID>"+ID+"</ID>\n"
                + "		    <xwid>"+(int)posX+"</xwid>\n"
                + "		    <ywid>"+(int)posY+"</ywid>\n"
                + "		    <widor>"+(int)Orientation+"</widor>\n\n";
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
    public  double getOrientation() {return Orientation;}
    public  double getPosX() {return posX;}
    public  double getPosY() {return posY;}

}
