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
    
    
    

	public static void main(String[] args) {
		Array test = new Array();
		int[] array = {2,2,10,5,2,7,2,2,13};
		test.canPartitionKSubsets(array, 3);

	}

}
