package request;

import server.FtpClient;
import exception.RequestException;

public class RequestQUIT implements Request {
	
	/**
	 * Singleton 
	 */
	private RequestQUIT(){}
	
	private static RequestQUIT INSTANCE = new RequestQUIT();
	
	/**
	 * Créer une instance de la requete QUIT
	 * @return 
	 */
	public static RequestQUIT getInstance(){
		return INSTANCE;
	}

	@Override
	public void executeRequest(String[] requete, FtpClient ftp) throws RequestException {
		if (!requete[0].equals("QUIT")){
			this.nextRequest(requete, ftp);
			return;
		} else {
			/* Fermeture de la socket du client */
			ftp.getWriter().println("Vous êtes maintenant déconnecté du serveur");
			ftp.closeClient();		
		}
	}

	@Override
	public void nextRequest(String[] requete, FtpClient ftp) throws RequestException {
		RequestCWD.getInstance().executeRequest(requete, ftp);
	}
}