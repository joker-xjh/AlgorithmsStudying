package leetcode;

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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	
}
