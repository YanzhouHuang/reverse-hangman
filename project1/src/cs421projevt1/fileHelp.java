package cs421projevt1;

import java.io.BufferedReader;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.util.ArrayList;  
import java.util.List;  
  
public class fileHelp {  
    public static List<String> readFileByLines(String filePath){    
        File file = new File(filePath);    
        if(!file.exists() || !file.isFile()){    
            return null;    
        }           
        List<String> content = new ArrayList<String>();    
        try {    
            FileInputStream fileInputStream = new FileInputStream(file);    
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "GBK");    
            BufferedReader reader = new BufferedReader(inputStreamReader);    
            String lineContent = "";    
            while ((lineContent = reader.readLine()) != null) {    
                content.add(lineContent);    
            }    
                
            fileInputStream.close();    
            inputStreamReader.close();    
            reader.close();    
        } catch (FileNotFoundException e) {    
            e.printStackTrace();    
        } catch (IOException e) {    
            e.printStackTrace();    
        }    
        return content;    
    }    
}  