package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
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
    
    
    public int networkDelayTime(int[][] times, int N, int K) {
        @SuppressWarnings("unchecked")
		List<Integer>[] graph = new List[N+1];
        for(int i=0; i<=N; i++)
        	graph[i] = new ArrayList<>();
        for(int[] time : times) {
        	int source = time[0], target = time[1], cost = time[2];
        	graph[source].add(target);
        	graph[source].add(cost);
        }
        boolean[] visited = new boolean[N+1];
        int[] time = new int[N+1];
        Arrays.fill(time, Integer.MAX_VALUE);
        visited[K] = true;
        time[0] = 0;
        networkDelayTimeBacktrack(graph, K, visited, time, 0);
        int answer = 0;
        for(int t : time) {
        	if(t== Integer.MAX_VALUE)
        		return -1;
        	answer = Math.max(answer, t);
        }
    	return answer;
    }
    
    private void networkDelayTimeBacktrack(List<Integer>[] graph, int point, boolean[] visited, int[] time, int cost) {
    	List<Integer> adj = graph[point];
    	time[point] = Math.min(time[point],  cost);
    	for(int i=0; i<adj.size(); i+=2) {
    		int other = adj.get(i);
    		int t = adj.get(i+1);
    		if(visited[other]) {
    			int temp = cost + t;
    			if(temp >= time[other])
    				continue;
    		}
    		visited[other] = true;
    		networkDelayTimeBacktrack(graph, other, visited, time, cost + t);
    	}
    }
    
    public int dominantIndex(int[] nums) {
    	if(nums == null || nums.length == 0)
    		return -1;
    	if(nums.length == 1)
    		return 0;
        int one = Integer.MIN_VALUE, two = Integer.MIN_VALUE;
        int one_index = -1;
        for(int i=0; i<nums.length; i++) {
        	int num = nums[i];
        	if(num > one) {
        		two = one;
        		one = num;
        		one_index = i;
        	}
        	else if(num > two) {
        		two = num;
        	}
        }
        if(one >= two * 2)
        	return one_index;
    	return -1;
    }
    
    
    public int[] findRedundantConnection(int[][] edges) {
        int N = edges.length;
        int[] answer = null;
        @SuppressWarnings("unchecked")
		ArrayList<Integer>[] graph = new ArrayList[N+1];
        for(int i=1; i<=N; i++)
        	graph[i] = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        for(int[] edge : edges) {
        	int source = edge[0], target = edge[1];
        	visited.add(source);
        	if(findRedundantConnectionDFS(graph, source, target, visited))
        		answer = edge;
        	else {
        		graph[source].add(target);
        		graph[target].add(source);
        	}
        	visited.clear();
        }
    	return answer;
    }
    
    private boolean findRedundantConnectionDFS(ArrayList<Integer>[] graph, int source, int target, Set<Integer> visited) {
    	if(source == target)
    		return true;
    	for(int adj : graph[source]) {
    		if(visited.contains(adj))
    			continue;
    		visited.add(adj);
    		if(findRedundantConnectionDFS(graph, adj, target, visited))
    			return true;
    	}
    	return false;
    }
    
    public int lengthOfLIS(int[] nums) {
    	int[] tails = new int[nums.length];
    	int size = 0;
    	for(int num : nums) {
    		int i = 0, j = size;
    		while(i != j) {
    			int mid = i + (j - i) / 2;
    			if(tails[mid] < num)
    				i = mid + 1;
    			else
    				j = mid;
    		}
    		tails[i] = num;
    		if(i == size)
    			size++;
    	}
    	return size;
    }
    
    
    public int maxEnvelopes(int[][] envelopes) {
    	 if(envelopes.length < 2) return envelopes.length;
    	 Arrays.sort(envelopes, new EnvelopeComparator());
    	 int[] dp = new int[envelopes.length];
    	 int size = 0;
    	 for(int[] envelope : envelopes) {
    		 int num = envelope[1];
    		 int i=0, j = size;
    		 while(i < j) {
    			 int mid = i + (j - i) / 2;
    			 if(dp[mid] < num)
    				 i = mid + 1;
    			 else
    				 j = mid;
    		 }
    		 dp[i] = num;
    		 if(i == size)
    			 size++;
    	 }
    	 return size;
    }
    
    class EnvelopeComparator implements Comparator<int[]> {

		@Override
		public int compare(int[] o1, int[] o2) {
			return o1[0] == o2[0] ? o2[1] - o1[1] : o1[0] - o2[0];
		}
    	
    }
    
    
    
    public int findRadius(int[] houses, int[] heaters) {
        Arrays.sort(heaters);
        int answer = -1;
        for(int house : houses) {
        	int index = Arrays.binarySearch(heaters, house);
        	if(index < 0)
        		index = -(index+1);
        	int dis1 = index - 1 >= 0 ? house - heaters[index-1] : Integer.MAX_VALUE;
        	int dis2 = index < heaters.length ? heaters[index] - house : Integer.MAX_VALUE;
        	answer = Math.max(answer, Math.min(dis1, dis2));
        }
        return answer;
    }
    
    public int[] anagramMappings(int[] A, int[] B) {
        int n = A.length;
        int[] answer = new int[n];
        Map<Integer, Integer> map = new HashMap<>();
        for(int i=0; i<n; i++) {
        	map.put(B[i], i);
        }
        for(int i=0; i<n; i++) {
        	answer[i] = map.get(A[i]);
        }
    	return answer;
    }
    
    
    
     public class Interval {
    	      int start;
    	      int end;
    	      Interval() { start = 0; end = 0; }
    	      Interval(int s, int e) { start = s; end = e; }
    	  }
    
    
     public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
    	 if(schedule == null || schedule.size() == 0)
    		 return null;
         List<Interval> freeTime = new ArrayList<>();
         List<Interval> first = schedule.get(0);
         for(int i=0; i<first.size(); i++) {
        	 Interval interval = first.get(i);
        	 if(i == 0) {
        		 freeTime.add(new Interval(Integer.MIN_VALUE, interval.start));
        	 }
        	 if(i == first.size() -1) {
        		 freeTime.add(new Interval(interval.end, Integer.MAX_VALUE));
        	 }
        	 if(i<first.size() - 1) {
        		 freeTime.add(new Interval(interval.end, first.get(i+1).start));
        	 }
         }
         for(int i=0; i<freeTime.size(); i++) {
        	 System.out.println("start: "+freeTime.get(i).start + "end: "+freeTime.get(i).end);
         }
         if(schedule.size() == 1) {
        	 List<Interval> answer = new ArrayList<>();
        	 for(int i=1; i<freeTime.size()-1; i++){
        		 answer.add(freeTime.get(i));
        	 }
        	 return answer;
         }
         for(int i=1; i<schedule.size(); i++) {
        	 List<Interval> another = new ArrayList<>();
             List<Interval> list = schedule.get(i);
             for(int j=0; j<list.size(); j++) {
            	 Interval interval = list.get(j);
            	 if(j == 0) {
            		 another.add(new Interval(Integer.MIN_VALUE, interval.start));
            	 }
            	 if(j == list.size() -1) {
            		 another.add(new Interval(interval.end, Integer.MAX_VALUE));
            	 }
            	 if(j<list.size() - 1) {
            		 another.add(new Interval(interval.end, list.get(j+1).start));
            	 }
             }
             System.out.println("***********another**************");
             for(int x=0; x<another.size(); x++) {
            	 System.out.println("start: "+another.get(x).start + "end: "+another.get(x).end);
             }
             System.out.println("-----------another--------------");
             List<Interval> newFT = new ArrayList<>();
             int index1 = 0, index2 = 0;
             while(index1 < freeTime.size() && index2 < another.size()) {
            	 Interval interval1 = freeTime.get(index1);
            	 Interval interval2 = another.get(index2);
            	 if(interval2.start < interval1.start) {
            		 if(interval2.end <= interval1.start) {
            			 index2++;
            		 }
            		 else if(interval2.end > interval1.start && interval2.end <= interval1.end) {
            			 newFT.add(new Interval(interval1.start, interval2.end));
            			 interval1.start = interval2.end;
            			 index2++;
            		 }
            		 else {
            			 newFT.add(interval1);
            			 index1++;
            			 interval2.start = interval1.end;
            		 }
            	 }
            	 else if(interval2.start == interval1.start) {
            		 if(interval2.end == interval1.end) {
            			 newFT.add(interval1);
            			 index1++;
            			 index2++;
            		 }
            		 else if(interval2.end < interval1.end) {
            			 newFT.add(interval2);
            			 index2++;
            			 interval1.start = interval2.end;
            		 }
            		 else {
            			 newFT.add(interval1);
            			 index1++;
            			 interval2.start = interval1.end;
            		 }
            	 }
            	 else if(interval2.start > interval1.start && interval2.start < interval1.end) {
            		 if(interval2.end < interval1.end) {
            			 newFT.add(interval2);
            			 index2++;
            			 interval1.start = interval2.end;
            		 }
            		 else if(interval2.end == interval1.end) {
            			 newFT.add(interval2);
            			 index1++;
            			 index2++;
            		 }
            		 else {
            			 newFT.add(new Interval(interval2.start, interval1.end));
            			 interval2.start = interval1.end;
            			 index1++;
            		 }
            	 }
            	 else if(interval2.start >= interval1.end) {
            		 index1++;
            	 }
             }
             freeTime = newFT;
             System.out.println("***********newFT**************");
             for(int x=0; x<freeTime.size(); x++) {
            	 System.out.println("start: "+newFT.get(x).start + "end: "+newFT.get(x).end);
             }
             System.out.println("-----------newFT--------------");
         }
         List<Interval> answer = new ArrayList<>();
    	 for(int i=1; i<freeTime.size()-1; i++){
    		 answer.add(freeTime.get(i));
    	 }
    	 return answer;
    	 
    	 
     }
     
     
     public int hIndex(int[] citations) {
         int h = 1;
         Arrays.sort(citations);
         for(int i=citations.length-1; i >=0; i--) {
        	 if(citations[i] >= h)
        		 h++;
        	 else
        		 break;
         }
         h--;
         return h;
     }
     
     public int hIndex2(int[] citations) {
    	 int[] bucket = new int[citations.length+1];
    	 for(int citation : citations) {
    		 if(citation >= citations.length) {
    			 bucket[citations.length]++;
    		 }
    		 else {
    			 bucket[citation]++;
    		 }
    	 }
    	 int count = 0;
    	 for(int i=citations.length; i>=0; i--) {
    		 count += bucket[i];
    		 if(count >= i)
    			 return count;
    	 }
    	 
    	 return 0;
     }
     
     
     public List<List<String>> solveNQueens(int n) {
         List<List<String>> solve = new ArrayList<>();
    	 if(n <= 0)
    		 return solve;
    	 char[][] chessboard = new char[n][n];
    	 for(int i=0; i<n; i++)
    		 for(int j=0; j<n; j++)
    			 chessboard[i][j] = '.';
    	 int[][] used = new int[n][n];
    	 solveNQueensBacktrack(solve, chessboard, 0, used);
    	 return solve;
     }
     
     private void solveNQueensBacktrack(List<List<String>> solve, char[][] chessboard, int level, int[][] used) {
    	 if(level == chessboard.length) {
    		 List<String> buf = new ArrayList<>();
    		 for(char[] array:chessboard)
    			 buf.add(new String(array));
    		 solve.add(buf);
    	 }
    	 else {
    		 for(int i=0; i<chessboard.length; i++) {
    			 if(used[level][i] > 0)
    				 continue;
    			 chessboard[level][i] = 'Q';
    			 NQueensUsed(level, i, used, 1);
    			 solveNQueensBacktrack(solve, chessboard, level+1, used);
    			 chessboard[level][i] = '.';
    			 NQueensUsed(level, i, used, -1);
    		 }
    	 }
     }
     
     private void NQueensUsed(int x, int y, int[][] used, int num) {
    	 int n = used.length;
    	 for(int i=0; i<n; i++) {
    		 used[x][i] = used[x][i] + num;
    		 used[i][y] = used[i][y] + num;
    		 if(x-i >= 0 && y - i >= 0)
    			 used[x-i][y-i] = used[x-i][y-i] + num;
    		 if(x+i < n && y+i < n)
    			 used[x+i][y+i] = used[x+i][y+i] + num;
    		 if(x-i >= 0 && y+i < n)
    			 used[x-i][y+i] = used[x-i][y+i] + num;
    		 if(x+i < n && y-i >= 0)
    			 used[x+i][y-i] = used[x+i][y-i] + num;
    	 }
     }
     
     
     public boolean isToeplitzMatrix(int[][] matrix) {
         int M = matrix.length;
         int N = matrix[0].length;
         for(int i=0; i<M; i++) {
        	 int num = matrix[i][0];
        	 for(int j=1; i+j < M && j<N; j++) {
        		 if(matrix[i+j][j] != num)
        			 return false;
        	 }
         }
         
         for(int i=1; i<N; i++) {
        	 int num = matrix[0][i];
        	 for(int j=1; i< M && i+j<N; j++) {
        		 if(matrix[i][i+j] != num)
        			 return false;
        	 }
         }
    	 
    	 return true;
     }
     
     public List<int[]> pacificAtlantic(int[][] matrix) {
         List<int[]> answers = new ArrayList<>();
         if(matrix == null || matrix.length == 0)
        	 return answers;
         int m = matrix.length, n = matrix[0].length;
         boolean[][] Pacific = new boolean[m][n];
         boolean[][] Atlantic = new boolean[m][n];
         
         for(int i=0; i<m; i++) {
        	 if(Pacific[i][0])
        		 continue;
        	 Pacific[i][0] = true;
        	 pacificAtlanticDFS(Pacific, i, 0, matrix);
         }
         
         for(int i=0; i<n; i++) {
        	 if(Pacific[0][i])
        		 continue;
        	 Pacific[0][i] = true;
        	 pacificAtlanticDFS(Pacific, 0, i, matrix);
         }
         
         for(int i=0; i<m; i++) {
        	 if(Atlantic[i][n-1])
        		 continue;
        	 Atlantic[i][n-1] = true;
        	 pacificAtlanticDFS(Atlantic, i, n-1, matrix);
         }
         
         for(int i=0; i<n; i++) {
        	 if(Atlantic[m-1][i])
        		 continue;
        	 Atlantic[m-1][i] = true;
        	 pacificAtlanticDFS(Atlantic, m-1, i, matrix);
         }
         
         for(int i=0; i<m; i++)
        	 for(int j=0; j<n; j++)
        		 if(Pacific[i][j] && Atlantic[i][j])
        			 answers.add(new int[] {i, j});
         
         return answers;
     }
     
     private int[][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}};
     
     private void pacificAtlanticDFS(boolean[][] ocean, int x, int y, int[][] matrix) {
    	 int m = matrix.length, n = matrix[0].length;
    	 int height = matrix[x][y];
    	 for(int[] direction : directions) {
    		 int nextX = x + direction[0], nextY = y + direction[1];
    		 if(nextX < 0 || nextX >= m || nextY < 0 || nextY >= n)
    			 continue;
    		 if(ocean[nextX][nextY])
    			 continue;
    		 if(height > matrix[nextX][nextY])
    			 continue;
    		 ocean[nextX][nextY] = true;
    		 pacificAtlanticDFS(ocean, nextX, nextY, matrix);
    	 }
     }
     
     
     
     public int maxChunksToSorted(int[] arr) {
         int chunks = 1;
         int length = arr.length;
         boolean[] used = new boolean[length];
         int start = 0;
         for(int i=0; i<length; i++) {
        	 int num = arr[i];
        	 used[num] = true;
        	 boolean split = true;
        	 for(int j=start; j<=i; j++) {
        		 if(!used[j]) {
        			 split = false;
        			 break;
        		 }
        	 }
        	 if(split) {
        		 chunks++;
        		 start = i+1;
        	 }
         }
         if(chunks > 1)
        	 chunks--;
         
         return chunks;
     }
     
     
     
     class Cell implements Comparable<Cell> {
    	int row;
    	int col;
    	int height;
    	public Cell(int row, int col, int height) {
			this.row = row;
			this.col = col;
			this.height = height;
		}
		@Override
		public int compareTo(Cell o) {
			return this.height - o.height;
		}
    	 
     }
     
     public int trapRainWater(int[][] heightMap) {
         int answers = 0;
         if(heightMap == null || heightMap.length == 0 || heightMap[0].length == 0)
        	 return answers;
         int m = heightMap.length, n = heightMap[0].length;
         boolean[][] visited = new boolean[m][n];
         PriorityQueue<Cell> queue = new PriorityQueue<>();
         
         for(int i=0; i<m; i++) {
        	 visited[i][0] = true;
        	 visited[i][n-1] = true;
        	 queue.offer(new Cell(i, 0, heightMap[i][0]));
        	 queue.offer(new Cell(i, n-1, heightMap[i][n-1]));
         }
         for(int i=0; i<n; i++) {
        	 visited[0][i] =true;
        	 visited[m-1][i] = true;
        	 queue.offer(new Cell(0, i, heightMap[0][i]));
        	 queue.offer(new Cell(m-1, i, heightMap[m-1][i]));
         }
         int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}};
         while(!queue.isEmpty()) {
        	 Cell cell = queue.poll();
        	 for(int[] dir : dirs) {
        		 int row = cell.row + dir[0];
        		 int col = cell.col + dir[1];
        		 if(row < 0 || row >= m || col < 0 || col >= n || visited[row][col])
        			 continue;
        		 visited[row][col] = true;	
        		 answers += Math.max(0, cell.height - heightMap[row][col]);
        		 queue.offer(new Cell(row, col, Math.max(heightMap[row][col], cell.height)));
        	 }
         }
         
         return answers;
     }
     
     
     public int maxChunksToSortedHard(int[] arr) {
         int chunks = 1;
         Stack<Integer> stack = new Stack<>();
         int small = Integer.MAX_VALUE;
         int target = arr[0];
         for(int i=1; i<arr.length; i++) {
        	 if(arr[i] < target) {
        		 small = Math.min(small, arr[i]);
        		 continue;
        	 }
        	 while(!stack.isEmpty() && stack.peek() > small) {
        		 chunks--;
        		 stack.pop();
        	 }
        	 stack.push(target);
        	 chunks++;
        	 target = arr[i];
        	 small = Integer.MAX_VALUE;
         }
         while(!stack.isEmpty() && stack.peek() > small) {
    		 chunks--;
    		 stack.pop();
    	 }
    	 stack.push(target);
    	 chunks++;
         
         if(chunks > 1)
        	 chunks--;
         return chunks;
     }
     
     public int minSwapsCouples(int[] row) {
         int answer = 0;
         int n = row.length;
         int[] pos = new int[n];
         for(int i=0; i<n; i++)
        	 pos[row[i]] = i;
         for(int i=0; i<n; i+=2) {
        	 int j = (row[i] & 1 ) == 0 ? row[i] + 1 : row[i] - 1;
        	 if(row[i+1] != j) {
        		 answer++;
        		 SwapsCouples(row, pos, i+1, pos[j]);
        	 }
         }
         
         return answer;
     }
     
     private void SwapsCouples(int[] row, int[] pos, int x, int y) {
    	 pos[row[x]] = y;
    	 pos[row[y]] = x;
    	 int temp = row[x];
    	 row[x] = row[y];
    	 row[y] = temp;
     }
     
     
     public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
         if(needs == null || needs.size() == 0)
        	 return 0;
         int n = needs.size();
         List<Integer> temp = new ArrayList<>();
         for(int i=0; i<n; i++)
        	 temp.add(0);
         Map<String, Integer> memorization = new HashMap<>();
         memorization.put(temp.toString(), 0);
    	 return shoppingOffersMemorization(price, special, needs, memorization);
     }
     
     private int shoppingOffersMemorization(List<Integer> price, List<List<Integer>> special, List<Integer> needs, Map<String, Integer> memorization) {
    	 String key = needs.toString();
    	 if(memorization.containsKey(key))
    		 return memorization.get(key);
    	 
    	 int min  = Integer.MAX_VALUE;
    	 int n = price.size();
    	 
    	 int local = 0;
    	 for(int i=0; i<n; i++) {
    		 int p = price.get(i);
    		 int need = needs.get(i);
    		 local += p * need;
    	 }
    	 
    	 min = Math.min(min, local);
    	 
    	 for(List<Integer> list : special) {
    		 int total = list.get(n);
    		 boolean zero = true;
    		 boolean canBuy = true;
    		 for(int i=0; i<n; i++) {
    			 if(list.get(i) > 0)
    				 zero = false;
    			 if(needs.get(i) < list.get(i)) {
    				 canBuy = false;
    				 break;
    			 }
    		 }
    		 if(canBuy && !zero) {
    			 for(int i=0; i<n; i++) {
    				 int need = needs.get(i);
    				 needs.set(i, need - list.get(i));
    			 }
    			 min = Math.min(min, total + shoppingOffersMemorization(price, special, needs, memorization));
    			 for(int i=0; i<n; i++) {
    				 int need = needs.get(i);
    				 needs.set(i, need + list.get(i));
    			 }
    		 }
    	 }
    	 
    	 
    	 
    	 
    	 memorization.put(key, min);
    	 return min;
     }
     
     
     public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
    	 if(desiredTotal <= 0)
    		 return true;
    	 if (maxChoosableInteger*(maxChoosableInteger+1)/2<desiredTotal) 
    		 return false;
    	 boolean[] used = new boolean[maxChoosableInteger+1];
    	 return canIWin(used, desiredTotal, new HashMap<>());
     }
     
     private boolean canIWin(boolean[] used, int total, Map<String, Boolean> memorization) {
    	 String key = Arrays.toString(used);
    	 if(memorization.containsKey(key))
    		 return memorization.get(key);
    	 for(int i=1; i<used.length; i++) {
    		 if(used[i])
    			 continue;
    		 used[i] = true;
    		 if(i >= total || !canIWin(used, total - i, memorization)) {
    			 used[i] = false;
    			 memorization.put(key, true);
    			 return true;
    		 }
    		 used[i] = false;
    	 }
    	 
    	 memorization.put(key, false);
    	 return false;
     }
     
     
     public int slidingPuzzle(int[][] board) {
         int steps = 0;
         int[][] target = {{1,2,3}, {4,5,0}};
         String goal = Arrays.deepToString(target);
         int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}};
         Map<String, int[][]> map = new HashMap<>();
         String start = Arrays.deepToString(board);
         if(start.equals(goal))
        	 return 0;
         map.put(start, board);
         
         Queue<String> queue = new LinkedList<>();
         queue.add(start);
         
         Queue<int[]> position = new LinkedList<>();
         for(int i=0; i<2; i++)
        	 for(int j=0; j<3; j++)
        		 if(board[i][j] == 0) {
        			 position.add(new int[] {i, j});
        			 break;
        		 }
         
         while(!queue.isEmpty()) {
        	 int size = queue.size();
        	 steps++;
        	 for(int i=0; i<size; i++) {
        		 String state = queue.poll();
        		 int[][] matrix = map.get(state);
        		 int[] pos = position.poll();
        		 for(int[] dir : dirs) {
        			 int x = pos[0] + dir[0];
        			 int y = pos[1] + dir[1];
        			 if(x < 0 || x >= 2 || y < 0 || y >= 3)
        				 continue;
        			 slidingPuzzleSwap(matrix, pos[0], pos[1], x, y);
        			 String newState = Arrays.deepToString(matrix);
        			 if(newState.equals(goal))
        				 return steps;
        			 if(map.containsKey(newState)) {
        				 slidingPuzzleSwap(matrix, pos[0], pos[1], x, y);
        				 continue;
        			 }
        			 int[][] clone = new int[2][3];
        			 for(int a=0; a<2; a++)
        				 for(int b=0; b<3; b++)
        					 clone[a][b] = matrix[a][b];
        			 map.put(newState, clone);
        			 queue.offer(newState);
        			 position.offer(new int[] {x, y});
        			 slidingPuzzleSwap(matrix, pos[0], pos[1], x, y);
        		 }
        	 }
         }
         
         return -1;
     }
     
     private void slidingPuzzleSwap(int[][] board, int i, int j, int x, int y) {
    	 int temp = board[i][j];
    	 board[i][j] = board[x][y];
    	 board[x][y] = temp;
     }
     
     
     class isIdealPermutationNode {
    	 int val;
    	 isIdealPermutationNode left, right;
    	 int bigger; 
     }
     
     public boolean isIdealPermutation(int[] A) {
    	 int length = A.length;
    	 int[] index = new int[length];
    	 for(int i=0; i<length; i++)
    		 index[A[i]] = i;
    	 int local = 0;
    	 for(int i=0; i<length-1; i++) {
    		 if(A[i] > A[i+1])
    			 local++;
    	 }
    	 int global = 0;
    	 int[] result = new int[1];
    	 isIdealPermutationNode root = null;
    	 for(int i=0; i<length; i++) {
    		 root = isIdealPermutationHelp(root, index[i], result);
    		 global += result[0];
    		 result[0] = 0;
    	 }
    	 return global == local;
     }
     
     private isIdealPermutationNode isIdealPermutationHelp(isIdealPermutationNode node, int value, int[] reslut) {
    	 if(node == null) {
    		 node = new isIdealPermutationNode();
    		 node.val = value;
    		 return node;
    	 }
    	 if(node.val > value) {
    		 reslut[0] += node.bigger + 1;
    		 node.left = isIdealPermutationHelp(node.left, value, reslut);
    	 }
    	 else if(node.val < value) {
    		 node.bigger++;
    		 node.right = isIdealPermutationHelp(node.right, value, reslut);
    	 }
    	 return node;
     }
     
     
     public int findMinArrowShots(int[][] points) {
         int arrows = 0;
         long end = Long.MIN_VALUE;
         
         Arrays.sort(points, (a, b) -> (a[1]-b[1]));
         
         for(int[] point : points) {
        	 if(point[0] > end) {
        		 arrows++;
        		 end = point[1];
        	 }
         }
         
         return arrows;
     }
     
     
     public int eraseOverlapIntervals(Interval[] intervals) {
    	 int count = 0;
    	 if(intervals == null || intervals.length < 2)
    		 return count;
    	 
    	 Arrays.sort(intervals, new Comparator<Interval>() {
             @Override
             public int compare(Interval o1, Interval o2) {
                 if (o1.end != o2.end) return o1.end - o2.end;  //first sort by end
                 return o2.start - o1.start;  //second sort by start
             }
         });
    	 
    	 
    	 int end = intervals[0].end;
    	 for(int i=1; i<intervals.length; i++) {
    		 if(end > intervals[i].start){
    			 count++;
    		 }
    		 else {
    			 end = intervals[i].end;
    		 }
    	 }

    	 
    	 
    	 return count;
    	 
     }
     
     
     public int intersectionSizeTwo(int[][] intervals) {
         int size = 0;
    	 Arrays.sort(intervals, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				if(o1[1] < o2[1])
					return -1;
				else if(o1[1] > o2[1])
					return 1;
				return o1[0] - o2[0];
			}
    		 
		});
    	int max1 = -1, max2 = -1;
    	for(int[] interval : intervals) {
    		int start = interval[0];
    		int end = interval[1];
    		if(start > max1) {
    			size += 2;
    			max1 = end;
    			max2 = end - 1;
    		}
    		else if(start > max2) {
    			size++;
    			max2 = max1 == end ? end - 1 : max1;
    			max1 = end;
    		}
    	}
    	 return size;
     }
     
     
     public int swimInWater(int[][] grid) {
         int[] result = new int[1];
         result[0] = 100;
         int n = grid.length;
         boolean[][] visited = new boolean[n][n];
    	 swimInWaterHelp(grid, visited, 0, 0, 0, result);
    	 return result[0];
     }
     
     
     private void swimInWaterHelp(int[][] grid, boolean[][] visited, int time, int x, int y, int[] result) {
    	 if(time > result[0])
    		 return;
    	 int n = grid.length;
    	 if(x == n-1 && y == n-1) {
    		 result[0] = Math.min(result[0], time);
    	 }
    	 else {
    		 for(int[] dir : directions) {
    			 int a = dir[0] + x;
    			 int b = dir[1] + y;
    			 if(a < 0 || a >= n || b < 0 || b >= n || visited[a][b])
    				 continue;
    			 visited[a][b] = true;
    			 int adjacent = grid[a][b];
    			 int max = Math.max(adjacent, grid[x][y]);
    			 int nextTime = max > time ? max : time;
    			 swimInWaterHelp(grid, visited, nextTime, a, b, result);
    			 visited[a][b] = false;
    		 }
    	 }
     }
     
     
     public int swimInWater2(int[][] grid) {
    	 int n = grid.length;
    	 boolean[][] visited = new boolean[n][n];
    	 Map<String, Integer> memorization = new HashMap<>();
    	 return swimInWater2Help(grid, visited, 0, 0, 0, memorization);
     }
     
     private int swimInWater2Help(int[][] grid, boolean[][] visited, int time, int x, int y, Map<String, Integer> memorization) {
    	 int n = grid.length;
    	 if(x == n-1 && y == n-1)
    		 return time;
    	 String key = ""+x+","+y+","+time;
    	 if(memorization.containsKey(key))
    		 return memorization.get(key);
    	 int result = Integer.MAX_VALUE;
		 for(int[] dir : directions) {
			 int a = dir[0] + x;
			 int b = dir[1] + y;
			 if(a < 0 || a >= n || b < 0 || b >= n || visited[a][b])
				 continue;
			 visited[a][b] = true;
			 int adjacent = grid[a][b];
			 int max = Math.max(adjacent, grid[x][y]);
			 int nextTime = max > time ? max : time;
			 result = Math.min(result, swimInWater2Help(grid, visited, nextTime, a, b, memorization));
			 visited[a][b] = false;
		 }
		 memorization.put(key, result);
         return result;
     }
     
     
     public int swimInWater3(int[][] grid) {
    	 int time = 0;
    	 int n = grid.length;
    	 PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[2]));
    	 queue.add(new int[] {0,0,grid[0][0]});
    	 grid[0][0] = -1;
    	 while(!queue.isEmpty()) {
    		 int[] array = queue.poll();
    		 time = Math.max(time, array[2]);
    		 if(array[0] == n-1 && array[1] == n-1)
    			 return time;
    		 for(int[] dir:directions) {
    			 int x = array[0] + dir[0];
    			 int y = array[1] + dir[1];
    			 if(x < 0 || x >= n || y < 0 || y >= n || grid[x][y] == -1)
    				 continue;
    			 queue.add(new int[] {x, y, grid[x][y]});
    			 grid[x][y] = -1;
    		 }
    	 }
    	 return time;
     }
          
     public boolean isScramble(String s1, String s2) {
         if(s1.equals(s2))
        	 return true;
         int[] hash = new int[26];
         int n = s1.length();
         for(int i=0; i<n; i++) {
        	 char c1 = s1.charAt(i);
        	 char c2 = s2.charAt(i);
        	 hash[c1 - 'a']++;
        	 hash[c2 - 'a']--;
         }
         for(int i=0; i<26; i++)
        	 if(hash[i] != 0)
        		 return false;
         for(int i=1; i<n; i++) {
        	 if(isScramble(s1.substring(0, i), s2.substring(0, i)) && 
        	    isScramble(s1.substring(i), s2.substring(i)))
        		 return true;
        	 
        	 if(isScramble(s1.substring(0, i), s2.substring(s2.length()-i)) &&
        		isScramble(s1.substring(i), s2.substring(0, s2.length()-i)))
        		 return true;
         }
    	 
    	 return false;
     }
     
     
     public int kthGrammar(int N, int K) {
         if(N == 1)
        	 return 0;
         if(N == 2)
        	 return K == 1 ? 0: 1;
         int pow = (int) Math.pow(2, N-2);
         if(K <= pow) {
        	 return kthGrammar(N-1, K);
         }
         else {
        	 return 1 - kthGrammar(N-1, K - pow);
         }
     }
     
     
     
     public int movesToChessboard(int[][] board) {
         int moves = 0;
         int n = board.length;
         if(!movesToChessboardPreCheck(board))
        	 return -1;
         Set<String> used = new HashSet<>();
         String goal1 = movesToChessboardGoal(n, true);
         String goal2 = movesToChessboardGoal(n, false);
         String start = Arrays.deepToString(board);
         if(start.equals(goal1) || start.equals(goal2))
        	 return 0;
         used.add(start);
         Queue<int[][]> queue = new LinkedList<>();
         queue.add(board);
         while(!queue.isEmpty()) {
        	 int size = queue.size();
        	 moves++;
        	 for(int i=0; i<size; i++) {
        		 int[][] matrix = queue.poll();
        		 for(int x=0; x<n; x++) {
        			 for(int y=x+1; y<n; y++) {
        				 if(movesToChessboardOptimized(board, x, y, true)) {
        					 movesToChessboardSwap(matrix, x, y, true);
            				 String temp = Arrays.deepToString(matrix);
            				 if(temp.equals(goal1) || temp.equals(goal2))
            					 return moves;
            				 if(!used.contains(temp)) {
            					 used.add(temp);
            					 queue.add(movesToChessboardClone(matrix));
            				 }
            				 movesToChessboardSwap(matrix, x, y, true);
        				 }
        				 
        				 if(movesToChessboardOptimized(board, x, y, false)) {
        					 movesToChessboardSwap(matrix, x, y, false);
            				 String temp = Arrays.deepToString(matrix);
            				 if(temp.equals(goal1) || temp.equals(goal2))
            					 return moves;
            				 if(!used.contains(temp)) {
            					 used.add(temp);
            					 queue.add(movesToChessboardClone(matrix));
            				 }
            				 movesToChessboardSwap(matrix, x, y, false);
        				 }
        			 }
        		 }
        	 }
         }
         
         
         return -1;
     }
     
     private boolean movesToChessboardOptimized(int[][] board, int x, int y, boolean row) {
    	 int n = board.length;
    	 for(int i=0; i<n; i++) {
			 int one = row ? board[x][i] : board[i][x];
			 int other = row ? board[y][i] : board[i][y];
			 if(one != (other ^ 1))
				 return false;
		 }
    	 return true;
     }
     
     private int[][] movesToChessboardClone(int[][] board){
    	 int n = board.length;
    	 int[][] matrix = new int[n][n];
    	 for(int i=0; i<n; i++) {
    		 for(int j=0; j<n; j++) {
    			 matrix[i][j] = board[i][j];
    		 }
    	 }
    	 return matrix;
     }
     
     private void movesToChessboardSwap(int[][] board, int i, int j, boolean row) {
    	 if(row) {
    		 for(int a=0; a<board.length; a++) {
        		 int temp = board[i][a];
        		 board[i][a] = board[j][a];
        		 board[j][a] = temp;
        	 }
    	 }
    	 else {
    		 for(int a=0; a<board.length; a++) {
    			 int temp = board[a][i];
    			 board[a][i] = board[a][j];
    			 board[a][j] = temp;
    		 }
    	 }
     }
     
     private String movesToChessboardGoal(int n, boolean b) {
    	 int[][] board = new int[n][n];
    	 for(int i=0; i<n; i++) {
    		 for(int j=0; j<n; j++) {
    			 if(b)
    				 board[i][j] = 1;
    			 else
    				 board[i][j] = 0;
    			 b = !b;
    		 }
    		 if((n & 1) == 0)
    			 b = !b;
    	 }
    	 return Arrays.deepToString(board);
     }
     
     private boolean  movesToChessboardPreCheck(int[][] board) {
    	 int n = board.length;
    	 int one = 0;
    	 int zero = 0;
    	 for(int i=0; i<n; i++)
    		 for(int j=0; j<n; j++) {
    			 if(board[i][j] == 0)
    				 zero++;
    			 else
    				 one++;
    		 }
    	 return Math.abs(one - zero) <= 1;
     }
     
     public int numRabbits(int[] answers) {
         int[] bucket = new int[1000];
         for(int i=0; i<answers.length; i++) {
        	 bucket[answers[i]]++;
         }
         int rabbits = 0;
         for(int i=0; i<1000; i++) {
        	int counter = bucket[i];
        	int mod = counter % (i+1);
        	int div = counter / (i+1);
        	if(mod == 0) {
        		rabbits += counter;
        	}
        	else {
        		rabbits += (div + 1) * (i+1);
        	}
         }
    	 return rabbits;
     }
     
     
     
     public int orderOfLargestPlusSign(int N, int[][] mines) {
         int[][] grid = new int[N][N];
         for(int[] mine : mines)
        	 grid[mine[0]][mine[1]] = 1;
         
         int[][] up = new int[N][N];
         int[][] down = new int[N][N];
         int[][] left = new int[N][N];
         int[][] right = new int[N][N];
         
         for(int i=0; i<N; i++) {
        	 for(int j=0; j<N-1; j++) {
        		 if(grid[j][i] == 1)
        			 continue;
        		 up[j+1][i] += 1 + up[j][i];
        	 }
         }
         for(int i=0; i<N; i++) {
        	 for(int j=N-1; j>0; j--) {
        		 if(grid[j][i] == 1)
        			 continue;
        		 down[j-1][i] += 1 + down[j][i];
        	 }
         }
         
         for(int i=0; i<N; i++) {
        	 for(int j=0; j<N-1; j++) {
        		 if(grid[i][j] == 1)
        			 continue;
        		 left[i][j+1] += 1 + left[i][j];
        	 }
         }
         for(int i=0; i<N; i++) {
        	 for(int j=N-1; j>0; j--) {
        		 if(grid[i][j] == 1)
        			 continue;
        		 right[i][j-1] += 1 + right[i][j];
        	 }
         }
         int length = 0;
         
         for(int i=0; i<N; i++) {
        	 for(int j=0; j<N; j++) {
        		 if(grid[i][j] == 1)
        			 continue;
        		 int temp = Math.min(left[i][j], Math.min(right[i][j], Math.min(up[i][j], down[i][j])));
        		 length = Math.max(length, temp+1);
        	 }
         }
         
    	 return length;
     }
     
     
     public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
         Map<Integer, List<int[]>> graph = new HashMap<>();
         for(int[] flight : flights) {
        	 int s = flight[0];
        	 int d = flight[1];
        	 int p = flight[2];
        	 if(!graph.containsKey(s))
        		 graph.put(s, new ArrayList<>());
        	 graph.get(s).add(new int[] {d, p});
         }
         int[] min = new int[1];
         min[0] = Integer.MAX_VALUE;
    	 int ans = findCheapestPriceHelp(graph, K+1, src, dst, 0, new HashMap<>(), min);
    	 if(ans == Integer.MAX_VALUE)
    		 return -1;
    	 return ans;
     }
     
     private int findCheapestPriceHelp(Map<Integer, List<int[]>> graph, int stop, int point, int dst, int total, Map<String, Integer> memoization, int[] min) {
    	 if(point == dst) {
    		 min[0] = Math.min(total, min[0]);
    		 return total;
    	 }
    	 if(stop == 0)
    		 return Integer.MAX_VALUE;
    	 if(total > min[0])
    		 return Integer.MAX_VALUE;
    	 int price = Integer.MAX_VALUE;
    	 List<int[]> list = graph.get(point);
    	 if(list == null)
    		 return price;
    	 String key = point+","+stop;
    	 if(memoization.containsKey(key)) {
    		 int temp = memoization.get(key);
    		 if(total >= temp)
    		 return price;
    	 }
    	 memoization.put(key, total);
    	 for(int[] adj : list) {
    		 int v = adj[0];
    		 int p = adj[1];
    		 price = Math.min(price, findCheapestPriceHelp(graph, stop-1, v, dst, total+p, memoization, min));
    	 }
    	 return price;
     }
     
     public int[] kthSmallestPrimeFraction(int[] A, int K) {
         PriorityQueue<int[]> queue = new PriorityQueue<>(new kthSmallestPrimeFractionSorter());
         int n = A.length;
    	 for(int i=0; i<n; i++) {
    		 int son = A[i];
    		 int mon = A[n-1];
    		 queue.add(new int[] {son, mon, i, n-1});
    	 }
    	 int[] answer = new int[2];
    	 while(!queue.isEmpty() && K > 0) {
    		 int[] array = queue.poll();
    		 answer[0] = array[0];
    		 answer[1] = array[1];
    		 int left = array[2];
    		 int right = array[3];
    		 if(right > left) {
    			 queue.add(new int[] {array[0], A[right-1], left, right-1});
    		 }
    		 K--;
    	 }
    	 
    	 return answer;
     }
     
     class kthSmallestPrimeFractionSorter implements Comparator<int[]> {
		@Override
		public int compare(int[] o1, int[] o2) {
			return o1[0] * o2[1] - o1[1] * o2[0];
		}
    	 
     }
     
     
     public int rotatedDigits(int N) {
         Set<Integer> used = new HashSet<>();
    	 Queue<Integer> queue = new LinkedList<>();
    	 int[] numbers = {0,1,2,5,6,8,9};
    	 queue.add(0);
    	 used.add(0);
    	 int counter = 0;
    	 while(!queue.isEmpty()) {
    		 int size = queue.size();
    		 for(int i=0; i<size; i++) {
    			 int cur = queue.poll();
    			 for(int factor : numbers) {
    				 int rotate = cur * 10 + factor;
    				 if(rotate > N)
    					 continue;
    				 if(used.contains(rotate))
    					 continue;
    				 boolean valid = rotatedDigitsValid(rotate);
    				 used.add(rotate);
    				 if(valid)
    					 counter++;
    				 queue.add(rotate);
    				 System.out.println(rotate);
    			 }
    		 }
    	 }
    	 return counter;
     }
     
     
     private boolean rotatedDigitsValid(int num) {
    	 while(num != 0) {
    		 int digit = num % 10;
    		 if(!(digit == 0 || digit == 1 || digit == 8))
    			 return true;
    		 num /= 10;
    	 }
    	 return false;
     }
     
     
     
     public boolean escapeGhosts(int[][] ghosts, int[] target) {
    	 int man_path = Math.abs(target[0]) + Math.abs(target[1]);
    	 for(int[] pos : ghosts) {
    		 int ghost_path = Math.abs(target[0] - pos[0]) + Math.abs(target[1] - pos[1]);
    		 if(ghost_path <= man_path)
    			 return false;
    	 }
    	 return true;
     }
     
     
     
     
     public void gameOfLife(int[][] board) {
         int m = board.length, n = board[0].length;
         int[][] preDir = {{-1,-1}, {-1,0},{0,-1},{1,-1}};
         int[][] afterDir = {{-1,1}, {0,1}, {1,1}, {1,0}};
         
         for(int i=0; i<n; i++) {
        	 for(int j=0; j<m; j++) {
        		 int original = board[j][i];
        		 int counter = 0;
        		 
        		 for(int[] dir : preDir) {
        			 int x = j + dir[0];
        			 int y = i + dir[1];
        			 if(x<0 || x>=m || y<0 || y>=n)
        				 continue;
        			 if(board[x][y] > 1)
        				 counter++;
        		 }
        		 for(int[] dir : afterDir) {
        			 int x = j + dir[0];
        			 int y = i + dir[1];
        			 if(x<0 || x>=m || y<0 || y>=n)
        				 continue;
        		     counter += board[x][y];
        		 }
        		 
        		 if(original == 1) {
        			 if(counter < 2)
        				 board[j][i] = 2;
        			 else if(counter == 2 || counter == 3)
        				 board[j][i] = 3;
        			 else if(counter > 3)
        				 board[j][i] = 2;
        		 }
        		 else {
        			 if(counter == 3)
        				 board[j][i] = 1;
        			 else
        				 board[j][i] = 0;
        		 }
        		 
        		 
        	 }
         }
         for(int i=0; i<m; i++)
        	 System.out.println(Arrays.toString(board[i]));
         
         for(int i=0; i<m; i++)
        	 for(int j=0; j<n; j++) {
        		 if(board[i][j] == 2)
        			 board[i][j] = 0;
        		 else if(board[i][j] == 3)
        			 board[i][j] = 1;
        	 }
     }
     
     
     
     public boolean isPossible(int[] nums) {
    	 if(nums.length < 3)
    		 return false;
         Map<Integer, PriorityQueue<Integer>> map = new HashMap<>();
         TreeSet<Integer> set = new TreeSet<>();
         map.put(nums[0]-1, new PriorityQueue<>());
         set.add(nums[0]-1);
         for(int num : nums) {
        	 if(!map.containsKey(num))
        		 map.put(num, new PriorityQueue<>());
        	 int pre = set.lower(num);
        	 set.add(num);
        	 PriorityQueue<Integer> preQueue = map.get(pre);
        	 PriorityQueue<Integer> curQueue = map.get(num);
        	 if(pre + 1 != num) {
        		 curQueue.add(1);
        		 continue;
        	 }
        	 if(preQueue.isEmpty()) {
        		 curQueue.add(1);
        		 continue;
        	 }
        	 int shortest = preQueue.poll();
        	 curQueue.offer(shortest+1);
         }
         for(Integer num : map.keySet()) {
        	 PriorityQueue<Integer> queue = map.get(num);
        	 if(queue.isEmpty())
        		 continue;
        	 int shortest = queue.peek();
        	 if(shortest < 3)
        		 return false;
         }
    	 return true;
     }
     
     
     public int rob(int[] nums) {
         int n = nums.length;
         if(n == 0)
             return 0;
         if(n == 1)
             return nums[0];
         int profit1 = rob(nums,0,n-2);
         int profit2 = rob(nums,1,n-1);
         return Math.max(profit1, profit2);
     }
     
     public int rob(int[] nums, int start, int end) {
         int no=0,yes=0;
        for(int i=start; i<=end; i++){
            int n = nums[i];
     	   int temp = no;
     	   no=Math.max(no, yes);
     	   yes=temp+n;
        }
        
        return Math.max(yes, no);
     }
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     


	public static void main(String[] args) {
		 
	}

}
