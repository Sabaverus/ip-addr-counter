package com.sabaverus.ipcounter.methods;

import java.io.InputStream;
import java.util.BitSet;
import java.util.Scanner;

/**
 * This method stores IP address as one bit in BitSet array
 * 
 * @author Sabaverus
 */
public class BitSetMethod extends Method {

	private BitSet[] bitset;
	public static final short CHUNK_SIZE = 2;

	public BitSetMethod() {

		bitset = new BitSet[CHUNK_SIZE];
		for (Integer i = 0; i < CHUNK_SIZE; i++) {
			bitset[i] = new BitSet(Integer.MAX_VALUE);
		}
	}

	@Override
	public void process(InputStream stream) {

		Scanner s = new Scanner(stream);
		s.useDelimiter("\\n");

		long ip;
		while (s.hasNext()) {

			ip = getIpAsNumber(s.next());
			pushToSet(ip);
		}
		s.close();
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
}
