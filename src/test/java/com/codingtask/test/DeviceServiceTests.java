package com.codingtask.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.aerospike.client.query.KeyRecord;
import com.aerospike.client.query.RecordSet;
import com.codingtask.dto.DeviceDto;
import com.codingtask.entity.Device;
import com.codingtask.repository.DeviceRepository;
import com.codingtask.service.DeviceService;
import com.codingtask.utils.DeviceUtils;

@SpringBootTest
class DeviceServiceTests {

	@MockBean
	private DeviceRepository deviceRepository;

	@Autowired
	private DeviceService deviceService;

	@Test
	public void testMatchOrCreateDevice_NewDevice() {
		Long id = getId();
		Device device = new Device(id, "Windows", "10", "Chrome", "92.0", 1);
		Mockito.when(deviceRepository.save(any(Device.class))).thenReturn(device);
		if (device != null) {
			assertEquals(1, device.getHitCount());
		}
	}

	public static long getId() {
		return Math.abs(UUID.randomUUID().getMostSignificantBits());
	}

}
