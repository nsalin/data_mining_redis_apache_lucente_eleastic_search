package noSQLConnections;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
/**
 * Created by Alin on 10/8/2015.
 */
public class ESConnection {
    private String host;
    private int port;
    protected Client esClient;

    public ESConnection(String esHost, int esPort){
        this.host = esHost;
        this.port = esPort;
        esClient = new TransportClient().addTransportAddress(new InetSocketTransportAddress(this.host, this.port));
    }

    public IndexResponse createIndexForWordsAndTDIDF(String word, double tdidf) throws IOException {
        IndexResponse indexResponse = esClient.prepareIndex(word, "token")
                .setSource(jsonBuilder()
                .startObject()
                .field("word", word)
                .field("TdidfValue", tdidf)
                .endObject())
                .execute()
                .actionGet();
        return indexResponse;
    }

    public IndexResponse createIndexForFileAndWords(String fileName, Object words) throws IOException {
        IndexResponse indexResponse = esClient.prepareIndex(fileName, "file")
                .setSource(jsonBuilder()
                .startObject()
                        .field("fileName", fileName)
                        .field("fileWords", words)
                .endObject())
                .execute()
                .actionGet();

        return indexResponse;
    }

    public IndexResponse createIndexFromFolderForFilesAndWords(String indexName,String indexType,String directoryName ,List<File> fileName, Object words) throws IOException {
//        XContentBuilder xContentBuilder = XContentFactory.jsonBuilder().startObject();
//
////        xContentBuilder.startArray(directoryName);
////            xContentBuilder.startArray(dirName);
//            for (File file : fileName){
//                xContentBuilder.startArray(file.toString());
//                xContentBuilder.value(words.toString());
//                xContentBuilder.endArray();
//            }
////        xContentBuilder.endArray();
//        xContentBuilder.endObject();
        //return esClient.prepareIndex(indexName,indexType).setSource(xContentBuilder).execute().actionGet();

        IndexResponse response = esClient.prepareIndex(indexName,indexType)
                .setSource(jsonBuilder()
                .startObject()
                .field("directory", directoryName)
                .field("fileName", fileName)
                .field("file words", words))
                .execute().actionGet();
        return response;
    }
}
