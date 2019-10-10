import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public abstract class GUI {
	JFrame window;
	static JTextArea textBox;
	static JTextArea log;
	JScrollPane scroll;
    JButton button;
    Thread mainThread;
    Socket clientSocket;
    KeyExchange exchange;
    ObjectInputStream in;
    ObjectOutputStream out;
    AES aes;
    
    public GUI() {
    	mainThread = Thread.currentThread();
		window = new JFrame();
		window.setExtendedState(window.MAXIMIZED_BOTH);
		textBox = new JTextArea();
		textBox.setFont(textBox.getFont().deriveFont(18f));
		log = new JTextArea();
		log.setLineWrap(true);
		log.setEditable(false);
		log.setFont(log.getFont().deriveFont(18f));
		scroll = new JScrollPane(log);
		scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        button = new JButton("Send");
        button.setEnabled(false);
        GridBagConstraints con = new GridBagConstraints();
        con.fill = GridBagConstraints.BOTH;
		window.setSize(500, 500);
        window.setLayout(new GridBagLayout());
        con.gridx = 0;
        con.gridy = 0;
        con.gridwidth = 3;
        con.weightx = 1;
        con.weighty = 4;
        window.add(scroll,con);
        con.gridx = 2;
        con.gridy = 3;
        con.gridwidth = 1;
        con.weightx = 1;
        con.weighty = 1;
        window.add(button,con);
        con.gridx = 0;
        con.gridy = 3;
        con.gridwidth = 2;
        con.weightx = 3;
        con.weighty = 1;
        textBox.setPreferredSize(new Dimension(50,50));
        window.add(textBox,con);
		window.setVisible(true);
		textBox.setBorder(BorderFactory.createLineBorder(Color.black));
		log.setBorder(BorderFactory.createLineBorder(Color.black));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				printMessage("You: Decrypted: " + textBox.getText());
				printMessage("You: Encrypted: " + aes.encrypt(textBox.getText()));
				try {
					out.writeObject(aes.encrypt(textBox.getText()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				textBox.setText("");
			}
		});
		
		WindowAdapter adapter = new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent e) {
	        	if(clientSocket != null) {
	        		try {
						clientSocket.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	        	}
	           System.exit(0);
	        }
	    };
	    window.addWindowListener(adapter);
	    window.addWindowFocusListener(adapter);
    }
    
    public abstract void start() throws IOException, InterruptedException, ClassNotFoundException; // Will initiate the connection between client and server
    
    public static synchronized void printMessage(String message) {
    	String contents = log.getText();
    	log.setText(contents + "\n" + message);
    	return;
    }
}
