package leetcode;

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
import java.util.TreeMap;
import java.util.TreeSet;

public class Array {
	
	  public class ListNode {
		      int val;
		      ListNode next;
		      ListNode(int x) { val = x; }
		  }
	
	
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
    
    
   
    
    static int[][] dir = {{0,1}, {0, -1}, {1, 0}, {-1, 0}};
    public int cutOffTree(List<List<Integer>> forest) {
    	if(forest == null || forest.size() == 0)
    		return 0;
        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        int X = forest.size();
        int Y = forest.get(0).size();
        int[][] map = new int[X][Y];
        for(int i=0; i<forest.size(); i++) {
        	List<Integer> list = forest.get(i);
        	for(int j=0; j<list.size(); j++) {
        		int num = list.get(j);
        		map[i][j] = num;
        		if(num > 1) {
        			queue.add(new int[] {i,j,num});
        		}
        	}
        }
        
        int step = 0;
        int[] begin = {0,0};
        while(queue.size() > 0) {
        	int[] array = queue.poll();
        	int temp = bfs(map, begin, array,X,Y);
        	if(temp == -1)
        		return -1;
        	step += temp;
        	begin[0] = array[0];
        	begin[1] =array[1];
        }
        
    	return step;
    }
    
   
    
    
    private int bfs(int[][] map, int[] start, int[] tree,int m, int n) {
    	 int step = 0;
         boolean[][] visited = new boolean[m][n];
         Queue<int[]> queue = new LinkedList<>();
         queue.add(start);
         visited[start[0]][start[1]] = true;

         while (!queue.isEmpty()) {
             int size = queue.size();
             for (int i = 0; i < size; i++) {
                 int[] curr = queue.poll();
                 if (curr[0] == tree[0] && curr[1] == tree[1]) return step;

                 for (int[] d : dir) {
                     int nr = curr[0] + d[0];
                     int nc = curr[1] + d[1];
                     if (nr < 0 || nr >= m || nc < 0 || nc >= n 
                         || map[nr][nc] == 0 || visited[nr][nc]) continue;
                     queue.add(new int[] {nr, nc});
                     visited[nr][nc] = true;
                 }
             }
             step++;
         }

         return -1;
    }
    
    
    public boolean judgePoint24(int[] nums) {
        List<Double> list = new ArrayList<>();
        for(int num : nums)
        	list.add((double)num);
    	return judgePoint24(list);
    }
    
    private boolean judgePoint24(List<Double> num) {
    	if(num.size() ==1) {
    		return Math.abs(num.get(0) - 24) < 1e-6;
    	}
    	for(int i=0; i<num.size(); i++) {
    		for(int j=0; j<num.size(); j++) {
    			if(i==j)
    				continue;
    			List<Double> num2 = new ArrayList<>();
    			for(int k=0; k<num.size(); k++) {
    				if(k != i && k!= j)
    					num2.add(num.get(k));
    			}
    			for(int k=0; k<4; k++) {
    				if(k==0) {
    					num2.add(num.get(i) + num.get(j));
    				}
    				else if(k==1) {
    					num2.add(num.get(i) * num.get(j));
    				}
    				else if(k == 2) {
    					num2.add(num.get(i) - num.get(j));
    				}
    				else {
    					if(num.get(j)!=0) {
    						num2.add(num.get(i) / num.get(j));
    					}
    					else {
    						continue;
    					}
    				}
    				if(judgePoint24(num2))
    					return true;
    				num2.remove(num2.size()-1);
    			}
    			
    		}
    	}
    	
    	return false;
    }
    
    
    public int calPoints(String[] ops) {
        int sum = 0;
        LinkedList<Integer> list = new LinkedList<>();
        for(String op: ops) {
        	if(op.equals("C")) {
        		sum -= list.removeLast();
        	}
        	else if(op.equals("D")) {
        		list.add(list.peekLast() * 2);
        		sum += list.peekLast();
        	}
        	else if(op.equals("+")) {
        		list.add(list.peekLast() + list.get(list.size() -2 ));
        		sum += list.peekLast();
        	}
        	else {
        		list.add(Integer.parseInt(op));
        		sum += list.peekLast();
        	}
        }
        return sum;
    }
    
    
    class Employee {
        // It's the unique id of each node;
        // unique id of this employee
        public int id;
        // the importance value of this employee
        public int importance;
        // the id of direct subordinates
        public List<Integer> subordinates;
    };
    
