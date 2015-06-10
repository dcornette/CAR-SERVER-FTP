package request;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import config.AllConstant;

import server.FtpClient;
import exception.RequestException;

public class RequestLIST implements Request {

	/**
	 * Singleton 
	 */
	private RequestLIST(){}
	
	private static RequestLIST INSTANCE = new RequestLIST();
	
	/**
	 * Créer une instance de la requete LIST
	 * @return 
	 */
	public static RequestLIST getInstance(){
		return INSTANCE;
	}

	/**
	 * @see <a href="https://filezilla-project.org/specs/eplf.html">https://filezilla-project.org/specs/eplf.html</a>
	 */
	@Override
	public void executeRequest(String[] requete, FtpClient ftp) throws RequestException {
		if (!requete[0].equals("LIST") && !requete[0].equals("NLST")){
			this.nextRequest(requete, ftp);
			return;
		}
		/* Vérification des droits de lecture */
		if (!ftp.getClient().isRead()){
			ftp.sendResponse(AllConstant.MSG_IO_ERROR);
			return;
		}
		String directory = ftp.getRoot();
		if (requete.length > 1){
			if (requete[1].startsWith("/") || requete[1].equals("/")){
				directory = requete[1];
			} else {
				directory = directory+requete[1];
			}
		}
		
		// On oublie pas de vérifier que le répertoire fini bien par un /
		if(!directory.endsWith("/")) {
			directory += "/";
		}
		
		/* On check depuis la racine */
		File rootDirectory = new File(directory);
		/* On liste tous les fichiers */
		File listFiles[] = rootDirectory.listFiles();
		String nameCurrentFile="";
		ftp.getWriter().println("150 Opening ASCII mode data connection for file list");
		
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
			
			/* Affichage des dossiers et fichiers */
			if (listFiles.length > 0){
				for (int i=0; i<listFiles.length; i++){
					if (!listFiles[i].isHidden()){
						if (listFiles[i].isFile()){
							nameCurrentFile = "+s"+listFiles[i].length()+",m"+listFiles[i].lastModified()+",\011"+listFiles[i].getName()+"\015\012";
						} else {
							nameCurrentFile = "+/,m"+listFiles[i].lastModified()+",\011"+listFiles[i].getName()+"\015\012";
						}
						
						//nameCurrentFile = directory+nameCurrentFile+"\n";
						ftp.getDataWriter().writeBytes(nameCurrentFile);
						ftp.getDataWriter().flush();
					}
				}
			}
			/* fermeture de la socket */
			ftp.getDataSocket().close();
			ftp.sendResponse(AllConstant.MSG_TRANSFERT_COMPLETE);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			throw new RequestException(ftp, AllConstant.LOG_IO_ERROR, AllConstant.MSG_IO_ERROR);
		}
	}

	@Override
	public void nextRequest(String[] requete, FtpClient ftp) throws RequestException {
		RequestPORT.getInstance().executeRequest(requete, ftp);
	}
}