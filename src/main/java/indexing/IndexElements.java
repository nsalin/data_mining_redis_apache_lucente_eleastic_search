package indexing;

import noSQLConnections.ESConnection;
import noSQLConnections.RedisConnection;
import redis.clients.jedis.Jedis;
import utils.CalculateTFIDF;
import utils.FilesManager;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static utils.Constants.*;

/**
 * Created by Alin on 10/7/2015.
 */
public class IndexElements {
    public static void main(String args[]) throws IOException {
        RedisConnection redisCon = new RedisConnection(LOCALHOST, DEFAULT_REDIS_PORT);
        ESConnection esConnection = new ESConnection(LOCALHOST, DEFAULT_ES_PORT_TRANSPORTCONNECTION);

        FilesManager filesManager = new FilesManager(ROOT_FILES_DIRECTORY);
        CalculateTFIDF calculateTFIDF = new CalculateTFIDF();

        List<String> documentsTerms = filesManager.getFilesContent(); //replace getFilesContent with getFilesContentWithLucene in order to use Lucene Tokenizer
        Map<String, List> filesAndTerms = filesManager.getDocumentTermsMap();
        Map<File, List<File>> directories = filesManager.getFolders();

        Jedis jedis = redisCon.jedisStatement();
        jedis.flushAll();
        jedis.select(1);


        for (Map.Entry<String, List> stringListEntry : filesAndTerms.entrySet()){
            for (Object indexTerm : stringListEntry.getValue()){
                if (indexTerm instanceof String ){
                    String[] allElements = documentsTerms.toArray(new String[documentsTerms.size()]);
                    double tdIdfTermValue = calculateTFIDF.getTFIDF(allElements,documentsTerms, indexTerm.toString());

                    jedis.set(indexTerm.toString(), String.valueOf(tdIdfTermValue));//index in Redis word->tdidf
                    jedis.sadd(stringListEntry.getKey(), indexTerm.toString());//index in Redis document -> set of words

                    //index in ES word -> TDIDF
                    esConnection.createIndexForWordsAndTDIDF(indexTerm.toString(), tdIdfTermValue);
                }

            }
            //index in ES  document -> list of words
            esConnection.createIndexForFileAndWords(stringListEntry.getKey(), stringListEntry.getValue());
        }

        jedis.close();

        //index in ES negative/positive -> document -> list of words
        for (Map.Entry<File, List<File>> entry : directories.entrySet()){
            if(entry.getKey().toString().contains("neg")){
                esConnection.createIndexFromFolderForFilesAndWords("negative", "review", entry.getKey().toString(), entry.getValue(), filesManager.getFileContentWithLucene(entry.getKey()));
            }
            else {
                esConnection.createIndexFromFolderForFilesAndWords("positive", "review", entry.getKey().toString(), entry.getValue(), filesManager.getFileContentWithLucene(entry.getKey()));
            }
        }
    }
}
