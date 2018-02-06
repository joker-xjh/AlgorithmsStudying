package lintcode;

import java.util.ArrayList;
import java.util.Arrays;
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
	  

	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
		  
	  
	
	

}
