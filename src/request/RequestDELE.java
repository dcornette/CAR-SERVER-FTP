package request;

import java.io.File;
import java.io.IOException;

import server.FtpClient;
import exception.RequestException;

public class RequestDELE implements Request {
	
	/**
	 * Singleton 
	 */
	private RequestDELE(){}
	
	private static RequestDELE INSTANCE = new RequestDELE();
	
	/**
	 * Créer une instance de la requete QUIT
	 * @return 
	 */
	public static RequestDELE getInstance(){
		return INSTANCE;
	}

	@Override
	public void executeRequest(String[] requete, FtpClient ftp) throws RequestException {
		if (!requete[0].equals("DELE")){
			this.nextRequest(requete, ftp);
			return;
		} else {
			
			String path;
			if (requete[1].startsWith("/")){
				path = requete[1];
			} else {
				path = ftp.getRoot()+requete[1];
			}

			File test = new File(path);
			
			if (test.isFile()){
				try {
					if (test.delete()){
						ftp.getWriter().println("226 file removed "+ test.getCanonicalPath());
					} else {
						ftp.getWriter().println("451 Impossible to remove file "+ test.getCanonicalPath());
					}
				}
				catch (IOException e){
					e.printStackTrace();
				}
			} else {
				try {
					ftp.getWriter().println("451 Impossible to remove file "+ test.getCanonicalPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void nextRequest(String[] requete, FtpClient ftp) throws RequestException {
		RequestUnknown.getInstance().executeRequest(requete, ftp);
	}
}