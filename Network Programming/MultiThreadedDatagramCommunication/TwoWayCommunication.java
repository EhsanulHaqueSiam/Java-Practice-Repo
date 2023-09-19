import java.io.IOException;
import java.net.*;

public class TwoWayCommunication {
    public static void main(String[] args) {
        int port = 9876;

        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("Two-way communication started on port " + port);

            InetAddress localhost = InetAddress.getByName("localhost");
            int peerPort = 8888; // Port of the other peer

            // Create a thread for receiving messages
            Thread receiveThread = new Thread(() -> {
                try {
                    while (true) {
                        byte[] receiveData = new byte[1024];
                        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                        socket.receive(receivePacket);
                        String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                        System.out.println("Received: " + message);
                    }
                } catch (IOException e) {
                    System.err.println("Error receiving data: " + e.getMessage());
                }
            });

            receiveThread.start();

            // Create a thread for sending messages
            Thread sendThread = new Thread(() -> {
                try {
                    BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));
                    while (true) {
                        System.out.print("Enter a message to send (or 'exit' to quit): ");
                        String userInput = userInputReader.readLine();

                        if (userInput.equalsIgnoreCase("exit")) {
                            System.out.println("Communication ended.");
                            break;
                        }

                        byte[] sendData = userInput.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, localhost, peerPort);
                        socket.send(sendPacket);
                    }
                } catch (IOException e) {
                    System.err.println("Error sending data: " + e.getMessage());
                }
            });

            sendThread.start();

            // Wait for both threads to finish before closing the socket
            receiveThread.join();
            sendThread.join();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
