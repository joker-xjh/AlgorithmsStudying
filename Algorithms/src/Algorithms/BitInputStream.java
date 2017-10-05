package Algorithms;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class BitInputStream extends FilterInputStream{
	
	private int bitLeft;
	private int bitCountLeft;
	
	protected BitInputStream(InputStream in) {
		super(in);
	}
	
	@Override
	public int read() throws IOException {
		clearBuff();
		return super.read();
	}
	
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		clearBuff();
		return super.read(b, off, len);
	}
	
	@Override
	public int read(byte[] b) throws IOException {
		clearBuff();
		return super.read(b);
	}
	
	public int readBit() throws IOException{
		if(bitCountLeft == 0) {
			bitLeft = super.read();
			bitCountLeft = 8;
			if(bitLeft == -1) {
				bitCountLeft = 0;
				return -1;
			}
		}
		return (bitLeft & mask(--bitCountLeft))>>bitCountLeft;
	}
	
	public int[] readBitsToArray(int nbits) throws IOException {
		if(nbits <= 0)
			throw new IllegalArgumentException();
		int bit = readBit();
		if(bit == -1)
			return new int[] {};
		int[] array = new int[nbits];
		array[0] = bit;
		for(int i=1; i<nbits; i++) {
			bit = readBit();
			if(bit == -1) {
				return Arrays.copyOf(array, i);
			}
			array[i] = bit;
		}
		
		return array;
	}
	
	public int readBisToInt(int nbits) throws IOException {
		if(nbits <=0 || nbits >32)
			throw new IllegalArgumentException();
		int bit =readBit();
		if(bit == -1)
			return -1;
		int result = bit;
		for(int i=1; i<nbits; i++) {
			bit = readBit();
			if(bit == -1)
				return result;
			result = result << 1 | bit;
		}
		return result;
	}
	
	
	
	private static int mask(int num) {
		if(num <0 || num >= 8)
			throw new IllegalArgumentException();
		return 1 << num;
	}
	
	private void clearBuff() {
		bitLeft = 0;
		bitCountLeft = 0;
	}

}
