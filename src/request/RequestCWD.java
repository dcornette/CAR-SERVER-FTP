package request;

import java.io.File;
import java.io.IOException;

import config.AllConstant;

import server.FtpClient;
import exception.RequestException;

public class RequestCWD implements Request {
	
	/**
	 * Singleton 
	 */
	private RequestCWD(){}
	
	private static RequestCWD INSTANCE = new RequestCWD();
	
	/**
	 * Créer une instance de la requete CWD
	 * @return 
	 */
	public static RequestCWD getInstance(){
		return INSTANCE;
	}

	@Override
	public void executeRequest(String[] requete, FtpClient ftp) throws RequestException {
		if (!requete[0].equals("CWD") && !requete[0].equals("CDUP")){
			this.nextRequest(requete, ftp);
			return;
		} 
		/* Vérification des droits de lecture */
		if (!ftp.getClient().isRead()){
			throw new RequestException(ftp, AllConstant.LOG_FILE_UNAVAILABLE, AllConstant.MSG_FILE_UNAVAILABLE);
		}
		/* creation backup */
		String backup_root = ftp.getRoot();
		
		if (requete[0].equals("CWD")){
			if (requete[1].startsWith("/")){
				ftp.setRoot(requete[1]);
			} else {
				ftp.setRoot(ftp.getRoot()+requete[1]);
			}			
		} else {
			ftp.setRoot(ftp.getRoot()+"..");
		}
		
		if (!ftp.getRoot().endsWith("/")){
			ftp.setRoot(ftp.getRoot()+"/");
		}
		
		changeDirectory(ftp, backup_root);
	}

	private void changeDirectory(FtpClient ftp, String backup_root) {
		/* On crée le fichier pour tester son existance */
		File file;
		file = new File(ftp.getRoot());
		/* s'il existe, on change le racine du serveur et on utilise le canonical pathname */
		if (file.exists()){
			try {
				if (file.getCanonicalPath().equals("/")){
					ftp.setRoot(file.getCanonicalPath());
				} else {
					ftp.setRoot(file.getCanonicalPath()+"/");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			ftp.sendResponse(AllConstant.MSG_CWD_SUCCESS);
			
		} else {
			/* sinon on restaure la racine */
			ftp.setRoot(backup_root);
			ftp.sendResponse(AllConstant.MSG_FILE_UNAVAILABLE);
		}
	}

	@Override
	public void nextRequest(String[] requete, FtpClient ftp) throws RequestException {
		RequestPASV.getInstance().executeRequest(requete, ftp);
	}
}