package Bauelemente;

import com.sun.javafx.geom.Rectangle;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;

import java.awt.*;
import java.awt.Button;
import java.awt.event.*;
import java.awt.event.KeyEvent;

import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;


public class Spannungsquelle extends Bauelement {

    int WidthHeight=50;
    //Bilder von den Objekten beim drag and drop Schwarz und Transparent
    Image Spannungsquelle00S=new Image("file:Images/Bauelementeschwarz/spannungsquelle00S.png",WidthHeight,WidthHeight,false,false);
    Image Spannungsquelle45S=new Image("file:Images/Bauelementeschwarz/spannungsquelle45S.png",WidthHeight,WidthHeight,false,false);
    Image Spannungsquelle90S=new Image("file:Images/Bauelementeschwarz/spannungsquelle90S.png",WidthHeight,WidthHeight,false,false);
    Image Spannungsquelle135S=new Image("file:Images/Bauelementeschwarz/spannungsquelle135S.png",WidthHeight,WidthHeight,false,false);

    Image Spannungsquelle00T=new Image("file:Images/Bauelementetransparent/spannungsquelle00T.png",50,50,false,false);
    Image Spannungsquelle45T=new Image("file:Images/Bauelementetransparent/spannungsquelle45T.png",50,50,false,false);
    Image Spannungsquelle90T=new Image("file:Images/Bauelementetransparent/spannungsquelle90T.png",50,50,false,false);
    Image Spannungsquelle135T=new Image("file:Images/Bauelementetransparent/spannungsquelle135T.png",50,50,false,false);

    ImageView imageviewSpannungsquelle1 = new ImageView();
    BorderPane border=new BorderPane();

