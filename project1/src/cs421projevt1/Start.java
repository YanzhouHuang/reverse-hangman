package cs421projevt1;



import java.io.File;  
import java.util.ArrayList;  
import java.util.Iterator;  
import java.util.List;  
import java.util.Random;  
  
import cs421projevt1.fileHelp;  
  
public class Start {  
  
    ParseToken parseToken = null;  
  
    public void startGame() {  
        // �õ����ʿ�      
        getToken();   
          
        // �������ʿ⣬������������ĸ��λ��������  
        parseToken();  
          
        // ���ɵ��������  
        String str = randomSelect();  
          
        autoJudge resu = new autoJudge(str, parseToken);  
          
        resu.getResult();  
    }  
  
    private String randomSelect() {  
      
        int size = parseToken.allString.size();  
        Random rdl = new Random();  
        String str = parseToken.allString.get(rdl.nextInt(size));  
        return str;  
    }  
  
    private void parseToken() {  
        parseToken = new ParseToken();  
        parseToken.parseToken();  
    }  
  
    private void getToken() {  
        File file = new File("doc.txt");    
        if(file.exists()){    
            return;  
        }    
    }
}  