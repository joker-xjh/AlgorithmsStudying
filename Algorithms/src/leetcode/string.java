package leetcode;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

public class string {
	
	
    public boolean judgeCircle(String moves) {
        if(moves == null || moves.length()==0)
        	return false;
        int x = 0, y = 0;
        for(char c:moves.toCharArray()) {
        	if( c == 'L') {
        		x--;
        	}
        	else if(c =='R') {
        		x++;
        	}
        	else if(c == 'U') {
        		y++;
        	}
        	else {
        		y--;
        	}
        }
        if(x == 0 && y == 0)
        	return true;
        
        
        return false;
    }
    
    
    public String getHint(String secret, String guess) {
        int a = 0, b = 0;
        char[] secretArray = secret.toCharArray();
        char[] guessArray = guess.toCharArray();
        int length = secret.length();
        int[] map = new int[10];
        for(int i=0; i<length; i++) {
        	char c1 = secretArray[i];
        	char c2 = guessArray[i];
        	if(c1 == c2) {
        		a++;
        	}
        	else
        		map[c1-'0']++;
        }
        	
        for(int i=0; i<length;i ++) {
        	char c1 = guessArray[i];
        	char c2 = secretArray[i];
        	if(c1 != c2) {
        		int count = map[c1-'0'];
        		if(count > 0) {
        			b++;
        			map[c1-'0']--;
        		}
        	}
        	
        }
        
        return a+"A"+b+"B";
    }
    
    
    public boolean validPalindrome(String s) {
        if(s== null || s.length() <2)
        	return true;
        char[] array = s.toCharArray();
    	int left =0, right = array.length-1;
    	while(left <= right) {
    		char c1 = array[left];
    		char c2 = array[right];
    		if(c1 != c2) {
    			
    			return validPalindrome(array, left+1,right) || validPalindrome(array, left, right-1);
    		}
    		
    		left++;
    		right--;
    	}
    	
    	
    	
    	return true;
    }
    
    private boolean validPalindrome(char[] array, int left, int right) {
    	while(left <= right) {
    		char c1 = array[left];
    		char c2 = array[right];
    		if(c1 != c2)
    			return false;
    		left++;
    		right--;
    	}
    	return true;
    }
    
    
    
    class Trie{
    	int val;
    	Trie[] next = new Trie[26];
    }
    
    private Trie root;
    
    public void MapSum() {
        root = new Trie();
    }
    
    public void insert(String key, int val) {
        Trie node = root;
        for(char c:key.toCharArray()) {
        	int i = c-'a';
        	if(node.next[i] == null)
        		node.next[i] = new Trie();
        	node = node.next[i];
        }
        node.val = val;
    }
    
    public int sum(String prefix) {
        int[] sum = new int[1];
    	Trie node = getNode(root, prefix.toCharArray(), 0);
    	sum(node, sum);
    	return sum[0];
    }
    
    private void sum(Trie node, int[] sum) {
    	if(node == null)
    		return;
    	sum[0] += node.val;
    	for(int i=0; i<26; i++)
    		sum(node.next[i],sum);
    }
    
    
    private Trie getNode(Trie node, char[] array, int index) {
    	if(node == null )
    		return null;
    	if(index == array.length)
    		return node;
    	char c = array[index];
    	node = node.next[c-'a'];
    	return getNode(node, array, index+1);
    }
    
    
    public boolean checkValidString(String s) {
        if(s==null || s.length()==0)
        	return true;
    	Stack<Integer> left = new Stack<>();
    	Stack<Integer> mid = new Stack<>();
    	char[] array = s.toCharArray();
    	for(int i=0; i<array.length; i++) {
    		char c = array[i];
    		if(c=='(')
    			left.push(i);
    		else if(c == '*')
    			mid.push(i);
    		else {
    			if(left.size() > 0) {
    				left.pop();
    			}
    			else if(mid.size() >0) {
    				mid.pop();
    			}
    			else {
					return false;
				}
    		}
    	}
    	if(left.size()==0)
    		return true;
    	while(mid.size() > 0) {
    		int index = mid.pop();
    		int pos = left.peek();
    		if(pos < index)
    			left.pop();
    		if(left.size()==0)
        		return true;
    	}
    	if(left .size() ==0)
    		return true;
    	return false;
    }
    
    
    private int max = Integer.MAX_VALUE;
    private char[] result = new char[4];
    public String nextClosestTime(String time) {
        char[] array = new char[4];
        array[0] = time.charAt(0);
        array[1] = time.charAt(1);
        array[2] = time.charAt(3);
        array[3] = time.charAt(4);
        List<Character> list = new ArrayList<>();
        nextClosestTime(list, array);
        StringBuilder sb = new StringBuilder();
        sb.append(result[0]).append(result[1]).append(':').append(result[2]).append(result[3]);
        max = Integer.MAX_VALUE;
        result = new char[4];
    	return sb.toString();
    }
    
    private void nextClosestTime(List<Character> list, char[] array) {
    	if(list.size() == 4) {
    		if(judgeTime(list)) {
    			char[] another = listToCharArray(list);
    			int diff = getTimeDiff(array, another);
    			System.out.println(list  +"  diff: "+diff);
    			if(diff < max) {
    				max = diff;
    				for(int i=0; i<4; i++) {
    					result[i] = another[i];
    				}
    			}
    		}
    	}
    	else {
    		for(int i=0; i<4; i++) {
    			char c = array[i];
    			list.add(c);
    			nextClosestTime(list, array);
    			list.remove(list.size()-1);
    		}
    	}
    }
    
    private boolean judgeTime(List<Character> list) {
    	char one = list.get(0);
    	if(one >='3')
    		return false;
    	char two = list.get(1);
    	if(one =='2' && two >= '4')
    		return false;
    	char three = list.get(2);
    	if(three >= '6')
    		return false;
    	return true;
    }
    
    private char[] listToCharArray(List<Character> list) {
    	char[] array = new char[4];
    	for(int i=0; i<4; i++) {
    		array[i] = list.get(i);
    	}
    	return array;
    }
    
    
    
    public int getTimeDiff(char[] time1, char[] time2) {
    	int diff = 0;
    	boolean small = false;
    	for(int i=0; i<4; i++) {
    		char c1 = time1[i];
    		char c2 = time2[i];
    		if(c1 < c2) {
    			small = true;
    			break;
    		}
    		else if(c1 > c2) {
    			break;
    		}
    	}
    	if(small) {
    		diff = getDiffToMidnight(time1) - getDiffToMidnight(time2);
    	}
    	else {
    		diff = getDiffToMidnight(time1) + getDiffToZero(time2);
    	}
    	
    	return diff;
    }
    
    
    private int getDiffToMidnight(char[] time) {
    	int diff = 0;
    	diff += 60 - Integer.parseInt((""+time[2]+time[3]));
    	diff += (23 - Integer.parseInt((""+time[0]+time[1])))*60;
    	return diff;
    }
    
