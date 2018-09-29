package lintcode;

import java.util.AbstractMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
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
	
	
	public int shortestDistance(int[][] grid) {
        int m = grid.length, n = grid[0].length;
		@SuppressWarnings("unchecked")
		Set<Integer>[][] sets = (HashSet<Integer>[][])new HashSet[m][n];
		int build = 0;
		Queue<int[]> queue = new LinkedList<>();
		int[][] counter = new int[m][n];
		for(int i=0; i<m; i++) {
			for(int j=0; j<n; j++) {
				sets[i][j] = new HashSet<>();
				if(grid[i][j] == 1) {
					queue.add(new int[] {i, j, build});
					build++;
				}
			}
		}
		int distance = 0;
		int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}};
		while(!queue.isEmpty()) {
			int size = queue.size();
			distance++;
			while(size-- > 0) {
				int[] pos = queue.poll();
				int label = pos[2];
				for(int[] d : dirs) {
					int x = d[0] + pos[0];
					int y = d[1] + pos[1];
					if(x<0||x>=m||y<0||y>=n||grid[x][y]!=0)
						continue;
					if(!sets[x][y].add(label))
						continue;
					counter[x][y] += distance;
					queue.add(new int[] {x,y,label});
				}
			}
		}
		distance = Integer.MAX_VALUE;
		for(int i=0; i<m; i++) {
			for(int j=0; j<n; j++) {
				if(grid[i][j] == 0 && sets[i][j].size() == build) {
					distance = Math.min(distance, counter[i][j]);
				}
			}
		}
		return distance;
    }
	
	
	public int minTotalDistance(int[][] grid) {
		int m = grid.length, n = grid[0].length;
		BitSet[][] bitSets = new BitSet[m][n];
		int build = 0;
		Queue<short[]> queue = new LinkedList<>();
		int[][] counter = new int[m][n];
		for(int i=0; i<m; i++) {
			for(int j=0; j<n; j++) {
				BitSet bs = new BitSet();
				if(grid[i][j] == 1) {
					queue.add(new short[] {(short) i, (short) j, (short) build});
					bs.set(build);
					build++;
				}
				bitSets[i][j] = bs;
			}
		}
		int distance = 0;
		int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}};
		while(!queue.isEmpty()) {
			int size = queue.size();
			distance++;
			while(size-- > 0) {
				short[] pos = queue.poll();
				int label = pos[2];
				for(int[] d : dirs) {
					short x = (short) (d[0] + pos[0]);
					short y = (short) (d[1] + pos[1]);
					if(x<0||x>=m||y<0||y>=n)
						continue;
					if(bitSets[x][y].get(label))
						continue;
					bitSets[x][y].set(label);
					counter[x][y] += distance;
					queue.add(new short[] {(short)x,(short)y,(short)label});
				}
			}
		}
		distance = Integer.MAX_VALUE;
		for(int i=0; i<m; i++) {
			for(int j=0; j<n; j++) {
				BitSet bs = bitSets[i][j];
				if( bs.length() == build) {
					distance = Math.min(distance, counter[i][j]);
				}
			}
		}
		return distance;
    }
	
	  public int minTotalDistance2(int[][] grid) {
		  int row = grid.length;
		  int col = grid[0].length;

		      int distance = Integer.MAX_VALUE;
		      for (int i = 0; i < row; i++) {
		          for (int j = 0; j < col; j++) {
		              int temp = helper(grid, i, j);
		              distance = Math.min(temp, distance);
		          }
		      }
		      return distance;
		  }

		  private int helper(int[][] grid,
		                     int a,
		                     int b) {
		      int row = grid.length;
		      int col = grid[0].length;
		      int result = 0;
		      for (int i = 0; i < row; i++) {
		          for (int j = 0; j < col; j++) {
		              if (grid[i][j] == 1) {
		                  result += Math.abs(a - i) + Math.abs(b - j);
		              }
		          }
		      }
		      return result;
		  }
	
	
	public int minTotalDistance3(int[][] grid) {
		int distance = 0;
		int row = grid.length, col = grid[0].length;
		List<Integer> X = new ArrayList<>();
		List<Integer> Y = new ArrayList<>();
		for(int i=0; i<row; i++) {
			for(int j=0; j<col; j++) {
				if(grid[i][j] == 1) {
					X.add(i);
					Y.add(j);
				}
			}
		}
		Collections.sort(X);
		Collections.sort(Y);
		for(int i=0; i<X.size(); i++) {
			distance += Math.abs(X.get(X.size()/2) - X.get(i));
			distance += Math.abs(Y.get(Y.size()/2) - Y.get(i));
		}
		return distance;
	}
	
		int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}};
		
	   class Island {
		   int x, y;
		   public Island(int x, int y) {
			this.x = x;
			this.y = y;
		}
		 @Override
		public String toString() {
			return "["+x+","+y+"]";
		}
	   }
		
	   public int numberofDistinctIslands(int[][] grid) {
	       Set<String> set = new HashSet<>();
	       if(grid == null || grid.length == 0)
	    	   return 0;
	       int answer = 0;
	       for(int i=0; i<grid.length; i++) {
	    	   for(int j=0; j<grid[0].length; j++) {
	    		   if(grid[i][j] == 0)
	    			   continue;
	    		   grid[i][j] = 0;
	    		   List<Island> list = new ArrayList<>();
	    		   list.add(new Island(i, j));
	    		   numberofDistinctIslandsDFS(grid, i, j, list);
	    		   norm(list);
	    		   String str = list.toString();
	    		   if(set.add(str)) {
	    			   answer++;
	    			   numberofDistinctIslandsGetTransformStr(list, set);
	    		   }
	    	   }
	       }
	       
	       return answer;
	   }
	   
	   private void numberofDistinctIslandsDFS(int[][] grid, int i, int j, List<Island> list) {
		   for(int[] d : dirs) {
			   int x = d[0] + i;
			   int y = d[1] + j;
			   if(x<0 || x>=grid.length || y<0 || y>=grid[0].length || grid[x][y] == 0)
				   continue;
			   grid[x][y] = 0;
			   list.add(new Island(x, y));
			   numberofDistinctIslandsDFS(grid, x, y, list);
		   }
	   }
	   
	   class IslandComparator implements Comparator<Island> {

		@Override
		public int compare(Island o1, Island o2) {
			if(o1.x == o2.x)
				return o1.y - o2.y;
			return o1.x - o2.x;
		}
		   
	   }
	   
	   private void norm(List<Island> list) {
		   Collections.sort(list, new IslandComparator());
		   System.out.println(list);
		   for(int i=1; i<list.size(); i++) {
			   list.get(i).x = list.get(i).x - list.get(0).x;
			   list.get(i).y = list.get(i).y - list.get(0).y;
		   }
		   list.get(0).x = 0;
		   list.get(0).y = 0;
	   }
	   
	   private void numberofDistinctIslandsGetTransformStr(List<Island> list, Set<String> set){
		   List<List<Island>> list_list = new ArrayList<>();
		   for(int i=0; i<7; i++) {
			   list_list.add(new ArrayList<>());
		   }
		   for(Island island : list) {
			   int x = island.x;
			   int y = island.y;
			   list_list.get(0).add(new Island(x, -y));
			   list_list.get(1).add(new Island(-x, y));
			   list_list.get(2).add(new Island(-x, -y));
			   list_list.get(3).add(new Island(y, -x));
			   list_list.get(4).add(new Island(-y, x));
			   list_list.get(5).add(new Island(-y, -x));
			   list_list.get(6).add(new Island(y, x));
		   }
		   for(List<Island> L : list_list) {
			   norm(L);
			   set.add(L.toString());
		   }
	   }
	
	   
	   //"itwasthebestoftimes"
	  // "ittwaastthhebesttoofttimes"
	   
	   public boolean wordPatternMatch(String pattern, String str) {
	        pattern = buildPattern(pattern, null);
	        if(wordPatternMatch(str, pattern,  new HashMap<>(), new HashSet<>()))
			   return true;
		   return false;
	   } 
	   
	   private boolean wordPatternMatch(String str, String pattern, Map<Character, String> map, Set<String> set) {
		   if(pattern.length() == 0) {
			   return str.length() == 0;
		   }
		   char patternChar = pattern.charAt(0);
		   if(map.containsKey(patternChar)) {
			   if(!str.startsWith(map.get(patternChar)))
				   return false;
			   return wordPatternMatch(str.substring(map.get(patternChar).length()), pattern.substring(1), map, set);
		   }
		   for(int i=0; i<str.length(); i++) {
			   String sub = str.substring(0, i+1);
			   if(set.contains(sub))
				   continue;
			   map.put(patternChar, sub);
			   set.add(sub);
			   if(wordPatternMatch(str.substring(i+1), pattern.substring(1), map, set))
				   return true;
			   map.remove(patternChar);
			   set.remove(sub);
		   }
		   return false;
	   }
	   	   
	   @SuppressWarnings("unused")
	private boolean wordPatternMatchBT(String str, int index, String pattern, List<String> list, Map<Character, String> map, Map<String, Character> remap) {
		   if(index == str.length()) {
			   String p = buildPattern(null, list);
			   return p.equals(pattern);
		   }
		   if(list.size() >= pattern.length())
			   return false;
		   StringBuilder sb = new StringBuilder();
		   for(int i=index; i<str.length(); i++) {
			   char c = str.charAt(i);
			   char p = pattern.charAt(list.size());
			   sb.append(c);
			   String symble = map.get(p);
			   if(symble != null) {
				   if(sb.length() < symble.length())
					   continue;
				   if(sb.length() == symble.length()) {
					   if(!symble.equals(sb.toString())) {
						   return false;
					   }
				   }
				   if(sb.length() > symble.length())
					   return false;
			   }
			   String key = sb.toString();
			   Character re_p = remap.get(key);
			   if(re_p != null && re_p != p)
				   return false;
			   map.put(p, key);
			   remap.put(key, p);
			   list.add(key);
			   if(wordPatternMatchBT(str, i+1, pattern, list, map, remap)) {
				   list.remove(list.size()-1);
				   return true;
			   }
			   list.remove(list.size()-1);
			   map.remove(p);
			   remap.remove(key);
		   }
		   
		   return false;
	   }
	   
	   private String buildPattern(String word, List<String> list) {
			StringBuilder sb = new StringBuilder();
			if(word != null) {
				Map<Character, Integer> map = new HashMap<>();
				for(int i=0; i<word.length(); i++) {
					char c = word.charAt(i);
					if(!map.containsKey(c)) {
						map.put(c, map.size());
					}
					sb.append(map.get(c));
				}
			}
			else {
				Map<String, Integer> map = new HashMap<>();
				for(String s : list) {
					if(!map.containsKey(s)) {
						map.put(s, map.size());
					}
					sb.append(map.get(s));
				}
			}
			return sb.toString();
		}
	   
	public List<String> wordBreak(String s, Set<String> wordDict) {
		List<String> list = new ArrayList<>();
		int[] map = new int[26];
		for(char c : s.toCharArray()) {
			map[c - 'a'] = 1;
		}
		for(String word : wordDict) {
			for(char c : word.toCharArray()) {
				map[c - 'a'] = 0;
			}
		}
		for(int i=0; i<26; i++) {
			if(map[i] == 1) {
				return list;
			}
		}
		wordBreakBT(s, wordDict, new ArrayList<>(), list);
		return list;
    }
	
	private void wordBreakBT(String s, Set<String> dict, List<String> buffer, List<String> list) {
		if(s.length() == 0) {
			StringBuilder sb = new StringBuilder();
			for(int i=0; i<buffer.size(); i++) {
				if(i == buffer.size()-1) {
					sb.append(buffer.get(i));
				}
				else {
					sb.append(buffer.get(i) + " ");
				}
			}
			list.add(sb.toString());
			return;
		}
		for(int i=0; i<s.length(); i++) {
			String word = s.substring(0, i+1);
			if(dict.contains(word)) {
				buffer.add(word);
				wordBreakBT(s.substring(word.length()), dict, buffer, list);
				buffer.remove(buffer.size()-1);
			}
		}		
	}
	   
	   
	public int findDuplicate(int[] nums) {
		int n = nums.length-1;
		int start = 1, end = n;
		while(start < end) {
			int mid = (start + end) / 2;
			int count = 0;
			for(int num : nums) {
				if(num <= mid)
					count++;
			}
			if(count > mid)
				end = mid;
			else
				start = mid + 1;
		}
		return end;
    } 
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	
	public static void main(String[] args) {
		
	}
	
	
	
	
	
	
	

}
