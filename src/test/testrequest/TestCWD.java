package test.testrequest;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestCWD extends TestRequest {

	@Test
	public void actualDirectorytest() {
		// begin the connection
		this.client.receiveRequest();

		// request user
		this.client.sendRequest("USER test");
		this.client.receiveRequest();
		
		// request pass
		this.client.sendRequest("PASS test");
		this.client.receiveRequest();
		
		this.client.sendRequest("PWD");
		assertEquals(this.client.receiveRequest(), "257 \"/\" is current directory.");
	}
	
	@Test
	public void changeDirectorytest() {
		// begin the connection
		this.client.receiveRequest();

		// request user
		this.client.sendRequest("USER test");
		this.client.receiveRequest();
		
		// request pass
		this.client.sendRequest("PASS test");
		this.client.receiveRequest();
		
		this.client.sendRequest("CWD home");
		assertEquals(this.client.receiveRequest(), "250 CWD command successful.");
		
		this.client.sendRequest("PWD");
		assertEquals(this.client.receiveRequest(), "257 \"/home/\" is current directory.");
	}
	
	@Test
	public void badChangeFakeDirectorytest() {
		// begin the connection
		this.client.receiveRequest();

		// request user
		this.client.sendRequest("USER test");
		this.client.receiveRequest();
		
		// request pass
		this.client.sendRequest("PASS test");
		this.client.receiveRequest();
		
		this.client.sendRequest("CWD bidule");
		assertEquals(this.client.receiveRequest(), "550 Requested action not taken. File unavailable (e.g., file not found, no access).");
		
		this.client.sendRequest("PWD");
		assertEquals(this.client.receiveRequest(), "257 \"/\" is current directory.");
	}
}
