package com.bank.account.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.bank.account.dto.Customer;
import com.bank.account.dto.request.TransferDetails;
import com.bank.account.repository.CustomerRepository;

@SpringBootTest
class AccountServiceImplTest {
	
	@InjectMocks
	AccountServiceImpl accountService;
	
	@Mock
	CustomerRepository customerRepository;
	
	@Test
	void testGetAllAccount() {
		
		Customer c1 = new Customer(Long.valueOf(2548634),"Eur",Double.valueOf(15000));
		Customer c2 = new Customer(Long.valueOf(2548345),"Eur",Double.valueOf(25000));
		
		ConcurrentHashMap<Long,Customer> sourceData = new ConcurrentHashMap<>();
		sourceData.put(Long.valueOf(2548634), c1);
		sourceData.put(Long.valueOf(2548345), c2);			
		when(customerRepository.getAllAccount()).thenReturn(sourceData);
		
		ConcurrentHashMap<Long, Customer> customers = accountService.getAllAccount();
		
		 assertThat(customers.size() == 2);
	}

	@Test
	void testValidateTransferDetails() {
		Customer c1 = new Customer(Long.valueOf(2548634),"Eur",Double.valueOf(15000));
		Customer c2 = new Customer(Long.valueOf(2548345),"Eur",Double.valueOf(25000));
		
		ConcurrentHashMap<Long,Customer> sourceData = new ConcurrentHashMap<>();
		sourceData.put(Long.valueOf(2548634), c1);
		sourceData.put(Long.valueOf(2548345), c2);			
		when(customerRepository.getAllAccount()).thenReturn(sourceData);
		
		TransferDetails transferDetail = new TransferDetails();
		transferDetail.setAmount(1000.50);
		transferDetail.setCalAmount(2000.00);
		transferDetail.setCreditAccount(Long.valueOf(2548634));
		transferDetail.setDebitAccount(Long.valueOf(2548345));
		
		Map<String,Customer> validatedTransferDetail = accountService.validateTransferDetails(transferDetail);
		 
		assertThat(validatedTransferDetail.get("Debit").getAccountNumber().equals(transferDetail.getDebitAccount()));
	}

	

}
