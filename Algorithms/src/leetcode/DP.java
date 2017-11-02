package leetcode;

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
    
    
    
    
    
    
    
    
    public static void main(String[] args) {
		DP test = new DP();
		int[] array= {4,2,3,0,3};
		System.out.println(test.maxCoins2(array));
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
    
    
    
    
    
    
    
    
    
    
	

}
