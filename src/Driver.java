import java.io.IOException;

import javax.swing.JOptionPane;

public class Driver {
	public static void main(String[] args){
		System.out.println("Welcome");
		String type = JOptionPane.showInputDialog("Server or Client?");
		if(type.equals("Server")) {
			Server server = new Server();
			try {
				server.start();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(type.equals("Client")) {
			Client client = new Client();
			try {
				client.start();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return;
	}
}