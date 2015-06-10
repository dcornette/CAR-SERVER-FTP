package test.testrequest;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestPWD extends TestRequest {

	@Test
	public void goodPWDtest() {
		// begin the connection
		this.client.receiveRequest();

		// request user
		this.client.sendRequest("USER test");
		this.client.receiveRequest();
		
		// request pass
		this.client.sendRequest("PASS test");
		this.client.receiveRequest();
		
		// send pwd command
		this.client.sendRequest("PWD");
		assertEquals("257 \"/\" is current directory.",this.client.receiveRequest());
	}
	
	@Test
	public void badPWDtest() {
		// begin the connection
		this.client.receiveRequest();

		// request user
		this.client.sendRequest("USER test");
		this.client.receiveRequest();
		
		// request pass
		this.client.sendRequest("PASS test");
		this.client.receiveRequest();
		
		// send pwd command
		this.client.sendRequest("PWD");
		assertFalse("257 \"/home/bidule\" is current directory.".equals(this.client.receiveRequest()));
	}
	
	@Test
	public void goodchangedPWDtest() {
		// begin the connection
		this.client.receiveRequest();

		// request user
		this.client.sendRequest("USER test");
		this.client.receiveRequest();
		
		// request pass
		this.client.sendRequest("PASS test");
		this.client.receiveRequest();
		
		// request pass
		this.client.sendRequest("CWD home");
		this.client.receiveRequest();
		
		// send pwd command
		this.client.sendRequest("PWD");
		assertEquals("257 \"/home/\" is current directory.",this.client.receiveRequest());
	}

}
