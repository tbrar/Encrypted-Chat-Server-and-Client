import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.Key;
import java.util.Base64;

import javax.swing.JOptionPane;

public class Client extends GUI{
	
	public void start() throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException {
    	window.setTitle("Chat Client");
    	clientSocket = new Socket("127.0.0.1", 49276);
    	out = new ObjectOutputStream(clientSocket.getOutputStream());
    	in = new ObjectInputStream(clientSocket.getInputStream());
    	exchange = new KeyExchange(2048);
    	exchange.setPG((BigInteger) in.readObject(), (BigInteger) in.readObject());
    	exchange.generateSecret( (Key) in.readObject());
    	out.writeObject(exchange.getPublicKey());
    	byte[] secret = exchange.getSecret();
    	printMessage("Your shared secret is " + new BigInteger(secret).toString());
    	aes = new AES(secret);
    	aes.setIV(Base64.getDecoder().decode((String) in.readObject()));
    	JOptionPane.showMessageDialog(null, "Connection Established");
        button.setEnabled(true);
    	Thread receiver = new Thread(new Receiver(clientSocket, in, aes));
    	receiver.start();
    }
	
}