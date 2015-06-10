package test.testrequest;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestPASS extends TestRequest {

	@Test
	public void testGoodPassWithGoodUser() {
		// 220 connection etablished, plz login
		this.client.receiveRequest();
		this.client.sendRequest("USER test");
		// 331 login OK, Password required
		this.client.receiveRequest();		
		
		// Envoi de la requête USER
		this.client.sendRequest("PASS test");
		assertEquals("230 User test logged in", this.client.receiveRequest());
	}
	
	@Test
	public void testBadPassWithGoodUser() {
		// 220 connection etablished, plz login
		this.client.receiveRequest();
		this.client.sendRequest("USER test");
		// 331 login OK, Password required
		this.client.receiveRequest();		
		
		// Envoi de la requête USER
		this.client.sendRequest("PASS fake");
		assertEquals("430 Password rejected", this.client.receiveRequest());
	}
	
	@Test
	public void testPassWithoutParam() {
		// 220 connection etablished, plz login
		this.client.receiveRequest();
		this.client.sendRequest("USER test");
		// 331 login OK, Password required
		this.client.receiveRequest();		
		
		// Envoi de la requête USER
		this.client.sendRequest("PASS ");
		assertEquals("501 syntax error in parameters or arguments", this.client.receiveRequest());
	}
	
	@Test
	public void testPassWithoutUser() {
		// 220 connection etablished, plz login
		this.client.receiveRequest();	
		
		// Envoi de la requête USER
		this.client.sendRequest("PASS test");
		assertEquals("332 need valid username", this.client.receiveRequest());
	}
}