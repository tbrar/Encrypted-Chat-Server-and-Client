import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Receiver implements Runnable{
	Socket socket;
	ObjectInputStream in;
	AES aes;
	public Receiver(Socket socket, ObjectInputStream in, AES aes) {
		this.socket = socket;
		this.in = in;
		this.aes = aes;
	}
	
	public void run() {
		String message;
        try {
			while((message = (String) in.readObject()) != null)
			{
			    GUI.printMessage("Them: Encrypted: " + message);
			    GUI.printMessage("Them: Decrypted: " + aes.decrypt(message));
			      if (aes.decrypt(message).equals("exit")) {
			    	  in.close();
			      }
			}
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
