package leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

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
    
   
    
    public static void main(String[] args) {
		
	}
    
    
    
    
    
    
	
}
