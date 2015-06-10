package request;

import java.io.File;
import java.io.IOException;

import server.FtpClient;
import exception.RequestException;

public class RequestRMD implements Request {
	
	/**
	 * Singleton 
	 */
	private RequestRMD(){}
	
	private static RequestRMD INSTANCE = new RequestRMD();
	
	/**
	 * Créer une instance de la requete QUIT
	 * @return 
	 */
	public static RequestRMD getInstance(){
		return INSTANCE;
	}

	@Override
	public void executeRequest(String[] requete, FtpClient ftp) throws RequestException {
		if (!requete[0].equals("RMD")){
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
			
			if (test.isDirectory()){
				try {
					if (removeFile(test.getCanonicalPath())){
						ftp.getWriter().println("226 Folder removed "+ test.getCanonicalPath());
					} else {
						ftp.getWriter().println("451 Impossible to remove folder "+ test.getCanonicalPath());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					ftp.getWriter().println("451 Impossible to remove folder "+ test.getCanonicalPath());
				}
				catch (IOException e){
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void nextRequest(String[] requete, FtpClient ftp) throws RequestException {
		RequestDELE.getInstance().executeRequest(requete, ftp);
	}
	
	private boolean removeFile(final String file){
		if (new File(file).isDirectory()){
			File listFiles[] = new File(file).listFiles();
			for (File child: listFiles){
				try {
					removeFile(child.getCanonicalPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return new File(file).delete();
		} else {
			return new File(file).delete();
		}
	}
}
