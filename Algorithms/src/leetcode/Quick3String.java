package leetcode;

public class Quick3String {
	
	private static int charAt(String a, int d) {
		if(d<a.length())
			return a.charAt(d);
		return -1;
	}
	
	public static void sort(String[] array) {
		sort(array,0,array.length-1,0);
	}
	
	private static void sort(String[] array, int left, int right, int d) {
		if(right<=left)
			return;
		int lt = left,gt = right;
		int i = left +1;
		int v = charAt(array[left], d);
		while(i<=gt) {
			int cmp = charAt(array[i], d);
			if(cmp < v)
				exch(array, i++, lt++);
			else if(cmp>v)
				exch(array, i, gt--);
			else 
				i++;
		}
		sort(array, left, lt-1, d);
		if(v > 0)
			sort(array,lt,gt,d+1);
		sort(array,gt+1,right,d);
	}
	
	private static void exch(String[] array, int i, int j) {
		String temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

}
