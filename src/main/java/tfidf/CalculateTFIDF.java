package tfidf;

import java.util.List;

/**
 * Created by alinnitu on 10/6/2015.
 */
public class CalculateTFIDF {

    private double calculateTF(String[] elements, String elementToCheck){
        double elementOccurrence = 0;
        for (String element : elements){
            if (element.equalsIgnoreCase(elementToCheck)){
                elementOccurrence++;
            }
        }
        return elementOccurrence / elements.length;

    }

    private double calculateIDF(List<String> documentElements, String elementToCheck){
        double elementOccurrence = 0;
        //for(String[] elements : documentElements){
            for(String element : documentElements){
                if(element.equalsIgnoreCase(elementToCheck)){
                    elementOccurrence++;
                    break;
                }
            }
       // }
        return 1 + Math.log(documentElements.size() / elementOccurrence);
    }

    public double getTFIDF(String[] elements, List<String> documentElements, String elementToCheck){
        return calculateTF(elements, elementToCheck) * calculateIDF(documentElements, elementToCheck);
    }


}
