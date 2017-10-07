package Algorithms;

import java.util.ArrayList;
import java.util.List;

public class SeparateChainingHashST<K, V> {
	
	private int N;
	private int size;
	
	private SequentialSearchST<K, V>[] st;
	
	public SeparateChainingHashST(int capacity) {
		this.N = capacity;
		for(int i=0; i<N; i++) {
			st[i] = new SequentialSearchST<>();
		}
	}
	
	public SeparateChainingHashST() {
		this(997);
	}
	
	private int hash(K key) {
		return (key.hashCode() & 0x7fffffff) % st.length;
	}
	
	public V get(K key) {
		int hash = hash(key);
		return st[hash].get(key);
	}
	
	public void put(K key, V value) {
		int hash = hash(key);
		boolean add = st[hash].put(key, value);
		if(add)
			size++;
	}
	
	public int size() {
		return size;
	}
	
	public boolean remove(K key) {
		int hash = hash(key);
		boolean removed = st[hash].remove(key);
		if(removed)
			size--;
		return removed;
	}
	
	
	public List<K> keys(){
		List<K> list  = new ArrayList<>();
		for(int i=0; i<st.length; i++) {
			SequentialSearchST<K, V> temp = st[i];
			list.addAll(temp.keys());
		}
		return list;
	}

}
