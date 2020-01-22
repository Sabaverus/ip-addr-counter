package com.sabaverus.ipcounter.methods;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BitSetMethodTest {

	@Test
	public void test() {
		
		String ip = null;
		long number = 0;
		int index = 0;
		
		long maxIpCount = (long) Math.pow(256, 4);
		
		BitSetMethod bitset = new BitSetMethod();
		
		ip = "0.0.0.0";
		number = Method.getIpAsNumber(ip);
		index = BitSetMethod.getIpBitSetIndex(number);
		assertEquals(ip, 0, number);
		assertEquals(ip + " BitSet index", 0, index);
		
		bitset.pushToSet(number);
		assertEquals(ip + " checking must be in array", true, bitset.isSet(ip));
		
		ip = "127.255.255.255";
		number = Method.getIpAsNumber(ip);
		index = BitSetMethod.getIpBitSetIndex(number);
		assertEquals(ip, Integer.MAX_VALUE, number);
		assertEquals(ip + " BitSet index must be same as Integer.MAX_VALUE", Integer.MAX_VALUE, index);
		
		bitset.pushToSet(number);
		assertEquals(ip + " checking must be in array", true, bitset.isSet(ip));
		
		ip = "128.0.0.0";
		number = Method.getIpAsNumber(ip);
		index = BitSetMethod.getIpBitSetIndex(number);
		assertEquals(ip, 1L + Integer.MAX_VALUE, number);
		assertEquals(ip + " must goin to second chunk at start of bitset", 0, index);
		
		bitset.pushToSet(number);
		assertEquals(ip + " checking must be in array", true, bitset.isSet(ip));
		
		ip = "255.255.255.254";
		number = Method.getIpAsNumber(ip);
		index = BitSetMethod.getIpBitSetIndex(number);
		assertEquals(ip, maxIpCount - 2, number);
		assertEquals(ip + " BitSet index", Integer.MAX_VALUE - 1, index);
		
		bitset.pushToSet(number);
		assertEquals(ip + " checking must be in array", true, bitset.isSet(ip));
		
		ip = "255.255.255.255";
		number = Method.getIpAsNumber(ip);
		index = BitSetMethod.getIpBitSetIndex(number);
		assertEquals(ip, maxIpCount - 1, number);
		assertEquals(ip + " BitSet index must be same as Integer.MAX_VALUE", Integer.MAX_VALUE, index);
		
		bitset.pushToSet(number);
		assertEquals(ip + " checking must be in array", true, bitset.isSet(ip));
		
		ip = "0.0.0.1";
		assertEquals(ip + " checking must be NOT in array", false, bitset.isSet(ip));
		ip = "128.0.0.1";
		assertEquals(ip + " checking must be NOT in array", false, bitset.isSet(ip));
		ip = "255.255.255.253";
		assertEquals(ip + " checking must be NOT in array", false, bitset.isSet(ip));
		
		long total = bitset.getResult();
		assertEquals("Get result", 5, total);
	}
}