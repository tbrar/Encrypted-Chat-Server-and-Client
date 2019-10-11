import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class ConnectionTest {

	@Test
	public void test() {
		String message = "hello world";
		ServerRunner server =  new ServerRunner();
		Thread serverThread = new Thread(server);
		Connection client = new Connection();
		serverThread.start();
		client.initalizeClientConnection();
		server.getServer().send(message);
		String rev = (String) client.receive();
	    assertEquals(message, rev);
	}
}

final class ServerRunner implements Runnable{
	Connection server;
	
	public void run(){
		server = new Connection();
		server.initalizeServerConnection();
	}
	
	public Connection getServer() {
		return server;
	}

}