package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javafx.application.Platform;

public class Client extends Thread{
	
	private static int SERVER_PORT=9999;  //***
	private Socket socket;
	private String username;
	private BufferedReader input;
	private PrintWriter output;
	private App app;
	
	public Client(App app) {
		this.app=app;
		try {
			InetAddress address= InetAddress.getByName("localhost");
			socket= new Socket(address, SERVER_PORT);
			input=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			System.out.println("Client connected");
		} catch (IOException e) {
			try {
				closeResourses();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	public void run() {
			try {
				String serverMessage;
				while(true) {
				serverMessage=input.readLine();
				if(serverMessage==null)
					break;
				String action = serverMessage.split(" ")[0];
				if(action.equals("START")) {
					
					String name=serverMessage.split(" ")[1];
					String firstMove=serverMessage.split(" ")[2];
					app.setOponentName(name);
					if(firstMove.equals("0"))
						app.setEnemyTurn(false);
					else
						app.setEnemyTurn(true);
					app.setOponentConnected();
					
				}else if (action.equals("MESSAGE")) {
					
					String message=serverMessage.substring(8);
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							app.addMessageToChat(message);
						}
						
					});
					
				}else if (action.equals("SELECT")) {
					
					String select=serverMessage.substring(7);
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							app.select(select);
						}
						
					});
					
				} else if (action.equals("DISCONNECTED"))  {
					
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							app.disconnectedOponent();
						}
					
					});
				}
				
				}
				closeResourses();
			} catch (IOException e) {
				// TODO Auto-generated catch block  
				try {
					closeResourses();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			
		}
	
	public void sendMessage(String message) {
		output.println("MESSAGE " + username + ": " + message);
	}

	public void sendUsername() {
		output.println("USERNAME " + username);
	}

	public void sendDisconnected() {
		output.println("DISCONNECTED");
	}

	public void sendLeftGame() {
		output.println("LEFT_GAME");
	}
	
	public void sendSelect(String select) {
		output.println("SELECT "+ select);
	}
	
	public void setUsername(String name) {
		username=name;
	}
	
	public String getUsername() {  
		return username;
	}
	
	public void sendConfirmation(String value) {  
		output.println("CONFIRMATION " + value);
	}
	
	public void closeResourses() throws IOException {
		input.close();
		output.close();
		socket.close();
		interrupt();                     	
	}	
}
