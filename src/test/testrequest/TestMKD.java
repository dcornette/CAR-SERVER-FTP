package test.testrequest;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestMKD extends TestRequest {

	@Test
	public void creationOKTest() {
		// begin the connection
		this.client.receiveRequest();

		// request user
		this.client.sendRequest("USER test");
		this.client.receiveRequest();
		
		// request pass
		this.client.sendRequest("PASS test");
		this.client.receiveRequest();
		
		this.client.sendRequest("MKD /tmp/bidule");
		assertEquals(this.client.receiveRequest(), "226 Folder created /tmp/bidule");
		
		this.client.sendRequest("RMD /tmp/bidule");
		
	}
	
	@Test
	public void creationFailTest() {
		// begin the connection
		this.client.receiveRequest();

		// request user
		this.client.sendRequest("USER test");
		this.client.receiveRequest();
		
		// request pass
		this.client.sendRequest("PASS test");
		this.client.receiveRequest();
		
		this.client.sendRequest("MKD /var/bidule");
		assertEquals(this.client.receiveRequest(), "451 Impossible to create folder /var/bidule");
		
	}

}
