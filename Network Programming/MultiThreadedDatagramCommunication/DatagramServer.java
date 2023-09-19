import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatagramServer {
    public static void main(String[] args) {
        int serverPort = 9876; // Server's port
        ExecutorService executor = Executors.newFixedThreadPool(10); // Thread pool for handling clients

        try (DatagramSocket socket = new DatagramSocket(serverPort)) {
            System.out.println("Server is listening on port " + serverPort);

            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                // Receive data from a client
                socket.receive(receivePacket);
                String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received from client: " + clientMessage);

                // Create a new thread to handle the client request
                executor.execute(new ClientHandler(socket, receivePacket));
            }
        } catch (IOException e) {
            System.err.println("Error in the server: " + e.getMessage());
        } finally {
            executor.shutdown();
        }
    }
}

class ClientHandler implements Runnable {
    private DatagramSocket socket;
    private DatagramPacket receivePacket;

    public ClientHandler(DatagramSocket socket, DatagramPacket receivePacket) {
        this.socket = socket;
        this.receivePacket = receivePacket;
    }

    @Override
    public void run() {
        try {
            // Process the client's message
            String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());

            // Prepare a response message
            String responseMessage = "Server: " + clientMessage;
            byte[] responseData = responseMessage.getBytes();

            // Get the client's address and port from the received packet
            InetAddress clientAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();

            // Send the response back to the client
            DatagramPacket sendPacket = new DatagramPacket(responseData, responseData.length, clientAddress, clientPort);
            socket.send(sendPacket);
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        }
    }
}
