package com.bank.account.dto;

public class Customer {
	
	Long accountNumber;
	String currency;
	Double balance;
	
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Customer(Long accountNumber, String currency, Double balance) {
		super();
		this.accountNumber = accountNumber;
		this.currency = currency;
		this.balance = balance;
	}
	
	
	public Long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	
}
