package request;

import server.FtpClient;
import exception.RequestException;

public class RequestPWD implements Request {
	
	/**
	 * Singleton 
	 */
	private RequestPWD(){}
	
	private static RequestPWD INSTANCE = new RequestPWD();
	
	/**
	 * Créer une instance de la requete QUIT
	 * @return 
	 */
	public static RequestPWD getInstance(){
		return INSTANCE;
	}

	@Override
	public void executeRequest(String[] requete, FtpClient ftp) throws RequestException {
		if (!requete[0].equals("PWD")){
			this.nextRequest(requete, ftp);
			return;
		} else {
			ftp.getWriter().println("257 \""+ftp.getRoot()+"\" is current directory.");		
		}
	}

	@Override
	public void nextRequest(String[] requete, FtpClient ftp) throws RequestException {
		RequestSYST.getInstance().executeRequest(requete, ftp);
	}
}