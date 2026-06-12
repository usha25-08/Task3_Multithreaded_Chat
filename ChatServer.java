import java.io.*;
import java.net.*;

public class ChatServer {

    public static void main(String[] args) {

        try {

            ServerSocket serverSocket = new ServerSocket(5000);

            System.out.println("Server started...");
            System.out.println("Waiting for client...");

            Socket socket = serverSocket.accept();

            System.out.println("Client connected!");

            BufferedReader input =
                    new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));

            PrintWriter output =
                    new PrintWriter(socket.getOutputStream(), true);

            BufferedReader console =
                    new BufferedReader(
                            new InputStreamReader(System.in));

            Thread receiveThread = new Thread(() -> {

                try {

                    String message;

                    while ((message = input.readLine()) != null) {
                        System.out.println("Client: " + message);
                    }

                } catch (Exception e) {
                    System.out.println("Connection closed.");
                }
            });

            receiveThread.start();

            String serverMessage;

            while ((serverMessage = console.readLine()) != null) {
                output.println(serverMessage);
            }

            socket.close();
            serverSocket.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}