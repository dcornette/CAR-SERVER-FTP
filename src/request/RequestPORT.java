package request;

import config.AllConstant;
import server.FtpClient;
import exception.RequestException;

public class RequestPORT implements Request {
	
	/**
	 * Singleton 
	 */
	private RequestPORT(){}
	
	private static RequestPORT INSTANCE = new RequestPORT();
	
	/**
	 * Créer une instance de la requete PORT
	 * @return 
	 */
	public static RequestPORT getInstance(){
		return INSTANCE;
	}

	@Override
	public void executeRequest(String[] requete, FtpClient ftp) throws RequestException {
		if (!requete[0].equals("PORT")){
			this.nextRequest(requete, ftp);
			return;
		}
		
		/* Construction du port et de l'adresse */
		String ports[] = requete[1].split(",");
		ftp.setAddress(ports[0] + "." + ports[1] + "." + ports[2] + "." + ports[3]);
		ftp.setPort((Integer.parseInt(ports[4]) * 256) + Integer.parseInt(ports[5]));
		ftp.sendResponse(AllConstant.MSG_PORT_SUCCESS);
		
		/* desactivation du mode passif */
		ftp.setPassive(false);
	}

	@Override
	public void nextRequest(String[] requete, FtpClient ftp) throws RequestException {
		RequestRETR.getInstance().executeRequest(requete, ftp);
	}	
}