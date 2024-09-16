package com.codingtask.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.aerospike.repository.AerospikeRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aerospike.client.query.KeyRecord;
import com.codingtask.entity.Device;

@Repository
public interface DeviceRepository extends AerospikeRepository<Device, Long>, CrudRepository<Device, Long>{

	List<Device> findById(String id);

	void save(KeyRecord keyRecord);

	List<Device> findByOsName(String osName);

	void deleteById(String id);

}
