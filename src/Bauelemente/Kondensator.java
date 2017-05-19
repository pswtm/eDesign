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
    Image S00=new Image("file:Images/Bauelementeschwarz/kondensator00S.png",50,50,false,false);
    Image S45=new Image("file:Images/Bauelementeschwarz/kondensator45S.png",50,50,false,false);
    Image S90=new Image("file:Images/Bauelementeschwarz/kondensator90S.png",50,50,false,false);
    Image S135=new Image("file:Images/Bauelementeschwarz/kondensator135S.png",50,50,false,false);

    Image T00=new Image("file:Images/Bauelementetransparent/kondensator00T.png",50,50,false,false);
    Image T45=new Image("file:Images/Bauelementetransparent/kondensator45T.png",50,50,false,false);
    Image T90=new Image("file:Images/Bauelementetransparent/kondensator90T.png",50,50,false,false);
    Image T135=new Image("file:Images/Bauelementetransparent/kondensator135T.png",50,50,false,false);

    ImageView imageview = new ImageView();
    boolean deleted=false;

    public Kondensator(int ID, double x, double y, int Orientation1)
    {
        super(ID,x,y,Orientation1);
        imageview.setImage(S00);
        //Rechtsklick Drehung bzw ändern des Bildes
        imageview.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY) {
                    //Welches Bild ist aktuell? Wegen drehen des Bildes
                    if(imageview.getImage()==S00)
                    {imageview.setImage(S45);Orientation=1;}
                    else if(imageview.getImage()==S45)
                    {imageview.setImage(S90);Orientation=2;}
                    else if(imageview.getImage()==S90)
                    {imageview.setImage(S135);Orientation=3;}
                    else if(imageview.getImage()==S135)
                    {imageview.setImage(S00);Orientation=0;}
                }}});
        //zeichnet während des drag das Transparente Bild
        imageview.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {
                //Welches Bild ist aktuell? Wegen drehen des Bildes
                if(imageview.getImage()==S00)
                {imageview.setImage(T00);}
                else if(imageview.getImage()==S45)
                {imageview.setImage(T45);}
                else if(imageview.getImage()==S90)
                {imageview.setImage(T90);}
                else if(imageview.getImage()==S135)
                {imageview.setImage(T135);}

                imageview.setX(event.getSceneX()-25);
                imageview.setY(event.getSceneY()-25);
            }});
        //Ändert das Bild in das mit schwarzen Hintergrund beim Losllassen der Maustaste
        imageview.setOnMouseReleased(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {
                //Mülleimer Funktion löscht alle Händler und das Bild Klasse bleibt allerdings erhalten
                if(event.getSceneX()<=125&&event.getSceneY()>=450&&event.getSceneY()<=500) {
                    deleted=true;
                    imageview.setImage(null);
                    imageview.removeEventHandler(MouseEvent.ANY, this);
                }
                else {
                    //Welches Bild ist aktuell? Wegen drehen des Bildes
                    if (imageview.getImage() == T00) {
                        imageview.setImage(S00);
                    } else if (imageview.getImage() == T45) {
                        imageview.setImage(S45);
                    } else if (imageview.getImage() == T90) {
                        imageview.setImage(S90);
                    } else if (imageview.getImage() == T135) {
                        imageview.setImage(S135);
                    }
                    imageview.setX(rundenLeitungen(event.getSceneX()) - 25);
                    imageview.setY(rundenLeitungen(event.getSceneY()) - 25);
                    posX = rundenLeitungen(event.getSceneX());
                    posY = rundenLeitungen(event.getSceneY());
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
    public double rundenLeitungen(double runden)
    {
        double a=0,b=0;
        if (runden % 25 < 12.5) {
            a= runden - (runden % 25);
            return a;

        } else if (runden % 25 >= 12.5) {
            b= runden +  (25-runden % 25);
            return b;
        } else return 0;
    }
    //Zeichnen Methode
    public void draw1(BorderPane borderPane)
    {
        imageview.setX(posX-25);
        imageview.setY(posY-25);
        if(Orientation==0) {imageview.setImage(S00);}
        else if(Orientation==1){imageview.setImage(S45);}
        else if(Orientation==2){imageview.setImage(S90);}
        else if(Orientation==3){imageview.setImage(S135);}
        else imageview.setImage(S00);
        borderPane.getChildren().add(imageview);
    }
    public  double getOrientation() {return Orientation;}
    public  double getPosX() {return posX;}
    public  double getPosY() {return posY;}

}
