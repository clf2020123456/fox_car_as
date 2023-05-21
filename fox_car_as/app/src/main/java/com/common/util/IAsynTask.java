package com.common.util;
import java.io.Serializable;

/**
 * 异步订单执行
 * 
 * @author kaifa
 * 
 */
public interface IAsynTask {

	public Serializable run();

	public void updateUI(Serializable runData);
	
}
