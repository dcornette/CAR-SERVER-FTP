package test.testrequest;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestCDUP extends TestRequest {

	@Test
	public void changeUpDirectorytest() {
		// begin the connection
		this.client.receiveRequest();

		// request user
		this.client.sendRequest("USER test");
		this.client.receiveRequest();
		
		// request pass
		this.client.sendRequest("PASS test");
		this.client.receiveRequest();
		
		this.client.sendRequest("CWD home");
		this.client.receiveRequest();
		
		this.client.sendRequest("CDUP");
		assertEquals(this.client.receiveRequest(), "250 CWD command successful.");
		
		this.client.sendRequest("PWD");
		assertEquals(this.client.receiveRequest(), "257 \"/\" is current directory.");
	}
	
	@Test
	public void badChangeUpDirectorytest() {
		// begin the connection
		this.client.receiveRequest();

		// request user
		this.client.sendRequest("USER test");
		this.client.receiveRequest();
		
		// request pass
		this.client.sendRequest("PASS test");
		this.client.receiveRequest();
		
		this.client.sendRequest("CDUP");
		assertEquals(this.client.receiveRequest(), "250 CWD command successful.");
		
		this.client.sendRequest("PWD");
		assertEquals(this.client.receiveRequest(), "257 \"/\" is current directory.");
	}

}
