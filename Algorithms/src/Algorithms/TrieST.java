package Algorithms;

import java.util.ArrayList;
import java.util.List;

public class TrieST<V> {
	
	private static final int R = 256;
	
	private static class Node{
		Object val;
		Node[] next = new Node[R];
	}
	
	private static Node root = new Node();
	
	@SuppressWarnings("unchecked")
	public V get(String key) {
		Node node = root;
		for(char c:key.toCharArray()) {
			if(node.next[c] == null)
				return null;
			node = node.next[c];
		}
		return (V)node.val;
	}
	
	
	public void put(String key, V val) {
		if(key==null || key.length()==0)
			return;
		Node node = root;
		for(char c:key.toCharArray()) {
			if(node.next[c] == null)
				node.next[c] = new Node();
			node = node.next[c];
		}
		node.val = val;
	}
	
	public int size() {
		return size(root);
	}
	
	private int size(Node node) {
		if(node == null)
			return 0;
		int size = 0;
		if(node.val != null)
			size++;
		for(char c=0; c<R; c++)
			size += size(node.next[c]);
		return size;
	}
	
	
	private void collect(Node node, String pre, List<String> list) {
		if(node == null)
			return;
		if(node.val != null)
			list.add(pre);
		for(char c=0; c<R; c++)
			collect(node.next[c], pre+c, list);
	}
	
	public Iterable<String> keysWithPre(String pre){
		List<String> list = new ArrayList<>();
		Node node = root;
		for(char c:pre.toCharArray()) {
			if(node == null) {
				break;
			}
			node = node.next[c];
		}
		collect(node, pre, list);
		return list;
	}
	
	public Iterable<String> keys(){
		return keysWithPre("");
	}
	
	
	
	private void collect(Node node, String pre, String pat, List<String> list) {
		if(node == null)
			return;
		int d = pre.length();
		if(d == pat.length() && node.val != null)
			list.add(pre);
		if(d == pat.length())
			return;
		char next = pat.charAt(d);
		for(char c = 0; c<R; c++)
			if(next == '.' || next == c)
				collect(node.next[c], pre+c, list);
	}
	
	public Iterable<String> keysThatMatch(String pat){
		List<String> list = new ArrayList<>();
		collect(root, "",pat,list);
		return list;
	}
	
	public String longestPre(String key) {
		return key.substring(0, searchLongestPre(root,0,0,key));
	}
	
	private int searchLongestPre(Node node, int pre, int d, String key) {
		if(node == null)
			return d;
		if(node.val != null)
			d = pre;
		if(pre == key.length())
			return d;
		char c = key.charAt(pre);
		return searchLongestPre(node.next[c], pre+1, d, key);
	}
	
	private Node delete(Node node, String key, int d) {
		if(node == null)
			return null;
		if(d == key.length())
			node.val = null;
		else {
			char c = key.charAt(d);
			node.next[c] = delete(node.next[c], key, d+1);
		}
		if(node.val != null)
			return node;
		for(char c=0; c<R; c++)
			if(node.next[c] != null)
				return node;
		return null;
	}
	
	public void delete(String key) {
		root = delete(root, key, 0);
	}
	
	
	
	
	
	
	
	
	
	

}
