package com.github.lixiang2114.netty.handler;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Lixiang
 * @description TCP客户端处理器
 */
@Sharable
public class ClientHandler extends  SimpleChannelInboundHandler<String> {
	/**
	 * TCP消息处理器
	 */
	private MessageHandler messageHandler;
	
	public ClientHandler(MessageHandler messageHandler) {
		this.messageHandler=messageHandler;
	}
	
	@Override
    protected void channelRead0(ChannelHandlerContext context, String message) throws Exception {
		messageHandler.onMessage(context, message);
    }
}
