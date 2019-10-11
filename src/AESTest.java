import static org.junit.Assert.*;

import java.util.Base64;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class AESTest {

	byte[] key = new byte[200];
	byte[] message = new byte[500];
	
	@Before
	public void initalize() {
		Random random = new Random();
		random.nextBytes(key);
		random.nextBytes(message);
	}
	@Test
	public void test() {
		AES server = new AES(key);
		AES client = new AES(key);
		server.generateIV();
		client.setIV(server.getIV());
		String encrypted = server.encrypt(Base64.getEncoder().encodeToString(message));
		assertArrayEquals(message, Base64.getDecoder().decode(client.decrypt(encrypted)));
	}

}
