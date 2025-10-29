import java.util.ArrayList;

public class ClientList {
    private ArrayList<Client> clients;

    public ClientList() {
        clients = new ArrayList<Client>();
    }

    public boolean addClient(Client client) {
        if (client != null) {
            clients.add(client);
            return true;
        } else {
            return false;
        }
    }

    public Client getClient(String clientId) {
        for (int i = 0; i < clients.size(); i++) {
            Client client = clients.get(i);
            if (client.getId().equals(clientId)) {
                return client;
            }
        }
        return null;
    }

    public void displayAllClients() {
        for (Client client : clients) {
            client.displayClientInfo();
        }
    }

    public String[] getClientIds() {
        String[] ids = new String[clients.size()];
        for (int i = 0; i < clients.size(); i++) {
            ids[i] = clients.get(i).getId();
        }
        return ids;
    }
}
