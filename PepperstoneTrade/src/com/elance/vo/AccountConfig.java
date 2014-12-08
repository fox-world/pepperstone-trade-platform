package com.elance.vo;

import javax.swing.ButtonGroup;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AccountConfig {

	public final ExecutorService executorService;

	public AccountConfig() {
		executorService = Executors.newCachedThreadPool();
	}
    
	private JSpinner serverNumberSpinner;
	private JSpinner maxLotsSpinner;
	private JSpinner maxTradesSpinner;
	private JTextField lotSizeText;
	private JTextField hedgePipsText;
	private ButtonGroup hedgeProButtonGroup;

	public JSpinner getServerNumberSpinner() {
		return serverNumberSpinner;
	}

	public void setServerNumberSpinner(JSpinner serverNumberSpinner) {
		this.serverNumberSpinner = serverNumberSpinner;
	}

	public JSpinner getMaxLotsSpinner() {
		return maxLotsSpinner;
	}

	public void setMaxLotsSpinner(JSpinner maxLotsSpinner) {
		this.maxLotsSpinner = maxLotsSpinner;
	}

	public JSpinner getMaxTradesSpinner() {
		return maxTradesSpinner;
	}

	public void setMaxTradesSpinner(JSpinner maxTradesSpinner) {
		this.maxTradesSpinner = maxTradesSpinner;
	}

	public JTextField getLotSizeText() {
		return lotSizeText;
	}

	public void setLotSizeText(JTextField lotSizeText) {
		this.lotSizeText = lotSizeText;
	}

	public JTextField getHedgePipsText() {
		return hedgePipsText;
	}

	public void setHedgePipsText(JTextField hedgePipsText) {
		this.hedgePipsText = hedgePipsText;
	}

	public ButtonGroup getHedgeProButtonGroup() {
		return hedgeProButtonGroup;
	}

	public void setHedgeProButtonGroup(ButtonGroup hedgeProButtonGroup) {
		this.hedgeProButtonGroup = hedgeProButtonGroup;
	}

}
