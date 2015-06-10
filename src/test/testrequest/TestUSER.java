package test.testrequest;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestUSER extends TestRequest {

	@Test
	public void testGoodUser() {
		// 220 connection etablished, plz login
		this.client.receiveRequest();
		
		// Envoi de la requête USER
		this.client.sendRequest("USER test");
		assertEquals("331 login OK, Password required", this.client.receiveRequest());
	}
	
	@Test
	public void testBadUser() {
		// 220 connection etablished, plz login
		this.client.receiveRequest();
		
		// Envoi de la requête USER
		this.client.sendRequest("USER fake");
		assertEquals("430 Bad username", this.client.receiveRequest());
	}
	
	@Test
	public void testUserWithoutParam() {
		// 220 connection etablished, plz login
		this.client.receiveRequest();
		
		// Envoi de la requête USER
		this.client.sendRequest("USER ");
		assertEquals("501 syntax error in parameters or arguments", this.client.receiveRequest());
	}
	
	@Test
	public void testGoodAnonymousUser() {
		// 220 connection etablished, plz login
		this.client.receiveRequest();
		
		// Envoi de la requête USER
		this.client.sendRequest("USER anonymous");
		assertEquals("230 Logged in anonymous", this.client.receiveRequest());
	}
	
	
}