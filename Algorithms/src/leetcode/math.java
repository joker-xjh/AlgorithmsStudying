package leetcode;

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
	
	

}
