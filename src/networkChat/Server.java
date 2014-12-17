package networkChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
	ServerSocket server;
	List<Socket> clients;
	
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
	
	public void listenSocket(int port) {
		try {
			server = new ServerSocket(port); 
		} catch (IOException e) {
			exitWith("could not listen on port " + port, -1);
		}
	}
	
	public void boot(int port) {
        try (
        	ServerSocket server = new ServerSocket(port);
            Socket clientSocket = server.accept();    
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);                  
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
            }
        } catch (IOException e) {
            System.err.println(
            		"Exception caught when trying to listen on port "
            		+ port + " or listening for a connection");
            exitWith(e.getMessage(), 1);
        }
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		server.boot(6789);
	}
}
