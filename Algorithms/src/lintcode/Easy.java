package lintcode;

import java.util.ArrayList;


import java.util.Arrays;
import java.util.Iterator;
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
	
	
	public interface NestedInteger {
		 
		      // @return true if this NestedInteger holds a single integer,
		      // rather than a nested list.
		      public boolean isInteger();
		 
		      // @return the single integer that this NestedInteger holds,
		      // if it holds a single integer
		      // Return null if this NestedInteger holds a nested list
		      public Integer getInteger();
		 
		      // @return the nested list that this NestedInteger holds,
		      // if it holds a nested list
		      // Return null if this NestedInteger holds a single integer
		      public List<NestedInteger> getList();
		  }
	public List<Integer> flatten(List<NestedInteger> nestedList) {
        List<Integer> answer = new ArrayList<>();
        flattenHelp(answer, nestedList);
        return answer;
    }
	
	private void flattenHelp(List<Integer> list, List<NestedInteger> nestedList) {
		for(NestedInteger nestedInteger : nestedList) {
			if(nestedInteger.isInteger())
				list.add(nestedInteger.getInteger());
			else
				flattenHelp(list, nestedInteger.getList());
		}
	}
	
	
	public boolean searchMatrix(int[][] matrix, int target) {
		if(matrix == null || matrix.length == 0)
			return false;
        int M = matrix.length, N = matrix[0].length;
		if(N == 0)
			return false;
		if(target < matrix[0][0])
			return false;
		for(int i=0; i<M; i++) {
			if(i == M-1) {
				return binarySearchHelp(matrix[i], target);
			}
			else {
				int cur = matrix[i][0];
				int next = matrix[i+1][0];
				if(cur == target || next == target)
					return true;
				if(cur < target && target < next)
					return binarySearchHelp(matrix[i], target);
			}
		}
		
		
		return false;
    }
	
	
	private boolean binarySearchHelp(int[] array, int target) {
		int left = 0, right = array.length - 1;
		while(left <= right) {
			int mid = left + (right - left) / 2;
			if(array[mid] == target)
				return true;
			else if(array[mid] < target)
				left = mid + 1;
			else
				right = mid - 1;
		}
		
		return false;
	}
	
	public class Interval {
		      int start, end;
		      Interval(int start, int end) {
		          this.start = start;
		          this.end = end;
		      }
	}
	
	
	public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        boolean needAdded = true;
        int index = 0;
        Iterator<Interval> iterator = intervals.iterator();
        while(iterator.hasNext()) {
        	Interval interval = iterator.next();
        	
        	if(interval.end < newInterval.start) {
        		index++;
        	}
        	else if(interval.end == newInterval.start) {
        		newInterval.start = interval.start;
        		iterator.remove();
        	}
        	else if(interval.end > newInterval.start && interval.end <= newInterval.end) {
        		if(interval.start < newInterval.start)
        			newInterval.start = interval.start;
        		iterator.remove();
        	}
        	else if(interval.end > newInterval.end) {
        		if(interval.start <= newInterval.start) {
        			needAdded = false;
        			break;
        		}
        		else if(interval.start > newInterval.start && interval.start <= newInterval.end) {
        			newInterval.end = interval.end;
        			iterator.remove();
        		}
        		else if(interval.start > newInterval.end) {
        			break;
        		}
        	}
        }
        
        if(needAdded)
        	intervals.add(index, newInterval);
		return intervals;
    }
	
	
	public class ListNode {
		      int val;
		      ListNode next;
		      ListNode(int val) {
		          this.val = val;
		          this.next = null;
		      }
		  }
	
	public ListNode reverse(ListNode head) {
        if(head == null)
        	return null;
        ListNode node = head, pre = null;
        while(node != null) {
        	ListNode temp = node.next;
        	node.next = pre;
        	pre = node;
        	node = temp;
        }
        head = pre;
        return head;
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
