/**********************************************
 Workshop 10
 Course: JAC444 - 4
 Last Name: Panchal
 First Name: Moxa
 ID: 148885197
 Section: NBB
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Moxa Panchal.
 Date: 22/04/2022
 ********************************************/
package myProjects.ClientB;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import myProjects.ClientA.Client1;


public class Client2 extends Application {

    private final Text name = new Text("Enter your name: ");
    private final Text errorMessage = new Text("");
    private final TextField nameInput = new TextField();
    private  final Button submitButton = new Button("SUBMIT");

    @Override
    public void start(Stage stage) {
        //scene1
        BorderPane border1 = new BorderPane();
        HBox hBoxA = new HBox();
        hBoxA.setPadding(new Insets(15, 12, 10, 12));
        hBoxA.setSpacing(10);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(1, 12, 10, 12));
//        gridPane.setSpacing(10);
        gridPane.setHgap(6.8);
        gridPane.setVgap(6.8);

        Scene scene1 = new Scene(border1);
        stage.setHeight(350);
        stage.setWidth(490);
        stage.setResizable(false);
        stage.setTitle("Client 2");
        stage.setScene(scene1);
        stage.show();


        //scene1
        border1.setTop(hBoxA);
        nameInput.setPrefSize(150, 20);
        hBoxA.getChildren().addAll(name, nameInput);

        border1.setLeft(gridPane);
        submitButton.setPrefSize(100, 20);
        gridPane.add(errorMessage, 15, 1);
        gridPane.add(submitButton, 15, 2);

        submitButton.setOnAction(e -> {
            if (nameInput.getText().length() != 0) {
                String clientName = nameInput.getText();
                Client1 client = new Client1(clientName);
                try {
                    Thread clientThread = new Thread(client);
                    clientThread.start();
                    stage.hide();
                }
                catch(NullPointerException e1){
                    e1.printStackTrace();
                }

            }
            else {
                errorMessage.setText("Please enter some value!");
                errorMessage.setFill(Color.DARKRED);
            }
        });
    }
    public static void main(String[] args) {
        launch();
    }
}
