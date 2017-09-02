package leetcode;

import java.util.Arrays;

import java.util.LinkedList;
import java.util.List;

public class Array {
	
	
    public void nextPermutation(int[] nums) {
    	if (nums == null || nums.length==0) {
			return;
		}
    	
    	int length = nums.length;
    	
    	
    	int i = length - 2;
    	for(; i>=0 && nums[i]>=nums[i-1]; i--);
    	
    	if (i>=0) {
			int j = i+1;
			for(; j<length && nums[i] < nums[j];j++);
			exch(nums, i, j-1);
			
		}
    	i++;
    	int j = length-1;
    	while(i<j) {
    		exch(nums, i++, j--);
    	}
    	
    }
    
    private void exch(int[] array,int i, int j) {
    	int temp = array[i];
    	array[i] =array[j];
    	array[j] = temp;
    }
    
    
    public List<Integer> findClosestElements(List<Integer> arr, int k, int x) {
        List<Integer> list = new LinkedList<>();
        if(arr.get(0) >= x) {
        	for(int i=0; i<k; i++)
        		list.add(arr.get(i));
        	return list;
        }
        if(arr.get(arr.size()-1) <= x) {
        	for(int i=arr.size()-k+1; i<arr.size(); i++)
        		list.add(arr.get(i));
        	return list;
        }
        
        int left = 0 , right = arr.size() - 1;
        while(left <= right) {
        	int mid = (left+right)>>>1;
    	    int cur = arr.get(mid);
    	    if(cur == x) {
    	    	left = mid;
    	    	break;
    	    }
    	    else if(cur < x)
    	    	left = mid + 1;
    	    else 
    	    	right = mid - 1;
        }
        int cur = arr.get(left);
        int pre = 0;
        if(cur == x) {
        	list.add(cur);
        	right = left+1;
        	left--;
        }
        else {
        	pre = arr.get(left-1);
        	if(x - pre <= cur - x) {
        		list.add(pre);
        		right = left;
        		left = left - 2;
        	}
        	else {
        		list.add(cur);
        		right = left +1;
        		left--;
        	}
        		
        }
        k--;
        while(k > 0) {
        	if(left < 0) {
        		list.add(arr.get(right));
        		right++;
        	}
        	else if(right >= arr.size()) {
        		list.add(0, arr.get(left));
        		left--;
        	}
        	else {
        		int l = arr.get(left);
        		int r = arr.get(right);
        		if(x-l <= r-x) {
        			list.add(0, l);
        			left--;
        		}
        		else {
        			list.add(r);
        			right++;
        		}
        	}
        	k--;
        }
        
        return list;
    }
    
    public int combinationSum4(int[] nums, int target) {
    	int count = 0;
    	if(nums==null || nums.length ==0)
    		return count;
    	Arrays.sort(nums);
    	int[] dp = new int[target+1];
    	for(int i=1; i<dp.length; i++) {
    		for(int num:nums) {
    			if(num > i)
    				break;
    			else if(num == i)
    				dp[i] += 1;
    			else
    				dp[i] += dp[i-num];
    		}
    	}
    	return dp[target];
    }
    
    
    public int[][] imageSmoother(int[][] M) {
        int X = M.length;
        int Y = M[0].length;
        int[][] image = new int[X][Y];
        for(int i=0;i<X;i++) {
        	for(int j=0; j<Y; j++) {
        		int val = M[i][j];
        		int count = 1;
        		if(i-1>=0) {
        			val += M[i-1][j];
        			count++;
        		}
        			
        		if(i+1<X) {
        			val += M[i+1][j];
        			count++;
        		}
        			
        		if(j-1>=0) {
        			val += M[i][j-1];
        			count++;
        		}
        			
        		if(j+1<Y) {
        			val += M[i][j+1];
        			count++;
        		}
        			
        		if(i-1 >=0 && j-1>=0) {
        			val += M[i-1][j-1];
        			count++;
        		}
        			
        		if(i-1 >=0 && j+1<Y) {
        			val += M[i-1][j+1];
        			count++;
        		}
        			
        		if(i+1<X && j-1>=0) {
        			val += M[i+1][j-1];
        			count++;
        		}
        			
        		if(i+1<X && j+1<Y) {
        			val += M[i+1][j+1];
        			count++;
        		}
        			val /= count;
        		
        		image[i][j] = val;
        	}
        }
        
        return image;
    }
    
    public int countDigitOne(int n) {
    	if(n <= 0)
    		return 0;
        long ones = 0;
        for(long i=1,k=n;i<=n;i*=10,k/=10) {
        	long pre = n/(i*10);
        	long cur = k %10;
        	long next = n % i;
        	ones += pre * i;
        	ones += cur > 1 ? i:(cur == 1 ? next+1 : 0);
        }
        return (int) ones;
    }
    
    
    
    
    
    
    

	public static void main(String[] args) {
		

	}

}
