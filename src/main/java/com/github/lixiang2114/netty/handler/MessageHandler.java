package com.github.lixiang2114.netty.handler;

import java.util.ArrayList;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author Lixiang
 * @description 消息处理器接口
 */
public interface MessageHandler {
	/**
	 * 阻塞返回消息
	 * @return 消息
	 */
	default public String getMessage() {
		return null;
	}
	
	/**
	 * 阻塞给定的最大超时时间返回消息
	 * @param timeout 超时时间
	 * @return 消息
	 */
	default public String getMessage(long timeout) {
		return null;
	}
	
	/**
	 * 快速无阻塞返回队列中所有元素
	 * @param timeout 超时时间
	 * @return 消息
	 */
	default public ArrayList<String> getAllMessage() {
		return null;
	}
	
	/**
	 * 快速无阻塞返回队列中给定数量的元素
	 * @param timeout 超时时间
	 * @return 消息
	 */
	default public ArrayList<String> getMessages(int msgNum) {
		return null;
	}
	
	/**
	 * 设置消息数据
	 * @param context 通道上下文
	 * @param message 消息内容
	 */
	default public void setMessage(Channel channel, String message) {
		System.out.println(message);
	}
	
	/**
	 * 触发消息事件
	 * @param context 通道上下文
	 * @param message 消息内容
	 */
	default public void onMessage(ChannelHandlerContext context, String message) {
		this.setMessage(context.channel(),message);
	}
}
