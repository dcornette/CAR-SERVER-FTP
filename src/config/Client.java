package config;

public class Client {

	private String username;
	private String pasword;
	private boolean read;
	private boolean write;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasword() {
		return pasword;
	}

	public void setPasword(String pasword) {
		this.pasword = pasword;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public boolean isWrite() {
		return write;
	}

	public void setWrite(boolean write) {
		this.write = write;
	}

	public Client() {
		this.username="";
		this.pasword="";
		this.read=false;
		this.write=false;
	}
}
