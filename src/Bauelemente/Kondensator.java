package Bauelemente;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class Kondensator extends Bauelement {

    //Bilder von den Objekten beim drag and drop Schwarz und Transparent
    Image Kondensator00S=new Image("file:Images/Bauelementeschwarz/kondensator00S.png",50,50,false,false);
    Image Kondensator45S=new Image("file:Images/Bauelementeschwarz/kondensator45S.png",50,50,false,false);
    Image Kondensator90S=new Image("file:Images/Bauelementeschwarz/kondensator90S.png",50,50,false,false);
    Image Kondensator135S=new Image("file:Images/Bauelementeschwarz/kondensator135S.png",50,50,false,false);

    Image Kondensator00T=new Image("file:Images/Bauelementetransparent/kondensator00T.png",50,50,false,false);
    Image Kondensator45T=new Image("file:Images/Bauelementetransparent/kondensator45T.png",50,50,false,false);
    Image Kondensator90T=new Image("file:Images/Bauelementetransparent/kondensator90T.png",50,50,false,false);
    Image Kondensator135T=new Image("file:Images/Bauelementetransparent/kondensator135T.png",50,50,false,false);

    ImageView imageviewKondensator1 = new ImageView();
    boolean deleted=false;

    public Kondensator(int ID, double x, double y, int Orientation1)
    {
        super(ID,x,y,Orientation1);
        imageviewKondensator1.setImage(Kondensator00S);
        //Rechtsklick Drehung bzw ändern des Bildes
        imageviewKondensator1.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                //Welches Bild ist aktuell? Wegen drehen des Bildes
                if (event.getButton() == MouseButton.SECONDARY) {
                    if(imageviewKondensator1.getImage()==Kondensator00S)
                    {imageviewKondensator1.setImage(Kondensator45S);Orientation=1;}
                    else if(imageviewKondensator1.getImage()==Kondensator45S)
                    {imageviewKondensator1.setImage(Kondensator90S);Orientation=2;}
                    else if(imageviewKondensator1.getImage()==Kondensator90S)
                    {imageviewKondensator1.setImage(Kondensator135S);Orientation=3;}
                    else if(imageviewKondensator1.getImage()==Kondensator135S)
                    {imageviewKondensator1.setImage(Kondensator00S);Orientation=0;}
                }}});
        //zeichnet während des drag das Transparente Bild
        imageviewKondensator1.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {
                //Welches Bild ist aktuell? Wegen drehen des Bildes
                if(imageviewKondensator1.getImage()==Kondensator00S)
                {imageviewKondensator1.setImage(Kondensator00T);}
                else if(imageviewKondensator1.getImage()==Kondensator45S)
                {imageviewKondensator1.setImage(Kondensator45T);}
                else if(imageviewKondensator1.getImage()==Kondensator90S)
                {imageviewKondensator1.setImage(Kondensator90T);}
                else if(imageviewKondensator1.getImage()==Kondensator135S)
                {imageviewKondensator1.setImage(Kondensator135T);}

                imageviewKondensator1.setX(event.getSceneX()-25);
                imageviewKondensator1.setY(event.getSceneY()-25);
            }});
        //Ändert das Bild in das mit schwarzen Hintergrund beim Losllassen der Maustaste
        imageviewKondensator1.setOnMouseReleased(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {
                //Welches Bild ist aktuell? Wegen drehen des Bildes
                if(imageviewKondensator1.getImage()==Kondensator00T)
                {imageviewKondensator1.setImage(Kondensator00S);}
                else if(imageviewKondensator1.getImage()==Kondensator45T)
                {imageviewKondensator1.setImage(Kondensator45S);}
                else if(imageviewKondensator1.getImage()==Kondensator90T)
                {imageviewKondensator1.setImage(Kondensator90S);}
                else if(imageviewKondensator1.getImage()==Kondensator135T)
                {imageviewKondensator1.setImage(Kondensator135S);}

                imageviewKondensator1.setX(rundenBauteile(event.getSceneX())-25);
                imageviewKondensator1.setY(rundenBauteile(event.getSceneY())-25);
                posX=rundenBauteile(event.getSceneX());
                posY=rundenBauteile(event.getSceneY());

                //Mülleimer Funktion löscht alle Händler und das Bild Klasse bleibt allerdings erhalten
                if(event.getSceneX()<=125&&event.getSceneY()>=450&&event.getSceneY()<=500) {
                    deleted=true;
                    imageviewKondensator1.setImage(null);
                    imageviewKondensator1.removeEventHandler(MouseEvent.ANY, this);
                }
            }});
    }
    //Wird zum String xml hinzugefügt
    public String toxml(String xml){
        if(deleted==false) {

            xml += "		<Kondensator>" + "Kondensator" + "</Kondensator>\n" //Kondensatorname in fett
                    + "         <ID>" + ID + "</ID>\n"
                    + "		    <PositionX>" + (int) posX + "</PositionX>\n"
                    + "		    <PositionY>" + (int) posY + "</PositionY>\n"
                    + "		    <Richtung>" + Orientation + "</Richtung>\n\n";
            return xml;
        }
        else return xml;
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
    public void draw1(BorderPane borderPane)
    {
        imageviewKondensator1.setX(posX-25);
        imageviewKondensator1.setY(posY-25);
        if(Orientation==0) {imageviewKondensator1.setImage(Kondensator00S);}
        else if(Orientation==1){imageviewKondensator1.setImage(Kondensator45S);}
        else if(Orientation==2){imageviewKondensator1.setImage(Kondensator90S);}
        else if(Orientation==3){imageviewKondensator1.setImage(Kondensator135S);}
        else imageviewKondensator1.setImage(Kondensator00S);

        borderPane.getChildren().add(imageviewKondensator1);
    }
    public  double getOrientation() {return Orientation;}
    public  double getPosX() {return posX;}
    public  double getPosY() {return posY;}

}
