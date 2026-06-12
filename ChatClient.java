import java.io.*;
import java.net.*;

public class ChatClient {

    public static void main(String[] args) {

        try {

            Socket socket = new Socket("localhost", 5000);

            System.out.println("Connected to Server!");

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
                        System.out.println("Server: " + message);
                    }

                } catch (Exception e) {
                    System.out.println("Connection closed.");
                }
            });

            receiveThread.start();

            String clientMessage;

            while ((clientMessage = console.readLine()) != null) {
                output.println(clientMessage);
            }

            socket.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}