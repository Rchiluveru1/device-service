package com.codingtask.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aerospike.client.AerospikeClient;
import com.codingtask.repository.DeviceRepository;

@Configuration
public class DeviseServiceConfig {

	@Bean
	public DeviceService deviseService(DeviceRepository repository, AerospikeClient aerospikeClient) {
		return new DeviceService(repository, aerospikeClient);
	}

}
