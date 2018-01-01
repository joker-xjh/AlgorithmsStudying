package leetcode;

import java.util.ArrayList;
import java.util.List;

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
	
	

}
