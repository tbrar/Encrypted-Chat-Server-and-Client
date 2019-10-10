import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection {

    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    
    public Connection(){
    }
    
    public void initalizeServerConnection() {
    	ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(49276);
	    	clientSocket = serverSocket.accept();
	    	setupStreams();
	    	serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void initalizeClientConnection() {
    	try {
			clientSocket = new Socket("127.0.0.1", 49276);
	    	setupStreams();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void setupStreams() {
    	try {
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			in = new ObjectInputStream(clientSocket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void send(Object message) {
    	try {
			out.writeObject(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public Object receive() {
    	try {
			return in.readObject();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }

	public Socket getClientSocket() {
		return clientSocket;
	}

	public ObjectInputStream getIn() {
		return in;
	}

	public ObjectOutputStream getOut() {
		return out;
	}
    
}
