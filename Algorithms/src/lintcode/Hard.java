package lintcode;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Hard {
	
	public List<Map.Entry<Integer, Double>> dicesSum(int n) {
        List<Map.Entry<Integer, Double>> list = new ArrayList<>();
        long[][] dp = new long[n+1][6*n+1];
        double total = Math.pow(6, n);
        dp[1][1] = 1; dp[1][2] = 2; dp[1][3] = 1; dp[1][4] = 1; dp[1][5] = 1; dp[1][6] = 1;
        for(int i=2; i<=n; i++) {
        	for(int j=i; j<= i* 6; j++) {
        		if(j - 1 > 0) {
        			dp[i][j] += dp[i-1][j-1];
        		}
        		if(j - 2 > 0) {
        			dp[i][j] += dp[i-1][j-2];
        		}
        		if(j - 3 > 0) {
        			dp[i][j] += dp[i-1][j-3];
        		}
        		if(j - 4 > 0) {
        			dp[i][j] += dp[i-1][j-4];
        		}
        		if(j - 5 > 0) {
        			dp[i][j] += dp[i-1][j-5];
        		}
        		if(j - 6 > 0) {
        			dp[i][j] += dp[i-1][j-6];
        		}
        	}
        }
        for(int i=n; i<= n*6; i++) {
        	list.add(new AbstractMap.SimpleEntry<Integer, Double>(i, dp[n][i] / total));
        }
        return list;
    }
	
	
	class LFUCache {
		
		private int capacity;
		private Map<Integer, Integer> content;
		private Map<Integer, Integer> counter;
		private Map<Integer, Set<Integer>> frequent;
		private int minFre;
	    /*
	    * @param capacity: An integer
	    */public LFUCache(int capacity) {
	        this.capacity = capacity;
	        content = new HashMap<>();
	        counter = new HashMap<>();
	        frequent = new HashMap<>();
	        minFre = 1;
	        frequent.put(minFre, new LinkedHashSet<>());
	    }

	    /*
	     * @param key: An integer
	     * @param value: An integer
	     * @return: nothing
	     */
	    public void set(int key, int value) {
	    	if(content.containsKey(key)) {
	        	content.put(key, value);
	        	get(key);
	        	return;
	        }
	        
	    	if(content.size() >= capacity) {
	    		int deleteKey = frequent.get(minFre).iterator().next();
	        	frequent.get(minFre).remove(deleteKey);
	        	counter.remove(deleteKey);
	        	content.remove(deleteKey);
	    	}
	        content.put(key, value);
        	counter.put(key, 1);
        	minFre = 1;
        	frequent.get(minFre).add(key);
	    }

	    /*
	     * @param key: An integer
	     * @return: An integer
	     */
	    public int get(int key) {
	        if(!content.containsKey(key))
	        	return -1;
	        int value = content.get(key);
	    	int counter_num = counter.get(key);
	    	counter.put(key, counter_num+1);
	    	frequent.get(counter_num).remove(key);
	    	if(!frequent.containsKey(counter_num+1))
	    		frequent.put(counter_num+1, new LinkedHashSet<>());
	    	frequent.get(counter_num+1).add(key);
	        frequent.get(counter_num).remove(key);
	        if(frequent.get(counter_num).isEmpty() && counter_num == minFre)
	        	minFre++;
	    	return value;
	    }
	}
	
	
	public int atoi(String str) {
        long number = 0;
        if(str == null || str.length() == 0)
        	return (int) number;
        str = str.trim();
        char first = str.charAt(0);
        boolean negative = false;
        int start = 0;
        if(first == '-') {
        	negative = true;
        	start++;
        }
        else if(first == '+') {
        	start++;
        }
        else if(!( first >= '0' && first <= '9') )
        	return (int) number;
        for(int i=start; i<str.length(); i++) {
        	char c = str.charAt(i);
        	int num = c - '0';
        	if(c == '.')
        		return (int) number;
        	if(c >= '0' && c <= '9') {
        		if(negative) {
        			if(number > (2147483648L-num)/10)
        				return Integer.MIN_VALUE;
        		}
        		else {
        			if(number > (Integer.MAX_VALUE - num) / 10)
        				return Integer.MAX_VALUE;
        		}
    			number = number * 10 + num;
        	}
        	else
        		break;
        }
        
        return negative ? ((int) -number) : (int) number;
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
