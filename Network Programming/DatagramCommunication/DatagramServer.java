import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class DatagramServer {
    public static void main(String[] args) {
        int serverPort = 9876; // Server's port

        try (DatagramSocket socket = new DatagramSocket(serverPort)) {
            System.out.println("Server is listening on port " + serverPort);

            while (true) {
                // Receive data from a client
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);
                String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received from client: " + clientMessage);

                // Prepare a response message
                String responseMessage = "Server: " + clientMessage;
                byte[] responseData = responseMessage.getBytes();

                // Get the client's address and port from the received packet
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                // Send the response back to the client
                DatagramPacket sendPacket = new DatagramPacket(responseData, responseData.length, clientAddress, clientPort);
                socket.send(sendPacket);
            }
        } catch (IOException e) {
            System.err.println("Error in the server: " + e.getMessage());
        }
    }
}
