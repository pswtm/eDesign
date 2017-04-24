import Bauelemente.Spannungsquelle;
import Bauelemente.Widerstand;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import Bauelemente.Spannungsquelle;
import Bauelemente.Spule;
import Bauelemente.Widerstand;
import Bauelemente.Kondensator;


import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.util.Iterator;
import javafx.scene.image.ImageView;


public class Editor extends Application {

    //unser Fenster
    Stage window;
    String xmlstring="XML";
    File file;
 XMLCreater xmlcreater;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //Allgemeine Fenstereinstellungen
        window = primaryStage;
        window.setTitle("eDesign");
        //TODO: ProgrammIcon einbauen
        window.getIcons().add(new Image ("file:Images/eIcon.jpg"));
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
        kit.setPrefWidth(100);

        //Icon Versuch
        final ImageView imageview = new ImageView();
        Image image=new Image("file:Images/widerstand.png");
        imageview.setX(1300);
        imageview.setY(1300);
        imageview.setImage(image);
        kit.getChildren().addAll(imageview);

        //TODO: Raster


        /*
        * Ab hier ensteht das Layout des Editors
        * Top: Menüleiste als ein MenuBar
        * Left: Baukasten als eine VBox
        * Central: EditorFläche
        * */
        BorderPane layout = new BorderPane();
        layout.setTop(menuBar);


        Scene scene = new Scene(layout, 1000, 600);
        scene.getStylesheets().add("Css.css");
        //scene.setRoot(kit); //dann nur noch icon
        window.setScene(scene);
        window.show();
    }
public void saveas()
{
    String xkon="1";
    String ykon="2";
    String xspu="3";
    String yspu="4";
    String xwid="5";
    String ywid="6";
    String xspa="7";
    String yspa="8";
    String konor="1";
    String spaor="2";
    String widor="3";
    String spuor="4";
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
