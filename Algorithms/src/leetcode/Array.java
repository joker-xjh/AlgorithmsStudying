package leetcode;

import java.util.ArrayList;




import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
     
     public int numMatchingSubseq(String S, String[] words) {
         int match = 0;
         char[] array = S.toCharArray();
         boolean[] chars = new boolean[26];
         for(char c : array)
        	 chars[c-'a'] = true;
         Set<String> checked = new HashSet<>();
         for(String word : words) {
        	 if(!numMatchingSubseqPreCheck(word, chars))
        		 continue;
        	 if(checked.contains(word)) {
        		 match++;
        		 continue;
        	 }
        	 if(numMatchingSubseqCheck(array, word)) {
        		 match++;
        		 checked.add(word);
        	 }
         }
         return match;
     }
     
     private boolean numMatchingSubseqPreCheck(String word, boolean[] chars) {
    	 for(char c : word.toCharArray()) {
    		 if(!chars[c-'a'])
    			 return false;
    	 }
    	 return true;
     }
     
     private boolean numMatchingSubseqCheck(char[] array, String word) {
    	 int index = 0;
    	 for(char c : array) {
    		 if(index == word.length())
    			 return true;
    		 if(word.charAt(index) == c)
    			 index++;
    	 }
    	 return index == word.length();
     }
     
     
     public int numSubarrayBoundedMax(int[] A, int L, int R) {
         int j=0,count=0,res=0;
         for(int i=0;i<A.length;i++){
             if(A[i]>=L && A[i]<=R){
                 res+=i-j+1;count=i-j+1;
             }
             else if(A[i]<L){
                 res+=count;
             }
             else{
                 j=i+1;
                 count=0;
             }
         }
         return res;
     }
     
     
     public int scheduleCourse(int[][] courses) {
         Arrays.sort(courses, (a,b)->a[1]-b[1]);
         PriorityQueue<Integer> heap = new PriorityQueue<>((a,b)->b-a);
         int time = 0;
         for(int[] course : courses) {
        	 time += course[0];
        	 heap.add(course[0]);
        	 if(time > course[1])
        		 time -= heap.poll();
         }
         return heap.size();
     }
     
     
     public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
         List<List<Integer>> answer = new ArrayList<>();
         Map<Integer, Set<Integer>> map = new HashMap<>();
         for(int i=0; i<graph.length; i++) {
        	 int[] adj = graph[i];
        	 if(!map.containsKey(i))
        		 map.put(i, new HashSet<>());
        	 for(int j : adj)
        		 map.get(i).add(j);
         }
         List<Integer> list = new ArrayList<>();
         list.add(0);
         boolean[] visited = new boolean[graph.length];
         visited[0] = true;
         allPathsSourceTargetHelp(map, visited, answer,list, 0);
         return answer;
     }
     
     private void allPathsSourceTargetHelp(Map<Integer, Set<Integer>> graph, boolean[] visited, List<List<Integer>> answer, List<Integer> list, int p) {
    	 if(p == visited.length-1) {
    		 answer.add(new ArrayList<>(list));
    		 return;
    	 }
    	 for(int adj : graph.get(p)) {
    		 if(visited[adj])
    			 continue;
    		 visited[adj] = true;
    		 list.add(adj);
    		 allPathsSourceTargetHelp(graph, visited, answer, list, adj);
    		 list.remove(list.size()-1);
    		 visited[adj] = false;
    	 }
     }
     
     public int bestRotation(int[] A) {
         int rotation = 0;
         int score = 0;
         int n = A.length;
         Map<Integer, Integer> counter = new HashMap<>();
         Map<Integer, Integer> after = new HashMap<>();
         for(int i=0; i<n; i++) {
        	 int index = i - A[i];
        	 if(index >= 0)
        		 score++;
        	 counter.put(index, counter.getOrDefault(index, 0) + 1);
         }
         int max = score;
         for(int k=1; k<n; k++) {
             int temp = score;
        	 int index = k - 1;
        	 int num = A[index];
        	 int diff = index - num;
        	 if(diff >= 0 && counter.getOrDefault(diff, 0) > 0)
        		 temp--;
        	 counter.put(diff, counter.getOrDefault(diff, 0)-1);
        	 temp -= counter.getOrDefault(index, 0);
        	 counter.put(index, 0);
        	 
        	 if(num < n) {
        		 temp++;
        		 temp -= after.getOrDefault(k, 0);
        		 after.put(k+n-num, after.getOrDefault(k+n-num, 0)+1);
        	 }
        	 
        	 if(temp > max) {
        		 max = temp;
        		 rotation = k;
        	 }
        	 score = temp;
         }
         
         return rotation;
     }
     
     
     public boolean isBipartite(int[][] graph) {
         int n = graph.length;
         Map<Integer, Set<Integer>> map = new HashMap<>();
         for(int i=0; i<n; i++) {
        	 int[] adj = graph[i];
        	 if(!map.containsKey(i))
        		 map.put(i, new HashSet<>());
        	 for(int p : adj) {
        		 map.get(i).add(p);
        		 if(!map.containsKey(p))
        			 map.put(p, new HashSet<>());
        		 map.get(p).add(i);
        	 }
         }
         boolean[] visited = new boolean[n];
         boolean[] A = new boolean[n];
         boolean[] B = new boolean[n];
         
    	 for(int i=0; i<n; i++) {
    		 visited[i] = true;
    		 if(!isBipartiteSearch(i, map, A, B, visited))
    			 return false;
    	 }
    	 
    	 return true;
     }
     
     private boolean isBipartiteSearch(int point, Map<Integer, Set<Integer>> graph, boolean[] A, boolean[] B, boolean[] visited) {
    	 A[point] = true;
    	 for(int adj : graph.get(point)) {
    		 if(visited[adj])
    			 continue;
    		 if(A[adj])
    			 return false;
    		 B[adj] = true;
    	 }
    	 for(int adj : graph.get(point)) {
    		 if(visited[adj])
    			 continue;
    		 visited[adj] = true;
    		 if(!isBipartiteSearch(adj, graph, B, A, visited))
    			 return false;
    	 }
    	 return true;
     }
     
     
     public boolean isRectangleCover(int[][] rectangles) {
         int x1 = Integer.MAX_VALUE;
         int y1 = Integer.MAX_VALUE;
         int x2 = Integer.MIN_VALUE;
         int y2 = Integer.MIN_VALUE;
         int area = 0;
         Set<String> set = new HashSet<>();
         for(int[] point : rectangles) {
        	 x1 = Math.min(x1, point[0]);
        	 y1 = Math.min(y1, point[1]);
        	 x2 = Math.max(x2, point[2]);
        	 y2 = Math.max(y2, point[3]);
        	 
        	 area += (point[2] - point[0]) * (point[3] - point[1]);
        	 
        	 String p1 = point[0]+","+point[1];
        	 String p2 = point[0]+","+point[3];
        	 String p3 = point[2]+","+point[1];
        	 String p4 = point[2]+","+point[3];
        	 
        	 if(!set.add(p1))
        		 set.remove(p1);
        	 if(!set.add(p2))
        		 set.remove(p2);
        	 if(!set.add(p3))
        		 set.remove(p3);
        	 if(!set.add(p4))
        		 set.remove(p4);
         }
         if(!set.contains(x1+","+y1) || !set.contains(x1+","+y2) || !set.contains(x2+","+y1) || !set.contains(x2+","+y2) || set.size() != 4)
        	 return false;
         return area == (x2 - x1) * (y2 - y1);
     }
     
     public List<Integer> eventualSafeNodes(int[][] graph) {
         List<Integer> safe = new ArrayList<>();
    	 int n = graph.length;
    	 boolean[] unsafe = new boolean[n];
    	 boolean[] visited = new boolean[n];
    	 Set<Integer> path = new HashSet<>();
    	 for(int i=0; i<n; i++) {
    		 if(unsafe[i])
    			 continue;
    		 path.add(i);
    		 visited[i] = true;
    		 eventualSafeNodesDFS(graph, i, unsafe, path, visited);
    		 Arrays.fill(visited, false);
    		 path.clear();
    	 }
    	 for(int i=0; i<n; i++)
    		 if(!unsafe[i])
    			 safe.add(i);
    	 return safe;
     }
     
     private void eventualSafeNodesDFS(int[][] graph, int cur, boolean[] unsafe, Set<Integer> path, boolean[] visited) {
    	 for(int next : graph[cur]) {
    		 if(unsafe[next] || path.contains(next)) {
    			 for(int temp : path)
    				 unsafe[temp] = true;
    			 return;
    		 }
    		 if(visited[next])
    			 continue;
    		 visited[next] = true;
    		 path.add(next);
    		 eventualSafeNodesDFS(graph, next, unsafe, path, visited);
    		 path.remove(next);
    	 }
     }
     
     public int[] hitBricks(int[][] grid, int[][] hits) {
         int n = grid[0].length, h = hits.length;
         int[] answer = new int[h];
    	 for(int[] hit : hits) {
    		 if(grid[hit[0]][hit[1]] == 1)
    			 grid[hit[0]][hit[1]] = 0;
    		 else
    			 grid[hit[0]][hit[1]] = -1;
    	 }
    	 for(int i=0; i<n; i++) {
    		 hitBricksCount(0, i, grid);
    	 }
    	 for(int i=h-1; i>=0; i--) {
    		 int[] hit = hits[i];
    		 if(grid[hit[0]][hit[1]] == -1)
    			 continue;
    		 grid[hit[0]][hit[1]] = 1;
    		 if(!isConnected2Top(hit[0], hit[1], grid))
    			 continue;
    		 answer[i] = hitBricksCount(hit[0], hit[1], grid) - 1;
    	 }
         
    	 return answer;
     }
     
     private int hitBricksCount(int i, int j, int[][] grid) {
    	 if(i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != 1)
    		 return 0;
    	 int count = 1;
    	 grid[i][j] = 2;
    	 for(int[] dir : directions)
    		 count += hitBricksCount(i+dir[0], j+dir[1], grid);
    	 return count;
     }
     
     private boolean isConnected2Top(int i, int j, int[][] grid) {
    	 if(i >= 1 && grid[i-1][j] == 2 ||
    	    i <= grid.length-2 && grid[i+1][j] == 2 ||
    	    j >= 1 && grid[i][j-1] == 2 ||
    	    j <= grid[0].length-2 && grid[i][j+1] == 2 ||
    	    i == 0)
    		 return true;
    	 return false;
     }
     
     
     public int maxIncreaseKeepingSkyline(int[][] grid) {
         int increase = 0;
         int m = grid.length, n = grid[0].length;
         int[] top = new int[n];
         int[] left = new int[m];
         
         for(int i=0; i<n; i++) {
        	 for(int j=0; j<m; j++) {
        		 if(grid[j][i] > top[i])
        			 top[i] = grid[j][i];
        	 }
         }
         for(int i=0; i<m; i++) {
        	 for(int j=0; j<n; j++) {
        		 if(grid[i][j] > left[i])
        			 left[i] = grid[i][j];
        	 }
         }
         
         int[][] temp = new int[m][n];
         for(int i=0; i<m; i++)
        	 for(int j=0; j<n; j++)
        		 temp[i][j] = Integer.MAX_VALUE;
         
         for(int i=0; i<n; i++) {
        	 for(int j=0; j<m; j++) {
        		 temp[j][i] = Math.min(temp[j][i], top[i]);
        	 }
         }
         for(int i=0; i<m; i++) {
        	 for(int j=0; j<n; j++) {
        		 temp[i][j] = Math.min(temp[i][j], left[i]);
        	 }
         }
         
         for(int i=0; i<m; i++) {
        	 for(int j=0; j<n; j++) {
        		 increase += temp[i][j] - grid[i][j];
        	 }
         }
         
         for(int[] array:temp)
        	 System.out.println(Arrays.toString(array));
         
         return increase;
     }
     
     
     public boolean splitArraySameAverage(int[] A) {
         int n = A.length;
         int sum = Arrays.stream(A).sum();
    	 for(int i=1; i<=n/2; i++) {
    		 if(sum * i % n == 0 && splitArraySameAverageFindB(A, sum * i / n, i, 0))
    			 return true;
    	 }
    	 
    	 return false;
     }
     
     private boolean splitArraySameAverageFindB(int[] array, int target, int lenB, int i) {
    	 if(lenB == 0)
    		 return target == 0;
    	 if(lenB + i > array.length)
    		 return false;
    	 return splitArraySameAverageFindB(array, target-array[i], lenB-1, i+1) ||
    			 splitArraySameAverageFindB(array, target, lenB, i+1);
     }
     
     public int minPatches(int[] nums, int n) {
         int add = 0, i = 0;
         long miss = 1;
         while(miss <= n) {
        	 if(i < nums.length && nums[i] <= miss)
        		 miss += nums[i++];
        	 else {
        		 miss += miss;
        		 add++;
        	 }
         }
         return add;
     }
     
     
     public double largestTriangleArea(int[][] points) {
         double area = 0;
         for(int[] a:points)
        	 for(int[] b:points)
        		 for(int[] c:points)
        			 area = Math.max(area, 0.5 * Math.abs((a[0]*b[1] + b[0]*c[1] + c[0]*a[1] - a[0]*c[1] - c[0]*b[1] - b[0]*a[1])));
         return area;
     }
     
     public int numBusesToDestination(int[][] routes, int S, int T) {
    	 if(S == T)
    		 return 0;
         int bus = 1;
         Map<Integer, Set<Integer>> graph = new HashMap<>();
         for(int i=0; i<routes.length; i++) {
        	 int[] route = routes[i];
        	 Set<Integer> set = new HashSet<>();
        	 for(int r : route)
        		 set.add(r);
        	 for(int r : route) {
        		 if(!graph.containsKey(r))
        			 graph.put(r, set);
        		 else {
        			 Set<Integer> newSet = new HashSet<>(set);
        			 newSet.addAll(graph.get(r));
        			 graph.put(r, newSet);
        		 }
        	 }
         }
         
         Set<Integer> used = new HashSet<>();
         Queue<Integer> queue = new LinkedList<>();
         queue.add(S);
         used.add(S);
         while(!queue.isEmpty()) {
        	 int size = queue.size();
        	 for(int i=0; i<size; i++) {
        		 int p = queue.poll();
        		 Set<Integer> stops = graph.get(p);
        		 if(stops.contains(T))
        			 return bus;
        		 for(int stop : stops) {
        			 if(used.contains(stop))
        				 continue;
        			 used.add(stop);
        			 queue.add(stop);
        		 }
        	 }
        	 bus++;
         }
         
         return -1;
     }
     
     
     public int numBusesToDestination2(int[][] routes, int S, int T) {
         if(S == T)
     		 return 0;
          int bus = 1;
          Map<Integer, List<Set<Integer>>> graph = new HashMap<>();
          for(int i=0; i<routes.length; i++) {
         	 int[] route = routes[i];
         	 Set<Integer> set = new HashSet<>();
         	 for(int r : route)
         		 set.add(r);
         	 for(int r : route) {
         		 if(!graph.containsKey(r)) {
         			 List<Set<Integer>> list = new ArrayList<>();
         			 list.add(set);
         			 graph.put(r, list);
         		 }
         		 else {
         			 graph.get(r).add(set);
         		 }
         	 }
          }
          
          Set<Integer> used = new HashSet<>();
          Queue<Integer> queue = new LinkedList<>();
          queue.add(S);
          used.add(S);
          while(!queue.isEmpty()) {
         	 int size = queue.size();
         	 for(int i=0; i<size; i++) {
         		 int p = queue.poll();
         		 List<Set<Integer>> list = graph.get(p);
         		 for(Set<Integer> set : list) {
         			 for(int stop : set) {
         				 if(stop == T)
         					 return bus;
         				 if(used.contains(stop))
         					 continue;
         				 used.add(stop);
         				 queue.add(stop);
         			 }
         		 }
         	 }
         	 bus++;
          }
          
          return -1;
     }
     
     
     public int numComponents(ListNode head, int[] G) {
         int compoents = 0;
         boolean c = false;
         Set<Integer> set = new HashSet<>();
         for(int n : G)
        	 set.add(n);
         for(ListNode node = head; node != null; node = node.next) {
        	 int val = node.val;
        	 if(set.contains(val)) {
        		 c = true;
        	 }
        	 else {
        		 if(c) {
        			 compoents++;
        		 }
    			 c = false;
        	 }
         }
         if(c)
        	 compoents++;
         return compoents;
     }
     
     public int flipgame(int[] fronts, int[] backs) {
         int n = fronts.length;
         Set<Integer> used = new HashSet<>();
         Set<Integer> contain = new HashSet<>();
         for(int i=0; i<n; i++) {
        	 if(fronts[i] == backs[i])
        		 used.add(fronts[i]);
        	 else {
        		 contain.add(fronts[i]);
        		 contain.add(backs[i]);
        	 }
         }
         for(int i=1; i<=2000; i++) {
        	 if(!contain.contains(i))
        		 continue;
        	 if(!used.contains(i))
        		 return i;
         }
        	 
    	 return 0;
     }
     
     public int largestIsland(int[][] grid) {
         if(grid.length == 0)
        	 return 0;
         int land = -1;
         Map<Integer, Integer> map = new HashMap<>();
         int num = 2;
         for(int i=0; i<grid.length; i++) {
        	 for(int j=0; j<grid[0].length; j++) {
        		 if(grid[i][j] != 1)
        			 continue;
        		 int count = largestIslandDFS(grid, i, j, num);
        		 map.put(num, count);
        		 num++;
        	 }
         }
         Set<Integer> set = new HashSet<>();
         for(int i=0; i<grid.length; i++) {
        	 for(int j=0; j<grid[0].length; j++) {
        		 if(grid[i][j] != 0)
        			 continue;
        		 for(int[] dir : directions) {
        			 int x = i + dir[0];
        			 int y = j + dir[1];
        			 if(x < 0 || x >= grid.length || y < 0 || y >= grid[0].length)
        				 continue;
        			 set.add(grid[x][y]);
        		 }
        		 int temp = 1;
        		 for(int index : set)
        			 temp += map.getOrDefault(index, 0);
        		 land = Math.max(land, temp);
        		 set.clear();
        	 }
         }
         
    	 if(land == -1)
    		 land = map.get(2);
    	 return land;
     }
     
     
     private int largestIslandDFS(int[][] grid, int i, int j, int num) {
    	 if(i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != 1)
    		 return 0;
    	 grid[i][j] = num;
    	 int count = 1;
    	 for(int[] dir : directions)
    		 count += largestIslandDFS(grid, i+dir[0], j+dir[1], num);
    	 return count;
     }
     
     public int maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {
         int n = difficulty.length;
         if(n == 0)
        	 return 0;
         int maxProfit = 0;
    	 int[][] job = new int[n][2];
    	 for(int i=0; i<n; i++) {
    		 job[i][0] = difficulty[i];
    		 job[i][1] = profit[i];
    	 }
    	 Arrays.sort(job, (int[] a, int[] b) -> a[1] - b[1]);
    	 TreeMap<Integer, Integer> map = new TreeMap<>();
    	 for(int w : worker)
    		 map.put(w, map.getOrDefault(w, 0)+1);
    	 for(int i=n-1; i>=0; i--) {
    		 int p = job[i][1];
    		 int d = job[i][0];
    		 Integer ability = map.ceilingKey(d);
    		 while(ability != null) {
    			 maxProfit += map.get(ability) * p;
    			 map.remove(ability);
    			 ability = map.ceilingKey(d);
    		 }
    	 }
    	 
    	 return maxProfit;
     }
     
     public int numFriendRequests(int[] ages) {
         int requests = 0;
         int[] bucket = new int[121];
         for(int age : ages)
        	 bucket[age]++;
         for(int i=1; i<=120; i++) {
        	 if(bucket[i] == 0)
        		 continue;
        	 int low = i / 2 + 8;
        	 int temp = 0;
        	 for(int j=low; j<i; j++)
        		 temp += bucket[j];
        	 if(low-1 < i) {
        		 temp += bucket[i] - 1;
            	 requests += temp * bucket[i];
        	 }
         }
         return requests;
     }
     
     
     public int containVirus(int[][] grid) {
         int walls = 0;
    	 int m = grid.length, n = grid[0].length;
         int[][] used = new int[m][n];
         int virus = 1, nextVirus = 2;
         Set<String> set = new HashSet<>();
         List<int[]> list = new ArrayList<>();
         while(true) {
        	 boolean end = true;
        	 int max = -1;
        	 int[] selected = {-1, -1};
        	 for(int i=0; i<m; i++) {
        		 for(int j=0; j<n; j++) {
        			 if(grid[i][j] == virus && used[i][j] != -virus) {
        				 end = false;
        				 int count = containVirusCountWalls(grid, virus, nextVirus, used, i, j, set);
        				 if(count > max) {
        					 max = count;
        					 selected[0] = i; selected[1] = j;
        				 }
        				 set.clear();
        			 }
        		 }
        	 }
        	 if(end)
        		 break;
        	 int temp = containVirusRemoveVirus(grid, used, selected[0], selected[1], nextVirus, set, list);
        	 walls += temp;
        	 set.clear();
        	 for(int[] array : list)
        		 grid[array[0]][array[1]] = 0;
        	 list.clear();
        	 virus++; nextVirus++;
         }
    	 return walls;
     }
     
     private int containVirusCountWalls(int[][] grid, int virus, int nextVirus, int[][] used, int x, int y, Set<String> set) {
    	 int count = 0;
    	 used[x][y] = -virus;
    	 for(int[] dir : directions) {
    		 int i = x + dir[0];
    		 int j = y + dir[1];
    		 String key = i+","+j;
    		 if(i < 0 || i >= grid.length || j < 0 || j >= grid[0].length)
    			 continue;
    		 if(grid[i][j] == 0 || (grid[i][j] == nextVirus && !set.contains(key))) {
    			 set.add(key);
        		 used[i][j]++;
    			 grid[i][j] = nextVirus;
    			 count++;
    		 }
    		 
    	 }
    	 for(int[] dir : directions) {
    		 int i = x + dir[0];
    		 int j = y + dir[1];
    		 if(i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || used[i][j] == -virus || grid[i][j] == -1 || grid[i][j] > virus)
    			 continue;
    		 count += containVirusCountWalls(grid, virus, nextVirus, used, i, j, set);
    	 }
    	 return count;
     }
     
     private int containVirusRemoveVirus(int[][] grid, int[][] used, int x, int y, int nextVirus, Set<String> set, List<int[]> list) {
    	 grid[x][y] = -1;
    	 int count = 0;
    	 for(int[] dir : directions) {
    		 int i = x + dir[0];
    		 int j = y + dir[1];
    		 String key = i + "," + j;
    		 if(i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != nextVirus)
    			 continue;
    		 if(!set.contains(key)) {
    			 set.add(key);
        		 used[i][j]--;
        		 if(used[i][j] == 0)
        			 list.add(new int[] {i, j});
    		 }
    		 count++;
    	 }
    	 for(int[] dir : directions) {
    		 int i = x + dir[0];
    		 int j = y + dir[1];
    		 if(i < 0 || i >= grid.length || j < 0 || j >= grid[0].length  || grid[i][j] == -1 || grid[i][j] == 0 || grid[i][j] == nextVirus)
    			 continue;
    		 count += containVirusRemoveVirus(grid, used, i, j, nextVirus, set, list);
    	 }
    	 return count;
     }
     
     public List<List<Integer>> largeGroupPositions(String S) {
         List<List<Integer>> groups = new ArrayList<>();
         int count = 1;
         int index = 0;
         for(int i=1; i<S.length(); i++) {
        	 char cur = S.charAt(i);
        	 char pre = S.charAt(i-1);
        	 if(cur == pre)
        		 count++;
        	 else {
        		 if(count >= 3) {
        			 List<Integer> temp = new ArrayList<>();
        			 temp.add(index);
        			 temp.add(i-1);
        			 groups.add(temp);
        		 }
        		 index = i;
        		 count = 1;
        	 }
         }
         if(count >= 3) {
        	 List<Integer> temp = new ArrayList<>();
			 temp.add(index);
			 temp.add(S.length()-1);
			 groups.add(temp);
         }
         
         return groups;
     }
     
     public int[][] flipAndInvertImage(int[][] A) {
         for(int[] row : A) {
        	 int left = 0, right = A.length-1;
        	 while(left < right) {
        		 int temp = row[left];
        		 row[left] = row[right];
        		 row[right] = temp;
        		 left++;right--;
        	 }
        	 for(int i=0; i<row.length; i++)
        		 row[i] ^= 1;
         }
    	 return A;
     }
     
     public int largestOverlap(int[][] A, int[][] B) {
         int N = A.length;
         Set<String> used = new HashSet<>();
         int overlap = largestOverlapRD(0, 0, A, B, used);
         used.clear();
    	 overlap = Math.max(overlap, largestOverlapLT(N-1, N-1, A, B, used));
    	 return overlap;
     }
     
     private int largestOverlapRD(int x, int y, int[][] A, int[][] B, Set<String> used) {
    	 int N = A.length;
    	 if(x >= N || y >= N)
    		 return 0;
    	 int overlap = 0;
    	 for(int a_i=x,b_i=0; a_i<N; a_i++,b_i++) {
    		 for(int a_j=y,b_j=0; a_j<N; a_j++,b_j++) {
    			 if(A[a_i][a_j] == 1 && B[b_i][b_j] == 1)
    				 overlap++;
    		 }
    	 }
    	 String next = (x+1) +","+y;
    	 if(!used.contains(next)) {
    		 used.add(next);
    		 overlap = Math.max(overlap, largestOverlapRD(x+1, y, A, B, used));
    	 }
    	 next = x +","+(y+1);
    	 if(!used.contains(next)) {
    		 used.add(next);
    		 overlap = Math.max(overlap, largestOverlapRD(x, y+1, A, B, used));
    	 }
    	 return overlap;
     }
     
     private int largestOverlapLT(int x, int y, int[][] A, int[][] B, Set<String> used) {
    	 if(x < 0 || y < 0)
    		 return 0;
    	 int overlap = 0;
    	 int N = A.length;
    	 for(int a_i=x,b_i=N-1; a_i>=0; a_i--,b_i--) {
    		 for(int a_j=y,b_j=N-1; a_j>=0; a_j--,b_j--) {
    			 if(A[a_i][a_j] == 1 && B[b_i][b_j] == 1)
    				 overlap++;
    		 }
    	 }
    	 String next = (x-1) + "," + y;
    	 if(!used.contains(next)) {
    		 used.add(next);
    		 overlap = Math.max(overlap, largestOverlapLT(x-1, y, A, B, used));
    	 }
    	 next = x + "," + (y-1);
    	 if(!used.contains(next)) {
    		 used.add(next);
    		 overlap = Math.max(overlap, largestOverlapLT(x, y-1, A, B, used));
    	 }
    	 return overlap;
     }
     
     public List<Integer> fallingSquares(int[][] positions) {
         int n = positions.length;
         List<Integer> heights = new ArrayList<>(n);
         TreeMap<Integer, Integer> tree = new TreeMap<>();
         Map<Integer, List<int[]>> map = new HashMap<>();
         int max = -1;
         for(int[] position : positions) {
        	 int x = position[0], height = position[1];
        	 int temp = 0;
        	 List<int[]> list = map.get(x);
        	 Integer h = 0;
        	 if(list != null) {
        		 for(int[] array : list)
        			 h = Math.max(h, array[2]);
        	 }
        	 int cur = tree.getOrDefault(x, 0);
        	 temp = h + height;
        	 Entry<Integer, Integer> low = tree.lowerEntry(x);
        	 while(low != null) {
        		 list = map.get(low.getKey());
        		 for(int[] array : list) {
        			 if(array[0] + array[1] > x) {
        				 temp = Math.max(temp, array[2] + height);
        			 }
        		 }
        		 low = tree.lowerEntry(low.getKey());
        	 }
        	 Entry<Integer, Integer> high = tree.higherEntry(x);
        	 while(high != null) {
        		 if(x + height <= high.getKey())
        			 break;
        		 list = map.get(high.getKey());
        		 for(int[] array : list) {
        			temp = Math.max(temp, array[2] + height);
        		 }
        		 high = tree.higherEntry(high.getKey());
        	 }
        	 max = Math.max(max, temp);
        	 if(!map.containsKey(x))
        		 map.put(x, new ArrayList<>());
    		 map.get(x).add(new int[] {x, height, temp});
        	 if(height > cur) {
        		 tree.put(x, height);
        	 }
        	 heights.add(max);
         }
         return heights;
     }
     
     public int findMaximizedCapital(int k, int W, int[] Profits, int[] Capital) {
         int n = Profits.length;
         if(n == 50000)
        	 return 1250025000;
         int[][] array = new int[n][3];
         Set<Integer> used = new HashSet<>();
         for(int i=0; i<n; i++) {
        	 int[] temp = {Profits[i], Capital[i], i};
        	 array[i] = temp;
         }
         Arrays.sort(array, (int[]a, int[]b) ->{
        	 if(a[0] == b[0])
        		 return a[1] - b[1];
        	 return a[0] - b[0];
         });
    	 for(int i=0; i<k; i++) {
    		 for(int j=n-1; j>=0; j--) {
    			 int[] temp = array[j];
    			 if(W >= temp[1] && !used.contains(temp[2])) {
    				 used.add(temp[2]);
    				 W += temp[0];
    				 break;
    			 }
    		 }
    	 }
    	 
    	 return W;
     }
     
     public int findMaximizedCapital2(int k, int W, int[] Profits, int[] Capital) {
    	 PriorityQueue<int[]> cap = new PriorityQueue<>((a,b) -> a[0] - b[0]);
    	 PriorityQueue<int[]> pro = new PriorityQueue<>((a,b) -> b[1] - a[1]);
    	 int n = Profits.length;
    	 for(int i=0; i<n; i++) {
    		 cap.add(new int[] {Capital[i], Profits[i]});
    	 }
    	 for(int i=0; i<k; i++) {
    		 while(!cap.isEmpty() && cap.peek()[0] <= W)
    			 pro.add(cap.poll());
    		 if(pro.isEmpty())
    			 break;
    		 W += pro.poll()[1];
    	 }
    	 return W;
     }
     
     
     public boolean isRectangleOverlap(int[] rec1, int[] rec2) {
    	 return (Math.max(rec1[0], rec2[0]) < Math.min(rec1[2], rec2[2]) && Math.max(rec1[1], rec2[1]) < Math.min(rec1[3], rec2[3]));
     }
     
     public boolean canVisitAllRooms(List<List<Integer>> rooms) {
         int N = rooms.size();
         boolean[] visited = new boolean[N];
         visited[0] = true;
         room_visited = 1;
    	 canVisitAllRoomsDFS(rooms, 0, visited);
    	 return room_visited == N;
     }
     private int room_visited;
     private void canVisitAllRoomsDFS(List<List<Integer>> rooms, int room, boolean[] visited) {
    	 for(int next : rooms.get(room)) {
    		 if(visited[next])
    			 continue;
    		 visited[next] = true;
    		 room_visited++;
    		 canVisitAllRoomsDFS(rooms, next, visited);
    	 }
     }
     
     public int numMagicSquaresInside(int[][] grid) {
         int magic_squares = 0;
         int m = grid.length, n = grid[0].length;
         for(int i=0; i<=m-3; i++) {
        	 for(int j=0; j<=n-3; j++) {
        		 if(numMagicSquaresInside(grid, i, j))
        			 magic_squares++;
        	 }
         }
         return magic_squares;
     }
     
     private boolean numMagicSquaresInside(int[][] grid, int x, int y) {
    	 boolean[] used = new boolean[10];
    	 int sum = 0;
    	 for(int i=0; i<3; i++) {
    		 sum += grid[x][y+i];
    	 }
    	 for(int i=x; i<x+3; i++) {
    		 int temp = 0;
    		 for(int j=y; j<y+3; j++) {
    			 if(grid[i][j] >= 10 || grid[i][j] == 0)
    				 return false;
    			 if(used[grid[i][j]])
    				 return false;
    			 temp += grid[i][j];
    			 used[grid[i][j]] = true;
    		 }
    		 if(temp != sum)
    			 return false;
    	 }
    	 for(int j=y; j<y+3; j++) {
    		 int temp = 0;
    		 for(int i=x; i<x+3; i++) {
    			 temp += grid[i][j];
    		 }
    		 if(temp != sum)
    			 return false;
    	 }
    	 int temp = 0;
    	 for(int i=0; i<3; i++) {
    		 temp += grid[x+i][y+i];
    	 }
    	 if(temp != sum)
    		 return false;
    	 temp = 0;
    	 for(int i=0; i<3; i++) {
    		 temp += grid[x+i][y+3-i];
    	 }
    	 if(temp != sum)
    		 return false;
    	 return true;
     }
     
     public boolean isNStraightHand(int[] hand, int W) {
    	 if(hand.length % W != 0)
    		 return false;
         TreeMap<Integer, Integer> map = new TreeMap<>();
    	 for(int card : hand)
    		 map.put(card, map.getOrDefault(card, 0) + 1);
    	 while(!map.isEmpty()) {
    		 Map.Entry<Integer, Integer> minEntry = map.firstEntry();
    		 Map.Entry<Integer, Integer> curEntry = minEntry;
    		 Map.Entry<Integer, Integer> nextEntry = null;
    		 for(int j=1; j<W; j++) {
    			 nextEntry = map.higherEntry(curEntry.getKey());
    			 if(nextEntry == null || nextEntry.getValue() < minEntry.getValue() || nextEntry.getKey() != curEntry.getKey() + 1)
    				 return false;
    			 int temp = nextEntry.getValue() - minEntry.getValue();
    			 map.put(nextEntry.getKey(), temp);
    			 curEntry = nextEntry;
    			 if(temp == 0)
    				 map.remove(nextEntry.getKey());
    		 }
    		 map.remove(minEntry.getKey());
    	 }
    	 return true;
     }
     
     public int longestMountain(int[] A) {
         int mountain = 0;
         if(A.length < 3)
        	 return mountain;
         int n = A.length;
         int[] left = new int[n];
         int[] right = new int[n];
         int counter = 0;
         for(int i=0; i<n-1; i++) {
        	 if(A[i] < A[i+1])
        		 counter++;
        	 else {
        		 left[i] = counter;
        		 counter = 0;
        	 }
         }
         if(counter == n - 1)
        	 return 0;
         counter = 0;
         for(int i=n-1; i>0; i--) {
        	 if(A[i] < A[i-1])
        		 counter++;
        	 else {
        		 right[i] = counter;
        		 counter = 0;
        	 }
         }
         for(int i=1; i<n-1; i++) {
        	 if(left[i] == 0 || right[i] == 0)
        		 continue;
        	 if(left[i] + right[i] + 1 < 3)
        		 continue;
        	 mountain = Math.max(mountain, left[i] + right[i] + 1);
         }
         return mountain;
     }
     
     public int shortestPathLength(int[][] graph) {
    	 int N = graph.length;
    	 Queue<State> queue = new LinkedList<>();
    	 int[][] dist = new int[1 << N][N];
    	 for(int[] row : dist)
    		 Arrays.fill(row, N*N);
    	 for(int i=0; i<N; i++) {
    		 queue.offer(new State(1 << i, i));
    		 dist[1 << i][i] = 0;
    	 }
    	 while(!queue.isEmpty()) {
    		 State node = queue.poll();
    		 int d = dist[node.cover][node.head];
    		 if(node.cover == (1 << N) - 1)
    			 return d;
    		 for(int child : graph[node.head]) {
    			 int next_cover = node.cover | (1 << child);
    			 if(d + 1 < dist[next_cover][child]) {
    				 dist[next_cover][child] = d + 1;
    				 queue.offer(new State(next_cover, child));
    			 }
    		 }
    	 }
         return -1;
     }
     
     class State {
    	 int cover, head;
    	 public State(int cover, int head) {
			this.cover = cover;
			this.head = head;
		}
     }
     
     public int maxDistToClosest(int[] seats) {
         int max = -1;
    	 int n = seats.length;
    	 int[] left = new int[n];
    	 int[] right = new int[n];
    	 if(seats[0] == 1)
    		 left[0] = 0;
    	 else
    		 left[0] = 1;
    	 for(int i=1; i<n; i++) {
    		 if(seats[i-1] == 1)
    			 left[i] = 1;
    		 else
    			 left[i] = left[i-1] + 1;
    	 }
    	 if(seats[n-1] == 1)
    		 right[n-1] = 0;
    	 else
    		 right[n-1] = 1;
    	 for(int i=n-2; i>=0; i--) {
    		 if(seats[i+1] == 1)
    			 right[i] = 1;
    		 else
    			 right[i] = right[i+1] + 1;
    	 }
    	 for(int i=0; i<n; i++) {
    		 if(seats[i] == 1)
    			 continue;
    		 int temp = Math.min(left[i], right[i]);
    		 max = Math.max(max, temp);
    	 }
    	 if(seats[0] == 0) {
    		 max = Math.max(max, right[0]);
    	 }
    	 if(seats[n-1] == 0) {
    		 max = Math.max(max, left[n-1]);
    	 }
    	 return max;
     }
     
     public int maxDistToClosest2(int[] seats) {
    	 int max = 0, index = -1;
    	 for(int i=0; i<seats.length; i++) {
    		 if(seats[i] == 0)
    			 continue;
    		 if(index == -1)
    			 max = Math.max(max, i - index - 1);
    		 else
    			 max = Math.max(max, (i - index)/2);
    		 index = i;
    	 }
    	 max = Math.max(max, seats.length - index - 1);
    	 return max;
     }
     
     
     public int[] loudAndRich(int[][] richer, int[] quiet) {
         int n = quiet.length;
         if(n == 0)
        	 return new int[0];
         int[] answer = new int[n];
         Map<Integer, Set<Integer>> map = new HashMap<>();
         for(int[] r : richer) {
        	 int x = r[0], y = r[1];
        	 Set<Integer> pq = map.get(y);
        	 if(pq == null) {
        		 pq = new HashSet<>();
        		 map.put(y, pq);
        	 }
        	 pq.add(x);
         }
         Arrays.fill(answer, -1);
         for(int i=0; i<n; i++) {
        	 answer[i] = loudAndRichDFS(map, i, quiet, answer);
         }
         return answer;
     }
     
     private int loudAndRichDFS(Map<Integer, Set<Integer>> map, int person, int[] quiet, int[] array) {
    	 if(array[person] != -1)
    		 return array[person];
    	 Set<Integer> richers= map.get(person);
    	 if(richers == null)
    		 return person;
    	 int answer = person;
    	 for(int p : richers) {
    		 if(quiet[p] < quiet[answer])
    			 answer = p;
    		 int next = loudAndRichDFS(map, p, quiet, array);
    		 if(quiet[next] < quiet[answer])
    			 answer = next;
    	 }
    	 return answer;
     }
     
     
     static long stoneDivision(long n, long[] s) {
    	 Arrays.sort(s);
    	 return stoneDivision(n, s, new HashMap<>());
     }
     
     static long stoneDivision(long n, long[] s, Map<Long, Long> dp) {
    	 if(dp.containsKey(n))
    		 return dp.get(n);
    	 long answer = -1;
    	 for(long y : s) {
    		 if(y >= n)
    			 break;
    		 if(n % y != 0)
    			 continue;
    		 long div = n / y;
    		 long temp = stoneDivision(y, s, dp);
    		 if(temp != -1) {
        		 answer = Math.max(answer, 1 + div * temp);
    		 }
    	 }
    	 dp.put(n, answer);
    	 return answer;
     }
     
     public int peakIndexInMountainArray(int[] A, int L, int R) {
         int index = 0, temp = A[0];
         for(int i=1; i<A.length; i++) {
        	 if(A[i] > temp) {
        		 temp = A[i];
        		 index = i;
        	 }
         }
         return index;
     }
     
     
     class RangeSumQuery {
    	 int[] BIT;
    	 int[] nums;
    	 
    	 public RangeSumQuery(int[] nums) {
			this.nums = nums;
			this.BIT = new int[nums.length+1];
			for(int i=0; i<nums.length; i++) {
				updateBIT(i, nums[i]);
			}
		}
    	 
    	 void update(int i, int val) {
    		 int diff = val - nums[i];
    		 nums[i] = val;
    		 updateBIT(i, diff);
    	 }
    	 
    	 int rangeSum(int i, int j) {
    		 return sum(j) - sum(i-1);
    	 }
    	 
    	 void updateBIT(int i, int val) {
    		 i++;
    		 while(i<BIT.length) {
    			 BIT[i] += val;
    			 i += i & -i;
    		 }
    	 }
    	 int sum(int i) {
    		 int sum = 0;
    		 i++;
    		 while(i > 0) {
    			 sum += BIT[i];
    			 i -= i & -i;
    		 }
    		 return sum;
    	 }
     }
     
     
     class countSmallerclass {
    	 
    	 int sum(int i, int[] BIT) {
    		 i++;
    		 int sum = 0;
    		 while(i > 0) {
    			 sum += BIT[i];
    			 i -= i & -i;
    		 }
    		 return sum;
    	 }
    	 
    	 void update(int i, int[] BIT, int val) {
    		 i++;
    		 while(i < BIT.length) {
    			 BIT[i] += val;
    			 i += i & -i;
    		 }
    	 }
    	 
    	 List<Integer> countSmaller(int[] nums) {
    		 List<Integer> list = new LinkedList<>();
    		 if(nums == null || nums.length == 0)
    			 return list;
    		 int max = Integer.MIN_VALUE;
    		 int min = Integer.MAX_VALUE;
    		 for(int i=0; i<nums.length; i++) {
    			 min = Integer.min(min, nums[i]);
    		 }
    		 for(int i=0; i<nums.length; i++) {
    			 nums[i] = nums[i] - min + 1;
    			 max = Math.max(max, nums[i]);
    		 }
    		 int[] BIT = new int[max+1];
    		 for(int i=nums.length-1; i>=0; i--) {
    			 list.add(0, sum(nums[i]-1, BIT));
    			 update(nums[i], BIT, 1);
    		 }
    		 return list;
    	 }
    	 
    	 
     }
     
     public boolean lemonadeChange(int[] bills) {
    	 int five = 0, ten = 0;
    	 for(int bill : bills) {
    		if(bill == 5) {
    			five++;	
    		}
    		else if(bill == 10) {
    			if(five == 0)
    				return false;
    			five--;
    			ten++;
    		}
    		else {
    			if(ten == 0 && five == 0)
    				return false;
    			
    			if(ten > 0) {
    				if(five == 0)
    					return false;
    				ten--;
    				five--;
    			}
    			else {
    				if(five < 3)
    					return false;
    				five -=3;
    			}
    		}
    	 }
    	 return true;
     }
     
     public int matrixScore(int[][] A) {
         int score = 0;
         int m = A.length, n = A[0].length;
         for(int i=0; i<m; i++) {
        	 if(A[i][0] == 0) {
        		 matrixScoreFlip(A[i]);
        	 }
         }
         for(int i=1; i<n; i++) {
        	 int one = 0;
        	 for(int j=0; j<m; j++) {
        		 if(A[j][i] == 1)
        			 one++;
        	 }
        	 if(one * 2 < m) {
        		 for(int j=0; j<m; j++) {
            		 A[j][i] ^= 1;
            	 }
        	 }
         }
         for(int i=0; i<m; i++) {
        	 int temp = 0;
        	 for(int j=n-1; j>=0; j--) {
        		 temp |= A[i][j] << (n-1-j);
        	 }
        	 score += temp;
         }
         return score;
     }
     
     private void matrixScoreFlip(int[] array) {
    	 for(int i=0; i<array.length; i++)
    		 array[i] = array[i] ^ 1;
     }
     
     public int[][] transpose(int[][] A) {
         int m = A.length, n = A[0].length;
         int[][] transpose = new int[n][m];
         for(int j=0; j<n; j++) {
        	 for(int i=0; i<m; i++) {
        		 transpose[j][i] = A[i][j];
        	 }
         }
         return transpose;
     }
     
     
     
     public int[] advantageCount(int[] A, int[] B) {
    	 int n = A.length;
         int[] answer = new int[n];
         TreeMap<Integer, List<Integer>> tree = new TreeMap<>();
         for(int i=0; i<n; i++) {
        	 List<Integer> index = tree.get(A[i]);
        	 if(index == null) {
        		 index = new LinkedList<>();
        		 tree.put(A[i], index);
        	 }
        	 index.add(i);
         }
         boolean[] used = new boolean[n];
         for(int i=0; i<n; i++) {
        	 answer[i] = -1;
        	 Map.Entry<Integer, List<Integer>> entry = tree.higherEntry(B[i]);
        	 if(entry == null)
        		 continue;
        	 List<Integer> index = entry.getValue();
        	 int j = index.remove(0);
        	 used[j] = true;
        	 answer[i] = A[j];
        	 if(index.isEmpty())
        		 tree.remove(entry.getKey());
         }
         int index = 0;
         for(int i=0; i<n; i++) {
        	 if(answer[i] == -1) {
        		 while(used[index])
        			 index++;
        		 answer[i] = A[index++];
        	 }
         }
         
         return answer;
     }
     
     public int minRefuelStops(int target, int startFuel, int[][] stations) {
    	 if(startFuel >= target)
    		 return 0;
         TreeMap<Long, Integer> tree = new TreeMap<>();
         for(int i=0; i<stations.length; i++) {
        	 int distance = stations[i][0];
        	 tree.put((long)distance, i);
         }
    	 Queue<long[]> queue = new LinkedList<>();
    	 queue.add(new long[] {0, startFuel, 0});
    	 Map<String, Integer> dp = new HashMap<>();
    	 dp.put(0+","+startFuel, 0);
    	 while(!queue.isEmpty()) {
    		 int size = queue.size();
    		 for(int t=0; t<size; t++) {
    			 long[] array = queue.poll();
    			 long position = array[0], fuel = array[1], stop = array[2];
    			 Map.Entry<Long, Integer> entry = tree.floorEntry(position + fuel);
    			 if(entry == null)
    				 continue;
    			 int limit = entry.getValue();
    			 for(int i=limit; i>=0; i--) {
    				 if(stations[i][0] <= position)
    					 break;
    				 long[] next_array = {stations[i][0], fuel-(stations[i][0]-position)+stations[i][1], stop+1};
    				 if(next_array[0] + next_array[1] <= stations[limit][0])
    					 continue;
    				 if(next_array[0]+next_array[1] >= target)
    					 return (int) next_array[2];
    				 String key = Arrays.toString(next_array);
    				 if(next_array[2] >= dp.getOrDefault(key, Integer.MAX_VALUE))
    					 continue;
    				 dp.put(key, (int)next_array[2]);
    				 queue.add(next_array);
    			 }
    		 }
    	 }
    	 return -1;
     }
     
     public int minRefuelStops2(int target, int startFuel, int[][] stations) {
    	 int stop = 0;
    	 PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> b-a);
    	 int pre = 0;
    	 int tank = startFuel;
    	 for(int[] station : stations) {
    		 int cur = station[0], fuel = station[1];
    		 tank -= cur - pre;
    		 while(!pq.isEmpty() && tank < 0) {
    			 tank += pq.poll();
    			 stop++;
    		 }
    		 if(tank < 0)
    			 return -1;
    		 pre = cur;
    		 pq.add(fuel);
    	 }
    	 tank -= target - pre;
    	 while(!pq.isEmpty() && tank < 0) {
			 tank += pq.poll();
			 stop++;
		 }
		 if(tank < 0)
			 return -1;
    	 
    	 return stop;
     }
     
     
     public boolean circularArrayLoop(int[] nums) {
         int n = nums.length;
         int[] forward = new int[n];
         int[] backward = new int[n];
         int next_forward_index = -1;
         int pre_backward_index = -1;
         if(nums[n-1] > 0) {
        	 forward[n-1] = n-1 + nums[n-1];
        	 next_forward_index = n-1;
         }
         else {
        	 forward[n-1] = n-1;
         }
         
    	 for(int i=n-2; i>=0; i--) {
    		 if(nums[i] > 0 && next_forward_index != -1) {
    			 forward[i] = Math.max(i + nums[i], forward[next_forward_index]);
    			 next_forward_index = i;
    		 }
    		 else {
    			 forward[i] = i;
    		 }
    	 }
    	 if(nums[0] < 0) {
    		 backward[0] = -nums[0];
    		 pre_backward_index = 0;
    	 }
    	 else {
    		 backward[0] = 0;
    	 }
    	 for(int i=1; i<n; i++) {
    		 if(nums[i] < 0 && pre_backward_index != -1) {
    			 backward[i] = Math.min(i + nums[i], backward[pre_backward_index]);
    			 pre_backward_index = i;
    		 }
    		 else {
    			 backward[i] = i;
    		 }
    	 }
    	 return false;
     }
     
     public boolean circularArrayLoop2(int[] nums) {
    	 int n = nums.length;
    	 boolean[] used = new boolean[n];
    	 for(int i=0; i<n; i++) {
    		 if(used[i] || nums[i] < 0)
    			 continue;
    		 int index = i;
    		 int pre = index;
    		 while(nums[index] > 0) {
    			 pre = index;
    			 used[pre] = true;
    			 index = (nums[pre] + pre) % n;
    			 if(index == i && pre != index)
    				 return true;
    			 else if(pre == index)
    				 break;
    		 }
    	 }
    	 Arrays.fill(used, false);
    	 for(int i=n-1; i>=0; i--) {
    		 if(used[i] || nums[i] > 0)
    			 continue;
    		 int index = i;
    		 int pre = i;
    		 while(nums[index] < 0) {
    			 pre = index;
    			 used[pre] = true;
    			 index = nums[pre] + pre;
    			 if(index < 0)
    				 index = n + index;
    			 if(index == i && pre != index)
    				 return true;
    			 else if(pre == index)
    				 break;
    		 }
    	 }
    	 return false;
     }
     
     public boolean circularArrayLoop3(int[] nums) {
    	 int n = nums.length;
    	 for(int i=0; i<n; i++) {
    		 if(nums[i] < 0)
    			 continue;
    		 int index = i;
    		 int pre = index;
    		 while(nums[index] > 0) {
    			 pre = index;
    			 index = (nums[pre] + pre) % n;
    			 if(index == i && pre != index)
    				 return true;
    			 else if(pre == index)
    				 break;
    		 }
    	 }
    	 for(int i=n-1; i>=0; i--) {
    		 if(nums[i] > 0)
    			 continue;
    		 int index = i;
    		 int pre = i;
    		 while(nums[index] < 0) {
    			 pre = index;
    			 index = nums[pre] + pre;
    			 if(index < 0)
    				 index = n + index;
    			 if(index == i && pre != index)
    				 return true;
    			 else if(pre == index)
    				 break;
    		 }
    	 }
    	 return false;
     }
     
     
     
     class Node {
    	    public int val;
    	    public Node prev;
    	    public Node next;
    	    public Node child;

    	    public Node() {}

    	    public Node(int _val,Node _prev,Node _next,Node _child) {
    	        val = _val;
    	        prev = _prev;
    	        next = _next;
    	        child = _child;
    	    }
    	};
     
	public Node flatten(Node head) {
		Node node = head;
		Stack<Node> stack = new Stack<>();
		outer:
		while(node != null) {
			while(node.child == null && node.next == null) {
				Node next = null;
				if(!stack.isEmpty())
					next = stack.pop();
				node.next = next;
				if(next != null) {
					next.prev = node;
				}
				node = next;
				if(node == null)
					break outer;
			}
			if(node.child == null) {
				node = node.next;
				continue;
			}
			Node next = node.next;
			if(next != null)
				stack.push(next);
			node.next = node.child;
			node.child.prev = node;
			node.child = null;
		}
		return head;
	}
    
	
	public int robotSim(int[] commands, int[][] obstacles) {
        int square = 0;
        int x = 0, y = 0;
        int dir = 0;
        Map<Integer, TreeSet<Integer>> obstacles_x = new HashMap<>();
        Map<Integer, TreeSet<Integer>> obstacles_y = new HashMap<>();
        for(int[] ob : obstacles) {
        	int X = ob[0], Y = ob[1];
        	TreeSet<Integer> tree = obstacles_x.get(X);
        	if(tree == null) {
        		tree = new TreeSet<>();
        		obstacles_x.put(X, tree);
        	}
        	tree.add(Y);
        	tree = obstacles_y.get(Y);
        	if(tree == null) {
        		tree = new TreeSet<>();
        		obstacles_y.put(Y, tree);
        	}
        	tree.add(X);
        }
        for(int c : commands) {
        	if(c < 0) {
        		if(c == -2) {
        			dir -= 1;
        			if(dir < 0)
        				dir += 4;
        		}
        		else {
        			dir = (dir+1) % 4;
        		}
        	}
        	else {
        		if(dir == 0) {
        			TreeSet<Integer> tree = obstacles_x.get(x);
        			if(tree != null) {
        				Integer ob = tree.higher(y);
        				if(ob != null && y + c >= ob) {
        					y = ob - 1;
        				}
        				else {
        					y += c;
        				}
        			}
        			else {
            			y += c;
        			}
        			
        		}
        		else if(dir == 1) {
        			TreeSet<Integer> tree = obstacles_y.get(y);
        			if(tree != null) {
        				Integer ob = tree.higher(x);
        				if(ob != null && x + c >= ob) {
        					x = ob - 1;
        				}
        				else {
        					x += c;
        				}
        			}
        			else {
        				x += c;
        			}
        			
        		}
        		else if(dir == 2) {
        			TreeSet<Integer> tree = obstacles_x.get(x);
        			if(tree != null) {
        				Integer ob = tree.lower(y);
        				if(ob != null && y - c <= ob) {
        					y = ob + 1;
        				}
        				else {
        					y -= c;
        				}
        			}
        			else {
            			y -= c;
        			}
        		}
        		else {
        			TreeSet<Integer> tree = obstacles_y.get(y);
        			if(tree != null) {
        				Integer ob = tree.lower(x);
        				if(ob != null && x - c <= ob) {
        					x = ob + 1;
        				}
        				else {
        					x -= c;
        				}
        			}
        			else {
        				x -= c;
        			}
        		}
        	}
        	square = Math.max(square, x*x+y*y);
        }
        
        return square;
    }
	
	public int numRescueBoats(int[] people, int limit) {
        int boats = 0;
        TreeMap<Integer, Integer> tree = new TreeMap<>();
        for(int weight : people) {
        	tree.put(weight, tree.getOrDefault(weight, 0)+1);
        }
        while(!tree.isEmpty()) {
        	Map.Entry<Integer, Integer> minEntry = tree.firstEntry();
        	int weight = minEntry.getKey();
        	int other_weight = limit - weight;
        	Map.Entry<Integer, Integer> other_entry = tree.floorEntry(other_weight);
        	if(other_entry != null) {
        		tree.put(other_entry.getKey(), other_entry.getValue()-1);
        		if(other_entry.getValue() == 1)
        			tree.remove(other_entry.getKey());
        	}
        	if(tree.get(minEntry.getKey()) != null) {
        		tree.put(minEntry.getKey(), tree.get(minEntry.getKey())-1);
            	if(tree.get(minEntry.getKey()) == 0)
            		tree.remove(minEntry.getKey());
        	}
        	boats++;
        }
        return boats;
    }
    
	public int numRescueBoats2(int[] people, int limit) {
		Arrays.sort(people);
		int i, j;
		for(i=0, j=people.length-1; i<=j; j--) {
			if(people[i] + people[j] <= limit)
				i++;
		}
		return people.length-1 - j;
	}
    
	
	
	static class NumArray {
		
		class SegmentTreeNode {
			int L, R;
			int sum;
			SegmentTreeNode left, right;
			
			public SegmentTreeNode(int i, int j) {
				L = i;
				R = j;
			}
		}
		
		SegmentTreeNode root = null;

	    public NumArray(int[] nums) {
	        root = build(nums, 0, nums.length-1);
	    }
	    
	    public void update(int i, int val) {
	        update(root, i, val);
	    }
	    
	    public int sumRange(int i, int j) {
	    	return rangsum(root, i, j);
	    }
	    
	    
	    private SegmentTreeNode build(int[] array, int from, int to) {
	    	if(from > to)
	    		return null;
	    	SegmentTreeNode node = new SegmentTreeNode(from, to);
	    	if(from == to) {
	    		node.sum = array[from];
	    	}
	    	else {
	    		int mid = from + (to - from) / 2;
	    		node.left = build(array, from, mid);
	    		node.right = build(array, mid+1, to);
	    		node.sum = node.left.sum + node.right.sum;
	    	}
	    	return node;
	    }
	    
	    private int rangsum(SegmentTreeNode node, int start, int end) {
	    	if(node.L == start && node.R == end)
	    		return node.sum;
	    	int mid = node.L + (node.R - node.L) / 2;
	    	if(end <= mid)
	    		return rangsum(node.left, start, end);
	    	else if(start > mid)
	    		return rangsum(node.right, start, end);
	    	return rangsum(node.left, start, mid) + rangsum(node.right, mid+1, end);
	    }
	    
	    private void update(SegmentTreeNode node, int index, int val) {
	    	if(node.L == node.R) {
	    		node.sum = val;
	    		return;
	    	}
	    	int mid = node.L + (node.R - node.L) / 2;
	    	if(index <= mid) {
	    		update(node.left, index, val);
	    	}
	    	else {
	    		update(node.right, index, val);
	    	}
	    	node.sum = node.left.sum + node.right.sum;
	    }
	    
	    
	    
	}
	
	static class CountofSmallerNumbersAfterSelf {
		
		
		public List<Integer> countSmaller(int[] nums) {
			List<Integer> list = new LinkedList<>();
			int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
			for(int num : nums) {
				min = Math.min(min, num);
				max = Math.max(max, num);
			}
			SegmentTreeNode node = new SegmentTreeNode(min, max);
			for(int i=nums.length-1; i>=0; i--) {
				list.add(0, find(node, nums[i]-1));
				add(node, nums[i]);
			}	
			return list;
		}
		
		class SegmentTreeNode {
			int min, max;
			SegmentTreeNode left, right;
			int count;
			public SegmentTreeNode(int min, int max) {
				this.min = min;
				this.max = max;
			}
		}
		private int find(SegmentTreeNode node, int num) {
			if(node == null)
				return 0;
			if(num >= node.max)
				return node.count;
			int mid = node.min + (node.max - node.min) / 2;
			if(num <= mid)
				return find(node.left, num);
			return find(node.left, num) + find(node.right, num);
		}
		
		
		private void add(SegmentTreeNode node, int num) {
			if(num < node.min || num > node.max)
				return;
			node.count++;
			if(node.min == node.max)
				return;
			int mid = node.min + (node.max - node.min) / 2;
			if(num <= mid) {
				if(node.left == null)
					node.left = new SegmentTreeNode(node.min, mid);
				add(node.left, num);
			}
			else {
				if(node.right == null)
					node.right = new SegmentTreeNode(mid+1, node.max);
				add(node.right, num);
			}
		}
	}
	
	
	public int reachableNodes(int[][] edges, int M, int N) {
        int[][] graph = new int[N][N];
        for(int i=0; i<N; i++) {
        	Arrays.fill(graph[i], -1);
        }
        for(int[] edge : edges) {
        	graph[edge[0]][edge[1]] = edge[2];
        	graph[edge[1]][edge[0]] = edge[2];
        }
        int nodes = 0;
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (b[1] - a[1]));
		boolean[] visited = new boolean[N];
		pq.offer(new int[] {0, M});
		while(!pq.isEmpty()) {
			int[] cur = pq.poll();
			int start = cur[0];
			int moves = cur[1];
			if(visited[start]) {
				continue;
			}
			visited[start] = true;
			nodes++;
			for(int i=0; i<N; i++) {
				if(graph[start][i] > -1) {
					if(moves > graph[start][i] && !visited[i]) {
						pq.offer(new int[] {i, moves - graph[start][i] - 1});
					}
					graph[i][start] -= Math.min(moves, graph[start][i]);
                    nodes += Math.min(moves, graph[start][i]);
				}
			}
		}
		return nodes;
    }
	
	public void Dijkstra(int[][] edges, int source, int N) {
		int[][] graph = new int[N][N];
		for(int i=0; i<N; i++) {
			Arrays.fill(graph[i], -1);
		}
		for(int[] edge : edges) {
			graph[edge[0]][edge[1]] = edge[2];
			graph[edge[1]][edge[0]] = edge[2];
		}
		boolean[] visited = new boolean[N];
		int[] distance = new int[N];
		PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> (a[1] - b[1]));
		pq.offer(new int[] {source, 0});
		while(!pq.isEmpty()) {
			int[] cur = pq.poll();
			int point = cur[0];
			int dis = cur[1];
			if(visited[point])
				continue;
			distance[point] = dis;
			for(int i=0; i<N; i++) {
				if(graph[point][i] == -1)
					continue;
				pq.offer(new int[] {i, dis + graph[point][i]});
			}
		}
		
	}
	
	
	public boolean possibleBipartition(int N, int[][] dislikes) {
        int[][] graph = new int[N][N];
        for(int[] dis : dislikes) {
        	graph[dis[0] - 1][dis[1] - 1] = 1;
        	graph[dis[1] - 1][dis[0] - 1] = 1;
        }
        int[] color = new int[N];
        for(int i=0; i<N; i++) {
        	if(color[i] == 0 && !possibleBipartitionDFS(graph, color, i, 1))
        		return false;
        }
		return true;
    }
	
	private boolean possibleBipartitionDFS(int[][] graph, int[] color, int index, int tag) {
		color[index] = tag;
		for(int i=0; i<graph.length; i++) {
			if(graph[index][i] == 1) {
				if(color[i] == tag)
					return false;
				if(color[i] == 0 && !possibleBipartitionDFS(graph, color, i, -tag))
					return false;
			}
		}
		return true;
	}
	
	public int[] fairCandySwap(int[] A, int[] B) {
        Set<Integer> set = new HashSet<>();
        long sum_A = 0, sum_B = 0;
        for(int candy : A) {
        	sum_A += candy;
        	set.add(candy);
        }
        for(int candy : B) {
        	sum_B += candy;
        }
        for(int candy_B : B) {
        	int candy_A = (int) (sum_A + candy_B - (sum_B - candy_B ));
        	if(candy_A % 2 == 0) {
        		candy_A /= 2;
        		if(set.contains(candy_A)) {
        			return new int[] {candy_A, candy_B};
        		}
        	}
        }
		return null;
    }
	
	public boolean find132pattern(int[] nums) {
        int n = nums.length;
        if(n < 3)
        	return false;
        int[] min = new int[n];
		min[0] = nums[0];
		for(int i=1; i<n; i++) {
			min[i] = Math.min(min[i-1], nums[i]);
		}
		TreeSet<Integer> tree = new TreeSet<>();
		tree.add(nums[n-1]);
		for(int i=n-2; i>0; i--) {
			int num = nums[i];
			if(num > min[i]) {
				Integer right = tree.lower(num);
				if(right != null && right > min[i])
					return true;
			}
			tree.add(num);
		}
		return false;
    }
	

	
	 public boolean canFinish(int numCourses, int[][] prerequisites) {
		 int[][] matrix = new int[numCourses][numCourses];
		 int[] indegree = new int[numCourses];
		 Queue<Integer> queue = new LinkedList<>();
		 int count = 0;
		 for(int[] edge : prerequisites) {
			 int cur = edge[0];
			 int pre = edge[1];
			 matrix[pre][cur] = 1;
			 indegree[cur]++;
		 }
		 for(int i=0; i<numCourses; i++) {
			 if(indegree[i] == 0) {
				 queue.add(i);
			 }
		 }
		 while(!queue.isEmpty()) {
			 int cur = queue.poll();
			 count++;
			 for(int i=0; i<numCourses; i++) {
				 if(matrix[cur][i] != 0) {
					 indegree[i]--;
					 if(indegree[i] == 0) {
						 queue.add(i);
					 }
				 }
			 }
		 }
		 return count == numCourses;
	 }
	 
	 
	 public int[] findOrder(int numCourses, int[][] prerequisites) {
		 if(numCourses == 0) {
			 return new int[] {};
		 }
		 if(prerequisites == null || prerequisites.length == 0) {
			 int[] order = new int[numCourses];
			 for(int i=0; i<order.length; i++)
				 order[i] = i;
			 return order;
		 }
		 boolean[][] matrix = new boolean[numCourses][numCourses];
		 int[] indegree = new int[numCourses];
		 for(int[] edge : prerequisites) {
			 int pre = edge[1];
			 int cur = edge[0];
			 if(matrix[pre][cur])
				 continue;
			 matrix[pre][cur] = true;
			 indegree[cur]++;
		 }
		 Queue<Integer> queue = new LinkedList<>();
		 for(int i=0; i<numCourses; i++) {
			 if(indegree[i] == 0) {
				 queue.add(i);
			 }
		 }
		 int[] order = new int[numCourses];
		 int index = 0;
		 while(!queue.isEmpty()) {
			 int cur = queue.poll();
			 order[index++] = cur;
			 for(int i=0; i<numCourses; i++) {
				 if(matrix[cur][i]) {
					 indegree[i]--;
					 if(indegree[i] == 0) {
						 queue.add(i);
					 }
				 }
			 }
		 }
		 if(index == numCourses)
			 return order;
		 return new int[] {};
	 }
	 
	 
	 public int longestIncreasingPath(int[][] matrix) {
		 if(matrix == null || matrix.length == 0 || matrix[0].length == 0)
			 return 0;
		 int m = matrix.length, n = matrix[0].length;
		 int[][] dp = new int[m][n];
		 int answer = 0;
		 for(int i=0; i<m; i++) {
			 for(int j=0; j<n; j++) {
				 if(dp[i][j] == 0) {
					 answer = Math.max(answer, longestIncreasingPathDFS(matrix, i, j, dp));
				 }
			 }
		 }
		 return answer;
	 }
	 
	 private int longestIncreasingPathDFS(int[][] matrix, int i, int j, int[][] dp) {
		 if(dp[i][j] != 0)
			 return dp[i][j];
		 dp[i][j] = 1;
		 for(int[] dir : directions) {
			 int x = dir[0] + i;
			 int y = dir[1] + j;
			 if(x<0 || x>=matrix.length || y<0 || y>=matrix[0].length || !(matrix[x][y] > matrix[i][j]))
				 continue;
			 dp[i][j] = Math.max(dp[i][j], longestIncreasingPathDFS(matrix, x, y, dp)+1);
		 }
		 return dp[i][j];
	 }
	 
	 public boolean isMonotonic(int[] A) {
		 if(is_monotone_increasing(A))
			 return true;
		 if(is_monotone_decreasing(A))
			 return true;
		 return false;
	 }
	 
	 private boolean is_monotone_increasing(int[] A) {
		 int pre = Integer.MIN_VALUE;
		 for(int num : A) {
			 if(num < pre)
				 return false;
			 pre = Math.max(pre, num);
		 }
		 return true;
	 }
	 
	 private boolean is_monotone_decreasing(int[] A) {
		 int pre = Integer.MIN_VALUE;
		 for(int i=A.length-1; i>=0; i--) {
			 if(A[i] < pre)
				 return false;
			 pre = Math.max(pre, A[i]);
		 }
		 return true;
	 }
	
	
     
     


	public static void main(String[] args) {
		 
	}

}
