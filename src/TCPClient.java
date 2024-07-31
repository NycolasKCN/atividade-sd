import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    public void start(String serverIp, int serverPort) throws IOException {
        // Cria socket de comunicacao com o servidor e obtem canais de entrada e saida
        System.out.println("[C1] Conectando com servidor " + serverIp + ":" + serverPort);
        socket = new Socket(serverIp, serverPort);
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());

        // Espera mensagem ser digitada da entrada padrão (teclado)
        System.out.println("[C2] Conexão estabelecida, eu sou o cliente: " + socket.getLocalSocketAddress());        

        MessageHandler msgHandler = new MessageHandler(input);
        msgHandler.start();

        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        String msg;
        while (true) {
            System.out.println("> ");
            msg = scanner.nextLine();
            output.writeUTF(msg);
        }
    }
 
    public void stop() {
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String serverIp = "0.0.0.0";
        int serverPort = 6666;
        try {
            // Cria e roda cliente
            TCPClient client = new TCPClient();
            client.start(serverIp, serverPort);
            
            // Finaliza cliente
            client.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
