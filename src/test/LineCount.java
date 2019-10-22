package test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class LineCount extends Thread{
	private String Filename;
	public LineCount(String Filename) {
		this.Filename=Filename;
	}
	
	
	@Override
	public void run() {
		File file = new File(Filename);  
        BufferedReader reader = null;  
        try {  
            
            reader = new BufferedReader(new FileReader(file));  
            String tempString = null;  
            int line = 0;  
            // 一次读入一行，直到读入null为文件结束  
            while ((tempString = reader.readLine()) != null) {  
                line++;  
            }  
            reader.close();  
            System.out.println(Filename+":"+line);
            
        } 
        catch (IOException e) {  
        	System.out.println("we got sth wrong");
            e.printStackTrace();  
        } 
	}
	
	public static void main(String[] args) {
		for (String filename:args) {
			Thread t=new LineCount(filename);
			t.start();
		}
	}
	
}