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
        openMenuItem.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Öffnen");
            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("XML Dateien (*.xml)", "*.xml");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showOpenDialog(window);
            if (file != null) {
                //TODO: herausfinden wie man XML-Datei einliest
                //Zeig den File Inhalt in Console an
                try (Scanner scanner = new Scanner(new File(file.toString()))) {
                    String word = "Teile";

                    while(scanner.hasNext())
                    {
                        String line=scanner.nextLine();
                        if(line.indexOf("Spule")!=-1)
                        {
                            System.out.println("neue Spule");
                        }
                        else if(line.indexOf("Kondensator")!=-1)
                        {
                            System.out.println("neuer Kondensator");
                        }
                        else if(line.indexOf("Widerstand")!=-1)
                        {
                            System.out.println("neuer Widerstand");
                        }
                        else if(line.indexOf("Spannungsquelle")!=-1)
                        {
                            System.out.println("neue Spannungsquelle");
                        }
                    }
                } catch (Exception f){//Catch exception if any
                    System.err.println("Error: " + f.getMessage());
                }

            }


        });
        fileMenu.getItems().add(openMenuItem);
        fileMenu.getItems().add(new SeparatorMenuItem());

        fileMenu.getItems().add(new MenuItem("Speichern"));

        MenuItem save=new MenuItem("Speichern unter");
        save.setOnAction(e->{

            xmlstring+= "			XML Datei\n"
                    + "        <leer>\n"
                    + "                <name>XML File</name>\n"
                    + "		<Teile>\n\n"
                    + "		<Kondensator>" + "Kondensator" + "</Kondensator>\n" //Kondensatorname in fett
                    + "		<x>"+"xkon"+"</x>\n"
                    + "		<y>"+"ykon"+"</y>\n"
                    + "		<Spule>" + "Spule" + "</Spule>\n"
                    + "		<x>"+"xspu"+"</x>\n"
                    + "		<y>"+"yspu"+"</y>\n"
                    + "		<Widerstand>" + "Widerstand"  + "</Widerstand>\n"
                    + "		<x>"+"xwid"+"</x>\n"
                    + "		<y>"+"ywid"+"</y>\n"
                    + "		<Spannungsquelle>" + "Spannungsquelle" +  "</Spannungsquelle>\n"
                    + "		<x>"+"xspa"+"</x>\n"
                    + "		<y>"+"yspa"+"</y>\n"
                    +"\n"
                    + "		</Teile>\n"
                    + "        </leer>\n";

            FileChooser fileChoose= new FileChooser();
            fileChoose.setTitle("Speichern unter...");
            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("XML Dateien (*.xml)", "*.xml");
            fileChoose.getExtensionFilters().add(extFilter);
            File file = fileChoose.showSaveDialog(window);
            try {
                FileWriter writer = new FileWriter(file);
               writer.write(xmlstring);
               writer.close();

            }catch (Exception f){//Catch exception if any
                System.err.println("Error: " + f.getMessage());
            }


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

}
