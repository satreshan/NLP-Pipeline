package db;

import static org.junit.Assert.*;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class RedisConnectionTest {
	RedisConnection rconn;
	
	public RedisConnectionTest(){
		rconn = new RedisConnection("localhost");
	}
	
	@Test
	public void connectionTest(){		
		assertEquals("PONG", rconn.jedis.ping());
	}
	
	@Test
	public void pushValueTest(){
		rconn.pushValue("DoctorLetter", "Medical");
	}
	
	@Test
	public void popValueTest(){
		assertEquals("Medical",rconn.popValue("DoctorLetter"));
	}	

	@Test
	public void delValueTest(){
		assertEquals("1",rconn.deleteValue("DoctorLetter").toString());
	}
}
