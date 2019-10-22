package test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

class Clientu extends Thread {
	private DatagramSocket receive;
	private String ip;
	private int serverPort;
	private int clientPort;
	private File file;
	private String path;
	private DatagramPacket pkg;
	private DatagramPacket messagepkg;


	public Clientu(String ip, int serverPort, int clientPort, String path) {
		super();
		this.ip = ip;
		this.serverPort = serverPort;
		this.clientPort = clientPort;
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public String getip() {
		return ip;
	}

	public void setPath(String path) {
		this.ip = path;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void receive() {
		try {
			// 接收文件监听端口
			receive = new DatagramSocket(clientPort);
			// 写文件路径
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(new File(path)));

			// 读取文件包
			byte[] buf = new byte[1024 * 63];
			pkg = new DatagramPacket(buf, buf.length);
			// 发送收到文件后 确认信息包
			byte[] messagebuf = new byte[1024];
			messagebuf = "ok".getBytes();
			messagepkg = new DatagramPacket(messagebuf, messagebuf.length,
					new InetSocketAddress(ip, serverPort));
			// 循环接收包，每接到一个包后回给对方一个确认信息，对方才发下一个包(避免丢包和乱序)，直到收到一个结束包后跳出循环，结束文件传输，关闭流
			while (true) {
				receive.receive(pkg);
				if (new String(pkg.getData(), 0, pkg.getLength()).equals("end")) {
					System.out.println("文件接收完毕");
					bos.close();
					receive.close();
					break;
				}
				receive.send(messagepkg);
				System.out.println(new String(messagepkg.getData()));
				bos.write(pkg.getData(), 0, pkg.getLength());
				bos.flush();
			}
			bos.close();
			receive.close();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		receive();
	}
}
public class UDPClient{
	public static void main(String args[]) throws Exception {  
	      //为了简单起见，所有的异常都直接往外抛  
	      String host = "127.0.0.1";  //要连接的服务端IP地址  
	      int port = 9988;   //要连接的服务端对应的监听端口  
	      //与服务端建立连接  
	      Socket client = new Socket(host, port);  
	      //建立连接后就可以往服务端写数据了  
	      Writer writer = new OutputStreamWriter(client.getOutputStream());  
	      String name="pride-and-prejudice";
	      writer.write(name);  
	      writer.flush();
	      writer.close();  
	      client.close();
	      try {
	    	  Thread t=new Clientu("127.0.0.1",13000,12000,"C:\\Users\\Administrator\\Documents\\forclient\\"+name+".txt");
	    	  t.start();
	      }
	      catch(Exception e) {
	      		e.printStackTrace();
	      }
	}  
}
