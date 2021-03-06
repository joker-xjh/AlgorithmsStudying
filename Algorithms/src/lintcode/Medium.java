package lintcode;

import java.awt.Point;
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
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;




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
	  
	  public int jump(int[] A) {
	        int answer = jumpDFS(A, 0, new Integer[A.length]);
	        if(answer == Integer.MAX_VALUE)
	        	return 0;
	        return answer;
	  }
	  
	  private int jumpDFS(int[] A, int index, Integer[] dp) {
		  if(index >= A.length-1)
			  return 0;
		  if(dp[index] != null)
			  return dp[index];
		  int steps = Integer.MAX_VALUE;
		  for(int i=A[index]; i >0; i--) {
			  int temp = jumpDFS(A, index + i,dp);
			  if(temp != Integer.MAX_VALUE)
				  steps = Math.min(steps, temp+1);
		  }
		  dp[index] = steps;
		  return steps;
	  }
	  
	  
	  public int maxProduct(int[] nums) {
	     return (int) maxProductMemo(nums, nums.length-1, new Long[nums.length]);
	  }
	  
	  private long maxProductMemo(int[] nums, int index, Long[] dp) {
		  if(index < 0)
			  return Long.MIN_VALUE;
		  if(index == 0)
			  return nums[0];
		  if(dp[index] != null)
			  return dp[index];
		  long max = 1;
		  for(int i=index; i>=0; i--) {
			  max *= nums[i];
			  long temp = Math.max(max, maxProductMemo(nums, i-1, dp));
			  if(dp[index] == null)
				  dp[index] = Long.MIN_VALUE;
			  dp[index] = Math.max(dp[index], temp);
		  }
		  dp[index] = Math.max(dp[index], maxProductMemo(nums, index-1, dp));
		  return dp[index];
	  }
	  
	  
	  public int maxProduct2(int[] nums) {
		  int n = nums.length;
		  if(n == 0)
			  return 0;
		  long[] dp = new long[n+1];
		  Arrays.fill(dp, Long.MIN_VALUE);
		  dp[1] = nums[0];
		  for(int i=2; i<=n; i++) {
			  long max = 1;
			  for(int j=i; j>0; j--) {
				  max *= nums[j-1];
				  long temp = Math.max(max, dp[j-1]);
				  dp[i] = Math.max(dp[i], temp);
			  }
			  dp[i] = Math.max(dp[i], dp[i-1]);
		  }
		  
		  return (int) dp[n];
	  }
	  
	  
	  public int maxA(int N) { 
		  int[] dp = new int[N+1];  
	        for(int i=1;i<=N;i++){  
	            dp[i] = i;  
	            for(int j=3;j<i;j++){  
	                dp[i] = Math.max(dp[i], dp[i-j] * (j-1));  
	            }  
	        }  
	        return dp[N];  
	  }
	  
	  
	  public int maxWeight(int[][] nums) {
	        int m = nums.length, n = nums[0].length;
	        if(m == 0 || n == 0)
	        	return 0;
	        int[][] dp = new int[m][n];
	        dp[0][n-1] = nums[0][n-1];
	        for(int i=n-2; i>=0; i--)
	        	dp[0][i] = dp[0][i+1] + nums[0][i];
	        for(int i=1; i<m; i++)
	        	dp[i][n-1] = dp[i-1][n-1] + nums[i][n-1];
	        for(int i=1; i<m; i++) {
	        	for(int j = n-2; j>=0; j--) {
	        		dp[i][j] = Math.max(dp[i-1][j], dp[i][j+1]) + nums[i][j];
	        	}
	        }
	        
	        return dp[m-1][0];
	  }
	  
	  
	  public int numSquares(int n) {
	      List<Integer> list = new ArrayList<>();
	      for(long i=1; i*i <= n; i++) {
	    	  int temp = (int) (i * i);
	    	  list.add(temp);
	      }
	      
		  return numSquaresMemo(n, list, new HashMap<>());
	  }
	  
	  private int numSquaresMemo(int n, List<Integer> list, Map<Integer, Integer> memo) {
		  if(n < 0)
			  return -1;
		  if(n == 0)
			  return 0;
		  if(memo.containsKey(n))
			  return memo.get(n);
		  int squares = Integer.MAX_VALUE;
		  for(int suqare : list) {
			  int temp = numSquaresMemo(n - suqare, list, memo);
			  if(temp != -1)
				  squares = Math.min(squares, 1+temp);
			  if(temp == 0)
				  break;
		  }
		  memo.put(n, squares);
		  return squares;
	  }
	  
	  
	  public int numSquares2(int n) {
	      List<Integer> list = new ArrayList<>();
	      for(long i=1; i*i <= n; i++) {
	    	  int temp = (int) (i * i);
	    	  list.add(temp);
	      }
	      int[] dp = new int[n+1];
	      Arrays.fill(dp, Integer.MAX_VALUE);
	      dp[0] = 0;
	      for(int i=1; i<=n; i++) {
	    	  for(int square : list) {
	    		  if(square > i)
	    			  continue;
	    		  dp[i] = Math.min(dp[i], dp[i - square] + 1);
	    	  }
	      }
	      
		  return dp[n];
	  }
	  
	  public int numDecodings(String s) {
	        int length = s.length();
	        if(length == 0)
	        	return 0;
	        if(s.charAt(0) == '0')
	        	return 0;
	        if(length == 1)
	        	return 1;
	        char[] array = s.toCharArray();
	        int[] dp = new int[length+1];
	        dp[1] = 1;
	        int temp = (array[0] - '0') * 10 + array[1] - '0';
	        dp[2] = temp <= 26 && array[1] != '0' ? 2 : 1;
	        
	        for(int i=3; i<=length; i++) {
	        	char c1 = array[i-1];
	        	char c2 = array[i-2];
	        	int num = (c2 - '0') * 10 + c1 - '0';
	        	if(num == 0)
	        		return 0;
	        	if(c1 != '0')
	        		dp[i] += dp[i-1];
	        	if(c2 != '0' && num <= 26)
	        		dp[i] += dp[i-2];
	        }
	        return dp[length];
	  }
	  
	  
	  public int[] countBits(int num) {
		  if(num == 0)
			  return new int[] {0};
	        int[] dp = new int[num+1];
	        dp[0] = 0;
	        dp[1] = 1;
	        int a = 1 , b = 2;
	        for(int i=2; i<=num; i++) {
	        	if(i == b) {
	        		a = b;
	        		b *= 2;
	        	}
	        	dp[i] = dp[i-a]+1;
	        }
	        return dp;
	  }
	  
	  public int longestCommonSubsequence(String A, String B) {
		  int lenA = A.length();
		  int lenB = B.length();
		  int[][] dp = new int[lenA+1][lenB+1];
		  for(int i=1; i<=lenA; i++) {
			  char c1 = A.charAt(i-1);
			  for(int j=1; j<=lenB; j++) {
				  char c2 = B.charAt(j-1);
				  if(c1 == c2) {
					  dp[i][j] = dp[i-1][j-1] + 1;
				  }
				  else {
					  dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
				  }
			  }
		  }
		  return dp[lenA][lenB];
	  }
	  
	  
	  public int numTrees(int n) {
		  if(n < 3) {
			  return n == 0 ? 1 : n;
		  }
	      int[] dp = new int[n+1];
	      dp[0] = 1;
	      dp[1] = 1;
	      dp[2] = 2;
	      for(int i=3; i<=n; i++) {
	    	  for(int j=1; j<=i; j++) {
	    		  int left = dp[j-1];
	    		  int right = dp[i-j];
	    		  dp[i] += left * right;
	    	  }
	      }
	      return dp[n];
	  }
	  
	  
	  public List<TreeNode> generateTrees(int n) {
		  
		  return generateTreesDFS(1, n);
	  }
	  
	  
	  private List<TreeNode> generateTreesDFS(int low, int high){
		  List<TreeNode> list = new ArrayList<>();
		  if(low > high) {
			  list.add(null);
			  return list;
		  }
		  for(int i=low; i<=high; i++) {
			  List<TreeNode> left = generateTreesDFS(low, i-1);
			  List<TreeNode> right = generateTreesDFS(i+1, high);
			  for(TreeNode L :left)
				  for(TreeNode R : right) {
					  TreeNode node = new TreeNode(i);
					  node.left = L;
					  node.right = R;
					  list.add(node);
				  }
		  }
		  
		  return list;
	  }
	  
	  
	  public boolean canPartition(int[] nums) {
	       int sum = 0;
	       for(int num : nums)
	    	   sum += num;
	       if(sum % 2 != 0)
	    	   return false;
	       int n = nums.length;
	       boolean[][] dp = new boolean[sum/2+1][n+1];
	       Arrays.fill(dp[0], true);
	       
	       for(int i=1; i<=sum/2; i++) {
	    	   for(int j=1; j<=n; j++) {
	    		   int num = nums[j-1];
	    		   if(num > i)
	    			   dp[i][j] = dp[i][j-1];
	    		   else
	    			   dp[i][j] = dp[i][j-1] || dp[i - num][j-1];
	    	   }
	       }
	       
	       return dp[sum/2][n];
	  }
	  
	  public int findMin(int[] nums) {
		  if(nums.length == 0)
			  return 0;
		  if(nums.length == 1)
			  return nums[0];
		  int sum = 0;
	       for(int num : nums)
	    	   sum += num;
	       int n = nums.length;
	       boolean[][] dp = new boolean[sum/2+1][n+1];
	       Arrays.fill(dp[0], true);
	       
	       for(int i=1; i<=sum/2; i++) {
	    	   for(int j=1; j<=n; j++) {
	    		   int num = nums[j-1];
	    		   if(num > i)
	    			   dp[i][j] = dp[i][j-1];
	    		   else
	    			   dp[i][j] = dp[i][j-1] || dp[i - num][j-1];
	    	   }
	       }
	       int odd = sum % 2 == 0 ? 0 : 1;
	       for(int i=sum/2; i>0; i--) {
	    	   if(dp[i][n]) {
	    		   return (sum / 2 - i) * 2 + odd;
	    	   }
	       }
	       
	       
	       return -1;
	  }
	  
	  public int maxValue(String str) {
		  return maxValueMemo(str, 0, str.length()-1, new HashMap<>());
	  }
	  
	  private int maxValueMemo(String str, int left, int right, Map<String, Integer> memo) {
		  if(left == right)
			  return str.charAt(left) - '0';
		  String key = left+","+right;
		  if(memo.containsKey(key))
			  return memo.get(key);
		  int max = -1;
		  for(int i=right-1; i>=left; i--) {
			  int L = maxValueMemo(str, left, i, memo);
			  int R = maxValueMemo(str, i+1, right, memo);
			  max = Math.max(max, L + R);
			  max = Math.max(max, L * R);
		  }
		  memo.put(key, max);
		  return max;
	  }
	  
	  
	  public boolean validTree(int n, int[][] edges) {
		  if(n == 1)
			  return true;
		  Map<Integer, List<Integer>> graph = new HashMap<>();
		  for(int[] edge : edges) {
			  int p = edge[0], v = edge[1];
			  List<Integer> list = graph.get(p);
			  if(list == null) {
				  list = new ArrayList<>();
				  graph.put(p, list);
			  }
			  list.add(v);
			  list = graph.get(v);
			  if(list == null) {
				  list = new ArrayList<>();
				  graph.put(v, list);
			  }
			  list.add(p);
		  }
		  boolean[] visited = new boolean[n];
		  visited[0] = true;
		  if(!validTreeDFS(0, -1, graph, visited))
			  return false;
		  for(int i=1; i<n; i++) {
			  if(!visited[i])
				  return false;
		  }
		  return true;
	  }
	  
	  private boolean validTreeDFS(int cur, int father, Map<Integer, List<Integer>> graph, boolean[] visited) {
		  List<Integer> next = graph.get(cur);
		  if(next == null)
			  return false;
		  for(int v : next) {
			  if(v == father)
				  continue;
			  if(visited[v])
				  return false;
			  visited[v] = true;
			  if(!validTreeDFS(v, cur, graph, visited))
				  return false;
		  }
		  return true;
	  }
	  
	  
	  public float getSimilarity(List<String> words1, List<String> words2, List<List<String>> pairs) {
	      Map<String, String> UF = new HashMap<>();
	      for(List<String> list : pairs) {
	    	  for(String word : list) {
	    		  UF.put(word, word);
	    	  }
	      }
	      for(List<String> list : pairs) {
	    	  String one = list.get(0);
	    	  String one_root = getSimilarity_getRoot(UF, one);
	    	  for(int i=1; i<list.size(); i++) {
	    		  String two = list.get(i);
	    		  String two_root = getSimilarity_getRoot(UF, two);
	    		  if(!one_root.equals(two_root)) {
	    			  UF.put(two_root, one_root);
	    		  }
	    	  }
	      }
	      int[][] dp = new int[words1.size()+1][words2.size()+1];
	      for(int i=1; i<=words1.size(); i++) {
	    	  String one = words1.get(i-1);
	    	  for(int j=1; j<=words2.size(); j++) {
	    		  String two = words2.get(j-1);
	    		  if(getSimilarity_isSimilary(one, two, UF)) {
	    			  dp[i][j] = dp[i-1][j-1] + 1;
	    		  }
	    		  else {
	    			  dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
	    		  }
	    	  }
	      }
		  return dp[words1.size()][words2.size()] * 2.0f / (words1.size() + words2.size());
	  }
	  
	  private boolean getSimilarity_isSimilary(String one, String two, Map<String, String> UF) {
		  if(one.equals(two))
			  return true;
		  if(!UF.containsKey(one) || !UF.containsKey(two))
			  return false;
		  String one_root = getSimilarity_getRoot(UF, one);
		  String two_root = getSimilarity_getRoot(UF, two);
		  if(!one_root.equals(two_root))
			  return false;
		  return true;
	  }
	  
	  private String getSimilarity_getRoot(Map<String, String> UF, String word) {
		  while(!word.equals(UF.get(word))) {
			  UF.put(word, UF.get(UF.get(word)));
			  word = UF.get(word);
		  }
		  return word;
	  }
	  
	  public String DeleteDigits(String A, int k) {
	      String result = A;
	      while(k-- > 0) {
	    	  int i = 0;
	    	  while(i < result.length()-1 && result.charAt(i) <= result.charAt(i+1))
	    		  i++;
	    	  result = result.substring(0, i) + result.substring(i+1);
	      }
	      int i = 0;
	      while(i < result.length() && result.charAt(i) == '0')
	    	  i++;
	      result = result.substring(i);
	      return result;
	  }
	  
	  
	  public boolean hasPath(int[][] maze, int[] start, int[] destination) {
		  if(maze[start[0]][start[1]] == 1)
			  return false;
		  if(maze[destination[0]][destination[1]] == 1)
			  return false;
	      Queue<int[]> queue = new LinkedList<>();
		  int m = maze.length, n = maze[0].length;
		  boolean[][] visited = new boolean[m][n];
		  visited[start[0]][start[1]] = true;
		  queue.add(start);
		  while(!queue.isEmpty()) {
			  int size = queue.size();
			  for(int i=0; i<size; i++) {
				  int[] pos = queue.poll();
				  int x = pos[0], y = pos[1];
				  while(x > 0 && maze[x-1][y] == 0)
					  x--;
				  if(!visited[x][y]) {
					  visited[x][y] = true;
					  if(x == destination[0] && y == destination[1])
						  return true;
					  queue.add(new int[] {x, y});
				  }
				  
				  x = pos[0];
				  y = pos[1];
				  while(x < m-1 && maze[x+1][y] == 0)
					  x++;
				  if(!visited[x][y]) {
					  visited[x][y] = true;
					  if(x == destination[0] && y == destination[1])
						  return true;
					  queue.add(new int[] {x, y});
				  }
				  
				  
				  
				  x = pos[0];
				  y = pos[1];
				  while(y > 0 && maze[x][y-1] == 0)
					  y--;
				  if(!visited[x][y]) {
					  visited[x][y] = true;
					  if(x == destination[0] && y == destination[1])
						  return true;
					  queue.add(new int[] {x, y});
				  }
				  
				  
				  x = pos[0];
				  y = pos[1];
				  while(y < n-1 && maze[x][y+1] == 0)
					  y++;
				  if(!visited[x][y]) {
					  visited[x][y] = true;
					  if(x == destination[0] && y == destination[1])
						  return true;
					  queue.add(new int[] {x, y});
				  }
				  
			  }
		  }
		  return false;
	  }
	  
	  
	  public int cutting(int[] prices, int n) {
		  int[] dp = new int[n+1];
		  for(int i=1; i<=n; i++) {
			  dp[i] = prices[i-1];
			  int left = 1, right = i-1;
			  while(left <= right) {
				  dp[i] = Math.max(dp[left] + dp[right], dp[i]);
				  left++;
				  right--;
			  }
		  }
		  return dp[n];
	  }
	  
	  public int backPackVIII(int n, int[] value, int[] amount) {
		  int sum = 0;
		  for(int num : amount)
			  sum += num;
		  boolean[][] dp = new boolean[sum+1][n+1];
		  List<Integer> list = new ArrayList<>(sum);
		  for(int i=0; i<value.length; i++) {
			  for(int j=0; j<amount[i]; j++) {
				  list.add(value[i]);
			  }
		  }
		  dp[0][0] = true;
		  for(int i=1; i<=sum; i++) {
			  int num = list.get(i-1);
			  for(int j=0; j<=n; j++) {
				  if(j - num >= 0)
					  dp[i][j] |= dp[i-1][j-num];
				  dp[i][j] |= dp[i-1][j];
			  }
		  }
		  int answer = 0;
		  for(int i=1; i<=n; i++) {
			  if(dp[sum][i])
				  answer++;
		  }
	      return answer;
	  }
	  
	  
	  public int backPackVIII2(int n, int[] value, int[] amount) {
		  boolean[][] dp = new boolean[value.length+1][n+1];
		  for(int i=0; i<dp.length; i++) {
			  dp[i][0] = true;
		  }
		  for(int i=1; i<=value.length; i++) {
			  for(int j=1; j<=n; j++) {
				  for(int k=1; k<=amount[i-1]; k++) {
					  if(j - value[i-1]*k >= 0) {
						  dp[i][j] |= dp[i-1][j-value[i-1]*k];
					  }
					  dp[i][j] |= dp[i-1][j];
				  }
			  }
		  }
		  int answer = 0;
		  for(int i=1; i<=n; i++) {
			  if(dp[value.length][i])
				  answer++;
		  }
		  return answer;
	  }
	  
	  
	  public int backPackVIII3(int n, int[] value, int[] amount) {
		  List<Integer> value_list = new ArrayList<>(value.length * 3);
		  for(int i=0; i<value.length; i++) {
			  for(int j=1; j<=amount[i]; j<<=1) {
				  value_list.add(value[i] * j);
				  amount[i] -= j;
			  }
			  if(amount[i] > 0) {
				  value_list.add(amount[i] * value[i]);
			  }
		  }
		  boolean[] dp = new boolean[n+1];
		  dp[0] = true;
		  for(int i=1; i<=value_list.size(); i++) {
			  int v = value_list.get(i-1);
			  for(int j=n; j>=v; j--) {
				  dp[j] |= dp[j - v];
			  }
		  }
		  int answer = 0;
		  for(int i=1; i<=n; i++) {
			  if(dp[i])
				  answer++;
		  }
		  return answer;
	  }
	  
	  public int coinChange(int[] coins, int amount) {
		  int[][] dp = new int[coins.length+1][amount+1];
		  for(int i=0; i<dp.length; i++) {
			  Arrays.fill(dp[i], -1);
		  }
		  dp[0][0] = 0;
		  for(int i=1; i<=coins.length; i++) {
			  int coin = coins[i-1];
			  dp[i][0] = 1;
			  for(int j=1; j<=amount; j++) {
				  for(int k=0; k*coin <= j; k++) {
					  if(dp[i-1][j - k*coin] != -1) {
						  if(dp[i][j] == -1)
							  dp[i][j] = dp[i-1][j - k*coin] + k;
						  else 
							  dp[i][j] = Math.min(dp[i][j], dp[i-1][j - k*coin] + k);
					  }
				  }
			  }
		  }
		  if(dp[coins.length][amount] != -1)
			  return dp[coins.length][amount];
		  return -1;
	  }
	  
	  public int coinChange2(int[] coins, int amount) {
		  int[] dp = new int[amount+1];
		  Arrays.fill(dp, -1);
		  dp[0] = 0;
		  for(int coin : coins) {
			  for(int i=coin; i<=amount; i++) {
				  if(dp[i-coin] == -1)
					  continue;
				  if(dp[i] == -1)
					  dp[i] = dp[i-coin] + 1;
				  else
					  dp[i] = Math.min(dp[i], dp[i-coin] + 1);
			  }
		  }
		  return dp[amount];
	  }
	  
	  
	  public int shortestDistance(int[][] grid) {
		  int m = grid.length, n = grid[0].length;
		  int post_count = 0;
		  Queue<int[]> queue = new LinkedList<>();
		  int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}};
	      @SuppressWarnings("unchecked")
	      Set<Integer>[][] sets = new Set[m][n];
	      int[][] distance = new int[m][n];
		  for(int i=0; i<m; i++) {
			  for(int j=0; j<n; j++) {
				  sets[i][j] = new HashSet<>();
				  if(grid[i][j] == 1) {
					  queue.add(new int[] {i, j, post_count++});
				  }
			  }
		  }
		  int move = 0;
		  while(!queue.isEmpty()) {
			  move++;
			  int size = queue.size();
			  for(int t=0; t<size; t++) {
				  int[] pos = queue.poll();
				  for(int[] dir : dirs) {
					  int x = dir[0] + pos[0];
					  int y = dir[1] + pos[1];
					  if(x<0 || x>=m || y<0 || y>=n || grid[x][y] != 0)
						  continue;
					  if(!sets[x][y].add(pos[2]))
						  continue;
					  distance[x][y] += move;
					  queue.add(new int[] {x, y, pos[2]});
				  }
			  }
		  }
		  int answer = Integer.MAX_VALUE;
		  for(int i=0; i<m; i++) {
			  for(int j=0; j<n; j++) {
				  if(sets[i][j].size() == post_count) {
					  answer = Math.min(answer, distance[i][j]);
				  }
			  }
		  }
		  if(answer == Integer.MAX_VALUE)
			  return -1;
		  return answer;
	  }
	  
	  
	   class SegmentTreeNode {
		       public int start, end;
		       public int max;
		       public int min;
		       public int count;
		       public long sum;
		       public SegmentTreeNode left, right;
		       public SegmentTreeNode(int start, int end) {
		           this.start = start;
		           this.end = end;
		           this.left = this.right = null;
		       }
	   }
	   
	   public SegmentTreeNode build(int start, int end) {
		   	if(start > end)
			   return null;
		   	if(start == end)
		   		return new SegmentTreeNode(start, end);
	        SegmentTreeNode node = new SegmentTreeNode(start, end);
	        int mid = start + (end - start) / 2;
	        node.left = build(start, mid);
	        node.right = build(mid+1, end);
	        return node;
	   }
	  
	   public int query(SegmentTreeNode root, int start, int end) {
	       if(start == root.start && end == root.end)
	    	   return root.max;
		   int mid = (root.start + root.end) >>> 1;
		   if(end <= mid) {
			   return query(root.left, start, end);
		   }
		   else if(start > mid) {
			   return query(root.right, start, end);
		   }
		   else {
			   int left_max = query(root.left, start, mid);
			   int right_max = query(root.right, mid+1, end);
			   return Math.max(left_max, right_max);
		   }
	   }
	  
	  
	   public void modify(SegmentTreeNode root, int index, int value) {
	        if(root.start == root.end) {
	        	root.max = value;
	        	return;
	        }
	        int mid = (root.start + root.end) >>> 1;
			if(index <= mid) {
				modify(root.left, index, value);
			}
			else {
				modify(root.right, index, value);
			}
			root.max = Math.max(root.left.max, root.right.max);
	   }
	  
	  
	   public int queryCount(SegmentTreeNode root, int start, int end) {
		   	if(root == null)
		   		return 0;
		   	start = Math.max(start, root.start);
		   	end = Math.min(end, root.end);
	        if(root.start == start && root.end == end)
	        	return root.count;
	        int mid = root.start + (root.end - root.start) / 2;
	        if(end <= mid) {
	        	return query(root.left, start, end);
	        }
	        else if(start > mid) {
	        	return query(root.right, start, end);
	        }
	        else {
	        	return query(root.left, start, mid) + query(root.right, mid+1, end);
	        }
	   }
	  
	  
	   public class Interval {
		        int start, end;
		        Interval(int start, int end) {
		            this.start = start;
		            this.end = end;
		        }
	   }
	   
	   public List<Long> intervalSum(int[] A, List<Interval> queries) {
	       SegmentTreeNode root = intervalSumBuild(A, 0, A.length-1);
	       List<Long> list = new ArrayList<>(queries.size());
	       for(Interval interval : queries) {
	    	   list.add(intervalSumBuildQuery(A, interval.start, interval.end, root));
	       }
		   return list;
	   }
	   
	   public void intervalSumBuildModify(int index, int value, SegmentTreeNode node) {
	        if(node.start == node.end) {
	        	node.sum = value;
	        	return;
	        }
	        int mid = node.start + (node.end - node.start) / 2;
	        if(index <= mid)
	        	intervalSumBuildModify(index, value, node.left);
	        else if(index > mid)
	        	intervalSumBuildModify(index, value, node.right);
	        node.sum = node.left.sum + node.right.sum;
	    }
	   
	   private long intervalSumBuildQuery(int[] A, int start, int end, SegmentTreeNode node) {
		   if(node == null)
			   return 0;
		   if(node.start == start && node.end == end) {
			   return node.sum;
		   }
		   int mid = node.start + (node.end - node.start) / 2;
		   if(end <= mid) {
			   return intervalSumBuildQuery(A, start, end, node.left);
		   }
		   else if(start > mid) {
			   return intervalSumBuildQuery(A, start, end, node.right);
		   }
		   else {
			   return intervalSumBuildQuery(A, start, mid, node.left) +
					   intervalSumBuildQuery(A, mid+1, end, node.right);
		   }
	   }
	   
	   private SegmentTreeNode intervalSumBuild(int[] A, int start, int end) {
		   if(start > end)
			   return null;
		   SegmentTreeNode node = new SegmentTreeNode(start, end);
		   if(start == end) {
			   node.sum = A[start];
			   return node;
		   }
		   int mid = start + (end - start) / 2;
		   node.left = intervalSumBuild(A, start, mid);
		   node.right = intervalSumBuild(A, mid+1, end);
		   node.sum = node.left.sum + node.right.sum;
		   return node;
	   }
	  
	   private SegmentTreeNode intervalMinBuild(int[] A, int start, int end) {
		   if(start > end)
			   return null;
		   SegmentTreeNode node = new SegmentTreeNode(start, end);
		   if(start == end) {
			   node.min = A[start];
			   return node;
		   }
		   int mid = start + (end - start) / 2;
		   node.left = intervalMinBuild(A, start, mid);
		   node.right = intervalMinBuild(A, mid+1, end);
		   node.min = Math.min(node.left.min, node.right.min);
		   return node;
	   }
	   private int intervalMinQuery(int start, int end, SegmentTreeNode node) {
		   if(node.start == start && node.end == end) {
			   return node.min;
		   }
		   int mid = node.start + (node.end - node.start) / 2;
		   if(end <= mid)
			   return intervalMinQuery(start, end, node.left);
		   else if(start > mid)
			   return intervalMinQuery(start, end, node.right);
		   else {
			return Math.min(intervalMinQuery(start, mid, node.left),
							intervalMinQuery(mid+1, end, node.right));
		}
	   }
	   public List<Integer> intervalMinNumber(int[] A, List<Interval> queries) {
		   List<Integer> list = new ArrayList<>(queries.size());
		   SegmentTreeNode root = intervalMinBuild(A, 0, A.length-1);
		   for(Interval interval : queries) {
			   list.add(intervalMinQuery(interval.start, interval.end, root));
		   }
		   return list;
	   }
	   
	   
	   public int[] business(int[] A, int k) {
	        int[] profit = new int[A.length];
	        SegmentTreeNode root= null;
	        if(k > 10)
	        	 root = intervalMinBuild(A, 0, A.length-1);
	        for(int i=0; i<A.length; i++) {
	        	int min = Integer.MAX_VALUE;
	        	if(k > 10) {
	        		int start = Math.max(0, i-k);
		        	int end = Math.min(A.length-1, i+k);
		        	profit[i] = A[i] - intervalMinQuery(start, end, root);
		        	
	        	}
	        	else {
	        		for(int j=Math.max(0, i-k); j<i; j++) {
	        			min = Math.min(min, A[j]);
	        		}
	        		for(int j=i+1; j<=Math.min(A.length-1, i+k); j++) {
	        			min = Math.min(min, A[j]);
	        		}
	        		profit[i] = Math.max(0, A[i] - min);
	        	}
	        	
	        }
	        return profit;
	   }
	   
	   
	   public List<Integer> countOfSmallerNumber(int[] A, int[] queries) {
	        List<Integer> list = new ArrayList<>(queries.length);
	        int[] data = new int[10001];
	        for(int num : A) {
	        	data[num]++;
	        }
	        for(int i=1; i<=10000; i++) {
	        	data[i] += data[i-1];
	        }
	        for(int query : queries) {
	        	if(query == 0) {
	        		list.add(0);
	        	}
	        	else {
	        		list.add(data[query-1]);
	        	}
	        }
	        return list;
	   }
	   
	   
	   public int countOfAirplanes(List<Interval> airplanes) {
		   	if(airplanes == null || airplanes.isEmpty())
			   return 0;
	        List<int[]> list = new ArrayList<>(airplanes.size()*2);
	        for(Interval interval : airplanes) {
	        	list.add(new int[] {interval.start, 1});
	        	list.add(new int[] {interval.end, -1});
	        }
	        Collections.sort(list, new Comparator<int[]>() {
				@Override
				public int compare(int[] o1, int[] o2) {
					if(o1[0] == o2[0]) {
						return o1[1] - o2[1];
					}
					return o1[0] - o2[0];
				}
			});
	        int planes = 0;
	        int max = 0;
	        for(int[] pair : list) {
	        	planes += pair[1];
	        	max = Math.max(max, planes);
	        }
	        return max;
	   }
	   
	   
	   public List<Interval> merge(List<Interval> intervals) {
	        List<Interval> list = new ArrayList<>();
	        if(intervals == null || intervals.isEmpty())
				   return list;
	        Collections.sort(intervals, (a,b) -> (a.start - b.start));
	        Interval cmp = intervals.get(0);
	        for(int i=1; i<intervals.size(); i++) {
	        	Interval interval = intervals.get(i);
	        	if(interval.start > cmp.end) {
	        		list.add(new Interval(cmp.start, cmp.end));
	        		cmp.start = interval.start;
	        		cmp.end = interval.end;
	        	}
	        	else {
	        		cmp.end = Math.max(cmp.end, interval.end);
	        	}
	        }
	        list.add(new Interval(cmp.start, cmp.end));
	        return list;
	   }
	   
	   public int shortestPath(boolean[][] grid, Point source, Point destination) {
		   if(source.x == destination.x && source.y == destination.y)
			   return 0;
		   int m = grid.length, n = grid[0].length;
	       boolean[][] visited = new boolean[m][n];
		   int[][] dirs = {{1,2}, {1,-2}, {-1,2}, {-1,-2},
				   			{2,1}, {2,-1}, {-2,1}, {-2,-1}};
		   visited[source.x][source.y] = true;
		   Queue<int[]> queue = new LinkedList<>();
		   queue.add(new int[] {source.x, source.y, 0});
		   while(!queue.isEmpty()) {
			   int size = queue.size();
			   for(int t=0; t<size; t++) {
				   int[] pair = queue.poll();
				   for(int[] dir : dirs) {
					   int x = pair[0] + dir[0];
					   int y = pair[1] + dir[1];
					   if(x<0 || x>=m || y<0 || y>=n || visited[x][y] || grid[x][y])
						   continue;
					   visited[x][y] = true;
					   int[] new_pair = {x, y, pair[2]+1};
					   if(x == destination.x && y == destination.y)
						   return new_pair[2];
					   queue.add(new_pair);
				   }
			   }
		   }
		   
		   return -1;
	   }
	   
	   
	   public int shortestPath2(boolean[][] grid, Point source, Point destination) {
		   if(source.x == destination.x && source.y == destination.y)
			   return 0;
		   int m = grid.length, n = grid[0].length;
		   int[][] start_visited = new int[m][n];
		   int[][] end_visited = new int[m][n];
		   for(int i=0; i<m; i++) {
			   Arrays.fill(start_visited[i], -1);
			   Arrays.fill(end_visited[i], -1);
		   }
		   start_visited[source.x][source.y] = 0;
		   end_visited[destination.x][destination.y] = 0;
		   int[][] dirs = {{1,2}, {1,-2}, {-1,2}, {-1,-2},
				   			{2,1}, {2,-1}, {-2,1}, {-2,-1}};
		   Queue<int[]> start_queue = new LinkedList<>();
		   Queue<int[]> end_queue = new LinkedList<>();
		   start_queue.add(new int[] {source.x, source.y});
		   end_queue.add(new int[] {destination.x, destination.y});
		   while(!start_queue.isEmpty() && !end_queue.isEmpty()) {
			   int size = start_queue.size();
			   for(int t=0; t<size; t++) {
				   int[] pair = start_queue.poll();
				   for(int[] dir : dirs) {
					   int x = pair[0] + dir[0];
					   int y = pair[1] + dir[1];
					   if(x<0 || x>=m || y<0 || y>=n || (start_visited[x][y] != -1) || grid[x][y])
						   continue;
					   start_visited[x][y] = start_visited[pair[0]][pair[1]] + 1;
					   if(end_visited[x][y] != -1)
						   return start_visited[x][y] + end_visited[x][y];
					   int[] new_pair = {x, y};
					   start_queue.add(new_pair);
				   }
			   }
			   size = end_queue.size();
			   for(int t=0; t<size; t++) {
				   int[] pair = end_queue.poll();
				   for(int[] dir : dirs) {
					   int x = pair[0] + dir[0];
					   int y = pair[1] + dir[1];
					   if(x<0 || x>=m || y<0 || y>=n || (end_visited[x][y] != -1) || grid[x][y])
						   continue;
					   end_visited[x][y] = end_visited[pair[0]][pair[1]] + 1;
					   if(start_visited[x][y] != -1)
						   return start_visited[x][y] + end_visited[x][y];
					   int[] new_pair = {x, y};
					   end_queue.add(new_pair);
				   }
			   }
		   }
		   
		   return -1;
	   }
	   
	   
	   class NumMatrix {
		   
		   long[][] dp;

		    public NumMatrix(int[][] matrix) {
		        dp = new long[matrix.length][matrix[0].length+1];
		        for(int i=0; i<matrix.length; i++) {
		        	for(int j=0; j<matrix[0].length; j++) {
		        		dp[i][j+1] = dp[i][j] + matrix[i][j];
		        	}
		        }
		        for(int i=1; i<matrix.length; i++) {
		        	for(int j=0; j<matrix[0].length; j++) {
		        		dp[i][j+1] += dp[i-1][j+1];
		        	}
		        }
		        
		    }
		    
		    public int sumRegion(int row1, int col1, int row2, int col2) {
		        long sum = dp[row2][col2+1];
		        if(col1 > 0) {
		        	sum -= dp[row2][col1-1+1];
		        }
		        if(row1 > 0) {
		        	sum -= dp[row1-1][col2+1];
		        }
		        if(row1 > 0 && col1 > 0) {
		        	sum += dp[row1-1][col1-1+1];
		        }
		    	return (int) sum;
		    }
		}
	   
	   
	   public boolean isBipartite(int[][] graph) {
		   int N = graph.length;
		   int[] color = new int[N];
		   for(int i=0; i<N; i++) {
			   if(color[i] == 0 && !isBipartite(graph, color, i, 1))
				   return false;
		   }
		   return true;
	   }
	   
	   private boolean isBipartite(int[][] graph, int[] color, int index, int tag) {
		   color[index] = tag;
		   for(int i : graph[index]) {
			   if(color[i] == tag)
				   return false;
			   if(color[i] == 0 && !isBipartite(graph, color, i, -tag))
				   return false; 
		   }
		   return true;
	   }
	   
	   
	    
	   public ListNode[] rehashing(ListNode[] hashTable) {
	       int size = hashTable.length;
		   int new_size = size << 1;
		   ListNode[] new_table = new ListNode[new_size];
		   for(int i=0; i<size; i++) {
			   for(ListNode node = hashTable[i]; node!=null; node=node.next) {
				   int val = node.val;
				   int mod = val % new_size;
				   if(mod < 0)
					   mod = (mod + new_size) % new_size;
				   if(new_table[mod] == null) {
					   new_table[mod] = new ListNode(val);
				   }
				   else {
					   ListNode temp = new_table[mod];
					   while(temp.next != null)
						   temp = temp.next;
					   temp.next = new ListNode(val);
				   }
			   }
		   }
		   return new_table;
	   }
	   
	   
	   public void sortColors2(int[] colors, int k) {
		   int index = 0;
		   for(int i=1; i<=k; i++) {
			   for(int j=index; j<colors.length; j++) {
				   if(colors[j] == i) {
					   int temp = colors[index];
					   colors[index] = colors[j];
					   colors[j] = temp;
					   index++;
				   }
			   }
		   }
	   }
	   
	   
	   public int canCompleteCircuit(int[] gas, int[] cost) {
	        int start = 0;
	        int sum = 0;
	        int total = 0;
	        for(int i=0; i<gas.length; i++) {
	        	sum += gas[i] - cost[i];
	        	total += gas[i] - cost[i];
	        	if(sum < 0) {
	        		sum = 0;
	        		start = i+1;
	        	}
	        }
	        if(total < 0)
	        	return -1;
	        return start;
	    }
	   
	   public int maxKilledEnemies(char[][] grid) {
		   if(grid == null || grid.length == 0)
			   return 0;
	       int m = grid.length, n = grid[0].length;
		   int[][] up = new int[m][n];
		   int[][] down = new int[m][n];
		   int[][] left = new int[m][n];
		   int[][] right = new int[m][n];
		   for(int j=0; j<n; j++) {
			   for(int i=0; i<m; i++) {
				   if(i == 0) {
					   up[i][j] = grid[i][j] == 'E' ? 1 : 0;
				   }
				   else {
					   if(grid[i][j] == 'W') {
						   up[i][j] = 0;
					   }
					   else {
						   up[i][j] = up[i-1][j] + (grid[i][j] == 'E' ? 1 : 0);
					   }
				   }
			   }
		   }
		   
		   for(int j=0; j<n; j++) {
			   for(int i=m-1; i>=0; i--) {
				   if(i == m-1) {
					   down[i][j] = grid[i][j] == 'E' ? 1 : 0;
				   }
				   else {
					   if(grid[i][j] == 'W') {
						   down[i][j] = 0;
					   }
					   else {
						   down[i][j] = down[i+1][j] + (grid[i][j] == 'E' ? 1 : 0);
					   }
				   }
			   }
		   }
		   
		   for(int i=0; i<m; i++) {
			   for(int j=0; j<n; j++) {
				   if(j == 0) {
					   left[i][j] = grid[i][j] == 'E' ? 1 : 0;
				   }
				   else {
					   if(grid[i][j] == 'W') {
						   left[i][j] = 0;
					   }
					   else {
						   left[i][j] = left[i][j-1] + (grid[i][j] == 'E' ? 1 : 0);
					   }
				   }
			   }
		   }
		   
		   for(int i=0; i<m; i++) {
			   for(int j=n-1; j>=0; j--) {
				   if(j == n-1) {
					   right[i][j] = grid[i][j] == 'E' ? 1 : 0;
				   }
				   else {
					   if(grid[i][j] == 'W') {
						   right[i][j] = 0;
					   }
					   else {
						   right[i][j] = right[i][j+1] + (grid[i][j] == 'E' ? 1 : 0);
					   }
				   }
			   }
		   }
		   int max = 0;
		   for(int i=0; i<m; i++) {
			   for(int j=0; j<n; j++) {
				   if(grid[i][j] == '0') {
					   max = Math.max(max, left[i][j] + right[i][j] + up[i][j] + down[i][j]);
				   }
			   }
		   }  
		   return max;
	    }
	   
	   
	   public int backPackX(int n) {
	       boolean[] dp = new boolean[n+1];
	       dp[0] = true;
		   int[] goods = {150, 250, 350};
		   for(int good : goods) {
			   for(int j=good; j<=n; j++) {
				   dp[j] |= dp[j-good];
			   }
		   }
		   for(int i=n; i>0; i--) {
			   if(dp[i])
				   return n - i;
		   }
		   return -1;
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
	   
	   
	   public void rerange(int[] A) {
	        int index = 0;
	        int negative_count = 0;
	        for(int i=0; i<A.length; i++) {
	        	if(A[i] < 0) {
	        		int temp = A[i];
	        		A[i] = A[index];
	        		A[index] = temp;
	        		index++;
	        		negative_count++;
	        	}
	        }
	        if(negative_count < A.length - negative_count) {
	        	if(negative_count % 2 == 0)
	        		index++;
	        	for(int i=0; index<A.length; index+=2,i+=2) {
	        		int temp = A[i];
	        		A[i] = A[index];
	        		A[index] = temp;
	        	}
	        }
	        else if(negative_count > A.length - negative_count) {
	        	if(negative_count % 2 == 1)
	        		index -= 2;
	        	else
	        		index -=1;
	        	for(int i=A.length-1; index>=0; index-=2,i-=2) {
	        		int temp = A[i];
	        		A[i] = A[index];
	        		A[index] = temp;
	        	}
	        }
	        else {
	        	int start = negative_count % 2 == 1 ? 0 : 1;
	        	for(int i=start; index < A.length; index+=2,i+=2) {
	        		int temp = A[i];
	        		A[i] = A[index];
	        		A[index] = temp;
	        	}
	        }
	   }
	   
	   
	   public boolean sequenceReconstruction(int[] org, int[][] seqs) {
		   if(org.length == 0) {
			   int size = 0;
			   for(int[] array : seqs)
				   size += array.length;
			   if(size == 0)
				   return true;
			   return false;
		   }
	       if(seqs == null || seqs.length == 0)
	    	   return false;
	       int n = org.length;
	       int[] indegree = new int[n+1];
	       Arrays.fill(indegree, -1);
	       List<List<Integer>> graph = new ArrayList<>();
		   for(int i=0; i<=n;i++) {
			   graph.add(new ArrayList<>());
		   }
		   for(int[] array : seqs) {
			   if(array.length == 1) {
				   if(array[0] < 1 || array[0] > n)
					   return false;
				   indegree[array[0]] = 0;
			   }
			   else if(array.length > 1) {
				   for(int i=1; i<array.length; i++) {
					   int pre = array[i-1];
					   int cur = array[i];
					   if(pre < 1 || pre > n || cur < 1 || cur > n)
						   return false;
					   graph.get(pre).add(cur);
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
		   Queue<Integer> queue = new LinkedList<>();
		   int index = 0;
		   for(int i=1; i<=n; i++) {
			   if(indegree[i] == 0) {
				   queue.add(i);
			   }
		   }
		   if(queue.size() != 1)
			   return false;
		   while(!queue.isEmpty()) {
			   int cur = queue.poll();
			   if(org[index] != cur)
				   return false;
			   index++;
			   for(int next : graph.get(cur)) {
				   indegree[next]--;
				   if(indegree[next] == 0) {
					   queue.add(next);
				   }
			   }
			   if(queue.size() > 1)
				   return false;
			   if(index >= org.length && !queue.isEmpty())
				   return false;
		   }
		   if(index != org.length)
			   return false;
		   
		   return true;
	   }
	   
	 
	   
	   
	   
	   public List<Integer> numIslands2(int n, int m, Point[] operators) {
		    if(operators == null || operators.length == 0)
	        	return new ArrayList<>();
	        List<Integer> list = new ArrayList<>(operators.length);
	        int[] parent = new int[operators.length];
	        Arrays.fill(parent, -1);
	        int lands = 0;
	        int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}};
	        int[][] matrix = new int[n][m];
	        for(int i=0; i<n; i++) {
	        	Arrays.fill(matrix[i], -1);
	        }
	        for(int a=0; a<operators.length; a++) {
	        	Point point = operators[a];
	        	int x = point.x;
	        	int y = point.y;
	        	if(x < 0 || x >= n || y < 0 || y >= m || matrix[x][y] != -1) {
	        		list.add(lands);
	        		continue;
	        	}
	        	matrix[x][y] = a;
	        	lands++;
	        	parent[a] = a;
	        	for(int[] dir : dirs) {
	        		int i = x + dir[0];
	        		int j = y + dir[1];
	        		if(i < 0 || i >= n || j < 0 || j >= m || matrix[i][j] == -1)
	        			continue;
	        		int p = numIslands2Find(parent, matrix[i][j]);
	        		parent[a] = p;
	        		lands--;
	        		break;
	        	}
	        	for(int[] dir : dirs) {
	        		int i = x + dir[0];
	        		int j = y + dir[1];
	        		if(i < 0 || i >= n || j < 0 || j >= m || matrix[i][j] == -1)
	        			continue;
	        		int p = numIslands2Find(parent, matrix[i][j]);
	        		if(p != parent[a]) {
	        			lands--;
	        			parent[p] = parent[a];
	        		}
	        	}
	        	list.add(lands);
	        }
	        return list;
	    }
	   
	   private int numIslands2Find(int[] parent, int i) {
		   while(i != parent[i]) {
			   parent[i] = parent[parent[i]];
			   i = parent[i];
		   }
		   return i;
	   }
	   
	   
	   public int numsofIsland(boolean[][] grid, int k) {
		   int lands = 0;
		   if(grid == null || grid.length == 0)
			   return lands;
		   Queue<int[]> queue = new LinkedList<>();
		   int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}};
		   for(int i=0; i<grid.length; i++) {
			   for(int j=0; j<grid[0].length; j++) {
				   if(!grid[i][j])
					   continue;
				   grid[i][j] = false;
				   int temp = 1;
				   queue.add(new int[] {i, j});
				   while(!queue.isEmpty()) {
					   int[] pos = queue.poll();
					   for(int[] d : dirs) {
						   int x = d[0] + pos[0];
						   int y = d[1] + pos[1];
						   if(x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || !grid[x][y])
							   continue;
						   grid[x][y] = false;
						   temp++;
						   queue.add(new int[] {x, y});
					   }
				   }
				   if(temp >= k)
					   lands++;
				   queue.clear();
			   }
		   }
		   
		   return lands;   
	   }
	   
	   int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}};
	   public int numberofDistinctIslands(int[][] grid) {
	       Set<String> set = new HashSet<>();
	       if(grid == null || grid.length == 0)
	    	   return 0;
	       for(int i=0; i<grid.length; i++) {
	    	   for(int j=0; j<grid[0].length; j++) {
	    		   if(grid[i][j] == 0)
	    			   continue;
	    		   grid[i][j] = 0;
	    	       StringBuilder sb = new StringBuilder();
	    		   sb.append("(0,0)");
	    		   numberofDistinctIslandsDFS(grid, i, j, sb,new int[] {0,0});
	    		   set.add(sb.toString());
	    	   }
	       }
	       
	       return set.size();
	   }
	   
	   private void numberofDistinctIslandsDFS(int[][] grid, int i, int j, StringBuilder sb, int[] move) {
		   for(int[] d : dirs) {
			   int x = d[0] + i;
			   int y = d[1] + j;
			   if(x<0 || x>=grid.length || y<0 || y>=grid[0].length || grid[x][y] == 0)
				   continue;
			   grid[x][y] = 0;
			   int[] next_move = new int[] {move[0]+d[0], move[1]+d[1]};
			   String str = "(" + next_move[0] + "," + next_move[1] + ")";
			   sb.append(str);
			   numberofDistinctIslandsDFS(grid, x, y, sb,next_move);
		   }
	   }
	   
	   
	   public void surroundedRegions(char[][] board) {
		   if(board == null || board.length == 0)
			   return;
		   for(int i=0; i<board.length; i++) {
			   if(board[i][0] == 'O') {
				   board[i][0] = '@';
				   surroundedRegionsDFS(board, i, 0);
			   }
			   if(board[i][board[0].length-1] == 'O') {
				   board[i][board[0].length-1] = '@';
				   surroundedRegionsDFS(board, i, board[0].length-1);
			   }
		   }
		   for(int i=0; i<board[0].length; i++) {
			   if(board[board.length-1][i] == 'O') {
				   board[board.length-1][i] = '@';
				   surroundedRegionsDFS(board, board.length-1, i);
			   }
			   if(board[0][i] == 'O') {
				   board[0][i] = '@';
				   surroundedRegionsDFS(board, 0, i);
			   }
		   }
		   for(int i=0; i<board.length; i++) {
			   for(int j=0; j<board[0].length; j++) {
				   if(board[i][j] == 'O') {
					   board[i][j] = 'X';
				   }
				   else if(board[i][j] == '@') {
					   board[i][j] = 'O';
				   }
			   }
		   }
		   
	   }
	   
	   private void surroundedRegionsDFS(char[][] board, int i, int j) {
		   for(int[] d : dirs) {
			   int x = d[0] + i;
			   int y = d[1] + j;
			   if(x<0 || x>=board.length || y<0 || y>=board[0].length || board[x][y] != 'O')
				   continue;
			   board[x][y] = '@';
			   surroundedRegionsDFS(board, x, y);
		   }
	   }
	   
	   
	   public int minMeetingRooms(List<Interval> intervals) {
		   int rooms = 0;
		   List<int[]> list_pos = new ArrayList<>(intervals.size() * 2);
		   for(Interval interval : intervals) {
			   list_pos.add(new int[] {interval.start, 1});
			   list_pos.add(new int[] {interval.end, -1});
		   }
		   Collections.sort(list_pos, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				if(o1[0] == o2[0])
					return o1[1] - o2[1];
				return o1[0] - o2[0];
			}
		   });
		   int temp = 0;
		   for(int[] pos : list_pos) {
			   temp += pos[1];
			   rooms = Math.max(rooms, temp);
		   }
		   return rooms;
	   }
	   
	   
	   public List<Integer> partitionLabels(String S) {
	        List<Integer> list = new ArrayList<>();
	        if(S == null || S.length() == 0)
	        	return list;
	        int[] start = new int[26];
	        int[] end = new int[26];
	        Arrays.fill(start, Integer.MAX_VALUE);
	        Arrays.fill(end, -1);
	        for(int i=0,j=S.length()-1; j>=0;i++,j--) {
	        	char c1 = S.charAt(i);
	        	end[c1 - 'a'] = Math.max(i, end[c1-'a']);
	        	char c2 = S.charAt(j);
	        	start[c2 - 'a'] = Math.min(j, start[c2 - 'a']);
	        }
	        List<int[]> list_pos = new ArrayList<>();
	        for(int i=0; i<26; i++) {
	        	if(end[i] == -1)
	        		continue;
	        	list_pos.add(new int[] {start[i], 1});
	        	list_pos.add(new int[] {end[i], -1});
	        }
	        Collections.sort(list_pos, new Comparator<int[]>() {
				@Override
				public int compare(int[] o1, int[] o2) {
					if(o1[0] == o2[0]) {
						return o2[1] - o1[1];
					}
					return o1[0] - o2[0];
				}
			});
	        int left = 0;
	        int count = 0;
	        for(int[] pos : list_pos) {
	        	if(count == 0 && pos[1] == 1) {
	        		left = pos[0];
	        	}
	        	count += pos[1];
	        	if(count == 0) {
	        		list.add(pos[0] - left + 1);
	        	}
	        }
	        return list;
	   } 
	   
	   
	   
	   public int getBestRoad(int[][] grid) {
		   int answer = Integer.MAX_VALUE;
		   if(grid == null || grid.length == 0 || grid[0].length == 0)
			   return 0;
		   int m = grid.length, n = grid[0].length;
		   int[][] top_left = new int[m][n];
		   int[][] bottom_right = new int[m][n];
		   for(int i=0; i<m; i++) {
			   Arrays.fill(top_left[i], -1);
			   Arrays.fill(bottom_right[i], -1);
		   }
		   grid[0][0] = 0;
		   getBestRoadBFS(grid, new int[] {0,0}, top_left);
		   getBestRoadBFS(grid, new int[] {m-1,n-1}, bottom_right);
		   if(grid[m-1][n-1] == 1) {
			   if(bottom_right[0][0] == -1)
				   return -1;
			   return bottom_right[0][0];
		   }
		   if(bottom_right[0][0] != -1)
			   answer = bottom_right[0][0];
		   int[][] dis = {{-1,0}, {1,0}, {0,-1}, {0,1}};
		   for(int i=0; i<m; i++) {
			   for(int j=0; j<n; j++) {
				   if(grid[i][j] != 1)
					   continue;
				   for(int[] d1 : dis) {
					   int x1 = i + d1[0];
					   int y1 = j + d1[1];
					   if(x1<0||x1>=m||y1<0||y1>=n)
						   continue;
					   for(int[] d2 : dis) {
						   int x2 = i + d2[0];
						   int y2 = j + d2[1];
						   if(x2<0||x2>=m||y2<0||y2>=n || (x1==x2&&y1==y2))
							   continue;
						   if(top_left[x1][y1] != -1 && bottom_right[x2][y2] != -1) {
							   answer = Math.min(answer, top_left[x1][y1] + bottom_right[x2][y2]+2);
						   }
						   if(top_left[x2][y2] != -1 && bottom_right[x1][y1] !=-1) {
							   answer = Math.min(answer, top_left[x2][y2] + bottom_right[x1][y1]+2);
						   }
					   }
				   }
			   }
		   }
		   
	       return answer == Integer.MAX_VALUE ? -1 : answer;
	   }
	   
	   private void getBestRoadBFS(int[][] grid, int[] start, int[][] dp) {
		   Queue<int[]> queue = new LinkedList<>();
		   queue.add(start);
		   int step = 0;
		   dp[start[0]][start[1]] = 0;
		   int[][] dis = {{-1,0}, {1,0}, {0,-1}, {0,1}};
		   while(!queue.isEmpty()) {
			   step++;
			   int size = queue.size();
			   while(size-- > 0) {
				   int[] pair = queue.poll();
				   for(int[] d : dis) {
					   int x = pair[0] + d[0];
					   int y = pair[1] + d[1];
					   if(x<0||x>=grid.length||y<0||y>=grid[0].length||grid[x][y]==1||dp[x][y]!=-1)
						   continue;
					   dp[x][y] = step;
					   queue.add(new int[] {x, y});
				   }
			   }
		   }
	   }
	   
	   
	   
	   public int shortestDistance(int N, int[][] barriers) {
		   int path = 0;
		   if(N == 1)
			   return 0;
		   boolean[][][] used = new boolean[N][N][N];
		   for(int[] pos : barriers) {
			   used[pos[0]][pos[1]][pos[2]] = true;
		   }
		   Queue<int[]> queue = new LinkedList<>();
		   queue.add(new int[] {0, 0, 0});
		   used[0][0][0] = true;
		   int[][] D = {{-1,0,0}, {1,0,0}, {0,-1,0}, {0,1,0}, {0,0,1}, {0,0,-1}};
		   while(!queue.isEmpty()) {
			   int size = queue.size();
			   path++;
			   while(size-- > 0) {
				   int[] pos = queue.poll();
				   for(int[] d : D) {
					   int x = pos[0] + d[0];
					   int y = pos[1] + d[1];
					   int z = pos[2] + d[2];
					   if(x<0||x>=N||y<0||y>=N||z<0||z>=N||used[x][y][z])
						   continue;
					   used[x][y][z] = true;
					   if(x == N-1 && y == N-1 && z == N-1)
						   return path;
					   queue.add(new int[] {x, y, z});
				   }
			   }
		   }
		   return -1;
	   }
	   
	   
	   public int hasPath2(int[][] maze, int[] start, int[] destination) {
	        if(maze[start[0]][start[1]] == 1)
				  return -1;
			  if(maze[destination[0]][destination[1]] == 1)
				  return -1;
		      Queue<int[]> queue = new LinkedList<>();
			  int m = maze.length, n = maze[0].length;
			  boolean[][] visited = new boolean[m][n];
			  visited[start[0]][start[1]] = true;
			  queue.add(new int[] {start[0], start[1], 0});
			  while(!queue.isEmpty()) {
				  int size = queue.size();
				  for(int i=0; i<size; i++) {
					  int[] pos = queue.poll();
					  int step = pos[2];
					  int x = pos[0], y = pos[1];
					  while(x > 0 && maze[x-1][y] == 0) {
						  x--;
						  step++;
					  }
					  if(!visited[x][y]) {
						  visited[x][y] = true;
						  if(x == destination[0] && y == destination[1])
							  return step;
						  queue.add(new int[] {x, y, step});
					  }
					  step = pos[2];
					  x = pos[0];
					  y = pos[1];
					  while(x < m-1 && maze[x+1][y] == 0) {
						  x++;
						  step++;
					  }
					  if(!visited[x][y]) {
						  visited[x][y] = true;
						  if(x == destination[0] && y == destination[1])
							  return step;
						  queue.add(new int[] {x, y, step});
					  }
					
					  step = pos[2];
					  x = pos[0];
					  y = pos[1];
					  while(y > 0 && maze[x][y-1] == 0) {
						  y--;
						  step++;
					  }
					  if(!visited[x][y]) {
						  visited[x][y] = true;
						  if(x == destination[0] && y == destination[1])
							  return step;
						  queue.add(new int[] {x, y, step});
					  }
					  
					  step = pos[2];
					  x = pos[0];
					  y = pos[1];
					  while(y < n-1 && maze[x][y+1] == 0) {
						  y++;
						  step++;
					  }
					  if(!visited[x][y]) {
						  visited[x][y] = true;
						  if(x == destination[0] && y == destination[1])
							  return step;
						  queue.add(new int[] {x, y, step});
					  }
					  
				  }
			  }
			  return -1;
	    }
	   
	   
	   public String findShortestWay(int[][] maze, int[] ball, int[] hole) {
		      Queue<int[]> queue = new LinkedList<>();
			  int m = maze.length, n = maze[0].length;
			  boolean[][] visited = new boolean[m][n];
			  visited[ball[0]][ball[1]] = true;
			  Map<String, int[]> paths = new HashMap<>();
			  paths.put(ball[0]+","+ball[1], null);
			  queue.add(new int[] {ball[0], ball[1], 'a'});
			  while(!queue.isEmpty()) {
				  int size = queue.size();
				  for(int i=0; i<size; i++) {
					  int[] pos = queue.poll();
					  int x = pos[0], y = pos[1];
					  while(x > 0 && maze[x-1][y] == 0) {
						  x--;
						  if(x == hole[0] && y == hole[1]) {
							  paths.put(x+","+y, new int[] {pos[0],pos[1],'u'});
							  return findShortestWayHelp(paths, x, y);
						  }
					  }
					  if(!visited[x][y]) {
						  visited[x][y] = true;
						  if(x == hole[0] && y == hole[1])
							  return findShortestWayHelp(paths, x, y);
						  paths.put(x+","+y, pos);
						  queue.add(new int[] {x, y, 'u'});
					  }
					  x = pos[0];
					  y = pos[1];
					  while(x < m-1 && maze[x+1][y] == 0) {
						  x++;
						  if(x == hole[0] && y == hole[1]) {
							  paths.put(x+","+y, new int[] {pos[0],pos[1],'d'});
							  return findShortestWayHelp(paths, x, y);
						  }
					  }
					  if(!visited[x][y]) {
						  visited[x][y] = true;
						  if(x == hole[0] && y == hole[1])
							  return findShortestWayHelp(paths, x, y);
						  paths.put(x+","+y, pos);
						  queue.add(new int[] {x, y, 'd'});
					  }
					
					  x = pos[0];
					  y = pos[1];
					  while(y > 0 && maze[x][y-1] == 0) {
						  y--;
						  if(x == hole[0] && y == hole[1]) {
							  paths.put(x+","+y, new int[] {pos[0],pos[1],'l'});
							  return findShortestWayHelp(paths, x, y);
						  }
					  }
					  if(!visited[x][y]) {
						  visited[x][y] = true;
						  if(x == hole[0] && y == hole[1])
							  return findShortestWayHelp(paths, x, y);
						  paths.put(x+","+y, pos);
						  queue.add(new int[] {x, y,'l'});
					  }
					  
					  x = pos[0];
					  y = pos[1];
					  while(y < n-1 && maze[x][y+1] == 0) {
						  y++;
						  if(x == hole[0] && y == hole[1]) {
							  paths.put(x+","+y, new int[] {pos[0],pos[1],'r'});
							  return findShortestWayHelp(paths, x, y);
						  }
					  }
					  if(!visited[x][y]) {
						  visited[x][y] = true;
						  if(x == hole[0] && y == hole[1])
							  return findShortestWayHelp(paths, x, y);
						  paths.put(x+","+y, pos);
						  queue.add(new int[] {x, y, 'r'});
					  }
					  
				  }
			  }		   
		   return "impossible";
	   }
	   
	   private String findShortestWayHelp(Map<String, int[]> paths, int x, int y) {
		   StringBuilder sb = new StringBuilder();
		   String key = x + "," + y;
		   int[] pos = paths.get(key);
		   while(pos != null) {
			   char c = (char) (pos[2] - 'a');
			   sb.append(c);
			   key = pos[0] + ","+ pos[1];
			   System.out.println(key);
			   pos = paths.get(key);
			   //System.out.println(sb.toString());
		   }
		   return sb.reverse().toString();
	   }
	   
	   public String parseTernary(String expression) {
	       Stack<Character> stack = new Stack<>();
	       stack.add(expression.charAt(expression.length()-1));
	       for(int i=expression.length()-2; i>=0; i--) {
	    	   char cur = expression.charAt(i);
	    	   if(cur == ':' || cur == '?')
	    		   continue;
	    	   char next = expression.charAt(i+1);
	    	   if(next == '?') {
	    		   char one = stack.pop();
	    		   char two = stack.pop();
	    		   if(cur == 'T') {
	    			   stack.add(one);
	    		   }
	    		   else {
	    			   stack.add(two);
	    		   }
	    	   }
	    	   else {
		    	   stack.add(cur);
	    	   }
	       }
	       String answer = stack.pop()+"";
		   return answer;
	   }
	   
	   
	   public List<List<Integer>> findLeaves(TreeNode root) {
	        List<List<Integer>> answer = new ArrayList<>();
	        if(root == null)
	        	return answer;
	        TreeNode negative = new TreeNode(-1);
	        Map<TreeNode, TreeNode> tree = new HashMap<>();
	        Map<TreeNode, Integer> indegree = new HashMap<>();
	        indegree.put(negative, 100);
	        findLeavesHelp(root, negative, tree, indegree);
	        Queue<TreeNode> queue = new LinkedList<>();
	        List<Integer> list = new ArrayList<>();
	        for(TreeNode node : indegree.keySet()) {
	        	if(indegree.get(node) == 0) {
	        		queue.add(node);
	        		list.add(node.val);
	        	}
	        }
	        answer.add(list);
	        while(!queue.isEmpty()) {
	        	int size = queue.size();
	        	list = new ArrayList<>();
	        	while(size-- > 0) {
	        		TreeNode node = queue.poll();
	        		TreeNode father = tree.get(node);
	        		int ind = indegree.get(father) - 1;
	        		indegree.put(father, ind);
	        		if(ind == 0) {
	        			queue.add(father);
	        			list.add(father.val);
	        		}
	        	}
	        	if(!list.isEmpty())
	        		answer.add(list);
	        }
	        
	        return answer;
	   }
	   
	   private void findLeavesHelp(TreeNode node, TreeNode father, Map<TreeNode, TreeNode> tree, Map<TreeNode, Integer> indegree) {
		   if(node == null)
			   return;
		   tree.put(node, father);
		   indegree.put(node, 0);
		   indegree.put(father, indegree.get(father)+1);
		   findLeavesHelp(node.left, node, tree, indegree);
		   findLeavesHelp(node.right, node, tree, indegree);
	   }
	   
	   
	   public boolean canBeGenerated(String[] generator, char startSymbol, String symbolString) {
	       Map<String, List<String>> generator_map = new HashMap<>();
	       for(String str : generator) {
	    	   String[] pair = str.split("->");
	    	   String from = pair[0].trim();
	    	   String to = pair[1].trim();
	    	   if(to.contains(from))
	    		   continue;
	    	   List<String> list = generator_map.get(from);
	    	   if(list == null) {
	    		   list = new ArrayList<>();
	    		   generator_map.put(from, list);
	    	   }
	    	   list.add(to);
	       }
		   boolean answer = canBeGeneratedDFS(startSymbol+"", "", "", generator_map, symbolString);
		   return answer;
	   }
	   
	   
	   private boolean canBeGeneratedDFS(String cur, String left, String right, Map<String, List<String>> generator_map, String goal) {
		   String word = left + cur + right;
		   if(word.length() > goal.length())
			   return false;
		   if(!canBeGeneratedHelp(word))
			   return word.equals(goal);
		   for(int i=0; i<word.length(); i++) {
			   char c = word.charAt(i);
			   if(c >= 'A' && c <= 'Z') {
				   String next_left = word.substring(0, i);
				   String next_right = word.substring(i+1);
				   List<String> list = generator_map.get(c+"");
				   if(list == null || list.isEmpty())
					   return false;
				   for(String next_cur : list) {
					   if(canBeGeneratedDFS(next_cur, next_left, next_right, generator_map, goal))
						   return true;
				   }
			   }
		   }
		   
		   return false;
	   }
	   
	   
	   private boolean canBeGeneratedHelp(String word) {
		   for(char c : word.toCharArray()) {
			   if(c >= 'A' && c <= 'Z')
				   return true;
		   }
		   return false;
	   }
	   
	   public List<List<Integer>> subsets(int[] nums) {
		   List<List<Integer>> subsets = new ArrayList<>();
		   Arrays.sort(nums);
		   int size = 1 << (nums.length);
		   for(int i=0; i<size; i++) {
			   List<Integer> list = new ArrayList<>();
			   for(int j=nums.length-1; j>=0; j--) {
				   if(((i >>> j) & 1) == 1) {
					   list.add(nums[nums.length-1 - j]);
				   }
			   }
			   subsets.add(list);
		   } 
		   return subsets;
	   }
	   
	   
	   public int smallestDifference(int[] A, int[] B) {
	        int differ = Integer.MAX_VALUE;
	        TreeSet<Integer> tree = new TreeSet<>();
	        for(int num : A)
	        	tree.add(num);
	        for(int num : B) {
	        	Integer small = tree.floor(num);
	        	if(small != null) {
	        		differ = Math.min(differ, num - small);
	        	}
	        	Integer big = tree.ceiling(num);
	        	if(big != null) {
	        		differ = Math.min(differ, big - num);
	        	}
	        }
	        return differ;
	   }
	   
	   public List<List<Integer>> kSumII(int[] A, int k, int targer) {
		   List<List<Integer>> answer = new ArrayList<>();
		   kSumIIDFS(A, k, A.length-1, targer, answer, new ArrayList<>());
	       return answer;
	   }
	   
	   private void kSumIIDFS(int[] A, int k, int index, int target, List<List<Integer>> answer, List<Integer> list) {
		   if(k == 0 && target != 0)
			   return;
		   if(k == 0 && target == 0) {
			   answer.add(new ArrayList<>(list));
			   return;
		   }
		   if(index < 0 || k < 0)
			   return;
		   list.add(A[index]);
		   kSumIIDFS(A, k-1, index-1, target - A[index], answer, list);
		   list.remove(list.size()-1);
		   kSumIIDFS(A, k, index-1, target, answer, list);
	   }
	   
	   
	   public List<List<Integer>> kSumII2(int[] A, int k, int targer) {
		   List<List<Integer>> subsets = new ArrayList<>();
		   int size = 1 << (A.length);
		   for(int i=0; i<size; i++) {
			   int bitCount = Integer.bitCount(i);
			   if(bitCount != k)
				   continue;
			   List<Integer> list = new ArrayList<>();
			   int sum = 0;
			   for(int j=A.length-1; j>=0; j--) {
				   if(((i >>> j) & 1) == 1) {
					   list.add(A[A.length-1 - j]);
					   sum += A[A.length-1 - j];
				   }
			   }
			   if(!list.isEmpty() && targer == sum) {
				   subsets.add(list);
			   }
		   } 
		   return subsets;
	   }
	   
	   
	   public String longestCommonPrefix(String[] strs) {
		   String commonPrefix = "";
		   if(strs == null || strs.length == 0)
			   return commonPrefix;
		   commonPrefix = strs[0];
		   for(int i=1; i<strs.length; i++) {
			   commonPrefix = longestCommonPrefixHelp(commonPrefix, strs[i]);
		   }
		   return commonPrefix;
	   }
	   
	   private String longestCommonPrefixHelp(String a, String b) {
		   StringBuilder sb = new StringBuilder();
		   int min = Math.min(a.length(), b.length());
		   for(int i=0; i<min; i++) {
			   char c1 = a.charAt(i);
			   char c2 = b.charAt(i);
			   if(c1 != c2)
				   return sb.toString();
			   sb.append(c1);
		   }
		   return sb.toString();
	   }
	   
	   public List<List<Integer>> combinationSum2(int[] num, int target) {
	       List<List<Integer>> subsets = new ArrayList<>();
	       Arrays.sort(num);
	       TreeMap<String, List<Integer>> used = new TreeMap<>();
		   int size = 1 << (num.length);
		   for(int i=0; i<size; i++) {
			   List<Integer> list = new ArrayList<>();
			   int sum = 0;
			   for(int j=num.length-1; j>=0; j--) {
				   if(((i >>> j) & 1) == 1) {
					   list.add(num[num.length-1 - j]);
					   sum += num[num.length-1 - j];
				   }
			   }
			   if(!list.isEmpty() && target == sum) {
				   used.put(list.toString(), list);
			   }
		   } 
		   for(List<Integer> list : used.values()) {
			   subsets.add(list);
		   }
		   return subsets;
	   }
	   
	   public List<List<Integer>> combinationSum22(int[] num, int target) {
		   List<List<Integer>> answer = new ArrayList<>();
		   Arrays.sort(num);
		   combinationSum2(num, 0, target, answer, new ArrayList<>(), new HashSet<>());
		   return answer;
	   }
	   
	   private void combinationSum2(int[] A, int index, int target, List<List<Integer>> answer, List<Integer> list, Set<String> used) {
		   if(target < 0)
			   return;
		   if(target == 0 && !list.isEmpty()) {
			   if(used.add(list.toString())) {
				   answer.add(new ArrayList<>(list));
			   }
			   return;
		   }
		   if(index >= A.length)
			   return;
		   list.add(A[index]);
		   combinationSum2(A, index+1, target - A[index], answer, list, used);
		   list.remove(list.size()-1);
		   combinationSum2(A, index+1, target, answer, list, used);
	   }
	   
	   public List<List<Integer>> combinationSum(int[] candidates, int target) {
		   List<List<Integer>> answer = new ArrayList<>();
		   Arrays.sort(candidates);
		   combinationSum(candidates, 0, target, answer, new ArrayList<>(), new HashSet<>());
		   return answer;
	   }
	   
	   private void combinationSum(int[] A, int index, int target, List<List<Integer>> answer, List<Integer> list, Set<String> used) {
		   if(target < 0)
			   return;
		   if(target == 0 && !list.isEmpty()) {
			   if(used.add(list.toString())) {
				   answer.add(new ArrayList<>(list));
			   }
			   return;
		   }
		   if(index >= A.length)
			   return;
		   list.add(A[index]);
		   combinationSum(A, index+1, target - A[index], answer, list, used);
		   list.remove(list.size()-1);
		   list.add(A[index]);
		   combinationSum(A, index, target - A[index], answer, list, used);
		   list.remove(list.size()-1);
		   combinationSum(A, index+1, target, answer, list, used);
	   }
	   
	   
	   public int smallestFactorization(int a) {
		   int A = a;
		   int[] array = {2, 3, 5, 7};
		   for(int p : array) {
			   while(A % p == 0)
				   A /= p;
		   }
		   if(A != 1)
			   return 0;
		   List<Integer> prime = new ArrayList<>();
		   List<Integer> prime_exp = new ArrayList<>();
		   for(int p : array) {
			   int temp = a;
			   int exp = 0;
			   while(temp % p == 0) {
				   temp /= p;
				   exp++;
			   }
			   prime.add(p);
			   prime_exp.add(exp);
		   }
		   prime.add(2, 4);
		   prime_exp.add(2, 0);
		   prime.add(4, 6);
		   prime_exp.add(4, 0);
		   prime.add(8);
		   prime_exp.add(0);
		   prime.add(9);
		   prime_exp.add(0);
		   long answer = 0;
		   while(prime_exp.get(0) >= 3) {
			   prime_exp.set(6, prime_exp.get(6)+1);
			   prime_exp.set(0, prime_exp.get(0) - 3);
		   }
		   while(prime_exp.get(1) >= 2) {
			   prime_exp.set(7, prime_exp.get(7) + 1);
			   prime_exp.set(1, prime_exp.get(1)-2);
		   }
		   while(prime_exp.get(0) >= 1 && prime_exp.get(1) >= 1) {
			   prime_exp.set(4, prime_exp.get(4)+1);
			   prime_exp.set(0, prime_exp.get(0)-1);
			   prime_exp.set(1, prime_exp.get(1)-1);
		   }
		   while(prime_exp.get(0) >= 2) {
			   prime_exp.set(2, prime_exp.get(2) + 1);
			   prime_exp.set(0, prime_exp.get(0)-2);
		   }
		   for(int i=0; i<prime.size(); i++) {
			   int p = prime.get(i);
			   for(int j=0; j<prime_exp.get(i); j++) {
				   answer = answer * 10 + p;
				   if(answer > Integer.MAX_VALUE)
					   return 0;
			   }
		   }
	       return (int) answer;
	   }
	   
	   
	   public int[] subarraySumClosest(int[] nums) {
	        int[] answer = {0, 0};
	        if(nums == null || nums.length == 0)
	        	return answer;
	        int cloest = Integer.MAX_VALUE;
	        int sum = 0;
	        int[][] preSum = new int[nums.length][2];
	        sum += nums[0];
	        preSum[0] = new int[] {sum, 0};
	        for(int i=1; i<nums.length; i++) {
	        	sum += nums[i];
	        	preSum[i][0] = sum;
	        	preSum[i][1] = i;
	        }
	        Arrays.sort(preSum, (a,b) -> (a[0] - b[0]));
	        for(int i=1; i<preSum.length; i++) {
	        	int[] a = preSum[i];
	        	int[] b = preSum[i-1];
	        	int diff = a[0] - b[0];
	        	if(diff < cloest) {
	        		cloest = diff;
	        		if(a[1] < b[1]) {
	        			answer[0] = a[1] + 1;
	        			answer[1] = b[1];
	        		}
	        		else {
	        			answer[0] = b[1] + 1;
	        			answer[1] = a[1];
	        		}
	        	}
	        }
	        return answer;
	    }
	   
	   
	   public List<Integer> subarraySum(int[] nums) {
		   List<Integer> list = new ArrayList<>();
	        if(nums == null || nums.length == 0)
	        	return list;
	        int sum = 0;
	        int[][] preSum = new int[nums.length][2];
	        sum += nums[0];
	        preSum[0] = new int[] {sum, 0};
	        for(int i=1; i<nums.length; i++) {
	        	sum += nums[i];
	        	preSum[i][0] = sum;
	        	preSum[i][1] = i;
	        }
	        if(sum == 0) {
	        	list.add(0);
	        	list.add(nums.length-1);
	        	return list;
	        }
	        Arrays.sort(preSum, (a,b) -> (a[0] - b[0]));
	        for(int i=1; i<preSum.length; i++) {
	        	int[] a = preSum[i];
	        	int[] b = preSum[i-1];
	        	if(a[0] == b[0]) {
	        		list.add(Math.min(a[1], b[1])+1);
	        		list.add(Math.max(a[1], b[1]));
	        		break;
	        	}
	        }
		   return list;
	   }
	   
	   
	   public int minimumSize(int[] nums, int s) {
		   int length = nums.length + 1;
		   TreeMap<Integer, Integer> tree = new TreeMap<>();
		   int sum = 0;
		   for(int i=0; i<nums.length; i++) {
			   sum += nums[i];
			   tree.put(sum, i);
			   Map.Entry<Integer, Integer> low = tree.floorEntry(sum - s);
			   if(low == null)
				   continue;
			   int pre_i = low.getValue();
			   length = Math.min(length, i - pre_i);
		   }
		   if(length == nums.length + 1 && sum >= s)
			   return nums.length;
		   if(length == nums.length + 1)
			   return -1;
		   return length;
	   }
	   
	   
	   
	   class RandomizedSet {
		   List<Integer> list;
		   Random random;
		   Set<Integer> set;
		   Map<Integer, Integer> map;
		   int count = 0;
		    public RandomizedSet() {
		    	list = new ArrayList<>();
		    	random = new Random();
		    	set = new HashSet<>();
		    	map = new HashMap<>();
		    }

		    /*
		     * @param val: a value to the set
		     * @return: true if the set did not already contain the specified element or false
		     */
		    public boolean insert(int val) {
		        if(set.contains(val))
		        	return false;
		    	set.add(val);
		    	if(count >= list.size()) {
		    		list.add(Integer.MAX_VALUE);
		    	}
		    	list.set(count, val);
		    	map.put(val, count);
		    	count++;
		    	return true;
		    }

		    /*
		     * @param val: a value from the set
		     * @return: true if the set contained the specified element or false
		     */
		    public boolean remove(int val) {
		        if(!set.contains(val))
		        	return false;
		    	int i = map.get(val);
		    	int last_val = list.get(count-1);
		    	list.set(i, last_val);
		    	map.put(last_val, i);
		    	map.remove(val);
		    	set.remove(val);
		    	count--;
		    	return true;
		    }

		    /*
		     * @return: Get a random element from the set
		     */
		    public int getRandom() {
		        int index = random.nextInt(list.size());
		        return list.get(index);
		    }
		}
	   
	  
	   public boolean isOneEditDistance(String s, String t) {
	       int len1 = s.length();
	       int len2 = t.length();
	       if(Math.abs(len1 - len2) > 1)
	    	   return false;
		   if(len1 == len2) {
			   int diff = 0;
			   for(int i=0; i<len1; i++) {
				   char c1 = s.charAt(i);
				   char c2 = t.charAt(i);
				   if(c1 != c2)
					   diff++;
			   }
			   if(diff != 1)
				   return false;
		   }
		   else {
			   int[] map = new int[128];
			   for(char c : s.toCharArray()) {
				   map[c]++;
			   }
			   for(char c : t.toCharArray()) {
				   map[c]--;
			   }
			   int sum = 0;
			   for(int num : map) {
				   sum += num;
			   }
			   if(Math.abs(sum) != 1)
				   return false;
		   }
		   return true;
	   }
	   
	   
	   public long getSum(int n, int[] nums) {
	        long sum = 0;
	        int or = 0;
	        int max = Integer.MIN_VALUE;
	        int min = Integer.MAX_VALUE;
	        boolean[] bits = new boolean[32];
	        for(int num : nums) {
	        	or |= num;
	        	max = Math.max(max, num);
	        	min = Math.min(min, num);
	        	for(int i=31; i>=0; i--) {
	        		if(((num >>> i) & 1 ) == 0) {
	        			bits[i] = true;
	        		}
	        	}
	        }
	        sum = sum + or + max + min;
	        int last = 0;
	        for(int i=0; i<32; i++) {
	        	if(bits[i] == false) {
	        		last |= 1 << i;
	        	}
	        }
	        sum = sum + last;
	        return sum;
	   } 
	   
	   
     public boolean wordBreak2(String s, Set<String> dict) {
    	int[] map = new int[26];
 		for(char c : s.toCharArray()) {
 			map[c - 'a'] = 1;
 		}
 		for(String word : dict) {
 			for(char c : word.toCharArray()) {
 				map[c - 'a'] = 0;
 			}
 		}
 		for(int i=0; i<26; i++) {
 			if(map[i] == 1) {
 				return false;
 			}
 		}
    	int n = s.length();
    	boolean[] dp = new boolean[n+1];
 		dp[0] = true;
 		for(int i=1; i<=n; i++) {
 			for(int j=i-1; j>=0; j--) {
 				String sub = s.substring(j, i);
				if(dict.contains(sub) && dp[j]) {
 					dp[i] = true;
 					break;
 				}
 			}
 		}
 		return dp[n];
     } 
	   
	 @SuppressWarnings("unused")
	private boolean wordBreak2(String s, Set<String> dict, Map<String, Boolean> dp) {
		 if(s.length() == 0)
			 return true;
		 if(dp.containsKey(s))
			 return dp.get(s);
		 boolean result = false;
		 for(int i=0; i<s.length(); i++) {
			 String sub = s.substring(0, i+1);
			 if(dict.contains(sub)) {
				 result |= wordBreak2(s.substring(sub.length()), dict, dp);
				 if(result)
					 break;
			 }
		 }
		 dp.put(s, result);
		 return result;
	 }
	   
	
	 public boolean isPalindrome(String s) {
		 int left = 0, right = s.length() - 1;
		 while(left <= right) {
			 char c1 = s.charAt(left);
			 char c2 = s.charAt(right);
			 if(!((c1 >= 'a' && c1 <= 'z') || (c1 >= 'A' && c1 <= 'Z') || (c1 >= '0' && c1 <= '9'))) {
				 left++;
				 continue;
			 }
			 if(!((c2 >= 'a' && c2 <= 'z') || (c2 >= 'A' && c2 <= 'Z') || (c2 >= '0' && c2 <= '9'))) {
				 right--;
				 continue;
			 }
			 if(c1 >= 'A' && c1 <= 'Z') {
				 c1 += 32;
			 }
			 if(c2 >= 'A' && c2 <= 'Z') {
				 c2 += 32;
			 }
			 if(c1 == c2) {
				 left++;
				 right--;
			 }
			 else {
				 return false;
			 }
		 }
		 return true;
	 }
	   
	 
	 public List<List<Integer>> verticalOrder(TreeNode root) {
		 List<List<Integer>> answer = new ArrayList<>();
		 if(root == null)
			 return answer;
		 Map<TreeNode, Integer> index_map = new HashMap<>();
		 Map<TreeNode, Integer> depth_map = new HashMap<>();
		 List<TreeNode> list = new ArrayList<>();
		 verticalOrderHelp(root, 0, 0, index_map, depth_map, list);
		 Collections.sort(list, new verticalOrderComparator(index_map, depth_map));
		 for(int i=0; i<list.size(); i++) {
			 TreeNode node = list.get(i);
			 if(i == 0) {
				 List<Integer> temp = new ArrayList<>();
				 temp.add(node.val);
				 answer.add(temp);
			 }
			 else {
				 TreeNode pre_node = list.get(i-1);
				 int index = index_map.get(node);
				 int pre_index = index_map.get(pre_node);
				 if(index == pre_index) {
					 List<Integer> pre_list = answer.get(answer.size()-1);
					 pre_list.add(node.val);
				 }
				 else {
					 List<Integer> temp = new ArrayList<>();
					 temp.add(node.val);
					 answer.add(temp);
				 }
			 }
			 
		 }
		 return answer;
	 }
	 
	 class verticalOrderComparator implements Comparator<TreeNode> {
		
		 Map<TreeNode, Integer> index_map;
		 Map<TreeNode, Integer> depth_map;
		 public verticalOrderComparator(Map<TreeNode, Integer> index_map, Map<TreeNode, Integer> depth_map) {
			 this.index_map = index_map;
			 this.depth_map = depth_map;
		 }

		@Override
		public int compare(TreeNode o1, TreeNode o2) {
			int index1 = index_map.get(o1);
			int index2 = index_map.get(o2);
			if(index1 == index2) {
				int depth1 = depth_map.get(o1);
				int depth2 = depth_map.get(o2);
				return depth1 - depth2;
			}
			return index1 - index2;
		}
		 
	 }
	 
	 private void verticalOrderHelp(TreeNode node, int index, int depth, Map<TreeNode, Integer> index_map, Map<TreeNode, Integer> depth_map, List<TreeNode> list) {
		 if(node == null)
			 return;
		 index_map.put(node, index);
		 depth_map.put(node, depth);
		 list.add(node);
		 verticalOrderHelp(node.left, index-1, depth+1, index_map, depth_map, list);
		 verticalOrderHelp(node.right, index+1, depth+1, index_map, depth_map, list);
	 }
	   
	 
	 public boolean firstWillWin(int n) {
		 if(n % 3 == 0)
			 return false;
		 return true;
	 }
	   
	 public boolean firstWillWin(int[] values) {
		 long sum = 0;
		 for(int num : values)
			 sum = sum + num;
		 long value1 = firstWillWin(values, 0, new Long[values.length]);
		 return value1 >= sum - value1;
	 }
	 
	 private long firstWillWin(int[] values, int index, Long[] dp) {
		 if(index >= values.length)
			 return 0;
		 if(index >= values.length-2) {
			 long temp = 0;
			 for(int i=index; i<values.length; i++) {
				 temp = temp + values[i];
			 }
			 return temp;
		 }
		 if(dp[index] != null)
			 return dp[index];
		 long value1 = values[index] + Math.min(firstWillWin(values, index+2, dp), firstWillWin(values, index+3, dp));
		 long value2 = values[index] + values[index+1] + Math.min(firstWillWin(values, index+3, dp), firstWillWin(values, index+4, dp));
		 dp[index] = Math.max(value1, value2);
		 return dp[index];
	 }
	 
	 public boolean firstWillWin2(int[] values) {
		 return firstWillWin(values, 0, 0, 0, new HashMap<>());
	 }
	 
	 private boolean firstWillWin(int[] values, int index, long A, long B, Map<String, Boolean> dp) {
		 if(index >= values.length)
			 return A >= B;
 		String key = index + "," + A +"," + B;
 		if(dp.containsKey(key))
 			return dp.get(key);
		boolean result = false; 
 		if(!firstWillWin(values, index+1, B, A+values[index], dp)) {
 			result = true;
 		}
 		if(index+1 < values.length && !result && !firstWillWin(values, index+2, B, A+values[index]+values[index+1], dp)) {
 			result = true;
 		}
		dp.put(key, result);
		return result;
	 }
	 
	 public int[] getModifiedArray(int length, int[][] updates) {
		 int[] answer = new int[length];
		 int[] differ = new int[length+1];
		 for(int[] update : updates) {
			 int i = update[0], j = update[1], inc = update[2];
			 differ[i] += inc;
			 differ[j+1] -= inc;
		 }
		 int sum = 0;
		 for(int i=0; i<length; i++) {
			 sum += differ[i];
			 answer[i] = sum;
		 }
		 return answer;
	 }
	 
	 
	 public int findDerangement(int n) {
		 if(n < 2)
			 return 0;
		 if(n == 2)
			 return 1;
		 long mod = 1000000007;
		 long[] dp = new long[n+1];
		 dp[2] = 1;
		 for(int i=3; i<=n; i++) {
			 dp[i] = ((dp[i-1] + dp[i-2]) * (i - 1)) % mod;
		 }
		 return (int) dp[n];
	 }
	 
	 
	 public int findDerangement2(int n) {
		 if(n < 2)
			 return 0;
		 if(n == 2)
			 return 1;
		 long[] first = new long[] {1, 0};
		 long[] power = {2, 1, 2, 0};
		 power = matrixPower(power, n-2);
		 long answer = (first[0] * power[0] + first[1] * power[2]) % 1000000007;
		 return (int) answer;
	 }
	 
	 private long[] matrixPower(long[] matrix, int power) {
		 long[] answer = new long[]{1, 0, 0, 1};
		 while(power > 0) {
			 if((power & 1) == 1) {
				 answer = matrixMul(answer, matrix);
				 power--;
			 }
			 matrix = matrixMul(matrix, matrix);
			 power /= 2;
		 }
		 return answer;
	 }
	 
	 private long[] matrixMul(long[] matrix1, long[] matrix2) {
		 long[] answer = new long[4];
		 answer[0] = (matrix1[0] * matrix2[0] + matrix1[1] * matrix2[2]) % 1000000007;
		 answer[1] = (matrix1[0] * matrix2[1] + matrix1[1] * matrix2[3]) % 1000000007;
		 answer[2] = (matrix1[2] * matrix2[0] + matrix1[3] * matrix2[2]) % 1000000007;
		 answer[3] = (matrix1[2] * matrix2[1] + matrix1[3] * matrix2[3]) % 1000000007;
		 return answer;
	 }
	 
	 
	 class ValidWordAbbr {
		Map<String, Set<String>> map;
		    
		    public ValidWordAbbr(String[] dictionary) {
		    	map = new HashMap<>();
		    	for(String word : dictionary) {
		    		String t = transform(word);
		    		Set<String> set = map.get(t);
		    		if(set == null) {
		    			set = new HashSet<>();
		    			map.put(t, set);
		    		}
		    		set.add(word);
		    	}
		    }
		    
		    private String transform(String word) {
		    	if(word.length() < 3)
		    		return word;
		    	String temp = word.charAt(0) + "" + (word.length()-2) + word.charAt(word.length()-1);
		    	return temp;
		    }

		   
		    public boolean isUnique(String word) {
		    	String t = transform(word);
		    	Set<String> set = map.get(t);
		    	if(set == null)
		    		return true;
		    	if(set.size() == 1 && set.contains(word))
		    		return true;
		    	return false;
		    }
		}
	   
	 public List<String> generateAbbreviations(String word) {
	        List<String> list = new ArrayList<>();
	        int size = 1 << word.length();
	        for(int i=0; i<size; i++) {
	        	int count = 0;
	        	StringBuilder sb = new StringBuilder();
	        	for(int j=0; j<word.length(); j++) {
	        		if(((i >>> word.length()-1-j) & 1) == 1) {
	        			count++;
	        		}
	        		else {
	        			if(count != 0) {
	        				sb.append(count);
	        				count = 0;
	        			}
	        			sb.append(word.charAt(j));
	        		}
	        	}
	        	if(count != 0)
	        		sb.append(count);
	        	list.add(sb.toString());
	        }
	        return list;
	 }
	 
	 
	 public List<String> letterCombinations(String digits) {
		 List<String> answer = new ArrayList<>();
		 if(digits == null || digits.length() == 0)
			 return answer;
		 String[][] array = {{},{"a", "b", "c"}, {"d", "e", "f"}, {"g", "h", "i"},
				 			 {"j", "k", "l"}, {"m", "n", "o"}, {"p", "q", "r", "s"},
				 			 {"t", "u", "v"}, {"w", "x", "y", "z"}};
		 letterCombinationsHelp(0, array, answer, "", digits);
		 return answer;
	 }
	 
	 private void letterCombinationsHelp(int index, String[][] array, List<String> answer, String str, String digits) {
		 if(index >= digits.length()) {
			 answer.add(str);
			 return;
		 }
		 int i = digits.charAt(index) - '1';
		 String[] group = array[i];
		 for(String word : group) {
			 letterCombinationsHelp(index+1, array, answer, str+word, digits);
		 }
	 }
	 
	 
	 public int lengthOfLongestSubstring(String s) {
		 int longest = 0;
		 Set<Character> used = new HashSet<>();
		 for(int i=0,j=0; i<s.length(); i++) {
			 while(j < s.length() && used.add(s.charAt(j))) {
				 j++;
			 }
			 longest = Math.max(longest, j - i);
			 used.remove(s.charAt(i));
		 }
		 return longest;
	 }
	 
	 
	 public int lengthOfLongestSubstringKDistinct(String s, int k) {
	      int longest = 0;
	      if(k == 0 || s == null || s.length() == 0)
	    	  return 0;
	      Map<Character, Integer> map = new HashMap<>();
	      for(int i=0,j=0; i<s.length(); i++) {
	    	  while(j < s.length() && (map.size() < k || (map.size() == k && map.containsKey(s.charAt(j))))) {
	    		  map.put(s.charAt(j), map.getOrDefault(s.charAt(j), 0)+1);
	    		  j++;
	    	  }
	    	  longest = Math.max(longest, j-i);
	    	  int temp = map.get(s.charAt(i));
	    	  if(temp == 1)
	    		  map.remove(s.charAt(i));
	    	  else
	    		  map.put(s.charAt(i), temp-1);
	      }
	      return longest;
	 }
	 
	 public int findMinArrowShots(int[][] points) {
		 if(points == null || points.length == 0)
			 return 0;
		 List<int[]> list = new ArrayList<>(points.length*2);
		 for(int[] pos : points) {
			 list.add(new int[] {pos[0], 1});
			 list.add(new int[] {pos[1], -1});
		 }
		 Collections.sort(list, new findMinArrowShotsComparator());
		 int shots = 0;
		 int sum = 0;
		 for(int[] pos : list) {
			 sum += pos[1];
			 if(sum == 0) {
				 shots++;
			 }
		 }
		 return shots;
	 }
	 
	 class findMinArrowShotsComparator implements Comparator<int[]> {
		@Override
		public int compare(int[] o1, int[] o2) {
			if(o1[0] == o2[0]) {
				return o2[1] - o1[1];
			}
			return o1[0] - o2[0];
		}
		 
	 }
	 
	 
	 public int eraseOverlapIntervals(List<Interval> intervals) {
		 int answer = 0;
		 if(intervals == null || intervals.size() < 2)
			 return 0;
		 Collections.sort(intervals, (a,b) -> a.end - b.end);
		 int pos = intervals.get(0).end;
		 for(int i=1; i<intervals.size(); i++) {
			 Interval interval = intervals.get(i);
			 if(interval.start >= pos) {
				 pos = interval.end;
			 }
			 else {
				 answer++;
			 }
		 }
		 return answer;
	 }
	 
	 
	 public int partitionArray2(int[] nums, int k) {
		 int index = 0;
		 for(int i=0; i<nums.length; i++) {
			 if(nums[i] < k) {
				 int temp = nums[index];
				 nums[index] = nums[i];
				 nums[i] = temp;
				 index++;
			 }
		 }
		 return index;
	 }
	 
	 public int minElements(int[] arr) {
		 int min = 0;
		 Arrays.sort(arr);
		 long sum = 0;
		 for(int i=0; i<arr.length; i++) {
			 sum = sum + arr[i];
		 }
		 long right = 0;
		 for(int i=arr.length-1; i>=0; i--) {
			 right = right + arr[i];
			 min++;
			 if(right > sum - right)
				 return min;
		 }
		 
		 return min;
	 }
	 
	 
	 
	 
	 
	   
	  
	  public static void main(String[] args) {
		  
	  }

}
