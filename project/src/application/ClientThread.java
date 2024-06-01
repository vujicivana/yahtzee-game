package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread{
	private Socket client;
	private String username;
	private Server server;
	private BufferedReader input;
	private PrintWriter output;
	
	public ClientThread(Socket client, Server server) {
		this.client = client;
		this.server = server;
		try {
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			output = new PrintWriter(client.getOutputStream(), true);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			String message;
			while(true) {
				message = input.readLine();
				String [] action = message.split(" ");
				if(action[0].equals("USERNAME")) {
					username = action[1];
					server.addAndPair(client);
				}
				else if(message.equals("DISCONNECTED")) {
					server.sendToOpponent(message, client);
					closeAll();
				}
				else if(message.equals("LEFT_GAME")) {
					server.sendToOpponent("DISCONNECTED", client);
					server.clientDisconnected(this,1);
				}
				else 
					server.sendToOpponent(message, client);
			}
		}
		catch(IOException e) {
			closeAll();
		}
	}
	
	public Socket getSocket() {
		return client;
	}
	
	public String getUsername() {
		return username;
	}

	public void sendMessage(String message) {
		output.println(message);
	}
	
	public void closeAll() {
		server.clientDisconnected(this,0);
		try {
			input.close();
			output.close();
			client.close();	
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
