package indexing;

import java.io.*;
import java.util.*;

/**
 * Created by Win81 on 10/7/2015.
 */
public class FilesManager {
    private Map<String,List> documentTermsMap = new HashMap<>();
    private File folderName;

    public FilesManager(File directoryName){
        this.folderName = directoryName;
    }

    public List<String> getElementsFromAllFiles() throws IOException {
        List<File> fileName = getFolders(folderName);
        List<String> documentTerms = new ArrayList<>();
        for (File file : fileName){
            if(file.getName().endsWith(".txt")){
                StringBuilder allInOneString = parseFile(file);
                String[] tokenizerTerms = allInOneString.toString().replaceAll("[^ a-zA-Z0-9]+","").split("\\W+");
                for(String tokenizedTerm : tokenizerTerms){
//                    if (!documentTerms.contains(tokenizedTerm)){
                        documentTerms.add(tokenizedTerm);
//                   }
                }
                List plm = Arrays.asList(tokenizerTerms);
                documentTermsMap.put(file.getName().replace(".txt",""),plm);

            }
        }
        return documentTerms;
    }

    private StringBuilder parseFile(File file) throws IOException {
        BufferedReader bufferedReader;
        bufferedReader = new BufferedReader(new FileReader(file));
        StringBuilder allInOneString = new StringBuilder();
        String line = null;
        while ((line = bufferedReader.readLine()) != null){
            allInOneString.append(line);
        }
        return allInOneString;
    }

    private List<File> getFolders(File folder) {
        List<File> files = new ArrayList<>();
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                for (File file : fileEntry.listFiles()) {
                    if (file.isFile()) {
                        files.add(file);
                    }
                }
                getFolders(fileEntry);
            }
        }
        return files;
    }

    public Map<String,List> getDocumentTermsMap() throws IOException {
        if (documentTermsMap.size() == 0){
            getElementsFromAllFiles();
        }
        return documentTermsMap;
    }
}
