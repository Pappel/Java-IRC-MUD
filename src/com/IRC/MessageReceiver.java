package com.IRC;

public interface MessageReceiver {
	/**
	 * Parsed messages are sent here
	 * @param message the parsed message
	 */
	void receiveMessage(Message message);
}