package com.comp3008.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Observable;


//Singleton to centralize all data-manipulation, One for every new session
public class CreatePasswordSession extends Observable {

	
	private static CreatePasswordSession instance;
	public static final int MAX_ATTEMPTS = 3;
	private Password emailPassword;
	private Password shoppingPassword;
	private Password bankPassword;
	private String username;
	
	private boolean emailPasswordSet;
	private boolean shoppingPasswordSet;
	private boolean bankPasswordSet;
	
	private int emailAttempts;
	private int bankAttempts;
	private int shoppingAttempts;
	
	private boolean emailSuccess;
	private boolean bankSuccess;
	private boolean shoppingSuccess;
	
	private HashMap<String, double[]> attemptTimes;
	
	private double totalTimeEmail;
	private double totalTimeBank;
	private double totalTimeShopping;
	
	private String datetimeString;
	
	private ArrayList<Integer> testOrderList;
	private ArrayList<String> passwordTypeList;
	
	public CreatePasswordSession() {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		datetimeString = dateFormat.format(date);
		
		emailPasswordSet = false;
		shoppingPasswordSet = false;
		bankPasswordSet = false;
		
		emailAttempts = 1;
		bankAttempts = 1;
		shoppingAttempts = 1;
		
		emailSuccess = false;
		bankSuccess = false;
		shoppingSuccess = false;
		
		attemptTimes = new HashMap<String, double[]>();
		attemptTimes.put(Password.BANK, new double[MAX_ATTEMPTS]);
		attemptTimes.put(Password.EMAIL, new double[MAX_ATTEMPTS]);
		attemptTimes.put(Password.SHOPPING, new double[MAX_ATTEMPTS]);
		
		totalTimeEmail = 0;
		totalTimeBank = 0;
		totalTimeShopping = 0;
		
		testOrderList = new ArrayList<Integer>();
		
		testOrderList.add(0);
		testOrderList.add(1);
		testOrderList.add(2);
		Collections.shuffle(testOrderList);
		passwordTypeList = new ArrayList<String>();
		passwordTypeList.add(Password.BANK);
		passwordTypeList.add(Password.EMAIL);
		passwordTypeList.add(Password.SHOPPING);

		instance = this;
	}
	
	public static CreatePasswordSession getInstance() {
		if (instance == null) {
			instance = new CreatePasswordSession();
		}
		return instance;
	}

	public Password getEmailPassword() {
		return emailPassword;
	}

	public void setEmailPassword(Password emailPassword) {
		this.emailPasswordSet = true;
		this.emailPassword = emailPassword;	 
	    setChanged(); 
	    notifyObservers();
	}

	public Password getShoppingPassword() {
		return shoppingPassword;
	}

	public void setShoppingPassword(Password shoppingPassword) {
		this.shoppingPasswordSet = true;
		this.shoppingPassword = shoppingPassword;
	    setChanged(); 
	    notifyObservers();
	}

	public Password getBankPassword() {
		return bankPassword;
	}

	public void setBankPassword(Password bankPassword) {
		this.bankPasswordSet = true;
		this.bankPassword = bankPassword;
	    setChanged(); 
	    notifyObservers();
	}	
	public boolean emailPasswordSet() {
		return emailPasswordSet;
	}
	public boolean bankPasswordSet() {
		return bankPasswordSet;
	}
	public boolean shoppingPasswordSet() {
		return shoppingPasswordSet;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	
	public Password getPasswordByType(String passwordType) {
		if (passwordType.equals(Password.BANK)) {
			return bankPassword;
		}
		if (passwordType.equals(Password.EMAIL)) {
			return emailPassword;
		}
		else {
			return shoppingPassword;
		}
	}
	
	public int getAttemptsByType(String passwordType) {
		if (passwordType.equals(Password.BANK)) {
			return bankAttempts;
		}
		if (passwordType.equals(Password.EMAIL)) {
			return emailAttempts;
		}
		else {
			return shoppingAttempts;
		}
	}
	public void incrementAttemptsByType(String passwordType) {
		if (passwordType.equals(Password.BANK)) {
			 bankAttempts++;
		}
		if (passwordType.equals(Password.EMAIL)) {
			emailAttempts++;
		}
		if (passwordType.equals(Password.SHOPPING)) {
			shoppingAttempts++;
		}
	}
	
	public void setPasswordSuccess(String passwordType) {
		if (passwordType.equals(Password.BANK)) {
			 bankSuccess = true;
		}
		if (passwordType.equals(Password.EMAIL)) {
			emailSuccess = true;
		}
		if (passwordType.equals(Password.SHOPPING)){
			shoppingSuccess = true;
		}
	}
	public boolean passwordSuccesful(String passwordType) {
		if (passwordType.equals(Password.BANK)) {
			 return bankSuccess;
		}
		if (passwordType.equals(Password.EMAIL)) {
			return emailSuccess;
		}
		else {
			return shoppingSuccess;
		}
	}
	public int getNumberCorrect() {
		int correct = 0;
		if (bankSuccess) {
			 correct++;
		}
		if (emailSuccess) {
			correct++;
		}
		if (shoppingSuccess) {
			correct++;
		}
		return correct;
	}
	public void addAttemptTime(String passwordType, int attempt, double time) {
		double[] timeArray = attemptTimes.get(passwordType);
		timeArray[attempt-1] = time;
		attemptTimes.put(passwordType, timeArray);
	}
	public double getAttemptTime(String passwordType, int attempt) {
		double[] timeArray = attemptTimes.get(passwordType);
		return timeArray[attempt-1];
	}
	public void setTotalTime(String passwordType,double time) {
		if (passwordType.equals(Password.BANK)) {
			 totalTimeBank = time;
		}
		if (passwordType.equals(Password.EMAIL)) {
			totalTimeEmail = time;
		}
		if (passwordType.equals(Password.SHOPPING)) {
			totalTimeShopping = time;
		}
	}
	public double getTotalTime(String passwordType) {
		if (passwordType.equals(Password.BANK)) {
			 return totalTimeBank;
		}
		if (passwordType.equals(Password.EMAIL)) {
			return totalTimeEmail;
		}
		else {
			return totalTimeShopping;
		}
	}
	public String getNextTest(int testNum) {
		return passwordTypeList.get(testOrderList.get(testNum));
	}
	public String getDatetime() {
		return datetimeString;
	}
	public void newSession() {
		instance = null;
		getInstance();
	}
}
