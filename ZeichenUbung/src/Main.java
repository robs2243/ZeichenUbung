
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
        
        Button btnPfadLoeschen = new Button("Pfad löschen");
        Button btnPfadZeichnen = new Button("Pfad zeichnen");
        CheckBox ckbLoeschen = new CheckBox("Löschen");
        
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        
        ObservableList list = hbox.getChildren(); 
        list.addAll(btnPfadLoeschen, btnPfadZeichnen, ckbLoeschen);

        final Canvas canvas = new Canvas(250, 250);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //gc.setFill(Color.RED);
        gc.setStroke(Color.BLUE);
        
        Path p = new Path();
        //pfadzeichnen(gc, p);
        
        canvas.setOnMouseClicked(e -> {
        
            System.out.println(e.getClickCount());
            
        });
        
        canvas.setOnMousePressed(e -> {
            
            //System.out.println(e.getClickCount());
            gc.beginPath();
            gc.moveTo(e.getX(), e.getY());
            MoveTo move = new MoveTo();
            move.setX(e.getX());
            move.setY(e.getY());
            p.getElements().add(move);
            gc.stroke();
  
        });
        

        canvas.setOnMouseDragged(e -> {
            
            double x = e.getX();
            double y = e.getY();
            
            if(ckbLoeschen.isSelected()) {
                gc.clearRect(x, y, 10, 10);
                
            }
            else {
                gc.lineTo(x, y);
                LineTo linie = new LineTo();
                linie.setX(x);
                linie.setY(y);
                p.getElements().add(linie);
                gc.stroke();
            }
        });
        
        btnPfadLoeschen.setOnMouseClicked(e -> {
        
            System.out.println(p.toString());
        
        });
        
        btnPfadZeichnen.setOnMouseClicked(e -> {
            
            System.out.println("pfadZeichnen lebt");
            pfadzeichnen(gc, p);
            
        });
        
 
        root.getChildren().add(canvas);
        root.getChildren().add(hbox);

        primaryStage.setScene(s);

        primaryStage.show();
        
    }

    public void pfadzeichnen(GraphicsContext gc, Path p) {
        
        //gc.beginPath();
        System.out.println(p.toString());
        for (PathElement elem : p.getElements()) {

            if (elem instanceof MoveTo) {

                gc.moveTo(((MoveTo) elem).getX(), ((MoveTo) elem).getY());
                System.out.println("MoveTO gefunden");

            }
            
            if (elem instanceof LineTo) {

                gc.moveTo(((LineTo) elem).getX(), ((LineTo) elem).getY());
                System.out.println("LineTO gefunden");

            }
        }
        System.out.println("Anzahö der Elemente: " + p.getElements().size());
        gc.setStroke(Color.RED);
        gc.stroke();
        
    }
    
}
   
