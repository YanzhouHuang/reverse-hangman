package cs421projevt1;

import java.util.ArrayList;  
import java.util.Iterator;  
import java.util.List;  
  
import cs421projevt1.fileHelp;  
  
public class ParseToken {  
      
    public ArrayList<String> []tokenString=null;  
    public List<String> allString = null;  
      
    @SuppressWarnings("unchecked")  
    public void parseToken(){  
          
        List<String> list = fileHelp.readFileByLines("doc.txt");  
        Iterator<String> it =  list.iterator();  
        String token=null, tmp=null;  
          
        tokenString = new ArrayList[20];  
        for(int i=0; i < tokenString.length; i++)  
            tokenString[i] = new ArrayList<String>();  
        if(allString==null) allString = new ArrayList<String>();  
          
        while(it.hasNext())  
        {  
            token="";  
            tmp = it.next();  
      
            int i;  
            for(i=0; i < tmp.length(); i++){  
                
                if(tmp.charAt(i)!= ' ') token += tmp.charAt(i);  
                else break;  
            }  
            if(i!=0)  
            {  
                tokenString[i].add(token);  
                allString.add(token);  
            }  
        }  
    }  
   
}  