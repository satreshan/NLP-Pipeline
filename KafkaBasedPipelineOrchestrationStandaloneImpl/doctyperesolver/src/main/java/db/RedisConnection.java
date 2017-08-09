package db;

import redis.clients.jedis.Jedis;

public class RedisConnection {
	Jedis jedis = null;
	//Create connection object
	public RedisConnection(String hostName){
		jedis = new Jedis(hostName);
		//System.out.println("Ping redis: "+ jedis.ping());
	}
	
	//Push value
	public void pushValue(String key,String value){
		//jedis.lpush(key, value);
		jedis.set(key, value);
	}
	
	//Pop value
	public String popValue(String key){
		//return jedis.lpop(key);
		return jedis.get(key);
	}
	
	//Delete value	
	public Long deleteValue(String key){
		return jedis.del(key);
	}
	
	//Close connection
	public void close(){
		jedis.close();
	}
}
