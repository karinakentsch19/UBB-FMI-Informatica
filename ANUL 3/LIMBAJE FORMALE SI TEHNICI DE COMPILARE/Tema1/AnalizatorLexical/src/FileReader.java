
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {
    private String fileName;

    public FileReader(String fileName) {
        this.fileName = fileName;
    }

     public List<String> readFromFile(){
         try {
             List<String> fileData = new ArrayList<>();
             File myObj = new File(this.fileName);
             Scanner myReader = new Scanner(myObj);
             while (myReader.hasNextLine()) {
                 String data = myReader.nextLine();
                 fileData.add(data);
             }
             myReader.close();
             return fileData;
         } catch (FileNotFoundException e) {
             System.out.println("An error occurred.");
             e.printStackTrace();
         }
         return new ArrayList<>();
     }
}