    private int getDiffToZero(char[] time) {
    	int diff = 0;
    	diff += Integer.parseInt((""+time[2]+time[3]));
    	diff += (Integer.parseInt((""+time[0]+time[1]))) * 60; 
    	return diff;
    }
    
    
    public int repeatedStringMatch(String A, String B) {
    	if(A== null || B== null || A.length()==0|| B.length() ==0)
    		return -1;
        Set<Character> set = new HashSet<>();
        for(char c:A.toCharArray()) {
        	set.add(c);
        }
        for(char c : B.toCharArray()) {
        	if(!set.contains(c))
        		return -1;
        }
        int lengthA = A.length();
        int lengthB = B.length();
        if(lengthB < lengthA) {
        	 int first = A.indexOf(B);
             if(first !=-1)
             	return 1;
        	String temp = A+A;
        	int index = temp.indexOf(B);
        	if(index<0)
        		return -1;
        	return 2;
        }
        int times = lengthB / lengthA;
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<times; i++) {
        	sb.append(A);
        }
        String temp = sb.toString();
        int index = temp.indexOf(B);
        if(index >=0) {
        	return times;
        }
        temp += A;
        index = temp.indexOf(B);
        if(index >=0) {
        	return times+1;
        }
        else {
        	return -1;
        }
        
    }
    
    
    static class Sorter implements Comparator<String> {
    	private Map<String, Integer> map;
    	
    	public Sorter(Map<String, Integer> map) {
			this.map = map;
		}

		@Override
		public int compare(String o1, String o2) {
			int count1 = map.get(o1);
			int count2 = map.get(o2);
			if(count1 > count2)
				return 1;
			else if(count1 < count2)
				return -1;
			else {
				int cmp = o1.compareTo(o2);
				if(cmp < 0)
					return 1;
				else if(cmp > 0)
					return -1;
				else
					return 1;
			}
		}
    	
    }
    
    
    public List<String> topKFrequent(String[] words, int k) {
        List<String> list = new LinkedList<>();
        Map<String, Integer> map = new HashMap<>();
        for(String word : words)
        	map.put(word, map.getOrDefault(word, 0)+1);
        PriorityQueue<String> queue = new PriorityQueue<>(new Sorter(map));
        Set<String> set = new HashSet<>();
        int index = 0;
        while(queue.size() < k) {
        	String word = words[index++];
        	if(!set.contains(word)) {
        		queue.add(word);
        		set.add(word);
        	}
        }
        
        for(int i=index; i<words.length; i++) {
        	String word = words[i];
        	if(set.contains(word))
        		continue;
        	set.add(word);
        	int fre1 = map.get(word);
        	String peek = queue.peek();
        	int fre2 = map.get(peek);
        	if(fre1 < fre2)
        		continue;
        	else if(fre1 > fre2) {
        		queue.poll();
        		queue.add(word);
        	}
        	else {
        		int cmp = word.compareTo(peek);
        		if(cmp <0) {
        			queue.poll();
            		queue.add(word);
        		}
        	}
        }
        while(!queue.isEmpty())
        	list.add(0, queue.poll());
        return list;
    }
    
    
    public int compress(char[] chars) {
        int compress = 0;
        if(chars == null || chars.length == 0)
        	return compress;
        StringBuilder sb = new StringBuilder();
        char cmp = chars[0];
        int count = 1;
        for(int i=1; i<chars.length; i++) {
        	char c = chars[i];
        	if(c == cmp) {
        		count++;
        	}
        	else {
        		sb.append(cmp);
        		if(count > 1)
        			sb.append(count);
        		cmp = c;
        		count = 1;
        	}
        }
        sb.append(cmp);
		sb.append(count);
		String temp = sb.toString();
		int length = temp.length();
		if(length > chars.length)
			return chars.length;
        compress = length;
        for(int i=0; i<length; i++)
        	chars[i] = temp.charAt(i);
        return compress;
    }
    
    private static class Node{
    	boolean b;
    	Node[] next = new Node[26];
    }
    
    public String longestWord(String[] words) {
        if(words == null || words.length == 0)
        	return "";
        Node root = new Node();
        for(String word : words) {
        	Node node = root;
        	for(char c : word.toCharArray()) {
        		int i = c - 'a';
        		if(node.next[i] == null)
        			node.next[i] = new Node();
        		node = node.next[i];
        	}
        	node.b = true;
        }
        root.b = true;
    	List<Character> list = new ArrayList<>();
    	List<Character> target = new ArrayList<>();
    	longestWord(root, list, target);
    	StringBuilder sb = new StringBuilder();
    	for(char c:target)
    		sb.append(c);
    	return sb.toString();
    }
    
    private void longestWord(Node node, List<Character> list, List<Character> target) {
    	for(int i=0; i<26; i++) {
    		Node next = node.next[i];
    		if(next == null || !next.b) {
        		if(list.size() > target.size()) {
        			target.clear();
        			target.addAll(list);
        		}
        		else if(list.size() == target.size()) {
        			for(int j=0; j<target.size(); j++) {
        				char c1 = list.get(j);
        				char c2 = target.get(j);
        				if(c1 < c2) {
        					target.clear();
        					target.addAll(list);
        					break;
        				}
        				else if(c1 > c2)
        					break;
        				
        			}
        		}
        		System.out.println(target);
        		return;
        	}
    		char c = (char)('a'+i);
    		list.add(c);
    		longestWord(node.next[i], list, target);
    		list.remove(list.size()-1);
    	}
    		
    	
    }
    
    
    public List<String> removeComments(String[] source) {
        List<String> list = new ArrayList<>();
        if(source == null || source.length == 0)
        	return list;
        StringBuilder sb = new StringBuilder();
        boolean blocked = false;
        for(String line : source) {
        	char[] array = line.toCharArray();
        	for(int i=0; i<array.length; i++) {
        		char c = array[i];
        		if(c == '/' && i<array.length-1 && !blocked) {
        			char next = array[i+1];
        			if(next == '/') {
        				String temp = sb.toString();
                		if(temp.length() > 0)
                			list.add(temp);
                		sb = new StringBuilder();
        				break;
        			}
        			else if(next == '*') {
        				blocked = true;
        				i++;
        			}
        			else {
        				sb.append(c);
        			}
        		}
        		else if(c == '*' && blocked && i<array.length-1) {
        			char next = array[i+1];
        			if(next == '/') {
        				blocked = false;
        				i++;
        			}
        		}
        		else {
        			if(!blocked)
        				sb.append(c);
        		}
        	}
        	if(!blocked) {
        		String temp = sb.toString();
        		if(temp.length() > 0)
        			list.add(temp);
        		sb = new StringBuilder();
        	}
        }
        
        return list;
    }
    
    public int findMinDifference(List<String> timePoints) {
        Collections.sort(timePoints);
    	int diff = Integer.MAX_VALUE;
    	for(int i=1; i<timePoints.size(); i++) {
    		String before = timePoints.get(i-1);
    		String after = timePoints.get(i);
    		int temp = getTimeDiff(before, after);
    		if(temp < diff)
    			diff = temp;
    	}
    	int last = 1440 - getTimeDiff(timePoints.get(0), timePoints.get(timePoints.size()-1));
    	if(last < diff)
    		diff = last;
    	return diff;
    }
    
    private int getTimeDiff(String before, String after) {
    	int min1 = getMinutes(before);
    	int min2 = getMinutes(after);
    	return min2 - min1;
    }
    
    private int getMinutes(String time) {
    	int minutes = 0;
    	minutes = time.charAt(4) + time.charAt(3) * 10 + time.charAt(1) * 60 + time.charAt(0) * 10 * 60;
    	return minutes;
    }
   
    
    public int findMinDifference2(List<String> timePoints) {
    	boolean[] minutes = new boolean[24 * 60];
    	for(String time : timePoints) {
    		char[] array = time.toCharArray();
    		int hour = (array[0] - '0') * 10 + array[1] -'0';
    		int minute = (array[3] - '0') * 10 + array[4] - '0';
    		int index = hour * 60 + minute;
    		if(minutes[index])
    			return 0;
    		minutes[index] = true;
    	}
    	int min = Integer.MAX_VALUE;
    	int pre = 0;
    	for(int i=0; i<minutes.length; i++) {
    		if(minutes[i]) {
    			if(pre != 0) {
    				min = Math.min(min, i-pre);
    			}
    			pre = i;
    		}
    	}
    	int dist = 1;
    	int first = 0;
    	while(!minutes[first]) {
    		first++;
    		dist++;
    	}
    	int last = minutes.length-1;
    	while(!minutes[last]) {
    		last--;
    		dist++;
    	}
    	return Math.min(min, dist);
    }
    
    
    public String countOfAtoms(String formula) {
        Map<String, Integer> map = countOfAtomsHelp(formula);
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, Integer> entry : map.entrySet()) {
        	String atom = entry.getKey();
        	Integer count = entry.getValue();
        	sb.append(atom);
        	if(count != 1)
        		sb.append(count);
        }
    	return sb.toString();
    }
    
    private Map<String, Integer> countOfAtomsHelp(String formula){
    	Map<String, Integer> map = new TreeMap<>();
    	StringBuilder sb = new StringBuilder();
    	for(int i=0; i<formula.length(); i++) {
    		char c = formula.charAt(i);
    		if(c >= 'A' && c <= 'Z') {
    			sb.append(c);
    			if(i < formula.length() -1) {
    				char next = formula.charAt(i+1);
    				if(next>='A' && next<='Z') {
        				String atom = sb.toString();
        				sb = new StringBuilder();
            			map.put(atom, map.getOrDefault(atom, 0) + 1);
        			}
    			}
    			else if(i == formula.length() -1) {
    				String atom = sb.toString();
        			map.put(atom, map.getOrDefault(atom, 0) + 1);
    			}
    		}
    		else if( c >= 'a' && c <= 'z') {
    			sb.append(c);
    			if(i <formula.length()-1) {
    				char next = formula.charAt(i+1);
    				if(next >= '0' && next <='9') {
    					String atom = sb.toString();
    					sb = new StringBuilder();
    					i++;
    					StringBuilder num = new StringBuilder();
    	    			while( i < formula.length() && (formula.charAt(i) >= '0' && formula.charAt(i) <= '9')) {
    	    				num.append(formula.charAt(i));
    	    				i++;
    	    			}
    	    			if( i < formula.length() && !(formula.charAt(i) >= '0' && formula.charAt(i) <= '9')) {
    	    				i--;
    	    			}
    	    			int count = Integer.parseInt(num.toString());
    	    			
    	    			map.put(atom, map.getOrDefault(atom, 0) + count);
    				}
    				
    				else if(next >='A' && next <='Z') {
    					String atom = sb.toString();
    					sb = new StringBuilder();
    					map.put(atom, map.getOrDefault(atom, 0) + 1);
    				}
    			}
    			else if(i == formula.length() -1) {
    				String atom = sb.toString();
					sb = new StringBuilder();
					map.put(atom, map.getOrDefault(atom, 0) + 1);
    			}
    		}
    		else if(c == '(') {
    			
    			if(sb.length() > 0) {
    				String atom = sb.toString();
    				sb = new StringBuilder();
        			map.put(atom, map.getOrDefault(atom, 0) + 1);
    			}
    			int parentheses = 1;
    			int j = i+1;
    			while( parentheses != 0) {
    				char ch = formula.charAt(j);
    				if(ch == '(')
    					parentheses++;
    				else if(ch == ')')
    					parentheses--;
    				if(parentheses == 0)
    					break;
    				j++;
    			}
    			
    			String sub = formula.substring(i+1, j);
    			i = j;
    			int count = 1;
    			i++;
    			StringBuilder num = new StringBuilder();
    			while( i < formula.length() && (formula.charAt(i) >= '0' && formula.charAt(i) <= '9')) {
    				num.append(formula.charAt(i));
    				i++;
    			}
    			if( i < formula.length() && !(formula.charAt(i) >= '0' && formula.charAt(i) <= '9')) {
    				i--;
    			}
    			if(num.length() > 0)
    				count = Integer.parseInt(num.toString());
    			Map<String, Integer> subMap = countOfAtomsHelp(sub.toString());
    			for(String atom : subMap.keySet()) {
    				map.put(atom, map.getOrDefault(atom, 0) + subMap.get(atom) * count);
    			}
    		}
    		else if(c >='0' && c <='9') {
    			StringBuilder num = new StringBuilder();
    			while( i < formula.length() && (formula.charAt(i) >= '0' && formula.charAt(i) <= '9')) {
    				num.append(formula.charAt(i));
    				i++;
    			}
    			if( i < formula.length() && !(formula.charAt(i) >= '0' && formula.charAt(i) <= '9')) {
    				i--;
    			}
    			int count = Integer.parseInt(num.toString());
    			String atom = sb.toString();
    			sb = new StringBuilder();
    			map.put(atom, map.getOrDefault(atom, 0) + count);
    		}
     	}
    	return map;
    }
    
    private static final String IPV4 = "IPv4"; 
    private static final String IPV6 = "IPv6";
    private static final String Neither = "Neither";
    public String validIPAddress(String IP) {
        if(IP == null || IP.length() == 0)
        	return Neither;
        if(IP.contains(".")) {
        	return validIPv4Address(IP);
        }
        else if(IP.contains(":")) {
        	return validIPv6Address(IP);
        }
    	return Neither;
    }
    
    private String validIPv4Address(String ipv4) {
    	int count = 0;
    	for(char c:ipv4.toCharArray())
    		if(c == '.')
    			count++;
    	if(count != 3)
    		return Neither;
    	String[] array = ipv4.split("\\.");
    	if(array.length == 4) {
    		for(int i=0; i<4; i++) {
    			String temp = array[i];
    			if(temp.length() == 0)
    				return Neither;
    			Integer num = null;
    			try {
					num = Integer.parseInt(temp);
				} catch (Exception e) {
					return Neither;
				}
    			if(!(num >=0 && num <=255))
    				return Neither;
    			char first = temp.charAt(0);
    			if(first == '0' && temp.length() > 1)
    				return Neither;
    			for(char c : temp.toCharArray()) {
    				if(!(c>='0' && c<='9'))
    					return Neither;
    			}
    		}
    		return IPV4;
    	}
    	return Neither;
    }
    
    private String validIPv6Address(String ipv6) {
    	int count = 0;
    	for(char c : ipv6.toCharArray())
    		if(c == ':')
    			count++;
    	if(count != 7)
    		return Neither;
    	String[] array = ipv6.split(":");
		if(array.length == 8) {
			for(int i=0; i<8; i++) {
				String temp = array[i];
				if(temp.length() == 0 || temp.length() > 4)
					return Neither;
				try {
					 Integer.parseInt(temp, 16);
				} catch (Exception e) {
					return Neither;
				}
				for(char c:temp.toCharArray()) {
					if(!((c>='a' && c<='z') || (c>='A' && c<='Z') || (c>='0' && c<='9')))
						return Neither;
				}
			}
			return IPV6;
		}
		return Neither;
    }
    
    
    
    public boolean areSentencesSimilar(String[] words1, String[] words2, String[][] pairs) {
        if(words1.length != words2.length)
        	return false;
        Map<String, Set<String>> map = new HashMap<>();
        for(String[] pair : pairs) {
        	if(map.get(pair[0]) == null)
        		map.put(pair[0], new HashSet<>());
        	map.get(pair[0]).add(pair[1]);
        }
        int len = words1.length;
        for(int i=0; i<len; i++) {
        	String item1 = words1[i];
        	String item2 = words2[i];
        	if(item1.equals(item2))
        		continue;
        	Set<String> another1 = map.get(item1);
        	Set<String> another2 = map.get(item2);
        	if(another1 != null && another1.contains(item2))
        		continue;
        	if(another2 != null && another2.contains(item1))
        		continue;
        	return false;
        }
    	return true;
    }
    
    
    public boolean areSentencesSimilarTwo(String[] words1, String[] words2, String[][] pairs) {
        if(words1.length != words2.length)
        	return false;
        Map<String, Set<String>> map = new HashMap<>();
        for(String[] pair : pairs) {
        	if(!map.containsKey(pair[0]))
        		map.put(pair[0], new HashSet<>());
        	if(!map.containsKey(pair[1]))
        		map.put(pair[1], new HashSet<>());
        	map.get(pair[0]).add(pair[1]);
        	map.get(pair[1]).add(pair[0]);
        }
    	for(int i=0, len=words1.length; i<len; i++) {
    		if(words1[i].equals(words2[i]))
    			continue;
    		if(!map.containsKey(words1[i]))
    			return false;
    		if(!areSentencesSimilarTwoHelp(words1[i], words2[i], map, new HashSet<>()))
    			return false;
    	}
        
        
    	return true;
    }
    
    private boolean areSentencesSimilarTwoHelp(String source, String target, Map<String, Set<String>> map, Set<String> used) {
    	if(map.get(source).contains(target))
    		return true;
    	used.add(source);
    	for(String next : map.get(source)) {
    		if(!used.contains(next) && areSentencesSimilarTwoHelp(next, target, map, used))
    			return true;
    	}
    	return false;
    }
    
    
    class Account {
    	String email;
    	String name;
    	List<Account> neighbors;
    	
    	public Account(String email, String name) {
			this.email = email;
			this.name = name;
			neighbors = new ArrayList<>();
		}
    }
    
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        List<List<String>> result = new ArrayList<>();
        Map<String, Account> map = new HashMap<>(); 
        for(List<String> list : accounts) {
        	String name = list.get(0);
        	for(int i=1; i<list.size(); i++) {
        		String email = list.get(i);
        		if(!map.containsKey(email)) 
        			map.put(email, new Account(email, name));
        		if(i == 1)
        			continue;
        		String preEmail = list.get(i-1);
        		map.get(preEmail).neighbors.add(map.get(email));
        		map.get(email).neighbors.add(map.get(preEmail));
        	}
        }
        
        Set<String> visited = new HashSet<>();
        for(String email : map.keySet()) {
        	if(visited.add(email)) {
        		List<String> list = new LinkedList<>();
        		list.add(email);
        		accountsMergeDFS(map.get(email), visited, list);
        		Collections.sort(list);
        		list.add(0, map.get(email).name);
        		result.add(list);
        	}
        }
        return result;
    }
    
    private void accountsMergeDFS(Account root, Set<String> visited, List<String> list) {
    	for(Account other : root.neighbors) {
    		if(visited.add(other.email)) {
    			list.add(other.email);
    			accountsMergeDFS(other, visited, list);
    		}
    	}
    }
    
    public String convert(String s, int numRows) {
        char[] array = s.toCharArray();
        StringBuilder[] sbs = new StringBuilder[numRows];
        for(int i=0; i<numRows; i++)
        	sbs[i] = new StringBuilder();
        int index = 0;
        int length = array.length;
        while(index < length) {
        	for(int i=0; i<numRows && index<length; i++)
        		sbs[i].append(array[index++]);
        	for(int i=numRows-2; i>=1 && index<length; i--)
        		sbs[i].append(array[index++]);
        }
        for(int i=1; i<numRows; i++)
        	sbs[0].append(sbs[i]);
        return sbs[0].toString();
    }
    
    
    String[] nums = {
    		"","One","Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten",
    		"Eleven","Twelve","Thirteen","Fourteen","Fifteen","Sixteen","Seventeen","Eighteen","Ninetten",
    		"Twenty"
    };
    String[] tens = {
    		"","ten","Twenty","Thirty","Forty","Fifty","Sixty","Seventy","Eighty","Ninety"
    };
    
    public String numberToWords(int num) {
    	if(num == 0)
    		return "Zero";
        String str = String.valueOf(num);
        while(str.length() < 10) {
        	str = "0" + str;
        }
        char[] array = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        if(array[0] != '0')
        	sb.append(nums[array[0] - '0']).append(" ").append("Billion");
        String temp = numberToWords(array, 1);
        if(temp.length() != 0) {
        	if(sb.length() != 0)
        		sb.append(" ");
        	sb.append(temp).append(" ").append("Million");
        }
        temp = numberToWords(array, 4);
        if(temp.length() != 0) {
        	if(sb.length() != 0)
        		sb.append(" ");
        	sb.append(temp).append(" ").append("Thousand");
        }
        temp = numberToWords(array, 7);
        if(temp.length() != 0) {
        	if(sb.length() != 0)
        		sb.append(" ");
        	sb.append(temp);
        }
    	return sb.toString();
    }
    
    private String numberToWords(char[] array, int from) {
    	StringBuilder sb = new StringBuilder();
    	char one = array[from];
    	char two = array[from+1];
    	char three = array[from+2];
    	if(one != '0') {
    		sb.append(nums[one - '0']).append(" ").append("Hundred");
    	}
    	if(two != '0') {
    		if(sb.length() > 0)
    			sb.append(" ");
    		if(three == '0') {
    			sb.append(tens[two - '0']);
    		}
    		else {
    			if(two == '1') {
    				int temp = (two - '0') * 10 + (three - '0');
    				sb.append(nums[temp]);
    				return sb.toString();
    			}
    			else {
    				sb.append(tens[two - '0']);
    			}
    		}
    	}
    	if(three != '0') {
    		if(sb.length() > 0)
    			sb.append(" ");
    		sb.append(nums[three - '0']);
    	}
    	return sb.toString();
    }
    
    public String shortestCompletingWord(String licensePlate, String[] words) {
        String answer = null;
        licensePlate = licensePlate.toLowerCase();
        int[] target = new int[26];
        for(char c : licensePlate.toCharArray()) {
        	if(c >= 'a' && c <= 'z') {
        		target[c - 'a']++;
        	}
        }
        int[] buf = new int[26];
        for(int i=words.length-1; i>=0; i--) {
        	String word = words[i];
        	char[] array = word.toCharArray();
        	for(char c:array) {
        		buf[c - 'a']++;
        	}
        	if(shortestCompletingWord(target, buf)) {
        		if(answer == null || word.length() <= answer.length())
        			answer = word;
        	}
        	Arrays.fill(buf, 0);
        }
        return answer;
    }
    
    private boolean shortestCompletingWord(int[] target, int[] buf) {
    	for(int i=0; i<26; i++) {
    		if(target[i] == 0)
    			continue;
    		if(target[i] > buf[i])
    			return false;
    	}
    	
    	return true;
    }
    
    class Ladder {
    	String word;
    	Ladder[] next = new Ladder[26];
    }
    
    
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Ladder root = new Ladder();
        for(String word : wordList) {
        	Ladder node = root;
        	for(char c : word.toCharArray()) {
        		int i = c - 'a';
        		if(node.next[i] == null)
        			node.next[i] = new Ladder();
        		node = node.next[i];
        	}
        	node.word = word;
        }
        if(ladderContains(root, endWord.toCharArray()) == null)
        	return 0;
        int length = 1;
        Set<String> used = new HashSet<>();
        used.add(beginWord);
        Queue<String> queue = new LinkedList<>();
        queue.add(beginWord);
        while(!queue.isEmpty()) {
        	int size = queue.size();
        	length++;
        	for(int i=0; i<size; i++) {
        		String word = queue.poll();
        		char[] array = word.toCharArray();
        		for(int j=0; j<array.length; j++) {
        			char old = array[j];
        			for(char c='a'; c<='z'; c++) {
        				array[j] = c;
        				String next = ladderContains(root, array);
        				if(next != null) {
        					if(used.contains(next))
        						continue;
        					used.add(next);
        					queue.add(next);
        					if(endWord.equals(next))
        						return length;
        				}
        			}
        			array[j] = old;
        		}
        	}
        }
    	
    	return 0;
    }
    
    private String ladderContains(Ladder node, char[] word) {
    	for(int i=0; i<word.length; i++) {
    		if(node == null)
    			return null;
    		node = node.next[word[i] - 'a'];
    	}
    	if(node == null)
    		return null;
    	return node.word;
    }
    
    public String simplifyPath(String path) {
        if(path == null || path.length() == 0)
        	return "/";
        String[] array = path.split("/");
        LinkedList<String> list = new LinkedList<>();
        for(int i=0; i<array.length; i++) {
        	String item = array[i];
        	if(item.equals("") || item.equals("."))
        		continue;
        	else if(item.equals("..")) {
        		if(list.isEmpty())
        			continue;
        		list.pollLast();
        		continue;
        	}
        	list.addLast(item);
        }
        if(list.isEmpty())
        	return "/";
        StringBuilder sb = new StringBuilder();
        while(!list.isEmpty()) {
        	sb.append('/').append(list.pollFirst());
        }
    	return sb.toString();
    }
    
    public double[] calcEquation(String[][] equations, double[] values, String[][] queries) {
        Map<String, List<String>> graph = new HashMap<>();
        Map<String, Double> map = new HashMap<>();
        for(int i=0; i<equations.length; i++) {
        	String[] equation = equations[i];
        	String A = equation[0], B = equation[1];
        	double value = values[i];
        	List<String> list = graph.get(A);
        	if(list == null) {
        		list = new ArrayList<>();
        		graph.put(A, list);
        	}
        	list.add(B);
        	map.put(A+B, value);
        	
        	list = graph.get(B);
        	if(list == null) {
        		list = new ArrayList<>();
        		graph.put(B, list);
        	}
        	list.add(A);
        	map.put(B+A, 1/value);
        }
        int length = queries.length;
        double[] answer = new double[length];
        double[] val = new double[1];
        boolean[] find = new boolean[1];
        Set<String> visited = new HashSet<>();
    	for(int i=0; i<length; i++) {
    		String[] query = queries[i];
    		String A = query[0] , B = query[1];
    		if(!graph.containsKey(A) || !graph.containsKey(B)) {
    			answer[i] = -1.0;
    			continue;
    		}
    		if(A.equals(B)) {
    			answer[i] = 1.0;
    			continue;
    		}
    		calcEquationDFS(graph, A, B, 1, val, find, map, visited);
    		if(find[0]) {
    			answer[i] = val[0];
    		}
    		else {
    			answer[i] = -1.0;
    		}
    		find[0] = false;
			val[0] = 0.0;
			visited.clear();
    	}
        
    	return answer;
    }
    
    private void calcEquationDFS(Map<String, List<String>> graph, String point,String target, double cur, double[] val, boolean[] find, Map<String, Double> map, Set<String> visited) {
    	if(find[0])
    		return;
    	List<String> list = graph.get(point);
    	for(String next : list) {
    		if(visited.contains(next))
    			continue;
    		visited.add(next);
    		double temp = map.get(point + next);
    		if(next.equals(target)) {
    			val[0] = cur * temp;
    			find[0] = true;
    			return;
    		}
    		else {
    			calcEquationDFS(graph, next, target, cur * temp, val, find, map, visited);
    		}
    	}
    	
    }
    
    public String licenseKeyFormatting(String S, int K) {
        char[] array = S.toCharArray();
        StringBuilder buffer = new StringBuilder(array.length);
        int counter = 0;
        for(int i=array.length-1; i>=0; i--) {
        	char c = array[i];
        	if(c == '-')
        		continue;
        	if(c >='a' && c <= 'z')
        		c -= 32;
        	buffer.append(c);
        	counter++;
        	if(counter == K && i != 0) {
        		counter = 0;
        		buffer.append('-');
        	}
        }
        String answer = buffer.reverse().toString();
        if(answer.length() == 0)
        	return answer;
        if(answer.charAt(0) == '-')
        	answer = answer.substring(1);
    	return answer;
    }
    
    class Lock {
    	String lock = null;
    	Lock[] next = new Lock[10];
    }
    
    public int openLock(String[] deadends, String target) {
        if(target == null || target.length() == 0)
        	return -1;
        Lock root = new Lock();
        for(String deadend : deadends) {
        	Lock lock = root;
        	for(char c : deadend.toCharArray()) {
        		int i = c - '0';
        		if(lock.next[i] == null)
        			lock.next[i] = new Lock();
        		lock = lock.next[i];
        	}
        	lock.lock = deadend;
        }
        char[] start = "0000".toCharArray();
        char[] end = target.toCharArray();
        if(lockContains(root, start))
        	return -1;
        int step = 0;
        Queue<char[]> queue = new LinkedList<>();
        Lock used = new Lock();
        queue.add(start);
        used = addLock(used, start);
        while(!queue.isEmpty()) {
        	int size = queue.size();
        	step++;
        	for(int i=0; i<size; i++) {
        		char[] lock = queue.poll();
        		for(int j=0; j<4; j++) {
        			char[] clone_add = lock.clone();
        			char[] clone_sub = lock.clone();
        			char c = lock[j];
        			if(c == '0') {
        				clone_add[j] = '1';
        				clone_sub[j] = '9';
        			}
        			else if(c == '9') {
        				clone_add[j] = '0';
        				clone_sub[j] = '8';
        			}
        			else {
        				clone_add[j] = (char) (c + 1);
        				clone_sub[j] = (char) (c - 1);
        			}
        			if(Arrays.equals(clone_add, end))
        				return step;
        			if(Arrays.equals(clone_sub, end))
        				return step;
        			if(!lockContains(root, clone_add) && !lockContains(used, clone_add)) {
        				queue.offer(clone_add);
        				used = addLock(used, clone_add);
        			}
        			if(!lockContains(root, clone_sub) && !lockContains(used, clone_sub)) {
        				queue.offer(clone_sub);
        				used = addLock(used, clone_sub);
        			}
        		}
        	}
        }
    	
    	return -1;
    }
    
    private boolean lockContains(Lock root, char[] lock) {
    	for(char c : lock) {
    		int i = c - '0';
    		if(root.next[i] == null)
    			return false;
    		root = root.next[i];
    	}
    	if(root == null)
    		return false;
    	return root.lock != null;
    }
    
    private Lock addLock(Lock root, char[] deadend) {
    	Lock lock = root;
    	for(char c : deadend) {
    		int i = c - '0';
    		if(lock.next[i] == null)
    			lock.next[i] = new Lock();
    		lock = lock.next[i];
    	}
    	lock.lock = new String(deadend);
    	return root;
    }
    
    
    public String crackSafe(int n, int k) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<n; i++)
        	sb.append('0');
    	int total = (int) Math.pow(k, n);
    	Set<String> used = new HashSet<>();
        used.add(sb.toString());
    	crackSafeDFS(sb, used, n, k, total);
    	return sb.toString();
    }
    
    private boolean crackSafeDFS(StringBuilder sb, Set<String> used, int n, int k, int total) {
    	if(used.size() == total)
    		return true;
    	String pre = sb.substring(sb.length()-n +1, sb.length());
    	for(int i=0; i<k; i++) {
    		String next = pre + i;
    		if(used.contains(next))
    			continue;
    		used.add(next);
    		sb.append(i);
    		if(crackSafeDFS(sb, used, n, k, total))
    			return true;
    		else {
    			sb.delete(sb.length()-1, sb.length());
    			used.remove(next);
    		}
    	}
    	return false;
    }
    
    public int[] exclusiveTime(int n, List<String> logs) {
        int[] times = new int[n];
        Stack<Integer> ids = new Stack<>();
        Stack<Integer> timestamp = new Stack<>();
        for(int i=0, size = logs.size(); i<size; i++) {
        	String log = logs.get(i);
        	int index = log.indexOf(":");
        	int id = Integer.parseInt(log.substring(0, index));
        	int ts = Integer.parseInt(log.substring(log.lastIndexOf(":")+1, log.length()));
        	char c = log.charAt(index+1);
        	if(ids.isEmpty()) {
        		ids.push(id);
        		timestamp.push(ts);
        		continue;
        	}
        	if(c == 's') {
        		int pre_id = ids.peek();
        		times[pre_id] += ts - timestamp.peek();
        		ids.push(id);
        		timestamp.push(ts);
        	}
        	else {
        		int pre_id = ids.pop();
        		times[pre_id] += ts - timestamp.pop() + 1;
        		if(!ids.isEmpty()) {
        			timestamp.pop();
        			timestamp.push(ts+1);
        		}
        	}
        	
        }
        
        return times;
    }
    
    
    public List<Integer> partitionLabels(String S) {
        List<Integer> list = new ArrayList<>();
        if(S == null || S.length() == 0)
        	return list;
        char[] array = S.toCharArray();
        partitionLabelsHelp(array, 0, array.length-1, list);
        return list;
    }
    
    private void partitionLabelsHelp(char[] str, int begin, int end, List<Integer> list) {
    	int[] fre = new int[26];
    	int[] index_left = new int[26];
    	int[] index_right = new int[26];
    	Arrays.fill(index_left, -1);
    	for(int i=begin; i<=end; i++) {
    		char c = str[i];
    		fre[c - 'a']++;
    		index_right[c - 'a'] = i;
    		if(index_left[c - 'a'] == -1) {
    			index_left[c - 'a'] = i;
    		}
    	}
    	int max = -1;
    	char max_c = '\0';
    	for(int i=0; i<26; i++) {
    		int num = fre[i];
    		if(num > max) {
    			max = num;
    			max_c = (char) ('a' + i);
    		}
    	}
    	int left = index_left[max_c - 'a'];
    	int right = index_right[max_c - 'a'];
    	int left_copy = left, right_copy = right;
    	for(int i=left_copy; i<=right_copy; i++) {
    		left = Math.min(left, index_left[str[i]-'a']);
    		right = Math.max(right, index_right[str[i] - 'a']);
    	}
    	
    	if(left == begin && right == end) {
    		list.add(right - left + 1);
    	}
    	else if(left == begin) {
    		list.add(right - left + 1);
    		partitionLabelsHelp(str, right+1, end, list);
    	}
    	else if(right == end) {
    		partitionLabelsHelp(str, begin, left-1, list);
    		list.add(right - left + 1);
    	}
    	else {
    		partitionLabelsHelp(str, begin, left-1, list);
    		list.add(right - left + 1);
    		partitionLabelsHelp(str, right+1, end, list);
    	}
    }
    
    
    public List<Integer> partitionLabels2(String S) {
        List<Integer> list = new ArrayList<>();
        if(S == null || S.length() == 0)
        	return list;
        char[] array = S.toCharArray();
        int[] map = new int[26];
        for(int i=0; i<array.length; i++)
        	map[array[i] - 'a'] = i;
        int start = 0, end = 0;
        for(int i=0; i<array.length; i++) {
        	end = Math.max(end, map[array[i] - 'a']);
        	if(end == i) {
        		list.add(end - start + 1);
        		start = end + 1;
        	}
        }
        return list;
    }
    
    
      class NestedInteger {
    	      // Constructor initializes an empty nested list.
    	      public NestedInteger() {
			}
    	 
    	      // Constructor initializes a single integer.
    	      public  NestedInteger(int value) {
			}
    	 
    	      // @return true if this NestedInteger holds a single integer, rather than a nested list.
    	      public boolean isInteger() {
				return false;
			}
    	 
    	      // @return the single integer that this NestedInteger holds, if it holds a single integer
    	      // Return null if this NestedInteger holds a nested list
    	      public Integer getInteger() {
				return null;
			}
    	 
    	      // Set this NestedInteger to hold a single integer.
    	      public void setInteger(int value) {
			}
    	 
    	      // Set this NestedInteger to hold a nested list and adds a nested integer to it.
    	      public void add(NestedInteger ni) {
			}
    	 
    	      // @return the nested list that this NestedInteger holds, if it holds a nested list
    	      // Return null if this NestedInteger holds a single integer
    	      public List<NestedInteger> getList() {
				return null;
			}
    	  }
    
     
     public NestedInteger deserialize(String s) {
    	 Stack<NestedInteger> stack = new Stack<>();
    	 NestedInteger cur = null;
    	 char[] array = s.toCharArray();
    	 StringBuilder sb = new StringBuilder();
    	 for(int i=0; i<array.length; i++) {
    		 char c = array[i];
    		 if((c >= '0' && c <= '9') || c == '-') {
    			 sb.append(c);
    		 }
    		 else if(c == '[') {
    			 NestedInteger nestedInteger = new NestedInteger();
    	    	 stack.push(nestedInteger);
    	    	 if(cur != null) {
    	    		 cur.add(nestedInteger);
    	    	 }
    	    	 cur = nestedInteger;
    		 }
    		 else if(c == ']') {
    			 if(sb.length() > 0) {
    				 cur.add(new NestedInteger(Integer.parseInt(sb.toString())));
    				 sb = new StringBuilder();
    			 }
    			 stack.pop();
    			 if(!stack.isEmpty()) {
    				 cur = stack.peek();
    			 }
    		 }
    		 else if(c == ',') {
    			 if(sb.length() > 0) {
    				 cur.add(new NestedInteger(Integer.parseInt(sb.toString())));
    				 sb = new StringBuilder();
    			 }
    		 }
    	 }
    	 if(sb.length() > 0) {
			 if(cur == null) {
				 cur = new NestedInteger(Integer.parseInt(sb.toString()));
			 }
			 
    	 }
    	 
    	 return cur;
     }
     
     
     class LadderNode {
    	 String word;
    	 LadderNode[] next = new LadderNode[26];
     }
     
     
     private LadderNode buildLadderNode(List<String> wordList) {
    	 LadderNode root = new LadderNode();
    	 for(String word : wordList) {
    		 LadderNode node = root;
    		 for(char c : word.toCharArray()) {
    			 int i = c - 'a';
    			 if(node.next[i] == null)
    				 node.next[i] = new LadderNode();
    			 node = node.next[i];
    		 }
    		 node.word = word;
    	 }
    	 return root;
     }
     
     private String getWord(char[] word, LadderNode root) {
    	 for(int i=0; i<word.length; i++) {
    		 if(root == null)
    			 return null;
    		 char c = word[i];
    		 root = root.next[c - 'a'];
    	 }
    	 if(root == null)
    		 return null;
    	 return root.word;
     }
     
     
     
     public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
    	 List<List<String>> ladders = new ArrayList<>();
    	 if(wordList == null || wordList.isEmpty())
    		 return ladders;
    	 wordList.add(beginWord);
    	 LadderNode root = buildLadderNode(wordList);
    	 char[] begin = beginWord.toCharArray();
    	 char[] end = endWord.toCharArray();
    	 if(getWord(end, root) == null)
    		 return ladders;
    	 Set<String> used = new HashSet<>();
    	 used.add(beginWord);
    	 Queue<char[]> queue = new LinkedList<>();
    	 Map<String, Set<String>> graph = new HashMap<>();
    	 queue.add(begin);
    	 int length = 0;
    	 boolean stop = false;
    	 while(!queue.isEmpty() && !stop) {
    		 int size = queue.size();
    		 Set<String> cur = new HashSet<>();
    		 length++;
    		 for(int i=0; i<size; i++) {
    			 char[] word = queue.poll();
    			 String oldWord = getWord(word, root);
    			 if(!graph.containsKey(oldWord))
    				 graph.put(oldWord, new HashSet<>());
    			 for(int j=0; j<word.length; j++) {
    				 char c = word[j];
    				 for(char ch = 'a'; ch <='z'; ch++) {
    					 word[j] = ch;
    					 String newWord = getWord(word, root);
    					 if(oldWord.equals("sane")) {
    						 System.out.println(oldWord+" -> "+newWord);
    					 }
    					 if(newWord == null || used.contains(newWord)) {
    						 word[j] = c;
    						 continue;
    					 }
    					 queue.add(word.clone());
    					 cur.add(newWord);
    					 graph.get(oldWord).add(newWord);
    					 if(newWord.equals(endWord))
    						 stop = true;
    					 word[j] = c;
    				 }
    			 }
    		 }
    		 if(!stop)
    			 used.addAll(cur);
    	 }
    	 List<String> buffer = new ArrayList<>();
    	 buffer.add(beginWord);
    	 findLaddersHelp(graph, beginWord, endWord, buffer, ladders, length);
    	 return ladders;
     }
     
     private void findLaddersHelp(Map<String, Set<String>> graph, String word, String target, List<String> list, List<List<String>> ladders, int length) {
    	 if(length == 0) {
    		 if(!word.equals(target))
    			 return;
    		 ladders.add(new ArrayList<>(list));
    	 }
    	 else {
    		 Set<String> subs = graph.get(word);
    		 if(subs == null)
    			 return;
    		 for(String sub : subs) {
    			 list.add(sub);
    			 findLaddersHelp(graph, sub, target, list, ladders, length-1);
    			 list.remove(list.size() - 1);
    		 }
    	 }
    	 
     }
     
     public int minStickers(String[] stickers, String target) {
         int N = stickers.length;
    	 int cutdown = target.length();
    	 int times = 0;
    	 char[] targetArray = target.toCharArray();
    	 StickersBuffer = stickers;
    	 
    	 int[][] map = new int[N][26];
    	 for(int i=0; i<N; i++) {
    		 int[] array = new int[26];
    		 String sticker = stickers[i];
    		 for(char c : sticker.toCharArray())
    			 array[c-'a']++;
    		 map[i] = array;
    	 }
    	 int[] target_array = new int[26];
    	 for(char c : targetArray)
    		 target_array[c-'a']++;
    	 while(cutdown > 0) {
    		 times++;
    		 int counter = 0;
    		 int index = -1;
    		 for(int i=0; i<N; i++) {
    			 int[] array = map[i];
    			 int temp = 0;
    			 for(int j=0; j<26; j++) {
    				 int min = Math.min(target_array[j], array[j]);
    				// System.out.println("target_array[c-'a']:"+target_array[j]+" array[c-'a']:"+array[j]+"  min:"+min);
    				 temp += min;
    			 }
    			 System.out.println("temp : "+temp +" "+stickers[i]);
    			 if(temp > counter) {
    				 counter = temp;
    				 index = i;
    			 }
    		 }
    		 
    		 if(index == -1)
    			 return -1;
    		 System.out.println(stickers[index]+" : "+counter);
    		 cutdown -= counter;
    		 int[] chosen = map[index];
    		 for(int i=0; i<26; i++) {
    			 target_array[i] -= Math.min(target_array[i], chosen[i]);
    		 }
    		 System.out.println(Arrays.toString(target_array));
    	 }
    	 
    	 
    	 return times;
     }
     
     private int minStickers = 100;
     private boolean stickerimpossible = false;
     private String[] StickersBuffer;
     
     private void minStickersBT(int cutdown, int[][] map, int[] target_array, int times) {
    	 StringBuilder sb = new StringBuilder();
    	 for(int i=0; i<26; i++) {
    		 for(int j=0; j<target_array[i];j++) {
    			 sb.append((char)('a'+i));
    		 }
    	 }
    	 System.out.println("target:"+sb);
    	 
    	 if(stickerimpossible)
    		 return;
    	 if(cutdown == 0) {
    		 minStickers = Math.min(times, minStickers);
    		 System.out.println("min:"+minStickers);
    		 return;
    	 }
    	 times++;
    	 int counter = 0;
		// int index = -1;
		 int[] fre = new int[map.length];
		 for(int i=0; i<map.length; i++) {
			 int[] array = map[i];
			 int temp = 0;
			 for(int j=0; j<26; j++) {
				 int min = Math.min(target_array[j], array[j]);
				 temp += min;
			 }
			 fre[i] = temp;
			// System.out.println(StickersBuffer[i]+":"+fre[i]);
		 }
		 
		 if(counter == 0) {
			 stickerimpossible = true;
			 return;
		 }
		 for(int x=0; x<map.length; x++) {
			// if(fre[x] == counter) {
				 System.out.println("target: "+sb +   "   选:"+StickersBuffer[x]);
				 int[] clone = target_array.clone();
				 int[] chosen = map[x];
	    		 for(int i=0; i<26; i++) {
	    			 clone[i] -= Math.min(target_array[i], chosen[i]);
	    		 }
	    		 minStickersBT(cutdown - counter, map, clone, times);
	    		 
			// }
		 }
		 
		 
     }
     
     public int minStickers3(String[] stickers, String target) {
    	 int N = stickers.length;
    	 int cutdown = target.length();
    	 char[] targetArray = target.toCharArray();
    	 Set<Character> set = new HashSet<>();
    	 StickersBuffer = stickers;
    	 
    	 int[][] map = new int[N][26];
    	 for(int i=0; i<N; i++) {
    		 int[] array = new int[26];
    		 String sticker = stickers[i];
    		 for(char c : sticker.toCharArray()) {
    			 array[c-'a']++;
    			 set.add(c);
    		 }
    		 map[i] = array;
    	 }
    	 int[] target_array = new int[26];
    	 for(char c : targetArray) {
    		 target_array[c-'a']++;
    		 if(!set.contains(c))
    			 return -1;
    	 }
    	 StickerMemoization.put(Arrays.toString(new int[26]), 0);
    	 int answer = minStickersMemoization(cutdown, map, target_array);
    	 //System.out.println(StickerMemoization);
    	 return answer;
     }
     private Map<String, Integer> StickerMemoization = new HashMap<>();
     
     private int minStickersMemoization(int cutdown, int[][] map, int[] target_array) {
    	 StringBuilder sb = new StringBuilder();
    	 for(int i=0; i<26; i++) {
    		 for(int j=0; j<target_array[i];j++) {
    			 sb.append((char)('a'+i));
    		 }
    	 }
    	 System.out.println("***********************");  
    	 String memory = Arrays.toString(target_array);
    	 if(StickerMemoization.containsKey(memory))
    		 return StickerMemoization.get(memory);
    	 
    	 int[] fre = new int[map.length];
		 for(int i=0; i<map.length; i++) {
			 int[] array = map[i];
			 int temp = 0;
			 for(int j=0; j<26; j++) {
				 temp += Math.min(target_array[j], array[j]);
			 }
			 fre[i] = temp;
			 System.out.println(StickersBuffer[i] + ":"+temp);
		 }
    	 
    	 int min = Integer.MAX_VALUE;
		 for(int x=0; x<map.length; x++) {
			 if(fre[x] == 0)
				 continue;
			 int[] clone = target_array.clone();
			 int[] chosen = map[x];
    		 for(int i=0; i<26; i++) {
    			 clone[i] -= Math.min(target_array[i], chosen[i]);
    		 }
    		 min = Math.min(min, 1+minStickersMemoization(cutdown - fre[x], map, clone));
		 }
    	 StickerMemoization.put(memory, min);
    	 return min;
     }
     
     
     
     public int minStickers2(String[] stickers, String target) {
    	 int N = stickers.length;
    	 int cutdown = target.length();
    	 char[] targetArray = target.toCharArray();
    	 StickersBuffer = stickers;
    	 
    	 int[][] map = new int[N][26];
    	 for(int i=0; i<N; i++) {
    		 int[] array = new int[26];
    		 String sticker = stickers[i];
    		 for(char c : sticker.toCharArray())
    			 array[c-'a']++;
    		 map[i] = array;
    	 }
    	 int[] target_array = new int[26];
    	 for(char c : targetArray)
    		 target_array[c-'a']++;
    	 minStickersBT(cutdown, map, target_array, 0);
    	 if(stickerimpossible)
    		 return -1;
    	 return minStickers;
     }
     
     //final version
     public int minStickers4(String[] stickers, String target) {
    	 int N = stickers.length;
    	 int[][] map = new int[N][26];
    	 Set<Character> set = new HashSet<>();
    	 for(int i=0; i<N; i++) {
    		 for(char c : stickers[i].toCharArray()) {
    			 map[i][c-'a']++;
    			 set.add(c);
    		 }
    	 }
    	 for(char c : target.toCharArray())
    		 if(!set.contains(c))
    			 return -1;
    	 Map<String, Integer> memoization = new HashMap<>();
    	 memoization.put("", 0);
    	 return minStickersMemoization(memoization, target, map);
     }
     
     private int minStickersMemoization(Map<String, Integer> memoization, String target, int[][] map) {
    	 if(memoization.containsKey(target))
    		 return memoization.get(target);
    	 int answer = Integer.MAX_VALUE;
    	 int[] array = new int[26];
    	 for(char c : target.toCharArray())
    		 array[c - 'a']++;
    	 int n = map.length;
    	 for(int i=0; i<n; i++) {
    		 StringBuilder sb = new StringBuilder();
    		 for(int j=0; j<26; j++) {
    			if(array[j] > 0) {
    				for(int k=0; k<Math.max(0, array[j]-map[i][j]); k++)
       				 sb.append((char)(j+'a'));
    			}
    		 }
    		 String temp = sb.toString();
    		 if(temp.equals(target))
    			 continue;
    		 answer = Math.min(answer, minStickersMemoization(memoization, temp, map) + 1);
    	 }
    	 memoization.put(target, answer);
    	 return answer;
     }
     
     class reorganizeStringSorter implements Comparator<Character>{
    	 
    	 int[] map;
    	 
    	 public reorganizeStringSorter(int[] map) {
			this.map = map;
		}

		@Override
		public int compare(Character o1, Character o2) {
			return map[o2 - 'a'] - map[o1 - 'a'];
		}
    	 
     }
     
     public String reorganizeString(String S) {
         String EMPTY = "";
    	 StringBuilder sb = new StringBuilder();
         int[] map = new int[26];
         
         for(char c : S.toCharArray()) {
        	 map[c - 'a']++;
         }
         reorganizeStringSorter sorter = new reorganizeStringSorter(map);
         PriorityQueue<Character> priorityQueue = new PriorityQueue<>(sorter);
         for(int i=0; i<26; i++) {
        	 if(map[i] == 0)
        		 continue;
        	 char c = (char) ('a' + i);
        	 priorityQueue.offer(c);
         }
         if(priorityQueue.size() == 1)
        	 return EMPTY;
         char A = priorityQueue.poll();
         int ACounter = map[A - 'a'];
         while(!priorityQueue.isEmpty()) {
        	 char B = priorityQueue.poll();
        	 int BCounter = map[B - 'a'];
        	 if(ACounter < BCounter) {
        		 int temp = ACounter;
        		 ACounter = BCounter;
        		 BCounter = temp;
        		 
        		 char c = A;
        		 A = B;
        		 B = c;
        	 }
        	 
        	 if(priorityQueue.isEmpty()) {
        		 int remainACounter = ACounter - BCounter;
        		 if(remainACounter > 1) {
        			 int size = sb.length();
        			 if(remainACounter > size + 1)
        				 return EMPTY;
        			 StringBuilder buffer = new StringBuilder();
        			 if(remainACounter > size) {
        				 for(int j=0; j<size; j++)
        					 buffer.append(A).append(sb.charAt(j));
        				 buffer.append(A);
        				 char c = A;
                		 A = B;
                		 B = c;
        			 }
        			 else {
        				 for(int j=0; j<remainACounter; j++)
        					 buffer.append(A).append(sb.charAt(j));
        				 for(int j=remainACounter; j<size; j++)
        					 buffer.append(sb.charAt(j));
        			 }
            		 sb = buffer;
            		 ACounter -= remainACounter;
        		 }
        	 }
        	 
        	 
        	 for(int j=0; j<BCounter; j++)
        		 sb.append(A).append(B);
        	 ACounter -= BCounter;
         }
         for(int i=0; i<ACounter; i++)
        	 sb.append(A);
    	 return sb.toString();
     }
     
     
     class Zuma {
    	 int[] counter = new int[5];
    	 int size;
    	 public Zuma(String str) {
			for(char c : str.toCharArray()) {
				int i = index(c);
				counter[i]++;
			}
			size = str.length();
		}
    	 
    	public void change(char c, int num) {
    		int i = index(c);
    		counter[i] += num;
    	}
    	 
    	 public int index(char c) {
    		 switch (c) {
			case 'R':
				return 0;
			case 'Y':
				return 1;
			case 'B':
				return 2;
			case 'G':
				return 3;
			case 'W':
				return 4;
			}
    		 return -1;
    	 }
    	 
    	 public int getCharCount(char c) {
    		 int i = index(c);
    		 return counter[i];
    	 }
    	 
    	 public boolean isEmpty() {
    		 return size == 0;
    	 }
    	 
    	 @Override
    	public String toString() {
    		StringBuilder sb = new StringBuilder();
    		for(int i=0; i<counter[0]; i++)
    			sb.append('R');
    		for(int i=0; i<counter[1]; i++)
    			sb.append('Y');
    		for(int i=0; i<counter[2]; i++)
    			sb.append('B');
    		for(int i=0; i<counter[3]; i++)
    			sb.append('G');
    		for(int i=0; i<counter[4]; i++)
    			sb.append('W');
    		return sb.toString();
    	}
     }
     
     
     
     char[] zumaChar = {'R','Y','B','G','W'};
     public int findMinStep(String board, String hand) {
         Map<String, Zuma> map = new HashMap<>();
         map.put(board, new Zuma(hand));
    	 Queue<String> queue = new LinkedList<>();
    	 queue.add(board);
    	 int step = 0;
    	 while(!queue.isEmpty()) {
    		 int size = queue.size();
    		 step++;
    		 for(int i=0; i<size; i++) {
    			 String zummaStr = queue.poll();
        		 Zuma node = map.get(zummaStr);
        		 String handString = node.toString();
        		 if(node.isEmpty())
        			 continue;
        		 if(!zumaPossibleContinue(zummaStr, handString, node))
        			 continue;
        		 for(char c : zumaChar) {
        			 int count = node.getCharCount(c);
        			 if(count == 0)
        				 continue;
        			 for(int j=0; j<=zummaStr.length(); j++) {
        				 if(j < zummaStr.length() && zummaStr.charAt(j) != c)
        					 continue;
        				 String insertStr = zummaStr.substring(0, j) + c + zummaStr.substring(j);
        				 String changeStr = removeZumaString(insertStr);
        				 if(changeStr.length() == 0)
        					 return step;
        				 queue.add(changeStr);
        				 node.change(c, -1);
        				 map.put(changeStr, new Zuma(node.toString()));
        				 node.change(c, 1);
        			 }
        		 }
    		 }
    	 }
    	 
    	 return -1;
     }
     
     private boolean zumaPossibleContinue(String board, String hand, Zuma zuma) {
    	 int[] counter = new int[5];
    	 for(char c : board.toCharArray()) {
    		 int i = zuma.index(c);
    		 counter[i]++;
    	 }
    	 for(char c : hand.toCharArray()) {
    		 int i = zuma.index(c);
    		 if(counter[i] == 0)
    			 continue;
    		 counter[i]++;
    	 }
    	 for(int i=0; i<5; i++) {
    		 if(counter[i] < 3 && counter[i] != 0)
    			 return false;
    	 }
    	 return true;
     }
     
     
     private String removeZumaString(String str) {
    	 String result = str;
    	 boolean end = false;
    	 while(!end) {
    		 if(str.length() == 0)
    			 return "";
    		 boolean temp = true;
    		 char target = str.charAt(0);
    		 int count = 1;
    		 int index = str.length() - 1;
    		 for(int i=1; i<str.length(); i++) {
    			 char c = str.charAt(i);
    			 if(c == target)
    				 count++;
    			 else {
    				 if(count >= 3) {
    					 index = i-1;
    					 break;
    				 }
    				 else {
    					 count = 1;
    					 target = c;
    				 }
    			 }
    		 }
    		 if(count >= 3) {
    			 if(index == str.length() - 1) {
    				 str = str.substring(0, index-count+1);
    			 }
    			 else {
        			 str = str.substring(0, index-count+1) + str.substring(index+1); 
    			 }
				 temp = false;
    		 }
    		 end = temp;
    		 result = str;
    	 }
    	 
    	 return result;
     }
     
     
     public boolean pyramidTransition(String bottom, List<String> allowed) {
         Map<String, List<Character>> map = new HashMap<>();
         for(String triple : allowed) {
        	 String key = "" + triple.charAt(0) + triple.charAt(1);
        	 char c = triple.charAt(2);
        	 if(!map.containsKey(key))
        		 map.put(key, new ArrayList<>());
        	 map.get(key).add(c);
         }
    	 return pyramidTransitionDFS(bottom, new StringBuilder(), bottom.length()-1, map);
     }
     
     private boolean pyramidTransitionDFS(String bottom, StringBuilder rowNow, int length, Map<String, List<Character>> allowed) {
    	 if(length == 0)
    		 return true;
    	 if(rowNow.length() == length)
    		 return pyramidTransitionDFS(rowNow.toString(), new StringBuilder(), length - 1, allowed);
    	 int len = rowNow.length();
    	 String key = "" + bottom.charAt(len) + bottom.charAt(len+1);
    	 List<Character> list = allowed.get(key);
    	 if(list == null)
    		 return false;
    	 for(Character c : list) {
    		 rowNow.append(c);
    		 if(pyramidTransitionDFS(bottom, rowNow, length, allowed))
    			 return true;
    		 rowNow.deleteCharAt(rowNow.length()-1);
    	 }
    	 return false;
     }
     
     public int calculate(String s) {
    	 List<String> list = toPostfix(s);
    	 System.out.println(list);
    	 return getPostfixValue(list);
     }
     
     private List<String> toPostfix(String s){
    	 List<String> list = new ArrayList<>();
    	 StringBuilder sb = new StringBuilder();
    	 Stack<Character> stack = new Stack<>();
    	 for(int i=0; i<s.length(); i++) {
    		 char c = s.charAt(i);
    		 if(c >='0' && c <= '9') {
    			 sb.append(c);
    			 continue;
    		 }
    		 if(sb.length() > 0) {
				 list.add(sb.toString());
				 sb = new StringBuilder();
			 }
    		 if(c == ' ') {
    			 continue;
    		 }
    		 if(c == '(') {
    			 stack.push(c);
    			 continue;
    		 }
    		 if(c == '+' || c == '-' || c =='*' || c == '/') {
    			 int priority = getExpressionPriority(c);
				 while(!stack.isEmpty() && priority >= getExpressionPriority(stack.peek())) {
					 list.add(String.valueOf(stack.pop()));
				 }
				 stack.push(c);
    			 continue;
    		 }
    		 if(c == ')') {
    			 while(stack.peek() != '(') {
    				 list.add(String.valueOf(stack.pop()));
    			 }
    			 stack.pop();
    		 }
    		 
    	 }
    	 if(sb.length() > 0) {
    		 list.add(sb.toString());
    	 }
    	 while(!stack.isEmpty()) {
    		 list.add(String.valueOf(stack.pop()));
    	 }
    	 return list;
     }
     
     private int getPostfixValue(List<String> list) {
    	 Stack<Long> stack = new Stack<>();
    	 for(int i=0; i<list.size(); i++) {
    		 String str = list.get(i);
    		 if(str.equals("+")) {
    			 long a = stack.pop();
    			 long b = stack.pop();
    			 stack.push(a+b);
    		 }
    		 else if(str.equals("-")) {
    			 long a = stack.pop();
    			 long b = stack.pop();
    			 stack.push(b-a);
    		 }
    		 else if(str.equals("*")) {
    			 long a = stack.pop();
    			 long b = stack.pop();
    			 stack.push(a*b);
    		 }
    		 else if(str.equals("/")) {
    			 long a = stack.pop();
    			 long b = stack.pop();
    			 stack.push(b/a);
    		 }
    		 else {
    			 long num = Long.parseLong(str);
    			 stack.push(num);
    		 }
    	 }
    	 
    	 return stack.pop().intValue();
     }
     
     
     
     private int getExpressionPriority(char c) {
    	 if(c == '*' || c == '/')
    		 return 1;
    	 if(c == '+' || c == '-')
    		 return 2;
    	 return 3;
     }
     
     
     
     public int findRotateSteps(String ring, String key) {
         Map<Character, List<Integer>> map = new HashMap<>();
         char[] key_array = key.toCharArray();
         char[] ring_array = ring.toCharArray();
         for(char c : key_array) {
        	 if(!map.containsKey(c))
        		 map.put(c, new ArrayList<>());
         }
         for(int i=0; i<ring_array.length; i++) {
        	 char c = ring_array[i];
        	 if(map.containsKey(c)) {
        		 map.get(c).add(i);
        	 }
         }
    	 return findRotateSteps(key_array, ring_array, 0, 0, map) + key_array.length;
     }
     
     private int findRotateSteps(char[] key, char[] ring, int index, int lastPos, Map<Character, List<Integer>> posMap) {
    	 if(index == key.length)
    		 return 0;
    	 char c = key[index];
    	 List<Integer> list = posMap.get(c);
    	 int steps = Integer.MAX_VALUE;
    	 
    	 if(list.size() == 1) {
    		 int pos = list.get(0);
    		 
    		 if(pos > lastPos) {
    			 if(pos - lastPos > lastPos - 0 + ring.length - pos) {
    				 steps = Math.min(steps, lastPos - 0 + ring.length - pos + findRotateSteps(key, ring, index + 1, pos, posMap));
    			 }
    			 else {
    				 steps = Math.min(steps, pos - lastPos + findRotateSteps(key, ring, index + 1, pos, posMap));
    			 }
    		 }
    		 else if(pos < lastPos) {
    			 if(lastPos - pos > pos - 0 + ring.length - lastPos) {
    				 steps = Math.min(steps, pos - 0 + ring.length - lastPos + findRotateSteps(key, ring, index + 1, pos, posMap));
    			 }
    			 else {
    				 steps = Math.min(steps, lastPos - pos + findRotateSteps(key, ring, index + 1, pos, posMap));
    			 }
    		 }
    		 else {
    			 steps = Math.min(steps, findRotateSteps(key, ring, index + 1, pos, posMap));
    		 }
    	 }
    	 else {
    		 for(int i=0; i<list.size(); i++) {
        		 int pos = list.get(i);
        		 
        		 if(pos > lastPos) {
        			 steps = Math.min(steps, pos - lastPos + findRotateSteps(key, ring, index + 1, pos, posMap));
        			 steps = Math.min(steps, lastPos - 0 + ring.length - pos + findRotateSteps(key, ring, index + 1, pos, posMap));
        		 }
        		 else if(pos < lastPos) {
        			 steps = Math.min(steps, lastPos - pos + findRotateSteps(key, ring, index + 1, pos, posMap));
        			 steps = Math.min(steps, pos - 0 + ring.length - lastPos + findRotateSteps(key, ring, index + 1, pos, posMap));
        		 }
        		 else {
        			 steps = Math.min(steps, findRotateSteps(key, ring, index + 1, pos, posMap));
        		 }
        		 
        	 }
    	 }
    	 
    	 return steps;
     }
     
     
     public int numJewelsInStones(String J, String S) {
    	 if(J == null || J.length() == 0 || S == null || S.length() == 0)
    		 return 0;
         int jewels = 0;
         boolean[] table = new boolean[128];
         for(char c : J.toCharArray())
        	 table[c] = true;
         for(char c : S.toCharArray())
        	 if(table[c])
        		 jewels++;
         return jewels;
     }
     
     public boolean wordBreak(String s, List<String> wordDict) {
    	 Set<String> directory = new HashSet<>();
    	 for(String word : wordDict)
    		 directory.add(word);
    	 Map<String, Boolean> memorization = new HashMap<>();
    	 memorization.put("", true);
    	 return wordBreakMemorization(s, directory, memorization);
     }
     
     private boolean wordBreakMemorization(String s, Set<String> directory, Map<String, Boolean> memorization) {
    	 if(memorization.containsKey(s))
    		 return memorization.get(s);
    	 for(String word : directory) {
    		 if(!s.startsWith(word))
    			 continue;
    		 if(wordBreakMemorization(s.substring(word.length()), directory, memorization)) {
    			 memorization.put(s, true);
    			 return true;
    		 }
    	 }
    	 memorization.put(s, false);
    	 return false;
     }
     
     public int kthGrammar(int N, int K) {
         String grammar = generateGrammar(N, "0");
         if(grammar.charAt(K-1) == '1')
        	 return 1;
    	 return 0;
     }
     
     private String generateGrammar(int n, String s) {
    	 if(n == 0)
    		 return s;
    	 StringBuilder sb = new StringBuilder();
    	 for(int i=0; i<s.length(); i++) {
    		 char c = s.charAt(i);
    		 if(c == '0') {
    			 sb.append('0').append('1');
    		 }
    		 else {
    			 sb.append('1').append('0');
    		 }
    	 }
    	 return generateGrammar(n-1, sb.toString());
     }
     
     public int kthGrammar2(int N, int K) {
    	 StringBuilder sb = new StringBuilder();
    	 sb.append('0');
    	 for(int n=0; n<N; n++) {
    		 int length = sb.length();
    		 System.out.println(sb.toString());
    		 if(length >= K) {
    			 if(sb.charAt(K-1) == '1')
    				 return 1;
    			 return 0;
    		 }
    		 for(int i=0; i<length; i++) {
    			 char c = sb.charAt(i);
    			 if(c == '0') {
    				 sb.append('1');
    			 }
    			 else {
    				 sb.append('0');
    			 }
    		 }
    	 }
    	 if(sb.charAt(K-1) == '1')
			 return 1;
    	 return 0;
     }
     
     
     public boolean canTransform(String start, String end) {
         int len1 = start.length();
         int len2 = end.length();
         if(len1 != len2)
        	 return false;
         if (!start.replace("X", "").equals(end.replace("X", "")))
             return false;
         char[] from = start.toCharArray();
         char[] to = end.toCharArray();
         int p1 = 0, p2 = 0;
         while(p1 < len1 && p2 < len2) {
        	 while(p1 < len1 && from[p1] == 'X')
        		 p1++;
        	 while(p2 < len1 && to[p2] == 'X')
        		 p2++;
        	 if(p1 == len1 && p2 == len1)
        		 return true;
        	 if(p1 == len1 || p2 == len1)
        		 return false;
        	 if(from[p1] != to[p2])
        		 return false;
        	 
        	 if(from[p1] == 'L' && p2 > p1)
        		 return false;
        	 if(from[p1] == 'R' && p1 > p2)
        		 return false;
        	 p1++;
        	 p2++;
         }
    	 
    	 return true;
     }
     
     public List<String> letterCasePermutation(String S) {
         List<String> permutation = new ArrayList<>();
         letterCasePermutationHelp(S.toCharArray(), 0, new StringBuilder(), permutation);
         return permutation;
     }
     
     private void letterCasePermutationHelp(char[] array, int index, StringBuilder sb, List<String> list) {
    	 if(index == array.length) {
    		 list.add(sb.toString());
    		 return;
    	 }
    	 char c = array[index];
    	 if(c >= 'a' && c <= 'z') {
    		 sb.append(c);
    		 letterCasePermutationHelp(array, index+1, sb, list);
    		 sb.deleteCharAt(sb.length()-1);
    		 sb.append((char)(c-32));
    		 letterCasePermutationHelp(array, index+1, sb, list);
    		 sb.deleteCharAt(sb.length()-1);
    	 }
    	 else if(c >= 'A' && c <= 'Z') {
    		 sb.append(c);
    		 letterCasePermutationHelp(array, index+1, sb, list);
    		 sb.deleteCharAt(sb.length()-1);
    		 sb.append((char)(c+32));
    		 letterCasePermutationHelp(array, index+1, sb, list);
    		 sb.deleteCharAt(sb.length()-1);
    	 }
    	 else {
    		 sb.append(c);
    		 letterCasePermutationHelp(array, index+1, sb, list);
    		 sb.deleteCharAt(sb.length()-1);
    	 }
    	 
     }
     
     public String customSortString(String S, String T) {
         int[] index = new int[26];
         Arrays.fill(index, 100);
         Character[] temp = new Character[T.length()];
         char[] array = new char[T.length()];
         for(int i=0; i<T.length(); i++)
        	 temp[i] = T.charAt(i);
    	 for(int i=0; i<S.length(); i++) {
    		 char c = S.charAt(i);
    		 index[c-'a'] = i;
    	 }
    	 Arrays.sort(temp, new customSortStringSorter(index));
         for(int i=0; i<array.length; i++)
        	 array[i] = temp[i];
    	 return new String(array);
     }
     
     class customSortStringSorter implements Comparator<Character> {
    	 
    	 int[] index;
    	public customSortStringSorter(int[] index) {
			this.index = index;
		}
    	 
		@Override
		public int compare(Character o1, Character o2) {
			char c1 = o1.charValue();
			char c2 = o2.charValue();
			return index[c1-'a'] - index[c2-'a'];
		}
    	 
     }
     
     public int characterReplacement(String s, int k) {
         int[] counter = new int[26];
         int start = 0, end = 0, maxCount = 0, maxLength = 0;
         for(; end<s.length(); end++) {
        	 maxCount = Math.max(maxCount, ++counter[s.charAt(end)-'A']);
        	 while(end - start + 1 - maxCount > k) {
        		 counter[s.charAt(start)-'A']--;
        		 start++;
        	 }
        	 maxLength = Math.max(maxLength, end-start+1);
         }
    	 return maxLength;
     }
     
     
     public int lengthOfLongestSubstring(String s) {
         int start = 0, maxLength = 0;
         Set<Character> set = new HashSet<>();
         for(int end=0; end < s.length(); end++) {
        	 char c = s.charAt(end);
        	 if(!set.contains(c))
        		 set.add(c);
        	 else {
        		 while(s.charAt(start) != c) {
        			 set.remove(s.charAt(start));
        			 start++;
        		 }
        		 start++;
        	 }
        	 maxLength = Math.max(maxLength, end-start+1);
         }
    	 
    	 return maxLength;
     }
     
     
     public int longestSubstring(String s, int k) {
         char[] array = s.toCharArray();
         return longestSubstringHelp(array, 0, array.length, k);
     }
     
     private int longestSubstringHelp(char[] array, int start, int end, int k) {
    	 if(end - start < k)
    		 return 0;
    	 int[] counter = new int[26];
    	 for(int i=start; i<end; i++)
    		 counter[array[i]-'a']++;
    	 for(int i=0; i<26; i++) {
    		 if(counter[i] > 0 && counter[i] < k) {
    			 for(int j=start; j<end; j++) {
    				 if(array[j] == i+'a') {
    					 int left = longestSubstringHelp(array, start, j, k);
    					 int right = longestSubstringHelp(array, j+1, end, k);
    					 return Math.max(left, right);
    				 }
    			 }
    		 }
    	 }
    	 
    	 return end - start;
     }
     
     public boolean validTicTacToe(String[] board) {
         char[][] matrix = new char[3][3];
    	 for(int i=0; i<3; i++)
    		 matrix[i] = board[i].toCharArray();
    	 int X = 0, O = 0;
    	 for(int i=0; i<3; i++) {
    		 for(int j=0; j<3; j++) {
    			 char c = matrix[i][j];
    			 if(c == 'X')
    				 X++;
    			 else if(c == 'O')
    				 O++;
    		 }
    	 }
    	 if(X < O || X - O > 1)
    		 return false;
    	 if(X == O) {
    		 if(validTicTacToeIsWin(matrix, 'X'))
    			 return false;
    	 }
    	 else {
    		 if(validTicTacToeIsWin(matrix, 'O'))
    			 return false;
    	 }
    	 return true;
     }
     
     private boolean validTicTacToeIsWin(char[][] board, char c) {
    	 int count3 = 0, count4 = 0;
    	 for(int i=0; i<3; i++) {
    		 int count1 = 0, count2 = 0;
    		 for(int j=0; j<3; j++) {
    			 if(board[i][j] == c)
    				 count1++;
    			 if(board[j][i] == c)
    				 count2++;
    		 }
    		 if(count1 == 3 || count2 == 3)
    			 return true;
    		 if(board[i][i] == c)
    			 count3++;
    		 if(board[i][2-i] == c)
    			 count4++;
    	 }
    	 if(count3 == 3 || count4 == 3)
    		 return true;    	 
    	 return false;
     }
     
     
     public int findLUSlength(String[] strs) {
         int n = strs.length;
         if(n < 2)
        	 return 0;
         Arrays.sort(strs, new findLUSlengthSorter());
         for(int i=0; i<n; i++) {
        	 boolean end = true;
        	 String s2 = strs[i];
        	 for(int j=0; j<n; j++) {
        		 if(i == j)
        			 continue;
        		 String s1 = strs[j];
        		 if(findLUSlengthHelp(s1, s2)) {
        			end = false;
        			break;
        		 }
        	 }
        	 if(end)
        		 return strs[i].length();
         }
         return -1;
     }
     
     private boolean findLUSlengthHelp(String s1, String s2) {
    	 int index = 0;
    	 if(s2.length() > s1.length())
    		 return false;
    	 for(char c : s1.toCharArray()) {
    		 if(c == s2.charAt(index))
    			 index++;
    		 if(index == s2.length())
    			 break;
    	 }
    	 return index == s2.length();
     }
     
     class findLUSlengthSorter implements Comparator<String> {
		@Override
		public int compare(String o1, String o2) {
			return o2.length() - o1.length();
		}
    	 
     }
     
     public String predictPartyVictory(String senate) {
         int D = 0, R = 0;
         int slot_D = 0, slot_R = 0;
         char[] array = senate.toCharArray();
         for(char c : array)
        	 if(c == 'R')
        		 slot_R++;
        	 else
        		 slot_D++;
         boolean end = false;
         while(!end) {
        	 for(int i=0; i<array.length; i++) {
        		 char c = array[i];
        		 if(c == '\0')
        			 continue;
            	 if(c == 'R') {
            		if(D > 0) {
            			D--;
            			array[i] = '\0';
            			slot_R--;
            		}
            		else {
            			R++;
            		}
            	 }
            	 else {
            		 if(R > 0) {
            			 R--;
            			 array[i] = '\0';
            			 slot_D--;
            		 }
            		 else {
            			 D++;
            		 }
            	 }
             }
        	 if(slot_D == 0 || slot_R == 0)
        		 end = true;
         }
         
    	 if(slot_D < slot_R)
    		 return "Radiant";
    	 return "Dire";
     }
     
     public boolean rotateString(String A, String B) {
         String temp = A + A;
         return temp.indexOf(B) != -1;
     }
     
     public int lengthLongestPath(String input) {
         if(input == null || input.length() == 0)
        	 return 0;
         String[] lines = input.split("\n");
         Map<Integer, Integer> layer = new HashMap<>();
         layer.put(-1, 0);
         int max = 0;
         for(String line : lines) {
        	 int index = 0;
        	 int t = 0;
        	 while(line.charAt(index) == '\t') {
        		 index++;
        		 t++;
        	 }
        	 int length = line.length() - index + 1 + layer.get(t-1);
        	 layer.put(t, length);
        	 if(line.indexOf('.') != -1)
        		 max = Math.max(max, length);
         }
    	 
         if(max > 0)
        	 max--;
    	 return max;
     }
     
     
     public int getMaxRepetitions(String s1, int n1, String s2, int n2) {
         int repetition = 0;
         int index = 0;
         char[] array1 = s1.toCharArray();
         char[] array2 = s2.toCharArray();
         for(int i=0; i<n1; i++) {
        	 
        	 for(int j=0; j<array1.length; j++) {
        		 char c = array1[j];
        		 if(c == array2[index]) {
        			 index++;
        			 if(index == array2.length) {
        				 index = 0;
        				 repetition++;
        			 }
        		 }
        	 }
         }
         return repetition / n2;
     }
     
     private String[] morseCode = {".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."};
     public int uniqueMorseRepresentations(String[] words) {
         Set<String> set = new HashSet<>();
         for(String word : words) {
        	 StringBuilder sb = new StringBuilder();
        	 for(char c : word.toCharArray()) {
        		 sb.append(morseCode[c-'a']);
        	 }
        	 set.add(sb.toString());
         }
         return set.size();
     }
     
     
     public int[] numberOfLines(int[] widths, String S) {
         int[] answer = new int[2];
         answer[0] = 1;
         int line = 0;
         for(char c : S.toCharArray()) {
        	 int width = widths[c - 'a'];
        	 if(width + line > 100) {
        		 answer[0]++;
        		 line = width;
        	 }
        	 else {
        		 line += width;
        	 }
         }
         answer[1] = line;
         return answer;
     }
     
     
     public List<String> subdomainVisits(String[] cpdomains) {
         Map<String, Integer> map = new HashMap<>();
         for(String cpdomain : cpdomains) {
        	 String[] temp = cpdomain.split(" ");
        	 Integer count = Integer.parseInt(temp[0]);
        	 String domain = temp[1];
        	 while(true) {
        		 map.put(domain, map.getOrDefault(domain, 0) + count);
        		 int dot = domain.indexOf('.');
        		 if(dot == -1)
        			 break;
        		 domain = domain.substring(dot+1);
        	 }
         }
    	 List<String> list = new ArrayList<>();
    	 for(Map.Entry<String, Integer> entry : map.entrySet()) {
    		 String temp = entry.getValue()+" "+entry.getKey();
    		 list.add(temp);
    	 }
    	 return list;
     }
     
     public int expressiveWords(String S, String[] words) {
    	 if(S.length() == 0 || words.length == 0)
    		 return 0;
         int expressive = 0;
    	 List<Character> group_char = new ArrayList<>();
    	 List<Integer> group_count = new ArrayList<>();
    	 char ch = S.charAt(0);
    	 int ch_count = 1;
    	 for(int i=1; i<S.length(); i++) {
    		 char c =S.charAt(i);
    		 if(c == ch) {
    			 ch_count++;
    			 continue;
    		 }
    		 group_char.add(ch);
    		 group_count.add(ch_count);
    		 ch_count = 1;
    		 ch = c;
    	 }
    	 group_char.add(ch);
		 group_count.add(ch_count);
		 
		 System.out.println(group_char);
		 System.out.println(group_count);
		 
		 for(String word : words) {
			 if(word.length() == 0)
				 continue;
			 char pre = word.charAt(0);
			 int pre_count = 1;
			 int index = 0;
			 boolean stretchy = true;
			 for(int i=1; i<=word.length(); i++) {
				 char c = i == word.length() ? '\0' : word.charAt(i);
				 if(c == pre) {
					 pre_count++;
					 continue;
				 }
				 if(index == group_char.size()) {
					 stretchy = false;
					 break;
				 }
				 if(pre != group_char.get(index)) {
					 stretchy = false;
					 break;
				 }
				 int groupCount = group_count.get(index);
				 if(groupCount < pre_count) {
					 stretchy = false;
					 break;
				 }
				 if(groupCount != pre_count && groupCount < 3) {
					 stretchy = false;
					 break;
				 }
				 index++;
				 pre = c;
				 pre_count = 1;
			 }
			 if(stretchy && index == group_char.size()) {
				 expressive++;
			 }
		 }
		 
         return expressive;
     }
     
     public String mostCommonWord(String paragraph, String[] banned) {
         Set<String> used = new HashSet<>(Arrays.asList(banned));
    	 StringBuilder sb = new StringBuilder();
    	 String answer = "";
    	 int counter = 0;
    	 Map<String, Integer> map = new HashMap<>();
    	 for(int i=0; i<=paragraph.length(); i++) {
    		 char c = i == paragraph.length() ? '\0' : paragraph.charAt(i);
    		 if(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') {
    			 c = (char) (c < 'a' ? c + 32 : c);
    			 sb.append(c);
    		 }
    		 else {
    			 if(sb.length() == 0)
    				 continue;
    			 String word = sb.toString();
    			 sb = new StringBuilder();
    			 if(used.contains(word))
    				 continue;
    			 int temp = map.getOrDefault(word, 0) + 1;
    			 if(temp > counter) {
    				 counter = temp;
    				 answer = word;
    			 }
    			 map.put(word, temp);
    			 
    		 }
    	 }
    	 return answer;
     }
     
     public List<String> ambiguousCoordinates(String S) {
         int n = S.length();
         List<String> res = new ArrayList<>();
         for (int i = 1; i < n - 2; ++i) {
             List<String> A = f(S.substring(1, i + 1)), B = f(S.substring(i + 1, n - 1));
             for (String a : A) for (String b : B) res.add("(" + a + ", " + b + ")");
         }
         return res;
     }
     public List<String> f(String S) {
         int n = S.length();
         List<String> res = new ArrayList<>();
         if (n == 0 || (n > 1 && S.charAt(0) == '0' && S.charAt(n - 1) == '0')) return res;
         if (n > 1 && S.charAt(0) == '0') {
             res.add("0." + S.substring(1));
             return res;
         }
         res.add(S);
         if (n == 1 || S.charAt(n - 1) == '0') return res;
         for (int i = 1; i < n; ++i) res.add(S.substring(0, i) + '.' + S.substring(i));
         return res;
     }
     
     public int[] shortestToChar(String S, char C) {
         int n = S.length();
         int[] answer = new int[n];
         if(n == 0)
        	 return answer;
         TreeSet<Integer> set = new TreeSet<>();
         set.add(-100000);
         set.add(100000);
         for(int i=0; i<n; i++) {
        	 char c = S.charAt(i);
        	 if(c == C)
        		 set.add(i);
         }
         for(int i=0; i<n; i++) {
        	 int pre = set.floor(i);
        	 int next = set.ceiling(i);
        	 answer[i] = Math.min(i-pre, next - pre);
         }
         
         return answer;
     }
     
     public int minimumLengthEncoding(String[] words) {
         int min = 0;
         Map<Integer, Set<String>> map = new HashMap<>();
         for(String word : words) {
        	 int length = word.length();
        	 if(!map.containsKey(length))
        		 map.put(length, new HashSet<>());
        	 map.get(length).add(word);
         }
         Set<String> used = new HashSet<>();
         for(int i=7; i>0; i--) {
        	 Set<String> now = map.get(i);
        	 if(now == null)
        		 continue;
        	 for(String word : now) {
        		if(used.contains(word))
        			continue;
        		min += word.length() + 1;
        		for(int j=0; j<word.length(); j++) {
        			String sub = word.substring(j);
        			used.add(sub);
        		}
        	 }
        	 
         }
    	 
    	 return min;
     }
     
     public String toGoatLatin(String S) {
         String[] array = S.split(" ");
    	 StringBuilder sb = new StringBuilder();
    	 StringBuilder A = new StringBuilder();
    	 for(int i=0; i<array.length; i++) {
    		 String word = array[i];
    		 A.append('a');
    		 boolean vowel = false;
    		 char c = word.charAt(0);
    		 if(c == 'a' || c =='e' || c == 'i' || c == 'o' || c == 'u')
    			 vowel = true;
    		 c = (char)(c + 32);
    		 if(c == 'a' || c =='e' || c == 'i' || c == 'o' || c == 'u')
    			 vowel = true;
    		 if(vowel) {
    			 word = word + "ma";
    		 }
    		 else {
    			 c = word.charAt(0);
    			 word = word.substring(1, word.length()) + c + "ma";
    		 }
    		 sb.append(word).append(A.toString());
    		 if(i != array.length -1)
    			 sb.append(' ');
    	 }
    	 
    	 
    	 return sb.toString();
     }
     
     public String nearestPalindromic(String n) {
         int len = n.length();
         boolean even = len % 2 == 0;
         int i = even ? len/2-1 : len/2;
    	 long left = Long.parseLong(n.substring(0, i+1));
    	 List<Long> candidate = new ArrayList<>();
    	 candidate.add(getPalindrome(left, even));
    	 candidate.add(getPalindrome(left+1, even));
    	 candidate.add(getPalindrome(left-1, even));
    	 candidate.add((long)Math.pow(10, len-1)-1);
    	 candidate.add((long)Math.pow(10, len)+1);
    	 long diff = Long.MAX_VALUE, num = Long.parseLong(n), answer = 0;
    	 for(long cand : candidate) {
    		 if(cand == num)
    			 continue;
    		 if(Math.abs(cand - num) < diff) {
    			 diff = Math.abs(cand - num);
    			 answer = cand;
    		 }
    		 else if(Math.abs(cand - num) == diff) {
    			 answer = Math.min(answer, cand);
    		 }
    	 }
    	 return String.valueOf(answer);
     }
     
     private long getPalindrome(long left, boolean even) {
    	 long res = left;
    	 if(!even)
    		 left /= 10;
    	 while(left != 0) {
    		 res = res * 10 + left % 10;
    		 left /= 10;
    	 }
    	 
    	 return res;
     }
     
     public int uniqueLetterString(String S) {
         int sum = 0;
         int mod = 1000000007;
         char[] array = S.toCharArray();
    	 Map<Character, Integer> counter = new HashMap<>();
         for(int i=0; i<array.length; i++) {
        	 int temp = 0;
        	 int unique = 0;
        	 for(int j=i; j<array.length; j++) {
        		 char c = array[j];
        		 int t = counter.getOrDefault(c, 0) + 1;
        		 if(t == 1)
        			 unique++;
        		 else if(t == 2)
        			 unique--;
        		 temp = (temp + unique) % mod;
        		 counter.put(c, t);
        	 }
        	 sum = (sum + temp) % mod;
        	 counter.clear();
         }
         return sum;
     }
     
     public String maskPII(String S) {
    	 StringBuilder mask = new StringBuilder();
         int emailIndex = S.indexOf('@');
         if(emailIndex != -1) {
        	 int dotIndex = S.indexOf('.');
        	 String name1 = S.substring(0, emailIndex).toLowerCase();
        	 String name2 = S.substring(emailIndex+1, dotIndex).toLowerCase();
        	 String name3 = S.substring(dotIndex+1).toLowerCase();
        	 return name1.charAt(0)+"*****"+name1.charAt(name1.length()-1) + "@" + name2 +"."+name3;
         }
         StringBuilder phoneCode = new StringBuilder();
         StringBuilder countryCode = new StringBuilder();
         int phoneIndex = S.length()-1;
         for(; phoneIndex>=0; phoneIndex--) {
        	 char code = S.charAt(phoneIndex);
        	 if(code >= '0' && code <= '9')
        		 phoneCode.append(code);
        	 if(phoneCode.length() == 10)
        		 break;
         }
         for(int i=0; i<phoneIndex; i++) {
        	 char code = S.charAt(i);
        	 if(code >= '0' && code <= '9')
        		 countryCode.append(code);
         }
    	 if(countryCode.length() > 0) {
    		 mask.append('+');
    		 for(int i=0; i<countryCode.length(); i++)
    			 mask.append('*');
    		 mask.append('-');
    	 }
    	 mask.append("***-***-");
    	 for(int i=3; i>=0; i--)
    		 mask.append(phoneCode.charAt(i));
    	 return mask.toString();
     }
     
     
     public int uniqueLetterString2(String S) {
    	 int unique = 0;
    	 int mod = 1000000007;
    	 int N = S.length();
    	 int[][] index = new int[26][2];
    	 for(int i=0; i<26; i++)
    		 Arrays.fill(index[i], -1);
    	 for(int i=0; i<N; i++) {
    		 int c = S.charAt(i) - 'A';
    		 unique = (unique + ((i - index[c][1]) * (index[c][1] - index[c][0])) % mod) % mod;
    		 index[c] = new int[] {index[c][1], i};
    	 }
    	 for(int i=0; i<26; i++) {
    		 unique = (unique + ((N - index[i][1]) * (index[i][1] - index[i][0])) % mod) % mod;
    	 }
    	 return unique;
     }
     
     public String findReplaceString(String S, int[] indexes, String[] sources, String[] targets) {
         StringBuilder sb = new StringBuilder();
    	 int[] mark = new int[S.length()];
    	 String[] mark_str = new String[S.length()];
    	 int n = indexes.length;
    	 for(int i=0; i<n; i++) {
    		 int index = indexes[i];
    		 String source = sources[i];
    		 String target = targets[i];
    		 if(euqal(S, index, source)) {
    			 mark[index] = index + source.length();
    			 mark_str[index] = target;
    		 }
    	 }
    	 for(int i=0; i<S.length(); i++) {
    		 if(mark[i] == 0)
    			 sb.append(S.charAt(i));
    		 else {
    			 sb.append(mark_str[i]);
    			 i = mark[i] - 1;
    		 }
    	 }
    	 
    	 return sb.toString();
     }
     
     private boolean euqal(String S, int index, String source) {
    	 int len1 = source.length();
    	 int len2 = S.length();
    	 if(index  + len1 > len2)
    		 return false;
    	 for(int i=index; i<index+len1; i++)
    		 if(S.charAt(i) != source.charAt(i - index))
    			 return false;
    	 return true;
     }
     
     public int numSimilarGroups(String[] A) {
         int N = A.length;
         if(N == 0)
        	 return 0;
         int[] groups = new int[N];
         for(int i=0; i<N; i++) 
        	 groups[i] = i;
         for(int i=0; i<N; i++) {
        	 String a = A[i];
        	 int group_a = findGroup(groups, i);
        	 for(int j=0; j<N; j++) {
        		 String b = A[j];
        		 if(i == j)
        			 continue;
        		 if(!isSimilar(a, b))
        			 continue;
        		 int group_b = findGroup(groups, j);
        		 if(group_b != group_a) {
        			 groups[group_b] = group_a;
        		 }
        	 }
         }
         Set<Integer> set = new HashSet<>();
         for(int g : groups) {
        	 set.add(findGroup(groups, g));
         }
         return set.size();
     }
     
     private int findGroup(int[] groups, int i) {
    	 while(groups[i] != i) {
    		 groups[i] = groups[groups[i]];
    		 i = groups[i];
    	 }
    	 return i;
     }
     
     private boolean isSimilar(String a, String b) {
    	 int[] pos = {-1, -1};
    	 int count = 0;
    	 int n = a.length();
    	 for(int i=0; i<n; i++) {
    		 char c1 = a.charAt(i);
    		 char c2 = b.charAt(i);
    		 if(c1 == c2)
    			 continue;
    		 if(count == 2)
    			 return false;
    		 pos[count] = i;
    		 count++;
    	 }
    	 if(a.charAt(pos[0]) == b.charAt(pos[1]) && a.charAt(pos[1]) == b.charAt(pos[0]))
    		 return true;
    	 return false;
     }
     
     
     
     
     
     
     
     
     
    
    public static void main(String[] args) {
    	
    	
	}
    

    
    
    
    
	
}