    public int getImportance(List<Employee> employees, int id) {
        int important = 0;
        if(employees == null || employees.size() == 0)
        	return important;
        Map<Integer, Employee> map = new HashMap<>();
        for(Employee employee: employees) {
        	int index = employee.id;
        	map.put(index, employee);
        }
        Set<Integer> set = new HashSet<>();
        Employee root = map.get(id);
        if(root == null)
        	return important;
        set.add(id);
        Queue<Employee> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()) {
        	int size = queue.size();
        	for(int i=0; i<size; i++) {
        		Employee employee = queue.poll();
        		important += employee.importance;
        		List<Integer> next = employee.subordinates;
        		for(Integer integer : next) {
        			if(!set.contains(integer)) {
        				set.add(integer);
        				Employee employee2 = map.get(integer);
        				if(employee2 != null) {
        					queue.add(map.get(integer));
        				}
        				
        			}
        		}
        	}
        }
        
        return important;
    }
    
    
    public int maxSumSubmatrix(int[][] matrix, int k) {
        if(matrix==null || matrix.length == 0)
        	return 0;
        int res = Integer.MIN_VALUE;
        int row = matrix.length;
        int col = matrix[0].length;
        int m = Math.min(row, col);
        int n = Math.max(col, row);
        boolean colIsBig = col > row;
        for(int i=0; i<m; i++) {
        	int[] array = new int[n];
        	for(int j=i; j>=0; j--) {
        		TreeSet<Integer> set = new TreeSet<>();
        		int val = 0;
        		set.add(0);
        		for(int x=0; x<n; x++) {
        			array[x] += colIsBig ? matrix[j][x] : matrix[x][j];
        			val += array[x];
        			Integer sub = set.ceiling(val - k);
        			if(sub != null) {
        				res = Math.max(res, val - sub);
        			}
        			set.add(val);
        		}
        	}
        }
        
        return res;
    }
    
    
    public int maxAreaOfIsland(int[][] grid) {
        if(grid == null || grid.length == 0)
        	return 0;
    	int X = grid.length;
    	int Y = grid[0].length;
    	boolean[][] used = new boolean[X][Y];
    	int area = 0;
    	for(int i=0; i<X; i++) {
    		for(int j=0; j<Y; j++) {
    			int num = grid[i][j];
    			if(num == 1) {
    				area = Math.max(area, maxAreaOfIsland(grid, used, i, j,0));
    			}
    		}
    	}
    	return area;
    }
    
    private int maxAreaOfIsland(int[][] grid, boolean[][] used, int x, int y, int count) {
    	if(x<0 || x>=grid.length || y<0 || y>=grid[0].length || used[x][y] || grid[x][y] == 0)
    		return count;
    	count++;
    	used[x][y] = true;
    	count = Math.max(count, maxAreaOfIsland(grid, used, x-1,y,count));
    	count = Math.max(count, maxAreaOfIsland(grid, used, x+1,y,count));
    	count = Math.max(count, maxAreaOfIsland(grid, used, x,y+1,count));
    	count = Math.max(count, maxAreaOfIsland(grid, used, x,y-1,count));
    	return count;
    }
    
    
    public int findShortestSubArray(int[] nums) {
        if(nums == null || nums.length == 0)
        	return 0;
        int shortest = Integer.MAX_VALUE;
        Map<Integer, Integer> first = new HashMap<>();
        Map<Integer, Integer> last = new HashMap<>();
        Map<Integer, Integer> fre = new HashMap<>();
        int count = 0;
        for(int i=0; i<nums.length; i++) {
        	int num = nums[i];
        	if(!first.containsKey(num))
        		first.put(num, i);
        	last.put(num, i);
        	fre.put(num, fre.getOrDefault(num, 0)+1);
        	int temp = fre.get(num);
        	if(temp > count)
        		count = temp;
        }
        if(count == 1)
        	return 1;
        for(Integer integer : fre.keySet()) {
        	if(fre.get(integer) == count) {
        		shortest = Math.min(shortest, last.get(integer) - first.get(integer));
        	}
        }
        
        return shortest;
    }
    
    
    
    public boolean canPartitionKSubsets(int[] nums, int k) {
        if(nums == null || nums.length == 0)
        	return false;
        int sum = 0;
        for(int i=0; i<nums.length; i++) {
        	sum += nums[i];
        }
        if(sum % k != 0)
        	return false;
        int target = sum / k;
        boolean[] used = new boolean[nums.length];
    	return canPartitionKSubsets(nums, used, 0, target, k, 0);
    }
    
    private boolean canPartitionKSubsets(int[] nums,boolean[] used, int count, int target, int k, int index) {
    	if(k == 1)
    		return true;
    	if(count > target)
    		return false;
    	if(count == target)
    		return canPartitionKSubsets(nums, used, 0, target, k-1, 0);
    	for(int i=index; i<nums.length; i++) {
    		if(used[i])
    			continue;
    		int num = nums[i];
    		used[i] = true;
    		if(canPartitionKSubsets(nums, used, count+num, target, k, i+1))
    			return true;
    		used[i] = false;
    	}
    	return false;
    }
    
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        int count = 0;
        for(int i=0; i<nums.length; i++) {
        	int product = 1;
        	for(int j=i; j<nums.length; j++) {
        		product *= nums[j];
        		if(product < k)
        			count++;
        		else
        			break;
        	}
        }
        return count;
    }
    
    public int numSubarrayProductLessThanK2(int[] nums, int k) {
    	int count = 0;
    	int p =0;
    	int product = 1;
    	for(int i=0; i<nums.length; i++) {
    		if(p<i)
    			p=i;
    		while(p < nums.length && nums[p] * product < k) {
    			product *= nums[p];
    			p++;
    		}
    		count += p-i;
    		product /= nums[i];
    	}
    	
    	
    	return count;
    }
    
    public int countBinarySubstrings(String s) {
    	if(s == null || s.length() < 2)
    		return 0;
        int count = 0;
        int zero = 0;
        int one = 0;
        boolean change = s.charAt(0) == 1 ? true:false;
        for(int i=0; i<s.length(); i++) {
        	char c = s.charAt(i);
        	if(c == '0') {
        		if(!change) {
        			if(one > zero)
        				count++;
        			zero++;
        		}
        		else {
        			change = !change;
        			zero = 0;
        			if(one > zero)
        				count++;
        			zero++;
        		}
        	}
        	else {
        		if(change) {
        			if(zero > one)
        				count++;
        			one++;
        		}
        		else {
        			change = !change;
        			one = 0;
        			if(zero > one)
        				count++;
        			one++;
        		}
        	}
        }
        
        
        return count;
    }
    
    public boolean isOneBitCharacter(int[] bits) {
        boolean b = false;
        for(int i=0; i<bits.length; i++) {
        	int bit = bits[i];
        	if(bit == 1)
        		i++;
        	else {
        		if(i == bits.length-1)
        			b=true;
        	}
        }
        return b;
    }
    
    
    
    public int smallestDistancePair(int[] nums, int k) {
        int n = nums.length;
        int size = 1000000;
        int[] dp = new int[size];
        for(int i=0; i<n; i++) {
        	int num1 = nums[i];
        	for(int j=i+1; j<n; j++) {
        		int num2 = nums[j];
        		int distance = num1 - num2;
        		if(distance < 0)
        			distance = -distance;
        		dp[distance]++;
        	}
        }
        int sum = 0;
        for(int i=0; i<dp.length; i++) {
        	sum += dp[i];
        	if(sum >= k)
        		return i;
        }
    	
    	return 0;
    }
    
    
    
    public char[][] updateBoard(char[][] board, int[] click) {
    	if(board[click[0]][click[1]] == 'M') {
    		board[click[0]][click[1]] = 'X';
    		return board;
    	}
    	boolean[][] used = new boolean[board.length][board[0].length];
    	used[click[0]][click[1]] = true;
    	int[][] around = new int[8][2];
    	Queue<int[]> queue = new LinkedList<>();
    	queue.add(new int[] {click[0], click[1]});
    	while(!queue.isEmpty()) {
    		int[] array = queue.poll();
    		int X = array[0], Y = array[1];
    		around[0][0] = X; around[0][1] =Y-1;
    		around[1][0] = X; around[1][1] =Y+1;
    		around[2][0] = X-1; around[2][1] =Y;
    		around[3][0] = X+1; around[3][1] =Y;
    		around[4][0] = X-1; around[4][1] =Y-1;
    		around[5][0] = X-1; around[5][1] =Y+1;
    		around[6][0] = X+1; around[6][1] =Y-1;
    		around[7][0] = X+1; around[7][1] =Y+1;
    		int bomb = 0;
    		for(int[] temp : around) {
    			int x = temp[0];
    			int y = temp[1];
    			if(x>=0 && x< board.length && y>=0 && y<board[0].length) {
    				if(board[x][y] == 'M')
    					bomb++;
    			}
    		}
    		if(bomb != 0) {
    			board[X][Y] = (char) (bomb + '0');
    		}
    		else {
    			board[X][Y] = 'B';
    			for(int[] temp : around) {
    				int x = temp[0];
        			int y = temp[1];
        			if(x>=0 && x< board.length && y>=0 && y<board[0].length && !used[x][y]) {
        				queue.add(new int[] {x, y});
        				used[x][y] = true;
        			}
    			}
    		}
    	}
    	return board;
    }
    
    
    
    
    public char[][] updateBoard2(char[][] board, int[] click) {
    	if(board[click[0]][click[1]] == 'M') {
    		board[click[0]][click[1]] = 'X';
    		return board;
    	}
    	 int m = board.length, n = board[0].length;
    	 boolean[][] used = new boolean[m][n];
         Queue<int[]> queue = new LinkedList<>();
         queue.add(click);
         while (!queue.isEmpty()) {
             int[] cell = queue.poll();
             int row = cell[0], col = cell[1];
             int count = 0;
             for (int i = -1; i < 2; i++) {
                 for (int j = -1; j < 2; j++) {
                     if (i == 0 && j == 0) continue;
                     int r = row + i, c = col + j;
                     if (r < 0 || r >= m || c < 0 || c < 0 || c >= n) continue;
                     if (board[r][c] == 'M' || board[r][c] == 'X') count++;
                 }
             }
             
             if (count > 0) { // If it is not a 'B', stop further BFS.
                 board[row][col] = (char)(count + '0');
             }
             else { // Continue BFS to adjacent cells.
                 board[row][col] = 'B';
                 for (int i = -1; i < 2; i++) {
                     for (int j = -1; j < 2; j++) {
                         if (i == 0 && j == 0) continue;
                         int r = row + i, c = col + j;
                         if (r < 0 || r >= m || c < 0 || c < 0 || c >= n) continue;
                         if(used[r][c])
                        	 continue;
                         if (board[r][c] == 'E') {
                             queue.add(new int[] {r, c});
                             used[r][c] = true; // Avoid to be added again.
                         }
                     }
                 }
             }
             
         }
         
         return board;
    	
    }
    
     
    public int pivotIndex(int[] nums) {
        if(nums == null || nums.length < 2)
        	return -1;
        int sum = 0;
        for(int num : nums)
        	sum += num;
        int left = 0, right = sum - nums[0];
        for(int i=0; i<nums.length-1; i++) {
        	if(left == right)
        		return i;
        	left += nums[i];
        	right -= nums[i+1];
        }
        if(sum - nums[nums.length-1] == 0) {
        	return nums.length-1;
        }
    	return -1;
    }
    
    
    public ListNode[] splitListToParts(ListNode root, int k) {
        if(root == null)
        	return new ListNode[] {};
        ListNode[] res = new ListNode[k];
        int length = 0;
        ListNode node = root;
        while(node != null) {
        	length++;
        	node = node.next;
        }
        int[] count = new int[k];
        int segment = length / k;
        int mod = length % k;
        for(int i=0; i<k; i++) {
        	count[i] = segment;
        	if(i < mod)
        		count[i]++;
        }
        int i = 0 , j = 0;
        node = root;
        ListNode pivot = root;
        while(node != null) {
        	ListNode next = node.next;
        	count[j]--;
        	if(count[j] == 0) {
        		j++;
        		res[i++] = pivot;
        		node.next = null;
        		pivot = next;
        	}
        	node = next;
        }
        
        return res;
    }
    
    public int leastInterval(char[] tasks, int n) {
        int[] array = new int[26];
        for(char c : tasks)
        	array[c - 'A']++;
        Arrays.sort(array);
        int i = 25;
        while(i >= 0 && array[i] == array[25])
        	i--;
        return Math.max(tasks.length, (array[25]-1) * (n+1) + 25 - i);
    }
    
    class Child implements Comparable<Child>{
    	int rating;
    	int index;
    	public Child(int rating, int index) {
			this.rating = rating;
			this.index = index;
		}
		@Override
		public int compareTo(Child o) {
			if(this.rating < o.rating)
				return -1;
			else if(this.rating > o.rating)
				return 1;
			return 0;
		}
    }
    
    public int candy(int[] ratings) {
        if(ratings == null || ratings.length == 0)
        	return 0;
        int count = ratings.length;
        int n = ratings.length;
        Child[] childs = new Child[n];
        int[] temp = new int[n];
        for(int i=0; i<n; i++)
        	childs[i] = new Child(ratings[i], i);
        Arrays.sort(childs);
        for(int i=0; i<n; i++) {
        	Child child = childs[i];
        	int index = child.index;
        	int left = index - 1, right = index +1;
        	boolean gt1 = false, gt2 = false;
        	if(left >= 0) {
        		if(ratings[index] > ratings[left])
        			gt1 = true;
        	}
        	if(right < n) {
        		if(ratings[index] > ratings[right])
        			gt2 = true;
        	}
        	if(gt1 && gt2) {
        		temp[index] = Math.max(temp[left], temp[right]) + 1;
        	}
        	else if(gt1) {
        		temp[index] = temp[left] + 1;
        	}
        	else if(gt2){
        		temp[index] = temp[right] + 1;
        	}
        	count += temp[index];
        }
        return count;
    }
    
    class CalendarNode {
    	int left;
    	int right;
    	public CalendarNode(int left, int right) {
			this.left = left;
			this.right = right;
		}
    }
    
    private TreeMap<Integer, CalendarNode> map;
    
    public void MyCalendar() {
        map = new TreeMap<>();
    }
    
    public boolean book(int start, int end) {
        Map.Entry<Integer, CalendarNode> pre = map.ceilingEntry(start);
        Map.Entry<Integer, CalendarNode> after = map.floorEntry(start);
        if(after != null) {
        	int after_right = after.getValue().right;
        	if(after_right > start)
        		return false;
        }
        if(pre != null) {
        	int pre_left = pre.getValue().left;
        	if(pre_left < end)
        		return false;
        }
    	CalendarNode event = new CalendarNode(start, end);
    	map.put(start, event);
    	return true;
    }
    
    class Calendar2Node {
    	int left;
    	int right;
    	int count;
    	public Calendar2Node(int left, int right, int count) {
			this.left = left;
			this.right = right;
			this.count = count;
		}
    	
    }
    
    private TreeMap<Integer, Calendar2Node> map2;
    public boolean book2(int start, int end) {
        Map.Entry<Integer, Calendar2Node> after = map2.floorEntry(start);
        Map.Entry<Integer, Calendar2Node> next = map2.floorEntry(end);
        if(after != null) {
        	int after_right = after.getValue().right;
        	if(after_right > start ) {
        		if(after.getValue().count == 2)
        			return false;
        	}
        }
        if(next != null) {
        	if(next.getValue().left != end) {
        		int pre_right = next.getValue().right;
            	if(pre_right > end) {
            		if(next.getValue().count == 2)
            			return false;
            	}
        	}
        }
        
        
        if(after != null) {
        	int after_right = after.getValue().right;
        	if(after_right > start ) {
        		if(after.getValue().count == 2)
        			return false;
        		map2.remove(after.getValue().left);
        		Calendar2Node node1 = new Calendar2Node(after.getValue().left, start, 1);
        		map2.put(after.getValue().left, node1);
        		if(after_right < end) {
        			Calendar2Node node2 = new Calendar2Node(start, after.getValue().right, 2);
        			map2.put(start, node2);
        			start = after_right;
        		}
        		else if(after_right >= end) {
        			Calendar2Node node2 = new Calendar2Node(start, end, 2);
        			map2.put(start, node2);
        			if(after_right != end) {
        				Calendar2Node node3 = new Calendar2Node(end, after.getValue().right, 1);
            			map2.put(end, node3);
        			}
        			return true;
        		}
        	}
        }
        Map.Entry<Integer, Calendar2Node> pre = map2.ceilingEntry(start);
        if(pre != null) {
        	int pre_left = pre.getValue().left;
        	if(pre_left < end) {
        		if(pre.getValue().count == 2)
        			return false;
        		map2.remove(pre.getValue().left);
        		Calendar2Node node1 = new Calendar2Node(start, pre.getValue().left, 1);
        		map2.put(start, node1);
        		if(pre.getValue().right > end) {
        			Calendar2Node node2 = new Calendar2Node(pre.getValue().left, end, 2);
        			map2.put(pre.getValue().left, node2);
        			Calendar2Node node3 = new Calendar2Node(end, pre.getValue().right, 1);
        			map2.put(end, node3);
        		}
        		else {
        			Calendar2Node node2 = new Calendar2Node(pre.getValue().left, pre.getValue().right, 2);
        			map2.put(pre.getValue().left, node2);
        			if(pre.getValue().right != end) {
        				Calendar2Node node3 = new Calendar2Node(pre.getValue().right, end, 1);
            			map2.put(end, node3);
        			}
        		}
        		return true;
        	}
        		
        }
		Calendar2Node event = new Calendar2Node(start, end, 1);
    	map2.put(start, event);
    	return true;
    }
    
    
    private List<int[]> books = new ArrayList<>();
    public boolean book3(int start, int end) {
    	Mycalendar overlaps = new Mycalendar();
    	for(int[] b : books) {
    		if(Math.max(b[0], start) < Math.max(b[1], end)) {
    			if(!overlaps.book(Math.max(b[0], start), Math.max(b[1], end)))
    				return false;
    		}
    	}
    	books.add(new int[] {start, end});
    	return true;
    }
    
    
    private static class Mycalendar {
    	List<int[]> books = new ArrayList<>();
    	public boolean book(int start, int end) {
    		for(int[] b : books) {
    			if(Math.max(start, b[0]) < Math.min(b[1], end))
    				return false;
    		}
    		books.add(new int[] {start, end});
    		return true;
    	}
    }
    
    
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        if(image == null || image.length == 0 || image[0].length == 0)
        	return image;
        if(image[sr][sc] == newColor)
        	return image;
        floodFillDFS(image, sr, sc, newColor);
    	return image;
    }
    
    private void floodFillDFS(int[][] image, int x, int y, int newColor) {
    	int color = image[x][y];
    	image[x][y] = newColor;
    	if(x-1 >= 0) {
    		if(image[x-1][y] == color)
    			floodFillDFS(image, x-1, y, newColor);
    	}
    	if(x+1 <image.length) {
    		if(image[x+1][y] == color)
    			floodFillDFS(image, x+1, y, newColor);
    	}
    	if(y-1 >= 0) {
    		if(image[x][y-1] == color)
    			floodFillDFS(image, x, y-1, newColor);
    	}
    	if(y+1 < image[0].length) {
    		if(image[x][y+1] == color)
    			floodFillDFS(image, x, y+1, newColor);
    	}
    }
    
    
    public int[] asteroidCollision(int[] asteroids) {
        if(asteroids == null || asteroids.length < 2)
        	return asteroids;
        Stack<Integer> stack = new Stack<>();
        stack.push(asteroids[0]);
        for(int i=1; i<asteroids.length; i++) {
        	int cur = asteroids[i];
        	if(stack.isEmpty()) {
        		stack.push(cur);
        		continue;
        	}
        	int peek = stack.peek();
        	if(peek > 0 && cur < 0) {
        		if(peek > -cur)
        			continue;
        		else if(peek == -cur) {
        			stack.pop();
        		}
        		else {
        			boolean add = true;
        			while(!stack.isEmpty()) {
        				int temp = stack.peek();
        				if(temp < 0) {
        					break;
        				}
        				if(temp < -cur)
        					stack.pop();
        				else if(temp == -cur) {
        					stack.pop();
        					add = false;
        					break;
        				}
        				else {
        					add = false;
        					break;
        				}
        			}
        			if(add)
        				stack.push(cur);
        		}
        			
        	}
        	else
        		stack.push(cur);
        }
        int size = stack.size();
        int[] result = new int[size];
        for(int i=size-1; i>=0; i--)
        	result[i] = stack.pop();
    	return result;
    }
    
    private Map<Integer, Integer> map3 = new HashMap<>();
    
    public int book4(int start, int end) {
        map3.put(start, map3.getOrDefault(start, 0) + 1);
        map3.put(end, map3.getOrDefault(end, 0) - 1);
        int ongoing = 0, k = 0;
        for(int val : map3.values())
        	k = Math.max(k, ongoing += val);
        return k;
    }
    
    public int[] dailyTemperatures(int[] temperatures) {
        int length = temperatures.length;
        int[] array = new int[length];
        Stack<Integer> stack = new Stack<>();
        for(int i=0; i<length; i++) {
        	if(stack.isEmpty() || temperatures[stack.peek()] >= temperatures[i]) {
        		stack.push(i);
        	}
        	else {
        		while(!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]) {
        			int index = stack.pop();
        			array[index] = i - index;
        		}
        		stack.push(i);
        	}
        }
        return array;
    }
    
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    	int len = nums1.length + nums2.length;
    	if((len & 1) == 0)
    		return (findMedianSortedArraysHelp(nums1, 0, nums2, 0, len) + findMedianSortedArraysHelp(nums1, 0, nums2, 0, len/2+1)) / 2.0;
    	
    	return findMedianSortedArraysHelp(nums1, 0, nums2, 0, len/2);
    }
    
    private int findMedianSortedArraysHelp(int[] nums1, int m, int[] nums2, int n, int k) {
    	if(m >= nums1.length)
    		return nums2[n + k - 1];
    	if(n >= nums2.length)
    		return nums1[m + k - 1];
    	if(k == 1)
    		return Math.min(nums1[m], nums2[n]);
    	int p1 = m + k/2 - 1;
    	int p2 = n + k/2 - 1;
    	int mid1 = p1 >= nums1.length ? Integer.MAX_VALUE : nums1[p1];
    	int mid2 = p2 >= nums2.length ? Integer.MAX_VALUE : nums2[p2];
    	if(mid1 < mid2)
    		return findMedianSortedArraysHelp(nums1, m + k/2 , nums2, n, k - k/2);
    	return findMedianSortedArraysHelp(nums1, m, nums2, n+k/2, k-k/2);
    }
    
    
    public char nextGreatestLetter(char[] letters, char target) {
    	if(target >= letters[letters.length-1])
    		return letters[0];
    	target++;
        int left = 0, right = letters.length-1;
        while(left <= right) {
        	int mid = left + (right - left) / 2;
        	if(letters[mid] == target) {
        		left = mid;
        		break;
        	}
        	else if(letters[mid] < target)
        		left = mid + 1;
        	else 
        		right = mid - 1;
        }
    	return letters[left];
    }
    
    
    

	public static void main(String[] args) {
		Array test = new Array();
		int[] array = {2,2,10,5,2,7,2,2,13};
		test.canPartitionKSubsets(array, 3);

	}

}
