package com.IRC;

import com.core.threads.DBSave;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Provides low-level functionality to IRC Bots
 * @author kwgivler
 *
 */
public class BotCore {
	private String server; // IRC Server address
	private int port; // port on which to connect
        
        
        //Bin bin = new Bin();  // create a bin

	private static Socket socket;
        private static Scanner in; // Scanner for reading lines from the server
	private static PrintWriter out; // PrintWriter for sending lines to the server
	private MessageReceiver receiver; // The IMessageReceiver we will send messages to
	private static boolean connected = false; // are we connected?
	private static Logger logger; // Used for logging
	private MessageParser parser; // Message Parser
        
        
        

	/**
	 * GameBot constructor
	 * @param server IRC Server Address
	 * @param port IRC Port
	 * @param nick nickname for the bot
	 * @param realName the realname of the bot
	 * @param bot the bot we are providing services for
	 */
	public BotCore(String server, int port, MessageReceiver receiver)
	{
		logger = Logger.getLogger("TFI_CORE");
		this.port = port;
		this.server = server;
		this.receiver = receiver;
	}

	// --------------------------------------------------------------------------------------------------

	/**
	 * Connect to IRC server
	 */
	public void connect(String nick, String realName) throws IOException
	{
		// Set up MessageParser
		parser = new MessageParser(nick);
		
		// Open socket
		try {
			socket = new Socket(server, port);

                        InputStream instream = socket.getInputStream();
			OutputStream outstream = socket.getOutputStream();
			this.in = new Scanner(instream);
			out = new PrintWriter(outstream);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Unable to connect: {0}:{1}", new Object[]{server, port});
			e.printStackTrace();
			System.exit(0);
		}

		// Set up thread which listens for server output
                ListenThread listenThread = new ListenThread(this, in);
		Thread serverListener = new Thread(listenThread);
                serverListener.setName("serverListener");
		serverListener.start();
                
                DBSave dbSave = new DBSave();
                
                

		// Determine hostname
		String hostName="localhost";
		try {
			InetAddress addr = InetAddress.getLocalHost();
			hostName = addr.getHostName();
			logger.log(Level.INFO, "hostname: {0}", hostName);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(0);
		}

		// Register Connection
		sendRawMessage("NICK " + nick + "\r\n");
		sendRawMessage("USER " + nick + " " + hostName + " " + server + " :" + realName + "\r\n");

		// Wait for connection to register
		while(!connected)
		{
			System.out.println("Connecting...");
			try {
				Thread.sleep(1000);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
                
	}

	// --------------------------------------------------------------------------------------------------

	/**
	 * Disconnect from server
	 */
	public void disconnect()
	{
		if(!connected)
			logger.info("Not Connected");

		sendRawMessage("QUIT\r\n");
		try
		{
			socket.close();
			out.close();
			in.close();
		} catch (IOException e)
		{
			logger.warning("Exception while disconnecting:");
			e.printStackTrace();
			System.exit(0);
		}

		connected = false;
	}

	// --------------------------------------------------------------------------------------------------

	/**
	 * Send string directly to server
	 * @param command String to send IRC server
	 */
	public static void sendRawMessage(String command)
	{	
		logger.log(Level.INFO, "SENDING: {0}", command);
		out.print(command);
		out.flush();
	}
        


	// --------------------------------------------------------------------------------------------------

	/**
	 * Check if bot is connected
	 * @return true if connected to server, false if not connected
	 */
	public static boolean isConnected()
	{
		return connected;
	}

	/**
	 * Receive raw server messages from listen thread
	 * @param message
	 */
	public void receiveRawMessage(String message)
	{
            
		Message parsedMessage = parser.parseMessage(message);
		
		if(!connected)
		{
			String command = parsedMessage.getCommand();
			if(command.equals("433"))
			{
				System.out.println("Nick in use!");
				disconnect();
				System.exit(0);
			}

			if(command.equals("004"))
			{
				System.out.println("Connected!");
				connected = true;
			}
		} else
			receiver.receiveMessage(parsedMessage);
	}
        
        
        

}