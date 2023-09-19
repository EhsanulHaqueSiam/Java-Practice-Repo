import java.io.*;
import java.net.*;

public class ReverseEchoClient {
    public static void main(String[] args) {
        String serverHost = "localhost";
        int serverPort = 8080;

        try (
            Socket socket = new Socket(serverHost, serverPort);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            String userInput;
            System.out.println("Connected to server. Type a message (or 'exit' to quit):");

            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);

                if (userInput.equalsIgnoreCase("exit")) {
                    System.out.println("Client exiting.");
                    break;
                }

                String serverResponse = in.readLine();
                System.out.println("Server response: " + serverResponse);
            }
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + serverHost);
        } catch (IOException e) {
            System.err.println("Error connecting to the server: " + e.getMessage());
        }
    }
}
