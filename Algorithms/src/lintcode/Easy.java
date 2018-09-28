package lintcode;

import java.util.ArrayList;



import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;



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
	
	
	public void recoverRotatedSortedArray(List<Integer> nums) {
        if(nums == null || nums.size() < 2)
        	return;
        int index = -1;
        for(int i=0; i<nums.size()-1; i++) {
        	if(nums.get(i) > nums.get(i+1)) {
        		index = i;
        		break;
        	}
        }
        if(index == -1)
        	return;
        reverseArray(nums, 0, index);
        System.out.println(nums);
        reverseArray(nums, index+1, nums.size()-1);
        System.out.println(nums);
        reverseArray(nums, 0, nums.size()-1);
    }
	
	
	private void reverseArray(List<Integer> array, int left, int right) {
		int mid = left + (right - left) / 2;
		for(int i=left; i<mid; i++) {
			int c = array.get(i);
			array.set(i, array.get(right - 1 - i + left));
			array.set(right - 1 - i + left, c);
		}
	}
	
	
	
	public int maxSubArray(int[] nums) {
        int max = Integer.MIN_VALUE;
        int sum = 0;
        for(int i=0; i<nums.length; i++) {
        	sum += nums[i];
        	max = Math.max(max, sum);
        	if(sum < 0)
        		sum = 0;
        }
        
        return max;
    }
	
	
	public int maxTwoSubArrays(List<Integer> nums) {
        int max = Integer.MIN_VALUE;
        int n = nums.size();
        int sum = 0;
        int[] left = new int[n];
        int[] right = new int[n];
        for(int i=0; i<n; i++) {
        	sum += nums.get(i);
        	max = Math.max(max, sum);
        	if(sum < 0)
        		sum = 0;
        	left[i] = max;
        }
        sum = 0;
        max = Integer.MIN_VALUE;
        for(int i=n-1; i>=0; i--) {
        	sum += nums.get(i);
        	max = Math.max(max, sum);
        	right[i] = max;
        	if(sum < 0)
        		sum = 0;
        }
        max = Integer.MIN_VALUE;
        for(int i=0; i<n-1; i++) {
        	max = Math.max(max, left[i] + right[i+1]);
        }
        
        return max;
    }
	
	
	public int minSubArray(List<Integer> nums) {
        int min = Integer.MAX_VALUE;
        int sum = 0;
        for(int num : nums) {
        	sum += num;
        	min = Math.min(min, sum);
        	if(sum > 0)
        		sum = 0;
        }
        return min;
    }
	
	
	public int majorityNumber(List<Integer> nums) {
        int majory = nums.get(0);
        int counter = 1;
        for(int i=1; i<nums.size(); i++) {
        	int num = nums.get(i);
        	if(num == majory) {
        		counter++;
        	}
        	else {
        		counter--;
        		if(counter == 0) {
        			majory = num;
        			counter=1;
        		}
        	}
        }
        
        return majory;
    }
	
	
	public List<Long> productExcludeItself(List<Integer> nums) {
        List<Long> B = new ArrayList<>(nums.size());
        int zero = 0;
        int index = -1;
        long sum = 1;
        for(int i=0; i<nums.size(); i++) {
        	int num = nums.get(i);
        	if(num == 0) {
        		zero++;
        		index = i;
        		if(zero>1)
        			break;
        	}
        }
        if(zero > 1) {
        	for(int i=0; i<nums.size(); i++)
        		B.add(0L);
        	return B;
        }
        if(zero == 1) {
        	for(int i=0; i<nums.size(); i++) {
        		if(i == index)
        			continue;
        		sum *= nums.get(i);
        	}
        	for(int i=0; i<nums.size(); i++) {
        		if(i == index) {
        			B.add(sum);
        		}
        		else {
        			B.add(0L);
        		}
        	}
        	return B;
        }
        for(int num : nums)
        	sum *= num;
        for(int i=0; i<nums.size(); i++) {
        	B.add(sum / nums.get(i));
        }
        return B;
    }
	
	
	public int climbStairs(int n) {
		if(n == 1)
			return 1;
		if(n == 2)
			return 2;
        int[] dp = new int[n+1];
		dp[1] = 1;
		dp[2] = 2;
		for(int i=3; i<=n; i++)
			dp[i] += dp[i-1] + dp[i-2];
		return dp[n];
    }
	
	
	public int numWays(int n, int k) {
		if(n == 0)
			return 0;
		if(n == 1)
			return k;
		if(n == 2)
			return k * k;
        int[] dp = new int[n+1];
        dp[1] = k;
        dp[2] = k * k;
        for(int i=3; i<=n; i++) {
        	dp[i] = dp[i-2] * (k-1) + dp[i-1] * (k - 1);
        }
        return dp[n];
    }
	
	public int reverseInteger(int number) {
        int reverse = 0;
        while(number > 0) {
        	int mod = number % 10;
        	reverse = reverse * 10 + mod;
        	number /= 10;
        }
        
        return reverse;
    }
	
	public String reverseWords(String s) {
        String answer = "";
        Scanner scanner = new Scanner(s);
        while(scanner.hasNext()) {
        	String word = scanner.next();
        	answer = word+" "+ answer;
        }
        scanner.close();
        return answer.trim();
    }
	
	public boolean compareStrings(String A, String B) {
        int[] counter = new int[26];
		if(A.length() < B.length())
			return false;
		for(char c : A.toCharArray())
			counter[c-'A']++;
		for(char c : B.toCharArray()) {
			counter[c-'A']--;
			if(counter[c-'A'] < 0)
				return false;
		}
		return true;
    }
	
	public int[] twoSum(int[] numbers, int target) {
        int[] index = new int[2];
		Map<Integer, Integer> map = new HashMap<>();
		for(int i=0; i<numbers.length; i++) {
			int num = numbers[i];
			int other = target - num;
			if(map.containsKey(other)) {
				index[1] = i;
				index[0] = map.get(other);
				return index;
			}
			map.put(num, i);
		}
        return index;
    }
	
	public int searchInsert(int[] A, int target) {
        int left = 0, right = A.length-1;
        while(left <= right) {
        	int mid = (left + right) >>> 1;
			if(A[mid] == target)
				return mid;
			else if(A[mid] < target)
				left = mid + 1;
			else
				right = mid - 1;
        }
        
        return left;
    }
	
	public int[] searchRange(int[] A, int target) {
        int[] range = new int[2];
        int index = searchRangeHelp1(A, target);
        if(index == A.length || A[index] != target) {
        	range[0] = -1; range[1] = -1;
        	return range;
        }
        range[0] = index;
        index = searchRangeHelp2(A, target);
        range[1] = index -1 ;
        System.out.println(Arrays.toString(range));
        return range;
    }
	
	public int searchRangeHelp2(int[] A, int target) {
		int left = 0, right = A.length - 1;
		while(left <= right) {
			int mid = (left + right) >>> 1;
			if(A[mid] > target)
				right = mid - 1;
			else if(A[mid] < target)
				left = mid + 1;
			else
				left++;
				
		}
		
		return left;
	}
	
	public int searchRangeHelp1(int[] A, int target) {
		int left = 0, right = A.length - 1;
		while(left < right) {
			int mid = (left + right) >>> 1;
        	//System.out.println(left+"," + right);
			if(A[mid] < target)
				left = mid+1;
			else
				right = mid;
		}
		
		return left;
	}
	
	 class Point {
		      int x;
		      int y;
		      Point() { x = 0; y = 0; }
		      Point(int a, int b) { x = a; y = b; }
		  }
	
	public String radarDetection(Point[] coordinates, int[] radius) {
        int n = coordinates.length;
        for(int i=0; i<n; i++) {
        	Point point = coordinates[i];
        	int r = radius[i];
        	if(point.y >= 0 && point.y <= 1)
        		return "YES";
        	if(point.y < 0 && point.y+r > 0)
        		return "YES";
        	if(point.y > 1 && point.y - r < 1)
        		return "YES";
        }
		return "NO";
    }
	
	
	public String[] dataSegmentation(String str) {
		List<String> list = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<str.length(); i++) {
			char c = str.charAt(i);
			if(c == ' ') {
				if(sb.length() > 0) {
					list.add(sb.toString());
					sb = new StringBuilder();
				}
				continue;
			}
			else if(c >= 'a' && c <= 'z') {
				sb.append(c);
			}
			else {
				if(sb.length() > 0) {
					list.add(sb.toString());
					list.add(""+c);
					sb = new StringBuilder();
				}
				else {
					list.add(""+c);
				}
			}
		}
		if(sb.length() > 0)
			list.add(sb.toString());
		String[] answer = new String[list.size()];
		list.toArray(answer);
		return answer;
    }
	
	
	public String isBuild(int x) {
        int mod = x % 7;
		int div = x / 7;
		if(mod == 1 && div >= 2)
			return "YES";
		else if(mod == 2 && div >= 1)
			return "YES";
		else if(mod == 3 || mod == 6)
			return "YES";
		else if(mod ==4 && div >= 2)
			return "YES";
		else if(mod == 5 && div >= 1)
			return "YES";
		return "NO";
    }
	
	public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        for(int i=0; i<n; i++)
        	dp[0][i] = 1;
        for(int i=0; i<m; i++)
        	dp[i][0] = 1;
        for(int i=1; i<m; i++) {
        	for(int j=1; j<n; j++) {
        		dp[i][j] += dp[i-1][j] + dp[i][j-1];
        	}
        }
        
        return dp[m-1][n-1];
    }
	
	
	public String[] findRestaurant(String[] list1, String[] list2) {
        Map<String, Integer> map1 = new HashMap<>();
        for(int i=0; i<list1.length; i++) {
        	map1.put(list1[i], i);
        }
        int min = Integer.MAX_VALUE;
        Map<Integer, List<String>> restaurant = new HashMap<>();
        for(int i=0; i<list2.length; i++) {
        	int index1 = map1.getOrDefault(list2[i], -1);
        	if(index1 == -1 || index1 + i > min)
        		continue;
        	List<String> list = restaurant.get(index1 + i);
        	if(list == null) {
        		list = new ArrayList<>();
        		restaurant.put(index1+i, list);
        	}
        	list.add(list2[i]);
        	min = Math.min(min, index1 + i);
        }
        String[] answer = new String[restaurant.get(min).size()];
        restaurant.get(min).toArray(answer);
		return answer;
    }
	
	
	public int reachNumber(int target) {
		if(target < 0)
			target = -target;
        long sum = 0;
        int count = 0;
        int n = 1;
        while(true) {
        	sum += n;
        	count++;
        	if((sum >= target) && ((sum - target) % 2 == 0))
        		return count;
        	n++;
        }
        
    }
	
	public boolean canAttendMeetings(List<Interval> intervals) {
		if(intervals == null || intervals.isEmpty())
			return true;
        Collections.sort(intervals, (a,b) -> a.start - b.start);
		for(int i=1; i<intervals.size();i++) {
			Interval cur = intervals.get(i);
			Interval pre = intervals.get(i-1);
			if(pre.end > cur.start)
				return false;
		}
		return true;
    }
	
	public boolean canAttendMeetings2(List<Interval> intervals) {
		if(intervals == null || intervals.isEmpty())
			return true;
		List<int[]> list = new ArrayList<>(intervals.size() * 2);
		for(Interval interval : intervals) {
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
		int temp = 0;
		for(int[] array : list) {
			temp += array[1];
			if(temp > 1)
				return false;
		}
		
		return true;
	}
	
	public List<Interval> merge(List<Interval> intervals) {
		List<Interval> list = new ArrayList<>();
		if(intervals == null || intervals.isEmpty())
			return list;
		List<int[]> list_pos = new ArrayList<>(intervals.size() * 2);
		for(Interval interval : intervals) {
			list_pos.add(new int[] {interval.start, 1});
			list_pos.add(new int[] {interval.end, -1});
		}
		Collections.sort(list_pos, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				if(o1[0] == o2[0])
					return o2[1] - o1[1];
				return o1[0] - o2[0];
			}
		});
		Interval temp = null;
		int count = 0;
		for(int[] pos : list_pos) {
			if(count == 0 && pos[1] == 1) {
				temp = new Interval(pos[0], -1);
			}
			count += pos[1];
			if(count == 0 && pos[1] == -1) {
				temp.end = pos[0];
				list.add(temp);
			}
		}
		return list;
	}
	
	
	
	  public class TreeNode {
		      public int val;
		      public TreeNode left, right;
		      public TreeNode(int val) {
		          this.val = val;
		          this.left = this.right = null;
		      }
	  }
	
	
	
	
	public boolean isSymmetric(TreeNode root) {
        if(root == null)
        	return true;
		LinkedList<TreeNode> queue = new LinkedList<>();
		queue.add(root);
		while(!queue.isEmpty()) {
			int size = queue.size();
			LinkedList<TreeNode> next_queue = new LinkedList<>();
			while(size-- > 0) {
				TreeNode node = queue.poll();
				if(node == null)
					continue;
				next_queue.add(node.left);
				next_queue.add(node.right);
			}
			int left = 0, right = next_queue.size()-1;
			while(left < right) {
				TreeNode L = next_queue.get(left);
				TreeNode R = next_queue.get(right);
				if(L != null && R != null) {
					if(L.val != R.val)
						return false;
				}
				if((L == null && R != null) || (L != null && R == null))
					return false;
				left++;
				right--;
			}
			queue = next_queue;
		}
		
		return true;
    }
	
	public List<String> longestWords(String[] dictionary) {
        List<String> words = new ArrayList<>();
        int max_length = 0;
        for(String word : dictionary) {
        	if(word.length() > max_length) {
        		max_length = word.length();
        		words.clear();
        	}
        	if(word.length() < max_length)
        		continue;
        	words.add(word);
        }
        return words;
    }
	
	
	public void derangement(int n) {
		List<Integer> list = new ArrayList<>();
		boolean[] used = new boolean[n+1];
		derangementDFS(list, used, n, 1);
	}
	
	private void derangementDFS(List<Integer> list, boolean[] used, int n, int index) {
		if(list.size() == n) {
			System.out.println(list);
			return;
		}
		for(int i=1; i<=n; i++) {
			if(index ==i || used[i])
				continue;
			list.add(i);
			used[i] = true;
			derangementDFS(list, used, n, index+1);
			used[i] = false;
			list.remove(list.size()-1);
		}
	}
	
	public List<List<String>> splitString(String s) {
        List<List<String>> answer = new ArrayList<>();
        splitStringDFS(answer, new ArrayList<>(), s, 0);
        return answer;
    }
	
	private void splitStringDFS(List<List<String>> answer, List<String> list, String S, int index) {
		if(index == S.length()) {
			answer.add(new ArrayList<>(list));
			return;
		}
		String temp = S.charAt(index) + "";
		list.add(temp);
		splitStringDFS(answer, list, S, index + 1);
		list.remove(list.size()-1);
		if(index + 1 < S.length()) {
			temp += S.charAt(index+1);
			list.add(temp);
			splitStringDFS(answer, list, S, index + 2);
			list.remove(list.size()-1);
		}
	}
	
	public boolean wordPattern(String pattern, String teststr) {
		String s1 = buildPattern(pattern, null);
		String s2 = buildPattern(null, teststr.split(" "));
		if(s1.equals(s2))
			return true;
		return false;
    }
	
	private String buildPattern(String word, String[] array) {
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
			for(String s : array) {
				if(!map.containsKey(s)) {
					map.put(s, map.size());
				}
				sb.append(map.get(s));
			}
		}
		return sb.toString();
	}
	
	
	public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
		int oldColor = image[sr][sc];
		image[sr][sc] = newColor;
		floodFillDFS(image, sr, sc, newColor, oldColor);
		return image;
    }
	
	private void floodFillDFS(int[][] image, int i, int j, int newColor, int oldColor) {
		int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}};
		for(int[] d : dirs) {
			int x = d[0] + i;
			int y = d[1] + j;
			if(x<0||x>=image.length||y<0||y>=image[0].length||image[x][y]!=oldColor)
				continue;
			image[x][y] = newColor;
			floodFillDFS(image, x, y, newColor, oldColor);
		}
	}
	
	
	public boolean canPermutePalindrome(String s) {
		int[] map = new int[128];
		for(char c : s.toCharArray()) {
			map[c]++;
		}
		int odd = 0;
		for(int num : map) {
			if((num & 1) == 1) {
				odd++;
			}
		}
		if(odd > 1)
			return false;
		return true;
    }
	
	
	public class TwoSum {
		TreeSet<Integer> tree = new TreeSet<>();
		Map<Integer, Integer> map = new HashMap<>();
		{
			tree.add(Integer.MIN_VALUE);
			tree.add(Integer.MAX_VALUE);
		}
	    
	    public void add(int number) {
	    	tree.add(number);
	    	map.put(number, map.getOrDefault(number, 0) + 1);
	    }

	    public boolean find(int value) {
	    	if(value % 2 == 0) {
	    		int half = value / 2;
	    		if(map.getOrDefault(half, 0) > 1)
	    			return true;
	    	}
	    	int left = tree.first();
	    	int right = tree.last();
	    	while(left < right) {
	    		long temp = (long)left + right;
	    		if(temp < value) {
	    			left = tree.higher(left);
	    		}
	    		else if(temp > value) {
	    			right = tree.lower(right);
	    		}
	    		else {
	    			return true;
	    		}
	    	}
	    	return false;
	    }
	}
	
	
	public void moveZeroes(int[] nums) {
		int index = 0;
		for(int i=0; i<nums.length; i++) {
			if(nums[i] != 0) {
				nums[index++] = nums[i];
			}
		}
		for(int i=index; i<nums.length; i++) {
			nums[i] = 0;
		}
    }
	
	public int removeElement(int[] A, int elem) {
        int index = 0;
        for(int i=0; i<A.length; i++) {
        	if(A[i] != elem) {
        		A[index++] = A[i];
        	}
        }
        return index;
    }
	
	public int removeDuplicates(int[] nums) {
        int index = 0;
        for(int i=0; i<nums.length; i++) {
        	if(i == nums.length-1) {
        		nums[index++] = nums[i];
        	}
        	else {
        		if(nums[i] != nums[i+1]) {
            		nums[index++] = nums[i];
            	}
        	}
        }
        return index;
    }
	
	public int removeDuplicates2(int[] nums) {
        if(nums.length < 3)
        	return nums.length;
        int index = 0;
        for(int i=0; i+2<nums.length; i++) {
        	if(nums[i] == nums[i+1] && nums[i+1] == nums[i+2])
        		continue;
        	nums[index++] = nums[i];
        }
        nums[index++] = nums[nums.length-2];
        nums[index++] = nums[nums.length-1];
        return index;
    }
	
	
	
	public static void main(String[] args) {
		
	}
	
	
	

}
