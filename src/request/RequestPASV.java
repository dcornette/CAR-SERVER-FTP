package request;

import java.io.IOException;
import java.net.ServerSocket;

import server.FtpClient;
import exception.RequestException;

public class RequestPASV implements Request {
	
	/**
	 * Singleton 
	 */
	private RequestPASV(){}
	
	private static RequestPASV INSTANCE = new RequestPASV();
	
	/**
	 * Créer une instance de la requete CWD
	 * @return 
	 */
	public static RequestPASV getInstance(){
		return INSTANCE;
	}

	@Override
	public void executeRequest(String[] requete, FtpClient ftp) throws RequestException {
		if (!requete[0].equals("PASV")){
			this.nextRequest(requete, ftp);
			return;
		} 
		
		ServerSocket newConnection;
		try {
			newConnection = new ServerSocket(0);
			
			ftp.setPassiveServer(newConnection);
			
			int port = newConnection.getLocalPort();
			
			int port1 = port / 256; 
			int port2 = port % 256;
			
			String clientAddress = ftp.getSocket().getLocalAddress().getHostAddress();
			String decomposeAddres[] = clientAddress.split("\\.");
			
			String ip = decomposeAddres[0]+","+decomposeAddres[1]+","+decomposeAddres[2]+","+decomposeAddres[3]+","+port1+","+port2;
			
			ftp.getWriter().println("227 Entering Passive Mode ("+ip+").");
			
			
			ftp.setPassive(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void nextRequest(String[] requete, FtpClient ftp) throws RequestException {
		RequestPWD.getInstance().executeRequest(requete, ftp);
	}
}