package com.codingtask.utils;

import java.util.Optional;
import java.util.UUID;

import com.codingtask.dto.DeviceDto;
import com.codingtask.entity.Device;

import ua_parser.Client;
import ua_parser.Parser;

public class DeviceUtils {

	public static long getId() {
		return Math.abs(UUID.randomUUID().getMostSignificantBits());
	}
	
	public static DeviceDto getUserAgentRequestDto(String userAgent) {
    	DeviceDto dto = null;
    	try {
            Parser uaParser = new Parser();
            Client client = uaParser.parse(userAgent);
            
			String osName = client.os.family;
			String osVersion = client.os.major + "." + Optional.ofNullable(client.os.minor).orElse("0") + "."
					+ Optional.ofNullable(client.os.patchMinor).orElse("0");
			String browserName = client.userAgent.family;
			String browserVersion = client.userAgent.major + "."
					+ Optional.ofNullable(client.userAgent.minor).orElse("0");
            
            System.out.println("osName: " + osName);
            System.out.println("osVersion: " + osVersion);
            System.out.println("browserName: " + browserName);
            System.out.println("browserVersion: " + browserVersion);
            
            dto = new DeviceDto(null, osName, osVersion, browserName, browserVersion, 1);
            
           }catch(Exception e) {
        	   System.out.println("Exception while parsing user agent string");
           }
    	return dto;
    }
	
	public static DeviceDto getDeviceDtoByDevice(Device device) {
		return new DeviceDto(
	            device.getId().toString(),
	            device.getOsName(),
	            device.getOsVersion(),
	            device.getBrowserName(),
	            device.getBrowserVersion(),
	            device.getHitCount()
	        );
    }

}
