package networkChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	String hostname;
	int port;
	
	Socket ssock;
	
	BufferedReader in;
	PrintWriter out;
	
	// Nickname. Can be changed.
	String nick;
	
	/**
	 * Print an error to stdout and exit.
	 * 
	 * @param desc		String to print before exiting.
	 * @param exit_code	Integer to exit with.
	 */
	private void exitWith(String desc, int exit_code) {
		System.err.println("ERROR: " + desc);
		System.exit(exit_code);
	}
	
	public void connectTo(String hostname, int port) {
		// connect to server socket
		try {
		    ssock = new Socket(hostname, port);
        } catch (UnknownHostException e) {
        	exitWith("Can't find server at " + hostname, 1);
        } catch (IOException e) {
        	exitWith("IOException at " + hostname + " - is there a server running on that port?", 2);
		}
		
		// set up IO
		try {
			out = new PrintWriter(ssock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(ssock.getInputStream()));
		} catch (IOException e) {
            exitWith("Exception caught when trying to set up IO with a client", 1);
		}
		
		this.hostname = hostname;
		this.port = port;
	}
	
	public String getInput() {
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		
		try {
			input = stdin.readLine();
		} catch (IOException e) {
			exitWith("IOException at stdin", 3);
		}
		
		return input;
	}
	
	public String parseInput(String input) {
		//String[] command = {"PRIVMSG", input};
		//return command;
		return "PRIVMSG " + input;
	}

	//public void sendMessage(String[] message) {
	public void sendMessage(String message) {
		out.println(message);
	}
	
	public void sendInput() {
		String input = getInput();
		String message = parseInput(input);
		sendMessage(message);
	}
	
	public void listen() {
		while (true) {
			// read client messages
			String input;
			try {
				while ((input = in.readLine()) != null) {
					System.out.println("IN: " + input);
				}
			} catch (IOException e) {
				exitWith("Exception caught when trying to read from client", 1);
			}
		}
	}
	
	public static void main(String[] args) {
		Client client = new Client();
		client.connectTo("127.0.0.1", 6789);
		
		// listen for & print incoming messages
		Runnable listener = new Runnable() {
			public void run() {
				client.listen();
			}
		};
		Thread listenThread = new Thread(listener);
		listenThread.start();
		
		// read stdin
		while (true) {
			client.sendInput();
		}
	}
}
