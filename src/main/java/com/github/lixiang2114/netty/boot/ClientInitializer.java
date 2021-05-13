package com.github.lixiang2114.netty.boot;

import com.github.lixiang2114.netty.handler.ClientHandler;
import com.github.lixiang2114.netty.handler.MessageHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author Lixiang
 * @description 客户端初始化器
 */
@Sharable
public class ClientInitializer extends ChannelInitializer<SocketChannel> {
	/**
	 * 最大帧长度(默认32KB)
	 */
	private int maxFrameLength;
	
	/**
	 * TCP协议下数据行分隔符(默认\n)
	 */
	private ByteBuf[] lineDelimiter;
	
	/**
	 * 字符串编码器
	 */
	private StringEncoder stringEncoder;
	
	/**
	 * 字符串解码器
	 */
	private StringDecoder stringDecoder;
	
	/**
	 * TCP客户端处理器
	 */
	private ClientHandler clientHandler;
	
	public ClientInitializer() {
		this(32*1024,Delimiters.lineDelimiter(),new MessageHandler(){});
	}
	
	public ClientInitializer(int maxFrameLength,ByteBuf[] lineDelimiter,MessageHandler messageHandler) {
		this.stringDecoder=new StringDecoder();
		this.stringEncoder=new StringEncoder();
		this.maxFrameLength=maxFrameLength;
		this.lineDelimiter=lineDelimiter;
		try {
			clientHandler=new ClientHandler(messageHandler);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
    public void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(maxFrameLength, lineDelimiter));
        pipeline.addLast("decoder", stringDecoder);
        pipeline.addLast("encoder", stringEncoder);
        pipeline.addLast("handler", clientHandler);
    }
}
