package com.sabaverus.ipcounter.methods;

import java.io.InputStream;

public abstract class Method {

	public Method() {
	}

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

	public abstract void process(InputStream stream);

	public abstract long getResult();
}
