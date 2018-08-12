package leetcode;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

public class math {
	
    public int newInteger(int n) {
        int res = 0;
        int base = 1;
        while(n > 0) {
        	res += n%9 * base;
        	n /= 9;
        	base *= 10;
        }
        
        return res;
    }
    
    
    public boolean hasAlternatingBits(int n) {
    	if(n <= 0)
    		return false;
        int mod = n % 2;
        n /= 2;
        while(n != 0) {
        	int temp = n % 2;
        	if(temp == mod)
        		return false;
        	mod = temp;
        	n /= 2;
        }
    	return true;
    }
    
    
    public List<Integer> selfDividingNumbers(int left, int right) {
        List<Integer> list = new ArrayList<>();
        for(int i=left; i<=right; i++) {
        	boolean add = true;
        	int num = i;
        	while( num > 0) {
        		int mod = num % 10;
        		if(mod == 0 || i % mod != 0) {
        			add = false;
        			break;
        		}
        		num /= 10;
        	}
        	if(add)
        		list.add(i);
        }
        return list;
    }
    
    public int monotoneIncreasingDigits(int N) {
    	if(N < 10)
    		return N;
        String num = String.valueOf(N);
    	char[] array = num.toCharArray();
    	if(monotoneIncreasingDigitsCheck(array))
    		return N;
		while(true) {
			for(int i=1; i<array.length; i++) {
				char c = array[i];
				char pre = array[i-1];
				if(c < pre) {
					for(int j=i; j<array.length; j++)
						array[j] = '9';
					int index = i - 1;
					while(index >=0 && array[index] == '0') {
						array[index] = '9';
						index-- ;
					}
					array[index]--;
					if(monotoneIncreasingDigitsCheck(array))
						return Integer.parseInt(new String(array));
					break;
				}
			}
		}
    }
    
    private boolean monotoneIncreasingDigitsCheck(char[] array) {
    	for(int i=1; i<array.length; i++) {
    		char c = array[i];
    		char pre = array[i-1];
    		if(c < pre)
    			return false;
    	}
    	
    	return true;
    }
    
    public int reachNumber(int target) {
    	if(target < 0)
    		target = -target;
        int step = 0;
        int sum = 0;
        while(sum < target) {
        	step++;
        	sum += step;
        }
        while(((sum - target) & 1) != 0) {
        	step++;
        	sum += step;
        }
        return step;
    }
    
    private boolean[] primeTable = {false, false, true, true, false, true, false, true, false,
    		                        false, false, true, false, true, false, false, false, true, 
    		                        false, true, false, false, false, true, false, false, false,
    		                        false, false, true, false, true};
    
    public int countPrimeSetBits(int L, int R) {
        int count = 0;
        for(int i=L; i<=R; i++) {
        	int bits = Integer.bitCount(i);
        	if(primeTable[bits])
        		count++;
        }
        return count;
    }
    
    
    
    public boolean reachingPoints(int sx, int sy, int tx, int ty) {
    	return reachingPointsHelp(sx, sy, tx, ty);
    }
    
    private boolean reachingPointsHelp(long x, long y, long a, long b) {
    	if(x > a || y > b)
    		return false;
    	if(x == a)
    		return (b - y) % x == 0;
    	if(y == b)
    		return (a - x) % y == 0;
    	return reachingPointsHelp(2*x+y, x+y, a, b) ||
    		   reachingPointsHelp(x+y, x+2*y, a, b) ||
    		   reachingPointsHelp(3*x+y, 2*x+y, a, b) ||
    		   reachingPointsHelp(x+2*y, x+3*y, a, b);
    }
    public boolean reachingPoints2(int sx, int sy, int tx, int ty) {
    	return reachingPointsHelp2(sx, sy, tx, ty, new HashMap<>());
    }
    
    private boolean reachingPointsHelp2(long x, long y, long a, long b, Map<String, Boolean> memorization) {
    	if(x > a || y > b)
    		return false;
    	if(x == a)
    		return (b - y) % x == 0;
    	if(y == b)
    		return (a - x) % y == 0;
    	String key = x+","+y;
    	if(memorization.containsKey(key))
    		return memorization.get(key);
    	boolean result = reachingPointsHelp2(2*x+y, x+y, a, b, memorization) ||
     		   reachingPointsHelp2(x+y, x+2*y, a, b, memorization) ||
     		   reachingPointsHelp2(3*x+y, 2*x+y, a, b, memorization) ||
     		   reachingPointsHelp2(x+2*y, x+3*y, a, b, memorization);
    	memorization.put(key, result);
    	return result;
    }
    
