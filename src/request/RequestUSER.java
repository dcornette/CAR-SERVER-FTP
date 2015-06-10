package request;

import config.AllConstant;
import config.Client;
import server.FtpClient;
import exception.RequestException;

public class RequestUSER implements Request {
	
	/**
	 * Singleton 
	 */
	private RequestUSER(){}
	
	private static RequestUSER INSTANCE = new RequestUSER();
	
	/**
	 * Créer une instance de la requete USER
	 * @return 
	 */
	public static RequestUSER getInstance(){
		return INSTANCE;
	}

	@Override
	public void executeRequest(String[] requete, FtpClient ftp) throws RequestException {
		if (!requete[0].equals("USER")){
			this.nextRequest(requete, ftp);
			return;
		}
		this.verifRequete(requete, ftp);
	}
	
	/**
	 * Vérification de la correspondance du mot de passe avec le nom du client
	 * @param requete
	 * @param ftp
	 * @throws RequestException
	 */
	public void verifRequete(String[] requete, FtpClient ftp) throws RequestException {
		/* Verification du nom du client */
		if(requete.length < 2) {
			throw new RequestException(ftp, AllConstant.LOG_ERROR_PARAMETER, AllConstant.MSG_ERROR_PARAMETER);
		}
		if (requete[1].equals("anonymous")){
			Client client = new Client();
			client.setUsername("anonymous");
			client.setRead(true);
			client.setWrite(false);
			ftp.setClient(client);
			ftp.sendResponse(AllConstant.MSG_CONNECTION_ANONYMOUS);
		} else if (ftp.containsUserNamed(requete[1])){
			ftp.setClient(ftp.getUserNamed(requete[1]));
			ftp.sendResponse(AllConstant.MSG_GOOD_USER);
		} else {
			throw new RequestException(ftp, AllConstant.LOG_BAD_USER, AllConstant.MSG_BAD_USER);
		}		
	}

	@Override
	public void nextRequest(String[] requete, FtpClient ftp) throws RequestException {
		RequestPASS.getInstance().executeRequest(requete, ftp);
	}	
}