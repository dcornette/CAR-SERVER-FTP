package test.testrequest;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestSYST extends TestRequest {

	@Test
	public void goodSystemTypeTest() {
		// begin the connection
		this.client.receiveRequest();

		// request user
		this.client.sendRequest("USER test");
		this.client.receiveRequest();
		
		// request pass
		this.client.sendRequest("PASS test");
		this.client.receiveRequest();
		
		this.client.sendRequest("SYST");
		assertEquals(this.client.receiveRequest(), "215 UNIX Type: L8");
	}
	
	@Test
	public void badSystemTypeTest() {
		// begin the connection
		this.client.receiveRequest();

		// request user
		this.client.sendRequest("USER test");
		this.client.receiveRequest();
		
		// request pass
		this.client.sendRequest("PASS test");
		this.client.receiveRequest();
		
		this.client.sendRequest("SYST");
		assertFalse(this.client.receiveRequest().equals("215 UNIX Type"));
	}
	
}