    public boolean reachingPoints3(int sx, int sy, int tx, int ty) {
    	while(sx < tx && sy < ty) {
    		if(tx > ty)
    			tx %= ty;
    		else
    			ty %= tx;
    	}
    	return (sx == tx && (ty-sy) % sx == 0) || (sy == ty && (tx - sx) % sy == 0);
    }
    
    
    public boolean canMeasureWater(int x, int y, int z) {
        if(x + y < z) return false;
        if( x == z || y == z || x + y == z ) 
        	return true;        
        return z%GCD(x, y) == 0;
    }

    public int GCD(int a, int b){
        while(b != 0 ){
            int temp = b;
            b = a%b;
            a = temp;
        }
        return a;
    }
    
    public int preimageSizeFZF(int K) {
    	if(K == 0)
    		return 5;
        if(preimageSizeFZFCheck(1, K, K))
        	return 5;
        return 0;
    }
    
    private boolean preimageSizeFZFCheck(int left, int right, int target) {
    	while(left <= right) {
    		int mid = left + (right - left) / 2;
    		int temp = preimageSizeFZFSum(mid);
    		if(temp == target)
    			return true;
    		else if(temp < target)
    			left = mid + 1;
    		else
    			right = mid - 1;
    	}
    	return false;
    }
    
    private int preimageSizeFZFSum(int num) {
    	int sum = 0;
    	while(num != 0) {
    		sum += num;
    		num /= 5;
    	}
    	return sum;
    }
    
    public int superPow(int a, int[] b) {
        a %= mod;
        int answer = 1;
        for(int i=b.length-1; i>=0; i--) {
        	answer = answer * fastPower(a, b[i]) % mod;
        	a = fastPower(a, 10);
        }
        return answer;
    }
    
    private static int mod = 1337;
    
    private int fastPower(int a, int b) {
    	int answer = 1;
    	while(b != 0) {
    		if(b % 2 != 0)
    			answer = answer * a % mod;
    		a = a * a % mod;
    		b /= 2;
    	}
    	return answer;
    }
    
    public double champagneTower(int poured, int query_row, int query_glass) {
        double[][] array = new double[101][101];
        array[0][0] = poured;
        for(int i=0; i<100; i++) {
        	for(int j=0; j<=i; j++) {
        		if(array[i][j] > 1) {
        			double fall = (array[i][j] - 1) / 2;
        			array[i+1][j] += fall;
        			array[i+1][j+1] += fall;
        			array[i][j] = 1;
        		}
        	}
        }
    	
    	return array[query_row][query_glass];
    }
    
    public int consecutiveNumbersSum(int N) {
    	if(N == 3)
    		return 2;
        int count = 1;
        int sum = 1;
        for(int i=2; N/i > 1 && N > sum; i++) {
        	if((N > sum) && (N-sum) % i == 0)
        		count++;
        	sum += i;
        }
        return count;
    }
    
    
    public int computeArea(int A, int B, int C, int D, int E, int F, int G, int H) {
    	int area = 0;
    	int areaA = (C - A) * (D - B);
    	int areaB = (G - E) * (H - F);
    	int overlap = 0;
    	if(A >= G || E >= C || B >= H || F >= D) {
    		overlap = 0;
    	}
    	else {
    		overlap = ((Math.min(C, G) - Math.max(A, E)) * ((Math.min(D, H) - Math.max(B, F))));
    	}
    	area = areaA + areaB - overlap;
    	return area;
    }
    
    
    public int primePalindrome(int n) {
    	if(929 < n && n < 10301)
            return 10301;
        if(98689 < n && n < 1003001)
            return 1003001;
        if(9989899 < n && n < 100030001)
            return 100030001;
        n--;
        BigInteger num = new BigInteger(n+"");
        BigInteger prime = num.nextProbablePrime();
        while(!ispalindrome(prime.toString())) {
        	prime = prime.nextProbablePrime();
        }
        return prime.intValue();
        
    }
    
