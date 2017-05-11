package Bauelemente;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Leitung extends Bauelement{
    double xend;
    double yend;
    Color color=Color.rgb(238,238,238);
    Line line =new Line();


    public Leitung(double xstart, double ystart, double Orientation, double xend, double yend)
    {
        super(xstart,ystart,Orientation);
        this.xend=xend;
        this.yend=yend;
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
            //TOdo Berechnuns wo die Linie sein muss
            //Rechnung wo Maus ist und Länge der Linie zum positionieren
            double xs=xstart-event.getSceneX();
            double ys=ystart-event.getSceneY();
            double xe=xend-event.getSceneX();
            double ye=yend-event.getSceneY();
            System.out.println("dragged xs:"+xs+" ys: "+ys+" Maus x: "+event.getSceneX()+" Maus y: "+event.getSceneY());
            line.setStartX(xs);
            line.setStartY(ys);
            line.setEndX(xe);
            line.setEndY(ye);
        }});
        //zeichnet wenn Maus losgelassen
        line.setOnMouseReleased(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {
                System.out.println("dropped");
                line.setStartX(xstart);
                line.setStartY(ystart);
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
    public double getxstart() {return posX;}
    public double getystart() {return posY;}
    public double getxend()   {return xend;}
    public double getyend()   {return yend;}
    public Line getline()  {return line;}

}
