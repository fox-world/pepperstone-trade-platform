package com.elance.vo;

public class ButtonStatusVO {

	private boolean closeOrder;

	private boolean closeHedge;
	
	public boolean isCloseOrder() {
		return closeOrder;
	}
	public void setCloseOrder(boolean closeOrder) {
		this.closeOrder = closeOrder;
	}	
	public boolean isCloseHedge() {
		return closeHedge;
	}
	public void setCloseHedge(boolean closeHedge) {
		this.closeHedge = closeHedge;
	}
	
	public boolean needStopUpdate(){
		return closeOrder||closeHedge;
	}

	
}
