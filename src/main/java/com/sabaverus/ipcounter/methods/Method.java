package com.sabaverus.ipcounter.methods;

public interface Method {

	/**
	 * Converts IPv4 address to decimal format
	 * 
	 * @param ip String that represents valid IPv4 address
	 * @return IP address converted to decimal format
	 */
	public static long getIpAsNumber(String ip) {
		String[] splitted;

		Integer part;
		Integer power;

		long number = 0;

		splitted = ip.split("\\.");

		for (Integer i = 0; i < 4; i++) {
			power = 3 - i;
			part = Integer.parseInt(splitted[i]);
			number += part * Math.pow(256, power);
		}

		return number;
	}

	/**
	 * 
	 * @return Count of unique IP addresses passed in current method
	 */
	public abstract long getResult();
	
	/**
	 * Looking in collection for given address
	 * 
	 * @param ip IPv4 address string
	 * @return
	 */
	public boolean isSet(String ip);
	
	
	public boolean isSet(long ipDecimal);
}
