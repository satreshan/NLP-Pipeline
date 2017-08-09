package db;

public class Run {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RedisConnection redis = new RedisConnection("localhost");
		System.out.println(redis.popValue("names"));
	}

}
