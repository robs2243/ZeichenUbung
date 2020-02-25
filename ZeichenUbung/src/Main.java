
import java.util.ArrayList;
import java.util.Collections;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        Scene s = new Scene(root, 420, 450);

        TextField txfFeld = new TextField("2");
        Button btnPfadLoeschen = new Button("Pfad löschen");
        Button btnPfadZeichnen = new Button("Pfad zeichnen");
        CheckBox ckbLoeschen = new CheckBox("Löschen");
        
        ImageView imgview = new ImageView();
        Image img = new Image("/aufgabe.jpg");
        imgview.setImage(img);
        imgview.setX(0);
        imgview.setY(30);
        imgview.setFitHeight(400);
        imgview.setFitWidth(400);

        HBox hbox = new HBox();
        hbox.setSpacing(10);
        
        ObservableList list = hbox.getChildren(); 
        list.addAll(btnPfadLoeschen, btnPfadZeichnen, ckbLoeschen, txfFeld);

        final Canvas canvas = new Canvas(400, 400);
        canvas.setLayoutY(30);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //gc.setFill(Color.RED);
        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        
        //Path p = new Path();
        //pfadzeichnen(gc, p);
        ArrayList<Path> pfadListe = new ArrayList<Path>();
        Path p = new Path();
     
        canvas.setOnMousePressed(e -> {
            
            System.out.println("MousePressed Event!");
            //Path p = new Path();
            //System.out.println(e.getClickCount());
            gc.beginPath();
            gc.moveTo(e.getX(), e.getY());
            MoveTo move = new MoveTo();
            move.setX(e.getX());
            move.setY(e.getY());
            p.getElements().add(move);
            //pfadListe.add(p);
            //gc.stroke();
  
        });

        //ArrayList<Path> kopieVonPfadListe = new ArrayList<Path>(pfadListe);
        //Collections.copy(kopieVonPfadListe, pfadListe);

        canvas.setOnMouseDragged(e -> {

            //System.out.println("Dragged Event!");
            //Path p = new Path();
   
            double x = e.getX();
            double y = e.getY();
            Path zuEntferndenderPfad = null;

            if (ckbLoeschen.isSelected()) {
                
                //gc.clearRect(x, y, 10, 10);
                double positionX = 0;
                double positionY = 0;
                
                for(Path pfadAusListe : pfadListe) {
                
                    //if(pfadAusListe.contains(e.getX(), e.getY())) {
                    if(pfadAusListe.intersects(e.getX(), e.getY(), 5, 5)) {
                    
                        positionX = e.getX();
                        positionY = e.getY();
                        //pfadListe.remove(pfadAusListe);
                        zuEntferndenderPfad = pfadAusListe;
                    
                    }
                    
                }
                pfadListe.remove(zuEntferndenderPfad);
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                pfadzeichnen(gc, pfadListe, canvas);

            } else {
                gc.lineTo(x, y);
                LineTo linie = new LineTo();
                linie.setX(x);
                linie.setY(y);
                p.getElements().add(linie);
                //pfadListe.add(p);
                gc.stroke();
            }
        });
        
        canvas.setOnMouseReleased(e -> {
            
            pfadListe.add(pfadKopieren(p));
            p.getElements().clear();

        });
        
        
        btnPfadLoeschen.setOnMouseClicked(e -> {
        
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
        });
        
        btnPfadZeichnen.setOnMouseClicked(e -> {

            pfadzeichnen(gc, pfadListe, canvas);
            
        });
        
 
        root.getChildren().add(imgview);
        root.getChildren().add(canvas);
        root.getChildren().add(hbox);
        primaryStage.setScene(s);
        

        primaryStage.show();
        
    }
    
    public Path pfadKopieren(Path p) {

        Path neuerPfad = new Path();

        for (int j = 0; j < p.getElements().size(); j++) {

            if ((p.getElements().get(j)) instanceof LineTo) {

                LineTo lineto = (LineTo) (p.getElements().get(j));
                neuerPfad.getElements().add(lineto);

            } else {

                MoveTo moveto = (MoveTo) (p.getElements().get(j));
                neuerPfad.getElements().add(moveto);

            }
        }

        return neuerPfad;

    }


    public void pfadzeichnen(GraphicsContext gc, ArrayList<Path> pfadListe, Canvas canvas) {

        System.out.println("pfadListe: " + pfadListe.size());

        for (int i = 0; i < pfadListe.size(); i++) {
            gc.setStroke(Color.RED);
            gc.setLineWidth(2);
            gc.beginPath();
            Path pfad = pfadListe.get(i);
            System.out.println("Pfadlänge: " + pfad.getElements().size());
            for (int j = 0; j < pfad.getElements().size(); j++) {

                if ((pfad.getElements().get(j)) instanceof LineTo) {

                    LineTo lineto = (LineTo) (pfad.getElements().get(j));
                    gc.lineTo(lineto.getX(), lineto.getY());

                } else {

                    MoveTo moveto = (MoveTo) (pfad.getElements().get(j));
                    gc.moveTo(moveto.getX(), moveto.getY());

                }
            }

            gc.stroke();
        }

    }
    
}
   
