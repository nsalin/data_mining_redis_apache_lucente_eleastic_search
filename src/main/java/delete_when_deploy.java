import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Win81 on 10/6/2015.
 */
public class delete_when_deploy {
    public static void main(String args[]) throws IOException {

//        File folder = new File(Constants.ROOT_FILES_DIRECTORY);
//        getFolders(folder);
//    }
//    public static List<File> getFolders(File folder) {
//        List<File> files = new ArrayList<>();
//        for (File fileEntry : folder.listFiles()) {
//            if (fileEntry.isDirectory()) {
//                for(File file : fileEntry.listFiles()){
//                    if (file.isFile()){
//                        files.add(file);
//                    }
//                }
//                getFolders(fileEntry);
//            }
//        }
//        return files;
//        FilesManager fileManager = new FilesManager(Constants.ROOT_FILES_DIRECTORY);
////        List<String> strings = fileManager.getElementsFromAllFiles();
//        int counter=0;
////        for (String str : strings){
////            counter++;
////            System.out.println(str);
////
////        }
////
//        for (Map.Entry<String, List> entry : fileManager.getDocumentTermsMap().entrySet()){
//            System.out.println("DocumentName-> " + entry.getKey());
//            System.out.println("TermsInDoc-> " + entry.getValue());
//            counter++;
//        }
//        System.out.println("\n\n\n" + counter);
        String[] plm = {"1", "2", "3", "20"};
        String[] plm2 = {"101", "2", "3", "20"};
        String[] plm3 = {"134", "2", "3", "20"};
        List<String[]> kt = new ArrayList<>();
        kt.add(plm);
        kt.add(plm2);
        kt.add(plm3);

//        String[] vr = (String[]) kt.toArray();
//
//        for(String elements : vr) {
//            //for (String element : elements) {
//                System.out.println(elements);
           // }
       // }
//
//        System.out.println(plm.length + " " + kt.size());
//
//        double val = 1.0465;
//        String vals = String.valueOf(val);
//        System.out.println(val);
    }
}
