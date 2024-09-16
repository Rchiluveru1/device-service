package com.codingtask.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingtask.dto.DeviceDto;
import com.codingtask.entity.Device;
import com.codingtask.service.DeviceService;
import com.codingtask.utils.DeviceUtils;

import ua_parser.Client;
import ua_parser.Parser;


@RestController
@RequestMapping("/api/devices")
public class DeviceController {

	@Autowired
    DeviceService deviceService;

   
	@PostMapping("/match")
	public Device matchDevice(@RequestBody String userAgent) {
		DeviceDto requestDto = DeviceUtils.getUserAgentRequestDto(userAgent);
		return deviceService.matchDevice(requestDto);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Device> getDeviceById(@PathVariable String id) {
		Device device = deviceService.getDeviceById(id);
		return ResponseEntity.ok(device);
	}

    @GetMapping("/os/{osName}")
	public ResponseEntity<List<Device>> getDevicesByOsName(@PathVariable String osName) {
		List<Device> devices = deviceService.getDevicesByOsName(osName);
		return ResponseEntity.ok(devices);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteDevice(@PathVariable String id) {
		boolean isDeleted = deviceService.deleteDevice(id);
		if (isDeleted) {
			return ResponseEntity.status(HttpStatus.OK).body("Device deleted successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Device not found with id: " + id);
		}
	}
    
}
