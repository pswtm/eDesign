import Bauelemente.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.effect.Light;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.event.EventHandler;

import java.awt.*;
import java.util.Optional;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import Bauelemente.Spannungsquelle;
import Bauelemente.Widerstand;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.Event;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.print.DocFlavor;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.util.Iterator;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Editor extends Application {

    //unser Fenster
    Stage window;
    String xmlstring="";
    File file;
    String all="";
   // XMLCreater xmlcreater;
    int clickCount=0;
    double xStartLeitung=0,yStartLeitung=0,xEndLeitung=0,yEndLeitung=0;
    Dimension dim =Toolkit.getDefaultToolkit().getScreenSize();
    Color color=Color.rgb(238,238,238);
    private EventHandler<DragEvent> mIconDragOverRoot = null;
    Canvas canvas = new Canvas(dim.getWidth(),dim.getHeight());
    VBox vbox = new VBox();
    GraphicsContext gc = canvas.getGraphicsContext2D();
    MenuBar menuBar = new MenuBar();

    Image SpannungsquelleCanvas=new Image("file:Images/spannungsquelleCanvas.png",50,50,false,false);
    Image SpuleCanvas=new Image("file:Images/spuleCanvas.png",50,50,false,false);
    Image KondensatorCanvas=new Image("file:Images/kondensatorCanvas.png",50,50,false,false);
    Image WiderstandCanvas=new Image("file:Images/widerstandCanvas.png",50,50,false,false);
    BorderPane borderPane=new BorderPane();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //Allgemeine Fenstereinstellungen
        window = primaryStage;
        window.setTitle("eDesign");
        //TODO: ProgrammIcon einbauen
        window.getIcons().add(new Image ("file:Images/eIcon.png"));

        // BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 990, 600);

        //Menüpunkt "Datei" erstellen
        //TODO: Menüstruktur und Funktionen ergänzen
        Menu fileMenu = new Menu("_Datei");
        //Submenü/Unterpunkte zu fileMenu
        //Unterpunkt: Neu
        MenuItem newMenuItem = new MenuItem("Neu");
        newMenuItem.setOnAction(e -> {
            neu();

        });
        fileMenu.getItems().add(newMenuItem);

        //Unterpunkt: Öffnen
        MenuItem openMenuItem = new MenuItem("Öffnen");
        openMenuItem.setOnAction(e -> {open();});
        fileMenu.getItems().add(openMenuItem);
        fileMenu.getItems().add(new SeparatorMenuItem());


        MenuItem autosave=new MenuItem("Speichern");
        autosave.setOnAction(e->{
            autosave();
        });
        fileMenu.getItems().add(autosave);
        MenuItem save=new MenuItem("Speichern unter");
        save.setOnAction(e->{
            saveas();
        });
        fileMenu.getItems().add(save);
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(new MenuItem("Schließen"));

        //Menüpunkt "Bearbeiten" erstellen
        Menu editMenu = new Menu("_Bearbeiten");
        //Submenü/Unterpunkte zu editMenu
        editMenu.getItems().add(new MenuItem("Rückgängig"));
        editMenu.getItems().add(new SeparatorMenuItem());
        editMenu.getItems().add(new MenuItem("Ausschneiden"));
        editMenu.getItems().add(new MenuItem("Kopieren"));
        editMenu.getItems().add(new MenuItem("Einfügen"));
        editMenu.getItems().add(new MenuItem("Löschen"));
        editMenu.getItems().add(new SeparatorMenuItem());
        editMenu.getItems().add(new MenuItem("Screenshot"));

        //Menüpunkt "Ansicht" erstellen
        Menu viewMenu = new Menu("_Ansicht");
        //Submenü/Unterpunkte zu editMenu
        viewMenu.getItems().add(new MenuItem("Theme ändern"));
        viewMenu.getItems().add(new MenuItem("Schriftgröße ändern"));

        //Menüpunkt "Ansicht" erstellen
        Menu helpMenu = new Menu("_Hilfe");

        //Menüleiste zusammenüfhren
        //MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, editMenu, viewMenu, helpMenu);

        vbox.setPrefSize(100,100);
        //kit.setAlignment(Pos.BOTTOM_LEFT);

        vbox.setStyle("-fx-background-color: black;"
                + "-fx-border-style: solid;"
                + "-fx-border-color: darkgrey;"
                + "-fx-border-width: 0 3 0 0;"
                + "-fx-padding: 10.5px;");

        drawGitter(gc);

        borderPane.getChildren().add(canvas);

        //Icon für die Widerstand
        final ImageView imageviewWiderstand = new ImageView();
        Image widerstand=new Image("file:Images/widerstand.png",100,100,false,false);
        imageviewWiderstand.setImage(widerstand);
        vbox.getChildren().addAll(imageviewWiderstand);
        Image widerstandSchrift= new Image("file:Images/widerstandSchrift.png",100,100,false,false);


        //Mouse Over für das Einbleinden der IconBezeichnung
        imageviewWiderstand.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                imageviewWiderstand.setImage(widerstandSchrift);
            }});
        imageviewWiderstand.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                imageviewWiderstand.setImage(widerstand);
            }});

        //Icon für die Kondensator
        final ImageView imageviewKondensator = new ImageView();
        Image kondensator=new Image("file:Images/kondensator.png",100,100,false,false);
        imageviewKondensator.setImage(kondensator);
        vbox.getChildren().addAll(imageviewKondensator);
        Image kondensatorSchrift= new Image("file:Images/kondensatorSchrift.png",100,100,false,false);


        Image Spannungsquelle0=new Image("file:Images/BauelementIcon/spannungsquelleN_S.png",50,50,false,false);
        Image Spannungsquelle1=new Image("file:Images/BauelementIcon/spannungsquelleNO_SW.png",50,50,false,false);
        Image Spannungsquelle2=new Image("file:Images/BauelementIcon/spannungsquelleO_W.png",50,50,false,false);
        Image Spannungsquelle3=new Image("file:Images/BauelementIcon/spannungsquelleNW_SO.png",50,50,false,false);

        //Mouse Over für das Einbleinden der IconBezeichnung
        imageviewKondensator.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                imageviewKondensator.setImage(kondensatorSchrift);
            }});
        imageviewKondensator.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                imageviewKondensator.setImage(kondensator);
            }});


        //Icon für die Spule
        final ImageView imageviewSpule = new ImageView();
        Image spule=new Image("file:Images/spule.png",100,100,false,false);
        imageviewSpule.setImage(spule);
        vbox.getChildren().addAll(imageviewSpule);
        Image spuleSchrift= new Image("file:Images/spuleSchrift.png",100,100,false,false);

        //Mouse Over für das Einbleinden der IconBezeichnung
        imageviewSpule.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                imageviewSpule.setImage(spuleSchrift);
            }});
        imageviewSpule.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                imageviewSpule.setImage(spule);
            }});

        //Icon für die spannungsquelle
         ImageView imageviewSpannungsquelle = new ImageView();
         ImageView imageviewSpannungsquelle1 = new ImageView();
        Image spannungsquelle=new Image("file:Images/spannungsquelle.png",100,100,false,false);
        imageviewSpannungsquelle.setImage(spannungsquelle);
        imageviewSpannungsquelle1.setImage(Spannungsquelle2);
        vbox.getChildren().addAll(imageviewSpannungsquelle);
        Image spannungsquelleSchrift= new Image("file:Images/spannungsquelleSchrift.png",100,100,false,false);

        //Mouse Over für das Einbleinden der IconBezeichnung
        imageviewSpannungsquelle.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                imageviewSpannungsquelle.setImage(spannungsquelleSchrift);
            }});
        imageviewSpannungsquelle.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                imageviewSpannungsquelle.setImage(spannungsquelle);
            }});

        // Drag and Drop Versuch

        imageviewSpannungsquelle.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {
                imageviewSpannungsquelle1.setX(event.getSceneX()-25);
                imageviewSpannungsquelle1.setY(event.getSceneY()-25);
                borderPane.getChildren().remove(imageviewSpannungsquelle1);
                borderPane.getChildren().add(imageviewSpannungsquelle1);
            }
        });

        imageviewSpannungsquelle.setOnMouseReleased(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {
                borderPane.getChildren().remove(imageviewSpannungsquelle1);
                double x=0,y=0;
                x=rundenBauteile(event.getSceneX());
                y=rundenBauteile(event.getSceneY());
                Spannungsquelle spannungsquelle=new Spannungsquelle(x,y,0);
                //spannungsquelle.draw(gc,0);
                spannungsquelle.draw1(borderPane);
                xmlstring=spannungsquelle.toxml(xmlstring);

            }
        });
        imageviewSpule.setOnMouseReleased(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {
                double x=0,y=0;
                //System.out.println("losgelassen an: X: "+event.getSceneX()+" Y: "+event.getSceneY());
                x=rundenBauteile(event.getSceneX());
                y=rundenBauteile(event.getSceneY());
                Spule spule=new Spule(x,y,0);
                //spule.draw(gc,0);
                spule.draw1(borderPane);
                xmlstring=spule.toxml(xmlstring);
            }
        });
        imageviewKondensator.setOnMouseReleased(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {
                double x=0,y=0;
                //System.out.println("losgelassen an: X: "+event.getSceneX()+" Y: "+event.getSceneY());
                x=rundenBauteile(event.getSceneX());
                y=rundenBauteile(event.getSceneY());
                Kondensator kondensator=new Kondensator(x,y,0);
                //kondensator.draw(gc,0);
                kondensator.draw1(borderPane);
                xmlstring=kondensator.toxml(xmlstring);
            }
        });
        imageviewWiderstand.setOnMouseReleased(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {
                double x=0,y=0;
                //System.out.println("losgelassen an: X: "+event.getSceneX()+" Y: "+event.getSceneY());
                x=rundenBauteile(event.getSceneX());
                y=rundenBauteile(event.getSceneY());
                Widerstand widerstand=new Widerstand(x,y,0);
                //widerstand.draw(gc,0);
                widerstand.draw1(borderPane);
                xmlstring=widerstand.toxml(xmlstring);

            }
        });

    /*
        //Das ist Drag Drop um eine Datei in das Programm zu ziehen
        scene.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                System.out.println("setOnDragOver");
            }
        });
        scene.setOnDragDropped(new EventHandler<DragEvent>()  {
            @Override
            public void handle(DragEvent event) {
                System.out.println("setOnDragDropped");
            }
        });
        scene.setOnDragExited(new EventHandler<DragEvent>()  {
            @Override
            public void handle(final DragEvent event) {
                System.out.println("setOnDragExited");
            }
        });
    */
        //Neue Leitung zeichnen mit 2 Mausklicks auf Canvas
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event)
        {
        drawLines(event, gc);

        }});

        ImageView imageviewSpannungsquelleCanvas = new ImageView();
        imageviewSpannungsquelleCanvas.setImage(Spannungsquelle0);
        imageviewSpannungsquelleCanvas.setX(475);
        imageviewSpannungsquelleCanvas.setY(275);
        borderPane.getChildren().add(imageviewSpannungsquelleCanvas);
        imageviewSpannungsquelleCanvas.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY) {
                    if(imageviewSpannungsquelleCanvas.getImage()==Spannungsquelle0){
                       // System.out.println("Rechtsklick Maus image2");
                        imageviewSpannungsquelleCanvas.setImage(Spannungsquelle1);
                    }
                    else if(imageviewSpannungsquelleCanvas.getImage()==Spannungsquelle1)
                    {
                        imageviewSpannungsquelleCanvas.setImage(Spannungsquelle2);
                    }
                    else if(imageviewSpannungsquelleCanvas.getImage()==Spannungsquelle2)
                    {
                        imageviewSpannungsquelleCanvas.setImage(Spannungsquelle3);
                    }
                    else if(imageviewSpannungsquelleCanvas.getImage()==Spannungsquelle3)
                    {
                        imageviewSpannungsquelleCanvas.setImage(Spannungsquelle0);
                    }
                }
            }});
        //Unwichtige Linie zum testen
        Line line =new Line();
        line.setStartX(185);
        line.setStartY(185);
        line.setEndX(438);
        line.setEndY(438);
        line.setStroke(Color.WHITE);
        line.setStrokeWidth(10);
        borderPane.getChildren().add(line);
        line.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton()== MouseButton.SECONDARY) {

                    System.out.println("Rechtsklick Maus");
                    line.setStroke(Color.color(Math.random(),Math.random(),Math.random()));
                }
                else if(event.getButton()==MouseButton.PRIMARY)
                {
                    System.out.println("Linksklick Maus");
                    line.setStroke(Color.WHITE);
                }
            }});

        borderPane.setTop(menuBar);
        borderPane.setLeft(vbox);

        scene.getStylesheets().add("Css.css");
        window.setScene(scene);
        window.show();
    }
    //Linien für das Gitter Werden gezeichnet;
     private void drawGitter(GraphicsContext gc){
         int i,j;
         //System.out.println("Bildschirmgröße: "+dim.getWidth()+" "+dim.getHeight());
         gc.beginPath();
         gc.moveTo(0,0);
         gc.setStroke(Color.WHITE);
         for(i =0; i<=dim.getWidth(); i+=25){ //Senkrechte linien werden gezeichnet
             gc.moveTo(i,0);
             gc.lineTo(i, dim.getHeight());

         }
         for(j=0; j<=dim.getHeight(); j+=25){ //waagrechte linien werden gezichnet
             gc.moveTo(0,j);
             gc.lineTo(dim.getWidth(),j);
         }
         gc.stroke();
     }
    //Speichern unter
    public void saveas()
    {
        String xmlheader="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        String start= "			XML File\n"
                + "        <Header>\n"
                + "                <name>XML File</name>\n"
                + "		<Teile>\n\n";
        String end="		</Teile>\n"
                + "        </Header>\n";
         all=start+xmlstring+end;

        FileChooser fileChoose= new FileChooser();
        fileChoose.setTitle("Speichern unter...");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("XML Dateien (*.xml)", "*.xml");
        fileChoose.getExtensionFilters().add(extFilter);
        file = fileChoose.showSaveDialog(window);
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(all);
            writer.close();

        }catch (Exception f){//Catch exception if any
            System.err.println("Error: " + f.getMessage());
        }

    }
    //Öffnen
    public void open()
    {
        double xkon, yspu,xwid,yspa,xspa,ywid,ykon,xspu,xles,yles,xlee,ylee;
        double konOr,spaOr,widOr,spuOr;
       ;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Öffnen");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("XML Dateien (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        file = fileChooser.showOpenDialog(window);
        deleteall();
        if (file != null) {
            //Zeig den File Inhalt in Console an
            try (Scanner scanner = new Scanner(new File(file.toString()))) {
                //entscheidet ob Kondensator usw
                while(scanner.hasNext())
                {
                    String line=scanner.nextLine();
                    if(line.indexOf("Kondensator")!=-1)
                    {
                        line=scanner.nextLine();
                        xkon=(double)Integer.parseInt(line.substring(line.indexOf("<xkon>")+6, line.indexOf("</xkon>")));
                        line=scanner.nextLine();
                        ykon=(double)Integer.parseInt(line.substring(line.indexOf("<ykon>")+6, line.indexOf("</ykon>")));
                        line=scanner.nextLine();
                        konOr=(double)Integer.parseInt(line.substring(line.indexOf("<konor>")+7, line.indexOf("</konor>")));
                        Kondensator kondensator1=new Kondensator(xkon,ykon,konOr);
                        //kondensator1.draw(gc,0);
                        kondensator1.draw1(borderPane);
                        xmlstring=kondensator1.toxml(xmlstring);
                    }
                    else if(line.indexOf("Spule")!=-1)
                    {
                        line=scanner.nextLine();
                        xspu=Integer.parseInt(line.substring(line.indexOf("<xspu>")+6, line.indexOf("</xspu>")));
                        line=scanner.nextLine();
                        yspu=Integer.parseInt(line.substring(line.indexOf("<yspu>")+6, line.indexOf("</yspu>")));
                        line=scanner.nextLine();
                        spuOr=Integer.parseInt(line.substring(line.indexOf("<spuor>")+7, line.indexOf("</spuor>")));
                       Spule spule1= new Spule(xspu,yspu,spuOr);
                        xmlstring=spule1.toxml(xmlstring);
                       //spule1.draw(gc,0);
                       spule1.draw1(borderPane);
                    }
                    else if(line.indexOf("Widerstand")!=-1)
                    {
                        line=scanner.nextLine();
                        xwid=Integer.parseInt(line.substring(line.indexOf("<xwid>")+6, line.indexOf("</xwid>")));
                        line=scanner.nextLine();
                        ywid=Integer.parseInt(line.substring(line.indexOf("<ywid>")+6, line.indexOf("</ywid>")));
                        line=scanner.nextLine();
                        widOr= Integer.parseInt(line.substring(line.indexOf("<widor>")+7, line.indexOf("</widor>")));
                        Widerstand widerstand1=new Widerstand(xwid,ywid,widOr);
                        xmlstring=widerstand1.toxml(xmlstring);
                        //widerstand1.draw(gc,0);
                        widerstand1.draw1(borderPane);
                    }

                    else if(line.indexOf("Spannungsquelle")!=-1)
                    {
                        line=scanner.nextLine();
                        xspa=Integer.parseInt(line.substring(line.indexOf("<xspa>")+6, line.indexOf("</xspa>")));
                        line=scanner.nextLine();
                        yspa=Integer.parseInt(line.substring(line.indexOf("<yspa>")+6, line.indexOf("</yspa>")));
                        line=scanner.nextLine();
                        spaOr =Integer.parseInt(line.substring(line.indexOf("<spaor>")+7, line.indexOf("</spaor>")));
                        Spannungsquelle spannungsquelle1=new Spannungsquelle(xspa,yspa,spaOr);
                        xmlstring=spannungsquelle1.toxml(xmlstring);
                        //spannungsquelle1.draw(gc, 0);
                        spannungsquelle1.draw1(borderPane);
                    }
                    else if(line.indexOf("Leitung")!=-1)
                    {

                        //Todo
                        line=scanner.nextLine();
                        xles=Integer.parseInt(line.substring(line.indexOf("<xles>")+6, line.indexOf("</xles>")));
                        line=scanner.nextLine();
                        yles =Integer.parseInt(line.substring(line.indexOf("<yles>")+6, line.indexOf("</yles>")));
                        line=scanner.nextLine();
                        xlee=Integer.parseInt(line.substring(line.indexOf("<xlee>")+6, line.indexOf("</xlee>")));
                        line=scanner.nextLine();
                        ylee=Integer.parseInt(line.substring(line.indexOf("<ylee>")+6, line.indexOf("</ylee>")));
                        System.out.println("xles: "+xles+"yles: "+yles+"xlee: "+xlee+"ylee: "+ylee);
                        Leitung leitung1=new Leitung(xles,yles,0,xlee,ylee);
                        xmlstring=leitung1.toxml(xmlstring);
                        gc.setLineWidth(4);
                        gc.setStroke(color);
                        gc.strokeLine(leitung1.getxstart(),leitung1.getystart(),leitung1.getxend(),leitung1.getyend());
                        gc.setStroke(Color.WHITE);
                        gc.setLineWidth(1);
                    }
                }
            } catch (Exception f){//Catch exception if any
                System.err.println("Error: " + f.getMessage());
            }
        }
    }
    //Speichern
    public void autosave()
    {
        if(file==null) {
            System.out.println("Error kein Dateipfad vorhanden");
        }
        else
        {
            try {
                FileWriter writer = new FileWriter(file);
                writer.write(all);
                writer.close();
            }catch (Exception f) {//Catch exception if any
                System.err.println("Error: " + f.getMessage());
            }
        }
    }
    public void drawLines(MouseEvent event, GraphicsContext gc)
    {
    clickCount++;
    clickCount=clickCount%2;
    if(event.getButton()== MouseButton.PRIMARY) {

        if(clickCount==1)
        {
            xStartLeitung=rundenLeitungen(event.getSceneX());
            yStartLeitung=rundenLeitungen(event.getSceneY());
        }
        else if (clickCount==0)
        {
            xEndLeitung=rundenLeitungen(event.getSceneX());
            yEndLeitung=rundenLeitungen(event.getSceneY());
        }
        if(xStartLeitung!=0 && yStartLeitung!=0&&xEndLeitung!=0&&yEndLeitung!=0)
        {
            Leitung leitung=new Leitung(xStartLeitung,yStartLeitung,0,xEndLeitung,yEndLeitung);
            //Todo entscheiden wie man drauf malt bzw es als Element fassen kann
            //borderPane.getChildren().add(leitung.getline());
            gc.setLineWidth(4);
            gc.setStroke(color);
            gc.strokeLine(leitung.getxstart(),leitung.getystart(),leitung.getxend(),leitung.getyend());
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(1);
            xStartLeitung=0;
            yStartLeitung=0;
            xEndLeitung=0;
            yEndLeitung=0;
            xmlstring=leitung.toxml(xmlstring);
        }
        else return;
    }
    }
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
    public void neu()
    {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        xmlstring="";
        alert.setTitle("Neues Projekt anlegen");
        alert.setHeaderText("");
        alert.setContentText("Wollen Sie das aktuelle Projekt löschen und ein neues anlegen? Alle nicht gespeicherten Änderungen werden gelöscht.");
        Optional<ButtonType> result=alert.showAndWait();
        if(result.get()==ButtonType.OK) {
            deleteall();
        }
        else return;

    }
    public void deleteall()
    {
        xmlstring="";
        //Todo brauchen wir später nicht mehr
        gc.clearRect(0, 0, dim.getWidth(), dim.getHeight());
        drawGitter(gc);
        //Todo bis hier
        borderPane.getChildren().clear();
        borderPane.getChildren().add(canvas);
        borderPane.setTop(menuBar);
        borderPane.setLeft(vbox);
    }
}
