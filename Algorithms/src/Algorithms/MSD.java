package Algorithms;


public class MSD {
	
	private static final int R = 256;
	private static String[] temp;
	
	private static int charAt(String a, int d)
	{
		if (d<a.length()) {
			return a.charAt(d);
		}
		return -1;
	}
	
	public static void MSDSort(String[] array) {
		int n = array.length;
		temp = new String[n];
		MSDSort(array,0,n-1,0);
	}
	
	private static void MSDSort(String[] array, int left, int right, int d) {
		if (left>=right) {
			return;
		}
		int[] count = new int[R+2];
		
		for(int i=left; i<=right; i++)
			count[charAt(array[i], d)+2]++;
		for(int i=0; i<R+1; i++)
			count[i+1] += count[i];
		for(int i=left; i<=right; i++)
			temp[count[charAt(array[i], d)+1]++] = array[i];
		for(int i=left; i<=right; i++)
			array[i] = temp[i-left];
		for(int i=0;i<R;i++)
			MSDSort(array, left+count[i], left+count[i+1]-1,d+1);
		
	}
	
	
	@SuppressWarnings("unused")
	private static void MSDSort2(String[] array, int left, int right, int d) {
		if(right <= left)
			return;
		int[] count = new int[R+2];
		
		for(int i=left; i<=right; i++)
			count[charAt(array[i], d) +2 ]++;
		for(int i=0;i<=R;i++)
			count[i+1] += count[i];
		for(int i=left; i<=right; i++)
			temp[count[charAt(array[i], d)+1]++] = array[i];
		for(int i=left; i<=right; i++)
			array[i] = temp[i-left];
		for(int i=0; i<R; i++)
			MSDSort2(array, left+count[i], i+count[i+1]-1, d);
	}
	
	

}
