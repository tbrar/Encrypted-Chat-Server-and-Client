import java.io.IOException;
import java.math.BigInteger;
import java.security.Key;
import java.util.Base64;

public class Server extends GUI{
	
	public void start() throws IOException, InterruptedException, ClassNotFoundException {
    	window.setTitle("Chat Server");
    	connection.initalizeServerConnection();
    	initalizeCreds();
        button.setEnabled(true);
    	Thread receiver = new Thread(new Receiver(connection, aes));
    	receiver.start();
    }
	
	protected void initalizeCreds() {
    	exchange = new KeyExchange(2048);
    	exchange.generatePG();
    	connection.send(exchange.getP());
    	connection.send(exchange.getG());
    	connection.send(exchange.getPublicKey());
    	exchange.generateSecret((Key) connection.receive());
    	byte[] secret = exchange.getSecret();
    	aes = new AES(secret);
    	aes.generateIV();
    	connection.send(Base64.getEncoder().encodeToString(aes.getIV()));
    	printMessage("Your shared secret is " + new BigInteger(secret).toString());
	}
	
}