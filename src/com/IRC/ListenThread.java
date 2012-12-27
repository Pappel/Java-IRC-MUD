package com.IRC;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListenThread implements Runnable {
    
        
	private Scanner in; // Scanner from which we will get input
	private BotCore core;
	private Logger logger; // logging
        

           public static final Object lock = new Object();
           public static volatile boolean validate = true;
           
           
	/**
	 * Constructor
	 * @param core bot core for which we are listening
	 * @param in Scanner we are listening to
	 */
	public ListenThread(BotCore core, Scanner in) throws IOException {
		logger = Logger.getLogger("GameBot");
		this.core = core;
		this.in = in;    
	}
        

        @Override
        public void run() {
            while(true) {
                synchronized (lock) { 
                    if (validate) {
                        if( !in.hasNext() )
                            return;

                            String input = in.nextLine();
                            logger.log(Level.INFO, "GOT: {0}", input);
                        
                            if (input.startsWith("PING ") )
                                handlePing(input);
                            else {
                                core.receiveRawMessage(input);
                            }
                       
                    }
                }
            }
        }

	
	/**
	 * Respond to PING
	 * @param ping String containing the PING we were sent
	 */
	private void handlePing(String ping)
	{	
		if( ping.startsWith("PING") )
		{
			String command = "PONG " + ping.substring(5) + "\r\n";
			core.sendRawMessage(command);
		}
	}
        
	
}