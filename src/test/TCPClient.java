package test;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

public class TCPClient {
	public static void main(String args[]) throws Exception {  
	      //Ϊ�˼���������е��쳣��ֱ��������  
	      String host = "127.0.0.1";  //Ҫ���ӵķ����IP��ַ  
	      int port = 8899;   //Ҫ���ӵķ���˶�Ӧ�ļ����˿�  
	      //�����˽�������  
	      Socket client = new Socket(host, port);  
	      //�������Ӻ�Ϳ����������д������  
	      Writer writer = new OutputStreamWriter(client.getOutputStream());  
	      writer.write("txt2");  
	      writer.flush();//д���Ҫ�ǵ�flush  
	      writer.close();  
	      client.close();  
	   }  

}
