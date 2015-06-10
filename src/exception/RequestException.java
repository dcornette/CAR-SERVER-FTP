package exception;

import server.FtpClient;

public class RequestException extends Exception {

	private static final long serialVersionUID = -3796494876819647990L;
	
	public RequestException(final FtpClient ftp, String errorMsg, String clientMsg) {
		super(errorMsg);			
		ftp.sendResponse(clientMsg);
	}
}