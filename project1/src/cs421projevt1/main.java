package cs421projevt1;

import java.io.BufferedReader;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.util.ArrayList;  
import java.util.Iterator;  
import java.util.List;  
  
import cs421projevt1.fileHelp;  
  
  
public class main {  
      
    public static void main(String[] args) {  
        int size = 1;  
        Start []start = new Start[size];  
        for(int i=0; i < size; i++)  
        {  
            start[i] = new Start();  
            start[i].startGame();  
            System.out.println("--------------------------");  
        }  
    }             
}  