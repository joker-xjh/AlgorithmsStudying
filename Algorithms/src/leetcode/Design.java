package leetcode;

import java.util.ArrayList;


import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

public class Design {

	private Map<Integer, Integer> cache = new HashMap<>();
	private Map<Integer, Integer> cacheCounter = new HashMap<>();
	private int counter = 0;
	private int capacity;

	public void LRUCache(int capacity) {
		this.capacity = capacity;
	}

	public int get(int key) {
		Integer res = cache.get(key);
		if (res == null) {
			res = -1;
		} else {
			cacheCounter.put(key, counter);
			counter++;
		}
		return res.intValue();
	}

	public void put(int key, int value) {
		if (capacity > 0) {
			Integer contain = cache.get(key);
			if (contain == null)
				capacity--;
			cache.put(key, value);
			cacheCounter.put(key, counter);
			counter++;

		} else {
			Integer contain = cache.get(key);
			if (contain == null) {
				int vicitm = 0;
				int small = Integer.MAX_VALUE;
				// for(Integer k:cacheCounter.keySet()) {
				// Integer val = cacheCounter.get(k);
				// if(val < small) {
				// small = val;
				// vicitm = k;
				// }
				// }
				for (Entry<Integer, Integer> entry : cacheCounter.entrySet()) {
					int val = entry.getValue();
					if (val < small) {
						small = val;
						vicitm = entry.getKey();
					}
				}
				cache.remove(vicitm);
				cacheCounter.remove(vicitm);
				cache.put(key, value);
				cacheCounter.put(key, counter);
				counter++;
			} else {
				cache.put(key, value);
				cacheCounter.put(key, counter);
				counter++;
			}
		}
	}

	class Node {
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
	private int capa, count;
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
		if (node != null) {
			int val = node.val;
			deleteNode(node);
			addToHead(node);
			return val;
		}
		return -1;
	}

