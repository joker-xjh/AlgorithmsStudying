package Algorithms;
@SuppressWarnings("all")

public class LinearProbingHashST <K, V>{
	
	private int N;
	
	private K[] keys;
	private V[] values;
	
	public LinearProbingHashST(int size) {
		keys = (K[]) new Object[size];
		values = (V[]) new Object[size];
	}
	
	public LinearProbingHashST() {
		this(16);
	}
	
	private int hash(K key) {
		return (key.hashCode() & 0x7fffffff) % keys.length;
	}
	
	public V get(K key) {
		for(int i = hash(key); keys[i] != null; i=(i+1)%keys.length) {
			if(keys[i].equals(key))
				return values[i];
		}
		return null;
	}
	
	public void put(K key, V value) {
		int i;
		for(i=hash(key); keys[i]!=null; i=(i+1)%keys.length) {
			if(keys[i].equals(key)) {
				values[i] = value;
				return;
			}
		}
		keys[i] = key;
		values[i] = value;
		N++;
	}
	
	public void remove(K key) {
		if(!contains(key))
			return;
		int index = hash(key);
		while(!keys[index].equals(key)) {
			index = (index+1) % keys.length;
		}
		keys[index] = null;
		values[index] = null;
		N--;
		index = (index+1) % keys.length;
		while(keys[index]!= null) {
			K tempk = keys[index];
			V tempv = values[index];
			keys[index] = null;
			values[index] = null;
			N--;
			put(tempk, tempv);
		}
	}
	
	
	
	public int size() {
		return N;
	}
	
	public boolean contains(K key) {
		return get(key) != null;
	}
	
	
	

}
