package test.testrequest;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class TestDELE extends TestRequest {

	@Test
	public void goodRemovetest() {
		// Create a fake file to the test
		try {
			Runtime.getRuntime().exec(new String[]{"bash","-c","touch /tmp/test"});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// begin the connection
		this.client.receiveRequest();

		// request user
		this.client.sendRequest("USER test");
		this.client.receiveRequest();
		
		// request pass
		this.client.sendRequest("PASS test");
		this.client.receiveRequest();
		
		this.client.sendRequest("DELE /tmp/test");
		assertEquals(this.client.receiveRequest(), "226 file removed /tmp/test");
	}
	
	@Test
	public void failedToRemovetest() {
		// begin the connection
		this.client.receiveRequest();

		// request user
		this.client.sendRequest("USER test");
		this.client.receiveRequest();
		
		// request pass
		this.client.sendRequest("PASS test");
		this.client.receiveRequest();
		
		this.client.sendRequest("DELE /tmp/test");
		assertEquals(this.client.receiveRequest(), "451 Impossible to remove file /tmp/test");
	}

	
	
}
