package test;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

class Server extends Thread{
	
	private DatagramSocket send;
	private DatagramPacket pkg;
	private DatagramPacket messagepkg;
	private int serverPort;
	private int clientPort;
	private String path;
	private File file;
	private String ip;


	public Server(int serverPort, int clientPort, String path, String ip) {
		super();
		this.serverPort = serverPort;
		this.clientPort = clientPort;
		this.path = path;
		this.ip = ip;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void send() {
		try {
			//文件发送者设置监听端口
			send = new DatagramSocket(serverPort);
			
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(new File(path)));
			//确认信息包
			byte[] messagebuf = new byte[1024];
			messagepkg = new DatagramPacket(messagebuf, messagebuf.length);
			//文件包
			byte[] buf = new byte[1024 * 63];
			int len= bis.read(buf);
			while (len != -1) {
				
				try {
					pkg = new DatagramPacket(buf, len, new InetSocketAddress(
							ip, clientPort));
					//设置确认信息接收时间，3秒后未收到对方确认信息，则重新发送一次
					send.setSoTimeout(3000);
					send.send(pkg);
					send.receive(messagepkg);
					System.out.println(new String(messagepkg.getData()));
				}
				catch(SocketTimeoutException e) {
					System.out.println("resend");
					continue;
				}
				len= bis.read(buf);
			}
			// 文件传完后，发送一个结束包
			buf = "end".getBytes();
			DatagramPacket endpkg = new DatagramPacket(buf, buf.length,
					new InetSocketAddress(ip, serverPort));
			System.out.println("文件发送完毕");
			send.send(endpkg);
			bis.close();
			send.close();

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		send();
	}
	
}

public class UDPServer{
	public static void main(String args[]) throws IOException {  
	      //为了简单起见，所有的异常信息都往外抛  
	      int port = 9988;  
	      //定义一个ServerSocket监听在端口9988上  
	      DatagramSocket dsocket=new DatagramSocket(port);
	      ServerSocket server = new ServerSocket(port);  
	      while(true) {
	    	  Socket socket = null;
	    	  try {
	    		  socket=server.accept();
	    		  Reader reader = new InputStreamReader(socket.getInputStream());  
			      char chars[] = new char[64];  
			      int len;  
			      StringBuilder sb = new StringBuilder();  
			      while ((len=reader.read(chars)) != -1) {  
			    	  sb.append(new String(chars, 0, len));  	        
				  }
	    		  Thread t=new Server(13000,12000,"C:\\Users\\Administrator\\Documents\\forserver\\"+sb.toString()+".txt","127.0.0.1");
	    		  t.start();//监听从12000发过来的信息，以13000(server)发送
	    	  }
	    	  catch(IOException e) {
	      		e.printStackTrace();
	      	  }
	      }
	}
}
