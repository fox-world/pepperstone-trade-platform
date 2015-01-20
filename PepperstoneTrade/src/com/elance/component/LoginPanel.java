package com.elance.component;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import com.elance.listener.LoginListener;
import com.elance.nj4x.MT4ConnectionUtil;
import com.elance.util.constants.ComponentConstants;
import com.elance.vo.AccountConfig;
import com.elance.vo.AccountVO;

public class LoginPanel extends JPanel{
	
	private static final long serialVersionUID = 7217286573720791190L;
		
	private static final int ACCOUNT_LABEL_X=10;
	private static final int ACCOUNT_TEXT_X=80; 
	private static final int PASS_LABEL_X=260;
	private static final int PASS_TEXT_X=340;
	
	private static final int COMPONENT_Y_1=20;
	private static final int COMPONENT_Y_2=50;
	private static final int COMPONENT_Y_3=80;
	private static final int COMPONENT_Y_4=110;
	private static final int COMPONENT_Y_5=140;
	private static final int COMPONENT_Y_6=170;
	private static final int COMPONENT_Y_7=200;
	private static final int COMPONENT_Y_8=230;
	private static final int COMPONENT_Y_9=260;
	private static final int COMPONENT_Y_10=290;
		
	private JFrame frame;
	
	private JPanel accountPanel;
	private JPanel configPanel;
	private JPanel buttonPanel;
	
	private JTextField accountText1;
	private JTextField passwordText1;
	
	private JTextField accountText2;
	private JTextField passwordText2;
	
	private JTextField accountText3;
	private JTextField passwordText3;
	
	private JTextField accountText4;
	private JTextField passwordText4;
	
	private JTextField accountText5;
	private JTextField passwordText5;
	
	private JTextField accountText6;
	private JTextField passwordText6;
	
	private JTextField accountText7;
	private JTextField passwordText7;
	
	private JTextField accountText8;
	private JTextField passwordText8;
	
	private JTextField accountText9;
	private JTextField passwordText9;
	
	private JTextField accountText10;
	private JTextField passwordText10;
	
	//===============config component=================
	private JSpinner serverNumberSpinner;
	private JTextField serverIPText;
	private JTextField serverNameText;
	private JSpinner maxLotsSpinner;
	private JSpinner maxTradesSpinner;
	private JTextField lotSizeText;
	private JTextField hedgePipsText;
	private ButtonGroup hedgeProButtonGroup;

	private JLabel processingLabel;
	private JButton loginButton;
	private JButton resetButton;
	
	private List<AccountVO> accountList;
	
	private AccountConfig accountConfig;
	
	public LoginPanel(){
	}
	
	public LoginPanel(JFrame frame){
		
		this.frame=frame;
		
		accountPanel=new JPanel();
		configPanel=new JPanel();
		buttonPanel=new JPanel();

		accountList=new ArrayList<AccountVO>(10);
		accountConfig=new AccountConfig();
	}
	
	public LoginPanel(JFrame frame,List<AccountVO> accountList){
		this.frame=frame;
		
		accountPanel=new JPanel();
		configPanel=new JPanel();
		buttonPanel=new JPanel();

		accountList=this.accountList;
		accountConfig=new AccountConfig();
	}
	
