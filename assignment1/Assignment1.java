package assignment1;

import java.io.*;
import java.util.Scanner;

class Convertor {
    public void xmlToJSON(File xmlFile) throws IOException {
        String xml = "";

        try(Scanner filScanner = new Scanner(xmlFile)) {
            while(filScanner.hasNextLine()) {
                xml += filScanner.nextLine().toString().trim();
            }
        }

        System.out.println(xml);

        String[] tags = xml.split("<");

        String json = "{";
        boolean isSingle = true;
        int previousTagLength = 1;
        int currentTagLength = 1;
        String previousTagName = "";

        for (String tg : tags) {
            if(tg != ""){
                String[] attributes = tg.split(">");

                if(attributes.length == 1) {
                    currentTagLength = 1;
                }else {
                    currentTagLength = 2;
                }

                if((previousTagLength == 1 && currentTagLength == 2) || (previousTagLength == 1 && currentTagLength == 1)) {
                    if(!attributes[0].startsWith("/") && !json.endsWith("{")){
                        json += ",";
                    }
                }
                
                if(attributes.length == 1) {
                    if(attributes[0].startsWith("/") && isSingle) {
                        json += "}";
                    }
                    if(!attributes[0].startsWith("/")) {
                        String[] atts = attributes[0].split(" ");
                        if(atts.length == 1) {
                            json += "\""+attributes[0]+"\": {";
                        }else {
                            String[] at = atts[1].split("=");
                            json += "\""+atts[0]+"\": { \"@"+at[0]+"\": \""+at[1].split("'")[1]+"\"";
                        }
                    }
                    previousTagLength = 1;
                    isSingle = true;
                }else {
                    if(previousTagName == attributes[0]) {

                    }else {
                        if(attributes[0].split(" ").length == 1) {
                            json += "\""+attributes[0]+"\": \""+attributes[1]+"\"";
                        }else {
                            String[] innerAttributes = attributes[0].split(" ");

                            String[] innerAttribute = innerAttributes[1].split("=");

                            json += "\""+innerAttributes[0]+"\": { \"@"+innerAttribute[0]+"\": \""+innerAttribute[1].split("'")[1]+"\", \"content\": \""+attributes[1]+"\" }";
                        }
                    }
                    previousTagName = attributes[0];
                    isSingle = false;
                    previousTagLength = 2;
                }
            }
        }

        json += "}";

        System.out.println(json);

        File jsonFile = new File("D:/projects/college-related-projects/EAD_Labs/assignment1/output/test.json");
        FileWriter fileWriter = new FileWriter(jsonFile);

        fileWriter.write(json);
        fileWriter.close();
    }
}

public class Assignment1 {
    public static void main(String[] args) throws IOException {
        Convertor convertor = new Convertor();
        File xmlFile = new File("D:/projects/college-related-projects/EAD_Labs/assignment1/inputs/test.xml");
        convertor.xmlToJSON(xmlFile);
    }
}
