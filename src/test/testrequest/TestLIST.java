package test.testrequest;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestLIST extends TestRequest {

	@Test
	public void testLSSuccess() {
		// 220 connection etablished, plz login
		this.client.receiveRequest();
		
		this.client.sendRequest("USER test");
		// 331 login OK, Password required
		this.client.receiveRequest();	
		
		// Envoi de la requête USER
		this.client.sendRequest("PASS test");
		// 230 User test logged in
		this.client.receiveRequest();
		
		// 200 PORT command successful
		int port1 = this.client.getDataSocket().getLocalPort() >> 8;
		int port2 = this.client.getDataSocket().getLocalPort() & 255;
		this.client.sendRequest("PORT 127,0,0,1,"+ port1 +","+ port2);
		this.client.receiveRequest();
		
		// on démarre la socket de données
		new Thread(new Runnable() {
			@Override
			public void run() {
				client.startDataSocket();			
			}					
		}).start();
		
		this.client.sendRequest("LIST");
		// 150 Opening ASCII mode data connection for file list
		this.client.receiveRequest();
		assertEquals("226 Transfer complete", this.client.receiveRequest());
		
		this.client.stopDataSocket();
	}
	
	@Test
	public void testLSIOError() {
		// 220 connection etablished, plz login
		this.client.receiveRequest();
		
		this.client.sendRequest("USER test");
		// 331 login OK, Password required
		this.client.receiveRequest();	
		
		// Envoi de la requête USER
		this.client.sendRequest("PASS test");
		// 230 User test logged in
		this.client.receiveRequest();
		
		this.client.sendRequest("LIST");
		// 150 Opening ASCII mode data connection for file list
		this.client.receiveRequest();
		assertEquals("425 Can't open data connection.", this.client.receiveRequest());
	}
}