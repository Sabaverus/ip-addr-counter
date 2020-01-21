package com.sabaverus.ipcounter.methods;

import java.io.InputStream;

public abstract class Method {

	public Method() {
	}

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
	 * Accept as parameter InputStream from any file with IP list
	 * uses {@code java.util.Scanner} with delimeter {@code "\\n"} for
	 * parsing stream from line to line
	 * 
	 * @param stream
	 */
	public abstract void process(InputStream stream);

	/**
	 * 
	 * @return Count of unique IP addresses passed in current method
	 */
	public abstract long getResult();
}
