package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

class Handler extends Thread{
	private Socket socket;
	public Handler(Socket socket) {
		this.socket=socket;
	}
	@Override
    public void run() {
    	try{
    		  Reader reader = new InputStreamReader(socket.getInputStream());  
		      char chars[] = new char[64];  
		      int len;  
		      StringBuilder sb = new StringBuilder();  
		      while ((len=reader.read(chars)) != -1) {  
		         sb.append(new String(chars, 0, len));  
		      }  
		      File dir=new File("C:\\Users\\Administrator\\Documents\\forserver");
	    	  String[] files=dir.list();
		      if(sb.toString().equals("index")) {
		    	  for(String value:files) {
		    		  System.out.println(value);
		    	  }
		      }
		      else {
		    	  boolean signal=false;
		    	  BufferedReader reader1 = null;  
		    	  for(String value:files) {
		    		  if((sb.toString()+".txt").equals(value)) {
		    			  signal=true;
		    			  System.out.println("get"+sb.toString());
		    			  File file = new File("C:\\Users\\Administrator\\Documents\\forserver\\"+value);  
		    			  reader1 = new BufferedReader(new FileReader(file));  
		    			  String tempString = null;
		    			  while ((tempString = reader1.readLine()) != null) {  
		    				  System.out.println(tempString);
		    	          } 
		    			  break;
		    		  }
		    	  }
		    	  if(signal==false) {
		    		  System.out.println("error");
		    	  }
		    	  reader.close();  
		          socket.close();  
		          
		      }
    	}
    	catch(IOException e) {
    		e.printStackTrace();
    	}
    }
}

public class TCPServer{  
    
	public static void main(String args[]) throws IOException {  
	      //Ϊ�˼���������е��쳣��Ϣ��������  
	      int port = 8899;  
	      //����һ��ServerSocket�����ڶ˿�8899��  
	      ServerSocket server = new ServerSocket(port);  
	      //server���Խ�������Socket����������server��accept����������ʽ��  
	      
	      //���ͻ��˽���������֮�����ǾͿ��Ի�ȡsocket��InputStream�������ж�ȡ�ͻ��˷���������Ϣ�ˡ�  
	      while(true) {
	    	  Socket socket = null;
	    	  try {
	    		  socket=server.accept();
	    		  Thread t=new Handler(socket);
	    		  t.start();
	    	  }
	    	  catch(IOException e) {
	      		e.printStackTrace();
	      	  }
	      }
	}
	
}
