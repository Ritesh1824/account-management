package com.bank.account.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.account.dto.Customer;
import com.bank.account.dto.request.TransferDetails;
import com.bank.account.exception.ResourceNotFoundException;
import com.bank.account.service.AccountService;


@CrossOrigin
@RestController
@RequestMapping("/account")
public class AccountController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	AccountService accountService;
	
	public AccountController(AccountService accountService) {
		super();
		this.accountService = accountService;
	}

	@GetMapping("/allAcountDetails")
	public ResponseEntity<List<Customer>> getAllAccountDetails(){
		List<Customer> customers = accountService.getAllAccount().values().stream().collect(Collectors.toList());
			if(customers.isEmpty()) {
				logger.debug("No Customer found in Database");
				throw new ResourceNotFoundException("No Accounts  Available ");
			}
		return  ResponseEntity.ok().body(customers);		
	}
	
	@PostMapping("/validateTransfer")
	public ResponseEntity<Map<String,Customer>> validateTransferDetails(@RequestBody TransferDetails transferDetail){
		logger.info("Starting validating messages for debit:"+ transferDetail.getDebitAccount()+
						"Credit:"+transferDetail.getCreditAccount());
		return ResponseEntity.ok().body(accountService.validateTransferDetails(transferDetail));		
	}
	
	@PostMapping("/transfer")
	public ResponseEntity<String> transferMoney(@RequestBody TransferDetails transferDetail){
		logger.info("Transferring money from  debit:"+ transferDetail.getDebitAccount()+
				"Credit:"+transferDetail.getCreditAccount()+"Amount : " +transferDetail.getAmount());		
		return ResponseEntity.ok().body(accountService.processTransaction(transferDetail));
	}
}
