package request;

import java.io.File;
import java.io.IOException;

import server.FtpClient;
import exception.RequestException;

public class RequestMKD implements Request {
	
	/**
	 * Singleton 
	 */
	private RequestMKD(){}
	
	private static RequestMKD INSTANCE = new RequestMKD();
	
	/**
	 * Créer une instance de la requete QUIT
	 * @return 
	 */
	public static RequestMKD getInstance(){
		return INSTANCE;
	}

	@Override
	public void executeRequest(String[] requete, FtpClient ftp) throws RequestException {
		if (!requete[0].equals("MKD")){
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
			try {
				if (test.mkdir()){
					ftp.getWriter().println("226 Folder created "+ test.getCanonicalPath());
				} else {
					ftp.getWriter().println("451 Impossible to create folder "+ test.getCanonicalPath());
				}
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}
	}

	@Override
	public void nextRequest(String[] requete, FtpClient ftp) throws RequestException {
		RequestRMD.getInstance().executeRequest(requete, ftp);
	}
}