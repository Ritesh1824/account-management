package com.bank.account.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.bank.account.dto.Customer;
import com.bank.account.dto.request.TransferDetails;

public interface AccountService {
	
	public ConcurrentHashMap<Long,Customer> getAllAccount();
	
	public Map<String, Customer> validateTransferDetails(TransferDetails transferDetail);
	
	public String processTransaction(TransferDetails transferDetail);
}
