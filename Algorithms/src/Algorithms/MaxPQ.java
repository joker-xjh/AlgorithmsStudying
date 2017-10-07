package Algorithms;

@SuppressWarnings("all")
public class MaxPQ <K extends Comparable<K>>{
	
	private K[] pq;
	private int N;
	
	public MaxPQ(int capacity) {
		pq = (K[]) new Object[capacity+1];
	}
	
	public boolean isEmpty() {
		return N == 0;
	}
	
	
	public void insert(K key) {
		pq[++N] = key;
		swim(N);
	}
	
	public K deleteMax() {
		K max = pq[1];
		exch(1, N);
		pq[N--] = null; 
		sink(1);
		return max;
	}
	
	private boolean less(int i, int j) {
		return pq[i].compareTo(pq[j]) < 0;
	}
	
	private void exch(int i, int j) {
		K t = pq[i];
		pq[i] = pq[j];
		pq[j] = t;
	}
	
	
	private void swim(int k) {
		while(k > 1 && less(k/2, k)) {
			exch(k/2, k);
			k /= 2;
		}
	}
	
	
	private void sink(int k) {
		while(2*k <= N) {
			int j = k * 2;
			if(j < N && less(j, j+1))
				j++;
			if(!less(k, j))
				break;
			exch(k, j);
			k = j;
		}
	}
	
}
