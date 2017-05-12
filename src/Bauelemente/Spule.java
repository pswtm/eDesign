package Bauelemente;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;


public class Spule extends Bauelement {

    //Bilder von den Objekten beim drag and drop Schwarz und Transparent
    Image Spule00S=new Image("file:Images/Bauelementeschwarz/spule00S.png",50,50,false,false);
    Image Spule45S=new Image("file:Images/Bauelementeschwarz/spule45S.png",50,50,false,false);
    Image Spule90S=new Image("file:Images/Bauelementeschwarz/spule90S.png",50,50,false,false);
    Image Spule135S=new Image("file:Images/Bauelementeschwarz/spule135S.png",50,50,false,false);

    Image Spule00T=new Image("file:Images/Bauelementetransparent/spule00T.png",50,50,false,false);
    Image Spule45T=new Image("file:Images/Bauelementetransparent/spule45T.png",50,50,false,false);
    Image Spule90T=new Image("file:Images/Bauelementetransparent/spule90T.png",50,50,false,false);
    Image Spule135T=new Image("file:Images/Bauelementetransparent/spule135T.png",50,50,false,false);


    ImageView imageviewSpule1 = new ImageView();

    public Spule(double x, double y, double Orientation)
    {
        super(x,y,Orientation);
        imageviewSpule1.setImage(Spule00S);
        //Rechtsklick Drehung bzw ändern des Bildes
        imageviewSpule1.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                //Welches Bild ist aktuell? Wegen drehen des Bildes
                if (event.getButton() == MouseButton.SECONDARY) {
                    if(imageviewSpule1.getImage()==Spule00S)
                    {imageviewSpule1.setImage(Spule45S);}
                    else if(imageviewSpule1.getImage()==Spule45S)
                    {imageviewSpule1.setImage(Spule90S);}
                    else if(imageviewSpule1.getImage()==Spule90S)
                    {imageviewSpule1.setImage(Spule135S);}
                    else if(imageviewSpule1.getImage()==Spule135S)
                    {imageviewSpule1.setImage(Spule00S);}
                }}});
        //zeichnet während des drag das Transparente Bild
        imageviewSpule1.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {
                //Welches Bild ist aktuell? Wegen drehen des Bildes
                if(imageviewSpule1.getImage()==Spule00S)
                {imageviewSpule1.setImage(Spule00T);}
                else if(imageviewSpule1.getImage()==Spule45S)
                {imageviewSpule1.setImage(Spule45T);}
                else if(imageviewSpule1.getImage()==Spule90S)
                {imageviewSpule1.setImage(Spule90T);}
                else if(imageviewSpule1.getImage()==Spule135S)
                {imageviewSpule1.setImage(Spule135T);}

                imageviewSpule1.setX(event.getSceneX()-25);
                imageviewSpule1.setY(event.getSceneY()-25);
            }});
        //Ändert das Bild in das mit schwarzen Hintergrund beim Losllassen der Maustaste
        imageviewSpule1.setOnMouseReleased(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {
                //Welches Bild ist aktuell? Wegen drehen des Bildes
                if(imageviewSpule1.getImage()==Spule00T)
                {imageviewSpule1.setImage(Spule00S);}
                else if(imageviewSpule1.getImage()==Spule45T)
                {imageviewSpule1.setImage(Spule45S);}
                else if(imageviewSpule1.getImage()==Spule90T)
                {imageviewSpule1.setImage(Spule90S);}
                else if(imageviewSpule1.getImage()==Spule135T)
                {imageviewSpule1.setImage(Spule135S);}
                imageviewSpule1.setX(rundenBauteile(event.getSceneX())-25);
                imageviewSpule1.setY(rundenBauteile(event.getSceneY())-25);
            }});

    }

    //Wird zum String xml hinzugefügt
    public String toxml(String xml){
        xml+=     "		<Spule>" + "Spule" + "</Spule>\n"
                + "		<xspu>"+(int)posX+"</xspu>\n"
                + "		<yspu>"+(int)posY+"</yspu>\n"
                + "		<spuor>"+(int)Orientation+"</spuor>\n\n";
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
    public void draw1(BorderPane borderPane)
    {
        imageviewSpule1.setX(posX-25);
        imageviewSpule1.setY(posY-25);
        borderPane.getChildren().add(imageviewSpule1);
    }
}
