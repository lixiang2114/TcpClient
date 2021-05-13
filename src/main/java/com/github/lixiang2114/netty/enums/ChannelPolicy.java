package com.github.lixiang2114.netty.enums;

/**
 * @author Lixiang
 * @description 通道溢出策略
 */
public enum ChannelPolicy {
	/**
	 * 丢弃当前元素
	 */
	DISCARD("DISCARD"),
	
	/**
	 * 丢弃最早元素
	 */
	REMOVED("REMOVED");
	
	public String policyName;
	
	private ChannelPolicy(String policyName) {
		this.policyName=policyName;
	}
}
