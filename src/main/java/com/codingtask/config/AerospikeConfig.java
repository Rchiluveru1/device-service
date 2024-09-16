package com.codingtask.config;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.aerospike.config.AbstractAerospikeDataConfiguration;
import org.springframework.data.aerospike.repository.config.EnableAerospikeRepositories;
import org.springframework.validation.annotation.Validated;

import com.aerospike.client.Host;

import lombok.Data;

@EnableAerospikeRepositories(basePackages = "com.codingtask")
@EnableConfigurationProperties(AerospikeConfig.AerospikeConfigurationProperties.class)
@Configuration
public class AerospikeConfig extends AbstractAerospikeDataConfiguration {

    @Autowired
    AerospikeConfigurationProperties properties;

    @Override
    protected Collection<Host> getHosts() {
        return Host.parseServiceHosts(properties.getHosts());
    }

    @Override
    protected String nameSpace() {
        return properties.getNamespace();
    }

	@Data
	@ConfigurationProperties("aerospike")
	public static class AerospikeConfigurationProperties {

		String hosts;

		String namespace;
	}
}
