package config;

public class AllConstant {

	/* Debug mode */
	public static final Boolean DEBUG = true;
	
	/* Root directory */
	public static final String ROOT = "/";
	
	/* Log message */
	public static final String LOG_BAD_USER = "Error : Bad user";
	public static final String LOG_BAD_PASS = "Error : Password rejected";
	public static final String LOG_FILE_UNAVAILABLE = "Error : File unavailable";
	public static final String LOG_ERROR_PARAMETER = "Error : Error syntax in parameter";
	public static final String LOG_USER_MISSING = "Error : Need valid username";
	public static final String LOG_IO_ERROR = "Error : Can't open data connection";
	
	/* Message to the client */
	public static final String MSG_PORT_SUCCESS = "200 PORT command successful.";
	public static final String MSG_COMMAND_NOT_IMPLEMENTED = "202 command not implemented";
	public static final String MSG_UNIX_TYPE = "215 UNIX Type: L8";
	public static final String MSG_CONNECTION_SUCCESS = "220 connection etablished, plz login";
	public static final String MSG_TRANSFERT_COMPLETE = "226 Transfer complete";
	public static final String MSG_CONNECTION_ANONYMOUS = "230 Logged in anonymous";
	public static final String MSG_CWD_SUCCESS = "250 CWD command successful.";
	public static final String MSG_GOOD_USER = "331 login OK, Password required";
	public static final String MSG_USER_MISSING = "332 need valid username";
	public static final String MSG_IO_ERROR = "425 Can't open data connection.";
	public static final String MSG_BAD_USER = "430 Bad username";
	public static final String MSG_BAD_PASS = "430 Password rejected";
	public static final String MSG_ERROR_PARAMETER = "501 syntax error in parameters or arguments";
	public static final String MSG_FILE_UNAVAILABLE = "550 Requested action not taken. File unavailable (e.g., file not found, no access).";	
}