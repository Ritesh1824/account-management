package com.bank.account.repository;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.bank.account.dto.Customer;

@Repository
public class CustomerRepository {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	JsonOperationUtil jsonOperation ;
	
	
	public CustomerRepository(JsonOperationUtil jsonOperation) {
		super();
		this.jsonOperation = jsonOperation;
	}
	
	public ConcurrentHashMap<Long,Customer> getAllAccount(){
		logger.info("Reading customer data from source");
		return jsonOperation.readJsonData();		
	}
	
	public String writeTransferDetail(ConcurrentHashMap<Long,Customer> customers){
		logger.info("Writting customer data to source");
		return jsonOperation.writeJsonData(customers);
	}
}
