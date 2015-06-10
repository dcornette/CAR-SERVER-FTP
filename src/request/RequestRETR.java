package request;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import config.AllConstant;

import server.FtpClient;
import exception.RequestException;

public class RequestRETR implements Request {
	
	/**
	 * Singleton 
	 */
	private RequestRETR(){}
	
	private static RequestRETR INSTANCE = new RequestRETR();
	
	/**
	 * Créer une instance de la requete RETR
	 * @return 
	 */
	public static RequestRETR getInstance(){
		return INSTANCE;
	}

	@Override
	public void executeRequest(String[] requete, FtpClient ftp) throws RequestException {
		if (!requete[0].equals("RETR")){
			this.nextRequest(requete, ftp);
			return;
		}
		/* Vérification des droits de lecture */
		if (!ftp.getClient().isRead()){
			throw new RequestException(ftp, AllConstant.LOG_FILE_UNAVAILABLE, AllConstant.MSG_FILE_UNAVAILABLE);
		} else {
			String nameCurrentFile=requete[1];
			ftp.getWriter().println("150 Opening ASCII mode data connection for "+nameCurrentFile);
			
			getFile(ftp, nameCurrentFile);
		}
					
	}

	private void getFile(FtpClient ftp, String nameCurrentFile) {
		try {
			/* Si le mode Passif est activé */
			if (ftp.isPassive()){
				ftp.setDataSocket(ftp.getPassiveServer().accept());
			} else {
				/* socket utilisée pour les données */
				ftp.setDataSocket(new Socket(InetAddress.getByName(ftp.getAddress()), ftp.getPort()));
			}
			
			/* mise en place d'un writer pour une copie locale, on place le fichier a copier en out*/
			ftp.setDataWriter(new DataOutputStream(ftp.getDataSocket().getOutputStream()));
			/* On crée le fichier distant (celui sur le serveur) */
			FileInputStream file = new FileInputStream(ftp.getRoot()+nameCurrentFile);
			/* copie du fichier */
			int character;
			while ((character=file.read()) != -1){
				ftp.getDataWriter().write(character);
			}
			
			/* On ferme le tout */
			file.close();
			ftp.getDataSocket().close();
			
			/* affichage pour le serveur */
			System.out.println(ftp.getClient().getUsername()+" gets the file "+nameCurrentFile);
			
			
			ftp.sendResponse(AllConstant.MSG_TRANSFERT_COMPLETE);
		} catch(IOException e){
			ftp.sendResponse(AllConstant.MSG_IO_ERROR);
			e.printStackTrace();
		}
	}

	@Override
	public void nextRequest(String[] requete, FtpClient ftp) throws RequestException {
		RequestSTOR.getInstance().executeRequest(requete, ftp);
	}
}