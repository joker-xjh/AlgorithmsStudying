package lintcode;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;


public class Medium {
	
	public int digitCounts(int k, int n) {  
		int count = 0;
		if(n == 0 && k == 0)
			return 1;
		if(n <= 0)
			return 0;
		int left_part = n, right_part = 0, power = 1;
		while(left_part > 0) {
			int digit = left_part % 10;
			left_part /= 10;
			
			if(digit < k) {
				count += left_part * power;
			}
			else if(digit == k) {
				count += left_part * power + right_part + 1;
			}
			else {
				count += (left_part + 1) * power;
			}
			
			if(k == 0 && power > 1)
				count -= power;
			
			right_part = digit * power + right_part;
			power *= 10;
		}
		
		
		return count;
	}
	
	
	
	public int nthUglyNumber(int n) {
        long ugly = 0;
        PriorityQueue<Long> queue = new PriorityQueue<>();
        Set<Long> used = new HashSet<>();
        
        queue.offer(1L);
        used.add(1L);
        
        while(n > 0) {
        	ugly = queue.poll();
        	long one = ugly * 2;
        	if(!used.contains(one) && one > 0) {
        		used.add(one);
        		queue.offer(one);
        	}
        	long two = ugly * 3;
        	if(!used.contains(two) && two > 0) {
        		used.add(two);
        		queue.offer(two);
        	}
        	long three = ugly * 5;
        	if(!used.contains(three) && three > 0) {
        		used.add(three);
        		queue.offer(three);
        	}
        	n--;
        }
        
        return (int) ugly;
    }
	
	
	public int nthUglyNumber2(int n) {
        long ugly = 0;
        long pre = 0;
        PriorityQueue<Long> queue = new PriorityQueue<>();
        
        queue.offer(1L);
        
        while(n > 0) {
        	ugly = queue.poll();
        	if(pre == ugly)
        		continue;
        	long one = ugly * 2;
        	if(one > 0) {
        		queue.offer(one);
        	}
        	long two = ugly * 3;
        	if( two > 0) {
        		queue.offer(two);
        	}
        	long three = ugly * 5;
        	if(three > 0) {
        		queue.offer(three);
        	}
        	n--;
        	pre = ugly;
        }
        
        return (int) ugly;
    }
	
	
	public int kthLargestElement(int k, int[] nums) {
		k = nums.length - k;
		int left = 0, right = nums.length - 1;
		while(left < right) {
			int index = kthLargestElementPartition(nums, left, right);
			if(index == k)
				break;
			else if(index < k)
				left = index + 1;
			else
				right = index - 1;
		}
		return nums[k];
    }
	
	private int kthLargestElementPartition(int[] array, int left, int right) {
		int cmp = array[left];
		int i = left, j = right;
		while(i < j) {
			while(i < j && array[j] > cmp)
				j--;
			array[i] = array[j];
			while(i < j && array[i] <= cmp)
				i++;
			array[j] = array[i];
		}
		array[i] = cmp;
		return i;
	}
	
	
	class TreeNode {
	      public int val;
	      public TreeNode left, right;
	      public TreeNode(int val) {
	          this.val = val;
	          this.left = this.right = null;
          }
	}
	
