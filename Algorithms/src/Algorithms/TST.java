package Algorithms;

import java.util.ArrayList;
import java.util.List;


public class TST<V> {
	
	private class Node{
		char c;
		Node left, mid, right;
		V val;
	}
	
	
	private Node root;
	
	public V get(String key) {
		Node node = get(root, key, 0);
		if(node == null)
			return null;
		return node.val;
	}
	
	public V get2(String key) {
		if(key == null || key.length()==0)
			return null;
		Node temp = root;
		int d = 0;
		while(temp != null) {
			char c = key.charAt(d);
			if(temp.c < c)
				temp = temp.right;
			else if(temp.c > c)
				temp = temp.left;
			else {
				if(d==key.length()-1)
					return temp.val;
				temp = temp.mid;
				d++;
			}
		}
		return null;
	}
	
	
	
	private Node get(Node node, String key, int d) {
		if(node == null)
			return null;
		char c = key.charAt(d);
		if(node.c < c)
			return get(node.right, key, d);
		else if(node.c > c)
			return get(node.left, key, d);
		else if(d < key.length()-1)
			return get(node.mid, key, d+1);
		return node;
	}
	
	private Node put(Node node, String key, V val, int d) {
		char c = key.charAt(d);
		if(node == null) {
			node = new Node();
			node.c = c;
		}
		
		if(node.c < c)
			node.right = put(node.right, key, val, d);
		else if(node.c > c)
			node.left = put(node.left, key, val, d);
		else if(d < key.length()-1)
			node.mid = put(node.mid, key, val, d+1);
		else
			node.val = val;
		return node;
	}
	
	public void put(String key,V val) {
		root = put(root, key, val, 0);
	}
	
	public void put2(String key, V val) {
		if(key==null || key.length()==0)
			return;
		if(root == null) {
			root = new Node();
			root.c = key.charAt(0);
		}
		Node temp = root;
		int d = 0;
		while(d < key.length()) {
			char c = key.charAt(d);
			if(temp.c > c) {
				if(temp.left == null) {
					temp.left = new Node();
					temp.left.c = c;
				}
				
				temp = temp.left;
			}
			else if(temp.c < c) {
				if(temp.right == null) {
					temp.right = new Node();
					temp.right.c = c;
				}
				temp = temp.right;
			}
			else {
				if(d == key.length()-1) {
					temp.val = val;
					break;
				}
				if(temp.mid == null) {
					temp.mid = new Node();
					temp.mid.c = key.charAt(d+1);
				}
				temp = temp.mid;
				d++;
			}
		}
	}
	
	
	private void collect(List<String> list, Node node, String pre) {
		if(node == null)
			return;
		if(node.val != null)
			list.add(pre+node.c);
		collect(list, node.left, pre);
		collect(list, node.mid, pre+node.c);
		collect(list, node.right, pre);
	}
	
	public List<String> keysWithPre(String pre){
		List<String> list = new ArrayList<>();
		if(pre==null)
			return list;
		if(pre.length() ==0) {
			collect(list, root, "");
			return list;
		}
		Node node = get(root, pre, 0);
		if(node == null)
			return list;
		if(node.val != null)
			list.add(pre);
		node = node.mid;
		collect(list, node, pre);
		return list;
	}
	
	public List<String> keys(){
		return keysWithPre("");
	}
	
	
	

}
