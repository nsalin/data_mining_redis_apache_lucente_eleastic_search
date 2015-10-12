package noSQLConnections;

import redis.clients.jedis.Jedis;

/**
 * Created by Alin on 10/5/2015.
*/
public class RedisConnection {
    private String host;
    private int port;
    private Jedis jedis;

    public RedisConnection (String hostName, int portNumber){
        this.host = hostName;
        this.port = portNumber;
        jedis = new Jedis(host, port);
    }

    private boolean redisHasConnection(Jedis jedis){
        return jedis.ping().equals("PONG");
    }

    public Jedis jedisStatement(){
        if (!redisHasConnection(jedis)){
            return null;
        }
        return jedis;
    }





}
