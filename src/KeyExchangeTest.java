import static org.junit.Assert.*;

import org.junit.Test;

public class KeyExchangeTest {

	@Test
	public void testCommonKey() { // tests if both parties get the same key during Diffie Hellman Exchange
		KeyExchange server = new KeyExchange(2048);
		KeyExchange client = new KeyExchange(2048);
		server.generatePG();
		client.setPG(server.getP(), server.getG());
		client.generateSecret(server.getPublicKey());
		server.generateSecret(client.getPublicKey());
		byte[] secretClient = client.getSecret();
		byte[] secretServer = server.getSecret();
		assertArrayEquals(secretClient, secretServer);
	}

}
