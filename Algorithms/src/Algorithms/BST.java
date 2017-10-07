package Algorithms;

import java.util.ArrayList;
import java.util.List;

public class BST<K extends Comparable<K>, V> {
	
	private class Node {
		private K key;
		private V value;
		private Node left, right;
		private int N;
		
		public Node(K key, V value, int N) {
			this.key = key;
			this.value = value;
			this.N = N;
		}
	}
	
	
	private Node root;
	
	public int size() {
		return size(root);
	}
	
	private int size(Node node) {
		if(node == null)
			return 0;
		return node.N;
	}
	
	public V get(K key) {
		return get(root, key);
	}
	
	private V get(Node node, K key) {
		if(node == null)
			return null;
		int cmp = key.compareTo(node.key);
		if(cmp < 0)
			return get(node.left, key);
		else if(cmp > 0)
			return get(node.right, key);
		else
			return node.value;
	}
	
	public void put(K key, V value) {
		root = put(root, key, value);
	}
	
	
	private Node put(Node node, K key, V value) {
		if(node == null) {
			return new Node(key, value, 1);
		}
		int cmp = key.compareTo(node.key);
		if(cmp < 0)
			node.left = put(node.left, key, value);
		else if(cmp > 0)
			node.right = put(node.right, key, value);
		else
			node.value = value;
		node.N = size(node.left) + size(node.right) + 1;
		return node;
	}
	
	public K min() {
		return min(root).key;
	}
	
	private Node min(Node x) {
		if(x.left == null)
			return x;
		return min(x.left);
	}
	
	public K floor(K key) {
		Node node = floor(root, key);
		if(node != null)
			return node.key;
		return null;
	}
	
	private Node floor(Node node, K key) {
		if(node == null)
			return null;
		int cmp = key.compareTo(node.key);
		if(cmp < 0)
			return floor(node.left, key);
		else if(cmp == 0)
			return node;
		Node temp = floor(node.right, key);
		if(temp != null)
			return temp;
		return node;
	}
	
	public K select(int k) {
		Node node = select(root, k);
		return node.key;
	}
	
	private Node select(Node node, int k) {
		if(node == null)
			return null;
		int t = size(node.left);
		if(t > k)
			return select(node.left, k);
		else if(t < k)
			return select(node.right, k-t-1);
		else
			return node;	
	}
	
	
	private int rank(Node node, K key) {
		if(node == null)
			return 0;
		int cmp = key.compareTo(node.key);
		if(cmp < 0)
			return rank(node.left, key);
		else if(cmp > 0)
			return rank(node.right, key)+1+size(node.left);
		else
			return size(node.left);
	}
	
	public int rank(K key) {
		return rank(root,key);
	}
	
	private Node deleteMin(Node node) {
		if(node.left == null)
			return node.right;
		node.left = deleteMin(node.left);
		node.N = size(node.left) + size(node.right) + 1;
		return node;
	}
	
	public void deleteMin() {
		root = deleteMin(root);
	}
	
	public void delete(K key) {
		root = delete(root, key);
	}
	
	private Node delete(Node node, K key) {
		if(node == null)
			return null;
		int cmp = key.compareTo(node.key);
		if(cmp < 0)
			node.left = delete(node.left, key);
		else if(cmp > 0)
			node.right = delete(node.right, key);
		else {
			if(node.left == null)
				return node.right;
			if(node.right == null)
				return node.left;
			Node temp = node;
			node = min(node.right);
			node.right = deleteMin(temp.right);
			node.left = temp.left;
		}
		node.N = size(node.left) + size(node.right) + 1;
		return node;
	}
	
	public List<K> keys(K lo, K hi){
		List<K> list = new ArrayList<>();
		keys(root, list, lo, hi);
		return list;
	}
	
	
	private void keys(Node node, List<K> list, K lo, K hi) {
		if(node == null)
			return;
		int cmplo = lo.compareTo(node.key);
		int cmphi = hi.compareTo(node.key);
		if(cmplo < 0)
			keys(node.left, list, lo, hi);
		if(cmplo <= 0 && cmphi >= 0)
			list.add(node.key);
		if(cmphi > 0)
			keys(node.right, list, lo, hi);
	}
	
	
	
	
	
	
	
	
	
}