	public void showLoginPanel(boolean loginAll) {

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.ipadx=800;
		c.ipady=335;
		add(accountPanel,c);
		c.gridx=0;
		c.gridy=1;
		c.ipady=270;
		add(configPanel,c);
		c.gridx=0;
		c.gridy=2;
		c.ipady=50;
		add(buttonPanel,c);
		
		accountPanel.setLayout(null);
		accountPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Input account and password"));
		
		configPanel.setLayout(null);
		configPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Input config information"));
		
		buttonPanel.setLayout(null);

		addAccountComponents();		
		
		addConfigComponents();
	
		//=====================Account cofing information=================
	    processingLabel=new JLabel("ogin processing");
	    //processingLabel.setBounds(100, 10, 150, 25);
	    processingLabel.setBounds(2, 10, 250, 25);
	    processingLabel.setVisible(false);
	    buttonPanel.add(processingLabel);
	    if(loginAll){
	    	initAccountList();
	    }else{
	    	resetAccountList(accountList);
	    }
		loginButton = new JButton("Login");
		loginButton.setBounds(310,10, 80, ComponentConstants.BUTTON_HEIGHT);
		buttonPanel.add(loginButton);
	
		loginButton.addActionListener(new LoginListener(frame,this,processingLabel,accountList,accountConfig));
		
		accountConfig.setServerNameText(serverNameText);
		accountConfig.setServerNumberSpinner(serverNumberSpinner);
		accountConfig.setMaxLotsSpinner(maxLotsSpinner);
		accountConfig.setMaxTradesSpinner(maxTradesSpinner);
		accountConfig.setLotSizeText(lotSizeText);
		accountConfig.setHedgePipsText(hedgePipsText);
		accountConfig.setHedgeProButtonGroup(hedgeProButtonGroup);
		
		resetButton = new JButton("Reset");
		resetButton.setBounds(400, 10, 80, ComponentConstants.BUTTON_HEIGHT);
		resetButton.setEnabled(false);
	    buttonPanel.add(resetButton);
	    resetButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				processingLabel.setVisible(false);
				loginButton.setEnabled(true);
				resetButton.setEnabled(false);
				changeComponentsEnableStatus(true);
			}
	    	
	    });
	
	}
	
	public void initAccountList(){
		accountList.clear();
		accountList.add(new AccountVO(accountText1,passwordText1,new MT4ConnectionUtil()));
		accountList.add(new AccountVO(accountText2,passwordText2,new MT4ConnectionUtil()));
		accountList.add(new AccountVO(accountText3,passwordText3,new MT4ConnectionUtil()));
		accountList.add(new AccountVO(accountText4,passwordText4,new MT4ConnectionUtil()));
		accountList.add(new AccountVO(accountText5,passwordText5,new MT4ConnectionUtil()));
		accountList.add(new AccountVO(accountText6,passwordText6,new MT4ConnectionUtil()));
		accountList.add(new AccountVO(accountText7,passwordText7,new MT4ConnectionUtil()));
		accountList.add(new AccountVO(accountText8,passwordText8,new MT4ConnectionUtil()));
		accountList.add(new AccountVO(accountText9,passwordText9,new MT4ConnectionUtil()));
		accountList.add(new AccountVO(accountText10,passwordText10,new MT4ConnectionUtil()));
	}
	
	public void resetAccountList(List<AccountVO> accountList){
		accountText1.setText(accountList.get(0).getAccountText().getText());
		passwordText1.setText(accountList.get(0).getPasswordText().getText());
		if(accountList.get(0).isLoginSuccess()){
			accountText1.setEnabled(false);
		}
		
		accountText2.setText(accountList.get(1).getAccountText().getText());
		passwordText2.setText(accountList.get(1).getPasswordText().getText());
		if(accountList.get(1).isLoginSuccess()){
			accountText2.setEnabled(false);
		}
		
		accountText3.setText(accountList.get(2).getAccountText().getText());
		passwordText3.setText(accountList.get(2).getPasswordText().getText());
		if(accountList.get(2).isLoginSuccess()){
			accountText3.setEnabled(false);
		}
		
		accountText4.setText(accountList.get(3).getAccountText().getText());
		passwordText4.setText(accountList.get(3).getPasswordText().getText());
		if(accountList.get(3).isLoginSuccess()){
			accountText4.setEnabled(false);
		}
		
		accountText5.setText(accountList.get(4).getAccountText().getText());
		passwordText5.setText(accountList.get(4).getPasswordText().getText());
		if(accountList.get(4).isLoginSuccess()){
			accountText5.setEnabled(false);
		}
		
		accountText6.setText(accountList.get(5).getAccountText().getText());
		passwordText6.setText(accountList.get(5).getPasswordText().getText());
		if(accountList.get(5).isLoginSuccess()){
			accountText6.setEnabled(false);
		}
		
		accountText7.setText(accountList.get(6).getAccountText().getText());
		passwordText7.setText(accountList.get(6).getPasswordText().getText());
		if(accountList.get(6).isLoginSuccess()){
			accountText7.setEnabled(false);
		}
		
		accountText8.setText(accountList.get(7).getAccountText().getText());
		passwordText8.setText(accountList.get(7).getPasswordText().getText());
		if(accountList.get(7).isLoginSuccess()){
			accountText8.setEnabled(false);
		}
		
		accountText9.setText(accountList.get(8).getAccountText().getText());
		passwordText9.setText(accountList.get(8).getPasswordText().getText());
		if(accountList.get(8).isLoginSuccess()){
			accountText9.setEnabled(false);
		}
		
		accountText10.setText(accountList.get(9).getAccountText().getText());
		passwordText10.setText(accountList.get(9).getPasswordText().getText());
		if(accountList.get(9).isLoginSuccess()){
			accountText10.setEnabled(false);
		}
	}

	public void addAccountComponents(){
		//=====================Account 1=================
				JLabel accountLabel1 = new JLabel("Account1:");	
				accountLabel1.setBounds(ACCOUNT_LABEL_X, COMPONENT_Y_1,ComponentConstants.COMPONENT_LABEL_WIDTH_SMALL,ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(accountLabel1);

				accountText1 = new JTextField();
				//accountText1.setEnabled(false);
				accountText1.setBounds(ACCOUNT_TEXT_X, COMPONENT_Y_1,ComponentConstants.COMPONENT_TEXT_WIDTH,ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(accountText1);

				JLabel passwordLabel = new JLabel("Password1:");
				passwordLabel.setBounds(PASS_LABEL_X, COMPONENT_Y_1, ComponentConstants.COMPONENT_LABEL_WIDTH_SMALL, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(passwordLabel);

				passwordText1 = new JPasswordField();
				passwordText1.setBounds(PASS_TEXT_X, COMPONENT_Y_1,ComponentConstants.COMPONENT_TEXT_WIDTH, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(passwordText1);
				
				//=====================Account 2=================
				JLabel accountLabel2=new JLabel("Account2:");
				accountLabel2.setBounds(ACCOUNT_LABEL_X, COMPONENT_Y_2,ComponentConstants.COMPONENT_LABEL_WIDTH_SMALL, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(accountLabel2);
				
				accountText2=new JTextField();
				accountText2.setBounds(ACCOUNT_TEXT_X, COMPONENT_Y_2, ComponentConstants.COMPONENT_TEXT_WIDTH, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(accountText2);
				
				JLabel passwordLabel2=new JLabel("Password2:");
				passwordLabel2.setBounds(PASS_LABEL_X, COMPONENT_Y_2,ComponentConstants.COMPONENT_LABEL_WIDTH_SMALL, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(passwordLabel2);
				
				passwordText2=new JPasswordField();
				passwordText2.setBounds(PASS_TEXT_X, COMPONENT_Y_2, ComponentConstants.COMPONENT_TEXT_WIDTH,ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(passwordText2);
				
				//=====================Account 3=================
				JLabel accountLabel3=new JLabel("Account3:");
				accountLabel3.setBounds(ACCOUNT_LABEL_X, COMPONENT_Y_3,ComponentConstants.COMPONENT_LABEL_WIDTH_SMALL,ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(accountLabel3);
				
				accountText3=new JTextField();
				accountText3.setBounds(ACCOUNT_TEXT_X, COMPONENT_Y_3, ComponentConstants.COMPONENT_TEXT_WIDTH, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(accountText3);
				
				JLabel passwordLabel3=new JLabel("Password3:");
				passwordLabel3.setBounds(PASS_LABEL_X,COMPONENT_Y_3,ComponentConstants.COMPONENT_LABEL_WIDTH_SMALL,ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(passwordLabel3);
				
				passwordText3=new JPasswordField();
				passwordText3.setBounds(PASS_TEXT_X,COMPONENT_Y_3, ComponentConstants.COMPONENT_TEXT_WIDTH, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(passwordText3);
				
				//=====================Account 4=================
				JLabel accountLabel4=new JLabel("Account4:");
				accountLabel4.setBounds(ACCOUNT_LABEL_X, COMPONENT_Y_4,ComponentConstants.COMPONENT_LABEL_WIDTH_SMALL, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(accountLabel4);
				
				accountText4=new JTextField();
				accountText4.setBounds(ACCOUNT_TEXT_X, COMPONENT_Y_4, ComponentConstants.COMPONENT_TEXT_WIDTH, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(accountText4);
				
				JLabel passwordLabel4=new JLabel("Password4:");
				passwordLabel4.setBounds(PASS_LABEL_X,COMPONENT_Y_4,ComponentConstants.COMPONENT_LABEL_WIDTH_SMALL, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(passwordLabel4);
				
				passwordText4=new JPasswordField();
				passwordText4.setBounds(PASS_TEXT_X, COMPONENT_Y_4, ComponentConstants.COMPONENT_TEXT_WIDTH,ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(passwordText4);
				
				//=====================Account 5=================
				JLabel accountLabel5=new JLabel("Account5:");
				accountLabel5.setBounds(ACCOUNT_LABEL_X, COMPONENT_Y_5,ComponentConstants.COMPONENT_LABEL_WIDTH_SMALL, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(accountLabel5);
				
				accountText5=new JTextField();
				accountText5.setBounds(ACCOUNT_TEXT_X, COMPONENT_Y_5, ComponentConstants.COMPONENT_TEXT_WIDTH, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(accountText5);
				
				JLabel passwordLabel5=new JLabel("Password5:");
				passwordLabel5.setBounds(PASS_LABEL_X, COMPONENT_Y_5,ComponentConstants.COMPONENT_LABEL_WIDTH_SMALL, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(passwordLabel5);
				
				passwordText5=new JPasswordField();
				passwordText5.setBounds(PASS_TEXT_X,COMPONENT_Y_5, ComponentConstants.COMPONENT_TEXT_WIDTH, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(passwordText5);
				
				//=====================Account 6=================
				JLabel accountLabel6=new JLabel("Account6:");
				accountLabel6.setBounds(ACCOUNT_LABEL_X, COMPONENT_Y_6,ComponentConstants.COMPONENT_LABEL_WIDTH_SMALL, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(accountLabel6);
				
				accountText6=new JTextField();
				accountText6.setBounds(ACCOUNT_TEXT_X, COMPONENT_Y_6, ComponentConstants.COMPONENT_TEXT_WIDTH, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(accountText6);
				
				JLabel passwordLabel6=new JLabel("Password6:");
				passwordLabel6.setBounds(PASS_LABEL_X, COMPONENT_Y_6,ComponentConstants.COMPONENT_LABEL_WIDTH_SMALL, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(passwordLabel6);
				
				passwordText6=new JPasswordField();
				passwordText6.setBounds(PASS_TEXT_X, COMPONENT_Y_6, ComponentConstants.COMPONENT_TEXT_WIDTH, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(passwordText6);
				
				//=====================Account 7=================
				JLabel accountLabel7=new JLabel("Account7:");
				accountLabel7.setBounds(ACCOUNT_LABEL_X, COMPONENT_Y_7,ComponentConstants.COMPONENT_LABEL_WIDTH_SMALL, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(accountLabel7);
				
				accountText7=new JTextField();
				accountText7.setBounds(ACCOUNT_TEXT_X, COMPONENT_Y_7, ComponentConstants.COMPONENT_TEXT_WIDTH, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(accountText7);
				
				JLabel passwordLabel7=new JLabel("Password7:");
				passwordLabel7.setBounds(PASS_LABEL_X, COMPONENT_Y_7,ComponentConstants.COMPONENT_LABEL_WIDTH_SMALL, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(passwordLabel7);
				
				passwordText7=new JPasswordField();
				passwordText7.setBounds(PASS_TEXT_X, COMPONENT_Y_7, ComponentConstants.COMPONENT_TEXT_WIDTH, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(passwordText7);
				
				//=====================Account 8=================
				JLabel accountLabel8=new JLabel("Account8:");
				accountLabel8.setBounds(ACCOUNT_LABEL_X, COMPONENT_Y_8,ComponentConstants.COMPONENT_LABEL_WIDTH_SMALL, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(accountLabel8);
				
				accountText8=new JTextField();
				accountText8.setBounds(ACCOUNT_TEXT_X, COMPONENT_Y_8, ComponentConstants.COMPONENT_TEXT_WIDTH, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(accountText8);
				
				JLabel passwordLabel8=new JLabel("Password8:");
				passwordLabel8.setBounds(PASS_LABEL_X, COMPONENT_Y_8,ComponentConstants.COMPONENT_LABEL_WIDTH_SMALL, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(passwordLabel8);
				
				passwordText8=new JPasswordField();
				passwordText8.setBounds(PASS_TEXT_X,COMPONENT_Y_8, ComponentConstants.COMPONENT_TEXT_WIDTH, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(passwordText8);
				
				//=====================Account 9=================
				JLabel accountLabel9=new JLabel("Account9:");
				accountLabel9.setBounds(ACCOUNT_LABEL_X, COMPONENT_Y_9,ComponentConstants.COMPONENT_LABEL_WIDTH_SMALL, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(accountLabel9);
				
				accountText9=new JTextField();
				accountText9.setBounds(ACCOUNT_TEXT_X, COMPONENT_Y_9, ComponentConstants.COMPONENT_TEXT_WIDTH, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(accountText9);
				
				JLabel passwordLabel9=new JLabel("Password9:");
				passwordLabel9.setBounds(PASS_LABEL_X,COMPONENT_Y_9,ComponentConstants.COMPONENT_LABEL_WIDTH_SMALL, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(passwordLabel9);
				
				passwordText9=new JPasswordField();
				passwordText9.setBounds(PASS_TEXT_X, COMPONENT_Y_9, ComponentConstants.COMPONENT_TEXT_WIDTH, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(passwordText9);
				
				//=====================Account 10=================
				JLabel accountLabel10=new JLabel("Account10:");
				accountLabel10.setBounds(ACCOUNT_LABEL_X, COMPONENT_Y_10,ComponentConstants.COMPONENT_LABEL_WIDTH_SMALL, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(accountLabel10);
				
				accountText10=new JTextField();
				accountText10.setBounds(ACCOUNT_TEXT_X, COMPONENT_Y_10, ComponentConstants.COMPONENT_TEXT_WIDTH, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(accountText10);
				
				JLabel passwordLabel10=new JLabel("Password10:");
				passwordLabel10.setBounds(PASS_LABEL_X, COMPONENT_Y_10,ComponentConstants.COMPONENT_LABEL_WIDTH_SMALL, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(passwordLabel10);
				
				passwordText10=new JPasswordField();
				passwordText10.setBounds(PASS_TEXT_X, COMPONENT_Y_10, ComponentConstants.COMPONENT_TEXT_WIDTH, ComponentConstants.COMPONENT_HEIGHT);
				accountPanel.add(passwordText10);
				
/*				accountText1.setText("94955");
				accountText2.setText("94956");
				accountText3.setText("94957");
				accountText4.setText("94958");
				passwordText1.setText("3crcqkr");
				passwordText2.setText("1nsgraw");
				passwordText3.setText("gw4nwoz");
				passwordText4.setText("gll3jon");
				
//				accountText1.setText("97253");
//				passwordText1.setText("rztw5ru");
				accountText5.setText("101302");
				accountText6.setText("101303");
				accountText7.setText("101305");
				accountText8.setText("101306");
				accountText9.setText("101307");
				accountText10.setText("101308");
				
				passwordText5.setText("pkfd1kp");
				passwordText6.setText("dpnl3ps");
				passwordText7.setText("ddkm3po");
				passwordText8.setText("ddcp2bf");
				passwordText9.setText("ep1ksap");
				passwordText10.setText("l7fwbqv");*/
	}
	
	public void addConfigComponents(){
		//=====================Account cofing information=================
				JLabel serverNumberLabel=new JLabel("Server number:");
				serverNumberLabel.setBounds(10, 20, 120, ComponentConstants.COMPONENT_HEIGHT);
				//spinnerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
				configPanel.add(serverNumberLabel);
				
				SpinnerNumberModel serverNumberSpinnerNumberModel=new SpinnerNumberModel(5, 1, 10,1);
				serverNumberSpinner=new JSpinner(serverNumberSpinnerNumberModel);
				serverNumberSpinner.setEditor(new JSpinner.DefaultEditor(serverNumberSpinner));
				serverNumberSpinner.setBounds(120,20,ComponentConstants.COMPONENT_LABEL_WIDTH_SMALL,ComponentConstants.COMPONENT_HEIGHT);
				configPanel.add(serverNumberSpinner);
				
				JLabel serverIPLabel=new JLabel("Server IP:");
				serverIPLabel.setBounds(43, 50, ComponentConstants.COMPONENT_LABEL_WIDTH_SMALL, ComponentConstants.COMPONENT_HEIGHT);
				configPanel.add(serverIPLabel);
				
				serverIPText=new JTextField();
				serverIPText.setBounds(120, 50, ComponentConstants.COMPONENT_TEXT_WIDTH, ComponentConstants.COMPONENT_HEIGHT);
				configPanel.add(serverIPText);
				
				JLabel serverNameLabel=new JLabel("Server name:");
				serverNameLabel.setBounds(21,80,ComponentConstants.COMPONENT_LABEL_WIDTH_SMALL,ComponentConstants.COMPONENT_HEIGHT);
				configPanel.add(serverNameLabel);
				
				serverNameText=new JTextField();
				serverNameText.setBounds(120, 80, ComponentConstants.COMPONENT_TEXT_WIDTH, ComponentConstants.COMPONENT_HEIGHT);
				serverNameText.setText("Pepperstone-Demo02");
				configPanel.add(serverNameText);
				
				JLabel maxLotsLabel=new JLabel("Max lots:");
				maxLotsLabel.setBounds(45, 110, ComponentConstants.COMPONENT_LABEL_WIDTH_SMALL, ComponentConstants.COMPONENT_HEIGHT);
				configPanel.add(maxLotsLabel);
				
				SpinnerNumberModel maxLotsSpinnerNumberModel=new SpinnerNumberModel(50, 1, 100,1);
				maxLotsSpinner=new JSpinner(maxLotsSpinnerNumberModel);
				maxLotsSpinner.setEditor(new JSpinner.DefaultEditor(maxLotsSpinner));
				maxLotsSpinner.setBounds(120,110,ComponentConstants.COMPONENT_LABEL_WIDTH_SMALL,ComponentConstants.COMPONENT_HEIGHT);
				configPanel.add(maxLotsSpinner);
				
				JLabel maxTradesLabel=new JLabel("Max trades:");
				maxTradesLabel.setBounds(28, 140, ComponentConstants.COMPONENT_LABEL_WIDTH_SMALL, ComponentConstants.COMPONENT_HEIGHT);
				configPanel.add(maxTradesLabel);
				
				SpinnerNumberModel maxTradesSpinnerNumberModel=new SpinnerNumberModel(100, 1, 200,1);
				maxTradesSpinner=new JSpinner(maxTradesSpinnerNumberModel);
				maxTradesSpinner.setEditor(new JSpinner.DefaultEditor(maxTradesSpinner));
				maxTradesSpinner.setBounds(120,140,ComponentConstants.COMPONENT_LABEL_WIDTH_SMALL,ComponentConstants.COMPONENT_HEIGHT);
				configPanel.add(maxTradesSpinner);
				
				JLabel lotSizeLabel=new JLabel("Lot size:");
				lotSizeLabel.setBounds(48, 170, ComponentConstants.COMPONENT_LABEL_WIDTH_SMALL, ComponentConstants.COMPONENT_HEIGHT);
				configPanel.add(lotSizeLabel);
				
				lotSizeText=new JTextField("2.5");
				lotSizeText.setBounds(120,170,ComponentConstants.COMPONENT_TEXT_WIDTH,ComponentConstants.COMPONENT_HEIGHT);
				configPanel.add(lotSizeText);
				
				JLabel hedgePipsLabel=new JLabel("Hedge at pips:");
				hedgePipsLabel.setBounds(15, 200, ComponentConstants.COMPONENT_LABEL_WIDTH_SMALL, ComponentConstants.COMPONENT_HEIGHT);
				configPanel.add(hedgePipsLabel);
				
				hedgePipsText=new JTextField();
				hedgePipsText.setBounds(120,200,ComponentConstants.COMPONENT_TEXT_WIDTH,ComponentConstants.COMPONENT_HEIGHT);
				configPanel.add(hedgePipsText);
				
				JLabel hedgeProtectionLabel=new JLabel("Hedge protection:");
				hedgeProtectionLabel.setBounds(15, 230, ComponentConstants.COMPONENT_LABEL_WIDTH_SMALL+30, ComponentConstants.COMPONENT_HEIGHT);
				configPanel.add(hedgeProtectionLabel);
				
				hedgeProButtonGroup=new ButtonGroup();
				JRadioButton yesButton=new JRadioButton("Yes",true);
				hedgeProButtonGroup.add(yesButton);
				yesButton.setBounds(140, 230, 50, 25);
				JRadioButton noButton=new JRadioButton("No",false);
				hedgeProButtonGroup.add(noButton);
				noButton.setBounds(190, 230, 50, 25);
				configPanel.add(yesButton);
				configPanel.add(noButton);
	}
	
	public void changeComponentsEnableStatus(boolean isEnabled){
		accountText1.setEditable(isEnabled);
		passwordText1.setEditable(isEnabled);
		accountText2.setEditable(isEnabled);
		passwordText2.setEditable(isEnabled);
		accountText3.setEditable(isEnabled);
		passwordText3.setEditable(isEnabled);
		accountText4.setEditable(isEnabled);
		passwordText4.setEditable(isEnabled);
		accountText5.setEditable(isEnabled);
		passwordText5.setEditable(isEnabled);
		accountText6.setEditable(isEnabled);
		passwordText6.setEditable(isEnabled);
		accountText7.setEditable(isEnabled);
		passwordText7.setEditable(isEnabled);
		accountText8.setEditable(isEnabled);
		passwordText8.setEditable(isEnabled);
		accountText9.setEditable(isEnabled);
		passwordText9.setEditable(isEnabled);
		accountText10.setEditable(isEnabled);
		passwordText10.setEditable(isEnabled);
	
		
		loginButton.setEnabled(isEnabled);
		resetButton.setEnabled(!isEnabled);
	}
	
}
