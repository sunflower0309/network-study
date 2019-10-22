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
	      //为了简单起见，所有的异常信息都往外抛  
	      int port = 8899;  
	      //定义一个ServerSocket监听在端口8899上  
	      ServerSocket server = new ServerSocket(port);  
	      //server尝试接收其他Socket的连接请求，server的accept方法是阻塞式的  
	      
	      //跟客户端建立好连接之后，我们就可以获取socket的InputStream，并从中读取客户端发过来的信息了。  
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
