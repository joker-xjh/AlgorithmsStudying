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
    
    
    
    
    

	public static void main(String[] args) {
		

	}

}
