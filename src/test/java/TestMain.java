import com.github.lixiang2114.netty.TcpClient;

public class TestMain {

	public static void main(String[] args) {
		TcpClient tcpClient=new TcpClient("localhost",8080);
		tcpClient.connect();
		
		String respMsg=tcpClient.getMessage();
		System.out.println("client recv:"+respMsg);
	}
}
