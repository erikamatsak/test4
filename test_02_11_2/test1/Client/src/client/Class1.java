package client;

import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.layout.StackPane;

import javafx.stage.Stage;

public class Class1 extends Application {
    public Class1() {
        super();
    }

    public static void main(String[] args) {
        Class1 class1 = new Class1();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Implement this method
        Button btn = new Button();
                btn.setText("Say 'Hello World'");
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Hello World!");
                    }
                });
                
                StackPane root = new StackPane();
                root.getChildren().add(btn);
                
                Scene scene = new Scene(root, 300, 250);
                
                primaryStage.setTitle("Hello World!");
                primaryStage.setScene(scene);
                primaryStage.show();
       
    }
}
