package com.sabaverus.ipcounter.methods;

import java.io.InputStream;
import java.util.BitSet;
import java.util.Scanner;

public class BitSetMethod extends Method {

	private BitSet[] bitset;
	public static final short CHUNK_SIZE = 2;

	public BitSetMethod() {

		// https://stackoverflow.com/a/608177
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

	public void pushToSet(long ipNumber) {

		bitset[getChunkIndex(ipNumber)].set(getIpBitSetIndex(ipNumber));
	}

	public static int getIpBitSetIndex(long ip) {
		int index = (int) ip;

		return index >= 0 ? index : index - Integer.MAX_VALUE - 1;
	}

	public static int getChunkIndex(long ipNumber) {

		return ipNumber <= Integer.MAX_VALUE ? 0 : 1;
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
