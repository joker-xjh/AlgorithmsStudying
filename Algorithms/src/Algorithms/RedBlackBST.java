package Algorithms;

@SuppressWarnings("all")
public class RedBlackBST<K extends Comparable<K>, V> {
	
	private static final boolean RED = true;
	private static final boolean BLACK = false;
	
	private Node root;
	
	private class Node {
		K key;
		V value;
		Node left, right;
		int N;
		boolean color;
		public Node(K key, V value, int n, boolean color) {
			this.color = color;
			this.key = key;
			this.value = value;
			this.N = n;
		}
	}
	
	public int size() {
		return size(root);
	}
	
	private int size(Node node) {
		if(node == null)
			return 0;
		return node.N;
	}
	
	private boolean isRed(Node node) {
		if(node == null)
			return false;
		return node.color;
	}
	
	
	
	private Node rotateLeft(Node node) {
		Node temp = node.right;
		node.right = temp.right;
		temp.left = node;
		temp.color = node.color;
		node.color = RED;
		temp.N = node.N;
		node.N = size(node.left) + size(node.right) + 1;
		return temp;
	}
	
	private Node rotateRight(Node node) {
		Node left = node.left;
		node.left = left.right;
		left.right = node;
		left.color = node.color;
		node.color = RED;
		left.N = node.N;
		node.N = size(node.left) + size(node.right) + 1;
		return left;
	}
	
	private void flipColors(Node h) {
		h.color = RED;
		h.left.color = BLACK;
		h.right.color = BLACK;
	}
	
	public void put(K key, V value) {
		root = put(root, key, value);
	}
	
	private Node put(Node node, K key, V value) {
		if(node == null)
			return new Node(key, value, 1, RED);
		int cmp = key.compareTo(node.key);
		if(cmp < 0)
			node.left = put(node.left, key, value);
		else if(cmp > 0)
			node.right =put(node.right, key, value);
		else
			node.value = value;
		
		if(isRed(node.right) && !isRed(node.left))
			node = rotateLeft(node);
		if(isRed(node.left) && isRed(node.left.left))
			node = rotateRight(node);
		if(isRed(node.left) && isRed(node.right))
			flipColors(node);
		node.N = size(node.left) + size(node.right) + 1;
		return node;
	}
	
	
	
	

}
