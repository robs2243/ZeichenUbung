
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rd
 */
public class Main extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        Group root = new Group();
        Scene s = new Scene(root, 300, 300, Color.WHITE);
        
        TextField txfFeld = new TextField("2");
        Button btnPfadLoeschen = new Button("Pfad löschen");
        Button btnPfadZeichnen = new Button("Pfad zeichnen");
        CheckBox ckbLoeschen = new CheckBox("Löschen");
        
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        
        ObservableList list = hbox.getChildren(); 
        list.addAll(btnPfadLoeschen, btnPfadZeichnen, ckbLoeschen, txfFeld);

        final Canvas canvas = new Canvas(250, 250);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //gc.setFill(Color.RED);
        gc.setStroke(Color.BLUE);
        
        //Path p = new Path();
        //pfadzeichnen(gc, p);
        ArrayList< ArrayList<Path>> uberListe = new ArrayList< ArrayList<Path>>();
        ArrayList<Path> pfadListe = new ArrayList<Path>();
        //ArrayList<Path> pfadListe;
        
        canvas.setOnMouseClicked(e -> {});

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            
            @Override
            public void handle(MouseEvent event) {
                
                //pfadListe = new ArrayList<Path>();
                
            }
        });
                 
        canvas.setOnMousePressed(e -> {
            
            System.out.println("MousePressed Event!");
            Path p = new Path();
            //System.out.println(e.getClickCount());
            gc.beginPath();
            gc.moveTo(e.getX(), e.getY());
            MoveTo move = new MoveTo();
            move.setX(e.getX());
            move.setY(e.getY());
            p.getElements().add(move);
            pfadListe.add(p);
            gc.stroke();
  
        });
 
        canvas.setOnMouseDragged(e -> {

            System.out.println("Dragged Event!");
            Path p = new Path();
   
            double x = e.getX();
            double y = e.getY();

            if (ckbLoeschen.isSelected()) {
                gc.clearRect(x, y, 10, 10);

            } else {
                gc.lineTo(x, y);
                LineTo linie = new LineTo();
                linie.setX(x);
                linie.setY(y);
                p.getElements().add(linie);
                pfadListe.add(p);
                gc.stroke();
            }
        });
        
        canvas.setOnMouseReleased(e -> {
            
            uberListe.add(pfadListe);
            //pfadListe.clear();
            System.out.println("uberListe: " + (uberListe.size()-1));

        });
        
        
        btnPfadLoeschen.setOnMouseClicked(e -> {
        
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
        });
        
        btnPfadZeichnen.setOnMouseClicked(e -> {
            
            int zeichnungsnummer = new Integer(txfFeld.getText());
            System.out.println("Zeichnungsnummer: " + zeichnungsnummer);
            System.out.println("pfadZeichnen lebt");
            pfadzeichnen(gc, uberListe, canvas);
            
        });
        
 
        root.getChildren().add(canvas);
        root.getChildren().add(hbox);

        primaryStage.setScene(s);

        primaryStage.show();
        
    }

    public void pfadzeichnen(GraphicsContext gc, ArrayList<ArrayList<Path>> uberListe, Canvas canvas) {

        System.out.println("uberListe: " + uberListe.size());
        //gc.beginPath();
        for (int i=0; i < uberListe.size(); i++) {
            
            gc.setStroke(Color.RED);
            gc.beginPath();       
            ArrayList<Path> pfad = uberListe.get(i);
            System.out.println("Pfadlänge: " + pfad.size());
            //MoveTo moveto = (MoveTo) (pfad.get(0).getElements().get(0));
            //gc.moveTo(moveto.getX(), moveto.getY());
            for (int j = 1; j < pfad.size(); j++) {
                if ((pfad.get(j).getElements().get(0)) instanceof LineTo) {
                    LineTo lineto = (LineTo) (pfad.get(j).getElements().get(0));
                    gc.lineTo(lineto.getX(), lineto.getY());
                } else {
                    MoveTo moveto = (MoveTo) (pfad.get(j).getElements().get(0));
                    gc.moveTo(moveto.getX(), moveto.getY());
                }
            }
            gc.stroke();
        }

//        //gc.beginPath();
//        ArrayList<Path> p = uberListe.get(1);
//        //gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//        System.out.println(p.toString());
//        
//        for (Path pfad : p) {
//            //gc.beginPath();
//            for (PathElement elem : pfad.getElements()) {
//
//                if (elem instanceof MoveTo) {
//
//                    gc.moveTo(((MoveTo) elem).getX(), ((MoveTo) elem).getY());
//                    //gc.stroke();
//
//                }
//
//                if (elem instanceof LineTo) {
//
//                    gc.moveTo(((LineTo) elem).getX(), ((LineTo) elem).getY());
//                    //gc.stroke();
//
//                }
//            }
//            //System.out.println("Anzahl der Elemente: " + pfad.getElements().size());
//
//            //gc.beginPath();
//            gc.stroke();
//        }
//        
//        
    }
    
}
   
