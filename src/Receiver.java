import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Receiver implements Runnable{
	Socket socket;
	ObjectInputStream in;
	AES aes;
	public Receiver(Connection connection, AES aes) {
		this.socket = connection.getClientSocket();
		this.in = connection.getIn();
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
