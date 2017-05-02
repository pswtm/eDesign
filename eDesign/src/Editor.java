import Bauelemente.Spannungsquelle;
import Bauelemente.Widerstand;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.event.EventHandler;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import Bauelemente.Spannungsquelle;
import Bauelemente.Spule;
import Bauelemente.Widerstand;
import Bauelemente.Kondensator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.Event;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.util.Iterator;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;

//testkommentar f�r upload, bitte l�schen

public class Editor extends Application {

    //Büsra Test speichern

    //unser Fenster
    Stage window;
    String xmlstring="XML";
    File file;
    XMLCreater xmlcreater;
    final static int hoehe = 500, weite = 500;
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

        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 1000, 600);



        //System.out.println(eIcon.getWidth());

        /*
        * Menüleiste mit folgenden Punkten und Unterpunkten
        * Datei: Speichern, Speichern unter...
        * Bearbeiten:
        *
        *
        * !!! Unterstrichener Anfangsbuchstabe: IM CSS ändern
        * http://stackoverflow.com/questions/20541038/how-to-have-the-menu-mnemonic-underline-appearing-always
        * */
        //Menüpunkt "Datei" erstellen
        //TODO: Menüstruktur und Funktionen ergänzen
        Menu fileMenu = new Menu("_Datei");

        //Submenü/Unterpunkte zu fileMenu
        //Unterpunkt: Neu
        MenuItem newMenuItem = new MenuItem("Neu");
        newMenuItem.setOnAction(e -> {
            //TODO: neuen Schaltplan anlegen
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
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, editMenu, viewMenu, helpMenu);
        /*
        * Baukasten als eine VBox
        * folgende Icons sollen rein:
        * - Spannungsquelle
        * - Spule
        * - Kondensator
        * - Widerstand
        * */

        VBox kit = new VBox();
        kit.setPrefSize(100,100);
        //kit.setAlignment(Pos.BOTTOM_LEFT);
       kit.setStyle("-fx-background-color: black;"
                + "-fx-border-style: solid;"
                + "-fx-border-color: darkgrey;"
                + "-fx-border-width: 0 3 0 0;"
                + "-fx-padding: 5.5px;");



        //Icon für die Widerstand
        final ImageView imageviewWiderstand = new ImageView();
        Image widerstand=new Image("file:Images/widerstand.png",100,100,false,false);
        imageviewWiderstand.setImage(widerstand);
        kit.getChildren().addAll(imageviewWiderstand);
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
        kit.getChildren().addAll(imageviewKondensator);
        Image kondensatorSchrift= new Image("file:Images/kondensatorSchrift.png",100,100,false,false);

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
        kit.getChildren().addAll(imageviewSpule);
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
        final ImageView imageviewSpannungsquelle = new ImageView();
        Image spannungsquelle=new Image("file:Images/spannungsquelle.png",100,100,false,false);
        imageviewSpannungsquelle.setImage(spannungsquelle);
        kit.getChildren().addAll(imageviewSpannungsquelle);
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

        //TODO: Editorfläche
        Canvas canvas = new Canvas(1920,1080);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawLines(gc);

        borderPane.getChildren().add(canvas);






        //TODO: Raster
        Line line =new Line();
        line.setStartX(185);
        line.setStartY(185);
        line.setEndX(438);
        line.setEndY(438);
        line.setStroke(Color.WHITE);
        borderPane.getChildren().add(line);



        /*                      K
        * Ab hier ensteht das Layout des Editors
        * Top: Menüleiste als ein MenuBar
        * Left: Baukasten als eine VBox
        * Central: EditorFläche
        * */
        borderPane.setTop(menuBar);
        borderPane.setLeft(kit);
        borderPane.setRight(null);
        scene.getStylesheets().add("Css.css");
        window.setScene(scene);
        window.show();

    }
    //Linien für das Gitter Werden gezeichnet;
     private void drawLines(GraphicsContext gc){
         int i,j;
         gc.beginPath();
         gc.moveTo(500,500);
         gc.setStroke(Color.WHITE);

         for(i =0; i<=1920; i+=25){ //Senkrechte linien werden gezeichnet
             gc.moveTo(i,0);
             gc.lineTo(i, 1080);
         }
         for(j=0; j<=1080; j+=25){ //waagrechte linien werden gezichnet
             gc.moveTo(0,j);
             gc.lineTo(1920,j);
         }
         gc.stroke();
     }
    //Speichern unter
    public void saveas()
    {
        //Test Ints nachher löschen und richtige funktionen eintragen
        int xkon=1;
        int ykon=2;
        int xspu=3;
        int yspu=4;
        int xwid=5;
        int ywid=6;
        int xspa=7;
        int yspa=8;
        int konor=1;
        int spaor=2;
        int widor=3;
        int spuor=4;
        String xmlheader="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

        xmlstring+=xmlheader;
        xmlstring+= "			XML File\n"
                + "        <Header>\n"
                + "                <name>XML File</name>\n"
                + "		<Teile>\n\n"
                + "		<Kondensator>" + "Kondensator" + "</Kondensator>\n" //Kondensatorname in fett
                + "		<xkon>"+xkon+"</xkon>\n"
                + "		<ykon>"+ykon+"</ykon>\n"
                + "		<konor>"+konor+"</konor>\n\n"
                + "		<Spule>" + "Spule" + "</Spule>\n"
                + "		<xspu>"+xspu+"</xspu>\n"
                + "		<yspu>"+yspu+"</yspu>\n"
                + "		<spaor>"+spaor+"</spaor>\n\n"
                + "		<Widerstand>" + "Widerstand"  + "</Widerstand>\n"
                + "		<xwid>"+xwid+"</xwid>\n"
                + "		<ywid>"+ywid+"</ywid>\n"
                + "		<widor>"+widor+"</widor>\n\n"
                + "		<Spannungsquelle>" + "Spannungsquelle" +  "</Spannungsquelle>\n"
                + "		<xspa>"+xspa+"</xspa>\n"
                + "		<yspa>"+yspa+"</yspa>\n"
                + "		<spuor>"+spuor+"</spour>\n\n"
                +"\n"
                + "		</Teile>\n"
                + "        </Header>\n";
        //xmlcreater.create(xmlstring); //Warum funktioniert das nicht?
        FileChooser fileChoose= new FileChooser();
        fileChoose.setTitle("Speichern unter...");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("XML Dateien (*.xml)", "*.xml");
        fileChoose.getExtensionFilters().add(extFilter);
        file = fileChoose.showSaveDialog(window);
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(xmlstring);
            writer.close();

        }catch (Exception f){//Catch exception if any
            System.err.println("Error: " + f.getMessage());
        }

    }
    //Öffnen
    public void open()
    {
        int xkon, yspu,xwid,yspa,xspa,ywid,ykon,xspu;
        int konOr,spaOr,widOr,spuOr;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Öffnen");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("XML Dateien (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        file = fileChooser.showOpenDialog(window);
        if (file != null) {
            //Zeig den File Inhalt in Console an
            try (Scanner scanner = new Scanner(new File(file.toString()))) {
                //entscheidet ob Kondensator usw
                //TODO substrings noch bearbeiten, wegen Länge
                while(scanner.hasNext())
                {
                    String line=scanner.nextLine();
                    if(line.indexOf("Kondensator")!=-1)
                    {
                        line=scanner.nextLine();
                        xkon=Integer.parseInt(line.substring(8,9));
                        line=scanner.nextLine();
                        ykon=Integer.parseInt(line.substring(8,9));
                        line=scanner.nextLine();
                        konOr=Integer.parseInt(line.substring(9,10));
                        new Kondensator(xkon,ykon,konOr);
                    }
                    else if(line.indexOf("Spule")!=-1)
                    {
                        line=scanner.nextLine();
                        xspu=Integer.parseInt(line.substring(8,9));
                        line=scanner.nextLine();
                        yspu=Integer.parseInt(line.substring(8,9));
                        line=scanner.nextLine();
                        spaOr=Integer.parseInt(line.substring(9,10));
                        new Spule(xspu,yspu,spaOr);
                    }
                    else if(line.indexOf("Widerstand")!=-1)
                    {
                        line=scanner.nextLine();
                        xwid=Integer.parseInt(line.substring(8,9));
                        line=scanner.nextLine();
                        ywid=Integer.parseInt(line.substring(8,9));
                        line=scanner.nextLine();
                        widOr= Integer.parseInt(line.substring(9,10));
                        new Widerstand(xwid,ywid,widOr);
                    }

                    else if(line.indexOf("Spannungsquelle")!=-1)
                    {
                        line=scanner.nextLine();
                        xspa=Integer.parseInt(line.substring(8,9));
                        line=scanner.nextLine();
                        yspa=Integer.parseInt(line.substring(8,9));
                        line=scanner.nextLine();
                        spuOr =Integer.parseInt(line.substring(9,10));
                        new Spannungsquelle(xspa,yspa,spuOr);
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
                writer.write(xmlstring);
                writer.close();
            }catch (Exception f) {//Catch exception if any
                System.err.println("Error: " + f.getMessage());
            }
        }
    }

}
