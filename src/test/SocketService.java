package test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
public class SocketService {
	public static void main(String[] args) {
		try {
			SocketAddress address = new InetSocketAddress("127.0.0.1", 30001);
			// 启动监听端口 30001
			ServerSocket ss = new ServerSocket();
			ss.bind(address);
			// 接收请求
			Socket s = ss.accept();
			new Thread(new T(s)).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
class T implements Runnable {
	public void run() {
		try {
			System.out.println(socket.toString());
			socket.setKeepAlive(true);
			socket.setSoTimeout(3 * 1000);
			String _pattern = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat format = new SimpleDateFormat(_pattern);
			int count=1;
			int cnt;
			while (true) {
				System.out.println("开始：" + format.format(new Date())+count);
				try {
					InputStream ips = socket.getInputStream();
					ByteArrayOutputStream bops = new ByteArrayOutputStream();
					int data = -1;
					while((data = ips.read()) != -1){
						System.out.println(data);
						bops.write(data);
					}
					System.out.println(Arrays.toString(bops.toByteArray()));
				}catch(SocketTimeoutException e){
					e.printStackTrace();
					continue;
				}catch(SocketException e){
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				Thread.sleep(1000);
				System.out.println(socket.isBound()); // 是否邦定
				System.out.println(socket.isClosed()); // 是否关闭
				System.out.println(socket.isConnected()); // 是否连接
				System.out.println(socket.isInputShutdown()); // 是否关闭输入流
				System.out.println(socket.isOutputShutdown()); // 是否关闭输出流
				System.out.println("结束：" + format.format(new Date()));
				count+=1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private Socket socket = null;
	public T(Socket socket) {
		this.socket = socket;
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
}