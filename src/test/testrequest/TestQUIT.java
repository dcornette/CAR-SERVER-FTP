package test.testrequest;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestQUIT extends TestRequest {

	@Test
	public void test() {
		// begin the connection
		this.client.receiveRequest();

		// request user
		this.client.sendRequest("USER test");
		this.client.receiveRequest();
		
		// request pass
		this.client.sendRequest("PASS test");
		this.client.receiveRequest();
		
		this.client.sendRequest("QUIT");
		assertEquals("Vous êtes maintenant déconnecté du serveur", this.client.receiveRequest());
	}

}
