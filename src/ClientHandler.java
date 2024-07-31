import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

class ClientHandler extends Thread {
    private DataInputStream in;
    private DataOutputStream out;
    private Socket clientSocket;
    private Broadcast broadcast;
    private String username;

    public ClientHandler(Socket aClientSocket, Broadcast broadcast) {
        this.broadcast = broadcast;
        try {
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    public void run() {
        setUsername();
        try { // an echo server
            String clientMsg;
            while ((clientMsg = in.readUTF()) != null) {
                broadcast.sendMessage("["+username+"]: "+clientMsg, this);
            }
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        } finally {
            try {
                clientSocket.close();  
                broadcast.removeClient(this);
            } catch (IOException e) {
                /* close failed */
            }
        }
    }

    private void setUsername() {
        try {
            this.out.writeUTF("Escreva seu nome de usuário:");
            this.username = in.readUTF();
            this.out.writeUTF("Envie uma mensagem: ");
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        }

    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
            out.writeUTF("Envie uma mensagem: ");
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        }
    }
}