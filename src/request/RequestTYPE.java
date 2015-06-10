package request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import server.FtpClient;
import exception.RequestException;

public class RequestTYPE implements Request {
	
	/**
	 * Singleton 
	 */
	private RequestTYPE(){}
	
	private static RequestTYPE INSTANCE = new RequestTYPE();
	
	/**
	 * Créer une instance de la requete QUIT
	 * @return 
	 */
	public static RequestTYPE getInstance(){
		return INSTANCE;
	}

	@Override
	public void executeRequest(String[] requete, FtpClient ftp) throws RequestException {
		if (!requete[0].equals("TYPE")){
			this.nextRequest(requete, ftp);
			return;
		} else {
			String type = null;
			try {
				if (requete[1].equals("I")){
					type = "I";
					ftp.setWriter(new PrintWriter(new OutputStreamWriter(ftp.getSocket().getOutputStream(), "UTF8"), true));
					ftp.setReader(new BufferedReader(new InputStreamReader(ftp.getSocket().getInputStream(), "UTF8")));
				} else if (requete[1].equals("A")){
					type = "A";
					ftp.setWriter(new PrintWriter(new OutputStreamWriter(ftp.getSocket().getOutputStream()), true));
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			ftp.getWriter().println("200 Type set to "+type);
		}
	}

	@Override
	public void nextRequest(String[] requete, FtpClient ftp) throws RequestException {
		RequestMKD.getInstance().executeRequest(requete, ftp);
	}
}