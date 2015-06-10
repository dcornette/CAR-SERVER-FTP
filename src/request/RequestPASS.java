package request;

import config.AllConstant;
import server.FtpClient;
import exception.RequestException;

public class RequestPASS implements Request {

	/**
	 * Singleton 
	 */
	private RequestPASS(){}
	
	private static RequestPASS INSTANCE = new RequestPASS();
	
	/**
	 * Créer une instance de la requete PASS
	 * @return 
	 */
	public static RequestPASS getInstance(){
		return INSTANCE;
	}

	@Override
	public void executeRequest(String[] requete, FtpClient ftp) throws RequestException {
		if (!requete[0].equals("PASS")){
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
		if(ftp.getClient().getUsername().isEmpty()) {
			throw new RequestException(ftp, AllConstant.LOG_USER_MISSING, AllConstant.MSG_USER_MISSING);
		}
		if(requete.length < 2) {
			throw new RequestException(ftp, AllConstant.LOG_ERROR_PARAMETER, AllConstant.MSG_ERROR_PARAMETER);
		}
		if (ftp.getClient().getPasword().equals(requete[1])){
			ftp.getWriter().println("230 User "+ftp.getClient().getUsername()+" logged in");
			System.out.println("User connected : "+ftp.getClient().getUsername());
		} else {
			throw new RequestException(ftp, AllConstant.LOG_BAD_PASS, AllConstant.MSG_BAD_PASS);
		}		
	}

	@Override
	public void nextRequest(String[] requete, FtpClient ftp) throws RequestException {
		RequestLIST.getInstance().executeRequest(requete, ftp);
	}
}