    public Spannungsquelle(int ID,double x, double y, int Orientation1)
    {

        super(ID,x,y,Orientation1);
        imageviewSpannungsquelle1.setImage(Spannungsquelle00S);
        //Rechtsklick Drehung bzw ändern des Bildes
        imageviewSpannungsquelle1.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY) {
                    //Welches Bild ist aktuell? Wegen drehen des Bildes
                    if(imageviewSpannungsquelle1.getImage()==Spannungsquelle00S)
                    {imageviewSpannungsquelle1.setImage(Spannungsquelle45S);Orientation=1;}
                    else if(imageviewSpannungsquelle1.getImage()==Spannungsquelle45S)
                    {imageviewSpannungsquelle1.setImage(Spannungsquelle90S);Orientation=2;}
                    else if(imageviewSpannungsquelle1.getImage()==Spannungsquelle90S)
                    {imageviewSpannungsquelle1.setImage(Spannungsquelle135S);Orientation=3;}
                    else if(imageviewSpannungsquelle1.getImage()==Spannungsquelle135S)
                    {imageviewSpannungsquelle1.setImage(Spannungsquelle00S);Orientation=0;}
                }}});
        //zeichnet während des drag das Transparente Bild
        imageviewSpannungsquelle1.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {
                //Welches Bild ist aktuell? Wegen drehen des Bildes
                if(imageviewSpannungsquelle1.getImage()==Spannungsquelle00S)
                {imageviewSpannungsquelle1.setImage(Spannungsquelle00T);}
                else if(imageviewSpannungsquelle1.getImage()==Spannungsquelle45S)
                {imageviewSpannungsquelle1.setImage(Spannungsquelle45T);}
                else if(imageviewSpannungsquelle1.getImage()==Spannungsquelle90S)
                {imageviewSpannungsquelle1.setImage(Spannungsquelle90T);}
                else if(imageviewSpannungsquelle1.getImage()==Spannungsquelle135S)
                {imageviewSpannungsquelle1.setImage(Spannungsquelle135T);}

                imageviewSpannungsquelle1.setX(event.getSceneX()-25);
                imageviewSpannungsquelle1.setY(event.getSceneY()-25);

            }});
        //Ändert das Bild in das mit schwarzen Hintergrund beim Losllassen der Maustaste
        imageviewSpannungsquelle1.setOnMouseReleased(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {
                //Welches Bild ist aktuell? Wegen drehen des Bildes
                if(imageviewSpannungsquelle1.getImage()==Spannungsquelle00T)
                {imageviewSpannungsquelle1.setImage(Spannungsquelle00S);}
                else if(imageviewSpannungsquelle1.getImage()==Spannungsquelle45T)
                {imageviewSpannungsquelle1.setImage(Spannungsquelle45S);}
                else if(imageviewSpannungsquelle1.getImage()==Spannungsquelle90T)
                {imageviewSpannungsquelle1.setImage(Spannungsquelle90S);}
                else if(imageviewSpannungsquelle1.getImage()==Spannungsquelle135T)
                {imageviewSpannungsquelle1.setImage(Spannungsquelle135S);}

                imageviewSpannungsquelle1.setX(rundenBauteile(event.getSceneX())-25);
                imageviewSpannungsquelle1.setY(rundenBauteile(event.getSceneY())-25);

                //Mülleimer Funktion löscht alle Händler und das Bild Klasse bleibt allerdings erhalten
                if(event.getSceneX()<=125&&event.getSceneY()>=450&&event.getSceneY()<=500) {
                    imageviewSpannungsquelle1.setImage(null);
                    imageviewSpannungsquelle1.removeEventHandler(MouseEvent.ANY, this);
                }
            }});

        imageviewSpannungsquelle1.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                //Todo entweder Hilfe anzeigen weden Drehung oder kleine Buttons zum drehen
                System.out.println("Drehung mit rechtsklick auf das Objekt: "+ID);
                /*
                imageviewSpannungsquelle1.setOnKeyReleased(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        // if(event.getCode()==KeyCode.E) {
                        //if(event.getCode()== KeyCode.DELETE)
                        System.out.println("Test"+event.getText());
                    //}
                    }
                });*/
            }});
        imageviewSpannungsquelle1.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
            //Todo Buttons wieder verschwinden lassen bzw ausblenden falls wir uns dafür entscheiden oder Hilfetext verschwinden lassen

            }});
        /*
        //Key drücken um Aktion durchzuführen geht nicht
        imageviewSpannungsquelle1.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
               // if(event.getCode()==KeyCode.E) {
                    //if(event.getCode()== KeyCode.DELETE)
                    System.out.println("Test"+event.getText());
                }
            //}
        });
        */

    }
    //Zeichnen Methode
    //TODO Orientation ist hier richtig muss noch bei
    public void draw1(BorderPane borderPane)
    {
        //ka ob das gut ist funktioniert allerdings
        this.border=borderPane;
            imageviewSpannungsquelle1.setX(posX-25);
            imageviewSpannungsquelle1.setY(posY-25);
            if(Orientation==0) {imageviewSpannungsquelle1.setImage(Spannungsquelle00S);}
            else if(Orientation==1){imageviewSpannungsquelle1.setImage(Spannungsquelle45S);}
            else if(Orientation==2){imageviewSpannungsquelle1.setImage(Spannungsquelle90S);}
            else if(Orientation==3){imageviewSpannungsquelle1.setImage(Spannungsquelle135S);}
            else imageviewSpannungsquelle1.setImage(Spannungsquelle00S);
            borderPane.getChildren().add(imageviewSpannungsquelle1);
    }
    //Wird zum String xml hinzugefügt
    public String toxml(String xml){
        xml+=     "		<Spannungsquelle>" + "Spannungsquelle" +  "</Spannungsquelle>\n"
                + "         <ID>"+ID+"</ID>\n"
                + "		    <xspa>"+(int)posX+"</xspa>\n"
                + "		    <yspa>"+(int)posY+"</yspa>\n"
                + "		    <spaor>"+Orientation+"</spaor>\n\n";
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
    public  double getOrientation() {return Orientation;}
    public  double getPosX() {return posX;}
    public  double getPosY() {return posY;}


}
