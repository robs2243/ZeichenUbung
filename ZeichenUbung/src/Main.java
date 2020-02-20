
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
        Scene s = new Scene(root, 300, 300, Color.BLACK);
        
        Button btn = new Button("Pfad löschen");
        Button btn2 = new Button("Pfad zeichnen");
        
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        
        ObservableList list = hbox.getChildren(); 
        list.addAll(btn, btn2);

        final Canvas canvas = new Canvas(250, 250);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.BLUE);
        pfadzeichnen(gc);
        
        Path p = new Path();
        MoveTo move = new MoveTo();
        
        canvas.setOnMouseDragged(e -> {
            
            double x = e.getX();
            double y = e.getY();              
            move.setX(x);
            move.setY(y);
            p.getElements().add(move);
            gc.clearRect(x, y, 10, 10); 
            
        });
        
        
   
        root.getChildren().add(canvas);
        root.getChildren().add(hbox);

        primaryStage.setScene(s);

        primaryStage.show();
        
    }

    public void pfadzeichnen(GraphicsContext gc) {
        
        //gc.fillRect(75, 75, 100, 100);
        gc.beginPath();
        gc.moveTo(0, 0);
        gc.lineTo(150, 150);
        gc.lineTo(150, 0);
        gc.lineTo(0,0);
        gc.setStroke(Color.RED);
        gc.setLineWidth(10);
        gc.stroke();
        //gc.clearRect(0, 0, 100, 100);
        
    }
    
}
   
