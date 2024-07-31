import java.util.concurrent.CopyOnWriteArrayList;

public class Broadcast {
    private CopyOnWriteArrayList<ClientHandler> clients = new CopyOnWriteArrayList<>();

    public void sendMessage(String msg, ClientHandler sender) {
        System.out.println(msg);
        for (ClientHandler client : this.clients) {
            if (!client.equals(sender)) {
                client.sendMessage(msg);
            }
        }
    }

    public void addClient(ClientHandler client) {
        this.clients.add(client);
    }

    public void removeClient(ClientHandler client) {
        this.clients.remove(client);
    }
}