package com.sabaverus.ipcounter.methods;

import java.util.BitSet;

/**
 * This method stores IP address as one bit in BitSet array
 * 
 * @author Sabaverus
 */
public class BitSetMethod implements Method {

	private BitSet[] bitset;
	public static final short CHUNK_SIZE = 2;

	public BitSetMethod() {

		bitset = new BitSet[CHUNK_SIZE];
		for (Integer i = 0; i < CHUNK_SIZE; i++) {
			bitset[i] = new BitSet(Integer.MAX_VALUE);
		}
	}

	/**
	 * Calculates chunk index and BitSet index for given 
	 * <br> IP address and storing them to collection
	 * 
	 * @param ipDecimal IPv4 address in decimal format
	 */
	public void pushToSet(long ipDecimal) {

		bitset[getChunkIndex(ipDecimal)].set(getIpBitSetIndex(ipDecimal));
	}

	/**
	 * Calculates chunk index and BitSet index for given 
	 * <br> IP address and storing them to collection
	 * 
	 * @param ip IPv4 address string
	 */
	public void pushToSet(String ip) {

		pushToSet(Method.getIpAsNumber(ip));
	}

	/**
	 * Calculates bit index in BitSet for IP address
	 * 
	 * @param ipDecimal IPv4 address in decimal format
	 * @return Position in BitSet with value 0 <= Integer.MAX_VALUE
	 */
	public static int getIpBitSetIndex(long ipDecimal) {
		int index = (int) ipDecimal;

		return index >= 0 ? index : index - Integer.MAX_VALUE - 1;
	}

	/**
	 * 
	 * @param ipDecimal IPv4 address in decimal format
	 * @return BitSet[] chunk number for given IP address
	 */
	public static int getChunkIndex(long ipDecimal) {

		return ipDecimal <= Integer.MAX_VALUE ? 0 : 1;
	}

	@Override
	public long getResult() {

		long total = 0;
		for (int i = 0; i < CHUNK_SIZE; i++) {
			total += bitset[i].cardinality();
		}

		return total;
	}
	
	@Override
	public boolean isSet(String ip) {

		return isSet(Method.getIpAsNumber(ip));
	}
	
	/**
	 * Looking in collection for given address by IPv4 in decimal format
	 * 
	 * @param ipDecimal IPv4 address in decimal format
	 * @return 
	 */
	public boolean isSet(long ipDecimal) {
		
		int chunkIndex = getChunkIndex(ipDecimal);
		int ipBitSetIndex = getIpBitSetIndex(ipDecimal);
		
		return bitset[chunkIndex].get(ipBitSetIndex);
	}
}
