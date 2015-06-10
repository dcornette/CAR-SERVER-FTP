package request;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import config.AllConstant;

import server.FtpClient;
import exception.RequestException;

public class RequestSTOR implements Request {
	
	/**
	 * Singleton 
	 */
	private RequestSTOR(){}
	
	private static RequestSTOR INSTANCE = new RequestSTOR();
	
	/**
	 * Créer une instance de la requete STOR
	 * @return 
	 */
	public static RequestSTOR getInstance(){
		return INSTANCE;
	}

	@Override
	public void executeRequest(String[] requete, FtpClient ftp) throws RequestException {
		if (!requete[0].equals("STOR")){
			this.nextRequest(requete, ftp);
			return;
		}
		/* Vérification des droits de lecture */
		if (!ftp.getClient().isRead()){
			throw new RequestException(ftp, AllConstant.LOG_FILE_UNAVAILABLE, AllConstant.MSG_FILE_UNAVAILABLE);
		} else {
			String nameCurrentFile=requete[1];
			ftp.getWriter().println("150 Opening ASCII mode data connection for "+nameCurrentFile);

			storeFile(ftp, nameCurrentFile);
		}
					
	}

	private void storeFile(FtpClient ftp, String nameCurrentFile) {
		try {
			/* Si le mode Passif est activé */
			if (ftp.isPassive()){
				ftp.setDataSocket(ftp.getPassiveServer().accept());
			} else {
				/* socket utilisée pour les données */
				ftp.setDataSocket(new Socket(InetAddress.getByName(ftp.getAddress()), ftp.getPort()));
			}
			
			/* mise en place d'un reader pour une copie sur le serveur, on place le fichier a copier en input*/
			ftp.setDataReader(new DataInputStream(ftp.getDataSocket().getInputStream()));
			/* On crée le fichier distant (celui sur le serveur) */
			FileOutputStream file = new FileOutputStream(ftp.getRoot()+nameCurrentFile);
			/* copie du fichier */
			int character;
			while ((character=ftp.getDataReader().read()) != -1){
				file.write(character);	
			}
			/* On ferme le tout */
			file.close();
			ftp.getDataSocket().close();
			
			/* affichage pour le serveur */
			System.out.println(ftp.getClient().getUsername()+" puts the file "+nameCurrentFile);
			
			ftp.sendResponse(AllConstant.MSG_TRANSFERT_COMPLETE);
		} catch(IOException e){
			ftp.sendResponse(AllConstant.MSG_IO_ERROR);
		}
	}

	@Override
	public void nextRequest(String[] requete, FtpClient ftp) throws RequestException {
		RequestQUIT.getInstance().executeRequest(requete, ftp);
	}
}