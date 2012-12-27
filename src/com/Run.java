package com;


//import com.game.core.Props;
import com.core.Config;
import java.io.IOException;



public class Run {	
	
    
	public static void main(String[] args) throws IOException {           
            Run run = new Run();
	}
        
	
	public Run() throws IOException {
            
            Config.loadProperties();
		
		// Start the bot
		GameBot bot = new GameBot(Config.getServer(),
                                            Config.getPort(),
                                            Config.getNick(),
                                            Config.getRealName());
                
                
                
		bot.start(Config.getPrefix(),
                            Config.getChannel(),
                            Config.getOwner());
              
	}
}