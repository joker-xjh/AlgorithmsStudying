package lintcode;

import java.util.AbstractMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
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
	
	
	public int kSum(int[] A, int k, int target) {
		int len = A.length;  
        
        int[][][] dp = new int[len+1][k+1][target+1];  
          
        for(int i=0;i<len;++i)  
            if(A[i]<=target)  
                for(int j=i+1;j<=len;++j)  
                    dp[j][1][A[i]] = 1;  
              
        for(int i=1;i<=len;++i)  
            for(int j=2;j<=k&&j<=i;++j)  
                for(int s=1;s<=target;++s)  
                    dp[i][j][s] = dp[i-1][j][s] + (s>A[i-1]?dp[i-1][j-1][s-A[i-1]]:0);  
                      
        return dp[len][k][target]; 
    }
	
	
	
	public int maxSubArray(int[] nums, int k) {
		if(k == 0)
			return 0;
		int n = nums.length;
		if(k > n)
			return 0;
		int[][] dp = new int[k+1][n+1];
		for(int i=0; i<=k; i++)
			for(int j=0; j<=n; j++)
				dp[i][j] = Integer.MIN_VALUE;
		int[] preSum = new int[n+1];
		int sum = 0;
		for(int i=1; i<=n; i++) {
			sum += nums[i-1];
			preSum[i] = sum;
		}
		return maxSubArrayMemo(nums, k, n, dp, preSum);
	}
	
	private int maxSubArrayMemo(int[] nums, int k, int n, int[][] dp, int[] preSum) {
		if(k > n || k == 0)
			return 0;
		if(k == n)
			return preSum[n];
		if(dp[k][n] > 0)
			return dp[k][n];
		int sum = 0;
		for(int i=n; i>=k; i--) {
			sum += nums[i-1];
			int temp = sum + maxSubArrayMemo(nums, k-1, i-1, dp, preSum);
			dp[k][n] = Math.max(temp, dp[k][n]);
		}
		dp[k][n] = Math.max(maxSubArrayMemo(nums, k, n-1, dp, preSum), dp[k][n]);
		return dp[k][n];
	}
	
	public String alienOrder(String[] words) {
		if(words == null || words.length == 0)
			return "";
		int[] indegree = new int[26];
		Map<Character, Set<Character>> graph = new HashMap<>();
		Set<String> set = new HashSet<>();
		for(int i=0; i<26; i++) {
			char c = (char) (i + 'a');
			graph.put(c, new HashSet<>());
		}
		Arrays.fill(indegree, -1);
		for(String word : words) {
			if(word.length() == 1) {
				int i = word.charAt(0) - 'a';
				indegree[i] = 0;
			}
			else {
				for(int i=1; i<word.length(); i++) {
					int cur = word.charAt(i) - 'a';
					int pre = word.charAt(i-1) - 'a';
					if(cur == pre)
						continue;
					String edge = pre + "," + cur;
					if(!set.add(edge))
						continue;
					graph.get(word.charAt(i-1)).add(word.charAt(i));
					if(indegree[cur] == -1) {
						indegree[cur] = 1;
					}
					else {
						indegree[cur]++;
					}
					if(indegree[pre] == -1) {
						indegree[pre] = 0;
					}
				}
			}
		}
		
		StringBuilder sb = new StringBuilder();
		PriorityQueue<Character> pq = new PriorityQueue<>();
		int count = 0;
		for(int i=0; i<26; i++) {
			int num = indegree[i];
			if(num != -1) {
				count++;
			}
			if(num == 0) {
				pq.add((char)(i+'a'));
			}
		}
		while(!pq.isEmpty()) {
			char c = pq.poll();
			sb.append(c);
			for(char next : graph.get(c)) {
				int i = next - 'a';
				indegree[i]--;
				if(indegree[i] == 0) {
					pq.offer(next);
				}
			}
		}
		if(sb.length() == count)
			return sb.toString();
        return "";
    }
	
	
	
	public String alienOrder2(String[] words) {
		if(words == null || words.length == 0)
			return "";
		PriorityQueue<Character> pq = new PriorityQueue<>();
		int[] indegree = new int[26];
		Arrays.fill(indegree, -1);
		Map<Character, Set<Character>> graph = new HashMap<>();
		for(int i=0; i<26; i++) {
			char c = (char) (i + 'a');
			graph.put(c, new HashSet<>());
		}
		if(words.length == 1) {
			for(char c : words[0].toCharArray()) {
				int i = c - 'a';
				indegree[i] = 0;
			}
		}
		else {
			for(int i=1; i<words.length; i++) {
				String pre_word = words[i-1];
				String cur_word = words[i];
				int min = Math.min(pre_word.length(), cur_word.length());
				int max = Math.max(pre_word.length(), cur_word.length());
				for(int j=0; j<min; j++) {
					char pre_c = pre_word.charAt(j);
					char cur_c = cur_word.charAt(j);
					if(indegree[pre_c - 'a'] == -1) {
						indegree[pre_c - 'a'] = 0;
					}
					if(pre_c == cur_c)
						continue;
					graph.get(pre_c).add(cur_c);
					if(indegree[cur_c - 'a'] == -1) {
						indegree[cur_c - 'a'] = 1;
					}
					else {
						indegree[cur_c - 'a']++;
					}
					
					for(int k=j+1; k<max; k++) {
						if(k < pre_word.length()) {
							if(indegree[pre_word.charAt(k) - 'a'] == -1) {
								indegree[pre_word.charAt(k) - 'a'] = 0;
							}
						}
						if(k < cur_word.length()) {
							if(indegree[cur_word.charAt(k) - 'a'] == -1) {
								indegree[cur_word.charAt(k) - 'a'] = 0;
							}
						}
					}
					break;
				}
			}
		}
		int count = 0;
		for(int i=0; i<26;i++) {
			if(indegree[i] != -1) {
				count++;
			}
			if(indegree[i] == 0) {
				char c = (char) (i + 'a');
				pq.add(c);
			}
		}
		StringBuilder sb = new StringBuilder();
		while(!pq.isEmpty()) {
			char c = pq.poll();
			sb.append(c);
			for(char next : graph.get(c)) {
				int i = next - 'a';
				if(--indegree[i] == 0) {
					pq.add(next);
				}
			}
		}
		if(sb.length() == count)
			return sb.toString();
		return "";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		Hard test = new Hard();
		String[] words = {"wrt","wrf","er","ett","rftt"};
		test.alienOrder2(words);
	}
	
	
	
	
	
	
	

}
