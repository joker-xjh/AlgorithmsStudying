package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    
    
    
    
    
    
    
	
	

}
