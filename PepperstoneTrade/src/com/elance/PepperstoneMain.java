package com.elance;

import javax.swing.JFrame;

import com.elance.component.LoginPanel;

public class PepperstoneMain {

	private JFrame frame;
	private LoginPanel loginPanel;
	
	public static void main(String[] args) {
		PepperstoneMain ppstone=new PepperstoneMain();
		ppstone.startApp();
	}
	
	public void startApp(){
		frame = new JFrame("Pepperstone Multiple Platform Trade Software(Live Account)");
		frame.setSize(820,675);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		loginPanel=new LoginPanel(frame);
		loginPanel.showLoginPanel(true);
		frame.add(loginPanel);
		frame.setLocation(300,150);

		frame.setVisible(true);
	}
}
