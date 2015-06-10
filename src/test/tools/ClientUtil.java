package test.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * Simulation d'un client FTP afin de pour lancer les tests sur les requetes.
 *
 */
public class ClientUtil {

	private InetAddress address;
	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;
	
	private ServerSocket dataSocket;
	private Socket socketServer;
	
	/**
	 * 
	 * @param port
	 */
	public ClientUtil(final int port) {
		try {
			this.address = InetAddress.getLocalHost();
			this.socket = new Socket(this.address, port);
			this.writer = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()), true);
			this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			this.dataSocket = new ServerSocket(5897);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Permet de simuler l'envoi d'une requete ftp sur un serveur
	 * @param ftp request
	 */
	public void sendRequest(final String request) {
		this.writer.println(request);
	}
	
	/**
	 * Permet de demarrer le serveur FTP
	 */
	public void startDataSocket() {
		boolean isClosedDataSocket = false;
		while (!isClosedDataSocket) {
			try {
				this.socketServer = this.dataSocket.accept();
			} catch (IOException e) {
				isClosedDataSocket = true;
			}
		}	
	}
	
	/**
	 * Permet de simuler la reception d'une requete sur le client FTP
	 * @return message returned from the server
	 */
	public String receiveRequest() {
		String returnMessage=null;
		try {
			returnMessage = this.reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnMessage;
	}
	
	/**
	 * Ferme toute la simulation d'un client FTP
	 */
	public void closeAll(){
		try {
			this.reader.close();
			this.writer.close();
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Close the dataSocket and the serverSocket
	 */
	public void stopDataSocket() {
		try {
			this.dataSocket.close();
			if(this.socketServer != null) {
				this.socketServer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Socket getSocket() {
		return socket;
	}

	public ServerSocket getDataSocket() {
		return dataSocket;
	}
}