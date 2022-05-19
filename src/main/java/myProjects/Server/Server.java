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
package myProjects.Server;

import javafx.application.Application;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class Server extends Application implements Runnable{
    private static final TextArea server = new TextArea();
    public static ArrayList<BufferedWriter> clients = new ArrayList<>();
    Socket sockett;

    public Server() {}
    public Server(Socket soc) {
        try {
            server.appendText("\nConnection from " + soc + " at " + new Date() + "\n\n");
            sockett = soc;
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            InputStreamReader input = new InputStreamReader(sockett.getInputStream());
            BufferedReader reader = new BufferedReader(input);
            OutputStreamWriter output = new OutputStreamWriter(sockett.getOutputStream());
            BufferedWriter writer = new BufferedWriter(output);

            clients.add(writer);

            while (true) {

                String data1;
                data1 = reader.readLine().trim();
                server.appendText(" " + data1 + "\n");

                for (BufferedWriter client : clients) {
                    try {
                        client.write(data1);
                        client.write("\r");
                        client.write("\n");
                        client.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {


        ServerSocket srSoc = new ServerSocket(8000);

        while(true) {
            Socket socket = srSoc.accept();
            Server server1 = new Server(socket);
            Thread serverThread = new Thread(server1);

            serverThread.start();
        }
        //        stage.show();
//        BorderPane pane = new BorderPane();
//        Scene sceneServer =new Scene(pane);
//        stage.setScene(sceneServer);
//        stage.setTitle("Server");
//        stage.setHeight(550);
//        stage.setWidth(690);
//
//        pane.setTop(server);
//        server.setEditable(false);
//        server.setPrefSize(600, 400);
//        Date date = new Date();
//        server.appendText("MultiThreadServer started at :" + date + "\n");
    }


    public static void main(String[] args) {

        launch();
    }

}
