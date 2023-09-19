import java.io.IOException;
import java.net.*;

public class DatagramClient {
    public static void main(String[] args) {
        String serverHost = "localhost";
        int serverPort = 9876; // Server's port
        int clientPort = 8888; // Client's port

        try (DatagramSocket socket = new DatagramSocket(clientPort)) {
            InetAddress serverAddress = InetAddress.getByName(serverHost);

            BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                // Read user input
                System.out.print("Enter a message to send to the server (or 'exit' to quit): ");
                String userInput = userInputReader.readLine();

                if (userInput.equalsIgnoreCase("exit")) {
                    System.out.println("Client exiting.");
                    break;
                }

                // Send user input to the server
                byte[] sendData = userInput.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
                socket.send(sendPacket);

                // Receive the response from the server
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);
                String serverResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Server response: " + serverResponse);
            }
        } catch (IOException e) {
            System.err.println("Error in the client: " + e.getMessage());
        }
    }
}
