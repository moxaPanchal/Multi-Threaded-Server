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
package myProjects.ClientA;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.*;
import java.net.Socket;

public class Client1 extends Application implements Runnable{


    private final Text name = new Text("Enter your name: ");
    private final Text errorMessage = new Text("");
    private final TextField nameInput = new TextField();
    private  final Button submitButton = new Button("SUBMIT");

    private  final TextArea message = new TextArea();
    private  final TextField typeMessage = new TextField();
    private  final Button sendMessage = new Button("SEND");
    public String username;
    BufferedWriter buffWriter;
    BufferedReader buffReader;

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
        stage.setTitle("Client 1");
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
                catch(NullPointerException err){
                    System.out.println(err);
                }

            }
            else {
                errorMessage.setText("Please enter some value!");
                errorMessage.setFill(Color.DARKRED);
            }
        });
    }
    @Override
    public void run() {
        try {
            String serverMessage;

            while((serverMessage = buffReader.readLine()) != null) {
                message.appendText(serverMessage + "\n");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Client1(){ }
    public Client1(String clientUsername) {
        //Scene2
        Stage stage2 = new Stage();
        BorderPane border2 = new BorderPane();
        Scene scene2 = new Scene(border2);
        stage2.setScene(scene2);
        stage2.setTitle(clientUsername);
        stage2.setHeight(350);
        stage2.setWidth(490);
        stage2.setResizable(false);


        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
        hbox1.setPadding(new Insets(15, 12, 10, 12));
        hbox1.setSpacing(10);
        hbox2.setPadding(new Insets(5, 12, 15, 12));
        hbox2.setSpacing(10);

        border2.setTop(hbox1);
        hbox1.getChildren().addAll(message);
        message.setPrefSize(450, 220);
        message.setEditable(false);
        typeMessage.setPrefSize(340, 20);
        sendMessage.setPrefSize(100, 20);

        border2.setLeft(hbox2);
        hbox2.getChildren().addAll(typeMessage, sendMessage);

        username = clientUsername;

        sendMessage.setOnAction(e ->{
            String messageContent = username + " ~ " + typeMessage.getText();
            typeMessage.setText("");
            try{
                buffWriter.write(messageContent);
                buffWriter.write("\r\n");
                buffWriter.flush();
            } catch (IOException a) {
                a.printStackTrace();
            }
        });
        stage2.show();
        try {

            Socket socketClient= new Socket("localhost",8000);
            OutputStreamWriter output = new OutputStreamWriter(socketClient.getOutputStream());
            buffWriter = new BufferedWriter(output);
            InputStreamReader input = new InputStreamReader(socketClient.getInputStream());
            buffReader = new BufferedReader(input);
        }
        catch(IOException e1){
            e1.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch();
    }

}