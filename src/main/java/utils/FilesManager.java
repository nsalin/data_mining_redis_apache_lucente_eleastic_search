package utils;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.*;
import java.util.*;

/**
 * Created by Alin on 10/7/2015.
 */
public class FilesManager {
    private Map<String,List> documentTermsMap = new HashMap<>();
    List<File> folderList;
    private File folderName;

    public FilesManager(File directoryName){
        this.folderName = directoryName;
    }

    public List<String> getFilesContent() throws IOException {
        folderList = getFilesFromFolders(folderName);
        List<String> documentTerms = null;
        for (File file : folderList){
            documentTerms = new ArrayList<>();
            if(file.getName().endsWith(".txt")){
                StringBuilder allInOneString = parseFile(file);
                String[] tokenizerTerms = allInOneString.toString().replaceAll("[^ a-zA-Z0-9]+","").split("\\W+");
                for(String tokenizedTerm : tokenizerTerms){
                    documentTerms.add(tokenizedTerm);
                }
                List tokensList = Arrays.asList(tokenizerTerms);
                documentTermsMap.put(file.getName().replace(".txt",""),tokensList);

            }
        }
        return documentTerms;
    }
    public List<String> getFilesContentWithLucene() throws IOException {
        List<String> tokens = null;
        folderList = getFilesFromFolders(folderName);
        Analyzer tokenizer = new StandardAnalyzer();
        for (File file : folderList) {
            tokens = new ArrayList<>();
            if (file.getName().endsWith(".txt")) {
                StringReader reader = new StringReader(parseFile(file).toString());
                TokenStream stream  = tokenizer.tokenStream("", reader);
                stream.reset();
                while(stream.incrementToken()) {
                    tokens.add(stream.getAttribute(CharTermAttribute.class).toString());
                }
                stream.end();
                stream.close();
            }
            documentTermsMap.put(file.getName().replace(".txt",""), tokens);
        }


        return tokens;
    }

    public Map<String,List> getDocumentTermsMap() throws IOException {
        if (documentTermsMap.size() == 0){
            return null;
        }
        return documentTermsMap;
    }

    private StringBuilder parseFile(File file) throws IOException {
        BufferedReader bufferedReader;
        bufferedReader = new BufferedReader(new FileReader(file));
        StringBuilder allInOneString = new StringBuilder();
        String line = "";
        while ((line = bufferedReader.readLine()) != null){
            allInOneString.append(line);
        }
        return allInOneString;
    }

    private List<File> getFilesFromFolders(File folder) {
        List<File> files = new ArrayList<>();
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                for (File file : fileEntry.listFiles()) {
                    if (file.isFile()) {
                        files.add(file);
                    }
                }
                getFilesFromFolders(fileEntry);
            }
        }
        return files;
    }
    public Map<File, List<File>> getFolders(){
        Map<File, List<File>> folders = new HashMap<>();
        for (File newFolder : folderName.listFiles()){
            if(!newFolder.isDirectory()){
                getFolders();
            }
            folders.put(newFolder, Arrays.asList(newFolder.listFiles()));
        }
        return folders;
    }

    public List<String> getFileContent(File directoryName) throws IOException {
        List<String> words = new ArrayList<>();
        for(File file : directoryName.listFiles()){
            String[] wordSplit = parseFile(file).toString().replaceAll("[^ a-zA-Z0-9]+","").split("\\W+");
            for(String word : wordSplit){
                words.add(word);

            }
        }
        return words;
    }
}
