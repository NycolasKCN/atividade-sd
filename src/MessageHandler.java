import java.io.DataInputStream;
import java.io.IOException;

public class MessageHandler extends Thread {
    private DataInputStream input;
    public MessageHandler(DataInputStream input) {
        this.input = input;
    }

    @Override
    public void run() {
        try {
            String serverMessage;
            while ((serverMessage = input.readUTF()) != null) {
                System.out.println(serverMessage);
            }
        } catch (IOException e) {
            System.out.println("Message handler: " + e.getMessage());
        }
    }
}
