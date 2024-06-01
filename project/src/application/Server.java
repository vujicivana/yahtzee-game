package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {
	private int SERVER_PORT = 9999;
	private ServerSocket serverSocket = null;
	//upareni igraci, lista liste koja se sastoji od dva igraca
	private ArrayList<ArrayList<Socket>> clients = new ArrayList<ArrayList<Socket>>();
	private ArrayList<ClientThread> threads = new ArrayList<>();
	
	public Server() throws IOException{
		serverSocket = new ServerSocket(SERVER_PORT);
		System.err.println("Server bound on port " + SERVER_PORT);
		execute();
	}
	
	public void execute() {
		while(true) {
			System.err.println("Listening for clients...");
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				System.err.println("Client accepted! Dispatching thread...");
				ClientThread client = new ClientThread(socket, this);
				client.start();
				threads.add(client);
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void sendInfo(Socket player1, Socket player2) {
		String name1 = getUsername(player1);
		String name2 = getUsername(player2);
		
		for(ClientThread thread : threads) {
			if(thread.getSocket() == player1)
				thread.sendMessage("START " + name2 + " " +0);
			else if(thread.getSocket() == player2)
				thread.sendMessage("START " + name1 + " " +1);
		}
	}

	public void sendToOpponent(String message, Socket client) {
		for (ArrayList<Socket> list : clients) {
			if (list.size() == 2 && (client == list.get(0) || client == list.get(1))) {
				Socket opponent;
				if (client == list.get(0)) 
				    opponent = list.get(1);
				else 
				    opponent = list.get(0);
				for (ClientThread thread : threads) {
					if (thread.getSocket() == opponent)
						thread.sendMessage(message);
				}
				break;
			}
		}
	}
	
	public synchronized void addAndPair(Socket socket) {
		if(!clients.isEmpty() && clients.get(clients.size() - 1).size() == 1) {
			clients.get(clients.size() - 1).add(socket);
			Socket player1 = clients.get(clients.size() - 1).get(0);
			Socket player2 = clients.get(clients.size() - 1).get(1);
			sendInfo(player1, player2);
		}
		else {
			ArrayList<Socket> pair = new ArrayList<>();
			pair.add(socket);
			clients.add(pair);
		}
	}
	
	public synchronized void clientDisconnected(ClientThread client, int action) {
		if (action == 0)
			threads.remove(client);
		int index = -1;
		for (int i = 0; i < clients.size(); i++) {
			if (client.getSocket().equals(clients.get(i).get(0))
					|| (clients.get(i).size() == 2 && client.getSocket().equals(clients.get(i).get(1)))) {
				index = i;
			}
		}
		if (index != -1) {
			clients.remove(index);
		}
	}
	
	private String getUsername(Socket player) {
		for (ClientThread thread : threads) {
			if (thread.getSocket() == player)
				return thread.getUsername();
		}
		return "";
	}
	
	public static void main(String[] args) throws IOException {
		new Server();
	}
}