	Map<String, TreeNode> serializeMap = new HashMap<>();
	public String serialize(TreeNode root) {
		if(root == null) {
			serializeMap.put("null", root);
			return "null";
		}
		
		StringBuilder sb = new StringBuilder();
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root);
		while(!queue.isEmpty()) {
			int size = queue.size();
			for(int i=0; i<size; i++) {
				TreeNode node = queue.poll();
				sb.append(node.val).append(',');
				if(node.left != null)
					queue.offer(node.left);
				else 
					sb.append('#').append(',');
				if(node.right != null)
					queue.offer(node.right);
				else 
					sb.append('#').append(',');
			}
		}
		String key = sb.toString();
		serializeMap.put(key, root);
		return key;
    }
	
	 public TreeNode deserialize(String data) {
		 return  serializeMap.get(data);
	 }
	
	
	 public List<Integer> searchRange(TreeNode root, int k1, int k2) {
	       List<Integer> list = new ArrayList<>();
	       searchRangeHelp(root, k1, k2, list);
	       return list;
	 }
	 
	 private void searchRangeHelp(TreeNode node, int k1, int k2, List<Integer> list) {
		 if(node == null)
			 return;
		 searchRangeHelp(node.left, k1, k2, list);
		 if(node.val >= k1 && node.val <= k2)
			 list.add(node.val);
		 searchRangeHelp(node.right, k1, k2, list);
	 }
	
	  class MinStack {
		  
		    class Node {
		    	int val;
		    	int smallest;
		    }
		    Stack<Node> stack;
		    public MinStack() {
		        stack = new Stack<>();
		    }

		    /*
		     * @param number: An integer
		     * @return: nothing
		     */
		    public void push(int number) {
		    	Node node = new Node();
	        	node.val = number;
		        if(stack.isEmpty()) {
		        	node.smallest = number;
		        }
		        else {
		        	if(number < stack.peek().smallest)
		        		node.smallest = number;
		        	else
		        		node.smallest = stack.peek().smallest;
		        }
	        	stack.push(node);
		    }

		    /*
		     * @return: An integer
		     */
		    public int pop() {
		    	return stack.pop().val;
		    }

		    /*
		     * @return: An integer
		     */
		    public int min() {
		        return stack.peek().smallest;
		    }
		}
	
	  public List<List<Integer>> subsetsWithDup(int[] nums) {
	        List<List<Integer>> subsets = new ArrayList<>();
	        if(nums == null) {
	        	subsets.add(new ArrayList<>());
	        	return subsets;
	        }
	        boolean[] used = new boolean[nums.length];
	        List<Integer> list = new ArrayList<>();
	        Arrays.sort(nums);
	        for(int i=0; i<=nums.length; i++) {
	        	subsetsWithDupHelp(subsets, list, used, 0, i, nums);
	        }
	        return subsets;
	  }
	  
	  private void subsetsWithDupHelp(List<List<Integer>> subset, List<Integer> list, boolean[] used, int start, int count, int[] array) {
		  if(count == 0) {
			  subset.add(new ArrayList<>(list));
		  }
		  else {
			  for(int i=start; i<array.length; i++) {
				  if(used[i])
					  continue;
				  if(i > 0 && !used[i-1] && array[i] == array[i-1])
					  continue;
				  used[i] = true;
				  list.add(array[i]);
				  subsetsWithDupHelp(subset, list, used, i+1, count-1, array);
				  used[i] = false;
				  list.remove(list.size()-1);
			  }
		  }
	  }
	  
	  
	  public boolean isInterleave(String s1, String s2, String s3) {
		  if(s3.length() != s1.length() + s2.length())
			  return false;
		  Map<String, Boolean> memorization = new HashMap<>();
		  return isInterleaveMemorization(s3, s1, s2, 0, 0, memorization);
	  }
	  
	  private boolean isInterleaveMemorization(String s, String s1, String s2, int index1, int index2, Map<String, Boolean> memorization) {
		  if(index1 == s1.length() && index2 == s2.length())
			  return true;
		  String key = "" + index1 +","+index2;
		  if(memorization.containsKey(key))
			  return memorization.get(key);
		  
		  char c1 = index1 == s1.length() ? '\0' : s1.charAt(index1);
		  char c2 = index2 == s2.length() ? '\0' : s2.charAt(index2);
		  char c = s.charAt(index1 + index2);
		  if(c == c1) {
			  if(isInterleaveMemorization(s, s1, s2, index1 + 1, index2, memorization)) {
				  memorization.put(key, true);
				  return true;
			  }
		  }
		  if(c == c2) {
			  if(isInterleaveMemorization(s, s1, s2, index1, index2 + 1, memorization)) {
				  memorization.put(key, true);
				  return true;
			  }
		  }
		  
		  memorization.put(key, false);		  
		  return false;
	  }
	  

	  
	  public int partitionArray(int[] nums, int k) {
		   int n = nums.length;
	       int left = 0, right = nums.length - 1;
	       while(left < right) {
	    	   while(left < n && nums[left] < k)
	    		   left++;
	    	   while(right >= 0 && nums[right] >= k)
	    		   right--;
	    	   if(left < right) {
	    		   int temp = nums[left];
	    		   nums[left] = nums[right];
	    		   nums[right] = temp;
	    		   left++;
	    		   right--;
	    	   }
	       }
	       return left;
	  }
	  
	  
	  public String minWindow(String source , String target) {
	      int[] hash = new int[128];
	      for(char c : target.toCharArray())
	    	  hash[c]++;
	      int counter = target.length(), end = 0, start = 0, distance = source.length()+1, head = 0;
	      while(end < source.length()) {
	    	  if(hash[source.charAt(end++)]-- > 0)
	    		  counter--;
	    	  while(counter == 0) {
	    		  if(distance > end - start) {
	    			  distance = end - start;
	    			  head = start;
	    		  }
	    		  if(hash[source.charAt(start++)]++ ==0)
	    			  counter++;
	    	  }
	      }
		  
		  return distance == source.length() + 1  ? "" : source.substring(head, distance);
	  }
	  
	  
	  public class ListNode {
	      int val;
	      ListNode next;
	      ListNode(int val) {
	          this.val = val;
	          this.next = null;
	      }
	  }
	  
	  
	  public ListNode reverseBetween(ListNode head, int m, int n) {
	      if(head == null)
	    	  return null;
	      if(m == n)
	    	  return head;
	      ListNode node = head;
	      ListNode pre = null;
	      ListNode first = null;
	      while(m > 1) {
	    	  pre = node;
	    	  node = node.next;
	    	  m--;
	    	  n--;
	      }
	      first = node;
	      System.out.println(first.val);
	      ListNode next = null;
	      ListNode last = null;
	      while(n > 1) {
	    	  node = node.next;
	    	  n--;
	      }
	      last = node;
	      next = node.next;
	      last.next = null;
	      
		  first = reverse(first);
		  if(pre != null)
			  pre.next = first;
		  node = first;
		  while(node.next != null) {
			  node = node.next;
		  }
		  node.next = next;
		  return pre == null ? first : head;
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
	  
	  
	  public int searchMatrix(int[][] matrix, int target) {
		  if(matrix == null || matrix.length == 0)
			  return 0;
		  int m = matrix.length, n = matrix[0].length;
	      PriorityQueue<int[]> queue = new PriorityQueue<>(new searchMatrix());
		  for(int i=0; i<n; i++) {
			  queue.add(new int[] {matrix[0][i], 0, i});
		  }
		  int counter = 0;
		  while(!queue.isEmpty()) {
			  int[] array = queue.poll();
			  int val = array[0];
			  int index = array[1];
			  int num = array[2];
			  if(val > target)
				  break;
			  if(val == target)
				  counter++;
			  index++;
			  if(index < m)
				  queue.add(new int[] {matrix[index][num], index, num});
		  }
		  
		  return counter;
	  }
	  
	  class searchMatrix implements Comparator<int[]> {

		@Override
		public int compare(int[] o1, int[] o2) {
			return o1[0] - o2[0];
		}
		  
	  }
	  
	  
	  
	  public class MyQueue {
		  	
		    Stack<Integer> left;
		    Stack<Integer> right;
		  	
		    public MyQueue() {
		        // do intialization if necessary
		    	left = new Stack<>();
		    	right = new Stack<>();
		    }

		    /*
		     * @param element: An integer
		     * @return: nothing
		     */
		    public void push(int element) {
		        // write your code here
		    	left.push(element);
		    }

		    /*
		     * @return: An integer
		     */
		    public int pop() {
		        // write your code here
		    	if(!right.isEmpty())
		    		return right.pop();
		    	while(!left.isEmpty())
		    		right.push(left.pop());
		    	return right.pop();
		    }

		    /*
		     * @return: An integer
		     */
		    public int top() {
		        // write your code here
		    	if(!right.isEmpty())
		    		return right.peek();
		    	while(!left.isEmpty())
		    		right.push(left.pop());
		    	return right.peek();
		    }
		}
	  
	  
	 
	  public int maxDiffSubArrays(int[] nums) {
	      int n = nums.length;
	      int[] left_max = new int[n];
	      int[] left_min = new int[n];
	      int sum_max = 0, sum_min = 0;
	      
	      int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
	      for(int i=0; i<n; i++) {
	    	  sum_max += nums[i];
	    	  sum_min += nums[i];
	    	  max = Math.max(max, sum_max);
	    	  if(sum_max < 0)
	    		  sum_max = 0;
	    	  min = Math.min(min, sum_min);
	    	  if(sum_min > 0)
	    		  sum_min = 0;
	    	  left_max[i] = max;
	    	  left_min[i] = min;
	      }
	      
	      int[] right_max = new int[n];
	      int[] right_min = new int[n];
	      sum_max = 0; sum_min = 0;
	      max = Integer.MIN_VALUE; min = Integer.MAX_VALUE;
	      
	      for(int i=n-1; i>=0; i--) {
	    	  sum_max += nums[i];
	    	  sum_min += nums[i];
	    	  max = Math.max(max, sum_max);
	    	  min = Math.min(min, sum_min);
	    	  if(sum_max < 0)
	    		  sum_max = 0;
	    	  if(sum_min > 0)
	    		  sum_min = 0;
	    	  right_max[i] = max;
	    	  right_min[i] = min;
	      }
	      
	      max = Integer.MIN_VALUE;
	      for(int i=0; i<n-1; i++) {
	    	  max = Math.max(max, Math.abs(left_max[i] - right_min[i+1]));
	    	  max = Math.max(max, Math.abs(right_max[i+1] - left_min[i]));
	      }
	      return max;
	  }
	  
	  
	  
	  public int majorityNumber(List<Integer> nums) {
		  int num1 = 0, num2 = 0, time1 = 0, time2 = 0;
		  for(int i=0; i<nums.size(); i++) {
			  int n = nums.get(i);
			  if(n == num1) {
				  time1++;
			  }
			  else if(n == num2) {
				  time2++;
			  }
			  
			  else if(time1 == 0) {
				  time1 = 1;
				  num1 = n;
			  }
			  else if(time2 == 0) {
				  time2 = 1;
				  num2 = n;
			  }
			  else {
				  time1--;
				  time2--;
			  }
		  }
		  
		  time1 = 0; time2 = 0;
		  for(int i=0; i<nums.size(); i++) {
			  int a = nums.get(i);
			  if(a == num1)
				  time1++;
			  else if(a == num2)
				  time2++;
		  }
		  if(time1 > time2)
			  return num1;
		  return num2;
	  }
	  
	  
	  public int majorityNumber(List<Integer> nums, int k) {
		  int size = nums.size() / k;
		  Map<Integer, Integer> counter = new HashMap<>();
		  for(int num : nums) {
			  counter.put(num, counter.getOrDefault(num, 0)+1);
			  if(counter.get(num) > size)
				  return num;
		  }
		  
		  return -1;
	  }
	  
	  
	  public void sortLetters(char[] chars) {
	      if(chars == null || chars.length < 2)
	    	  return;
	      int n = chars.length;
	      char[] array1 = new char[n];
	      char[] array2 = new char[n];
	      
	      int index1 = 0, index2 = 0;
	      
	      for(int i=0; i<n; i++) {
	    	  char c = chars[i];
	    	  if(c >= 'a' && c <= 'z')
	    		  array1[index1++] = c;
	    	  else
	    		  array2[index2++] = c;
	      }
	      Arrays.sort(array1, 0, index1);
	      Arrays.sort(array2, 0, index2);
	      int index = 0;
	      for(int i=0; i<index1; i++) {
	    	  chars[index++] = array1[i];
	      }
	      for(int i=0; i<index2; i++) {
	    	  chars[index++] = array2[i];
	      }
	      
	  }
	  
	 
	  
	  public boolean wordBreak(String s, Set<String> dict) {
	       Map<String, Boolean> memorization = new HashMap<>();
	       memorization.put("", true);
		  return wordBreakMemorization(s, dict, memorization);
	  }
	  
	  private boolean wordBreakMemorization(String s, Set<String> dict, Map<String, Boolean> memorization) {
		  if(memorization.containsKey(s))
			  return memorization.get(s);
		  for(String word:dict) {
			  if(s.startsWith(word)) {
				  if(wordBreakMemorization(s.substring(word.length()), dict, memorization)) {
					  memorization.put(s, true);
					  return true;
				  }
			  }
		  }
		  return false;
	  }
	  
	  
	  public long houseRobber(int[] A) {
	      int n = A.length;
	      long[] rob = new long[n];
	      long[] no = new long[n];
	      rob[0] = A[0];
	      long profit = A[0];
	      for(int i=1; i<n; i++) {
	    	  int money = A[i];
	    	  if(i < 2)
	    		  rob[i] = no[i-1] + money;
	    	  else
	    		  rob[i] = Math.max(rob[i-2], no[i-1]) + money;
	    	  no[i] = Math.max(rob[i-1], no[i-1]);
	    	  profit = Math.max(rob[i], no[i]);
	      }
	      return profit;
	  }
	  
	  
	  public List<Integer> previousPermuation(List<Integer> nums) {
	      if(nums == null || nums.size() < 2)
	    	  return nums;
	      int size = nums.size();
	      Integer[] array = new Integer[size];
	      nums.toArray(array);
	      for(int i=size-1; i>0; i--) {
	    	  int cur = array[i];
	    	  int pre = array[i-1];
	    	  if(pre > cur) {
	    		  int index = -1;
	    		  int last = -1;
	    		  for(int j=size-1; j>=i; j--) {
	    			  if(array[j] < pre) {
	    				  if(array[j] > last) {
	    					  last = array[j];
	    					  index = j;
	    				  }
	    			  }
	    		  }
	    		  swap(array, index, i-1);
	    		  Arrays.sort(array, i, size, new reverseCom());
	    		  fill(nums, array);
	    		  return nums;
	    	  }
	      }
	      Collections.sort(nums, Collections.reverseOrder());
		  return nums;
	  }
	  
	  private void fill(List<Integer> list, Integer[] array) {
		  for(int i=0; i<list.size(); i++)
			  list.set(i, array[i]);
	  }
	  
	  class reverseCom implements Comparator<Integer> {

		@Override
		public int compare(Integer o1, Integer o2) {
			return o2 - o1;
		}
		  
	  }
	  
	  private void swap(Integer[] array, int i, int j) {
		  int temp = array[i];
	      array[i] = array[j];
	      array[j] = temp;
	  }
	  
	  
	  public int[] nextPermutation(int[] nums) {
	      for(int i=nums.length-1; i>0; i--) {
	    	  int cur = nums[i];
	    	  int pre = nums[i-1];
	    	  if(cur > pre) {
	    		  int big = Integer.MAX_VALUE;
	    		  int index = -1;
	    		  for(int j=nums.length-1; j>=i; j--) {
	    			  if(nums[j] > pre) {
	    				  if(nums[j] < big) {
	    					  big = nums[j];
	    					  index = j;
	    				  }
	    			  }
	    		  }
	    		  nums[i-1] = big;
	    		  nums[index] = pre;
	    		  Arrays.sort(nums, i, nums.length);
	    		  return nums;
	    	  }
	      }
		  
		  Arrays.sort(nums);
		  return nums;
	  }
	  
	  
	  public List<List<Integer>> threeSum(int[] numbers) {
		  List<List<Integer>> list = new ArrayList<>();
		  if(numbers.length < 3)
			  return list;
	      Arrays.sort(numbers);
	      Set<String> used = new HashSet<>();
	      for(int i=numbers.length-1; i>1; i--) {
	    	  int target = -numbers[i];
	    	  twoSum(numbers, 0, i-1, target, used, list);
	      }
		  
		  return list;
	  }
	  
	  private void twoSum(int[] array, int start, int end, int target, Set<String> used, List<List<Integer>> list) {
		  while(start < end) {
			  int left = array[start];
			  int right = array[end];
			  int sum = left + right;
			  if(sum < target) {
				  start++;
			  }
			  else if(sum > target) {
				  end--;
			  }
			  else {
				  String key = left+","+right+","+(-target);
				  if(!used.contains(key)) {
					  used.add(key);
					  List<Integer> temp = new ArrayList<>();
					  temp.add(left);
					  temp.add(right);
					  temp.add(-target);
					  list.add(temp);
				  }
				  start++;
				  end--;
			  }
		  }
	  }
	  
	  
	  public List<List<Integer>> fourSum(int[] numbers, int target) {
	      List<List<Integer>> list = new ArrayList<>();
	      if(numbers == null || numbers.length < 4)
	    	  return list;
	      Set<String> set = new HashSet<>();
	      Arrays.sort(numbers);
	      for(int i=numbers.length-1; i>2; i--) {
	    	  threeSum(numbers, i-1, target-numbers[i], list, set, numbers[i]);
	      }
		  return list;
	  }
	  
	  public void threeSum(int[] numbers, int start, int target, List<List<Integer>> list, Set<String> used, int four) {
	      for(int i=start; i>1; i--) {
	    	  int temp = target - numbers[i];
	    	  twoSum(numbers, 0, i-1, temp, used, list, four, numbers[i]);
	      }
		  
	  }
	  
	  private void twoSum(int[] array, int start, int end, int target, Set<String> used, List<List<Integer>> list, int four, int three) {
		  while(start < end) {
			  int left = array[start];
			  int right = array[end];
			  int sum = left + right;
			  if(sum < target) {
				  start++;
			  }
			  else if(sum > target) {
				  end--;
			  }
			  else {
				  String key = left+","+right+","+three +","+four;
				  if(!used.contains(key)) {
					  used.add(key);
					  List<Integer> temp = new ArrayList<>();
					  temp.add(left);
					  temp.add(right);
					  temp.add(three);
					  temp.add(four);
					  Collections.sort(temp);
					  list.add(temp);
				  }
				  start++;
				  end--;
			  }
		  }
	  }
	  
	  
	  public int longestIncreasingSubsequence(int[] nums) {
		  int n = nums.length;
		  if(n == 0)
			  return 0;
		  int[] dp= new int[n];
		  int longest = 0;
		  for(int i=0; i<n; i++) {
			  dp[i] = 1;
			  int cur = nums[i];
			  for(int j=i-1; j>=0; j--) {
				  int pre = nums[j];
				  if(pre >= cur)
					  continue;
				  if(dp[j] + 1 > dp[i])
					  dp[i] = dp[j] + 1;
				  if(dp[i] > longest)
					  longest = dp[i];
			  }
		  }
		  return longest;
	  }
	  
	  
	  public int backPack(int m, int[] A) {
		  int[][] dp = new int[A.length][m+1];
		  for(int j=1; j<=m; j++) {
			  if(A[0] <= j)
				  dp[0][j] = A[0];
			  for(int i=1; i<A.length; i++) {
				  if(A[i] > j)
					  dp[i][j] = dp[i-1][j];
				  else
					  dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-A[i]]+A[i]);
			  }
		  }
		  return dp[A.length-1][m];
	  }
	  
	  
	  public int backPackII(int m, int[] A, int[] V) {
	      int[][] dp = new int[A.length][m+1];
	      for(int j=1; j<=m; j++) {
	    	  if(A[0] <= j)
	    		  dp[0][j] = V[0];
	    	  for(int i=1; i<A.length; i++) {
	    		  if(A[i] > j)
	    			  dp[i][j] = dp[i-1][j];
	    		  else
	    			  dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-A[i]] + V[i]);
	    	  }
	      }
	      
	      return dp[A.length-1][m];
	  }
	  
	  
	  public int backPackVI(int[] nums, int target) {
		  Map<Integer, Integer> memoization = new HashMap<>();
		  memoization.put(0, 1);
		  int count = backPackVIMemo(nums, target, memoization);
		  return count;
	  }
	  
	  private int backPackVIMemo(int[] nums, int target, Map<Integer, Integer> memoization) {
		  if(target < 0)
			  return 0;
		  if(memoization.containsKey(target))
			  return memoization.get(target);
		  int count = 0;
		  for(int num : nums)
			  count += backPackVIMemo(nums, target - num, memoization); 
		  memoization.put(target, count);
		  return count;
	  }
	  
	  
	  public int change(int amount, int[] coins) {
	       int[] dp = new int[amount+1];
	       dp[0] = 1;
	       for(int i=0; i<coins.length; i++) {
	    	   int coin = coins[i];
	    	   for(int j=coin; j<=amount; j++)
	    		   dp[j] += dp[j-coin]; 
	       }
	       return dp[amount];
	  }
	  
	  Integer memo[][][];
	  public int findMaxForm(String[] strs, int m, int n) {
	        if(strs == null || strs.length == 0 || (m == 0 && n == 0)) return 0;
	        memo = new Integer[m+1][n+1][strs.length];
	        int [][] pairs = new int[strs.length][2];
	        for(int i = 0;i<strs.length;i++){
	            for(int j = 0;j<strs[i].length();j++){
	                char ch  = strs[i].charAt(j);
	                if(ch == '0') pairs[i][0]++;
	                else pairs[i][1]++;
	            }
	        }
	        return go(pairs, 0, m, n);
	    }
	  
	  public int go(int pairs[][], int s, int m, int n){
	        if(s >= pairs.length) return 0;
	        if(memo[m][n][s] != null) return memo[m][n][s];
	        int count = 0;
	        for(int i = s;i<pairs.length;i++){
	            int dm = m - pairs[i][0];
	            int dn = n - pairs[i][1];
	            if(dm >= 0 && dn >=0) {
	                count = Math.max(count, 1+go(pairs, i+1, dm, dn));
	            }
	        }
	        memo[m][n][s] = count;
	        return count;
	    }
	  
	  
	  public int MinAdjustmentCost(List<Integer> A, int target) {
	      int n = A.size();
	      if(n < 2)
	    	  return 0;
	      int[][] dp = new int[n][101];
	      for(int i=1; i<=100; i++)
	    	  dp[0][i] = Math.abs(A.get(0) - i);
	      
	      for(int i=1; i<n; i++) {
	    	  for(int j=1; j<=100; j++) {
	    		  dp[i][j] = 1000000;
	    		  int diff = Math.abs(j - A.get(i));
	    		  int max = Math.min(100, j + target);
	    		  int min = Math.max(1, j - target);
	    		  for(int k=min; k<=max; k++)
	    			  dp[i][j] = Math.min(dp[i][j], dp[i-1][k] + diff);
	    	  }
	      }
		  
		  int cost = Integer.MAX_VALUE;
		  for(int i=1; i<=100; i++)
			  cost = Math.min(cost, dp[n-1][i]);
		  return cost;
	  }
	  
	  public long getNumberOfWays(int n, int m, int limit, int[] cost) {
		    Long[][] dp = new Long[n+1][m+1];
		  	long ways = getNumberOfWaysMemo(n, m, limit, cost, dp, n);
	        return ways;
	  }
	  
	  private long getNumberOfWaysMemo(int n, int m, int limit, int[] cost, Long[][] dp, int index) {
		   if(index < 0 || index > n || m < 0)
			  return -1;
		   if(index == 0)
			   return 1;
		   if(dp[index][m] != null)
			   return dp[index][m];
		   long ways = 0;
		   for(int i=index-1; i>=index-limit && i >= 0; i--) {
			   long temp = getNumberOfWaysMemo(n, m - cost[index], limit, cost, dp, i);
			   if(temp != -1)
				   ways += temp;
		   }
		   dp[index][m] = ways;
		   return dp[index][m];
	  }
	  
	  public int numDistinct(String S, String T) {
		  int len1 = S.length(), len2 = T.length();
		  int[][] dp = new int[len2+1][len1+1];
		  for(int i=0; i<=len1; i++)
			  dp[0][i] = 1;
		  for(int i=1; i<=len2; i++) {
			  char c2 = T.charAt(i-1);
			  for(int j=1; j<=len1; j++) {
				  char c1 = S.charAt(j-1);
				  if(c1 == c2) {
					  dp[i][j] += dp[i][j-1] + dp[i-1][j-1];
				  }
				  else {
					  dp[i][j] += dp[i][j-1];
				  }
			  }
		  }
		  
		  return dp[len2][len1];
	  }
	  
	  public boolean canJump(int[] A) {
	      return canJumpMemo(A, 0, new Boolean[A.length]);
	  }
	  
	  private boolean canJumpMemo(int[] A, int index, Boolean[] memo) {
		  if(index >= A.length-1)
			  return true;
		  if(memo[index] != null)
			  return memo[index];
		  boolean result = false;
		  for(int i = A[index]; i>0; i--) {
			  if(canJumpMemo(A, index+i, memo)) {
				  result = true;
				  break;
			  }
		  }
		  memo[index] = result;
		  return result;
	  }
	  
	  
	  
	  
	  
	  public static void main(String[] args) {
		 
	  }

}
