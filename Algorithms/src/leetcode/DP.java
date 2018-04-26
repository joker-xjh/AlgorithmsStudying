package leetcode;

import java.util.Arrays;


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


public class DP {
	
	
    public int removeBoxes(int[] boxes) {
        int n = boxes.length;
        int[][][] dp = new int[n][n][n];
        return removeBoxes(boxes, 0, n-1, 0, dp);
    }
    
    private int removeBoxes(int[] boxes, int i, int j, int k, int[][][] dp) {
    	if(i>j)
    		return 0;
    	if(dp[i][j][k] > 0)
    		return dp[i][j][k];
    	int res = removeBoxes(boxes, i+1, j, 0, dp) + (k+1) * (k+1);
    	
    	for(int m=i+1; m<=j; m++) {
    		if(boxes[i] == boxes[m])
        		res = Math.max(res, removeBoxes(boxes, i+1, m-1, 0, dp) + removeBoxes(boxes, m, j, k+1, dp));

    	}
    	
    	dp[i][j][k] = res;
    	return res;
    }
    
    
    public int maxCoins(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n][n];
        return maxCoins(nums, 0, n-1, dp);
    }
    
    private int maxCoins(int[] nums, int i, int j, int[][] dp) {
    	if(i>j)
    		return 0;
    	if(i==j)
    		return nums[i];
    	if(dp[i][j] > 0)
    		return dp[i][j];
    	int res = -1;
    	
    	int[] copy = nums.clone();
    	
    	for(int k=i; k<=j; k++) {
    		int score = 0;
    		if(k==i) {
    		    score = nums[k] * nums[k+1];
    			score += maxCoins(nums,i+1,j,dp);
    		}
    		else if(k==j) {
    			score = nums[k] * nums[k-1];
    			score += maxCoins(nums, i, j-1, dp);
    		}
    		else {
    			score = nums[k] * nums[k-1] * nums[k+1];    			
    			for(int x=k; x>i; x--)
    				nums[x] = nums[x-1];
    			score += maxCoins(nums,i+1,j,dp);
    			for(int x=i;x<=k;x++)
    				nums[x] = copy[x];
    		}
    		res = Math.max(res, score);
    	}
    	dp[i][j] = res;
    	return res;
    }
    
    
    
    public int maxCoins2(int[] nums) {
        
    	int n = nums.length;
    	int[] copy = new int[n+2];
    	for(int i=0;i<n;i++)
    		copy[i+1] = nums[i];
    	copy[0] = copy[n+1] = 1;
    	int[][] dp = new int[n+2][n+2];
    	return maxCoins(copy, dp, 0, n+1);
    }
    
    
    private int maxCoins(int[] nums, int[][] dp, int left, int right) {
    	if(left+1 == right)
    		return 0;
    	if(dp[left][right] > 0)
    		return dp[left][right];
    	int res = 0;
    	
    	for(int i=left+1; i<right; i++) {
    		res = Math.max(res, nums[left]*nums[i]*nums[right]+
    				maxCoins(nums, dp, left, i) + maxCoins(nums, dp, i, right));
    	}
    	
    	dp[left][right] = res;
    	return res;
    }
    
    public int wiggleMaxLength(int[] nums) {
        if( nums.length<2)
        	return nums.length;
        int k=0;
        while(k<nums.length-1 && nums[k] == nums[k+1])
        	k++;
        if(k == nums.length-1)
        	return 1;
        int result = 2;
        boolean b = nums[k] < nums[k+1];
        for(int i=k+1; i<nums.length-1; i++) {
        	if(b && nums[i+1] < nums[i]) {
        		b = !b;
        		result++;
        	}
        	else if(!b && nums[i] < nums[i+1]) {
        		b = !b;
        		result++;
        	}
        }
        
    	return result;
    }
    
    
    
    private Map<Integer, Integer> coinChange = new HashMap<>();
    
    public int coinChange(int[] coins, int amount) {
        if(amount == 0)
        	return 0;
        if(coinChange.containsKey(amount))
        	return coinChange.get(amount);
        int n = amount + 1;
        
        for(int coin:coins) {
        	int cur = 0;
        	if(coin <= amount) {
        		int next = coinChange(coins, amount-coin);
        		if(next >=0)
        			cur = next + 1;
        	}
        	if(cur > 0)
        		n = Math.min(n, cur);
        }
        //Object
        int ans = n == amount+1 ? -1 : n;
        coinChange.put(amount, ans);
        return ans;
    }
    
    
    public int minimumDeleteSum(String s1, String s2) {
        int[][] dp = new int[s1.length()+1][s2.length()+1];
        for(int i=1; i<=s1.length(); i++) {
        	dp[i][0] = s1.charAt(i-1) + dp[i-1][0];
        }
        for(int i=1; i<=s2.length(); i++) {
        	dp[0][i] = s2.charAt(i-1) + dp[0][i-1];
        }
        for(int i=1; i<=s1.length(); i++) {
        	for(int j=1; j<=s2.length(); j++) {
        		int cost = s1.charAt(i-1) == s2.charAt(j-1) ? 0 : s1.charAt(i-1) + s2.charAt(j-1);
        		dp[i][j] = Math.min(dp[i-1][j]+s1.charAt(i-1), dp[i][j-1]+s2.charAt(j-1));
        		dp[i][j] = Math.min(dp[i][j], dp[i-1][j-1]+cost);
        	}
        }
        return dp[s1.length()][s2.length()];
    }
    
    
    
  
    
    
    public int findLength(int[] A, int[] B) {
        int max = 0;
        int[][] dp = new int[A.length+1][B.length+1];
        for(int i=1; i<=A.length; i++) {
        	int num1 = A[i-1];
        	for(int j=1; j<=B.length; j++) {
        		int num2 = B[j-1];
        		if(num1 == num2)
        			dp[i][j] = dp[i-1][j-1] + 1;
        		max = Math.max(max, dp[i][j]);
        	}
        }
        return max;
    }
    
    
    public int countPalindromicSubsequences(String S) {
        int len = S.length();
        int[][] dp = new int[len][len];
        char[] array = S.toCharArray();
        for(int i=0; i<len; i++)
        	dp[i][i] = 1;
        for(int distance = 1; distance < len; distance++) {
        	for(int i=0; i<len-distance; i++) {
        		int j = i + distance;
        		if(array[i] == array[j]) {
        			int low = i+1;
        			int high = j-1;
        			while(low <= high && array[low] != array[i])
        				low++;
        			while(low <= high && array[high] != array[i])
        				high--;
        			if(low > high) {
        				dp[i][j] = dp[i+1][j-1]*2 + 2;
        			}
        			else if(low == high) {
        				dp[i][j] =dp[i+1][j-1]*2 + 1;
        			}
        			else {
        				dp[i][j] = dp[i+1][j-1]*2 - dp[low+1][high-1];
        			}
        		}
        		else {
        			dp[i][j] = dp[i+1][j] + dp[i][j-1] - dp[i+1][j-1];
        		}
        		dp[i][j] = dp[i][j] < 0 ? dp[i][j] + 1000000007 : dp[i][j] % 1000000007 ; 
        	}
        }
    	
    	return dp[0][len-1];
    }
    
    
    
    public int strangePrinter(String s) {
        int n = s.length();
        if(n == 0)
        	return 0;
        int[][] dp = new int[101][101];
        for(int i=0; i<n; i++)
        	dp[i][i] = 1;
        for(int i=1; i<n; i++) {
        	for(int j=0; j<n-i; j++) {
        		dp[j][j+i] = i + 1;
        		for(int k=j+1; k<=j+i; k++) {
        			int temp = dp[j][k-1] + dp[k][j+i];
        			if(s.charAt(k-1) == s.charAt(j+i))
        				temp--;
        			dp[j][j+i] = Math.min(dp[j][j+i], temp);
        		}
        	}
        }
        return dp[0][n-1];
    }
    
    
    public int deleteAndEarn(int[] nums) {
        int n = 10001;
        int[] values = new int[n];
        for(int num : nums)
        	values[num] += num;
        int take = 0, skip = 0;
        for(int i=0; i<n; i++) {
        	int temp = skip + values[i];
        	skip = Math.max(skip,  take);
        	take = temp;
        }
        return Math.max(take, skip);
    }
    
    
    public int numDecodings(String s) {
        if(s == null || s.length() == 0)
        	return 0;
        if(s.charAt(0) == '0'){
            return 0;
        }
        long[] dp = new long[s.length()+1];
        dp[0] = 1;
        dp[1] = s.charAt(0) == '*' ? 9 : 1;
        for(int i=2; i<=s.length(); i++) {
        	char first = s.charAt(i-2);
        	char second = s.charAt(i-1);
        	if(second == '*') {
        		dp[i] += 9 * dp[i-1];
        	}
        	else if(second > '0'){
        		dp[i] += dp[i-1];
        	}
        	
        	if(first == '*') {
        		if(second == '*')
        			dp[i] += dp[i-2] * 15;
        		else if(second <= '6')
        			dp[i] += dp[i-2] * 2;
        		else 
        			dp[i] += dp[i-2];
        	}
        	else if(first == '1') {
        		if(second == '*')
        			dp[i] += dp[i-2] * 9;
        		else
        			dp[i] += dp[i-2];
        	}
        	else if(first == '2') {
        		if(second == '*')
        			dp[i] += 6 * dp[i-2];
        		else if(second <= '6')
        			dp[i] += dp[i-2];
        	}
        	
        	dp[i] %= 1000000007;
        }
    	
    	return (int) dp[s.length()];
    }
    
    
    public int numDecodings1(String s) {
    	if(s == null || s.length() == 0)
    		return 0;
    	if(s.charAt(0) == '0')
    		return 0;
    	int n = s.length();
    	int[] dp = new int[n+1];
    	dp[0] = 1;
    	dp[1] = 1;
    	char[] array = s.toCharArray();
    	for(int i=2; i<=n; i++) {
    		char first = array[i-2];
    		char second = array[i-1];
    		if(second > '0')
    			dp[i] += dp[i-1];
    		if(first == '1') {
    			dp[i] += dp[i-2];
    		}
    		else if(first == '2') {
    			if(second <= '6')
    				dp[i] += dp[i-2];
    		}
    		
    	}
    	
    	return dp[n];
    }
    
    
    public int minCostClimbingStairs(int[] cost) {
        int[] dp = new int[cost.length+1];
        for(int i=2; i<=cost.length; i++)
        	dp[i] = Math.min(dp[i-1] + cost[i-1],  dp[i-2] + cost[i-2]);
        return dp[cost.length];
    }
 
    
    public int maxProfit(int[] prices) {
    	if(prices == null || prices.length < 2)
    		return 0;
        int profit = 0;
        int n = prices.length;
        int[] buy = new int[n];
        int[] sell = new int[n];
        buy[0] = -prices[0];
        buy[1] = -Math.min(prices[0], prices[1]);
        sell[1] = Math.max(sell[0], buy[0] + prices[1]);
        profit = Math.max(buy[1], sell[1]);
        for(int i=2; i<n; i++) {
        	int price = prices[i];
        	buy[i] = Math.max(buy[i-1], sell[i-2] - price);
        	sell[i] = Math.max(sell[i-1], buy[i-1] + price);
        	profit = Math.max(buy[i], sell[i]);
        }
        
        return profit;
    }
    
    
    public int maxProfit(int[] prices, int fee) {
        int profit= 0;
        int n = prices.length;
        if(n < 2)
        	return profit;
        int[] buy = new int[n];
        int[] sell = new int[n];
        buy[0] = -prices[0];
        
        for(int i=1; i<n; i++) {
        	int price = prices[i];
        	buy[i] = Math.max(buy[i-1], sell[i-1] - price);
        	sell[i] = Math.max(sell[i-1], buy[i-1] + price - fee);
        	profit = Math.max(buy[i], sell[i]);
        }
        return profit;
    }
    
    
    
    public double knightProbability(int N, int K, int r, int c) {
        double[][] dp = new double[N][N];
        double[][] next = new double[N][N];
        dp[r][c] = 1;
        int[][] dirs = {{-2,-1},{-1,-2},{1,-2},{2,-1},{2,1},{1,2},{-1,2},{-2,1}};
        for(int a=0; a<K; a++) {
        	for(int i=0; i<N; i++) {
        		for(int j=0; j<N; j++) {
        			for(int[] dir : dirs) {
        				int x = i + dir[0];
        				int y = j + dir[1];
        				if(x<0 || x>=N || y<0 || y>=N)
        					continue;
        				next[i][j] += dp[x][y] ;
        			}
        		}
        	}
        	knightProbabilityFillZero(dp);
        	double[][] temp = next;
        	next = dp;
        	dp = temp;
        }
        double counter = 0;
        for(int i=0; i<N; i++) {
        	for(int j=0; j<N; j++) {
        		counter += dp[i][j];
        	}
        }
        double total = Math.pow(8, K);
    	return counter / total;
    }
    
    private void knightProbabilityFillZero(double[][] array) {
    	for(int i=0; i<array.length; i++)
    		for(int j=0; j<array.length; j++)
    			array[i][j] = 0;
    }
    
    static void print(double[][] array) {
    	for(double[] temp : array)
    		System.out.println(Arrays.toString(temp));
    }
    
    
    public int findMaxForm(String[] strs, int m, int n) {
    	int count = 0;
    	Map<String, int[]> cost = new HashMap<>();
    	boolean[] used = new boolean[strs.length];
    	for(String str : strs)
    		cost.put(str, findMaxFormCost(str));
    	System.out.println(cost);
    	Map<String, Integer> memoization = new HashMap<>();
    	memoization.put("0,0", 0);
    	count = findMaxFormMemoization(strs, used, m, n, cost, memoization);
    	return count;
    }
    
    private int findMaxFormMemoization(String[] strs,boolean[] used, int m, int n, Map<String, int[]> cost, Map<String, Integer> memoization) {
    	int max = 0;
    	String key = Arrays.toString(used)+","+m+","+n;
    	if(memoization.containsKey(key))
    		return memoization.get(key);
    	for(int i=0; i<strs.length; i++) {
    		if(used[i])
    			continue;
    		used[i] = true;
    		int[] fee = cost.get(strs[i]);
    		int zero = m - fee[0], one = n - fee[1];
    		if(zero >= 0 && one >= 0)
    			max = Math.max(max, 1+findMaxFormMemoization(strs, used, zero, one, cost, memoization));
    		used[i] = false;
    	}
    	memoization.put(key, max);
    	return max;
    }
    
    private int[] findMaxFormCost(String str) {
    	int[] res = new int[2];
    	for(char c : str.toCharArray())
    		if(c == '0')
    			res[0]++;
    		else
    			res[1]++;
    	return res;
    }
    
    public int findMaxFormDP(String[] strs, int m, int n) {
        int[][] dp = new int[m+1][n+1];
        for(String str : strs) {
        	int[] count = findMaxFormCost(str);
        	for(int i=m; i>=count[0]; i--) {
        		for(int j=n; j>=count[1]; j--) {
        			dp[i][j] = Math.max(dp[i][j], 1+dp[i-count[0]][j-count[1]]);
        		}
        	}
        }
        return dp[m][n];
    }
    
    
    public int findIntegers(int num) {
        int count = 0;
        String binary = Integer.toBinaryString(num);
        int length = binary.length();
        if(length == 1)
        	return 2;
        if(num == 2)
        	return 3;
        int[] dp = new int[length+2];
        dp[length+1] = 1; dp[length] = 1;
        dp[length-1] = 2; dp[length-2] = 3;
        for(int i=length-3; i>=0; i--)
        	dp[i] = dp[i+1] + dp[i+2];
        count = dp[0];
        boolean last = true;
        for(int i=0; i<length-1; i++) {
        	char cur = binary.charAt(i);
        	char next = binary.charAt(i+1);
        	if(cur == '1') {
        		if(next == '1') {
        			last = false;
        			break;
        		}
        		i++;
        	}
        	else {
        		count -= dp[i+2];
        	}
        }
        if(last && binary.charAt(length-1) == '0' && binary.charAt(length-2) == '0')
        	count--;
        return count;
    }
    
    public int calculateMinimumHP(int[][] dungeon) {
        int M = dungeon.length, N = dungeon[0].length;
        int[][] dp = new int[M][N];
        dp[M-1][N-1] = Math.max(1-dungeon[M-1][N-1], 1);
        for(int i=M-2; i>=0; i--)
        	dp[i][N-1] = Math.max(dp[i+1][N-1]-dungeon[i][N-1], 1);
        for(int i=N-2; i>=0; i--)
        	dp[M-1][i] = Math.max(dp[M-1][i+1]-dungeon[M-1][i], 1);
        for(int i=M-2; i>=0; i--) {
        	for(int j=N-2; j>=0; j--) {
        		int down = Math.max(1, dp[i+1][j] - dungeon[i][j]);
        		int right = Math.max(1, dp[i][j+1] - dungeon[i][j]);
        		dp[i][j] = Math.min(down, right);
        	}
        }
        return dp[0][0];
    }
    
    
    public int cherryPickup(int[][] grid) {
        int n = grid.length;
        int[][][][] dp = new int[n+1][n+1][n+1][n+1];
        for(int i=0; i<=n; i++)
        	for(int j=0; j<=n; j++)
        		for(int k=0; k<=n; k++)
        			Arrays.fill(dp[i][j][k], -1);
        dp[1][1][1][1] = grid[0][0];
        for(int x1=1; x1<=n; x1++) {
        	for(int y1=1; y1<=n; y1++) {
        		for(int x2=1; x2<=n; x2++) {
        			for(int y2=1; y2<=n; y2++) {
        				if(dp[x1][y1][x2][y2] >= 0 || grid[x1-1][y1-1] == -1 || grid[x2-1][y2-1] == -1)
        					continue;
        				int max1 = Math.max(dp[x1-1][y1][x2-1][y2], dp[x1-1][y1][x2][y2-1]);
        				int max2 = Math.max(dp[x1][y1-1][x2-1][y2], dp[x1][y1-1][x2][y2-1]);
        				int max = Math.max(max1, max2);
        				if(max == -1)
        					continue;
        				dp[x1][y1][x2][y2] = max + grid[x1-1][y1-1];
        				if(x1 != x2 && y1 != y2)
        					dp[x1][y1][x2][y2] += grid[x2-1][y2-1];
        			}
        		}
        	}
        }
        return Math.max(0, dp[n][n][n][n]);
    }
    
    public boolean canCross(int[] stones) {
        if(stones.length == 1)
        	return true;
        Map<Integer, Integer> index = new HashMap<>();
        for(int i=0; i<stones.length; i++)
        	index.put(stones[i], i);
    	return canCrossMemoization(stones, 1,1, new HashMap<>(),index);
    }
    
    private boolean canCrossMemoization(int[] stones, int stone, int step, Map<String, Boolean> memoization, Map<Integer, Integer> indexMap) {
    	int index = indexMap.getOrDefault(stone, -1);
    	if(index == -1)
    		return false;
    	if(index == stones.length-1)
    		return true;
    	String key = index+","+step;
    	if(memoization.containsKey(key))
    		return memoization.get(key);
    	for(int i=-1; i<=1; i++) {
    		int next = step + i;
    		if(next <= 0)
    			continue;
    		if(canCrossMemoization(stones, next+stone,next, memoization, indexMap)) {
    			memoization.put(key, true);
    			return true;
    		}
    	}
    	memoization.put(key, false);
    	return false;
    }
    
    
    private static final int MOD = 1_000_000_007;
    public int numTilings(int N) {
    	int T_prepre = 1, T_pre = 1;
        int T_up_pre = 0, T_down_pre = 0;
            
        for (int n = 2; n <= N; n++) {
            int T_cur = (int)((0L + T_prepre + T_pre + T_up_pre + T_down_pre) % MOD);
            int T_up_cur = (T_prepre + T_down_pre) % MOD;
            int T_down_cur = (T_prepre + T_up_pre) % MOD;
                
            T_prepre = T_pre;
            T_pre = T_cur;
                
            T_up_pre = T_up_cur;
            T_down_pre = T_down_cur;
        }
            
        return T_pre;
    }

    
    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        int[] answer = new int[3];
        if(nums == null || k <= 0 || nums.length < 3 * k)
        	return answer;
        int n = nums.length;
        int[][] dp = new int[4][n+1];
        int[][] pos = new int[4][n+1];
        int[] sum = new int[n+1];
        for(int i=1; i<=n; i++)
        	sum[i] = sum[i-1] + nums[i-1];
        for(int i=1; i<=3; i++) {
        	for(int j=k*i; j<=n; j++) {
        		int curSum = sum[j] - sum[j-k] + dp[i-1][j-k];
        		if(curSum > dp[i][j-1]) {
        			dp[i][j] = curSum;
        			pos[i][j] = j-k;
        		}
        		else {
        			dp[i][j] = dp[i][j-1];
        			pos[i][j] = pos[i][j-1];
        		}
        	}
        }
        int index = n;
        for(int i=2; i>=0; i--) {
        	answer[i] = pos[i+1][index];
        	index = answer[i];
        }
        return answer;
    }
    
    
    public int checkRecord(int n) {
        int record = 0;
        int mod = 1000000007;
        if(n == 0)
        	return 1;
        if(n == 1)
        	return 3;
        if(n == 2)
        	return 8;
        int[] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = 2;
        dp[2] = 4;
        for(int i=3; i<=n; i++) {
        	dp[i] =(( dp[i-1] + dp[i-2] ) % mod + dp[i-3]) % mod;
        }
        
        for(int i=0; i<n; i++) {
        	long left = dp[i];
        	long right = dp[n-1-i];
        	long temp = left * right;
        	record = (int) ((record + temp) % mod);
        }
        record = (record + dp[n]) %mod;
        return record;
    }
    
    public double soupServings(int N) {
        if(N >= 5000)
        	return 1;
        return soupServingsMemo(N, N, new HashMap<>());
    }
    
    private double soupServingsMemo(int A, int B, Map<String, Double> memoization) {
    	if (A <= 0 && B <= 0) {     
            return 0.5;
        }
        if (A <= 0) {    
            return 1.0;
        }
        if (B <= 0) {    
            return 0.0;
        }
    	String key = A+","+B;
    	if(memoization.containsKey(key))
    		return memoization.get(key);
    	double temp = 0.25 * (soupServingsMemo(A-100, B, memoization) + 
    			              soupServingsMemo(A-75, B-25, memoization) + 
    			              soupServingsMemo(A-50, B-50, memoization) + 
    			              soupServingsMemo(A-25, B-75, memoization));
    	memoization.put(key, temp);
    	return temp;
    }
    
    public int minCut(String s) {
    	Map<String, Integer> memoization = new HashMap<>();
    	memoization.put("", 0);
    	int min = minCutMemoization(s, memoization) - 1;
    	return min;
    }
    
    private int minCutMemoization(String s, Map<String, Integer> memoization) {
    	if(memoization.containsKey(s))
    		return memoization.get(s);
    	int min = Integer.MAX_VALUE;
    	for(int i=s.length()-1; i >= 0; i--) {
    		if(isPalindrome(s, 0, i)) {
    			min = Math.min(min, 1 + minCutMemoization(s.substring(i+1), memoization));
    			if(min == 2 || min == 1)
    				break;
    		}
    	}
    	memoization.put(s, min);
    	return min;
    }
    
    
    private boolean isPalindrome(String str, int left, int right) {
    	while(left <= right) {
    		if(str.charAt(left) != str.charAt(right))
    			return false;
    		left++;
    		right--;
    	}
    	return true;
    }
    
    
    //wrong answer
    public boolean xorGame(int[] nums) {
        int n = nums.length;
        int xor = 0;
        for(int num : nums)
        	xor ^= num;
        boolean[] used = new boolean[n];
    	return xorGameMiniMax(nums, xor, used);
    }
    
    private boolean xorGameMiniMax(int[] nums, int xor, boolean[] used) {
    	if(xor == 0)
    		return true;
    	for(int i=0; i<nums.length; i++) {
    		if(used[i])
    			continue;
    		used[i] = true;
    		xor ^= nums[i];
    		if(!xorGameMiniMax(nums, xor, used))
    			return true;
    		xor ^= nums[i];
    		used[i] = false;
    	}
    	return false;
    }
    
    
    public int kInversePairs(int n, int k) {
    	int mod = 1000000007;
        if (k > n*(n-1)/2 || k < 0) return 0;
        if (k == 0 || k == n*(n-1)/2) return 1;
        long[][] dp = new long[n+1][k+1];
        dp[2][0] = 1;
        dp[2][1] = 1;
        for (int i = 3; i <= n; i++) {
            dp[i][0] = 1;
            for (int j = 1; j <= Math.min(k, i*(i-1)/2); j++) {
                dp[i][j] = dp[i][j-1] + dp[i-1][j];
                if (j >= i) dp[i][j] -= dp[i-1][j-i];
                dp[i][j] = (dp[i][j]+mod) % mod;
            }
        }
        return (int) dp[n][k];
    }
    
    public double largestSumOfAverages(int[] A, int K) {
        int n = A.length;
        double[][] dp = new double[K+1][n+1];
        double[] average = new double[n+1];
        double sum = 0;
        for(int i=1; i<=n; i++) {
        	sum += A[i-1];
        	average[i] = sum / i;
        }
        double answer = largestSumOfAveragesMemo(A, K, n, dp, average);
        return answer;
    }
    
    private double largestSumOfAveragesMemo(int[] A, int k, int n, double[][] dp, double[] average) {
    	if(k > n)
    		return 0;
    	if(k == 1)
    		return average[n];
    	if(dp[k][n] > 0)
    		return dp[k][n];
    	double lagest = 0;
    	double temp = 0;
    	for(int i=n; i>= k; i--) {
    		temp += A[i-1];
    		double avg = temp / (n-i+1);
    		lagest = Math.max(lagest, avg + largestSumOfAveragesMemo(A, k-1, i-1, dp, average));
    	}
    	lagest = Math.max(lagest, largestSumOfAveragesMemo(A, k-1, n, dp, average));
    	dp[k][n] = lagest;
    	return lagest;
    }
    
    
    public int splitArray(int[] nums, int m) {
        int n = nums.length;
        int[][] dp = new int[m+1][n+1];
        int[] preSum = new int[n+1];
        int sum = 0;
        for(int i=1; i<=n; i++) {
        	sum += nums[i-1];
        	preSum[i] = sum;
        }
        int answer = splitArrayMemo(nums, m, n, preSum, dp);
    	return answer;
    }
    
    private int splitArrayMemo(int[] nums, int m, int n, int[] preSum, int[][] dp) {
    	if(m > n)
    		return Integer.MAX_VALUE;
    	if(m == 1)
    		return preSum[n];
    	if(dp[m][n] > 0)
    		return dp[m][n];
    	int sum = 0, min = Integer.MAX_VALUE;
    	for(int i=n; i>= m;i--) {
    		sum += nums[i-1];
    		int max = Math.max(sum, splitArrayMemo(nums, m-1, i-1, preSum, dp));
    		min = Math.min(min, max);
    	}
    	dp[m][n] = min;
    	return dp[m][n];
    }
    
    
    @SuppressWarnings("unchecked")
	public int numberOfArithmeticSlices(int[] A) {
        int slices = 0;
        Map<Long, Integer>[] indexMap = new HashMap[A.length];
        for(int i=0; i<A.length;i ++) {
        	int cur = A[i];
        	indexMap[i] = new HashMap<>();
        	for(int j=0; j<i; j++) {
        		int pre = A[j];
        		long diff = (long)cur - pre;
        		if(diff < Integer.MIN_VALUE || diff > Integer.MAX_VALUE)
        			continue;
        		int d1 = indexMap[i].getOrDefault(diff, 0);
        		int d2 = indexMap[j].getOrDefault(diff, 0);
        		slices += d2;
        		indexMap[i].put(diff, d1+d2+1);
        	}
        }
        return slices;
    }
    
    public int findRotateSteps(String ring, String key) {
    	return findRotateStepsMemo(ring, key, 0, new HashMap<>());
    }
    
    private int findRotateStepsMemo(String ring, String key, int index, Map<String, Integer> memo) {
    	if(index == key.length())
    		return 0;
    	String temp = ring + ","+index;
    	if(memo.containsKey(temp))
    		return memo.get(temp);
    	int steps = Integer.MAX_VALUE;
    	int length = ring.length();
    	for(int i=0; i<length; i++) {
    		char c = ring.charAt(i);
    		if(c == key.charAt(index)) {
    			String left = ring.substring(0, i);
    			String right = ring.substring(i);
    			String nextRing = right + left;
    			steps = Math.min(steps, i+1+findRotateStepsMemo(nextRing, key, index+1, memo));
    		}
    		c = ring.charAt(length - i - 1);
    		if(c == key.charAt(index)) {
    			String right = ring.substring(length - i - 1, length);
    			String left = ring.substring(0, length - i - 1);
    			String nextRing = right + left;
    			steps = Math.min(steps, i+2+findRotateStepsMemo(nextRing, key, index+1, memo));
    		}
    	}
    	
    	memo.put(temp, steps);
    	return steps;
    }
    
    
    public String longestPalindrome(String s) {
        if(s == null || s.length() == 0)
        	return "";
    	int n = s.length();
    	if(n == 1)
    		return s;
    	Integer[] dp = new Integer[n];
    	int[] pos = new int[n];
    	for(int i=0; i<n; i++) {
    		dp[i] = 1;
    		pos[i] = i;
    	}
    	longestPalindromeMemo(s.toCharArray(), n-1, dp, pos);
    	String answer = s.substring(pos[n-1], pos[n-1] + dp[n-1]);
    	return answer;
    }
    
    private int longestPalindromeMemo(char[] array, int index, Integer[] dp, int[] pos) {
    	if(index < 0)
    		return -1;
    	if(index == 0)
    		return 1;
    	if(dp[index] != null)
    		return dp[index];
    	dp[index] = 1;
    	pos[index] = index;
    	for(int i=index; i>=0; i--) {
    		if(!isPalindromeMemo(array, i, index))
    			continue;
    		int length = index - i + 1;
    		int preLength = longestPalindromeMemo(array, i-1, dp, pos);
    		if(length > preLength) {
    			dp[index] = length;
    			pos[index] = i;
    		}else {
    			dp[index] = preLength;
    			pos[index] = pos[i-1];
    		}
    	}
    	int pre = longestPalindromeMemo(array, index-1, dp, pos);
    	if(pre > dp[index]) {
    		dp[index] = pre;
    		pos[index] = pos[index-1];
    	}
    	return dp[index];
    }
    
    private boolean isPalindromeMemo(char[] array, int left, int right) {
    	while(left <= right) {
    		if(array[left] != array[right])
    			return false;
    		left++;
    		right--;
    	}
    	return true;
    }
    
    
    public String longestPalindrome2(String s) {
    	if(s == null || s.length() == 0)
    		return "";
    	int n = s.length();
    	if(n == 1)
    		return s;
    	char[] array = s.toCharArray();
    	int[] dp = new int[n+1];
    	int[] pos = new int[n+1];
    	dp[1] = 1;
    	pos[1] = 0;
    	for(int i=2; i<=n; i++) {
    		for(int j=i; j>0; j--) {
    			if(!isPalindromeMemo(array, j-1, i-1))
    				continue;
    			int length = i - j + 1;
    			if(length > dp[j-1]) {
    				dp[i] = length;
    				pos[i] = j-1;
    			}
    			else {
    				dp[i] = dp[j-1];
    				pos[i] = pos[j-1];
    			}
    		}
    		if(dp[i] < dp[i-1]) {
    			dp[i] = dp[i-1];
    			pos[i] = pos[i-1];
    		}
    	}
    	
    	String answer = s.substring(pos[n], pos[n] + dp[n]);
    	return answer;
    }
    
    public int longestPalindromeSubseq(String s) {
        if(s == null || s.length() == 0)
        	return 0;
        int length = s.length();
        int[][] dp = new int[length+1][length+1];
        for(int i=1; i<= length; i++)
        	dp[i][i] = 1;
        for(int i=length; i>0; i--) {
        	char c1 = s.charAt(i-1);
        	for(int j=i+1; j<=length; j++) {
        		char c2 = s.charAt(j-1);
        		if(c1 == c2) {
        			dp[i][j] = i+1 == j ? 2 : dp[i+1][j-1] + 2;
        		}
        		else {
        			dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1]);
        		}
        	}
        }
        
    	return dp[1][length];
    }
    
    
    public int racecar(int target) {
        int[] min = {Integer.MAX_VALUE};
        racecarDFS(0, 1, target, min, 0);
        return min[0];
    }
    
    private void racecarDFS(int position, int speed, int target, int[] min, int steps) {
    	if(steps > min[0])
    		return;
    	if(target == position) {
    		min[0] = Math.min(steps, min[0]);
    		return;
    	}
    	if(position >= 1000000)
    		return;
    	if(position < target) {
    		if(speed > 0) {
    			racecarDFS(position+speed, speed*2, target, min, steps+1);
            	racecarDFS(position, -1, target, min, steps+1);
    		}
    		else {
            	racecarDFS(position, 1, target, min, steps+1);
        		racecarDFS(position+speed, speed*2, target, min, steps+1);
    		}
    	}
    	else {
    		if(speed > 0) {
            	racecarDFS(position, -1, target, min, steps+1);
        		racecarDFS(position+speed, speed*2, target, min, steps+1);
    		}
    		else {
    			racecarDFS(position+speed, speed*2, target, min, steps+1);
            	racecarDFS(position, 1, target, min, steps+1);
    		}
    	}
    }
    
    
    public int racecarBFS(int target) {
    	Queue<int[]> queue = new LinkedList<>();
    	Set<String> used = new HashSet<>();
    	used.add(0+","+1);
    	queue.add(new int[] {0, 1, 0});
    	int max = 3000;
    	int min = -3000;
    	while(!queue.isEmpty()) {
    		int size = queue.size();
    		for(int i=0; i<size; i++) {
    			int[] array = queue.poll();
    			int pos = array[0]; 
    			int speed = array[1];
    			int step = array[2];
    			if(speed > 0) {
    				
    				if(pos < target) {
    					if(pos + speed == target)
        					return step + 1;
        				if(speed < max) {
        					int next_pos = pos + speed;
        					int next_speed = speed * 2;
        					queue.add(new int[] {next_pos, next_speed, step + 1});
        				}
        				String str = pos + "," + -1;
        				if(!used.contains(str)) {
        					used.add(str);
        					queue.add(new int[] {pos, -1, step+1});
        				}
    				}
    				else {
    					String str = pos + "," + -1;
        				if(!used.contains(str)) {
        					used.add(str);
        					queue.add(new int[] {pos, -1, step+1});
        				}
        				if(pos + speed == target)
        					return step + 1;
        				if(speed < max) {
        					int next_pos = pos + speed;
        					int next_speed = speed * 2;
        					queue.add(new int[] {next_pos, next_speed, step + 1});
        				}
    				}    				
    				
    			}
    			else {
    				
    				if(pos < target) {
    					String str = pos + "," + 1;
        				if(!used.contains(str)) {
        					used.add(str);
        					queue.add(new int[] {pos, 1, step+1});
        				}
        				if(pos + speed == target)
        					return step + 1;
        				if(speed >= min) {
        					int next_pos = pos + speed;
        					int next_speed = speed * 2;
        					queue.add(new int[] {next_pos, next_speed, step + 1});
        				}
    				}
    				else {
    					if(pos + speed == target)
        					return step + 1;
        				if(speed >= min) {
        					int next_pos = pos + speed;
        					int next_speed = speed * 2;
        					queue.add(new int[] {next_pos, next_speed, step + 1});
        				}
        				String str = pos + "," + 1;
        				if(!used.contains(str)) {
        					used.add(str);
        					queue.add(new int[] {pos, 1, step+1});
        				}
    				}
    				
    				
    			}
    		}
    	}
    	
    	return -1;
    }
    
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        String maxNumber = maxNumberMemo(nums1, nums2, nums1.length-1, nums2.length-1, k, new HashMap<>());
    	int[] answer = new int[k];
    	for(int i=0; i<k; i++)
    		answer[i] = maxNumber.charAt(i) - '0';
    	System.out.println(maxNumber);
    	return answer;
    }
    
    private String maxNumberMemo(int[] A, int[] B, int indexA, int indexB, int k, Map<String, String> memo) {
    	if((k < 0) || (indexA + 1 + indexB + 1 < k))
    		return "";
    	if(k == 0)
    		return "";
    	String key = indexA+","+indexB+","+k;
    	if(memo.containsKey(key))
    		return memo.get(key);
    	String max = "";
    	if(indexA >= 0) {
    		max = getMaxNum(max, maxNumberMemo(A, B, indexA-1, indexB, k, memo));
        	max = getMaxNum(max, maxNumberMemo(A, B, indexA-1, indexB, k-1, memo)+A[indexA]);
    	}
    	if(indexB >= 0) {
    		max = getMaxNum(max, maxNumberMemo(A, B, indexA, indexB-1, k, memo));
        	max = getMaxNum(max, maxNumberMemo(A, B, indexA, indexB-1, k-1, memo)+B[indexB]);
    	}
    	memo.put(key, max);
    	return max;
    }
    
    private String getMaxNum(String A, String B) {
    	int lenA = A.length();
    	int lenB = B.length();
    	if(lenA < lenB)
    		return B;
    	else if(lenA > lenB)
    		return A;
    	for(int i=0; i<lenA; i++) {
    		char c1 = A.charAt(i);
    		char c2 = B.charAt(i);
    		if(c1 < c2)
    			return B;
    		else if(c1 > c2)
    			return A;
    	}
    	return A;
    }
    
    public int[] maxNumberDP(int[] nums1, int[] nums2, int k) {
    	int lenA = nums1.length;
    	int lenB = nums2.length;
    	String[][][] dp = new String[k+1][lenA+1][lenB+1];
    	
    	for(int i=0; i<=lenA; i++)
    		for(int j=0; j<=lenB; j++)
    			dp[0][i][j] = "";
    	
    	for(int x=1; x<=k && x <= lenA; x++) {
    		for(int i=1; i<=lenA; i++) {
    			dp[x][i][0] = "";
    			dp[x][i][0] = getMaxNum(dp[x][i][0], dp[x][i-1][0] == null ? "" : dp[x][i-1][0]);
    			dp[x][i][0] = getMaxNum(dp[x][i][0], (dp[x-1][i-1][0] == null ? "" : dp[x-1][i-1][0])+nums1[i-1]);
    		}
    	}
    	
    	for(int x=1; x<=k && x <= lenB; x++) {
    		for(int i=1; i<=lenB; i++) {
    			dp[x][0][i] = "";
    			dp[x][0][i] = getMaxNum(dp[x][0][i], dp[x][0][i-1] == null ? "" : dp[x][0][i-1]);
    			dp[x][0][i] = getMaxNum(dp[x][0][i], (dp[x-1][0][i-1] == null ? "" : dp[x-1][0][i-1]) + nums2[i-1]);
    		}
    	}
    	
    	
    	for(int x=1; x<=k; x++) {
    		for(int i=1; i<=lenA; i++) {
        		for(int j=1; j<=lenB; j++) {
        			dp[x][i][j] = "";
        			if(i + j < x)
        				continue;
        			dp[x][i][j] = getMaxNum(dp[x][i][j], dp[x][i-1][j] == null ? "" : dp[x][i-1][j]);
        			dp[x][i][j] = getMaxNum(dp[x][i][j], (dp[x-1][i-1][j] == null ? "" : dp[x-1][i-1][j]) + nums1[i-1]);
        			dp[x][i][j] = getMaxNum(dp[x][i][j], dp[x][i][j-1] == null ? "" : dp[x][i][j-1]);
        			dp[x][i][j] = getMaxNum(dp[x][i][j], (dp[x-1][i][j-1] == null ? "" : dp[x-1][i][j-1]) + nums2[j-1]);
        		}
        	}
		}
    	int[] answer = new int[k];
    	System.out.println(dp[k][lenA][lenB]);
    	for(int i=0; i<k; i++) {
    		answer[i] = dp[k][lenA][lenB].charAt(i) - '0';
    	}
    	return answer;
    }
    
    public int[] maxNumber2(int[] nums1, int[] nums2, int k) {
    	int n = nums1.length;
    	int m = nums2.length;
    	int[] ans = new int[k];
    	for(int i=Math.max(0, k-m); i<=k && i<=n; i++) {
    		int[] candidate = merge(maxArray(nums1, i), maxArray(nums2, k-i), k);
    		if(greater(candidate, 0, ans, 0))
    			ans = candidate;
    	}
    	return ans;
    }
    
    private int[] merge(int[] nums1, int[] nums2, int k) {
    	int[] merge = new int[k];
    	for(int i=0, j=0, r=0; r<k; r++) {
    		merge[r] = greater(nums1, i, nums2, j) ? nums1[i++] : nums2[j++];
    	}
    	return merge;
    }
    
    
    public boolean greater(int[] nums1, int i, int[] nums2, int j) {
    	while(i < nums1.length && j < nums2.length && nums1[i] == nums2[j]) {
    		i++;
    		j++;
    	}
    	return j == nums2.length || (i < nums1.length && nums1[i] > nums2[j]);
    }
    
    
    
    
    public int[] maxArray(int[] nums, int k) {
    	int n = nums.length;
    	int[] ans = new int[k];
    	for(int i=0, j=0; i<n; i++) {
    		while(n-i > k-j && j>0 && ans[j-1] < nums[i])
    			j--;
    		if(j < k)
    			ans[j++] = nums[i];
    	}
    	
    	return ans;
    }
    
    public boolean isInterleave(String s1, String s2, String s3) {
    	if(s1.length() + s2.length() != s3.length())
    		return false;
    	if(s1.length() == 0)
    		return s3.equals(s2);
    	else if(s2.length() == 0)
    		return s3.equals(s1);
    	return isInterleaveMemo(s1, s2, s3, 1, 1, new HashMap<>());
    }
    
    private boolean isInterleaveMemo(String s1, String s2, String s3, int index1, int index2, Map<String, Boolean> memo) {
    	if(index1 + index2 == s3.length())
    		return true;
    	String key = index1 + "," + index2;
    	if(memo.containsKey(key))
    		return memo.get(key);
    	boolean isInterleave = false;
    	int index3 = index1 + index2;
    	if(index1 <= s1.length() && s1.charAt(index1-1)	== s3.charAt(index3-2))
    		isInterleave = isInterleaveMemo(s1, s2, s3, index1+1, index2, memo);
    	if(isInterleave)
    		return true;
    	if(index2 <= s2.length() && s2.charAt(index2-1) == s3.charAt(index3-2))
    		isInterleave = isInterleaveMemo(s1, s2, s3, index1, index2+1, memo);
    	memo.put(key, isInterleave);
    	return isInterleave;
    }
    
    public int minimumTotal(List<List<Integer>> triangle) {
    	if(triangle==null || triangle.size()==0)
 		   return 0;
    	for(int i=triangle.size()-2; i>=0; i--) {
    		List<Integer> now = triangle.get(i);
    		List<Integer> next = triangle.get(i+1);
    		for(int j=0; j<now.size(); j++) {
    			int cur = now.get(j);
    			now.set(j, cur + Math.min(next.get(j), next.get(j+1)));
    		}
    	}
    	return triangle.get(0).get(0);
    }
    
    public int maxProduct(int[] nums) {
    	return maxProductMemo(nums, nums.length-1, new Integer[nums.length]);
    }
    
    private int maxProductMemo(int[] nums, int n, Integer[] dp) {
    	if(n == 0)
    		return nums[0];
    	if(dp[n]!= null)
    		return dp[n];
    	dp[n] = Integer.MIN_VALUE;
    	int temp = 1;
    	for(int i=n; i>0; i--) {
    		temp *= nums[i];
    		dp[n] = Math.max(temp, dp[n]);
    		int left = maxProductMemo(nums, i-1, dp);
    		dp[n] = Math.max(left, dp[n]);
    	}
    	dp[n] = Math.max(dp[n], temp * nums[0]);
    	dp[n] = Math.max(dp[n], maxProductMemo(nums, n-1, dp));
    	return dp[n];
    }
    
    public int maxProduct2(int[] nums) {
    	int n = nums.length;
    	if(n == 0)
            return 0;
    	int[] dp = new int[n+1];
    	dp[0] = Integer.MIN_VALUE;
    	for(int i=1; i<=n; i++) {
    		int cur = 1;
    		dp[i] = Integer.MIN_VALUE;
    		for(int j=i; j>0; j--) {
    			cur *= nums[j-1];
    			dp[i] = Math.max(dp[i], cur);
    			dp[i] = Math.max(dp[i], dp[j-1]);
    		}
    	}
    	
    	return dp[n];
    }
    
    
    public int integerBreak(int n) {
        int[] dp = new int[n+1];
        dp[1] = 1;
        dp[2] = 1;
        for(int i=3; i<=n; i++) {
        	for(int j=1; j<=i/2; j++) {
        		dp[i] = Math.max(dp[i], dp[i-j] * dp[j]);
        		dp[i] = Math.max(dp[i],  j * (i - j));
        		dp[i] = Math.max(dp[i],  j * dp[i-j]);
        		dp[i] = Math.max(dp[i],  (i-j) * dp[j]);
        	}
        }
    	
    	return dp[n];
    }
    
    
    
    public int numFactoredBinaryTrees(int[] A) {
        if(A.length == 0)
        	return 0;
        int mod = 1000000007;
        Arrays.sort(A);
        Map<Integer, Long> dp = new HashMap<>();
        long count = 0;
        for(int i=0; i<A.length; i++) {
        	int num = A[i];
        	long temp = 1;
        	for(int j=0; j<i; j++) {
        		int one = A[j];
        		if(num % one != 0)
        			continue;
        		int two = num / one;
        		if(!dp.containsKey(two))
        			continue;
        		temp =(((dp.get(one) * dp.get(two)) % mod) + temp) % mod;
        	}
        	dp.put(num, temp);
        	count = (count + temp) % mod;
        }
        
        return (int) count;
    }
    
    
    public int countNumbersWithUniqueDigits(int n) {
        if(n > 10)
        	n = 10;
        if(n == 0)
        	return 1;
        if(n == 1)
        	return 10;
        if(n == 2)
        	return 91;
        int[] array = {9,9,8,7,6,5,4,3,2,1};
        int[] dp = new int[n+1];
        dp[1] = 10;
        dp[2] = 91;
        for(int i=3; i<=n; i++) {
        	dp[i] += dp[i-1];
        	int temp = 1;
        	for(int j=0; j<i; j++)
        		temp *= array[j];
        	dp[i] += temp;
        }
        return dp[n];
    }
    
    public int countSubstrings(String s) {
        int n = s.length();
        int[] dp = new int[n+1];
        for(int i=1; i<=n; i++) {
        	dp[i] += dp[i-1];
        	for(int j=i; j>0; j--) {
        		if(isPalindromic(s, j, i)) {
        			dp[i]++;
        		}
        	}
        }
        return dp[n];
    }
    
    private boolean isPalindromic(String str, int left, int right) {
    	while(left <= right) {
    		if(str.charAt(left) != str.charAt(right))
    			return false;
    		left++;
    		right--;
    	}
    	return true;
    }
    
    public boolean isSubsequence(String s, String t) {
    	if(s.length() == 0)
    		return true;
    	int index = 0;
    	for(char c : toString().toCharArray()) {
    		if(s.charAt(index) == c) {
    			index++;
    			if(index == s.length())
    				break;
    		}
    	}
    	return index == s.length();
    }
    
    public int findSubstringInWraproundString(String p) {
    	int[] count = new int[26];
        
        int maxLengthCur = 0; 

        for (int i = 0; i < p.length(); i++) {
            if (i > 0 && (p.charAt(i) - p.charAt(i - 1) == 1 || (p.charAt(i - 1) - p.charAt(i) == 25))) {
                maxLengthCur++;
            }
            else {
                maxLengthCur = 1;
            }
            
            int index = p.charAt(i) - 'a';
            count[index] = Math.max(count[index], maxLengthCur);
        }
        
        int sum = 0;
        for (int i = 0; i < 26; i++) {
            sum += count[i];
        }
        return sum;
    }
    
    public int findTargetSumWays(int[] nums, int S) {
        int n = nums.length;
        int[] preSum = new int[n];
        int sum = 0;
        for(int i=n-1; i>=0; i--) {
        	sum += nums[i];
        	preSum[i] = sum;
        }
    	return findTargetSumWaysMemo(nums, 0, S, new HashMap<>(), preSum);
    }
    
    private int findTargetSumWaysMemo(int[] nums, int n, int target, Map<String, Integer> memo, int[] preSum) {
    	if(n == nums.length)
    		return target == 0 ? 1 : 0;
    	String key = n + "," + target;
    	if(memo.containsKey(key))
    		return memo.get(key);
    	if(preSum[n] < Math.abs(target))
    		return 0;
    	int count = 0;
    	count += findTargetSumWaysMemo(nums, n+1, target+nums[n], memo, preSum);
    	count += findTargetSumWaysMemo(nums, n+1, target-nums[n], memo, preSum);
    	memo.put(key, count);
    	return count;
    }
    
    
    public boolean checkSubarraySum(int[] nums, int k) {
    	if(nums.length < 2)
    		return false;
    	 if(k == 0) {
    		 for(int i=1; i<nums.length; i++) {
    			 if(nums[i] == 0 && nums[i-1] == 0)
    				 return true;
    		 }
    		 return false;
    	 }
         if(k == 1)
             return true;
    	return checkSubarraySumMemo(nums, k, nums.length, new Boolean[nums.length]);
    }
    
    private boolean checkSubarraySumMemo(int[] nums, int k, int n, Boolean[] dp) {
    	if(n < 2)
    		return false;
    	if(dp[n] != null)
    		return dp[n];
    	int temp = nums[n-1];
    	boolean b = false;
    	for(int i=n-1; i>=1; i--) {
    		temp += nums[i-1];
    		if(temp % k == 0) {
    			b = true;
    			break;
    		}
    	}
    	if(!b)
    		b = checkSubarraySumMemo(nums, k, n-1, dp);
    	dp[n] = b;
    	return dp[n];
    }
    
    
    public int findPaths(int m, int n, int N, int i, int j) {
    	return findPathsMemo(m, n, i+1, j+1, N, new Integer[m+1][n+1][N+1]);
    }
    int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}};
    private int findPathsMemo(int m, int n, int i, int j, int N, Integer[][][] dp) {
    	if(i == 0 || j == 0 || i > m || j > n)
    		return 1;
    	if(N == 0)
    		return 0;
    	if(i - N > 0 || i + N <= m || j - N > 0 || j + N <= n)
    		return 0;
    	if(dp[i][j][N] != null)
    		return dp[i][j][N];
    	dp[i][j][N] = 0;
    	for(int[] dir : dirs) {
    		dp[i][j][N] = (dp[i][j][N] + findPathsMemo(m, n, i+dir[0], j+dir[1], N-1, dp)) % 1000000007;
    	}
    	return dp[i][j][N];
    }
    
    
    
    
    
    
    
    
    
    
    
    public static void main(String[] args) {
    	DP test = new DP();
    	test.findPaths(2, 2, 2, 0, 0);
	}
    

}