	public void put2(int key, int value) {
		Node node = map.get(key);
		if (node != null) {
			node.val = value;
			deleteNode(node);
			addToHead(node);
		} else {
			Node node2 = new Node(key, value);
			map.put(key, node2);
			if (count < capa) {
				count++;
				addToHead(node2);
			} else {
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
		if (!LFU.containsKey(key))
			return -1;
		int val = LFU.get(key);
		int count = LFUCount.get(key);
		lists.get(count).remove(key);
		LFUCount.put(key, count + 1);
		if (count == min && lists.get(min).size() == 0)
			min++;
		if (lists.get(count + 1) == null)
			lists.put(count + 1, new LinkedHashSet<>());
		lists.get(count + 1).add(key);
		return val;
	}

	public void LFUput(int key, int value) {
		if (cap <= 0)
			return;
		if (LFU.containsKey(key)) {
			LFU.put(key, value);
			LFUget(key);
			return;
		}

		if (LFU.size() >= cap) {
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
		if (intervals.containsKey(left)) {
			Interval cur = intervals.get(left);
			cur.end = Math.max(cur.end, right);
			Map.Entry<Integer, Interval> high = intervals.higherEntry(cur.start);
			while (high != null) {
				if (high.getKey() > cur.end)
					break;
				intervals.remove(high.getKey());
				cur.end = Math.max(cur.end, high.getValue().end);
				high = intervals.higherEntry(cur.start);
			}
		} else {
			Interval cur = new Interval(left, right);
			Map.Entry<Integer, Interval> low = intervals.lowerEntry(left);
			if (low != null && low.getValue().end >= cur.start) {
				intervals.remove(left);
				cur.start = Math.min(cur.start, low.getValue().start);
				cur.end = Math.max(cur.end, low.getValue().end);
			}
			Map.Entry<Integer, Interval> high = intervals.higherEntry(left);
			while (high != null) {
				if (high.getKey() > cur.end)
					break;
				intervals.remove(high.getKey());
				cur.end = Math.max(cur.end, high.getValue().end);
				high = intervals.higherEntry(cur.start);
			}
			intervals.put(cur.start, cur);
		}
	}

	public boolean queryRange(int left, int right) {
		if (intervals.containsKey(left)) {
			Interval cur = intervals.get(left);
			return cur.end >= right;
		} else {
			Map.Entry<Integer, Interval> low = intervals.lowerEntry(left);
			return low == null ? false : low.getValue().end >= right;
		}
	}

	public void removeRange(int left, int right) {
		if (intervals.containsKey(left)) {
			Interval cur = intervals.get(left);
			while (cur != null) {
				if (cur.start >= right)
					break;
				intervals.remove(cur.start);
				if (right <= cur.end) {
					cur.start = right;
					intervals.put(cur.start, cur);
					break;
				} else {
					Map.Entry<Integer, Interval> high = intervals.higherEntry(cur.start);
					cur = high == null ? null : high.getValue();
				}
			}
		} else {
			Map.Entry<Integer, Interval> low = intervals.lowerEntry(left);
			if (low != null) {
				if (right < low.getValue().end) {
					intervals.put(right, new Interval(right, low.getValue().end));
				}
				low.getValue().end = Math.min(low.getValue().end, left);
			}
			Map.Entry<Integer, Interval> high = intervals.higherEntry(left);
			Interval cur = high == null ? null : high.getValue();
			while (cur != null) {
				if (cur.start >= right)
					break;
				intervals.remove(cur.start);
				if (right <= cur.end) {
					cur.start = right;
					intervals.put(cur.start, cur);
					break;
				} else {
					high = intervals.higherEntry(cur.start);
					cur = high == null ? null : high.getValue();
				}
			}
		}
	}

	class Trie {
		int weight = -1;
		int reserve_weight = -1;
		String word;
		String reserve;
		Trie[] next = new Trie[26];
	}

	private Trie root;
	private Trie reserve_root;

	public void WordFilter(String[] words) {
		root = new Trie();
		reserve_root = new Trie();
		for (int i = 0; i < words.length; i++) {
			String word = words[i];
			Trie node = root;
			for (char c : word.toCharArray()) {
				int k = c - 'a';
				if (node.next[k] == null)
					node.next[k] = new Trie();
				node = node.next[k];
			}
			node.weight = i;
			node.word = word;

			node = reserve_root;
			for (int j = word.length() - 1; j >= 0; j--) {
				char c = word.charAt(j);
				int k = c - 'a';
				if (node.next[k] == null)
					node.next[k] = new Trie();
				node = node.next[k];
			}
			node.reserve_weight = i;
			node.reserve = word;
		}
	}

	public int f(String prefix, String suffix) {
		Trie pre = root;
		for (char c : prefix.toCharArray()) {
			int i = c - 'a';
			pre = pre.next[i];
			if (pre == null)
				return -1;
		}
		Trie suf = reserve_root;
		for (int i = suffix.length() - 1; i >= 0; i--) {
			char c = suffix.charAt(i);
			int j = c - 'a';
			suf = suf.next[j];
			if (suf == null)
				return -1;
		}

		Set<String> set = new HashSet<>();
		prefix(pre, set);
		int[] res = new int[1];
		suffix(suf, set, res);
		return res[0];
	}

	private void prefix(Trie node, Set<String> set) {
		if (node == null)
			return;
		if (node.weight >= 0) {
			set.add(node.word);
		}
		for (int i = 0; i < 26; i++) {
			prefix(node.next[i], set);
		}
	}

	private void suffix(Trie node, Set<String> set, int[] res) {
		if (node == null)
			return;
		if (node.reserve_weight >= 0 && set.contains(node.reserve)) {
			if (node.reserve_weight > res[0])
				res[0] = node.reserve_weight;
		}
		for (int i = 0; i < 26; i++)
			suffix(node.next[i], set, res);
	}
	
	
	
	 interface NestedInteger {
		 
		      // @return true if this NestedInteger holds a single integer, rather than a nested list.
		      public boolean isInteger();
		 
		      // @return the single integer that this NestedInteger holds, if it holds a single integer
		      // Return null if this NestedInteger holds a nested list
		      public Integer getInteger();
		 
		      // @return the nested list that this NestedInteger holds, if it holds a nested list
		      // Return null if this NestedInteger holds a single integer
		      public List<NestedInteger> getList();
		  }
	
	
	
	 class NestedIterator implements Iterator<Integer> {

		 	private Stack<List<NestedInteger>> stack;
		 	private Stack<Integer> indexStack;
		 	private int index;
		    public NestedIterator(List<NestedInteger> nestedList) {
		        stack = new Stack<>();
		        indexStack = new Stack<>();
		        index = 0;
		        if(nestedList != null && nestedList.size() > 0) {
		        	stack.push(nestedList);
			        indexStack.push(index);
		        }
		    }

		    @Override
		    public Integer next() {
		        while(!stack.peek().get(index).isInteger()) {
		        	List<NestedInteger> list = stack.peek().get(index).getList();
		        	indexStack.push(index);
		        	index = 0;
		        	stack.push(list);
		        }
		        
		        
		        int next = stack.peek().get(index).getInteger();
		    	index++;
		    	while(!stack.isEmpty() && index >= stack.peek().size()) {
		    		index = indexStack.pop();
		    		stack.pop();
		    		index++;
		    	}
		    	return next;
		    }

		    @Override
		    public boolean hasNext() {
		    	while(!stack.isEmpty()  && !stack.peek().get(index).isInteger()) {
		        	List<NestedInteger> list = stack.peek().get(index).getList();
		        	if(list.size() == 0) {
		        		index++;
		        		while( !stack.isEmpty() && index >= stack.peek().size()) {
		        			stack.pop();
		        			index = indexStack.pop();
		        			index++;
		        		}
		        	}
		        	else {
		        		indexStack.push(index);
			        	index = 0;
			        	stack.push(list);
		        	}
		        	
		        }
		    	
		    	return !stack.isEmpty();
		    }
	
	
	 }
	
	 class Twitter {
		 private long timestamp;
		 class Tweet {
			 long timestamp;
			 int tweetId;
			 int userId;
			 public Tweet(long timestamp, int tweetId, int userId) {
				this.timestamp = timestamp;
				this.userId = userId;
				this.tweetId = tweetId;
			}
		 }
		 
		 private final Sorter sorter = new Sorter();
		 private final PriorityQueue<Tweet> priorityQueue = new PriorityQueue<>(sorter);
		 
	     private final Map<Integer, Integer> indexs = new HashMap<>();

		 
		 class Sorter implements Comparator<Tweet> {

			@Override
			public int compare(Tweet o1, Tweet o2) {
				if(o1.timestamp > o2.timestamp)
					return -1;
				else if(o1.timestamp < o2.timestamp)
					return 1;
				return 0;
			}
			 
		 }
		 
		 
		 private Map<Integer, LinkedList<Tweet>> users;
		 private Map<Integer, Set<Integer>> follows;
		 
	    /** Initialize your data structure here. */
	    public Twitter() {
	        timestamp = 0;
	        users = new HashMap<>();
	        follows = new HashMap<>();
	    }
	    
	    /** Compose a new tweet. */
	    public void postTweet(int userId, int tweetId) {
	        LinkedList<Tweet> list = users.get(userId);
	        if(list == null) {
	        	list = new LinkedList<>();
	        	users.put(userId, list);
	        	follows.put(userId, new HashSet<>());
	        	follows.get(userId).add(userId);
	        }
	        Tweet tweet = new Tweet(timestamp++, tweetId, userId);
	        list.addFirst(tweet);
	    }
	    
	    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
	    public List<Integer> getNewsFeed(int userId) {
	        List<Integer> feeds = new ArrayList<>();
	        if(!users.containsKey(userId))
	        	return feeds;
	        Set<Integer> set = follows.get(userId);
	    	int size = set.size();
	    	for(int id : set) {
	    		LinkedList<Tweet> tweets = users.get(id);
	    		if(tweets.size() > 0) {
		    		priorityQueue.offer(tweets.getFirst());
	    		}
	    		else {
	    			size--;
	    		}
	    	}
	    	while(!priorityQueue.isEmpty() && size > 0 && feeds.size() < 10) {
	    		Tweet tweet = priorityQueue.poll();
	    		feeds.add(tweet.tweetId);
	    		int uId = tweet.userId;
	    		int index = indexs.getOrDefault(uId, 0);
	    		index++;
	    		if(index >= users.get(uId).size())
	    			size--;
	    		else {
	    			indexs.put(uId, index);
	    			priorityQueue.offer(users.get(uId).get(index));
	    		}
	    			
	    	}
	        priorityQueue.clear();
	        indexs.clear();
	    	return feeds;
	    }
	    
	    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
	    public void follow(int followerId, int followeeId) {
	    	if(followerId == followeeId)
	    		return;
	    	LinkedList<Tweet> list = users.get(followerId);
	        if(list == null) {
	        	list = new LinkedList<>();
	        	users.put(followerId, list);
	        	follows.put(followerId, new HashSet<>());
	        	follows.get(followerId).add(followerId);
	        }
	        list = users.get(followeeId);
	        if(list == null) {
	        	list = new LinkedList<>();
	        	users.put(followeeId, list);
	        	follows.put(followeeId, new HashSet<>());
	        	follows.get(followeeId).add(followeeId);
	        }
	        follows.get(followerId).add(followeeId);
	    }
	    
	    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
	    public void unfollow(int followerId, int followeeId) {
	    	if(followerId == followeeId)
	    		return;
	    	LinkedList<Tweet> list = users.get(followerId);
	        if(list == null) {
	        	list = new LinkedList<>();
	        	users.put(followerId, list);
	        	follows.put(followerId, new HashSet<>());
	        	follows.get(followerId).add(followerId);
	        }
	        list = users.get(followeeId);
	        if(list == null) {
	        	list = new LinkedList<>();
	        	users.put(followeeId, list);
	        	follows.put(followeeId, new HashSet<>());
	        	follows.get(followeeId).add(followeeId);
	        }
	    	follows.get(followerId).remove(followeeId);
	    }
	}
	
	
	 class SummaryRanges {

		    /** Initialize your data structure here. */
		 	TreeMap<Integer, Interval> tree;
		 
		    public SummaryRanges() {
		        tree = new TreeMap<>();
		    }
		    
		    public void addNum(int val) {
		        Entry<Integer, Interval> pre = tree.floorEntry(val);
		        boolean preMerge = false;
		        if(pre != null) {
		        	Interval preInterval = pre.getValue();
		        	int end = preInterval.end;
		        	if(end >= val)
		        		return;
		        	if(end+1 == val) {
		        		preMerge = true;
		        		preInterval.end = val;
		        	}
		        }
		        Entry<Integer, Interval> next = tree.ceilingEntry(val);
		        if(next != null) {
		        	Interval nextInterval = next.getValue();
		        	int start = nextInterval.start;
		        	if(start <= val)
		        		return;
		        	if(val + 1 == start) {
		        		if(preMerge) {
		        			pre.getValue().end = nextInterval.end;
		        			tree.remove(next.getKey());
		        			return;
		        		}
		        		else {
		        			nextInterval.start = val;
		        			return;
		        		}
		        	}
		        }
		        if(!preMerge)
		        	tree.put(val, new Interval(val, val));
		    }
		    
		    public List<Interval> getIntervals() {
		        List<Interval> list = new ArrayList<>();
		        for(Entry<Integer, Interval> entry : tree.entrySet()) {
		        	list.add(entry.getValue());
		        }
		        return list;
		    }
		}
	 
	 class AllOne {
		 	
		 	int max = 0;
		 	int min = 0;
		 	TreeMap<Integer, Set<String>> counter;
		 	Map<String, Integer> values;

		    /** Initialize your data structure here. */
		    public AllOne() {
		        values = new HashMap<>();
		        counter = new TreeMap<>();
		        counter.put(0, new HashSet<>());
		    }
		    
		    /** Inserts a new key <Key> with value 1. Or increments an existing key by 1. */
		    public void inc(String key) {
		        int count = values.getOrDefault(key, 0) + 1;
		        if(count == 1)
		        	min = 1;
		        if(!counter.containsKey(count))
	        		counter.put(count, new HashSet<>());
	        	counter.get(count).add(key);	
	        	counter.get(count-1).remove(key);
	        	if(counter.get(count-1).isEmpty() && count != 1)
	        		counter.remove(count-1);
		        if(count > max)
		        	max = count;
		        if(counter.get(min) == null)
		        	min++;
		        values.put(key, count);
		    }
		    
		    /** Decrements an existing key by 1. If Key's value is 1, remove it from the data structure. */
		    public void dec(String key) {
		        if(!values.containsKey(key) || values.get(key) == 0)
		        	return;
		        int count = values.get(key) - 1;
		        counter.get(count+1).remove(key);
		        if(counter.get(count+1).isEmpty())
		        	counter.remove(count+1);
		        if(!counter.containsKey(count))
	        		counter.put(count, new HashSet<>());
	        	counter.get(count).add(key);
	        	if(count > 0 && count < min)
	        		min = count;
	        	values.put(key, count);	     
		        if(counter.get(max) == null)
		        	max--;
		        if(counter.get(min) == null)
		        	min--;
		        if(min == 0) {
		        	Integer nextMin = counter.higherKey(0);
		        	if(nextMin != null)
		        		min = nextMin;
		        }
		    }
		    
		    /** Returns one of the keys with maximal value. */
		    public String getMaxKey() {
		        if(max == 0)
		        	return "";
		    	return counter.get(max).iterator().next();
		    }
		    
		    /** Returns one of the keys with Minimal value. */
		    public String getMinKey() {
		        if(min == 0)
		        	return "";
		    	return counter.get(min).iterator().next();
		    }
		}
	 
	 
	static class FreqStack {
		int counter;
		Map<Integer, Integer> counter_map;
		TreeMap<Integer, PriorityQueue<int[]>> fre_map;
	    public FreqStack() {
	        counter_map = new HashMap<>();
	        fre_map = new TreeMap<>();
	    }
	    
	    public void push(int x) {
	        int fre = counter_map.getOrDefault(x, 0);
	        int next_fre = fre + 1;
	        counter_map.put(x, next_fre);
	        PriorityQueue<int[]> pq = fre_map.get(next_fre);
	        if(pq == null) {
	        	pq = new PriorityQueue<>((a,b) -> b[1]-a[1]);
	        	fre_map.put(next_fre, pq);
	        }
	        pq.add(new int[] {x, counter});
	        counter++;
	    }
	    
	    public int pop() {
	        Map.Entry<Integer, PriorityQueue<int[]>> entry = fre_map.lastEntry();
	        PriorityQueue<int[]> pq = entry.getValue();
	        int answer = pq.poll()[0];
	        if(pq.isEmpty()) {
	        	fre_map.remove(entry.getKey());
	        }
	    	return answer;
	    }
	}
	 
	 
	class RLEIterator {
		Deque<Integer> nums;
		Deque<Integer> fre;

	    public RLEIterator(int[] A) {
	        nums = new LinkedList<>();
	        fre = new LinkedList<>();
	        for(int i=0; i<A.length; i+=2) {
	        	int times = A[i];
	        	if(times == 0)
	        		continue;
	        	int num = A[i+1];
	        	nums.add(num);
	        	fre.add(times);
	        }
	    }
	    
	    public int next(int n) {
	    	int answer = -1;
	        while(!nums.isEmpty() && n > 0) {
	        	answer = nums.pollFirst();
	        	int time = fre.pollFirst();
	        	int min = Math.min(time, n);
	        	time -= min;
	        	n -= min;
	        	if(time != 0) {
	        		nums.addFirst(answer);
	        		fre.addFirst(time);
	        	}
	        }
	        if(n != 0)
	        	return -1;
	    	return answer;
	    }
	}
	 
	 
	 
	 
	
	
	

	public static void main(String[] args) {
		
	}

}
