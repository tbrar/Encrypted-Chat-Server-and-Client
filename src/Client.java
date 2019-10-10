import java.io.IOException;
import java.math.BigInteger;
import java.net.UnknownHostException;
import java.security.Key;
import java.util.Base64;

import javax.swing.JOptionPane;

public class Client extends GUI{
	
	public void start() throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException {
    	window.setTitle("Chat Client");
    	connection.initalizeClientConnection();
    	initalizeCreds();
    	JOptionPane.showMessageDialog(null, "Connection Established");
        button.setEnabled(true);
    	Thread receiver = new Thread(new Receiver(connection, aes));
    	receiver.start();
    }
	
	protected void initalizeCreds() {
    	exchange = new KeyExchange(2048);
    	exchange.setPG((BigInteger) connection.receive(), (BigInteger) connection.receive());
    	exchange.generateSecret( (Key) connection.receive());
    	connection.send(exchange.getPublicKey());
    	byte[] secret = exchange.getSecret();
    	aes = new AES(secret);
    	aes.setIV(Base64.getDecoder().decode((String) connection.receive()));
    	printMessage("Your shared secret is " + new BigInteger(secret).toString());
	}
	
}