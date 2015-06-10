package request;

import config.AllConstant;
import server.FtpClient;
import exception.RequestException;

public class RequestUnknown implements Request {
	
	/**
	 * Singleton 
	 */
	private RequestUnknown(){}
	
	private static RequestUnknown INSTANCE = new RequestUnknown();
	
	/**
	 * Créer une instance de la requete inconnue
	 * @return 
	 */
	public static RequestUnknown getInstance(){
		return INSTANCE;
	}

	@Override
	public void executeRequest(String[] requete, FtpClient ftp) throws RequestException {
		ftp.sendResponse(AllConstant.MSG_COMMAND_NOT_IMPLEMENTED);
	}

	@Override
	public void nextRequest(String[] requete, FtpClient ftp) {
	}
}