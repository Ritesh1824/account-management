package com.bank.account.dto.request;

import org.springframework.stereotype.Component;

@Component
public class TransferDetails {
	
	Long debitAccount;
	Long creditAccount;
	Double amount;
	Double calAmount;

	public Double getCalAmount() {
		return calAmount;
	}
	public void setCalAmount(Double calAmount) {
		this.calAmount = calAmount;
	}
	public Long getDebitAccount() {
		return debitAccount;
	}
	public void setDebitAccount(Long debitAccount) {
		this.debitAccount = debitAccount;
	}
	public Long getCreditAccount() {
		return creditAccount;
	}
	public void setCreditAccount(Long creditAccount) {
		this.creditAccount = creditAccount;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
}
