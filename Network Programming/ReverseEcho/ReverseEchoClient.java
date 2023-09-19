import java.io.*;
import java.net.*;

public class ReverseEchoClient {
    public static void main(String[] args) {
        String serverAddress = "localhost"; 
        int serverPort = 12345; 

        try {
            Socket socket = new Socket(serverAddress, serverPort);
            
            // Input and output streams for the client
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Create a reader for user input
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            String userInput;
            while ((userInput = reader.readLine()) != null) {
                // Send user input to the server
                out.println(userInput);

                // Receive and print the reversed message from the server
                String reversedMessage = in.readLine();
                System.out.println("Server replied: " + reversedMessage);
            }

            // Close the socket when done
            socket.close();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
