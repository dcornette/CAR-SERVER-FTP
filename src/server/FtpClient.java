package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import request.RequestUSER;
import config.AllConstant;
import config.Client;
import exception.RequestException;

public class FtpClient implements Runnable {

	private Client client;
	private List<Client> listeClients;

	private String root;
	private int port;
	private String address;
	private boolean passive;
	
	private BufferedReader reader;
	private PrintWriter writer;
	private DataInputStream dataReader;
	private DataOutputStream dataWriter;
	
	private Socket socket;
	private Socket dataSocket;
	
	private ServerSocket passiveServer;
	
	public FtpClient(final Socket socket){
		client = new Client();
		this.initClients();
		this.root = AllConstant.ROOT;
		this.port = socket.getPort();
		this.address = socket.getInetAddress().getHostAddress();
		this.socket = socket;
		try {
			this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			this.writer = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()), true);
			this.sendResponse(AllConstant.MSG_CONNECTION_SUCCESS);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initClients(){
		listeClients = new ArrayList<Client>();
		Client client1 = new Client();
		Client client2 = new Client();
		client1.setUsername("test");
		client1.setPasword("test");
		client1.setRead(true);
		client1.setWrite(true);
		client2.setUsername("damdam");
		client2.setPasword("damdam");
		client2.setRead(true);
		listeClients.add(client1);
		listeClients.add(client2);
	}
	
	/**
	 * Lit en continue les requêtes envoyées par le client et les transmets aux
	 * objets de type Request pour trouver et éxécuter la tâche voulue. Appelée
	 * dans run()
	 * 
	 * @throws IOException
	 */
	public void processRequest() throws IOException {
		String line;
		while ((line = reader.readLine()) != null) {
			String request[] = line.split("\\s");
			try {
				RequestUSER.getInstance().executeRequest(request, this);
			} catch (RequestException e) {
				if(AllConstant.DEBUG) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
	
	@Override
	public void run() {
		try {
			this.processRequest();
		} catch (IOException e) {
			
		} 		
	}
	
	/**
	 * Ferme la connection du client
	 * @throws IOException
	 */
	public void closeClient() {
		try {
			this.socket.close();
		} catch (IOException e) {
			System.out.println("Quelque chose c'est mal passé avec la fermeture du client");
		}
		System.out.println("User logout : "+this.getClient().getUsername());
	}
	
	/**
	 * Précise si l'utilisateur fais bien parti de ceux autorisés
	 * @param Nom du client
	 * @return True si le serveur
	 */
	public boolean containsUserNamed(final String name){
		for (Client user : this.listeClients){
			if (user.getUsername().equals(name))
				return true;
		}
		return false;
	}
	
	/**
	 * retourne l'utilisateur nommé @param
	 * @param le nom du client 
	 * @return Le client
	 */
	public Client getUserNamed(final String name){
		for (Client user : this.listeClients){
			if (user.getUsername().equals(name))
				return user;
		}
		return null;
		
	}
	
	/**
	 * Permet d'envoyer une requête à un client
	 * @param ftp Le client ftp
	 * @param request La requête
	 */
	public void sendResponse(final String request) {
		this.writer.println(request);
	}
	
	public DataInputStream getDataReader() {
		return dataReader;
	}
	public void setDataReader(DataInputStream dataReader) {
		this.dataReader = dataReader;
	}
	public Socket getSocket() {
		return socket;
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public DataOutputStream getDataWriter() {
		return dataWriter;
	}
	public void setDataWriter(DataOutputStream dataWriter) {
		this.dataWriter = dataWriter;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public Socket getDataSocket() {
		return dataSocket;
	}
	public void setDataSocket(Socket dataSocket) {
		this.dataSocket = dataSocket;
	}
	public boolean isPassive() {
		return passive;
	}
	public void setPassive(boolean passive) {
		this.passive = passive;
	}
	public String getRoot() {
		if(this.root.endsWith("/")) {
			return root;	
		} else {
			return (this.root + "/");
		}
	}
	public void setRoot(String root) {
		if(root.endsWith("/")) {	
			this.root = root;
		} else {
			this.root = (root + "/");
		}
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public List<Client> getListeClients() {
		return listeClients;
	}
	public void setListeClients(List<Client> listeClients) {
		this.listeClients = listeClients;
	}
	public BufferedReader getReader() {
		return reader;
	}
	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}
	public PrintWriter getWriter() {
		return writer;
	}
	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}

	public ServerSocket getPassiveServer() {
		return passiveServer;
	}

	public void setPassiveServer(ServerSocket passiveServer) {
		this.passiveServer = passiveServer;
	}
}
