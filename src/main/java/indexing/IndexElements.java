package indexing;

import redis.RedisConnection;
import redis.clients.jedis.Jedis;
import tfidf.CalculateTFIDF;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static utils.Constants.*;

/**
 * Created by Win81 on 10/7/2015.
 */
public class IndexElements {
    public static void main(String args[]) throws IOException {
        FilesManager filesManager = new FilesManager(ROOT_FILES_DIRECTORY);
        RedisConnection redisCon = new RedisConnection(LOCALHOST, DEFAULT_REDIS_PORT);
        CalculateTFIDF calculateTFIDF = new CalculateTFIDF();

        int counter = 0;
        List<String> documentsTerms = filesManager.getElementsFromAllFiles();
        Map<String, List> filesAndTerms = filesManager.getDocumentTermsMap();
        Jedis jedis = redisCon.jedisStatement();
        jedis.flushDB();
        jedis.select(1);

        for (Map.Entry<String, List> stringListEntry : filesAndTerms.entrySet()){
            for (Object indexTerm : stringListEntry.getValue()){
                if (indexTerm instanceof String ){
                    String[] allElements = documentsTerms.toArray(new String[documentsTerms.size()]);
                    //List<String[]> list = stringListEntry.getValue();
                    double tdIdfTermValue = calculateTFIDF.getTFIDF(allElements,documentsTerms, indexTerm.toString());
                    jedis.set(indexTerm.toString(), String.valueOf(tdIdfTermValue));
                    jedis.sadd(stringListEntry.getKey(), indexTerm.toString());
                }

            }

        }
        System.out.println("\n\ncounter " + counter);
    }
}
