package request;

import exception.RequestException;
import server.FtpClient;

/**
 * Utilisation du design pattern "chain of responsability"
 *
 */
public interface Request {

	/**
	 * Permet de lancer le traitement de la requete.
	 * Dans le cas ou la requete ne correspond pas, on passe a la suivante.
	 * @param requete
	 * @param ftp
	 * @throws RequestException 
	 */
	public void executeRequest(final String[] requete, final FtpClient ftp) throws RequestException;

	/**
	 * Permet de creer la requete suivante
	 * @param requete
	 * @param ftp
	 */
	public void nextRequest(final String[] requete, final FtpClient ftp) throws RequestException;

}
