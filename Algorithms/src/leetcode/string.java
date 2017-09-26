package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
    
    
    public static void main(String[] args) {
		string test = new string();
		String time = "19:34";
		System.out.println("1111111v    "+test.nextClosestTime(time));
//		char [] time1 = {'1','9','3','4'};
//		char [] time2 = {'1','9','3','9'};
//		System.out.println(test.getTimeDiff(time1, time2));
	}
    
    
    
    
    
	
}
