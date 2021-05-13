package com.github.lixiang2114.netty.handler;

import java.util.ArrayList;

import com.github.lixiang2114.netty.channel.MesageChannel;
import com.github.lixiang2114.netty.enums.ChannelPolicy;

import io.netty.channel.Channel;

/**
 * @author Lixiang
 * @description 简单消息处理器
 */
public class SimpleMessageHandler implements MessageHandler{
	/**
	 * 通道溢出策略
	 */
	private ChannelPolicy channelPolicy;
	
	/**
	 * TCP协议消息通道
	 */
	private MesageChannel<String> messageChannel;
	
	public SimpleMessageHandler() {
		this(ChannelPolicy.DISCARD);
	}
	
	public SimpleMessageHandler(ChannelPolicy channelPolicy) {
		this(Integer.MAX_VALUE,channelPolicy);
	}
	
	public SimpleMessageHandler(int capacity,ChannelPolicy channelPolicy) {
		this.channelPolicy=channelPolicy;
		this.messageChannel=new MesageChannel<String>(capacity);
	}

	@Override
	public void setMessage(Channel channel, String message) {
		try {
			if(messageChannel.remainingCapacity()>0) {
				messageChannel.put(message);
				return;
			}
			if(ChannelPolicy.DISCARD==channelPolicy) return;
			messageChannel.get();
			messageChannel.put(message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getMessage() {
		return messageChannel.get();
	}
	
	@Override
	public String getMessage(long timeout) {
		return messageChannel.get(timeout);
	}
	
	@Override
	public ArrayList<String> getAllMessage() {
		return messageChannel.batchGet();
	}

	@Override
	public ArrayList<String> getMessages(int msgNum) {
		return messageChannel.batchGet(msgNum);
	}
}
