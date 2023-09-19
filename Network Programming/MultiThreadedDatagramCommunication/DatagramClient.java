import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatagramClient {
    public static void main(String[] args) {
        String serverHost = "localhost";
        int serverPort = 9876; // Server's port
        int clientPort = 8888; // Client's port
        ExecutorService executor = Executors.newFixedThreadPool(2); // Thread pool for sending and receiving

        try (DatagramSocket socket = new DatagramSocket(clientPort)) {
            InetAddress serverAddress = InetAddress.getByName(serverHost);

            BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));

            // Create a thread for receiving messages from the server
            executor.execute(() -> {
                try {
                    while (true) {
                        byte[] receiveData = new byte[1024];
                        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                        socket.receive(receivePacket);
                        String serverResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());
                        System.out.println("Server response: " + serverResponse);
                    }
                } catch (IOException e) {
                    System.err.println("Error receiving data from server: " + e.getMessage());
                }
            });

            // Create a thread for sending messages to the server
            executor.execute(() -> {
                try {
                    while (true) {
                        // Read user input
                        System.out.print("Enter a message to send to the server (or 'exit' to quit): ");
                        String userInput = userInputReader.readLine();

                        if (userInput.equalsIgnoreCase("exit")) {
                            System.out.println("Client exiting.");
                            executor.shutdown();
                            break;
                        }

                        // Send user input to the server
                        byte[] sendData = userInput.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
                        socket.send(sendPacket);
                    }
                } catch (IOException e) {
                    System.err.println("Error sending data to server: " + e.getMessage());
                }
            });

        } catch (IOException e) {
            System.err.println("Error in the client: " + e.getMessage());
        }
    }
}
