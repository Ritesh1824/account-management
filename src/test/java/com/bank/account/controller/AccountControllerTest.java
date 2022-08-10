package com.bank.account.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bank.account.dto.Customer;
import com.bank.account.dto.request.TransferDetails;
import com.bank.account.service.AccountService;


@SpringBootTest
class AccountControllerTest {
	
	@InjectMocks
	AccountController accountController;
	
	@Mock
	AccountService accountService;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void testGetAllAccountDetails() {
		Customer c1 = new Customer(Long.valueOf(2548634),"Eur",Double.valueOf(15000));
		Customer c2 = new Customer(Long.valueOf(2548345),"Eur",Double.valueOf(25000));
		
		ConcurrentHashMap<Long,Customer> sourceData = new ConcurrentHashMap<>();
		sourceData.put(Long.valueOf(2548634), c1);
		sourceData.put(Long.valueOf(2548345), c2);			
		when(accountService.getAllAccount()).thenReturn(sourceData);
		
		ResponseEntity<List<Customer>> customers = accountController.getAllAccountDetails();
		
		 assertThat(customers.getStatusCode()).isEqualTo(HttpStatus.OK);
		 assertThat(customers.getBody().size() == 2);
		 assertThat(customers.getBody().get(0).getAccountNumber().equals(Long.valueOf(2548634)) );
	}

	@Test
	void testValidateTransferDetails() {
		TransferDetails transferDetail = new TransferDetails();
		transferDetail.setAmount(1000.50);
		transferDetail.setCalAmount(2000.00);
		transferDetail.setCreditAccount(Long.valueOf(2548634));
		transferDetail.setDebitAccount(Long.valueOf(2548345));		
		
		Customer c1 = new Customer(Long.valueOf(2548634),"Eur",Double.valueOf(15000));
		Customer c2 = new Customer(Long.valueOf(2548345),"Eur",Double.valueOf(25000));
		
		Map<String, Customer> transferDetailSource = new HashMap<String, Customer>();
		transferDetailSource.put("Credit", c1);
		transferDetailSource.put("Debit", c2);	
		
		when(accountService.validateTransferDetails(transferDetail)).thenReturn(transferDetailSource);
		
		ResponseEntity<Map<String,Customer>> validatedTransferDetail = accountController.validateTransferDetails(transferDetail);
		 assertThat(validatedTransferDetail.getStatusCode()).isEqualTo(HttpStatus.OK);
		 assertThat(validatedTransferDetail.getBody().get("Debit").getAccountNumber().equals(transferDetail.getDebitAccount()));
	}

	@Test
	void testTransferMoney() {
		TransferDetails transferDetail = new TransferDetails();
		transferDetail.setAmount(1000.50);
		transferDetail.setCalAmount(2000.00);
		transferDetail.setCreditAccount(Long.valueOf(2548634));
		transferDetail.setDebitAccount(Long.valueOf(2548345));
		
		Customer c1 = new Customer(Long.valueOf(2548634),"Eur",Double.valueOf(15000));
		Customer c2 = new Customer(Long.valueOf(2548345),"Eur",Double.valueOf(25000));
		Map<String, Customer> transferDetailSource = new HashMap<String, Customer>();
		transferDetailSource.put("Credit", c1);
		transferDetailSource.put("Debit", c2);	
		
		when(accountService.processTransaction(transferDetail)).thenReturn("success");
		
		ResponseEntity<String> response = accountController.transferMoney(transferDetail);
		 assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		 assertThat(response.getBody().equalsIgnoreCase("success"));
	}

}
