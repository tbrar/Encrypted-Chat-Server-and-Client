import static org.junit.Assert.*;

import org.junit.Test;

public class GUITest {

	@Test
	public void printMessageTest() {
		String message = "hello world";
		Server server = new Server();
		String expected = Server.log.getText() + "\n" + message;
		GUI.printMessage(message);
		assertEquals(expected, GUI.log.getText());
		
	}

}
