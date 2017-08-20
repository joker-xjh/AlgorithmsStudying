package Algorithms;

public class LSD {
	
	public static void LSDSort(String[] array, int w) {
		int r = 256;
		int n = array.length; 
		String[] temp = new String[n];
		
		for(int d=w-1; d>=0; d--) {
			int[] count = new int[r+1];
			 
			for(int k=0;k<n;k++) {
				count[array[k].charAt(d)+1]++;
			}
			
			for (int i = 0; i <r ; i++) {
				count[r+1] += count[r];
			}
			
			for(int i=0; i<n; i++) {
				temp[ count[array[i].charAt(d)]++ ] = array[i];
			}
			
			for(int i=0; i<n; i++) {
				array[i] = temp[i];
			}
			
		}
		
	}
	
	
	public static void LSDSort2(String[] array, int w) {
		int length = array.length;
		final int r = 256;
		String[] temp = new String[length];
		
		for(int d=w-1; d>=0; d--) {
			
			int[] count = new int[r+1];
			
			for(int i=0; i<length; i++)
				count[array[i].charAt(d)+1]++;
			for(int i=0;i<r;i++)
				count[i+1] += count[i];
			for(int i=0; i<length; i++)
				temp[count[array[i].charAt(d)]++] = array[i];
			for(int i=0; i<length; i++)
				array[i] = temp[i];
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
