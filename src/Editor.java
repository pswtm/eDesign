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
import java.nio.charset.StandardCharsets;

public class Editor extends Application {
    //Variablen die auch in den Funktionen verwendet werden
    Stage window;
    String xmlstring="";
    File file;
    String all="";
    int clickCount=0;
    double xStartLeitung=0,yStartLeitung=0,xEndLeitung=0,yEndLeitung=0;
    Dimension dim =Toolkit.getDefaultToolkit().getScreenSize();
    Color color=Color.rgb(238,238,238);
    private EventHandler<DragEvent> mIconDragOverRoot = null;
    Canvas canvas = new Canvas(dim.getWidth(),dim.getHeight());
    VBox vbox = new VBox();
    GraphicsContext gc = canvas.getGraphicsContext2D();
    MenuBar menuBar = new MenuBar();
    BorderPane borderPane=new BorderPane();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //Allgemeine Fenstereinstellungen
        window = primaryStage;
        window.setTitle("eDesign");
        window.getIcons().add(new Image ("file:Images/eIcon.png"));
        Scene scene = new Scene(borderPane, 990, 600);
        //Todo Taste Entf zum löschen des Objekts
        scene.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
        @Override
         public void handle(KeyEvent event)
        {
        if(event.getCode()==KeyCode.DELETE)
            System.out.println("Lösch aktion noch nicht erledigt");
        }
        });
        //Menüpunkt "Datei" erstellen
        //TODO: Menüstruktur und Funktionen ergänzen
        Menu fileMenu = new Menu("_Datei");
        //Submenü/Unterpunkte zu fileMenu
        //Unterpunkt: Neu
        MenuItem newMenuItem = new MenuItem("Neu");
        newMenuItem.setOnAction(e -> {
            //Alles löschen
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
            //Speichert in das zuvor gespeicherte File
            autosave();
        });
        fileMenu.getItems().add(autosave);
        MenuItem save=new MenuItem("Speichern unter");
        save.setOnAction(e->{
            //Speichern unter
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

        //Menüpunkt "Hilfe" erstellen
        Menu helpMenu = new Menu("_Hilfe");

        //Menüleiste zusammenführen
        menuBar.getMenus().addAll(fileMenu, editMenu, viewMenu, helpMenu);
        //Vbox Größe
        vbox.setPrefSize(100,100);
        //VBox Style
        vbox.setStyle("-fx-background-color: black;"
                + "-fx-border-style: solid;"
                + "-fx-border-color: darkgrey;"
                + "-fx-border-width: 0 3 0 0;"
                + "-fx-padding: 10.5px;");
        //Zeichnet das Gitter
        drawGitter(gc);
        borderPane.getChildren().add(canvas);

        //Icon für den Widerstand auf der Vbox
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

        //Icon für den Kondensator auf der Vbox
        final ImageView imageviewKondensator = new ImageView();
        Image kondensator=new Image("file:Images/kondensator.png",100,100,false,false);
        imageviewKondensator.setImage(kondensator);
        vbox.getChildren().addAll(imageviewKondensator);
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

        //Icon für die Spule auf der Vbox
        final ImageView imageviewSpule = new ImageView();
        Image spule=new Image("file:Images/spule.png",100,100,false,false);
        imageviewSpule.setImage(spule);
        vbox.getChildren().addAll(imageviewSpule);
        Image spuleSchrift= new Image("file:Images/spuleSchrift.png",100,100,false,false);
        //Mouse Over für das Einblenden der IconBezeichnung
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

        //Icon für die spannungsquelle auf der Vbox
        ImageView imageviewSpannungsquelle = new ImageView();
        Image spannungsquelle=new Image("file:Images/spannungsquelle.png",100,100,false,false);
        imageviewSpannungsquelle.setImage(spannungsquelle);
        vbox.getChildren().addAll(imageviewSpannungsquelle);
        Image spannungsquelleSchrift= new Image("file:Images/spannungsquelleSchrift.png",100,100,false,false);
        //Mouse Over für das Einbleinden der IconBezeichnung
        imageviewSpannungsquelle.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {imageviewSpannungsquelle.setImage(spannungsquelleSchrift);
            }});
        imageviewSpannungsquelle.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {imageviewSpannungsquelle.setImage(spannungsquelle);
            }});

        //Images beim drag von der Vbox auf das Borderlane
        ImageView imageviewSpannungsquelle1 = new ImageView();
        Image spannungsquelle00T =new Image("file:Images/Bauelementetransparent/spannungsquelle00T.png",50,50,false,false);
        imageviewSpannungsquelle1.setImage(spannungsquelle00T);

        ImageView imageviewSpule1 = new ImageView();
        Image spule00T =new Image("file:Images/Bauelementetransparent/spule00T.png",50,50,false,false);
        imageviewSpule1.setImage(spule00T);

        ImageView imageviewKondensator1 = new ImageView();
        Image kondensator00T =new Image("file:Images/Bauelementetransparent/kondensator00T.png",50,50,false,false);
        imageviewKondensator1.setImage(kondensator00T);

        ImageView imageviewWiderstand1 = new ImageView();
        Image widerstand00T =new Image("file:Images/Bauelementetransparent/widerstand00T.png",50,50,false,false);
        imageviewWiderstand1.setImage(widerstand00T);

        // Drag and Drop von VBox aufs Borderpane
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
        imageviewSpule.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {
                imageviewSpule1.setX(event.getSceneX()-25);
                imageviewSpule1.setY(event.getSceneY()-25);
                borderPane.getChildren().remove(imageviewSpule1);
                borderPane.getChildren().add(imageviewSpule1);
            }
        });
        imageviewSpule.setOnMouseReleased(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {
                borderPane.getChildren().remove(imageviewSpule1);
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
        imageviewKondensator.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {
                imageviewKondensator1.setX(event.getSceneX()-25);
                imageviewKondensator1.setY(event.getSceneY()-25);
                borderPane.getChildren().remove(imageviewKondensator1);
                borderPane.getChildren().add(imageviewKondensator1);
            }
        });
        imageviewKondensator.setOnMouseReleased(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {
                borderPane.getChildren().remove(imageviewKondensator1);
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
        imageviewWiderstand.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {
                imageviewWiderstand1.setX(event.getSceneX()-25);
                imageviewWiderstand1.setY(event.getSceneY()-25);
                borderPane.getChildren().remove(imageviewWiderstand1);
                borderPane.getChildren().add(imageviewWiderstand1);
            }
        });
        imageviewWiderstand.setOnMouseReleased(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {
                borderPane.getChildren().remove(imageviewWiderstand1);

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

        //Mülleimer
        ImageView imageMuelleimer=new ImageView();
        Image muelleimerZu= new Image("file:Images/muelleimerOffen.png",100,75,false,false);
        Image muelleimerOffen=new Image("file:Images/muelleimerOffen.png",100,75,false,false);
        imageMuelleimer.setImage(muelleimerZu);
        imageMuelleimer.setX(120);
        imageMuelleimer.setY(400);
        //borderPane.getChildren().add(imageMuelleimer);
        vbox.getChildren().addAll(imageMuelleimer);
        /*
        imageMuelleimer.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {imageMuelleimer.setImage(muelleimerOffen);}});
        imageMuelleimer.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event)
            {imageMuelleimer.setImage(muelleimerZu);}});
*/
        scene.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                } else {
                    event.consume();
                }
                //System.out.println("setOnDragOver");
            }});
        scene.setOnDragDropped(new EventHandler<DragEvent>()  {
            @Override
            public void handle(DragEvent event) {

                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles())
                {
                    success = true;
                    for (File file:db.getFiles())
                    {
                        //entscheidet ob es ein xml file ist
                if(file.getName().toLowerCase().endsWith(".xml"))
                {
                    Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Achtung");
                    alert.setHeaderText("");
                    alert.setContentText("Wollen sie das neue Projekt: \""+file.getName()+"\" laden? Alle nicht gespeicherten Änderungen gehen verloren");
                    Optional<ButtonType> result=alert.showAndWait();
                    if(result.get()==ButtonType.OK) {FileReader(file);}
                    else return;
                }
                else
                    {
                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Falsches Dateiformat");
                    alert.setHeaderText("");
                    alert.setContentText("Das Programm unterstützt nur das XML Format");
                    alert.showAndWait();
                    }
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            }});

        //Neue Leitung zeichnen mit 2 Mausklicks auf Canvas
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event)
        {drawLines(event, gc);}});

        //leere Vbox für die rechte Spalte
        VBox vboxLeer = new VBox();
        vboxLeer.setPrefSize(15,100);
        //VBox Style
        vboxLeer.setStyle("-fx-background-color: black;"
                + "-fx-border-style: solid;"
                + "-fx-border-color: darkgrey;"
                + "-fx-border-width: 0 0 0 3;"
                + "-fx-padding: 10.5px;");


        //HBox als untere Leiste
        HBox hboxLeiste = new HBox();
        hboxLeiste.setPrefSize(100,15);
        //VBox Style
        hboxLeiste.setStyle("-fx-background-color: black;"
                + "-fx-border-style: solid;"
                + "-fx-border-color: darkgrey;"
                + "-fx-border-width: 3 0 0 0;"
                + "-fx-padding: 10.5px;");


        //Darstellung der menubar und vbox auf Borderpane mit Stylesheet
        borderPane.setTop(menuBar);
        borderPane.setLeft(vbox);
        borderPane.setRight(vboxLeer);
        borderPane.setBottom(hboxLeiste);
        scene.getStylesheets().add("Css.css");
        window.setScene(scene);
        window.show();
    }
    //Linien für das Gitter Werden gezeichnet;
     private void drawGitter(GraphicsContext gc){
         int i,j;
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
        //XKondensator, YKondensator, XlineStart, XLineEnd
        double xkon, yspu,xwid,yspa,xspa,ywid,ykon,xspu,xles,yles,xlee,ylee;
        //KondensatorOrientation
        double konOr,spaOr,widOr,spuOr;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Öffnen");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("XML Dateien (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        file = fileChooser.showOpenDialog(window);
        deleteall();
        FileReader(file);
    }
    //Speichern
    public void autosave()
    {
        if(file==null) {System.out.println("Error kein Dateipfad vorhanden");}
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
    //Zeichne Linien mit 2 Mausklicks
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
            Leitung leitung1=new Leitung(xStartLeitung,yStartLeitung,0,xEndLeitung,yEndLeitung);
            leitung1.draw1(borderPane);
            xmlstring=leitung1.toxml(xmlstring);
            xStartLeitung=0;
            yStartLeitung=0;
            xEndLeitung=0;
            yEndLeitung=0;

        }
        else return;
    }
    }
    //Snap ans Raster der Bauteile
    public double rundenBauteile(double runden) {
        if (runden % 50 < 25) {
            return runden - (runden % 50);
        } else if (runden % 50 >= 25) {
            return runden + (50 - (runden % 50));
        } else return 0;
    }
    //Snap der Linien ans Raster
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
    //Neu
    public void neu()
    {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Neues Projekt anlegen");
        alert.setHeaderText("");
        alert.setContentText("Wollen Sie das aktuelle Projekt löschen und ein neues anlegen? Alle nicht gespeicherten Änderungen werden gelöscht.");
        Optional<ButtonType> result=alert.showAndWait();
        if(result.get()==ButtonType.OK) {
            deleteall();
            xmlstring="";
        }
        else return;

    }
    //Löscht alles auf dem Borderpane und Graphic Content
    public void deleteall()
    {
        xmlstring="";
        //gc.clearRect(0, 0, dim.getWidth(), dim.getHeight());
        //drawGitter(gc);
        borderPane.getChildren().clear();
        borderPane.getChildren().add(canvas);
        borderPane.setTop(menuBar);
        borderPane.setLeft(vbox);
    }
    public void FileReader(File file)
    {
        //XKondensator, YKondensator, XlineStart, XLineEnd
        double xkon, yspu,xwid,yspa,xspa,ywid,ykon,xspu,xles,yles,xlee,ylee;
        //KondensatorOrientation
        double konOr,spaOr,widOr,spuOr;
        {
            //prüft ob das file etwas beinhaltet
            if (file != null) {
                //Löscht die borderpane
                deleteall();
                //Zeig den File Inhalt in String
                try (Scanner scanner = new Scanner(new File(file.toString()))) {
                    //entscheidet ob Kondensator usw
                    while(scanner.hasNext())
                    {
                        String line=scanner.nextLine();
                        if(line.indexOf("Kondensator")!=-1)
                        {
                            //Schreibt die Werte aus dem String in Variable
                            line=scanner.nextLine();
                            xkon=(double)Integer.parseInt(line.substring(line.indexOf("<xkon>")+6, line.indexOf("</xkon>")));
                            line=scanner.nextLine();
                            ykon=(double)Integer.parseInt(line.substring(line.indexOf("<ykon>")+6, line.indexOf("</ykon>")));
                            line=scanner.nextLine();
                            konOr=(double)Integer.parseInt(line.substring(line.indexOf("<konor>")+7, line.indexOf("</konor>")));
                            Kondensator kondensator1=new Kondensator(xkon,ykon,konOr);
                            //kondensator1.draw(gc,0);
                            //Kondensator wird auf BorderPane gezeichnet
                            kondensator1.draw1(borderPane);
                            //Kondensator wird in String gespeichert um es später wieder abspeichern zu können
                            xmlstring=kondensator1.toxml(xmlstring);
                        }
                        else if(line.indexOf("Spule")!=-1)
                        {
                            //Schreibt die Werte aus dem String in Variable
                            line=scanner.nextLine();
                            xspu=Integer.parseInt(line.substring(line.indexOf("<xspu>")+6, line.indexOf("</xspu>")));
                            line=scanner.nextLine();
                            yspu=Integer.parseInt(line.substring(line.indexOf("<yspu>")+6, line.indexOf("</yspu>")));
                            line=scanner.nextLine();
                            spuOr=Integer.parseInt(line.substring(line.indexOf("<spuor>")+7, line.indexOf("</spuor>")));
                            Spule spule1= new Spule(xspu,yspu,spuOr);
                            //Spule wird auf BorderPane gezeichnet
                            spule1.draw1(borderPane);
                            xmlstring=spule1.toxml(xmlstring);
                            //spule1.draw(gc,0);
                        }
                        else if(line.indexOf("Widerstand")!=-1)
                        {
                            //Schreibt die Werte aus dem String in Variable
                            line=scanner.nextLine();
                            xwid=Integer.parseInt(line.substring(line.indexOf("<xwid>")+6, line.indexOf("</xwid>")));
                            line=scanner.nextLine();
                            ywid=Integer.parseInt(line.substring(line.indexOf("<ywid>")+6, line.indexOf("</ywid>")));
                            line=scanner.nextLine();
                            widOr= Integer.parseInt(line.substring(line.indexOf("<widor>")+7, line.indexOf("</widor>")));
                            Widerstand widerstand1=new Widerstand(xwid,ywid,widOr);
                            //widerstand1.draw(gc,0);
                            //Widersatnd wird auf BorderPane gezeichnet
                            widerstand1.draw1(borderPane);
                            //Widerstand wird in String gespeichert um es später wieder abspeichern zu können
                            xmlstring=widerstand1.toxml(xmlstring);

                        }
                        else if(line.indexOf("Spannungsquelle")!=-1)
                        {
                            //Schreibt die Werte aus dem String in Variable
                            line=scanner.nextLine();
                            xspa=Integer.parseInt(line.substring(line.indexOf("<xspa>")+6, line.indexOf("</xspa>")));
                            line=scanner.nextLine();
                            yspa=Integer.parseInt(line.substring(line.indexOf("<yspa>")+6, line.indexOf("</yspa>")));
                            line=scanner.nextLine();
                            spaOr =Integer.parseInt(line.substring(line.indexOf("<spaor>")+7, line.indexOf("</spaor>")));
                            Spannungsquelle spannungsquelle1=new Spannungsquelle(xspa,yspa,spaOr);
                            //spannungsquelle1.draw(gc, 0);
                            //Sannungsquelle wird auf BorderPane gezeichnet
                            spannungsquelle1.draw1(borderPane);
                            //Spannungsquelle wird in String gespeichert um es später wieder abspeichern zu können
                            xmlstring=spannungsquelle1.toxml(xmlstring);

                        }
                        else if(line.indexOf("Leitung")!=-1)
                        {
                            //Schreibt die Werte aus dem String in Variable
                            line=scanner.nextLine();
                            xles=Integer.parseInt(line.substring(line.indexOf("<xles>")+6, line.indexOf("</xles>")));
                            line=scanner.nextLine();
                            yles =Integer.parseInt(line.substring(line.indexOf("<yles>")+6, line.indexOf("</yles>")));
                            line=scanner.nextLine();
                            xlee=Integer.parseInt(line.substring(line.indexOf("<xlee>")+6, line.indexOf("</xlee>")));
                            line=scanner.nextLine();
                            ylee=Integer.parseInt(line.substring(line.indexOf("<ylee>")+6, line.indexOf("</ylee>")));
                            Leitung leitung1=new Leitung(xles,yles,0,xlee,ylee);
                            //leitung1.draw(gc);
                            leitung1.draw1(borderPane);
                            //Leitung wird in String gespeichert um es später wieder abspeichern zu können
                            xmlstring=leitung1.toxml(xmlstring);
                        }
                    }
                } catch (Exception f){//Catch exception if any
                    System.err.println("Error: " + f.getMessage());
                }
            }
            else
            {
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Leeres Dokument");
                alert.setHeaderText("");
                alert.setContentText("Konnte Datei nicht laden, da die Datei leer ist");
                alert.showAndWait();
            }
        }
    }
}
