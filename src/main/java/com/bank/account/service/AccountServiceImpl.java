package com.bank.account.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bank.account.dto.Customer;
import com.bank.account.dto.request.TransferDetails;
import com.bank.account.exception.CustomException;
import com.bank.account.exception.ResourceNotFoundException;
import com.bank.account.repository.CustomerRepository;

@Service
public class AccountServiceImpl implements AccountService{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	CustomerRepository customerRepository;
	
	public AccountServiceImpl(CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
	}

	@Override
	public ConcurrentHashMap<Long, Customer> getAllAccount() {
		return customerRepository.getAllAccount();
	}
	
	@Override
	public Map<String, Customer> validateTransferDetails(TransferDetails transferDetail) {
		
		ConcurrentHashMap<Long, Customer> customers = getAllAccount();
		Map<String,Customer> customerDetail = new HashMap<>();
		if(!customers.containsKey(transferDetail.getDebitAccount())) {
			logger.error("Debit account "+ transferDetail.getCreditAccount()+" not found");
			throw new ResourceNotFoundException("Debit Account Does Not Found : "+ transferDetail.getDebitAccount());
		}
		if(!customers.containsKey(transferDetail.getCreditAccount())) {
			logger.error("credit account "+transferDetail.getCreditAccount()+ " not found");
			throw new ResourceNotFoundException("Credit Account Does Not Found : "+ transferDetail.getCreditAccount());
		}
		if(customers.get(transferDetail.getDebitAccount()).getBalance() < transferDetail.getAmount()) {
			logger.debug("Insufficient Balance in account : "+transferDetail.getDebitAccount());
			throw new CustomException("Insufficient Balance");
		}
		customerDetail.put("Debit",customers.get(transferDetail.getDebitAccount()));
		customerDetail.put("Credit",customers.get(transferDetail.getCreditAccount()));
		
		return customerDetail;
		
	}
	
	@Override
	public String processTransaction(TransferDetails transferDetail) {
		logger.info("Money transfer started");
		Map<String,Customer> customerDetail = validateTransferDetails(transferDetail);
		ConcurrentHashMap<Long,Customer> allAccountDetails = getAllAccount();
		
		Double debitBalance = customerDetail.get("Debit").getBalance()- transferDetail.getAmount();
		Double creditBalance = customerDetail.get("Credit").getBalance()+ transferDetail.getCalAmount();
		
		Customer debitAccount = customerDetail.get("Debit");
		Customer creditAccount = customerDetail.get("Credit");
		
		debitAccount.setBalance(debitBalance);
		creditAccount.setBalance(creditBalance);
		
		allAccountDetails.replace(customerDetail.get("Debit").getAccountNumber(), debitAccount);
		allAccountDetails.replace(customerDetail.get("Credit").getAccountNumber(), creditAccount);
		
		logger.info("Writting to source started ");
		customerRepository.writeTransferDetail(allAccountDetails);
		return "Transaction Successful";
	}

}
