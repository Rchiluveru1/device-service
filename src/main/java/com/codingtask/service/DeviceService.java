package com.codingtask.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.exp.Exp;
import com.aerospike.client.policy.QueryPolicy;
import com.aerospike.client.query.KeyRecord;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;
import com.codingtask.dto.DeviceDto;
import com.codingtask.entity.Device;
import com.codingtask.repository.DeviceRepository;
import com.codingtask.utils.DeviceUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeviceService {

	private DeviceRepository deviceRepository;
	private AerospikeClient aerospikeClient;

	public Device matchDevice(DeviceDto requestDto) {
		RecordSet result = findByDeviceFields(requestDto);
		// Updating hit counter for existing record
		for (KeyRecord keyRecord : result) {
			if (keyRecord != null) {
				Device device = getDeviceById(keyRecord.key.userKey.toString());
				if (device != null) {
					Device mapedDevice = mapToDeviceByUpdateHitCount(device, true);
					deviceRepository.save(mapedDevice);
					return mapedDevice;
				}
			}
		}
		// New Record
		long id = DeviceUtils.getId();
		Device newDevice = new Device(id, requestDto.getOsName(), requestDto.getOsVersion(),
				requestDto.getBrowserName(), requestDto.getBrowserVersion(), 1);
		return deviceRepository.save(newDevice);
	}
	
	public RecordSet findByDeviceFields(DeviceDto requestDto) {

		Statement statement = new Statement();
		statement.setNamespace("test");
		statement.setSetName("devices");

		QueryPolicy queryPolicy = new QueryPolicy();
		queryPolicy.failOnFilteredOut = true;
		queryPolicy.filterExp = Exp.build(Exp.and(Exp.eq(Exp.stringBin("osName"), Exp.val(requestDto.getOsName())),
				Exp.eq(Exp.stringBin("osVersion"), Exp.val(requestDto.getOsVersion())),
				Exp.eq(Exp.stringBin("browserName"), Exp.val(requestDto.getBrowserName())),
				Exp.eq(Exp.stringBin("browserVersion"), Exp.val(requestDto.getBrowserVersion()))));

		RecordSet result = aerospikeClient.query(queryPolicy, statement);
		return result;
	}

	public Device getDeviceById(String id) {
		List<Device> list = deviceRepository.findById(id);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	public List<Device> getDevicesByOsName(String osName) {
		return deviceRepository.findByOsName(osName);
	}

	public boolean deleteDevice(String id) {
		System.out.println("Delete Device ID: " + id);
		Device d = getDeviceById(id);
		if (d != null) {
			deviceRepository.delete(d);
			return true;
		}
		return false;
	}

	private Device mapToDeviceByUpdateHitCount(Device d, boolean isHitCountIncrease) {
		int hitCount = d.getHitCount();
		if (isHitCountIncrease) {
			hitCount = hitCount + 1;
		}
		return new Device(d.getId(), d.getOsName(), d.getOsVersion(), d.getBrowserName(), d.getBrowserVersion(),
				hitCount);
	}

}
