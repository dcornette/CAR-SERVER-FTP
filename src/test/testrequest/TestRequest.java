package test.testrequest;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import exception.ServerAlreadyStartedException;

import server.Server;
import test.tools.ClientUtil;

public class TestRequest {
	
	protected ClientUtil client;
	private Server serv;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		try {
			serv = new Server(4269);
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						serv.startServer();					
					} catch (ServerAlreadyStartedException e) {
						fail("Le server est déja démarré !");
					}
				}					
			}).start();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				fail("Le server a rencontré un problème de thread");
			}
		} catch (ServerAlreadyStartedException e) {
			fail("Le server est déja démarré !");
		} 
		this.client = new ClientUtil(4269);
	}

	@After
	public void tearDown() throws Exception {
		this.client.closeAll();
		this.serv.stop();
		this.client.stopDataSocket();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
}