    private boolean ispalindrome(String s) {
    	int left = 0, right = s.length()-1;
    	while(left < right) {
    		if(s.charAt(left++) != s.charAt(right--))
    			return false;
    	}
    	return true;
    }
    
    public boolean isPrime(int num) {
    	if(num < 2 || num % 2 == 0)
    		return num == 2;
    	int end = (int) Math.sqrt(num);
    	for(int i=3; i<=end; i+=2)
    		if(num % i == 0)
    			return false;
    	return true;
    }
    
    public int shortestPathAllKeys(String[] grid) {
        int m = grid.length, n = grid[0].length();
        int moves = 0;
        char[][] board = new char[m][n];
        int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}};
        for(int i=0; i<m; i++)
        	board[i] = grid[i].toCharArray();
    	int[] start = shortestPathAllKeys_getStartPos(board);
    	int key_count = start[2];
    	Queue<int[]> queue = new LinkedList<>();
    	Set<String> visited = new HashSet<>();
    	int[] start_state = new int[] {0,0,0,0,0,0,start[0],start[1],0};
    	visited.add(Arrays.toString(start_state));
    	queue.add(start_state);
    	while(!queue.isEmpty()) {
    		int size = queue.size();
    		moves++;
    		for(int t=0; t<size; t++) {
    			int[] array = queue.poll();
    			for(int[] d : dirs) {
    				int x = d[0] + array[6];
    				int y = d[1] + array[7];
    				if(x < 0 || x >= m || y < 0 || y >= n || board[x][y] == '#')
    					continue;
    				
    				if('A' <= board[x][y] && board[x][y] <= 'F') {
    					int key_index = board[x][y] + 32 - 'a';
    					if(array[key_index] == 0)
    						continue;
    				}
    				int[] next_array = new int[] {array[0], array[1], array[2], array[3], array[4],
		                      array[5], x, y, array[8]};
    				if('a' <= board[x][y] && board[x][y] <= 'f') {
    					int key_index = board[x][y] - 'a';
    					if(next_array[key_index] == 0) {
    						next_array[key_index] = 1;
    						next_array[8]++;
    						if(next_array[8] == key_count)
    							return moves;
    					}
    				}
    				
    				String visited_key = Arrays.toString(next_array);
    				if(visited.contains(visited_key))
    					continue;
    				visited.add(visited_key);
    				queue.add(next_array);
    			}
    		}
    	}
    	
    	
    	return -1;
    }
    
    private int[] shortestPathAllKeys_getStartPos(char[][] board) {
    	int[] result = new int[3];
    	for(int i=0; i<board.length; i++) {
    		for(int j=0; j<board[0].length; j++) {
    			if(board[i][j] == '@') {
    				result[0] = i;
    				result[1] = j;
    			}
    			if('a' <= board[i][j] && board[i][j] <= 'f') {
    				result[2]++;
    			}
    		}
    	}
    	return result;
    }
    
    
    class RandomPickWithBlacklist {
    	int[] array = null;
    	int index = 0;
    	int N;
    	public void  Solution(int N, int[] blacklist) {
            this.array = blacklist;
            this.N = N;
            Arrays.sort(array);
        }
        
        public int pick() {
        	if(array.length == 0)
        		return (int) (Math.random() * N);
        	int pick = -1;
            while(true) {
            	int min = -1, max = -1;
            	if(index == array.length-1) {
            		min = array[index] + 1;
            		max = N-1;
            	}
            	else if(index == 0) {
            		min = 0;
            		max = array[index] - 1;
            	}
            	else {
            		min = array[index] + 1;
            		max = array[index+1] - 1;
            	}
            	if(min <= max) {
    				pick = min + (int)(Math.random() * (max - min + 1));
    			}
        		index = (index + 1) % array.length;
        		if(pick != -1)
        			break;
            }
            return pick;
        }
    }
    
    
    class RandomPickWithBlacklist2 {
    	int M;
    	Map<Integer, Integer> map = new HashMap<>();
    	Random random;
    	public void  Solution(int N, int[] blacklist) {
            for(int b : blacklist) {
            	map.put(b, -1);
            }
            M = N - blacklist.length;
            for(int b : blacklist) {
            	if(b >= M)
            		continue;
            	while(map.containsKey(N - 1))
            		N--;
            	map.put(b, N-1);
            	N--;
            }
            random = new Random();
        }
        
        public int pick() {
        	int pick = random.nextInt(M);
        	if(map.containsKey(pick))
        		return map.get(pick);
        	return pick;
        }
    }
    
    
    static class RandomFlipMatrix {
    	
    	Set<String> used;
    	int x, y;
    	Random random;
    	
    	public RandomFlipMatrix(int n_rows, int n_cols) {
    		used = new HashSet<>();
    		x = n_rows;
    		y = n_cols;
    		random = new Random();
        }
        
        public int[] flip() {
            int row = random.nextInt(x);
            int col = random.nextInt(y);
            while(!used.add(row + "," + col)) {
            	row = random.nextInt(x);
                col = random.nextInt(y);
            }
            used.add(row+","+col);
        	return new int[] {row, col};
        }
        
        public void reset() {
            used.clear();
        }
        
    }
    
    class RandomPointinNonOverlappingRectangles2{
    	RandomFlipMatrix[] array;
    	int n;
    	int[] counter;
    	Random random;
    	
    	public void Solution(int[][] rects) {
            this.n = rects.length;
            array = new RandomFlipMatrix[n];
            counter = new int[n];
            random = new Random();
            for(int i=0; i<n; i++) {
            	int[] pos = rects[i];
            	int x = pos[2] - pos[0];
            	int y = pos[3] - pos[1];
            	counter[i] = x * y;
            	array[i] = new RandomFlipMatrix(x, y);
            }
        }
        
        public int[] pick() {
            int index = random.nextInt(n);
        	while(counter[index] <= 0) {
        		index = random.nextInt(n);
        	}
        	counter[index]--;
        	return array[index].flip();
        }
    }
    
    
    
    
    
    
    
    
   static class RandomFlipMatrix2 {
    	int[] row_counter;
        Map<Integer, Integer> row_map;
        Map<Integer, Integer> row_to_col;
        Map<Integer, Map<Integer, Integer>> map;
        int row, original_row, original_col;
        Random row_random, col_random;
        public void Solution(int n_rows, int n_cols) {
            row_counter = new int[n_rows];
            row_map = new HashMap<>();
            row_to_col = new HashMap<>();  
            row = n_rows;
            original_row = row;
            original_col = n_cols;
            row_random = new Random();
            col_random = new Random();
            map = new HashMap<>();
            for(int i=0; i<row; i++) {
                map.put(i, new HashMap<>());
                row_to_col.put(i, n_cols);
            }
        }
        
        public int[] flip() {
            int[] pos = new int[2];
            int i = row_random.nextInt(row);
            int pre_i = i;
            i = row_map.getOrDefault(i, i);
            row_counter[i]++;
            if(row_counter[i] == original_col) {
                row--;
                if(i < row) {
                    if(row_counter[row] == original_col)
                			row = row_map.get(row);
                    row_map.put(i, row);
                }
                else if(i > row) {
                	row_map.put(pre_i, row);
                }
            }
            pos[0] = i;
          //  System.out.println(Arrays.toString(row_counter));
            
            int col = row_to_col.get(i);
            Map<Integer, Integer> col_map = map.get(i);
            int j = col_random.nextInt(col);
            int pre_j = j;
            j = col_map.getOrDefault(j, j);
            col--;
            row_to_col.put(i, col);
            if(j < col) {
            	int temp_col = col;
            	if(col_map.containsKey(temp_col))
            		temp_col = col_map.get(temp_col);
                col_map.put(j, temp_col);
            }
            else if(j > col) {
            	int temp_col = col;
            	if(col_map.containsKey(temp_col))
            		temp_col = col_map.get(temp_col);
            	col_map.put(pre_j, temp_col);
            }
            pos[1] = j;
            return pos;
        }
        
        public void reset() {
            row_map.clear();
            row = original_row;
            row_to_col.clear();
            for(int i=0; i<row; i++) {
                map.get(i).clear();
                row_to_col.put(i, original_col);
                row_counter[i] = 0;
            }
        }
        
    }
    
   
   
   static class RandomPointinNonoverlappingRectangles {
	   static interface RandomFilpMrtixHelp {
		   int[] pick();
	   }
	   
	   static class RandomPointinNonoverlappingRectanglesHelp implements RandomFilpMrtixHelp {
		int x, y;
		int baseX,  baseY;
		Map<Integer, Integer> map;
		int total;
		Random random;
		   public RandomPointinNonoverlappingRectanglesHelp(int x, int y, int baseX, int baseY) {
			   this.x = x;
			   this.y = y;
			   this.baseX = baseX;
			   this.baseY = baseY;
			   map = new HashMap<>();
			   total = x * y;
			   random = new Random();
		}
		
		@Override
		public int[] pick() {
			int r = random.nextInt(total--);
			int x = map.getOrDefault(r, r);
			map.put(r, map.getOrDefault(total, total));
			int[] answer = {x / y  + baseX, x % y + baseY};
			return answer;
		}
		   
	   }
	   
	   
	   static class RandomPointinNonoverlappingRectanglesHelpLine implements RandomFilpMrtixHelp {
		int length, baseX, baseY;
		boolean isX;
		Map<Integer, Integer> map;
		Random random;
		public RandomPointinNonoverlappingRectanglesHelpLine(int length, int baseX, int baseY, boolean isX) {
			this.length = length;
			this.baseX = baseX;
			this.baseY = baseY;
			this.isX = isX;
			map = new HashMap<>();
			random = new Random();
		}
		@Override
		public int[] pick() {
			int r = random.nextInt(length--);
			int x = map.getOrDefault(r, r);
			map.put(r, map.getOrDefault(length, length));
			int[] answer = new int[2];
			if(isX) {
				answer[0] = baseX + x;
			}
			else {
				answer[1] = baseY + x;
			}
 			return answer;
		}
		   
	   }
	   
	   RandomFilpMrtixHelp[] array;
	   int n;
	   int[] counter;
	   Random random;
	   Map<Integer, Integer> map;
	   public void Solution(int[][] rects) {
		   n = rects.length;
		   counter = new int[n];
		   array = new RandomFilpMrtixHelp[n];
		   map = new HashMap<>();
		   for(int i=0; i<n; i++) {
			   int[] pos = rects[i];
			   int x = pos[2] - pos[0];
			   int y = pos[3] - pos[1];
			   if(x == 0) {
				   array[i] =new RandomPointinNonoverlappingRectanglesHelpLine(y, pos[0], pos[1], false);
				   counter[i] = y;
			   }
			   else if(y == 0) {
				   array[i] =new RandomPointinNonoverlappingRectanglesHelpLine(x, pos[0], pos[1], true);
				   counter[i] = x;
			   }
			   else {
				   array[i] = new RandomPointinNonoverlappingRectanglesHelp(x, y, pos[0], pos[1]);
				   counter[i] = x * y;
				   if(counter[i] == 0)
					   counter[i] = 1;
			   }
		   }
		   random = new Random();
	   }
	    
	    
	

		public int[] pick() {
			int r = random.nextInt(n);
			int index = map.getOrDefault(r, r);
			counter[index]--;
			if(counter[index] == 0) {
				map.put(r, map.getOrDefault(n, n));
				n--;
			}
			
	        //int index = random.nextInt(n);
	    	while(counter[index] <= 0) {
	    		index = random.nextInt(n);
	    	}
	    	counter[index]--;
	    	if(array[index] instanceof RandomPointinNonoverlappingRectanglesHelp) {
	    		RandomPointinNonoverlappingRectanglesHelp temp = (RandomPointinNonoverlappingRectanglesHelp)array[index];
	    		if(temp.total == 0) {
	    			return new int[] {temp.baseX, temp.baseY};
	    		}
	    	}
	    	return array[index].pick();
	    }
	   
   }
   
   
   class  RandomPointinNonoverlappingRectangles2{
	   TreeMap<Integer, Integer> tree;
	   int sum;
	   Random random;
	   int[][] array;
	   public void Solution(int[][] rects) {
	        tree = new TreeMap<>();
	        random = new Random();
	        array = rects;
	        for(int i=0; i<rects.length; i++) {
	        	int[] pos = rects[i];
	        	sum += (pos[2] - pos[0] + 1) * (pos[3] - pos[1] + 1);
	        	tree.put(sum, i);
	        }
	   }
	   public int[] pick() {
	       int r = random.nextInt(sum) + 1;
		   int i = tree.ceilingEntry(r).getValue();
		   int[] pos = array[i];
		   return new int[] {pos[0] + random.nextInt(pos[2] - pos[0] + 1),
				             pos[1] + random.nextInt(pos[3] - pos[1] + 1)};
	   }
   }
   
   
   
   class GenerateRandomPointinaCircle {
	   double R, X, Y;
	   Random random;
	   public GenerateRandomPointinaCircle(double radius, double x_center, double y_center) {
	        random = new Random();
	        R = radius;
	        X = x_center;
	        Y = y_center;
	   }
	    
	   public double[] randPoint() {
	       double x = random.nextDouble() * R;
	       double y = random.nextDouble() * R;
	       if( x * x + y *y > R * R)
	    	   return randPoint();
		   int bit = random.nextInt(2);
		   x = bit == 0 ? x : -x;
		   bit = random.nextInt(2);
		   y = bit == 0 ? y : -y;
		   return new double[] {x + X, y + Y};
	   }
   }
   
   public int nthMagicalNumber(int N, int A, int B) {
       long a = A, b = B, temp, L = 2, R = (long)1e14, mod = 1000000007;
	   while(b > 0) {
		   temp = a;
		   a = b;
		   b = temp % b;
	   }
	   
	   while(L < R) {
		   long mid = (L + R) / 2;
		   if(mid / A + mid /B - mid / (A*B/a) < N)
			   L = mid + 1;
		   else
			   R = mid;
	   }
	   
	   return (int) (L % mod);
   }
   
   
   
   public int projectionArea(int[][] grid) {
       int area = 0;
       int n = grid.length;
       for(int i=0; i<n; i++) {
    	   for(int j=0; j<n; j++) {
    		   if(grid[i][j] > 0)
    			   area++;
    	   }
       }
       for(int i=0; i<n; i++) {
    	   int max = -1;
    	   for(int j=0; j<n; j++) {
    		   max = Math.max(max, grid[i][j]);
    	   }
    	   area += max;
       }
       for(int j=0; j<n; j++) {
    	   int max = -1;
    	   for(int i=0; i<n; i++) {
    		   max = Math.max(max, grid[i][j]);
    	   }
    	   area += max;
       }
       return area;
   }
   
   
   public int[][] spiralMatrixIII(int R, int C, int r0, int c0) {
       int[][] answer = new int[R*C][2];
       int index = 0;
       answer[0] = new int[] {r0, c0};
       index++;
       if(index >= R*C)
    	   return answer;
       int X = r0, Y = c0+1;
       int length = 1;
       outer:
       while(true) {
    	   for(int i=X; i<X+length; i++) {
    		   if(i >= 0 && i < R && Y >= 0 && Y < C) {
    			   answer[index++] = new int[] {i, Y};
    			   if(index >= R*C)
    				   break outer;
    		   }
    	   }
    	   for(int i=Y-1; i>=Y-length; i--) {
    		   if(X+length-1 >= 0 && X+length-1 < R && i>=0 && i < C) {
    			   answer[index++] = new int[] {X+length-1, i};
    			   if(index >= R*C)
    				   break outer;
    		   }
    	   }
    	   
    	   for(int i=X+length-2; i>= X-1; i--) {
    		   if(i >= 0 && i < R && Y-length >= 0 && Y-length < C) {
    			   answer[index++] = new int[] {i, Y-length};
    			   if(index >= R*C)
    				   break outer;
    		   }
    	   }
    	   
    	   for(int i=Y-length+1; i<=Y; i++) {
    		   if(X-1 >= 0 && X-1 < R && i >= 0 && i<C) {
    			   answer[index++] = new int[] {X-1, i};
    			   if(index >= R*C)
    				   break outer;
    		   }
    	   }
    	   
    	   X -= 1;
    	   Y += 1;
    	   length += 2;
       }
       
       return answer;
   }
   
   
   
   
   
   
   
    
    
    
    
    public static void main(String[] args) {
		
	}
    
    
    
    
    
    
    
    
    
    
    
    
    
	
	

}
