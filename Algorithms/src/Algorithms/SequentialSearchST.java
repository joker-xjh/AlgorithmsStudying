package Algorithms;

import java.util.ArrayList;
import java.util.List;

public class SequentialSearchST<K,V> {
	
	private class Node {
		K key;
		V value;
		Node next;
		public Node(K key, V value, Node next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}
	}
	
	private Node first;
	
	private int N;
	
	public V get(K key) {
		for(Node temp = first; temp!= null; temp=temp.next) {
			if(temp.key.equals(key)) {
				return temp.value;
			}
		}
		return null;
	}
	
	public boolean put(K key, V value) {
		for(Node node = first; node!= null; node=node.next) {
			if(node.key.equals(key)) {
				node.value = value;
				return false;
			}
		}
		first = new Node(key, value, first);
		N++;
		return true;
	}
	
	public boolean remove(K key) {
		Node pre = first;
		for(Node node = first; node != null; node = node.next) {
			if(node.key.equals(key)) {
				if(node == first) {
					first = first.next;
				}
				else {
					pre.next = node.next;
				}
				return true;
			}
			pre = node;
		}
		return false;
	}
	
	public List<K> keys(){
		List<K> list = new ArrayList<>();
		for(Node node = first; node != null; node = node.next) {
			list.add(node.key);
		}
		return list;
	}
	
	
	
	public int size() {
		return N;
	}
	

}
