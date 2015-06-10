package test.testrequest;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestRMD extends TestRequest {

	@Test
	public void suppressionOKTest() {
		// begin the connection
		this.client.receiveRequest();

		// request user
		this.client.sendRequest("USER test");
		this.client.receiveRequest();
		
		// request pass
		this.client.sendRequest("PASS test");
		this.client.receiveRequest();
		
		this.client.sendRequest("MKD /tmp/bidule");
		this.client.receiveRequest();
		
		this.client.sendRequest("RMD /tmp/bidule");
		assertEquals(this.client.receiveRequest(), "226 Folder removed /tmp/bidule");
		
	}
	
	@Test
	public void suppressionFailedTest() {
		// begin the connection
		this.client.receiveRequest();

		// request user
		this.client.sendRequest("USER test");
		this.client.receiveRequest();
		
		// request pass
		this.client.sendRequest("PASS test");
		this.client.receiveRequest();
		
		this.client.sendRequest("RMD /tmp/locked");
		assertEquals(this.client.receiveRequest(), "451 Impossible to remove folder /tmp/locked");
		
	}
	
	
	

}
