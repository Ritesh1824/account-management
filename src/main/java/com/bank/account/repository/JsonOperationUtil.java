package com.bank.account.repository;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bank.account.dto.Customer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonOperationUtil {
	
	private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	public synchronized ConcurrentHashMap<Long,Customer> readJsonData(){
		ObjectMapper  mapper = new ObjectMapper ();
		
		ConcurrentHashMap<Long,Customer> sourceData = new ConcurrentHashMap<Long,Customer>();
		try {
			sourceData = (ConcurrentHashMap<Long,Customer>) mapper.readValue(Paths.get("src//main//resources//source-data.json").toFile(), new TypeReference<ConcurrentHashMap<Long,Customer>>() {});
		} catch (IOException e) {
			logger.debug("Error while reading data ",e.getMessage());
		}
		return sourceData;
		
	}
	
	public synchronized  String writeJsonData(ConcurrentHashMap<Long,Customer> customers) {
		
		ObjectMapper  mapper = new ObjectMapper ();
		ConcurrentHashMap<Long,Customer> sourceData = readJsonData();
		customers.forEach((k,v) -> sourceData.put(k, v));
		
		try {
			mapper.writeValue(Paths.get("src//main//resources//source-data.json").toFile(), sourceData);
		} catch (IOException e) {
			logger.debug("Error while writing data ",e.getMessage());
		}
		return "Successfully write data to source";
		
	}
}
