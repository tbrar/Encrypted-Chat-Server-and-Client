import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.security.Key;
import java.util.Base64;

import javax.swing.JOptionPane;

public class Server extends GUI{
	
	public void start() throws IOException, InterruptedException, ClassNotFoundException {
    	window.setTitle("Chat Server");
    	ServerSocket serverSocket = new ServerSocket(49276);
    	clientSocket = serverSocket.accept();
    	serverSocket.close();
    	exchange = new KeyExchange(2048);
    	exchange.generatePG();
    	out = new ObjectOutputStream(clientSocket.getOutputStream());
    	in = new ObjectInputStream(clientSocket.getInputStream());
    	out.writeObject(exchange.getP());
    	out.writeObject(exchange.getG());
    	out.writeObject(exchange.getPublicKey());
    	exchange.generateSecret((Key) in.readObject());
    	byte[] secret = exchange.getSecret();
    	printMessage("Your shared secret is " + new BigInteger(secret).toString());
    	aes = new AES(secret);
    	aes.generateIV();
    	out.writeObject(Base64.getEncoder().encodeToString(aes.getIV()));
    	JOptionPane.showMessageDialog(null, "Connection Established");
        button.setEnabled(true);
    	Thread receiver = new Thread(new Receiver(clientSocket, in, aes));
    	receiver.start();
    }
}