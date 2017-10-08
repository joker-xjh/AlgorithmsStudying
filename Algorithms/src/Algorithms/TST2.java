package Algorithms;

import java.util.ArrayList;
import java.util.List;

public class TST2<V> {
	
	private class Node{
		char c;
		V val;
		Node left, mid, right;
	}
	
	private Node root;
	
	private Node getNode(Node node, String key, int d) {
		if(node == null)
			return null;
		char c = key.charAt(d);
		if(c < node.c)
			return getNode(node.left, key, d);
		else if(c > node.c)
			return getNode(node.right, key, d);
		else {
			if(d == key.length()-1)
				return node;
			else
				return getNode(node.mid, key, d+1);
		}
	}
	
	public V get(String key) {
		if(key == null || key.length() == 0)
			return null;
		Node node = root;
		int index = 0;
		while(node != null) {
			char c = key.charAt(index);
			if(c < node.c)
				node = node.left;
			else if(c > node.c)
				node = node.right;
			else {
				if(index == key.length() -1)
					return node.val;
				else {
					node = node.mid;
					index++;
				}
			}
		}
		
		return null;
	}
	
	
	public void put(String key, V value) {
		if(key == null || key.length() == 0)
			return;
		if(root == null) {
			root = new Node();
			root.c = key.charAt(0);
		}
		Node node = root;
		int d = 0;
		while(true) {
			char c = key.charAt(d);
			if(c < node.c) {
				if(node.left == null) {
					node.left = new Node();
					node.left.c = c;
				}
				node = node.left;
			}
			else if(c > node.c) {
				if(node.right == null) {
					node.right = new Node();
					node.right.c = c;
				}
				node = node.right;
			}
			else {
				if(d == key.length() - 1) {
					node.val = value;
					break;
				}
				if(node.mid == null) {
					node.mid = new Node();
					node.mid.c = key.charAt(d+1);
				}
				node = node.mid;
				d++;
			}
		}
	}
	
	private void collect(Node node, List<String> keys, String pre) {
		if(node == null)
			return;
		if(node.val != null)
			keys.add(pre+node.c);
		collect(node.left, keys, pre);
		collect(node.mid, keys, pre+node.c);
		collect(node.right, keys, pre);
	}
	
	public List<String> keysWithPre(String pre){
		if(pre == null )
			return new ArrayList<>();
		if(pre.length() == 0)
			return keys();
		List<String> keys = new ArrayList<>();
		Node node = getNode(root, pre, 0);
		if(node == null)
			return keys;
		if(node.val != null)
			keys.add(pre);
		node = node.mid;
		collect(node, keys, pre);
		return keys;
	}
	
	public List<String> keys(){
		List<String> keys = new ArrayList<>();
		collect(root, keys, "");
		return keys;
	}
	
	
	
	
	
	
	
	

}
