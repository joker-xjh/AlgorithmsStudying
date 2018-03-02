package leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
    
    
    
    
    
    
    
    
    
    
    public static void main(String[] args) {
		DP test = new DP();
		test.findIntegers(43242);
	}
    
    
    
    
 
 
 
 
    
    
    
	

}
