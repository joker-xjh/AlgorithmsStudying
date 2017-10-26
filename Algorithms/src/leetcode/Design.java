package leetcode;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Design {
	
	private Map<Integer, Integer> cache = new HashMap<>();
	private Map<Integer, Integer> cacheCounter = new HashMap<>();
	private int counter=0;
	private int capacity;
	
    public void LRUCache(int capacity) {
        this.capacity = capacity;
    }
    
    public int get(int key) {
        Integer res = cache.get(key);
        if(res == null) {
        	res = -1;
        }
        else {
        	cacheCounter.put(key, counter);
        	counter++;
        }
    	return res.intValue();
    }
    
    public void put(int key, int value) {
        if( capacity >0) {
        	Integer contain = cache.get(key);
        	if(contain == null)
        		capacity--;
        	cache.put(key, value);
        	cacheCounter.put(key, counter);
        	counter++;
        	
        }
        else {
        	Integer contain = cache.get(key);
        	if(contain == null) {
        		int vicitm=0;
            	int small = Integer.MAX_VALUE;
//            	for(Integer k:cacheCounter.keySet()) {
//            		Integer val = cacheCounter.get(k);
//            		if(val < small) {
//            			small = val;
//            			vicitm = k;
//            		}
//            	}
            	for(Entry<Integer, Integer> entry:cacheCounter.entrySet()) {
            		int val = entry.getValue();
            		if(val<small) {
            			small = val;
            			vicitm = entry.getKey();
            		}
            	}
            	cache.remove(vicitm);
            	cacheCounter.remove(vicitm);
            	cache.put(key, value);
            	cacheCounter.put(key, counter);
            	counter++;
        	}
        	else {
        		cache.put(key, value);
        		cacheCounter.put(key, counter);
        		counter++;
        	}
        }
    }
    
    
    
    
    class Node{
    	int key;
    	int val;
    	Node pre;
    	Node next;
    	
    	public Node(int key, int val) {
    		this.key = key;
    		this.val = val;
    	}
    	
    }
    
    private Map<Integer, Node> map;
    private int capa,count;
    private Node head, tail;
    
    
    public void LRUCache2(int capacity) {
    	map = new HashMap<>();
    	capa = capacity;
    	head = new Node(0, 0);
    	tail = new Node(0, 0);
    	head.next = tail;
    	tail.pre = head;
    }
    
    private void deleteNode(Node node) {
    	node.pre.next = node.next;
    	node.next.pre = node.pre;
    }
    
    private void addToHead(Node node) {
    	node.next = head.next;
    	head.next.pre = node;
    	node.pre = head;
    	head.next = node;
    }
    
    
    public int get2(int key) {
    	Node node = map.get(key);
    	if(node != null) {
    		int val = node.val;
    		deleteNode(node);
    		addToHead(node);
    		return val;
    	}
    	return -1;
    }
    
    
    public void put2(int key, int value) {
    	Node node = map.get(key);
    	if(node != null) {
    		node.val = value;
    		deleteNode(node);
    		addToHead(node);
    	}
    	else {
    		Node node2 = new Node(key, value);
    		map.put(key, node2);
    		if(count < capa) {
    			count++;
    			addToHead(node2);
    		}
    		else {
    			map.remove(tail.pre.key);
    			deleteNode(tail.pre);
    			addToHead(node2);
    		}
    	}
    }
    
    
    private Map<Integer, Integer> LFU;
    private Map<Integer, Integer> LFUCount;
    private Map<Integer, LinkedHashSet<Integer>> lists;
    private int cap;
    private int min = -1;
    
    public void LFUCache(int capacity) {
        cap = capacity;
        LFU = new HashMap<>();
        LFUCount = new HashMap<>();
        lists = new HashMap<>();
        lists.put(1, new LinkedHashSet<>());
    }
    
    public int LFUget(int key) {
    	if(!LFU.containsKey(key))
    		return -1;
    	int val = LFU.get(key);
    	int count = LFUCount.get(key);
    	lists.get(count).remove(key);
    	LFUCount.put(key, count+1);
    	if(count == min && lists.get(min).size()==0)
    		min++;
    	if(lists.get(count+1) == null)
    		lists.put(count+1, new LinkedHashSet<>());
    	lists.get(count+1).add(key);
    	return val;
    }
    
    public void LFUput(int key, int value) {
    	if(cap<=0)
    		return;
    	if(LFU.containsKey(key)) {
    		LFU.put(key, value);
    		LFUget(key);
    		return;
    	}
    	
    	if(LFU.size()>=cap) {
    		int remove = lists.get(min).iterator().next();
    		lists.get(min).remove(remove);
    		LFU.remove(remove);
    		LFUCount.remove(remove);
    	}
    	LFU.put(key, value);
    	LFUCount.put(key, 1);
    	lists.get(1).add(key);
    	min = 1;
    }
    
   
    
    
    private class Interval {
    	int start, end;
    	public Interval(int start, int end) {
			this.start = start;
			this.end = end;
		}
    }
    
    
    TreeMap<Integer, Interval> intervals;
    
    public void RangeModule() {
    	intervals = new TreeMap<>();
    }
    
    public void addRange(int left, int right) {
    	if(intervals.containsKey(left)) {
    		 Interval cur = intervals.get(left);
             cur.end = Math.max(cur.end, right);
             Map.Entry<Integer, Interval> high = intervals.higherEntry(cur.start);
             while (high != null) {
                 if (high.getKey() > cur.end) break;
                 intervals.remove(high.getKey());
                 cur.end = Math.max(cur.end, high.getValue().end);
                 high = intervals.higherEntry(cur.start);
             }
    	}
    	else {
    		Interval cur = new Interval(left, right);
    		Map.Entry<Integer, Interval> low = intervals.lowerEntry(left);
    		if(low != null && low.getValue().end >= cur.start) {
    			intervals.remove(left);
    			cur.start = Math.min(cur.start, low.getValue().start);
    			cur.end = Math.max(cur.end, low.getValue().end);
    		}
    		Map.Entry<Integer, Interval> high = intervals.higherEntry(left);
    		while(high != null) {
    			if(high.getKey() > cur.end)
    				break;
    			intervals.remove(high.getKey());
    			cur.end = Math.max(cur.end, high.getValue().end);
    			high = intervals.higherEntry(cur.start);
    		}
    		intervals.put(cur.start, cur);
    	}
    }
    
    
    public boolean queryRange(int left, int right) {
    	if(intervals.containsKey(left)) {
    		Interval cur = intervals.get(left);
    		return cur.end >= right;
    	}
    	else {
    		Map.Entry<Integer, Interval> low = intervals.lowerEntry(left);
    		return low == null ? false : low.getValue().end >= right;
    	}
    }
    
    
    public void removeRange(int left, int right) {
    	if(intervals.containsKey(left)) {
    		Interval cur = intervals.get(left);
    		while(cur != null) {
    			if(cur.start >= right)
    				break;
    			intervals.remove(cur.start);
    			if(right <= cur.end) {
    				cur.start = right;
    				intervals.put(cur.start, cur);
    				break;
    			}
    			else {
    				Map.Entry<Integer, Interval> high = intervals.higherEntry(cur.start);
    				cur = high == null ? null : high.getValue();
    			}
    		}
    	}
    	else {
    		Map.Entry<Integer, Interval> low = intervals.lowerEntry(left);
    		if(low != null) {
    			if(right < low.getValue().end) {
    				intervals.put(right, new Interval(right, low.getValue().end));
    			}
    			low.getValue().end = Math.min(low.getValue().end,  left);
    		}
    		Map.Entry<Integer, Interval> high = intervals.higherEntry(left);
    		Interval cur = high == null ? null : high.getValue();
    		while(cur != null) {
    			if(cur.start >= right)
    				break;
    			intervals.remove(cur.start);
    			if(right <= cur.end) {
    				cur.start = right;
    				intervals.put(cur.start, cur);
    				break;
    			}
    			else {
    				high = intervals.higherEntry(cur.start);
    				cur = high == null ? null : high.getValue();
    			}
    		}
    	}
    }
    
    
    

    
    public static void main(String[] args) {
		Design cache = new Design();
		cache.LFUCache(0);
        cache.LFUput(0, 0);
        cache.LFUget(0);
        

    }

}
