package Algorithms;

public class StripedMap {
	
	private static final int N_LOCK = 16;
	
	private static class Node{
		Object key;
		Object value;
		Node next;
		@SuppressWarnings("unused")
		public Node(Object key, Object value) {
			this.key = key;
			this.value = value;
		}
	}
	
	private Node[] tables;
	private Object[] locks;
	
	public StripedMap(int capacity) {
		tables = new Node[capacity];
		locks = new Object[N_LOCK];
		for(int i=0; i<N_LOCK; i++)
			locks[i] = new Object();
	}
	
	private final int hash(Object obj) {
		return (obj.hashCode() & 0x7fffffff)%tables.length;
	}
	
	public Object get(Object key) {
		int hash = hash(key);
		synchronized(locks[hash % N_LOCK]) {
			for(Node node = tables[hash]; node != null; node=node.next) {
				if(node.key.equals(key))
					return node.value;
			}
		}
		return null;
	}
	
	public void clear() {
		for(int i=0; i<tables.length; i++) {
			synchronized (locks[i % N_LOCK]) {
				tables[i] = null;
			}
		}
	}
	
	
	
	
	

}
