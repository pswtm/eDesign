package Bauelemente;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Leitung extends Bauelement{
    public double xend;
    public double yend;
    double xs,ys,xe,ye;
    Color color=Color.rgb(238,238,238);
    Line line =new Line();


    public Leitung(double xstart, double ystart, double Orientation, double xende, double yende)
    {
        super(xstart,ystart,Orientation);
        this.xend=xende;
        this.yend=yende;
        //System.out.println("Class Leitung: "+xstart+","+ystart+","+xend+","+yend);
        line.setStartX(xstart);
        line.setStartY(ystart);
        line.setEndX(xend);
        line.setEndY(yend);
        line.setStroke(color);
        line.setStrokeWidth(5);

        //zeichnet während des drag
        line.setOnMouseDragged(new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event)
        {
            posX=event.getSceneX()+xs;
            posY=event.getSceneY()+ys;
            xend=event.getSceneX()+xe;
            yend=event.getSceneY()+ye;

            line.setStartX(posX);
            line.setStartY(posY);
            line.setEndX(xe+event.getSceneX());
            line.setEndY(ye+event.getSceneY());
        }});
        //um den Abstand von X und Y Koordinaten zu der Maus zu bekommen
        line.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                xs=posX-event.getSceneX();
                ys=posY-event.getSceneY();
                xe=xend-event.getSceneX();
                ye=yend-event.getSceneY();
            }});
        //zeichnet wenn Maus losgelassen
        line.setOnMouseReleased(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {
                posX=rundenLeitungen(event.getSceneX()+xs);
                posY=rundenLeitungen(event.getSceneY()+ys);
                xend=rundenLeitungen(event.getSceneX()+xe);
                yend=rundenLeitungen(event.getSceneY()+ye);
                line.setStartX(posX);
                line.setStartY(posY);
                line.setEndX(xend);
                line.setEndY(yend);



            }});
    }
    //Wird zum String xml hinzugefügt
    public String toxml(String xml){
        xml+=     "		<Leitung>" + "Leitung" + "</Leitung>\n"
                + "		<xles>"+(int)posX+"</xles>\n"
                + "		<yles>"+(int)posY+"</yles>\n"
                + "		<xlee>"+(int)xend+"</xlee>\n"
                + "		<ylee>"+(int)yend+"</ylee>\n\n";
        return xml;
    }
    public void draw1(BorderPane borderPane)
    {
        borderPane.getChildren().add(line);
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
    /*
    public void draw(GraphicsContext gc)
    {
        gc.setLineWidth(5);
        gc.setStroke(color);
        gc.strokeLine(getxstart(),getystart(),getxend(),getyend());
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1);
    }
    */

}
