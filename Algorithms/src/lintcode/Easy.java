package lintcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Easy {
	
	public int aplusb(int a, int b) {
	       return a + b;
	}
	
	
	public long trailingZeros(long n) {
       long count = 0;
       while(n > 0) {
    	   long temp = n / 5;
    	   count += temp;
    	   n = temp;
       }
       return count;
    }
	
	public int[] mergeSortedArray(int[] A, int[] B) {
        int n = A.length + B.length;
        int[] array = new int[n];
        int indexA = 0, indexB = 0, index = 0;
        while(indexA < A.length || indexB < B.length) {
        	if(indexA == A.length)
        		array[index++] = B[indexB++];
        	else if(indexB == B.length)
        		array[index++] = A[indexA++];
        	else {
        		if(A[indexA] < B[indexB]) {
        			array[index++] = A[indexA++];
        		}
        		else {
        			array[index++] = B[indexB++];
        		}
        	}
        }
        
        return array;
    }
	
	
	public void rotateString(char[] str, int offset) {
		if(str == null || str.length < 2)
			return;
		offset = offset % str.length;
        offset = str.length - offset;
        flipString(str, offset, str.length);
        flipString(str, 0, offset);
        flipString(str, 0, str.length);
    }
	
	private void flipString(char[] str, int left, int right) {
		int mid = left + (right - left) / 2;
		for(int i=left; i<mid; i++) {
			char c = str[i];
			str[i] = str[right - 1 - i + left];
			str[right - 1 - i + left] = c;
		}
	}
	
	public List<String> fizzBuzz(int n) {
        List<String> list = new ArrayList<>();
        String fizz = "fizz";
        String buzz = "buzz";
        for(int i=1; i<=n; i++) {
        	boolean three = i % 3 == 0;
        	boolean five = i % 5 == 0;
        	if(three && five)
        		list.add(fizz + " "+ buzz);
        	else if(three)
        		list.add(fizz);
        	else if(five)
        		list.add(buzz);
        	else
        		list.add(String.valueOf(i));
        }
        
        return list;
    }
	
	public int strStr(String source, String target) {
        if(source == null || target == null)
        	return -1;
        return source.indexOf(target);
    }
	
	
	public int binarySearch(int[] nums, int target) {
        if(nums ==null || nums.length == 0)
        	return -1;
        int left = 0, right = nums.length - 1;
        while(left < right) {
        	int mid = left + (right - left) / 2;
        	if(nums[mid] < target)
        		left = mid+1;
        	else
        		right = mid;
        }
        if(nums[left] != target)
        	return -1;
		return left;
    }
	
	
	public List<List<Integer>> permute(int[] nums) {
		List<List<Integer>> permute = new ArrayList<>();		
		if(nums == null || nums.length == 0) {
			permute.add(new ArrayList<>());
			return permute;
		}
		boolean[] used = new boolean[nums.length];
		permuteHelp(permute, new ArrayList<>(), used, nums);
		return permute;
    }
	
	private void permuteHelp(List<List<Integer>> permute, List<Integer> list, boolean[] used, int[] nums) {
		if(list.size() == nums.length) {
			permute.add(new ArrayList<>(list));
		}
		else {
			for(int i=0; i<nums.length; i++) {
				if(used[i])
					continue;
				used[i] = true;
				list.add(nums[i]);
				permuteHelp(permute, list, used, nums);
				used[i] = false;
				list.remove(list.size()-1);
			}
		}
	}
	
	
	public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> permute = new ArrayList<>();
        if(nums == null || nums.length == 0) {
			permute.add(new ArrayList<>());
			return permute;
		}
        Arrays.sort(nums);
		boolean[] used = new boolean[nums.length];
        permuteUniqueHelp(permute, new ArrayList<>(), used, nums);
        return permute;
    }
	
	private void permuteUniqueHelp(List<List<Integer>> permute, List<Integer> list, boolean[] used, int[] nums) {
		if(list.size() == nums.length) {
			permute.add(new ArrayList<>(list));
		}
		else{
			for(int i=0; i<nums.length; i++) {
				if(used[i])
					continue;
				if(i > 0 && nums[i] == nums[i-1] && !used[i-1])
					continue;
				used[i] = true;
				list.add(nums[i]);
				permuteUniqueHelp(permute, list, used, nums);
				used[i] = false;
				list.remove(list.size() - 1);
			}
		}
	}
	
	
	public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> subsets = new ArrayList<>();
        if(nums == null || nums.length == 0) {
        	subsets.add(new ArrayList<>());
        	return subsets;
        }
        Arrays.sort(nums);
        List<Integer> list = new ArrayList<>();
        for(int i=0; i<=nums.length; i++) {
        	subsetsHelp(subsets, list, nums, i, 0);
        }
        return subsets;
    }
	
	private void subsetsHelp(List<List<Integer>> subsets, List<Integer> list, int[] nums, int count, int start) {
		if(count == 0) {
			subsets.add(new ArrayList<>(list));
		}
		else {
			for(int i=start; i<nums.length; i++) {
				list.add(nums[i]);
				subsetsHelp(subsets, list, nums, count-1, i+1);
				list.remove(list.size() - 1);
			}
		}
	}
	
	
	
	

}
