package networkChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	String server;
	int port;
	
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
		try (
		    Socket echoSocket = new Socket(hostname, port);
		    PrintWriter out =
		        new PrintWriter(echoSocket.getOutputStream(), true);
		    BufferedReader in =
		        new BufferedReader(
		            new InputStreamReader(echoSocket.getInputStream()));
		    BufferedReader stdIn =
		        new BufferedReader(new InputStreamReader(System.in));
		) {
			String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("echo: " + in.readLine());
            }
        } catch (UnknownHostException e) {
        	exitWith("Can't find server at " + hostname, 1);
        } catch (IOException e) {
        	exitWith("IOException at  " + hostname, 1);
		}
	}
	
	public void sendCommand(String[] command) {
	}
	
	public static void main(String[] args) {
		Client client = new Client();
		client.connectTo("127.0.0.1", 6789);
	}
}
