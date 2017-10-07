package Algorithms;

@SuppressWarnings("all")
public class Sort<T> {
	
	public static void bubbleSort(int[] array) {
		int length = array.length;
		int temp = 0;
		boolean b = false;
		for(int i=0; i<length-1; i++) {
			for(int j=0; j<length-i-1;j++) {
				if(array[j] > array[j+1]) {
					temp = array[j];
					array[j] = array[j+1];
					array[j+1] = temp;
					b = true;
				}
			}
			if(!b)
				break;
			//Hashtable
			//HashMap
		}
	}
	
	
	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}
	
	private static void exch(Comparable v, Comparable w) {
		Comparable temp = v;
		v = w;
		w = temp;
	}
	
	private static void exch(Comparable[] array, int i, int j) {
		Comparable temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	
	public static void Selection(Comparable[] array) {
		int size = array.length;
		for(int i=0; i<size; i++) {
			int min = i;
			for(int j=i+1; j<size; j++) {
				if(less(array[j], array[min])) {
					min = j;
				}
			}
			exch(array, i, min);
		}
	}
	
	
	public static void Insertion(Comparable[] array) {
		int N = array.length;
		for(int i=1; i<N; i++) {
			for(int j=i; j>0 && less(array[j], array[j-1]); j--) {
				exch(array, j, j-1);
			}
		}
	}
	
	
	public static void Shell(Comparable[] array) {
		int n = array.length;
		int h = 1;
		while(h < n/3) {
			h = h*3+1;
		}
		while(h >= 1) {
			for(int i = h; i<n; i++) {
				for(int j=i; j>=h && less(array[j], array[j-h]); j-=h) {
					exch(array, j, j-h);
				}
				h /= 3;
			}
		}
	}
	
	
	private static Comparable[] aux;
	
	public static void MergeSort(Comparable[] array) {
		int n = array.length;
		aux = new Comparable[n];
		MergeSort(array, 0, n-1);
	}
	
	private static void MergeSort(Comparable[] array, int lo, int hi) {
		if(hi <= lo)
			return;
		int mid = lo + (hi - lo) / 2;
		MergeSort(array, lo, mid);
		MergeSort(array, mid+1, hi);
		MergeSort(array, lo, mid, hi);
	}
	
	private static void MergeSort(Comparable[] array, int lo, int mid, int hi) {
		int i=lo, j = mid+1;
		for(int k=lo; k<=hi; k++) {
			aux[k] = array[k];
		}
		for(int k=lo; k<=hi; k++) {
			if(i > mid)
				array[k] = aux[j++];
			else if(j > hi)
				array[k] = aux[i++];
			else if(less(aux[i], aux[j]))
				array[k] = aux[i++];
			else
				array[k] = aux[j++];
		}
	}
	
	public static void QuickSort(Comparable[] array) {
		QuickSort(array, 0, array.length-1);
	}
	
	
	private static void QuickSort(Comparable[] array, int left, int right) {
		if(right <= left)
			return;
		int p = partition(array, left, right);
		QuickSort(array, left, p-1);
		QuickSort(array, p+1, right);
	}
	
	
	private static int partition(Comparable[] array, int left, int right) {
		int i = left, j = right;
		Comparable x = array[i];
		while(i < j) {
			while( i < j && less(x, array[j])){
				j--;
			}
			if(i < j)
				array[i++] = array[j];
			while(i < j && less(array[i], x)) {
				i++;
			}
			if(i < j) {
				array[j--] = array[i];
			}
		}
		array[i] = x;
		return i;
	}
	
	public static void Quick3Way(Comparable[] array) {
		Quick3Way(array, 0, array.length-1);
	}
	
	private static void Quick3Way(Comparable[] array, int lo, int hi) {
		if(lo > hi)
			return;
		int lt = lo, i = lo+1, gt = hi;
		Comparable v = array[lo];
		while(i <= gt) {
			int cmp = array[i].compareTo(v);
			if(cmp < 0)
				exch(array, lt++, i++);
			else if(cmp > 0)
				exch(array, i, gt--);
			else
				i++;
		}
		Quick3Way(array, lo, lt-1);
		Quick3Way(array, gt+1, hi);
	}
	
	
	
	

	public static void main(String[] args) {

	}

}
