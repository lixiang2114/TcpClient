package com.github.lixiang2114.netty;

import com.github.lixiang2114.netty.boot.ClientInitializer;
import com.github.lixiang2114.netty.enums.ChannelPolicy;
import com.github.lixiang2114.netty.handler.MessageHandler;
import com.github.lixiang2114.netty.handler.SimpleMessageHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.Delimiters;

/**
 * @author Lixiang
 * @description TCP客户端
 */
public class TcpClient {
	/**
	 * 目标TCP端口
	 */
	public int port;
	
	/**
	 * 目标TCP主机(IP或主机名)
	 */
	public String host;
	
	/**
	 * TCP连接引导器
	 */
	private Bootstrap bootstrap;
	
	/**
	 * 是否已经初始化Bootstrap
	 */
	private boolean unInit=true;
	
	/**
	 * TCP事件线程组
	 */
	private EventLoopGroup group;
	
	/**
	 * TCP最大帧长度(默认32KB)
	 */
	public int maxFrameLength=32*1024;
	
	/**
	 * TCP客户端连接通道
	 */
	private NioSocketChannel clientChannel;
	
	/**
	 * TCP消息处理器
	 */
	public MessageHandler messageHandler;
	
	/**
	 * 消息通道尺寸
	 */
	public int capacity=Integer.MAX_VALUE;
	
	/**
	 * TCP协议数据行分隔符
	 */
	public ByteBuf[] lineDelimiter=Delimiters.lineDelimiter();
	
	/**
	 * 通道溢出策略
	 */
	public ChannelPolicy channelPolicy=ChannelPolicy.DISCARD;
	
	public TcpClient(String host,int port) {
		this(host,port,new SimpleMessageHandler());
	}
	
	public TcpClient(String host,int port,MessageHandler messageHandler) {
		this.messageHandler=null==messageHandler?new SimpleMessageHandler(capacity,channelPolicy):messageHandler;
		if(null!=host && !(host=host.trim()).isEmpty()) this.host=host;
		if(port>0) this.port=port;
	}
	
	private void initBootstrap() {
		this.unInit=false;
		this.group = new NioEventLoopGroup();
        this.bootstrap =new Bootstrap().group(group).channel(NioSocketChannel.class).handler(new ClientInitializer(maxFrameLength,lineDelimiter,messageHandler));
	}
	
	public String getMessage() {
		return messageHandler.getMessage();
	}
	
	public String getMessage(long timeout) {
		return messageHandler.getMessage(timeout);
	}
	
	public MessageHandler getMessageHandler() {
		return this.messageHandler;
	}
	
	/**
	 * 重连TCP服务端
	 */
	public void reconnect() {
		connect();
	}

	/**
	 * 连接TCP服务端
	 */
	public void connect() {
		if(unInit) this.initBootstrap();
        try {
            this.clientChannel=(NioSocketChannel)bootstrap.connect(host, port).sync().channel();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 销毁连接池
	 */
	public void destory() {
		this.group.shutdownGracefully();
		this.bootstrap=null;
		this.group=null;
	}
	
	/**
	 * 同步发送消息到TCP服务端
	 * @param message 消息内容
	 * @return 是否发送成功或消息发送句柄
	 * @description 若未给定timeoutMillis参数则持续阻塞直到发送完成
	 */
	public Object syncSend(String message,long... timeoutMillis) {
		try {
			if(null==timeoutMillis || 0==timeoutMillis.length) {
				return clientChannel.writeAndFlush(message+"\r\n").await();
			}
			return clientChannel.writeAndFlush(message+"\r\n").await(timeoutMillis[0]);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 异步发送消息到TCP服务端
	 * @param message 消息内容
	 * @return 消息发送句柄
	 */
	public ChannelFuture asyncSend(String message) {
		return clientChannel.writeAndFlush(message+"\r\n");
	}
	
	/**
	 * 获取主机字符串
	 * @return 主机字串
	 */
	public String getHostString() {
		return host+":"+port;
	}
	
	public String toString() {
		return getHostString();
	}
}
