### 开发背景  
TcpClient是一款基于JAVA的NIO框架Netty4.1设计的嵌入式TCP客户端，可以无缝嵌入JAVA应用程序中。此工件中并没有集成对HTTP协议的支持，这是因为对HTTP协议支持的组件非常多，如HttpClient、OkHttp、Spring框架的RestTemplate等，我们本着不重复造轮子的思想，在本工件中摒弃了对HTTP协议的支持，如果你认为有支持HTTP协议的必要，那么可以在此工件基础上来完成二次开发即可。  
​      
      

### 功能特性  
仅支持ISO四层TCP协议，不支持七层HTTP协议，所以功能基于异步事件驱动和IO多路复用机制完成客户端连接和请求响应处理过程。  
        
      
### 安装部署  
mkdir -p $MVN_HOME/repository/com/github/lixiang2114/netty/TcpClient/2.0/
wget https://github.com/lixiang2114/TcpClient/raw/main/target/TcpClient-2.0.jar -P $MVN_HOME/repository/com/github/lixiang2114/netty/TcpClient/2.0/   
wget https://github.com/lixiang2114/TcpClient/raw/main/target/TcpClient-2.0-sources.jar -P $MVN_HOME/repository/com/github/lixiang2114/netty/TcpClient/2.0/   
​      
### 工程应用  
1. 引用依赖  
```Xml
<dependency>
    <groupId>io.netty</groupId>
    <artifactId>netty-all</artifactId>
    <version>4.1.63.Final</version>
    <scope>provided</scope>
</dependency>
<dependency>
    <groupId>com.github.lixiang2114.netty</groupId>
    <artifactId>TcpClient</artifactId>
    <version>2.0</version>
</dependency>
```
2. 应用范例  
```JAVA
package com.wa.bfw.server.test;

import com.github.lixiang2114.netty.HttpServer;
import com.github.lixiang2114.netty.context.ServerConfig;
import com.wa.bfw.server.test.servlet.UserServlet;

public class TestMain {

	public static void main(String[] args) throws Exception {
		TcpClient tcpClient=new TcpClient("localhost",1567);
		tcpClient.connect();
		System.out.println(tcpClient.getMessage());
	}
}
```
#### 说明：  
1. 本工件只能连接TCP服务端，不能用于连接HTTP服务端。  
2. TcpClient的getMessage方法用于阻塞同步获取TCP服务端响应回来的数据，若需要异步获取数据则可以调动重载的getMessage(int msgNum)方法。  