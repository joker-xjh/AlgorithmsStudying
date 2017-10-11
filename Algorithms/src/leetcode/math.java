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
	
	

}
