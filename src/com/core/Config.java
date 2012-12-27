package com.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.logging.Logger;


public class Config {
    
    private static Logger log = Logger.getLogger(Config.class.getName());
    private static Properties config;
    private static final String SEP = System.getProperty("file.separator");
    public static final String MUDROOT = "config";
    
    
    public static void loadProperties() {
        config = new Properties();
        try {
        	String path = "config" + SEP + "bot.conf";
            config.load(new FileInputStream(path));
        }
        catch (FileNotFoundException e) {
            System.out.println("[WARNING] Couldn't find the config file. Loading default values.");
        }
        catch (Exception e) {
            System.err.println("[ERROR] There was an error loading the program configuration:");
            e.printStackTrace();
            System.exit(1);
        }
    }
    
         
    
    public static String getServer() {
        System.out.print(config.getProperty("server"));
    	return config.getProperty("server");
    }
    
    public static int getPort() {
    	String stringport = config.getProperty("port");
        int intport = Integer.parseInt(stringport);
        return intport;
    }
    
    public static String getNick() {
    	return config.getProperty("nick");
    }
    
    public static String getRealName() {
    	return config.getProperty("realname");
    }
    
    public static String getChannel() {
    	return config.getProperty("main_channel");
    }
    
    public static String getPrefix() {
    	return config.getProperty("prefix");
    }
    
    public static String getOwner() {
    	return config.getProperty("owner");
    }
    
    public static String getPassword() {
    	return config.getProperty("password");
    }
    
    
    //DB
    public static String getDatabaseURI() {
        return config.getProperty("db.uri");
    }

    public static String getDatabaseUser() {
	return config.getProperty("db.user");
    }
	
    public static String getDatabasePassword() {
        return config.getProperty("db.password");
    }    
        
        
        
}
