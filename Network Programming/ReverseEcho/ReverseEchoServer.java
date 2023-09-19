import java.io.*;
import java.net.*;

public class ReverseEchoServer {
    public static void main(String[] args) {
        int portNumber = 12345; 
        
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Server is listening on port " + portNumber);
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                
                // Handle the client connection in this thread
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
        }
    }

    private static void handleClient(Socket clientSocket) throws IOException {
        try {
            // Input and output streams for the client
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String clientMessage;
            while ((clientMessage = in.readLine()) != null) {
                System.out.println("Received from client: " + clientMessage);

                // Reverse the message
                String reversedMessage = new StringBuilder(clientMessage).reverse().toString();

                // Send the reversed message back to the client
                out.println(reversedMessage);
            }

            // Close the connection
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        }
    }
}
