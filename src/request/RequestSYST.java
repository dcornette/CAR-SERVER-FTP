package request;

import config.AllConstant;
import server.FtpClient;
import exception.RequestException;

public class RequestSYST implements Request {
	
	/**
	 * Singleton 
	 */
	private RequestSYST(){}
	
	private static RequestSYST INSTANCE = new RequestSYST();
	
	/**
	 * Créer une instance de la requete QUIT
	 * @return 
	 */
	public static RequestSYST getInstance(){
		return INSTANCE;
	}

	@Override
	public void executeRequest(String[] requete, FtpClient ftp) throws RequestException {
		if (!requete[0].equals("SYST")){
			this.nextRequest(requete, ftp);
			return;
		} else {
			ftp.sendResponse(AllConstant.MSG_UNIX_TYPE);
		}
	}

	@Override
	public void nextRequest(String[] requete, FtpClient ftp) throws RequestException {
		RequestTYPE.getInstance().executeRequest(requete, ftp);
	}
}